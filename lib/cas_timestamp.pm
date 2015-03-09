#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_timestamp.pm
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description:
#	cas perl library
#	module to handle timestamps
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.4 $
## $Date: 2007/01/12 04:36:53 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_timestamp;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in cas_timestamp::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_timestamp";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package cas_timestamp::private;

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
      $pkgname= "cas_timestamp";

      # ----------------------------------------------------------------------
      # other stuff

      use vars qw ( $timestamp_st );
      $timestamp_st= "";

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
## Subroutine cas_timestamp::get()
## ===========================================================================
# Description: returns a timestamp
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
sub cas_timestamp::get
{{
   #my( )= @_;

   if ( $timestamp_st eq "" )  {
      my ( $sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $isdst)=
         localtime();

      # set a few things into a more user-friendly format
      if ( $mon == 0 )  {
         $mon= "jan";
      } elsif ( $mon == 1 )  {
         $mon= "feb";
      } elsif ( $mon == 2 )  {
         $mon= "mar";
      } elsif ( $mon == 3 )  {
         $mon= "apr";
      } elsif ( $mon == 4 )  {
         $mon= "may";
      } elsif ( $mon == 5 )  {
         $mon= "jun";
      } elsif ( $mon == 6 )  {
         $mon= "jul";
      } elsif ( $mon == 7 )  {
         $mon= "aug";
      } elsif ( $mon == 8 )  {
         $mon= "sep";
      } elsif ( $mon == 9 )  {
         $mon= "oct";
      } elsif ( $mon == 10 )  {
         $mon= "nov";
      } elsif ( $mon == 11 )  {
         $mon= "dec";
      } 

      $year+= 1900;

      $mday= sprintf ( "%2d", $mday );

      # set the timestamp
      $timestamp_st= sprintf ( "%4.4d%s%2.2d_%2.2d%2.2d%2.2d", 
	    $year, $mon, $mday, $hour, $min, $sec);

      if ( $main::debug_flag )  {
         # make sure there aren't any spaces in the timestamp
         &cas_exit::assert ( $timestamp_st !~ m/ /, $pkgname, 
               "Internal error  169.4nov2000, code= \"$timestamp_st\". " );
      }
   }

   # clean up and return
   return $timestamp_st;
}}


#template is 25 lines long
## ===========================================================================
## Subroutine sub_name()
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
#sub sub_name
#{{
#   my( )= @_;
#}}





## ****************************************************************************
## end this package 
package main;

# the package must return 1
1;

