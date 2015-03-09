#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_stopwatch.pm
##
## Description: provides a mechanism for measuring time between 2 events
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## Copyright (C) 2007-2007 Christian Schreiner.  All rights reserved. You
## may use, examine, or modify this file only in accordance with the Lesser
## GNU Public License, or, alternately, by special written arrangement with
## the author.  This file comes with no warranties.  If you use it and
## something breaks, you are solely responsible for cleaning up afterwards.
## 
## $Revision: 1.5 $
## $Date: 2012/09/06 02:17:22 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_stopwatch;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in cas_stopwatch::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_stopwatch";

      # ----------------------------------------------------------------------
      # other stuff

   }}


   ## ========================================================================
   ## Subroutine new()
   ## ========================================================================
   # Description: creates a new stopwatch
   #
   # Method: 
   #
   # Notes: 
   #
   # Warnings: 
   #
   # Inputs: 
   #   $superclass (provided by perl): some kind of reference to our superclass
   # 
   # Outputs: 
   #   none
   #
   # Return Value: 
   #   a reference to a new stopwatch instance
   #
   # =========================================================================
   sub new
   {{
      my( $superclass )= @_;

      my ( $perl_this )= {};
      bless $perl_this, $superclass;

      return $perl_this;
   }}


## ****************************************************************************
## private package BEGIN and END functions

package cas_stopwatch::private;

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

      use POSIX qw ( floor );

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_stopwatch";

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
## Subroutine cas_stopwatch::start()
## Subroutine cas_stopwatch::stop()
## ===========================================================================
# Description: 
#	start and stop this instance's stopwatch.  Starting erases any
#	previous elapsed time.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this (provided by perl): reference to our instance
# 
# Outputs: 
#   none
#
# Return Value: 
#   PERL_SUCCESS
#
# ============================================================================
sub cas_stopwatch::start
{{
   my( $perl_this )= @_;

   $perl_this->{'start_time'}= time();
   delete $perl_this->{'stop_time'};
  
}}
sub cas_stopwatch::stop
{{
   my( $perl_this )= @_;

   $perl_this->{'stop_time'}= time();
}}

## ===========================================================================
## Subroutine cas_stopwatch::is_running()
## ===========================================================================
# Description: returns TRUE if the stopwatch is currently running
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this (provided by perl): reference to our instance
# 
# Outputs: 
#   none
#
# Return Value: 
#   TRUE or FALSE
#
# ============================================================================
sub cas_stopwatch::is_running
{{
   my( $perl_this )= @_;

   if ( ! exists($perl_this->{'start_time'}) )  {
      return $main::FALSE;
   }
   return exists( $perl_this->{'stop_time'} );
}}


## ===========================================================================
## Subroutine cas_stopwatch::get_time()
## ===========================================================================
# Description: 
#	returns the time from the most recent start to the most recent stop.  
#	If the stopwatch is currently running, returns the time from the 
#	most recent start till now.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this (provided by perl): reference to our instance
# 
# Outputs: 
#   none
#
# Return Value: 
#   the time in seconds, or undef if the stopwatch never started
#
# ============================================================================
sub cas_stopwatch::get_time
{{
   my( $perl_this )= @_;

   if ( ! exists($perl_this->{'start_time'}) )  {
      return undef;
   }

   my ( $end_time );
   if ( exists($perl_this->{'stop_time'}) )  {
      $end_time= $perl_this->{'stop_time'};
   } else {
      $end_time= time();
   }

   return $end_time- $perl_this->{'start_time'};
}}

## ===========================================================================
## Subroutine cas_stopwatch::get_beautified_time()
## ===========================================================================
# Description: gets the time in a "beautiful", human-readable format
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this (provided by perl): reference to our instance
# 
# Outputs: 
#   none
#
# Return Value: 
#   a string similar to
#	43 minutes 21 seconds (2601 seconds)
#   or
#	2 hours 1 minutes 27 seconds (7287 seconds)
#
# ============================================================================
sub cas_stopwatch::get_beautified_time
{{
   my( $perl_this )= @_;

   my ( $delta_time )= $perl_this->get_time();

   my ( $hrs )= floor( $delta_time / 3600 );

   my ( $remainder )= $delta_time % 3600;

   my ( $min )= floor( $remainder / 60 );
   my ( $sec )= $remainder % 60;

   my ( $ret_val )= "";

   if ( $hrs == 1 ) { 
      $ret_val.= "1 hour "; 
   } elsif ( $hrs > 1 ) { 
      $ret_val.= $hrs . " hours "; 
   }

   if ( $min == 1 )  {
      $ret_val.= "1 minute "; 
   } else {
      $ret_val.= $min . " minutes "; 
   } 

   if ( $sec == 1 )  {
      $ret_val.= "1 second "; 
   } else {
      $ret_val.= $sec . " seconds "; 
   } 

   $ret_val.= "($delta_time seconds)";

   return $ret_val;
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

