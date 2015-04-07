#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: Bitwidth.pm
##
## Description: 
#	Holds information on a bitwidth, such as its maximum and minimum
#	integer values, a random number generator, and so forth.
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## Bitwidth.pm was written by Christian A. Schreiner at University of Utah.  
## Copyright (C) 2014-2014 University of Utah.  All rights reserved. You
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

package Bitwidth;


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
      $pkgname= "Bitwidth";

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
## Subroutine new
## ===========================================================================
# Description: creates a new instance
#
# Inputs:
#   $class: information about the class (provided by perl)
#   $target_width: an integer indicating the preferred with to work with.
#	If undefined, a random value will be chosen.
# 
# Outputs: none
#
# Return Value: a new instance
#
# ============================================================================
sub new
{{
   my( $class, $target_width )= @_;

   my( $perl_this )= {};
   bless $perl_this, $class;

   # TODO3: adjust this to exactly handle 64+-bit integers
   my( $max_width )= 32;
   my( $abs_min_width )= 1;
   my( $pref_min_width )= 6;
   my( $width );
   if ( $target_width eq "" )  {
      $width= int( rand()*($max_width-$pref_min_width) + $pref_min_width );
   } else {
      if ( $target_width < $abs_min_width or $target_width > $max_width )  {
	 die $main::scriptname . ": bitwidth out of range. \n";
      }
      $width= $target_width;
   }
   $perl_this->{'bitwidth'}= $width;
   $perl_this->{'maxVal'}= 2** $width- 1;
   $perl_this->{'minVal'}= 0;
   $perl_this->{'name'}= "i" . $width;

   return $perl_this;
}}

## ===========================================================================
## Subroutine getRandVal
## ===========================================================================
# Description: returns a random value for this size of integer
#
# Inputs:
#   $perl_this: the instance in question (provided by PERL)
#   $margin: do not return a value within this distance of the min or max value.
#	Defaults to 0.  This guarantees that the value returned can be 
#	incremented or decremented by up to this amount without overflow.
# 
# Outputs: none
#
# Return Value: per Description
#
# ============================================================================
sub getRandVal
{{
   my( $perl_this, $margin )= @_;

   my( $margin2 )= $margin;
   if ( !defined($margin) )  { $margin2= 0; }

   my( $range )= $perl_this->{'maxVal'} - $perl_this->{'minVal'}- 2*$margin2;
   if ( $main::debug_flag )  {
     print "max=\"" . $perl_this->{'maxVal'} . "\", min=\"" . 
	   $perl_this->{'minVal'} . "\", margin2=\"$margin2\"\n";
     print "range=\"$range\"\n";
   }
   my $val= int( rand() * $range+ $perl_this->{'minVal'}+ $margin2 );

   return $val;
}}

## ===========================================================================
## Subroutine getRandShiftVal
## ===========================================================================
# Description: returns a random number from 0 to this instance's bitwidth. 
#	This is the legal range that a register may be shifted right or left.
#
# Note: strange, shifting by the width of the register is sometimes allowed 
#	and sometimes not.  For example, 
#	   %9= lshr i13 %8, 13
#	   %11= ashr  i13 %10, 13
#	are ok, but
#	   %9= lshr exact i13 %8, 13
#	and
#	   %11= ashr exact i13 %10, 13
#	are not.  Even though the 'exact' flag is supposted to generate
#	poison if 0 bits are shifted out, its presence causes
#	execution to halt with an "overshift" error.  Officially, shifting by 
#	the register width or larger yields an undefined result (NOT undefined 
#	behavior). Is the fact that this is sometimes allowed and sometimes 

#	not an error in the interpreter? TODO2: look into this.  If
#	the result is undefined, I assume the interpreter should replaced it 
#	with a random value of appropriate width.
#
# Inputs:
#   $perl_this: the instance in question (provided by PERL)
# 
# Outputs: none
#
# Return Value: a number n such that 0 <= n < bitwidth
#
# ============================================================================
sub getRandShiftVal
{{
   my( $perl_this )= @_;

   my( $val )= int( rand() * ($perl_this->{'bitwidth'}) );
   return $val;
}}

#template is 16 lines long
## ===========================================================================
## Subroutine sub_name
## ===========================================================================
# Description:
#
# Inputs:
# 
# Outputs:
#
# Return Value:
#
# ============================================================================
#sub sub_name
#{{
#   my( )= @_;
#}}

## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: $perl_this: the instance in question (provided by PERL)
#
# Return Value: the field's value
#
# ============================================================================
#sub sub_name {{ my($perl_this)= @_; return $perl_this->{'xx'}; }}
sub getBitwidth {{ my($perl_this)= @_; return $perl_this->{'bitwidth'}; }}
sub getMaxVal {{ my($perl_this)= @_; return $perl_this->{'maxVal'}; }}
sub getMinVal {{ my($perl_this)= @_; return $perl_this->{'minVal'}; }}
sub getName {{ my($perl_this)= @_; return $perl_this->{'name'}; }}

## ===========================================================================
## Short set subroutines
## ===========================================================================
# Description: short subroutines that only set one field
#
# Inputs: the field's new value
#
# Return Value: none
#
# ============================================================================
#sub sub_name
#{{
#   my ($ii)= @_;
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

