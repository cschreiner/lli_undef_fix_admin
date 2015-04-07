#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: instruction.pm
##
## Description: code to generate an instruction.  This is a singleton package.
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## instruction.pm was written by Christian A. Schreiner at University of Utah.  
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

package instruction;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in instruction::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "instruction";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package instruction::private;

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
      $pkgname= "instruction";

      # ----------------------------------------------------------------------
      # other stuff
      our( %opcode_hash )=  (
	    'add' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( nsw nuw ) ],
	       },
	    'sub' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( nsw nuw ) ],
	       },
	    'mul' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( nsw nuw ) ],
	       },
	    'sdiv' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( exact ) ],
	       },
	    'udiv' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( exact ) ],
	       },
	    'srem' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( ) ],
	       },
	    'urem' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( ) ],
	       },
	    'and' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( ) ],
	       },
	    'or' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( ) ],
	       },
	    'xor' => {
	       'type'=> 'arith',
	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( ) ],
	       },
	    'shl' => {
	       'type'=> 'shift',
	       'gen_ftn' => 'generate_shift_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( nsw nuw ) ],
	       },
	    'lshr' => {
	       'type'=> 'shift',
	       'gen_ftn' => 'generate_shift_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( exact ) ],
	       },
	    'ashr' => {
	       'type'=> 'shift',
	       'gen_ftn' => 'generate_shift_inst', # change this to a ftn ptr
	       'flag_listref' => [ qw( exact ) ],
	       },
	    'storeload' => {
	       'type'=> 'storeload',
	       'gen_ftn' => 'generate_storeload_inst', # change this to a ftn ptr
	       'flag_listref' => [ ],
	       },
	    # template: is 4 lines long:
	    #'xxx' => {
	    #	 'type'=> 'arith',
	    #   'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
	    #   'flag_listref' => [ qw( ) ],
	    #	 },
	    );


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

