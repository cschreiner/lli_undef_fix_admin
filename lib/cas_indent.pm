#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: cas_perl_utilities
## Module Name: cas_indent.pm
##
## Description: manages indenting when autogenerating source code
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## Copyright (C) 2013-2013 Christian Schreiner.  All rights reserved. You
## may use, examine, or modify this file only in accordance with the Lesser
## GNU Public License, or, alternately, by special written arrangement with
## the author.  This file comes with no warranties.  If you use it and
## something breaks, you are solely responsible for cleaning up afterwards.
## 
## $Revision: 1.4 $
## $Date: 2013/04/05 14:47:03 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_indent;


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
      $pkgname= "cas_indent";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## start the package


## ===========================================================================
## Subroutine new()
## ===========================================================================
# Description: creates a new indent instance
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_class: class information (provided by perl)
#   $whitespace: a base string, containing the whitespace for one indentation
#	increment.  This is typically 3 spaces, or a tab character, or
#	similar.
# 
# Outputs: none
#   
# Return Value: 
#   a new instance
#
# ============================================================================
sub new
{{
   my( $perl_class, $whitespace )= @_;

   if ( $whitespace eq "" )  {
      warn $main::scriptname . ": indent will be ineffective due to \n" .
	    "zero-length base string. (message 2013apr05_094614) \n";
   }

   my( $perl_this )= {};

   $perl_this->{'whitespace'}= $whitespace;

   # how many increments are we indented?
   $perl_this->{'increment'}= 0; 

   # prefix this string before each line of code
   $perl_this->{'indentation'}= ""; 

   bless $perl_this, $perl_class;
   return $perl_this;
}}


## ===========================================================================
## Subroutine get()
## ===========================================================================
# Description: returns the string to prefix before a line of code
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to use (provided by PERL)
# 
# Outputs: none
#   
# Return Value: see description
#   
# ============================================================================
sub get
{{
   my( $perl_this )= @_;
   return $perl_this->{'indentation'};
}}


## ===========================================================================
## Subroutine add()
## ===========================================================================
# Description: changes the amount of indentation
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to use (provided by PERL)
#   $delta: integer specifying how many indent increments to add to
#      the current indentation.  Negative values reduce the indentation.
#      It is an error of the resulting indentation is less than 0
#      increments.
# 
# Outputs: none
#   
# Return Value: 
#   PERL_SUCCESS or dies if an error happens
#
# ============================================================================
sub add
{{
   my( $perl_this, $delta )= @_;
   $perl_this->{'increment'}+= $delta;
   if ( $perl_this->{'increment'} < 0 )  {
      die $main::scriptname . ": internal error 2013apr02_184538, \n" .
            "\t" . "delta=\"$delta\", code=\"" .
	    $perl_this->{'increment'} . "\". \n";
   }
   # TODO2: see if it would be faster to increase indentation by
   # appending or deleting from the indentation string.
   if ( $main::FALSE )  {
      print "whitespace=\"" . $perl_this->{'whitespace'} . "\", " .
	    "increment=\"" . $perl_this->{'increment'} . "\"\n";
   }
   $perl_this->{'indentation'}= 
	 $perl_this->{'whitespace'} x $perl_this->{'increment'};
   return $main::PERL_SUCCESS;
}}

## ===========================================================================
## Subroutine getIncrement()
## ===========================================================================
# Description: returns the number of indentations in effect
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_this: the instance to work with (provided by perl)
# 
# Outputs: none
#   
# Return Value: an integer
#   
# ============================================================================
sub getIncrement
{{
   my( $perl_this )= @_;
   return $perl_this->{'increment'};
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

