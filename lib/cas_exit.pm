#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_exit
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description:
#	gives various ways of exiting from a PERL script, with appropriate
#	error messages.
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.18 $
## $Date: 2007/01/12 04:36:53 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## package identification

package cas_exit::private;


## ****************************************************************************
## package prerequisites (use's and require's)

use strict;


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
      use vars qw ( $packagename );
      $packagename= "cas_exit::private";

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
## Subroutine cas_exit::panic()
## ===========================================================================
# Description: prints the indicated message and dies.  In debug mode, also
#	prints caller information so we can tell how the current function
#	got called.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs: 
#   * the name of the calling package (which is only printed in debug mode)
#   * a list of strings, that are printed one string per line.  
#	All strings after the first one are indented one tab space.
# 
# Outputs: none
#
# Return Value: does not return
#
# ============================================================================
sub cas_exit::panic()
{{
   my ( $calling_package ) = shift @_;

   # print out the public panic message
   {
      my ( $pkg );

      if ( $main::debug_flag )  {
         $pkg= $calling_package . ": ";
      } else {
         $pkg= "";
      }
      print STDERR $main::scriptname . ": " .$pkg .  join ( "\n\t", @_ ) . 
	    "\n\n";
   }

   # print out the contents of the call stack so we can trace how this
   # panic happened.
   if ( $main::debug_flag )  {
      # print out call stack via Carp, so we can get the arguments passed in
      use Carp qw(cluck);
      cluck "call history: \n";  # warn about errors with stack backtrace
      print "\n\n";

      # print out the call stack via caller, so we can see more of the
      #	context of each call.
      my ( $call_frame );
      my ( $package, $filename, $line, $subroutine, $hasargs, $wantarray, 
	    $evaltext, $is_require );

      # Method: keep looping until we get an invalid package.
      CALL_FRAME_LABEL:
      for ( $call_frame= 0, $package= 0; $main::TRUE; $call_frame++ )  {
         # read this call frame
         ($package, $filename, $line, $subroutine, $hasargs, $wantarray, 
	       $evaltext, $is_require)=  caller($call_frame);

         # see if the package is valid
         if ( ! defined($package) )  { last CALL_FRAME_LABEL; }

         # check these 2 variables, the caller function does not always 
         # return them
         if ( !defined($package) )    { $package= "undefined"; }
         if ( !defined($filename) )   { $filename= "undefined"; }
         if ( !defined($line) )       { $line= "undefined"; }
         if ( !defined($subroutine) ) { $subroutine= "undefined"; }
         if ( !defined($hasargs) )    { $hasargs= "undefined"; }
         if ( !defined($wantarray) )  { $wantarray= "undefined"; }
         if ( !defined($evaltext) )   { $evaltext= "undefined"; }
         if ( !defined($is_require) ) { $is_require= "undefined"; }
         
         # print information about this frame
         print $packagename; 
	 print ":panic(): call frame= $call_frame, package= $package, \n"; 
	 print "\t" . "filename= \"$filename\" \n";
	 print "\t" . "line= $line \t subroutine= $subroutine \n"; 
         print "\t" . "hasargs= $hasargs \t want array= $wantarray \n";
	 print "\t" . "evaltext= $evaltext \t is_require= $is_require \n"; 
      }
      print "\n\n";
   }

   # exit
   exit $main::EXIT_FAILURE;
}}


## ===========================================================================
## Subroutine cas_exit::assert
## ===========================================================================
# Description:
#	panics if the given condition is not true
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   * a condition that should be true
#   * the name of the calling package (which is only printed in debug mode)
#   * a list of strings, that are printed one string per line.  
#	All strings after the first one are indented one tab space.
# 
# Outputs: none
#
# Return Value: PERL_SUCCESS
#
# ============================================================================
sub cas_exit::assert
{{
   my( $condition, @panic_arg_list )= @_;

   if ( $condition ) { return $main::PERL_SUCCESS; }
   &cas_exit::panic ( @panic_arg_list );
}}


## ===========================================================================
## Subroutine cas_exit::check_arg_types
## ===========================================================================
# Description:
#	Intended for checking the number and types of arguments passed into
#	a subroutine.
#
#	If the number and type of arguments does not agree, returns a string
#	containing appropriate error messages.
#
#	If everything agrees, returns a null string.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $calling_pkg: a string containing the name of the calling package
#   $arg_list_ref: a reference to a list containing all the arguments passed
#	to the subroutine.  This list should be identical to the subroutine's 
#	@_ list.
#   @arg_type_list: a list specifying the type of each argument we should find
#	in @$arg_list_ref.  The number of items in this list should match
#	the number of items in @$arg_list_ref.
# 
# Outputs:
#
# Return Value:
#   A null string (on success). or a string containing error messages.
#   The default configuration is to print the error messages and panic 
#   instead of returning.
#
#   In the future, it may be possible to configure this subroutine to return
#   a string containing error messages instead of panicking.
#
# ============================================================================
sub cas_exit::check_arg_types
{{
   my($calling_pkg, $real_arg_list_ref, @desired_arg_type_list )= @_;

   # ------------------------------------------------------------
   # check own arguments
   {
      my ($tt);
      $tt= scalar(@_);
      &cas_exit::assert ( $tt >= 2, $packagename, 
	    "Internal error 244.27oct2000, code= $tt." );

      $tt= ref($real_arg_list_ref);
      &cas_exit::assert ( $tt eq "ARRAY", $packagename,
	    "Internal error 248.27oct2000, code= \"$tt\"." );

      $tt= scalar(@desired_arg_type_list);
      &cas_exit::assert ( $tt >= 0, $packagename,
	    "Internal error 252.27oct2000, code= \"$tt\"." );
   }

   # ------------------------------------------------------------
   # define variables
   my ($real, $desired);

   # ------------------------------------------------------------
   # check the number of arguments
   $real= scalar(@$real_arg_list_ref);
   $desired= scalar( @desired_arg_type_list );

   if ( $real != $desired )  {
      &cas_exit::panic ( $calling_pkg, "Internal error: ",
            "Error type: \"argument count\",", 
            "Code is $real, not $desired. " );
   }

   # ------------------------------------------------------------
   # check the type of each argument
   my ( $arg_num );

   for ( $arg_num= $[ ; $arg_num <= $#desired_arg_type_list; $arg_num++ )  {
      $real= ref( $$real_arg_list_ref[ $arg_num ] );   
      $desired= $desired_arg_type_list[ $arg_num ];

      if ( $real ne $desired )  {
	 &cas_exit::panic ( $calling_pkg, "Internal error: ",
	       "Error type: \"argument type\",", 
	       "Code is \"$real\", not \"$desired\". " );
      }
   }

   # ------------------------------------------------------------
   # clean up and return
   return "";
}}





## ****************************************************************************
## end this package 
package main;

# the package must return 1
1;



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

