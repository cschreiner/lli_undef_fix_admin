#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: RegContext.pm
##
## Description: information for selecting and managing register names
##
## TODO: do we also need to add code to track what data type each
## register contains, e.g. i24 vs i48 and such?
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

   $this->{'regPrefix'}= $prefix;
   $this->{'regNum'}= MIN_REG_NUM;
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

   my( $ret_val )= "%" . $this->{'regPrefix'} . $this->{'reg_num'};
   $this->{'regNum'}++;
   return $ret_val;
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

   if ( ($this->{'regNum'}- 1 - $steps) < 0 )  {
      die $main::scriptname . 
	    ": internal error 2014nov24_154228, " . 
	    "codes=\"" . $this->{'regNum'} . "\", \"$steps\"\n";
   }
   return "%" . $this->{'regPrefix'} . ($this->{'regNum'}- 1- $steps2);
}}

## ===========================================================================
## Subroutine getRecentRegName
## ===========================================================================
# Description: gets the name of a recently issued register
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

   my( $limit )= 10;
   my( $max_returnable_reg_num )= $this->{'regNum'}- 3;
   if ( $max_returnable_reg_num < $limit )  { 
      $limit= $max_returnable_reg_num; 
   };
   my( $rr )= $max_returnable_reg_num- int( rand()*$limit );
   if ( $rr < MIN_REG_NUM )  {
      die $main::scriptname . 
	    ": internal error 2014nov24_210556, code=\"$rr\"\n";
   }
   return "%" . $this->{'regPrefix'} . $rr;
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
# Inputs: none
#
# Return Value: the field's value
#
# ============================================================================
#sub sub_name
#{{
#   return ;
#}}

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

