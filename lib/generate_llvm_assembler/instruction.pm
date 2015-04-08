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
      use vars qw ( %opcode_hash );
      %opcode_hash= (
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
## Subroutine generate_one_inst()
## ===========================================================================
# Description: generates one assembler instruction, chosen at random
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $basicBlock: a BasicBlockSeq instance with context information
#	for the instruction.
# 
# Outputs: 
#   
#
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub instruction::generate_one_inst
{{
   my( $basicBlock )= @_;

   my( $opcode );
   {
      my( @opcode_list )= keys %opcode_hash;
      $opcode= $opcode_list[ int( rand() * scalar(@opcode_list) ) ];
      if ( $main::debug_flag )  {
	print "selected opcode=\"$opcode\"\n";
      }
   }

   my( $pre_def, $inst );
   if ( $opcode_hash{$opcode}->{'type'} eq 'arith' )  {
      ( $pre_def, $inst )= instruction::generate_arith_inst( 
	    $basicBlock, $opcode );
   } elsif ( $opcode_hash{$opcode}->{'type'} eq 'shift' )  {
      ( $pre_def, $inst )= instruction::generate_shift_inst( 
	    $basicBlock, $opcode );
   } elsif ( $opcode_hash{$opcode}->{'type'} eq 'storeload' )  {
      ( $pre_def, $inst )= instruction::generate_storeload_insts( 
	    $basicBlock, $opcode );
   } else {
      die $main::scriptname . 
	    ": don't recognize opcode type for \"$opcode\", \"" . 
	    $opcode_hash{$opcode}->{'type'} . "\"\n";
   }

   return ( $pre_def, $inst );
}}


## ===========================================================================
# Subroutine generate_storeload_insts()
# ============================================================================
# Description: generates a store instruction, immediately followed by a load
#	instruction.  The idea is to make sure every value gets stored also
#	gets loaded later in the program.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $basicBlock: a BasicBlockSeq instance with context information
#	for the instruction.
#   $opcode: the opcode to generate
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub instruction::generate_storeload_insts
{{
   my( $basicBlock, $opcode )= @_;

   # TODO2: have a package similar to reg_context that generates these names
   # and guarantees that the same name is not used twice.  Maybe make the
   # names have form consonant-vowel-consonant_consonant-vowel-consonant to
   # make them pronouncible.
   my( $addr_name )= '@addr_';
   for ( my $ii= 0; $ii < 4; $ii++ )  {
      # This assumes that the character encoding has the lower-case codepoints
      # be consecutive.
      $addr_name.= chr( ord('a')+ int(26*rand()) );
   }

   my( $dest_reg )= reg_context::getName();
   my( $src_reg )= reg_context::getPrevName(1);

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
   my( $pre_func )= $addr_name . " = global " . 
	 $basicBlock->regWidth()->getName() . 
	 " " . $basicBlock->regWidth()->getRandVal() . "\n";

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
   my( $store_flags )= " ";
   if ( rand() < .5 )  {
      $store_flags= "volatile ";
   }
   # recall that flags strings must always end in a space

   my( $inst );
   $inst.= "  " . "store " . 
         $store_flags . $basicBlock->regWidth()->getName() . ' ' . 
	 $src_reg . ', ' . 
         $basicBlock->regWidth()->getName() . '* ' . $addr_name . "\n";
   $inst.= "  " . $dest_reg . "= load " . $basicBlock->regWidth()->getName() . 
	 "* $addr_name \n";
   # TODO2: consider adding an 'align 4' or similar to the load and 
   # store instructions.

   return ( $pre_func, $inst );
}}

## ===========================================================================
## Subroutine generate_shift_inst()
## ===========================================================================
# Description: generates a shift instruction.  Shift instructions 
#	use a register value for one argument, and randomly generated
#	constant for the other.  This guards against the constant
#	being larger than the number of bits in the register.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $basicBlock: a BasicBlockSeq instance with context information
#	for the instruction.
#   $opcode: the opcode to generate
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub instruction::generate_shift_inst
{{
   my( $basicBlock, $opcode )= @_;

   my( $dest_reg )= reg_context::getName();

   my( $flags )= "";
   my( $flag_listref )= $opcode_hash{$opcode}->{'flag_listref'};
   for ( my $ii= 0; $ii < scalar(@$flag_listref); $ii++ )  {
      if ( rand() < .5 )  {
	 # a flag must ALWAYS end in a space. 
         $flags.= $$flag_listref[$ii] . " ";
      }
   } 

   my( $operand1 )= reg_context::getPrevName(1);

   my( $operand2 );
   # operand is a constant
   $operand2= $basicBlock->regWidth()->getRandShiftVal();
   
   my( $inst )= "  " . $dest_reg . "= " . $opcode . ' ' . 
	 $flags . $basicBlock->regWidth()->getName() . ' ' .
	 $operand1 . ', ' . $operand2 . "\n";

   return ("", $inst );
}}

## ===========================================================================
## Subroutine generate_arith_inst()
## ===========================================================================
# Description: generates an arithmetic instruction.  Arithmetic instructions 
#	use a register value for one argument, and either a register or a 
#	literal value for the other.  These arguments can be in either order. 
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $basicBlock: a BasicBlockSeq instance with context information
#	for the instruction.
#   $opcode: the opcode to generate
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub instruction::generate_arith_inst
{{
   my( $basicBlock, $opcode )= @_;

   my( $dest_reg )= reg_context::getName();

   my( $flags )= "";
   my( $flag_listref )= $opcode_hash{$opcode}->{'flag_listref'};
   for ( my $ii= 0; $ii < scalar(@$flag_listref); $ii++ )  {
      if ( rand() < .5 )  {

         $flags.= $$flag_listref[$ii] . " ";
      }
   } 

   my( $operand1 )= reg_context::getPrevName(1);

   my( $operand2 );
   if ( rand() < .5 )  {
      # operand is a constant
      $operand2= $basicBlock->regWidth()->getRandVal();
   } else {
      $operand2= reg_context::getRecentName();
   }
   
   # maybe swap operands
   if ( rand() < .5 )  {
      my( $tmp )= $operand1;
      $operand1= $operand2;
      $operand2= $tmp;
   }

   my( $inst )= "  " . $dest_reg . "= " . $opcode . ' ' . 
	 $flags . $basicBlock->regWidth()->getName() . ' ' .
	 $operand1 . ', ' . $operand2 . "\n";

   return ("", $inst );
}}

## ===========================================================================
## Subroutine generate_const_inst()
## ===========================================================================
# Description: generates a constant-assignment instruction.  A constant 
#	assignment instruction assigns a constant to a register. 
#
# Method: This has to be implemented by adding 0 to a constant.  Sheesh.
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $basicBlock: a BasicBlockSeq instance with context information
#	for the instruction.
#   
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#   
# ============================================================================
sub instruction::generate_const_inst
{{
   my( $basicBlock )= @_;

   my( $inst )= "  " . 
	 reg_context::getName(). "= add ". $basicBlock->regWidth()->getName(). 
	 ' ' . 
         $basicBlock->regWidth()->getRandVal() . ", 0 \n";
   return ("", $inst );
}}


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

