#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: BasicBlockSeq.pm
##
## Description: code to generate a basic block.
#	Recall that, in general, a basic block is a sequence of instructions
#	such that if any of them are executed, all of them are executed.  A
#	basic block sequence is a sequence of 1+ basic blocks, possibly
#	interrupted by a control flow statement (e.g. branch, switch, or
#	similar).
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## BasicBlockSeq.pm was written by Christian A. Schreiner at University of Utah.  
## Copyright (C) 2015-2015 University of Utah.  All rights reserved. You
## may use, examine, or modify this file only in accordance with the Lesser
## GNU Public License, or, alternately, by special written arrangement with
## the author.  This file comes with no warranties.  If you use it and
## something breaks, you are solely responsible for cleaning up afterwards.
## 
## $Revision: 1.3 $
## $Date: 2014/04/04 10:25:56 $
## $Author: cas $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;
use Carp qw( cluck confess croak );

use TypeInteger;
use instruction;

## ****************************************************************************
## package identification

package BasicBlockSeq;
use parent qw ( RegContext );

## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # we don't set basic constants here, as we inherit them from package main
      # But we do need to make sure they're defined properly
      if ( !defined($main::TRUE) or !defined($main::FALSE) or 
	    !defined($main::PERL_SUCCESS) or !defined($main::PERL_FAILURE) or
            !defined($main::EXIT_SUCCESS) or !defined($main::EXIT_FAILURE) or
            !defined($main::scriptname) or !defined($main::debug_flag)  )  {
         die "internal error: basic constants improperly defined ";
      }

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "BasicBlockSeq";

      # ----------------------------------------------------------------------
      # other stuff

   }}

   # =========================================================================
   # subroutine END
   # =========================================================================
   sub END
   {{
      
   }}

## ****************************************************************************
## start the package


## ===========================================================================
## Subroutine new()
## ===========================================================================
# Description: creates a new instance
#
# Method: 
#
# Notes: reads command line arguments
#
# Warnings: 
#
# Inputs: 
#   $perl_class: class info (provided by PERL)
#   $parentBasicBlock: the basic block (or basic block sequence) invoking this
#	one, or 
#	undef if this is the first basic block of a function.
#   $optHashref: reference to a hash of options.  This parameter must be 
#	present, but may be empty.  Valid options are:
#	startPoison (boolean): true if the block should set a variable to a 
#		poison value early in the block.
#       numSteps (unsigned): the number of steps (instructions) the block 
#		should contain.  The actual number may be slightly higher 
#		in order to do data conversions and other semantic 
#		housekeeping.
#	startType (TypeInteger instance): initial integer type for the 
#		block.  This is required if the block has no parent.  If the
#		block has a parent, this defaults to the current type of
#		the parent.
#	stopType (TypeInteger instance): final integer type for the block. 
#		If omitted, defaults to startType.
# 
# Outputs: none
#   
# Return Value: a reference to the new instance
#   
# ============================================================================
sub new
{{
   my( $perl_class, $parentBasicBlock, $optHashref )= @_;

   my $this= {};
   bless $this, $perl_class;

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # copy over the options, element by element

   # set the default values
   $this->{'optHashref'}= {
			    'startPoison' => $main::FALSE,
			    'numSteps' => 10,
			    'startType' => undef,
			    'stopType' => undef, 
			   };

   # replace defaults with whatever options were handed in
   foreach my $opt ( keys( %{$this->{'optHashref'}} ) )  {
      if ( exists($$optHashref{$opt}) )  {
	 $this->{'optHashref'}->{$opt}= $optHashref->{$opt}; 
      }
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # main initialization based on the parent block
   if ( defined($parentBasicBlock) )  {
      $parentBasicBlock->incrementSubBlock();
      $this->{'numSteps'}= int($parentBasicBlock->{'remainingSteps'}/ 3);
      if ( defined( $this->{'optHashref'}->{'numSteps'} ) )  {
	 $this->{'numSteps'}= $this->{'optHashref'}->{'numSteps'}; 
      } else {
	 $this->{'numSteps'}= int($parentBasicBlock->{'remainingSteps'}/ 3);
      }
      $this->{'indent'}= $parentBasicBlock->{'indent'} . "  ";
      if ( defined( $this->{'optHashref'}->{'startType'} ) )  {
	 $this->{'startType'}= $this->{'optHashref'}->{'startType'}; 
      } else {
	 $this->{'startType'}= $parentBasicBlock->{'currentType'};
      }
      $this->{'regPrefix'}= $parentBasicBlock->{'regPrefix'} . 
	    $parentBasicBlock->{'subBlock'};
      $this->{'labelPrefix'}= $parentBasicBlock->{'labelPrefix'} . 
	    $parentBasicBlock->{'subBlock'};
   } else {
      if ( defined( $this->{'optHashref'}->{'numSteps'} ) )  {
	 $this->{'numSteps'}= $this->{'optHashref'}->{'numSteps'}; 
      } else {
	 # we need a number of steps
	 die $main::scriptname . ": internal error 2015apr09_115148. \n";
      }
      $this->{'indent'}= "  ";
      if ( defined( $this->{'optHashref'}->{'startType'} ) )  {
	 $this->{'startType'}= $this->{'optHashref'}->{'startType'}; 
      } else {
	 # no known starting type
	 die $main::scriptname . ": internal error 2015apr09_114443.\n";
      }
      $this->{'regPrefix'}= ""; 
	    # LLVM IR rules require the first register to be named %1, with no
	    # prefix.  For consistency, other registers in this block don't
	    # have a prefix, either.
      $this->{'labelPrefix'}= "l_";
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # other main initialization 
   $this->{'parentBasicBlock'}= $parentBasicBlock;
   $this->{'subBlockNum'}= 0;
   $this->{'subBlock'}= "-";
   $this->{'currentType'}= $this->{'startType'};
   if ( defined( $this->{'optHashref'}->{'stopType'} ) )  {
      $this->{'stopType'}= $this->{'optHashref'}->{'stopType'}; 
   } else {
      $this->{'stopType'}= $this->{'startType'};
   }
   if ( $this->{'numSteps'} < 1 )  { 
      $this->{'numSteps'}= 1; 
   }
   $this->{'remainingSteps'}= $this->{'numSteps'};
   
   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # initialize superclass with stuff derived from the above
   $this->initRegContext( $this->{'regPrefix'} );

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # clean up and return
   return $this;
}}

## ===========================================================================
## Subroutine incrementSubBlock()
## ===========================================================================
# Description: increments the subblock designation
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work with (provided by PERL)
# 
# Outputs: none
#   
# Return Value: TRUE
#   
# ============================================================================
sub incrementSubBlock
{{
   my( $this )= @_;

   use constant DIGITS=> qw ( 
	 a b c d e f g h i j k l m n o p q r s t u v w x y z );
   use constant BASE=> scalar(DIGITS);
   $this->{'subBlockNum'}++;

   my $rest= $this->{'subBlockNum'};
   my $st= "";

   while ( $rest > 0 )  {
      $st= DIGITS->[$rest % BASE] . $st;
      $rest= $rest / BASE;
   }

   # a subblock always begins with a capital letter
   substr($st, $[)= uc( substr($st, $[) );

   # clean up and return
   $this->{'subBlock'}= $st;
   return $main::TRUE;
}}

## ===========================================================================
## Subroutine generate()
## ===========================================================================
# Description: generates the block
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work with
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub generate
{{
   my( $this )= @_;

   our ( $definitions, $instructions );

   if ( !defined($this->{'parentBasicBlock'}) )  {
      # A basic block beginning a function must initially set at least
      # 2 registers, so later opcodes requiring 2 operands will have
      # them available.  If there aren't enough arguments provided, create
      # enough registers to compensate.
      for ( my $ii= $this->numArgs(); $ii < 2; $ii++ )  {
	 my ( $def, $inst )= instruction::generate_const_inst( $this );
	 $definitions.= $def;
	 $instructions.= $inst;
      }
   }

   if ( $this->{'optHashref'}->{'startPoison'} )  {
     $instructions.= "  " . $this->getRegName() . 
	   "= sub nuw " . $this->currentType()->getName() . 
	    " 0, 1 ; generates POISON \n";
     # TODO2: replace the above operands with random numbers
   }

   print "remainingSteps=" . $this->{'remainingSteps'} . ".\n";;
   while ( $this->{'remainingSteps'} > 0 )  {
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      my( $def, $inst )= instruction::generate_one_inst( $this );
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      $definitions.= $def;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      {
	 # TODO: maybe move this to a method called by the instr. generator
	 $this->{'remainingSteps'}--;
	 $instructions.= $this->{'indent'} . "; step \n";
      }
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      $instructions.= $inst;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      if ( $inst =~ m/%1\D.*%1\D/ )  {
	 Carp::confess( $main::scriptname . 
	    ": internal error 2015apr10_102650 " .
	    "(two %1s in inst)" );;
      }
      if ( $instructions =~ m/%1\D.*%1\D/ )  {
	 Carp::confess( $main::scriptname . 
	    ": internal error 2015apr10_102820 " .
	    "(two %1s in instructions)" );;
      }
   }

   if ( $this->{'currentType'}->compareTo( $this->{'stopType'} ) != 0 )  {
      # TODO2: add code here to convert the last 1 or 2 registers to
      # the stop type.
      die $main::scriptname . ": internal error 2015apr09_114820.\n";
   }
 
   # clean up and return
   return ( $definitions, $instructions );
}}

## ===========================================================================
## short getter routines
## ===========================================================================
# Description: return the corresponding field
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance whose field will be returned
# 
# Return Value: the field value
#   
# ============================================================================
# template is 5 lines long
#sub name
#{{
#   my( $this )= @_;
#   return $this->{'xx'};
#}}

# returns the TypeInteger instance defining the stop type
sub getStopType
{{
   my( $this )= @_;
   return $this->{'stopType'};
}}

sub indent
{{
   my( $this )= @_;
   return $this->{'indent'};
}}

sub getStartPoison
{{
   my( $this )= @_;
   return $this->{'optHashref'}->{'startPoison'};
}}

sub numRemainingSteps
{{
   my( $this )= @_;
   return $this->{'remainingSteps'};
}}

sub currentType
{{
   my( $this )= @_;
   return $this->{'currentType'};
}}


#template is 25 lines long
## ===========================================================================
## Subroutine name()
## ===========================================================================
# Description: 
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   
# 
# Outputs: 
#   
#
# Return Value: 
#   
#
# ============================================================================
#sub name
#{{
#   my( )= @_;
#}}





## ****************************************************************************
## end this package 
package main;

# the package must return 1
1;

