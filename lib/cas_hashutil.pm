#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: cas_perl_utilities
## Module Name: cas_hashutil.pm
##
## Description: 
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## Copyright (C) 2012-2012 Christian Schreiner.  All rights reserved. You
## may use, examine, or modify this file only in accordance with the Lesser
## GNU Public License, or, alternately, by special written arrangement with
## the author.  This file comes with no warranties.  If you use it and
## something breaks, you are solely responsible for cleaning up afterwards.
## 
## $Revision: 1.3 $
## $Date: 2012/10/18 15:37:44 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_hashutil;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in cas_hashutil::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_hashutil";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package cas_hashutil::private;

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
      $pkgname= "cas_hashutil";

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
## Subroutine cas_hashutil::to_st()
## ===========================================================================
# Description: 
#	converts a hash to a string in a format useful for printing and 
#	other human consumption.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $hashref: reference to the hash to act on
#
#   Planned expansion:
#   opt_hashref: a reference to a hash containing any of the following
#	options:
#	recurse_depth: set to the number of levels through which the hash
#		should recurse.  The default is 1.  0 indicates infinite 
#		recursion.
#	indent: a string to prepend to each line.  This may not be practical
#		to apply to fields containing multiline strings.
# 
# Outputs: none
#   
# Return Value: 
#   a string showing the hash's contents
#
# ============================================================================
sub cas_hashutil::to_st
{{
   my( $hashref )= @_;

   my($result)="";
   foreach my $key ( sort keys %$hashref )  {
      $result.= "\"$key\" => \"" . $hashref->{$key} . "\"\n";   
   }
   return $result;
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

