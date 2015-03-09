#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_tmpfile
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description:
#	generates names for temporary files.  Automatically deletes the
#	temporary files (if they still exist) when the program exists.
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.3 $
## $Date: 2007/01/12 04:36:53 $
## Author: $
##
## ****************************************************************************

# features to add: 
#
# * add a public function that allows the caller to place a file of its own
#	choosing into the temporary file list (so that file will be
#	automatically deleted when the program ends).  Should we require
#	that this file have an absolute pathname, or should we use the Cwd
#	functions to generate the absolute pathname ourselves?
#
# * add a public feature to let the caller find out which temporary directory
#	this module decided to use.
#
# * add a public feature to let the caller  specify that some temporary
#	files should not be deleted...? Or is it better to simply let the
#	caller copy/rename such files, and we simply notice that those
#	temporary files do not exist when we clean up.



## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_tmpfile;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in cas_tmpfile::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_tmpfile";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package cas_tmpfile::private;

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

      require cas_exit;
      #require cas_listutil;  # only needed for debugging

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_tmpfile";

      # ----------------------------------------------------------------------
      # other stuff

      # ..............................
      # the directory where all temporary files will be placed 
      use vars qw ( $tmpdir );
      #$tmpdir= get_tmpdir();
      # set tmpdir via this "subroutine"
      {
	 my ( $last_chance ) = $main::FALSE;
	 my ( @possibility_list );

	 if ( defined($ENV{TMPDIR}) ) {
	    push @possibility_list, $ENV{TMPDIR};
	 } elsif ( defined($ENV{TMP}) )  {
	    push @possibility_list, $ENV{TMP};
	 } elsif ( defined($ENV{TEMP}) ) {
	    push @possibility_list, $ENV{TEMP};
	 }
	 push @possibility_list, ( '/usr/tmp', '/tmp' );

	 my ( $dir );
	 foreach $dir ( @possibility_list )  {
	    if ( -d $dir && -r $dir && -w $dir && -x $dir )  {
	       # we found a good dir for temporary files
	       $tmpdir= $dir;
               goto FOUND_GOOD_TMPDIR;
	    }
            if ( $main::debug_flag && $main::FALSE )  {
               my ($H)= " \t";

	       print "Tried dir= $dir \n";
	       print $H. '-d: ' . (( -d $dir ) ? $main::TRUE : $main::FALSE );
	       print $H. '-r: ' . (( -r $dir ) ? $main::TRUE : $main::FALSE );
	       print $H. '-w: ' . (( -w $dir ) ? $main::TRUE : $main::FALSE );
	       print $H. '-x: ' . (( -x $dir ) ? $main::TRUE : $main::FALSE );
	       print "\n";
            }
	 }

	 &cas_exit::panic ( $pkgname, 
	       "Can't find a directory for temporary files.",  
	       "Is your TMPDIR environment variable set properly?", 
	       "Tried these possibilities: ", 
	       "", 
	       @possibility_list, 
	       "", 
	       "Check your TMPDIR environment variable or see your system " . 
	       "administrator." );

         FOUND_GOOD_TMPDIR:

      }

      # ..............................
      # a list of temporary files, all relative to the directory $tmpdir
      use vars qw ( @tmpfile_list );

      # ..............................
      # an integer number, used to guarantee that each filename is unique
      use vars qw ( $unique_code );
      $unique_code= 0;

      # ..............................
      # the prefix to use in the file names
      use vars qw ( $prefix );
      {
         my ( $timestamp ) = `date +%Y%b%d.%H%M%S`;
         chomp $timestamp;

         my ( $tmp_scriptname ) = $main::scriptname;
         # eliminate characters that don't go well in filenames
         $tmp_scriptname=~ tr/A-Za-z0-9_//cd;
         #tr/ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789//

         $prefix= $tmpdir . '/' . 
	       $tmp_scriptname . "." . $timestamp . "." . $$ . ".";
      }

   }}

   # =========================================================================
   # subroutine END
   # =========================================================================
   sub END
   {{
      if ( $main::debug_flag )  {
	 print "$main::scriptname package $pkgname: \n" . 
	       "\t" . "tmpfile_list= (" . 
	       &cas_listutil::quote2( \@tmpfile_list ) . 
	       ") \n";;
      }

      # delete all the temporary files that still exist
      my ( $file, $tt );
      my ( @problem_list );
      foreach $file ( @tmpfile_list ) {
	 if ( -e $file )  {
            $tt= unlink ( $file );
            if ( $tt != 1 )  {
               push @problem_list, $file;
            }
         }
      }

      # gripe if we couldn't remove some files
      if ( scalar(@problem_list) > 0 )  {
	 warn "$main::scriptname package $pkgname: \n" .
	       "\t" . "Could not remove the following temporary files: \n" .
	       "\t\t\"" . join ( "\"\n\t\t\"", @problem_list ) . "\"\n\n";
      }
      
      return $main::PERL_SUCCESS;
   }}

## ****************************************************************************
## start the package


## ===========================================================================
## Subroutine get_tmpdir()
## ===========================================================================
# Description: finds a directory to put temporary files in
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs: none
# 
# Outputs: none
#
# Return Value: the full pathname to the directory
#
# ============================================================================
sub get_tmpdir
{{
   #my( )= @_;
   my ( $last_chance ) = $main::FALSE;
   my ( @possibility_list );

   if ( defined($ENV{TMPDIR}) ) {
      push @possibility_list, $ENV{TMPDIR};
   } elsif ( defined($ENV{TMP}) )  {
      push @possibility_list, $ENV{TMP};
   } elsif ( defined($ENV{TEMP}) ) {
      push @possibility_list, $ENV{TEMP};
   }
   push @possibility_list, ( '/usr/tmp', '/tmp' );

   my ( $dir );
   foreach $dir ( @possibility_list )  {
      if ( -d $dir && -r $dir && -w $dir && -x $dir )  {
         # we found a good dir for temporary files
         return $dir;
      }
   }

   &cas_exit::panic ( $pkgname, 
	 "Can't find a directory for temporary files.",  
	 "Is your TMPDIR environment variable set properly?", 
         "Tried these possibilities: ", 
	 "", 
	 @possibility_list, 
	 "", 
         "Check your TMPDIR environment variable or see your system " . 
	 "administrator." );

}}


## ===========================================================================
## Subroutine cas_tmpfile::get_tmpfile_name()
## ===========================================================================
# Description: returns a full pathname to a temporary file.  This file
#	does not exist, but should be able to be created.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs: none
# 
# Outputs: none
#
# Return Value: the full pathname to the file
#
# ============================================================================
sub cas_tmpfile::get_tmpfile_name
{{
   #my( )= @_;

   my ( $try )= $prefix . $unique_code;
   $unique_code++; 
   while ( -e $try )  {
      $try= $prefix . $unique_code;
      $unique_code++; 
   }

   push @tmpfile_list, $try;
   return $try;
}}


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

