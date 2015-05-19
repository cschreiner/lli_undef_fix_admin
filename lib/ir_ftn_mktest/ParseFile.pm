#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: ir_ftn_mktest::ParseFile.pm
##
## Description: parses an IR file into individual functions
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## ParseFile.pm was written by Christian A. Schreiner at University of Utah.  
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

package ir_ftn_mktest::ParseFile;


## ****************************************************************************
## public package BEGIN and END functions

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
      $pkgname= "ir_ftn_mktest::ParseFile";

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
## Subroutine new()
## ===========================================================================
# Description: creates a new parser instance
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $pkgInfo: information about the package (provided by PERL)
#   $filename: the name of the file to read
# 
# Outputs: 
#   
# Return Value: the new instance
#   
# ============================================================================
sub new
{{
   my( $pkgInfo, $filename )= @_;

   my $this= {};
   $this->{'filename'}= $filename;
   $this->{'lastLineRead'}= undef;
   $this->{'isClosed'}= $main::FALSE;

   $this->{'fh'}= new FileHandle;
   $this->{'fh'}->open( "<$filename" ) or
      die $main::scriptname . ": can't open LLVM IR file for reading, \n" .
            "\t" . "file=\"$filename\", \n" .
            "\t" . "$!. \n";

   return $this;
}}

## ===========================================================================
## Subroutine parseFtn()
## ===========================================================================
# Description: parses the next function out of the file
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on
# 
# Outputs: none
#   
# Return Value: 
#   string containing the function parsed, or 
#   undef if further reads are not possible (e.g. EOF has been reached).
#
# ============================================================================
sub parseFtn
{{
   my( $this )= @_;

   if ( $this->{'isClosed'} )  { return undef; }

   my $ftnTxt= "";
   my $line= undef;

   $ftnTxt.= $this->{'lastLineRead'};

   while ( $line= < $this->{'fh'} > )  {
      if ( $line =~ m/^ \s* define \s+/x )  {
         last;
      }
   }
   if ( $this->{'fh'}->eof )  {
      $this->close();
      return $ftnTxt;
   }

   $ftnTxt.= $line;

   while ( $line= < $this->{'fh'} > )  {
      if ( $line =~ m/^ \s* define \s+/x )  {
         last;
      }
      $ftnTxt.= $line;
   }
   if ( $this->{'fh'}->eof )  {
      $this->close();
      return $ftnTxt;
   }

   $this->{'lastLineRead'}= $line;

   return $ftnTxt;
}}


## ===========================================================================
## Subroutine close()
## ===========================================================================
# Description: closes the file being read
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on (provided by PERL)
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS
#   
# ============================================================================
sub close
{{
   my( $this )= @_;

   $this->{'fh'}->close() or
      die $main::scriptname . ": can't close LLVM IR file after reading, \n" .
            "\t" . "file=\"$filename\", \n" .
            "\t" . "$!. \n";
   $this-{'isClosed'}= $main::TRUE;
   return $main::PERL_SUCCESS;
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

