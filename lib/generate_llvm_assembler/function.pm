#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: function.pm
##
## Description: code to generate function(s)
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## function.pm was written by Christian A. Schreiner at University of Utah.  
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
use Carp qw( cluck confess croak );

use cas_listutil;

use BasicBlockSeq;
use TypeInteger;

## ****************************************************************************
## package identification

package function;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # Note: we check for basic constants in function::private::BEGIN

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "function";

      # ----------------------------------------------------------------------
      # other stuff

   }}

## ****************************************************************************
## private package BEGIN and END functions

package function::private;

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
      $pkgname= "function";

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
## Subroutine generate()
## ===========================================================================
# Description: generates a generic function.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   ret_type: the function's return type (expressed as a TypeInteger instance)
#   name: the function's name (expressed as a string)
#   arg_listref: a reference to a list of TypeInteger instances giving the 
#	function's argument types

#   opt_hashref: a reference to a hash giving various options.  Valid options 
#	are:
#	numSteps (unsigned): the number of steps (instructions) the function
#		should have.  This is required.
#	printRetVal (boolean): add code to print the return value
#	startPoison (boolean): the function should generate a poison value
#		as one of its first instructions
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the new function generated
sub function::generate
{{
   my( $ret_type, $name, $arg_listref, $opt_hashref )= @_;

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # set default values for options as needed
   if ( !exists($opt_hashref->{'numSteps'}) )  {
      die $main::scriptname . ": internal error 2015apr09_122418. \n";
   }
   if ( !exists($opt_hashref->{'printRetVal'}) )  {
      $$opt_hashref{'printRetVal'}= $main::FALSE;
   }
   if ( !exists($opt_hashref->{'startPoison'}) )  {
      $$opt_hashref{'startPoison'}= $main::FALSE;
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # check arguments

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # set up and report arguments

   my $start_type= $ret_type;
   if ( scalar(@$arg_listref) >= 1 )  {
      # if there are arguments, start with their type
      $start_type= $$arg_listref[ $[ ];
   }
   print "creating a basic block for function \"$name\"\n";;
   my( $basicBlock )= new BasicBlockSeq( undef, 
	 { 'startPoison'=> $opt_hashref->{'startPoison'},
	   'numSteps' => $opt_hashref->{'numSteps'},
           'startType' => $start_type,
           'stopType' => $ret_type,
           'ftnName' => $name, #;;
	 } );

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # generate function heading
   my ( $definitions, $instructions );

   $instructions.= "define " . $ret_type->getName() . 
	 ' ' . $name . '( '; 

   print "arg_listref=>[" . cas_listutil::quote2($arg_listref) . "] \n" .
	 "\t" . "length=" . scalar(@$arg_listref) . "\n";;
   for ( my $ii= $[; $ii < scalar(@$arg_listref); $ii++ )  {
      my $argName= "%arg" . ($ii+1);
      print "   $ii\n";;
      $instructions.= $$arg_listref[$ii]->getName() . " " . $argName;
      $basicBlock->registerArg( $argName, $$arg_listref[$ii] );
      if ( $ii < $#$arg_listref )  {
	 $instructions.= ", ";
      }
   }

   $instructions.= " ) { \n"; 
   $instructions.= "  ; \%convert [? x i8]* to i8* \n";
   $instructions.= 
	 "  \%printf_st_i8 = getelementptr [37 x i8]* \@printf_st, " . 
	 "i64 0, i64 0\n";
   $instructions.= "\n";

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # generate instructions
   {
      my ($defs, $insts )= $basicBlock->generate();
      if ( $insts =~ m/%1\D.*%1\D/ )  {
	 confess( $main::scriptname . ": internal error 2015apr10_101817 " .
	       "(two %1s in insts)" );;
      }
      $definitions.= $defs;
      $instructions.= $insts;
   }
   if ( $definitions =~ m/%1\D.*%1\D/ )  {
      confess( $main::scriptname . ": internal error 2015apr10_101506 " .
	    "(two %1s in definitions)" );;
   }
   if ( $instructions =~ m/%1\D.*%1\D/ )  {
      confess( $main::scriptname . ": internal error 2015apr10_100654 " .
	    "(two %1s in instructions)" );;
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # generate function ending

   $instructions.= "\n";

   if ( $opt_hashref->{'printRetVal'} )  {
      $instructions.= "  call i32 (i8*, ...)* \@printf(i8* \%printf_st_i8, " .
	    $basicBlock->currentType()->getName() . ' ' . 
	    $basicBlock->getPrevRegName() . ")\n";
   }

   $instructions.= "\n";
   $instructions.= "  ; clean up and return \n";
   my $retRegister= $basicBlock->getPrevRegName(1);
   my $retRegisterType= $basicBlock->getRegType($retRegister);
   if ( $basicBlock->getStopType()->compareTo($retRegisterType) != 0 )  {
      # The return value should be of the intended return type.
      die $main::scriptname . ": internal error 2015apr09_235615.\n";
   }

   $instructions.= "  ret " . $retRegisterType . ' ' . $retRegister . " \n";
   $instructions.= "} \n";

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # clean up and return
   return ( $definitions, $instructions );
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

