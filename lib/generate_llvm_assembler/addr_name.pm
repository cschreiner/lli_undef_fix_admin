#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: addr_name.pm
##
## Description: package to create and manage address names
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

      use vars qw( %addr_used_hash );
      %addr_used_hash= ();

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
## Subroutine get()
## ===========================================================================
# Description: generates a unique address name.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $prefix: the prefix to put on the address name
# 
# Outputs: none
#   
# Return Value: an string containing an address of form "@prefix_xxxxxx", 
#   where prefix is the specified prefix, and xxxxxx is randomly generated. 
#   The xxxxxx may have more characters than shown.
#
# ============================================================================
sub addr_name::get
{{
   my( $prefix )= @_;

   my @CONSONANT_LIST= qw( b c d f g h j k l m n p q r s t v w x z );
   my @VOWEL_LIST= qw( a e i o u );

   for ( my $safety_counter= 0; $safety_counter < 1000; $safety_counter++ )  {
      my( $addr_core )= "";
      for ( my $ii= 0; $ii < 2; $ii++ )  {
         # Each of these iterations multiplies the number of permutations 
	 # by 2000.
	 my $aa= "";
	 $aa= int( scalar(@CONSONANT_LIST)* rand() );
	 $addr_core.= $CONSONANT_LIST[ $aa ];
	 $aa= int( scalar(@VOWEL_LIST)* rand() );
	 $addr_core.= $VOWEL_LIST[ $aa ];
	 $aa= int( scalar(@CONSONANT_LIST)* rand() );
	 $addr_core.= $CONSONANT_LIST[ $aa ];
      }
      if ( exists($addr_used_hash{$addr_core}) )  { next; }
      my( $addr)= '@' . $prefix . '_' . $addr_core;
      if ( 1 )  {;;
	 print "   returning address \"$addr\"\n";
      }
      return $addr;
   }

   # we couldn't generate a unique sequence in a reasonable number of tries
   die $main::scriptname . ": internal error 2015apr09_220001. \n";
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

