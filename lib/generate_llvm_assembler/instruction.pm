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
use Carp qw( cluck confess croak );

use addr_name;
use function;

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
#;;	    'mul' => {
#;;	       'type'=> 'arith',
#;;	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
#;;	       'flag_listref' => [ qw( nsw nuw ) ],
#;;	       },
#;;	    'sdiv' => {
#;;	       'type'=> 'arith',
#;;	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
#;;	       'flag_listref' => [ qw( exact ) ],
#;;	       },
#;;	    'udiv' => {
#;;	       'type'=> 'arith',
#;;	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
#;;	       'flag_listref' => [ qw( exact ) ],
#;;	       },
#;;	    'srem' => {
#;;	       'type'=> 'arith',
#;;	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
#;;	       'flag_listref' => [ qw( ) ],
#;;	       },
#;;	    'urem' => {
#;;	       'type'=> 'arith',
#;;	       'gen_ftn' => 'generate_arith_inst', # change this to a ftn ptr
#;;	       'flag_listref' => [ qw( ) ],
#;;	       },
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
	    'call' => {
	       'type'=> 'call',
	       'gen_ftn' => 'generate_call_inst', # change this to a ftn ptr
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

   if ( $basicBlock->{'regNum'}== 1 )  {;;
      Carp::confess ( "   regNum=1\n" );;
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
   } elsif ( $opcode_hash{$opcode}->{'type'} eq 'call' )  {
      ( $pre_def, $inst )= instruction::generate_call_inst( 
	    $basicBlock, $opcode );
   } else {
      die $main::scriptname . 
	    ": don't recognize opcode type for \"$opcode\", \"" . 
	    $opcode_hash{$opcode}->{'type'} . "\"\n";
   }

   if ( $basicBlock->{'regNum'}== 1 )  {;;
      print "last instruction=\"$opcode\"\n";;
      Carp::confess ( "   regNum=1\n" );;
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

   # TODO2: have a package similar to RegContext that generates these names
   # and guarantees that the same name is not used twice.  Maybe make the
   # names have form consonant-vowel-consonant_consonant-vowel-consonant to
   # make them pronouncible.
   my( $addr_name )= addr_name::get('var');

   my( $dest_reg )= $basicBlock->getRegName();
   my( $src_reg )= $basicBlock->getPrevRegName(1);

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
   my( $pre_func )= $addr_name . " = global " . 
	 $basicBlock->currentType()->getName() . 
	 " " . $basicBlock->currentType()->getRandVal() . "\n";

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
   my( $store_flags )= " ";
   if ( rand() < .5 )  {
      $store_flags= "volatile ";
   }
   # recall that flags strings must always end in a space

   my( $inst );
   $inst.= "  " . "store " . 
         $store_flags . $basicBlock->currentType()->getName() . ' ' . 
	 $src_reg . ', ' . 
         $basicBlock->currentType()->getName() . '* ' . $addr_name . "\n";
   $inst.= "  " . $dest_reg . "= load " . 
	 $basicBlock->currentType()->getName() . 
	 "* $addr_name \n";
   # TODO2: consider adding an 'align 4' or similar to the load and 
   # store instructions.

   $basicBlock->reportType( $dest_reg, $basicBlock->currentType() );
   if ( $basicBlock->{'regNum'}== 1 )  {;;
      Carp::confess ( "   regNum=1\n" );;
   }
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

   my( $dest_reg )= $basicBlock->getRegName();

   my( $flags )= "";
   my( $flag_listref )= $opcode_hash{$opcode}->{'flag_listref'};
   for ( my $ii= 0; $ii < scalar(@$flag_listref); $ii++ )  {
      if ( rand() < .5 )  {
	 # a flag must ALWAYS end in a space. 
         $flags.= $$flag_listref[$ii] . " ";
      }
   } 

   my( $operand1 )= $basicBlock->getPrevRegName(1);

   my( $operand2 );
   # operand is a constant
   $operand2= $basicBlock->currentType()->getRandShiftVal();
   
   my( $inst )= "  " . $dest_reg . "= " . $opcode . ' ' . 
	 $flags . $basicBlock->currentType()->getName() . ' ' .
	 $operand1 . ', ' . $operand2 . "\n";

   $basicBlock->reportType( $dest_reg, $basicBlock->currentType() );
   if ( $basicBlock->{'regNum'}== 1 )  {;;
      Carp::confess ( "   regNum=1\n" );;
   }
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

   my( $dest_reg )= $basicBlock->getRegName();

   my( $flags )= "";
   my( $flag_listref )= $opcode_hash{$opcode}->{'flag_listref'};
   for ( my $ii= 0; $ii < scalar(@$flag_listref); $ii++ )  {
      if ( rand() < .5 )  {

         $flags.= $$flag_listref[$ii] . " ";
      }
   } 

   my( $operand1 )= $basicBlock->getPrevRegName(1);

   my( $operand2 );
   if ( rand() < .5 )  {
      # operand is a constant
      $operand2= $basicBlock->currentType()->getRandVal();
   } else {
      $operand2= $basicBlock->getRecentRegName();
   }
   
   # maybe swap operands
   if ( rand() < .5 )  {
      my( $tmp )= $operand1;
      $operand1= $operand2;
      $operand2= $tmp;
   }

   my( $inst )= "  " . $dest_reg . "= " . $opcode . ' ' . 
	 $flags . $basicBlock->currentType()->getName() . ' ' .
	 $operand1 . ', ' . $operand2 . "\n";

   $basicBlock->reportType( $dest_reg, $basicBlock->currentType() );
   if ( $basicBlock->{'regNum'}== 1 )  {;;
      Carp::confess ( "   regNum=1\n" );;
   }
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

   my $regName= $basicBlock->getRegName();
   my $inst= "  " . 
	 $regName. "= add ". 
	 $basicBlock->currentType()->getName(). ' ' . 
         $basicBlock->currentType()->getRandVal() . ", 0 \n";
   $basicBlock->reportType( $regName, $basicBlock->currentType() );
   if ( $basicBlock->{'regNum'}== 1 )  {;;
      Carp::confess ( "   regNum=1\n" );;
   }
   return ("", $inst );
}}

## ===========================================================================
## Subroutine RegWithType_init()
## ===========================================================================
# Description: initializes a "RegWithType" record
#
# Method: 
#   A "RegWithType" is a simple hash with two fields:
#	register: the name of the register
#	type: a TypeInteger giving the register's data type
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $name: a string holding the register's name
#   $type: a TypeInteger instance giving the register's type
# 
# Outputs: none
#   
# Return Value: a reference to the hash
#   
# ============================================================================
sub RegWithType_init
{{
   my( $name, $type )= @_;

   my $ret_val= { 'register'=> $name, 'type'=>$type };
   if ( $ret_val->{'register'} eq "" )  {;;
      warn $main::scriptname . ": found a null register at 2015apr9_194003.\n";;
   }
   if ( $ret_val->{'type'} eq "" )  {;;
      warn $main::scriptname . ": found a null type at 2015apr9_190554.\n";;
   }
   if ( !defined($ret_val->{'type'}) )  {
      warn $main::scriptname . ": internal warning 2015apr09_170526\n";
   }
   return $ret_val;
}}


## ===========================================================================
## Subroutine instruction::generate_call_inst()
## ===========================================================================
# Description: generates a call instruction, and a function for it to call
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
sub instruction::generate_call_inst
{{
   my( $basicBlock, $opcode )= @_;

   if ( $opcode ne 'call' )  {
      die $main::scriptname . 
	    ": internal error 2015apr9_162208, code=\"$opcode\". \n";
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # set up everything but the arguments
   my $ftnName= addr_name::get("ftn"); # TODO: finish fixing this
   my $retType= $basicBlock->currentType();

   my $numSteps= $basicBlock->numRemainingSteps()/ 3;
   if ( $numSteps < 2 )  {
      $numSteps= 2;
      # TODO: consider returning a NO_OP in this case, and make the caller
      # then choose a different instruction.
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # set up arguments
   use constant MAX_NUM_ARGS => 3;

   # should yield something in range 0...MAX_NUM_ARGS.
   my $numArgs= int( rand()* (MAX_NUM_ARGS+1) );  

   my @allAboutArgList= (); 
   my @argTypeList= (); 
   if ( $numArgs >= 1 )  {
      my $name= $basicBlock->getPrevRegName(1);
      my $allAboutArgHashref= RegWithType_init( 
            $name,
            $basicBlock->getRegType($name) );
      push @allAboutArgList, $allAboutArgHashref;
   }
   for ( my $ii= ($[+1); $ii < $numArgs; $ii++ )  {
      my $name= $basicBlock->getRecentRegName();
      my $allAboutArgHashref= RegWithType_init( 
            $name,
            $basicBlock->getRegType($name) );
      push @allAboutArgList, $allAboutArgHashref;
      print "pushing to allAboutArgList[$ii], type=\"" . 
	    $allAboutArgHashref->{'type'} . "\"\n";;
   }
   # permute the order of the arguments
   for ( my $ii= 0; $ii < (2*$numArgs); $ii++ )  {
      my $aa= int( rand()* $numArgs );
      my $bb= int( rand()* $numArgs );
      if ( $aa == $bb ) { next; }
      my $tmp= $allAboutArgList[$aa];
      $allAboutArgList[$aa]= $allAboutArgList[$bb];
      $allAboutArgList[$bb]= $tmp;
   }
   for ( my $ii= $[; $ii < $numArgs; $ii++ )  {
      push @argTypeList, $allAboutArgList[$ii]->{'type'};
      print "allAboutArgList[$ii]->type=\"" . 
	    $allAboutArgList[$ii]->{'type'} . "\"\n";;
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # generate the target function
   my( $definitions, $instructions );
   {
      my( $defs, $insts )= function::generate(
	    $retType,
            $ftnName,
	    \@argTypeList,
	    { 'numSteps' => $numSteps,
              # TODO: find some way of ensuring a basic block uses ALL of its
              # args to compute its result, and delete this.  Until then, we
              # need this to guarantee that poison is not lost when calling a
              # function whenever the poisoned argument does not happen to be
              # called.
	      'startPoison' => $basicBlock->getStartPoison(),
	    } );

      if ( $defs =~ m/%1\D.*%1\D/ )  {
	 confess( $main::scriptname . ": internal error 2015apr10_100654 " .
	       "(two %1s in defs)" );;
      }
      if ( $insts =~ m/%1\D.*%1\D/ )  {
	 confess( $main::scriptname . ": internal error 2015apr10_101421 " .
	       "(two %1s in defs)" );;
      }

      $definitions.= $defs;
      $definitions.= $insts;
      if ( $definitions =~ m/%1\D.*%1\D/ )  {
	 confess( $main::scriptname . ": internal error 2015apr10_100943 " .
	       "(two %1s in definitions)" );;
      }
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # generate the function call
   #
   # we're trying to generate something like this:
   # %call = call i32* @func_2(i32 %13, i32 %12)
   my $dest= $basicBlock->getRegName();
   $basicBlock->getRegType( $dest, $retType );
   $instructions= $basicBlock->indent() . "$dest= " .
	 "call " . $retType->getName() . " $ftnName( ";
   for ( my $ii= $[; $ii < $numArgs; $ii++ )  {
      $instructions.= $allAboutArgList[$ii]->{'type'}->getName() . ' ' . 
	    $allAboutArgList[$ii]->{'register'};
      if ( $ii < ($numArgs-1) )  {
	 $instructions.= ", ";
      }
   }
   $instructions.= " ) \n";

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # clean up and return
   if ( $basicBlock->{'regNum'}== 1 )  {;;
      Carp::confess ( "   regNum=1\n" );;
   }
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

