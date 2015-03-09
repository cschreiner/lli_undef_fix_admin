#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_testutil.pm
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description: 
#	Contains various utilities useful for testing Perl scripts (and some
#	other programs, too)
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.5 $
## $Date: 2007/01/12 04:36:53 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_testutil;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in cas_testutil::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_testutil";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package cas_testutil::private;

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
      $pkgname= "cas_testutil";

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
## Subroutine cas_testutil::print_text_files()
## ===========================================================================
# Description:
#   Prints all specified files with visual separation between them.
#
# Method:
#
# Notes:
#   CAS todo: add a generic print_files() function that acts like this one
#	for text files, but simply prints a 64 bit checksum for binary files.
#	Or maybe bit-64 encode the binary file somehow.  Hmmm...  How would
#	we distinguish between a text file and a binary one?  Hmmm...
#
# Warnings:
#
# Inputs:
#   @file_list: a list of names of files to print
# 
# Outputs:
#   
#
# Return Value:
#   PERL_SUCCESS if all files were printed 
#   PERL_FAILURE if one or more files could not be printed 
#
# ============================================================================
sub cas_testutil::print_text_files
{{
   my(@file_list )= @_;

   my( $result );
   my( $ret_val )= $main::PERL_SUCCESS;


   my( $file, $line );
   my( $filehandle ) = new FileHandle;
   my( $ret_val )= $main::PERL_SUCCESS;

   my( $separator_line )= "#" x 78 . "\n";

   foreach $file ( @file_list )  {
      print "\n" . $separator_line . $separator_line . "\n";
      print "File \"$file\": \n";

      if ( ! -f $file )  {
         print "file doesn't exist \n";
         $ret_val= $main::PERL_FAILURE;
         next;
      }
      if ( ! -r $file )  {
         print "can not read file \n";
         $ret_val= $main::PERL_FAILURE;
         next;
      }

      $result= $filehandle->open ( "< $file" );
      if ( $result != $main::PERL_SUCCESS )  {
         print "Could not open file for reading, \n" . 
               "\t" . "file= \"$file\", \n" .
               "\t" . "$! \n";
         $ret_val= $main::PERL_FAILURE;
         next;
      }

      print "file size= " . ( -s $file ) . " \n";
      print "\n";

      while ( $line= <$filehandle> )  {
         print $line;
      }

      $result= $filehandle->close(); 
      if ( $result != $main::PERL_SUCCESS )  {
         print "Could not close file after reading, \n" . 
               "\t" . "file= \"$file\", \n" .
               "\t" . "$! \n";
         $ret_val= $main::PERL_FAILURE;
         next;
      }

      print "\n" . $separator_line . $separator_line . "\n";

   } #foreach $file

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

