#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: RegContext.pm
##
## Description: information for selecting and managing register names
##	and what type of data is in each of them.
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## RegContext.pm was written by Christian A. Schreiner at University of Utah.  
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
use Carp qw( confess );

use cas_listutil;

## ****************************************************************************
## package identification

package RegContext;


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
      $pkgname= "RegContext";

      # ----------------------------------------------------------------------
      # other stuff

      # llvm requires the first register of a function to be %1
      use constant MIN_REG_NUM => 1;

      use vars qw ( $last_basic_block );;
      $last_basic_block= undef;
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
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_class: class information (provided by PERL)
#   $prefix: prefix all variables with this string.  Should not include the 
#	sigil (%).  May be undefined if no prefix exists.
# 
# Outputs: none
#   
# Return Value: a new instance
#   
# ============================================================================
sub new
{{
   my( $perl_class, $prefix )= @_;

   my $this= {};
   bless $this, $perl_class;

   $this->init( $prefix );
   return $this;
}}

## ===========================================================================
## Subroutine initRegContext()
## ===========================================================================
# Description: initializes just as "new()" does, but acts on an
#	existing instance instead of creating a new one.
#
# Method: 
#
# Notes: 
#   * this is called "initRegContext()" instead of "init()" to
#	avoid confusion with other classes' initialization methods.
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on
#   $prefix: prefix all variables with this string.  Should not include the 
#	sigil (%).  May be undefined if no prefix exists.
# 
# Outputs: none
#   
# Return Value: TRUE
#   
# ============================================================================
sub initRegContext
{{
   my( $this, $prefix )= @_;
   $this->{'regPrefix'}= $prefix;
   $this->{'regNum'}= MIN_REG_NUM;
   $this->{'regNumMayBeOne'}= $main::TRUE;;
   $this->{'regTypeHashref'}= {}; 
	 # keys= reg names, values=TypeInteger instance
   $this->{'regNumArgs'}= 0; # the number of known arguments
   return $main::TRUE;
}}


## ===========================================================================
## Subroutine getRegName
## ===========================================================================
# Description: returns the name of a new register
#
# Inputs: 
#   $this: the instance to work on (provided by PERL)
# 
# Outputs: none
#
# Return Value: per description
#
# ============================================================================
sub getRegName
{{
   my( $this )= @_;

   my( $retVal )= "%" . $this->{'regPrefix'} . $this->{'regNum'};
   $this->{'regNum'}++;
   $this->{'regNumMayBeOne'}= $main::FALSE;;

   # note that the register exists, but we don't know its type yet.
   $this->{'regTypeHashref'}->{$retVal}= undef;

   if ( $retVal =~ m/^\s*$/ )  {
      # why is the register name a null string?
      die $main::scriptname . ": internal error 2015apr09_139631. \n";
   }
   return $retVal;
}}

## ===========================================================================
## Subroutine getPrevRegName
## ===========================================================================
# Description: gets the name of the nth previously issued register
#
# Inputs: 
#   $this: the instance to work on (provided by PERL)
#   $steps: the number of steps back to go. 0 (the default) indicates the 
#	absolute most recently issued register, 1 is the one before that, 
#	and so forth.
# 
# Outputs: none
#
# Return Value: per description
#
# ============================================================================
sub getPrevRegName
{{
   my( $this, $steps )= @_;

   my( $steps2 )= $steps;
   if ( ! defined($steps) )  { $steps2= 0; }

   if ( ($this->{'regNum'}- 1 - $steps2) < 0 )  {
      die $main::scriptname . 
	    ": internal error 2014nov24_154228, " . 
	    "codes=\"" . $this->{'regNum'} . "\", \"$steps\"\n";
   }
   my $retVal="%" . $this->{'regPrefix'} . ($this->{'regNum'}- 1- $steps2);
   if ( $retVal =~ m/^\s*$/ )  {
      # why is the register name a null string?
      die $main::scriptname . ": internal error 2015apr09_133615. \n";
   }
   return $retVal;
}}

## ===========================================================================
## Subroutine getRecentRegName
## ===========================================================================
# Description: gets the name of a recently issued register or argument.  The 
#	register/argument's type is guaranteed to be known.
#
# Inputs: 
#   $this: the instance to work on (provided by PERL)
# 
# Outputs: none
#
# Return Value: per description
#
# ============================================================================
sub getRecentRegName
{{
   my( $this )= @_;

   if ( $main::FALSE )  {
      # An old way of implementing this function that might still be useful.
      my( $limit )= 10;
      my( $max_returnable_regNum )= $this->{'regNum'}- 3;
      if ( $max_returnable_regNum < $limit )  { 
	 $limit= $max_returnable_regNum; 
      };
      my( $rr )= $max_returnable_regNum- int( rand()*$limit );
      if ( $rr < MIN_REG_NUM )  {
      die $main::scriptname . 
	       ": internal error 2014nov24_210556, code=\"$rr\"\n";
      }
      return "%" . $this->{'regPrefix'} . $rr;
   }

   # find all of the registers/arguments with known types
   my @regList;
   foreach my $reg ( keys( %{$this->{'regTypeHashref'}} ) )  {
      if ( defined($this->{'regTypeHashref'}->{$reg} ) )  {
	 push @regList, $reg;
      }
   }

   # choose one at random and return
   my $numRegs= scalar( @regList );
   if ( $numRegs < 1 )  {
      # There should be at least _some_ registers, because we forcibly create
      # 2 registers if there aren't any arguments.
      die $main::scriptname . ": internal error 2015apr09_135003. \n";
   }
   my $retVal= $regList[ int(rand()*$numRegs) ]; 
   if ( $retVal =~ m/^\s*$/ )  {
      # why is the register name a null string?
      print "reg list= < " . cas_listutil::quote2( \@regList ) . "> \n";;
      print "   num regs= $numRegs. \n";;
      die $main::scriptname . ": internal error 2015apr09_133530. \n";
   }
   return $retVal;
}}

## ===========================================================================
## Subroutine registerArg()
## ===========================================================================

# Description: registers a function argument and its type, so future
#	instructions can use it.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work on
#   $name: name of the argument, which must not already be registered
#   $type: type of the argument (formatted as an TypeInteger instance)
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS
#   
# ============================================================================
sub registerArg
{{
   my( $this, $name, $type )= @_;

   if ( exists( $this->{'regTypeHashref'}->{$name} ) )  {
      # the argument was already registered
      die $main::scriptname . 
	    ": internal error 2015apr09_111444, code=\"$name\". \n";
   }
   $this->{'regTypeHashref'}->{$name}= $type;
   $this->{'regNumArgs'}++;
   return $main::PERL_SUCCESS;
}}

## ===========================================================================
## Subroutine reportType()
## ===========================================================================
# Description: reports the type of a register, so it can be used by future 
#	instructions
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work on
#   $name: name of the register, which must already have been created via 
#	getName(), and not have a type reported.
#   $type: type of the register (formatted as an TypeInteger instance)
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS
#   
# ============================================================================
sub reportType
{{
   my( $this, $name, $type )= @_;

   if ( !exists( $this->{'regTypeHashref'}->{$name} ) )  {
      # the register hasn't been created yet 
      die $main::scriptname . 
	    ": internal error 2015apr09_112208, code=\"$name\". \n";
   }
   if ( defined( $this->{'regTypeHashref'}->{$name} ) )  {
      # the register already had its type reported 
      die $main::scriptname . 
	    ": internal error 2015apr09_112337, code=\"$name\". \n";
   }
   $this->{'regTypeHashref'}->{$name}= $type;
   return $main::PERL_SUCCESS;
}}

## ===========================================================================
## Subroutine getRegType()
## ===========================================================================
# Description: returns the data type stored in a register
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on
#   $regName: the name of the register whose type should be returned
# 
# Outputs: none
#   
# Return Value: a TypeInteger instance, 
#   or undef if the register name is not recognized
#
# ============================================================================
sub getRegType
{{
   my( $this, $regName )= @_;

   my $retVal= $this->{'regTypeHashref'}->{$regName};
   return $retVal;
}}

## ===========================================================================
## Subroutine carpIfRegNumReset()
## ===========================================================================
# Description: reports an error (and probably a stack trace) if field regNum
#	got reset to 1
#
# Method: 
#
# Notes: this whole method is for debugging ;;
#
# Warnings: 
#
# Inputs: 
#   $this: the instance in question
#   $basicBlock: the basic block in question
#   $msg: a message to include in the error notification
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS, or 
#	if an error is reported, does not return
#   
# ============================================================================
sub carpIfRegNumReset
{{;;
   my( $this, $basicBlock, $msg )= @_;
   if ( !defined($last_basic_block) )  {
      $last_basic_block= $basicBlock;
   } else {
      if ( "$basicBlock" ne "$last_basic_block" )  {;;
	  my ( $mm )= "";
	 $mm.= "got a new basic block: \"$basicBlock\", \n"; 
	 $mm.= "\t" . "for ftn \"" ;
	 $mm.= $basicBlock->{'optHashref'}->{'ftnName'} . "\", \n";
	 $mm.= "\t" . "last was \"$last_basic_block\". \n";
	 $mm.= "\t" . "for ftn \"";
	 $mm.= $last_basic_block->{'optHashref'}->{'ftnName'} . "\", \n"; 
	 Carp::confess( $mm . $msg );
      }
   }
   if ( $this->{'regNum'} == 1 and !$this->{'regNumMayBeOne'} )  {
      print $msg;
      Carp::confess ( "   regNum=1, remaining steps= " . 
	    $this->{'remainingSteps'} . ".\n" );;
   }
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: 
#   $this: the instance whose field to get
#
# Return Value: the field's value
#
# ============================================================================
# template is 5 lines long
#sub name
#{{
#   my($this)= @_;
#   return ;
#}}

# returns the number of arguments registered
sub numArgs
{{
   my($this)= @_;
   return $this->{'regNumArgs'};
}}

## ===========================================================================
## Short set subroutines
## ===========================================================================
# Description: short subroutines that only set one field
#
# Inputs: 
#   $this: the instance whose field to get
#   $xx: the field's new value
#
# Return Value: none
#
# ============================================================================
#sub name
#{{
#   my ($this, $xx)= @_;
#}}


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

