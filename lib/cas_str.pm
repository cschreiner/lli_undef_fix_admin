#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_str.pm
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description: CAS string handling utilities
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
## package identification

package cas_str::private;


## ****************************************************************************
## package BEGIN and END functions

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
      $packagename= "cas_str::private";

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
## Subroutine cas_str::essence
## ===========================================================================
# Description: returns a copy of string that is stripped of all leading
#       trailing whitespace 
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
# 
# Outputs:
#
# Return Value:
#
# ============================================================================
sub cas_str::essence
{{
   my($orig_st)= @_;

   $orig_st =~ s/^\s*//;
   $orig_st =~ s/\s*$//;

   return $orig_st;
}}



## ===========================================================================
## Subroutine sub_name
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
# Outputs:
#
# Return Value:
#
# ============================================================================
#sub sub_name
#{{
#   my( )= @_;
#}}


#template
## ===========================================================================
## Subroutine sub_name
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
# Outputs:
#
# Return Value:
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

