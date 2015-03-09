#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_listutil
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description: CAS list handling utilities
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.9 $
## $Date: 2007/01/12 04:36:53 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_listutil;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in cas_listutil::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)
      require cas_exit;

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_listutil";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package cas_listutil::private;

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
      $pkgname= "cas_listutil";

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
## Subroutine cas_listutil::is_st_in_list()
## ===========================================================================
# Description: returns TRUE if the given string is in the given list
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $st: the string to look for
#   $list_ref: a reference to the list to look through
# 
# Outputs: none
#
# Return Value: TRUE or FALSE
#
# ============================================================================
sub cas_listutil::is_st_in_list
{{
   my($list_ref, $st )= @_;

   &cas_listutil::insure_list_ref( $list_ref, "" );

   my($this_member);
   foreach $this_member ( @$list_ref )  {
      if ( $st eq $this_member )  { return $main::TRUE; }
   }
   return $main::FALSE;
}}

## ===========================================================================
## Subroutine cas_listutil::quote2
## ===========================================================================
# Description: "double_quote"
#	returns a string containing each member of the input list 
#	enclosed with double quotes and separated by commas.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs: $list_ref: a reference to the list to put into the string
# 
# Outputs:
#
# Return Value:
#
# ============================================================================
sub cas_listutil::quote2
{{
   my($list_ref )= @_;

   &cas_listutil::insure_list_ref ( $list_ref, "" );

   # return a null string if we have a null list
   if ( scalar(@$list_ref) == 0 )  { return ""; }

   # return the results
   return '"' . join ( '", "', @$list_ref) . '"';
}}


## ===========================================================================
## Subroutine cas_listutil::insure_list_ref()
## ===========================================================================
# Description: makes sure that the supplied reference is a list reference.
#	Panics otherwise.
#
# Method: hash references are also allowed, since hashes are so similar in 
#	most cases.
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $questionable_ref: the reference we're questioning
#   $msg: a string containing a message in case we have to panic.
# 
# Outputs: none
#
# Return Value: main::TRUE;
#
# ============================================================================
sub cas_listutil::insure_list_ref()
{{
   my($questionable_ref, $msg )= @_;

   if ( ref($questionable_ref) eq "ARRAY" || 
	 ref($questionable_ref) eq "HASH" )  {
      return $main::TRUE;
   }

   &cas_exit::panic ( $main::scriptname . "::" . $pkgname, "",
         "internal error 189.20feb2000, ", 
	 "need a list reference instead of \"" . ref($questionable_ref) . "\",",
	 "$msg " );
}}


## ===========================================================================
## Subroutine cas_listutil::remove_duplicate_st
## ===========================================================================
# Description: removes duplicate list entries from the indicated list.
#	Does comparisions using string methods.  May rearrange the order
#	of elements in the list.
#
# Method:
#
# Notes:
#   CAS todo: add test cases for lists of 0, 1, 2, and 3 elements, as
#	well as lists of more elements.
#
# Warnings:
#   CAS todo: check the past-end-of-array-subscript-reads to make sure
#	they don't do funny things to the array.
#
# Inputs:
#   $list_ref: a reference to the list to remove duplicates from
# 
# Outputs:
#
# Return Value:
#   0: no duplicates found
#   1: all duplicates found were removed
#   -1: could not remove all duplicates 
#
# ============================================================================
sub cas_listutil::remove_duplicate_st
{{
   my($list_ref )= @_;

   &cas_listutil::insure_list_ref ( $list_ref, "" );

   my ( $ret_val )= 0;
   my ( $list_size ) = scalar( @$list_ref );
   if ( $list_size <= 1 )  {
      return 0;
   }

   @$list_ref= sort @$list_ref;

   my ($ii);
   for ( $ii= $#$list_ref- 1; $ii >= $[; $ii -- )  {
      if ( $$list_ref[$ii] eq $$list_ref[$ii+1] )  {
         @$list_ref= ( @$list_ref[ $[ .. $ii ], 
	       @$list_ref[ $ii+ 2 .. $#$list_ref ] ); 
         $ret_val= 1;
      }
   }
   
   return $ret_val;
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


#template is 23 lines long
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

