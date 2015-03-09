#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: cas_perl_utilities
## Module Name: cas_filesplit.pm
##
## Description: 
#	Class for dividing an output stream into a number of files, much
#	as GNU's split program does.  For example, write n lines to a file,
#	then that many lines to the next file, and so forth.
#
#  Desired Improvements:
#
#  * find a way to automatically call ->close() when this instance is going
#	out of scope.
#  * consider adding ability to optionally measure split points in units 
#	other than lines (e.g. maybe bytes or characters)  See GNU split(1).
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
## $Revision: 1.25 $
## $Date: 2012/10/17 19:17:27 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_filesplit;


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

      use FileHandle;

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "cas_filesplit";

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
# Description: creates a new instance
#
# Method: 
#
# Notes: 
#   Desired Feature: ability to ignore a % in a preceeding directory name,
#	in case external circumstances require use of a directory with name
#   	containing a % that shouldn't be replaced with a sequence designation.
#
# Warnings: 
#
# Inputs: 
#   $perl_class: class information (provided by PERL)
#   $filename_pattern: the pattern for the names of the file(s) to be
#	generated.  The pattern may include the character %, which will be
#	replaced with an alphanumeric designation indicating the sequence of
#	each file.  If not specified, the designation will be suffixed onto
#	the filename.  Note that a % in the preceeding directory name will
#	also be replaced.
# 
# Outputs: none
#   
# Return Value: 
#   a new instance
#
# ============================================================================
sub new
{{
   my( $perl_class, $filename_pattern )= @_;

   my( $perl_this )= {};
   bless $perl_this, $perl_class;

   if ( $filename_pattern =~ m/%/ )  {
      $perl_this->{'filename_pattern'}= $filename_pattern;
   } else {
      # User didn't specify where to put the sequence designation, so we
      # put it in the default location on the end.
      $perl_this->{'filename_pattern'}= $filename_pattern . '%';
   } 

   # set up the usual variables
   $perl_this->{'max_lines_per_file'}= 60000;
   $perl_this->{'num_lines_this_file'}= 0;
   $perl_this->{'file_num'}= 0;
   $perl_this->{'current_filename'}= undef;
   $perl_this->{'current_fh'}= undef;
   $perl_this->{'header'}= "";
   $perl_this->{'num_header_lines'}= 0;

   return $perl_this;
}}

## ===========================================================================
## Subroutine close()
## ===========================================================================
# Description: 
#	closes a stream, which closes the file currently being written to.
#	Further calls to print() will open the next file in the sequence.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to act on (provided by PERL)
# 
# Outputs: none
#   
# Return Value: 
#   PERL_SUCCESS
#
# ============================================================================
sub close
{{
   my( $perl_this )= @_;

   if ( ! defined( $perl_this->{'current_fh'} ) )  {
      # we don't have a file open at the moment
      return $main::PERL_SUCCESS;
   }

   $perl_this->{'current_fh'}->close() or
      die $main::scriptname . ": can't close file after writing, \n" .
	    "\t" . 
	    "filename= \"" . $perl_this->{'current_filename'} . "\", \n" .
	    "\t" . "$!. \n";
   $perl_this->{'file_num'}++;
   $perl_this->{'current_fh'}= undef;

   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine print()
## ===========================================================================
# Description: print a string to the output
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to use for printing
#   $st: the string to print
# 
# Outputs: none
#   
# Return Value: 
#   PERL_SUCCESS
#
# ============================================================================
sub print
{{
   my( $perl_this, $st )= @_;

   # make one chunk per line
   my( @chunk_list )= $perl_this->_splitIntoLines( $st );

   my( $line_st )= "";
   foreach my $chunk ( @chunk_list )  {
      if ( $chunk eq "" )  { next; }
      $perl_this->_printOneLine( $st );
   }

   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine _printOneLine()
## ===========================================================================
# Description: prints one line to the output.  
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to use for printing
#   $st: the string to print, which must be only one line long (including
#	end-of-line characters).  If the text does not end in a new line
#	it does not count as a new line.
#   
# Outputs: none
#   
# Return Value: 
#   PERL_SUCCESS
#
# ============================================================================
sub _printOneLine
{{
   my( $perl_this, $st )= @_;

   # make sure a file is open and ready for writing
   if ( !defined( $perl_this->{'current_fh'} )  )  {
      $perl_this->{'current_filename'}= $perl_this->_generateFilename();
      $perl_this->{'current_fh'}= new FileHandle;
      $perl_this->{'current_fh'}->open( 
	    ">" . $perl_this->{'current_filename'} ) or 
	 die $main::scriptname . ": can't open file for writing, \n" .
               "\t" . 
	       "filename= \"" . $perl_this->{'current_filename'} . "\", \n" .
               "\t" . "$!. \n";
      $perl_this->{'current_fh'}->print( $perl_this->{'header'} );
      $perl_this->{'num_lines_this_file'}= $perl_this->{'num_header_lines'};
   }

   # write the line, and do related bookkeeping
   $perl_this->{'current_fh'}->print( $st );
   if ( $st !~ m/(\r\n|\r|\n)$/ )  {
      # no bookkeeping necessary if this doesn't complete a line
      return $main::PERL_SUCCESS;
   }
   $perl_this->{'num_lines_this_file'}++;
   if ( $perl_this->{'num_lines_this_file'} < 
	 $perl_this->{'max_lines_per_file'} )  {
      # if this file isn't full, we're done
      return $main::PERL_SUCCESS;
   }

   # file is full, so close it out
   $perl_this->close();

   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine setMaxLinesPerFile()
## ===========================================================================
# Description: sets the maximum number of lines allowed per file
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to work on
#   $num_lines: set the limit to this value.  Nonpositive values will cause
#	strange behavior.
# 
# Outputs: none
#   
# Return Value: 
#   PERL_SUCCESS
#
# ============================================================================
sub setMaxLinesPerFile
{{
   my( $perl_this, $num_lines )= @_;
   $perl_this->{'max_lines_per_file'}= $num_lines;
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine setHeader()
## ===========================================================================
# Description: 
#	sets the header that will be inserted at the beginning of each file
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to work with 
#   $st: the string containing the header text (may be 1 or more lines).  For
#	no header, specify a null string.
# 
# Outputs: none
#   
# Return Value: 
#   PERL_SUCCESS
#
# ============================================================================
sub setHeader
{{
   my( $perl_this, $st )= @_;
   $perl_this->{'num_header_lines'}= 
	 scalar( $perl_this->_splitIntoLines( $st ) );
   $perl_this->{'header'}= $st;
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine _generateFilename()
## ===========================================================================
# Description: generates the name of a file to write to, based on the current
#	file number
#
# Method: 
#
# Notes: 
#
# Warnings: 
#   Output is NOT written to $perl_this->{'current_filename'}.  The caller
#	must do that him/herself.
#
# Inputs: 
#   $perl_this: the instance for which to generate the filename
# 
# Outputs: 
#   
# Return Value: 
#   the new filename
#
# ============================================================================
sub _generateFilename
{{
   my( $perl_this )= @_;

   use constant DIGIT_LIST => qw( 
         a b c d e f g h i j k l m n o p q r s t u v w x y z );
   use constant NUM_DIGITS => scalar( DIGIT_LIST );
   use constant MIN_NUM_DIGITS => 2;

   if ( $perl_this->{'file_num'} == 0 )  {
   }

   # compute the sequence, e.g. aa, ab, ac...
   my( $sequence_st )= "";
   {
      my( $xx )= $perl_this->{'file_num'};
      while ( $xx > 0 )  {
	 my( $remainder )= $xx % NUM_DIGITS;
	 my( $quotient )= int( $xx / NUM_DIGITS );
	 $sequence_st= (DIGIT_LIST)[$remainder] . $sequence_st;
	 $xx= $quotient;
      }
      if ( length($sequence_st) < MIN_NUM_DIGITS )  {
         $sequence_st= (DIGIT_LIST)[$[] x 
	       ( MIN_NUM_DIGITS-length($sequence_st)) .
               $sequence_st;
      }
   }
   
   # generate the filename for the sequence
   my( $filename )= $perl_this->{'filename_pattern'};
   $filename=~ s/%/$sequence_st/;

   # clean up and return
   return $filename;
}}


## ===========================================================================
## Subroutine _splitIntoLines()
## ===========================================================================
# Description: splits a string into lines
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to work with
#   $st: the string to split
# 
# Outputs: none
#   
# Return Value: 
#   a list of strings, each containing one line with its EOL character(s)
#
# ============================================================================
sub _splitIntoLines
{{
   my( $perl_this, $st )= @_;

   # splitting on /^/ is a bit of a special split() behavior, but it is
   # exactly what we need here..
   my( @chunk_list )= split( /^/, $st );

   return @chunk_list;
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

