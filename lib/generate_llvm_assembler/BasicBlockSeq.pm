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

## ****************************************************************************
## package identification

package BasicBlockSeq;


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
#   $opt_hashref: reference to a hash of options.  This parameter must be 
#	present, but may be empty.  Valid options are:
#	startPoison (boolean): true if the block should set a variable to a 
#		poison value early in the block.
# 
# Outputs: none
#   
# Return Value: a reference to the new instance
#   
# ============================================================================
sub new
{{
   my( $perl_class, $parentBasicBlock, $opt_hashref )= @_;

   my $this= {};
   bless $this, $perl_class;

   if ( defined($parentBasicBlock) )  {
      $parentBasicBlock->incrementSubBlock();
      $this->{'numSteps'}= $parentBasicBlock->{'remainingSteps'}/ 3;
      $this->{'indent'}= $parentBasicBlock->{'indent'} . "  ";
      $this->{'beginRegWidth'}= $parentBasicBlock->{'currentRegWidth'};
      $this->{'reg_prefix'}= $parentBasicBlock->{'reg_prefix'} . 
	    $parentBasicBlock->{'subBlock'};
      $this->{'label_prefix'}= $parentBasicBlock->{'label_prefix'} . 
	    $parentBasicBlock->{'subBlock'};
   } else {
      $this->{'numSteps'}= $main::arg_num_steps;
      $this->{'indent'}= "  ";
      $this->{'beginRegWidth'}= new Bitwidth( $main::arg_bitwidth );
      $this->{'reg_prefix'}= "r_";
      $this->{'label_prefix'}= "l_";
   }
   $this->{'parentBasicBlock'}= $parentBasicBlock;
   $this->{'subBlockNum'}= 0;
   $this->{'subBlock'}= "-";
   $this->{'currentRegWidth'}= $this->{'beginRegWidth'};
   $this->{'endRegWidth'}= $this->{'beginRegWidth'};
   if ( $this->{'numSteps'} < 1 )  { 
      $this->{'numSteps'}= 1; 
   }
   $this->{'remainingSteps'}= $this->{'numSteps'};
   
   # copy over the options, element by element
   foreach my $opt ( qw( startPoison restoreBitwidth ) )  {
      if ( exists($$opt_hashref{$opt}) )  {
	 $this->{'opt_hashref'}->{$opt}= $opt_hashref->{$opt}; 
      } else {
	 $this->{'opt_hashref'}->{$opt}= undef;
      }
   }

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
      # 2 variables, so later opcodes requiring 2 operands will have
      # them available.
      # TODO: find a way to consider the function's parameters as initial 
      # 	variables. 
      for ( my $ii= 0; $ii < 2; $ii++ )  {
	 my ( $def, $inst )= instruction::generate_const_inst( $this );
	 $definitions.= $def;
	 $instructions.= $inst;
      }
   }

   if ( $this->{'opt_hashref'}->{'startPoison'} )  {
     $instructions.= "  " . reg_context::getName() . 
	   "= sub nuw " . $regWidth->getName() . " 0, 1 ; generates POISON \n";
     # TODO2: replace the above operands with random numbers
   }

   for ( ; $this->{'remainingSteps'} > 0; $this->{'remainingSteps'}-- )  {
      my( $def, $inst )= instruction::generate_one_inst( $this );
      $definitions.= $def;
      $instructions.= $inst;
   }

   if ( $this->{'opt_hashref'}->{'restoreBitwidth'} )  {
      # TODO2: add code here to convert the last 1 or 2 registers to
      # the original bitwidth.
   }
 
   # clean up and return
   return ( $definitions, $instructions );
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

