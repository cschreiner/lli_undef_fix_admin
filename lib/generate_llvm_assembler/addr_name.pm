#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: addr_name.pm
##
## Description: 
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## addr_name.pm was written by Christian A. Schreiner at University of Utah.  
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

package addr_name;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in addr_name::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "addr_name";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package addr_name::private;

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
      $pkgname= "addr_name";

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

