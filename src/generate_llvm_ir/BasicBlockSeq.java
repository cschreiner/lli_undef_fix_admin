/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: BasicBlockSeq.java
   *
   * File Description: class BasicBlockSeq
   *	(list here the package-scope classes in this file)
   *
   * lli_undef_fix was written by Christian A. Schreiner at University of
   * Utah.  Copyright (C) 2015-2015 by University of Utah.  All rights
   * reserved.  You may use, examine, or modify this file only in accordance
   * with the GNU Public License, or, alternately, by special written
   * arrangement with the author.  This file comes with no warranties.  If
   * you use it and something breaks, you are solely responsible for
   * cleaning up afterwards.
   *
   */

/*.. Configuration Management Information:
   * 
   * $Source: /home/cas/templates/auto_uut/RCS/cas_tmpl.java,v $
   * $File: $
   * $Author: cas $
   * $Date: 2014/04/04 10:25:56 $
   * $Revision: 1.3 $
   * 
   */

/* ****************************************************************************
   *   package
   * **************************************************************************
   */
package generate_llvm_ir;

/* ****************************************************************************
   *   imports
   * **************************************************************************
   */
//import java.util.*;
//import java.applet.Applet;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.Color.*;
//import java.awt.geom.*;

import generate_llvm_ir.*;
// TODO2: see if this can be made more specific:
//import generate_llvm_ir.RegContext;
//import generate_llvm_ir.TypeInteger;
//import generate_llvm_ir.instruction;
//import generate_llvm_ir.BasicBlockSeqInitializer;


// ****************************************************************************
// File's primary class: BasicBlockSeq
// ****************************************************************************
/*** Description: code to generate a basic block.  Recall that, in general, a
 *	basic block is a sequence of instructions such that if any of them are
 *	executed, all of them are executed.  A basic block sequence, here, is
 *	a sequence of 1+ basic blocks, possibly interrupted by a control flow
 *	statement (e.g. branch, switch, or similar).
 *
 * <ul> 
 * <li> Algorithm: 
 * </ul>
 */
public class BasicBlockSeq extends RegContext
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */
   static final String INDENT_INCREMENT= "  ";

   /* =========================================================================
      * instance variables
      * =======================================================================
      */
   /** contains the whitespace that should prefix the line of code currently
      being generated.
    */
   String indent;

   /** the prefix to apply to this block's register names */
   String regPrefix;

   /** the prefix to apply to this block's label names */
   StringlabelPrefix;

   /** the basic block that called this one.  Is null if this is the initial
	basic block for a function.
   */
   BasicBlockSeq parentBasicBlock;

   /** if a child block was to be created now (presumably as a result of a
    * control flow instruction), would it be the 0th, first, second, or nth
    * child block?
    */
   int childBlockNum;
   /** string representation of childBlockNum */
   String childBlock;

   /** The data type that the block starts with, is currently using as the
    * block's instructions are generated, and that the block should stop with.
    * If this is the primary basic block for a function, stopType must be the
    * type the function will return.
    */
   TypeInteger currentType;

   /** the number if steps (instructions) remaining to be generated */
   int remainingSteps; 

   /** fields obtained from the BasicBlockSeqInitializer passed to the
    * constructor.  These are documented in that class.
    */
   boolean startPoison;
   int numSteps;
   TypeInteger startType, stopType;
   String ftnName;

   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // BasicBlockSeq()
   // -------------------------------------------------------------------------
   /*** default constructor
      *
      * <ul>
      * <li> Detailed Description: 
      *
      * <li> Algorithm: 
      *
      * <li> Reentrancy: unknown
      *
      * <li> No inputs.
      * </ul>
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   private BasicBlockSeq()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for BasicBlockSeq." );
      System.exit(-127);
   }}

   // ------------------------------------------------------------------------
   // BasicBlockSeq()
   // ------------------------------------------------------------------------
   /***  commonly used constructor
      * 
      * <ul>
      * <li> Detailed Description: 
      *
      * <li> Algorithm: 
      *
      * <li> Reentrancy: unknown
      * </ul>
      *
      * @param paramParentBasicBlock - the basic block (or basic block sequence)
      *		invoking this one, or
      *		null if this is the first basic block of a function.
      *
      * @param paramOptions - a BasicBlockSeqInitializer instance holding may
      *		options configuring this BasicBlockSeq instance.  If this is
      *		undef, default values are chosen.
      * 
      * @throws 
      */
   public BasicBlockSeq()
   {{
      BasicBlockSeqInitializer options= null;

      System.out.println( "starting BasicBlockSeq(~) constructor\n" );;
      if ( options.ftnName == null ) {
	 throw new Error( "ftnName may not be null." );
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // check options

      // if no options specified, use default values
      if ( paramOptions == null )  {
	 options= new BasicBlockSeqInitializer();
      } else {
         options= paramOptions;
      }

      if ( "".equals(options.ftnName) ) {
	 throw new Error( "ftnName may not be an empty string." );
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // copy over the options, element by element
      startPoison= options.startPoison;
      numSteps= options.numSteps;
      startType= options.startType;
      stopType= options.stopType;
      ftnName= options.ftnName;

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // main initialization based on the parent block
      if ( paramParentBasicBlock == null )  {
	 /* we DO NOT have a parent basic block; stated equivalently, we are the
	  * first block of a function.
	  */
	 parentBasicBlock= null;
	 if ( numSteps == BasicBlockSeqInitializer.NUM_STEPS_DEFAULT ) {
	    throw new Error( "Number of steps is required for the first "+ 
			     "block of a function." );
	 }
	 indent= INDENT_INCREMENT;
	 if( options.startType != null ) {
	    this.startType= options.startType;
	 } else {
	    throw new Error( "start type must be specified for the first "+
			     "block of a function. (2015apr09_114443).\n" );
	 }
	 
	 /* LLVM IR rules require the first register to be named %1, with no
	    prefix.  For consistency, other registers in this block don't have
	    a prefix, either.
	 */
	 regPrefix= "";
	 
	 labelPrefix= "l_";
      } else {
	 /* we DO have a parent basic block */
	 parentBasicBlock= paramParentBasicBlock;
         $parentBasicBlock->incrementSubBlock();

	 if ( options.numSteps == BasicBlockSeqInitializer.NUM_STEPS_DEFAULT ) {
	    numSteps= parentBasicBlock.numSteps/ 3;
	 } else {
	    numSteps= options.numSteps;
	 }

	 indent= parentBasicBlock.indent+ INDENT_INCREMENT;
	 if ( startType == null ) {
	    startType= parentBasicBlock.startType;
	 }
	 regPrefix= parentBasicBlock.regPrefix+ parentBasicBlock.subBlock;
	 labelPrefix= parentBasicBlock.labelPrefix+ parentBasicBlock.subBlock;
      }

      # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      # other main initialization 
      subBlockNum= 0;
      subBlock= "-";
      currentType= startType;
      if ( stopType== null ) {
	 stopType= startType;
      }
      if ( numSteps < 1 ) { numSteps= 1; }
      remainingSteps= numSteps;

      # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      # initialize superclass with stuff derived from the above
      super.init();

      # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      # check values
      if ( numSteps <= 0 ) {
	 throw new Error( "Number of steps must be positive." );
      }
      if ( startType == null ) {
	 throw new Error( "startType must not be null." );
      }
      if ( currentType == null ) {
	 throw new Error( "currentType must not be null." );
      }
      if ( stopType == null ) {
	 throw new Error( "stopType must not be null." );
      }

      # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      # clean up and return
      System.out.print( "stopping BasicBlockSeq::new(~)\n" );;
      return $this;
   }}

   /* =========================================================================
      * methods
      * =======================================================================
      */
   // ------------------------------------------------------------------------
   // incrementSubBlock()
   // ------------------------------------------------------------------------
   /*** increments the subblock designation
      * 
      * <ul>
      * <li> Detailed Description: 
      *
      * <li> Algorithm: 
      *
      * <li> Reentrancy: unknown
      *
      * <li> No inputs.
      * </ul>
      * 
      * @return - void
      *
      * @throws 
      */
   private void incrementSubBlock()
   {{
      static final String DIGITS= "abcdefghijklmnopqrstuvwxyz";
      static final int BASE= DIGITS.length();
      int rest= 0;

      // the reverse of the String we're trying to build
      StringBuffer revSt= new StringBuffer(""); 

      subBlockNum++;
      rest= subBlockNum;
      
      while ( rest > 0 )  {
	 revSt.append( DIGITS.charAt(rest % BASE ) );
	 rest= rest / BASE;
      }

      # a subblock always begins with a capital letter
      {
	 int lastCharIdx= revSt.length()- 1;
	 revSt.replace( lastCharIdx, lastCharIdx,
			revSt.substring(lastCharIdx).toUpper() );
      }

      subBlock= revSt.reverse();
   }}

asdf

## ===========================================================================
## Subroutine generate()
## ===========================================================================
# Description: generates the block
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work with
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub generate
{{
   my( $this )= @_;
   print "starting BasicBlockSeq::generate(~)\n";;

   our ( $definitions, $instructions );

   if ( !defined($this->{'parentBasicBlock'}) )  {
      # A basic block beginning a function must initially set at least
      # 2 registers, so later opcodes requiring 2 operands will have
      # them available.  If there aren't enough arguments provided, create
      # enough registers to compensate.
      for ( my $ii= $this->numArgs(); $ii < 2; $ii++ )  {
	 my ( $def, $inst )= instruction::generate_const_inst( $this );
	 $definitions.= $def;
	 $instructions.= $inst;
      }
   }

   if ( $this->{'optHashref'}->{'startPoison'} )  {
     $instructions.= "  " . $this->getRegName() . 
	   "= sub nuw " . $this->currentType()->getName() . 
	    " 0, 1 ; generates POISON \n";
     # TODO2: replace the above operands with random numbers
   }

   while ( $this->{'remainingSteps'} > 0 )  {
      print "remainingSteps=" . $this->{'remainingSteps'} . ".\n";;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      my( $def, $inst )= instruction::generate_one_inst( $this );
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      $definitions.= $def;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      {
	 # TODO: maybe move this to a method called by the instr. generator
	 $this->{'remainingSteps'}--;
	 $instructions.= $this->{'indent'} . "; step \n";
      }
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      $instructions.= $inst;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      if ( $inst =~ m/%1\D.*%1\D/ )  {
	 Carp::confess( $main::scriptname . 
	    ": internal error 2015apr10_102650 " .
	    "(two %1s in inst)" );;
      }
      if ( $instructions =~ m/%1\D.*%1\D/ )  {
	 Carp::confess( $main::scriptname . 
	    ": internal error 2015apr10_102820 " .
	    "(two %1s in instructions)" );;
      }
   }

   if ( $this->{'currentType'}->compareTo( $this->{'stopType'} ) != 0 )  {
      # TODO2: add code here to convert the last 1 or 2 registers to
      # the stop type.
      die $main::scriptname . ": internal error 2015apr09_114820.\n";
   }
 
   # clean up and return
   print "stopping BasicBlockSeq::generate(~)\n";;
   return ( $definitions, $instructions );
}}

## ===========================================================================
## short getter routines
## ===========================================================================
# Description: return the corresponding field
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance whose field will be returned
# 
# Return Value: the field value
#   
# ============================================================================
# template is 5 lines long
#sub name
#{{
#   my( $this )= @_;
#   return $this->{'xx'};
#}}

# returns the TypeInteger instance defining the stop type
sub getStopType
{{
   my( $this )= @_;
   return $this->{'stopType'};
}}

sub indent
{{
   my( $this )= @_;
   return $this->{'indent'};
}}

sub getStartPoison
{{
   my( $this )= @_;
   return $this->{'optHashref'}->{'startPoison'};
}}

sub numRemainingSteps
{{
   my( $this )= @_;
   return $this->{'remainingSteps'};
}}

sub currentType
{{
   my( $this )= @_;
   return $this->{'currentType'};
}}


/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class BasicBlockSeq


/* ****************************************************************************
   * templates 
   * **************************************************************************
   */

/* template is 22 lines long */
// ############################################################################
// class_name()
// ############################################################################
/***  
   * 
   * <ul>
   * 
   * <li> Detailed Description: 
   * 
   * <li> Algorithm: 
   * </ul>
   * 
   */
//private class class_name
//{
//   /* variables */
//
//   /* constructors */
//   class_name() { }
//
//   /* methods */
//} // end nestedclass class_name


   /* template is 22 lines long */
   /* Note: change the initial dots to stars for public functions */
   // ------------------------------------------------------------------------
   // fname()
   // ------------------------------------------------------------------------
   /***  
      * 
      * <ul>
      * <li> Detailed Description: 
      *
      * <li> Algorithm: 
      *
      * <li> Reentrancy: unknown
      *
      * <li> No inputs.
      * </ul>
      * 
      * @return - 
      *
      * @throws 
      */
   //private type fname()
   //{{
   //}}



/* ****************************************************************************
   *   end of file
   * **************************************************************************
   */

#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: BasicBlockSeq.pm
##
## Description: code to generate a basic block.
#	Recall that, in general, a basic block is a sequence of instructions
#	such that if any of them are executed, all of them are executed.  A
#	basic block sequence is a sequence of 1+ basic blocks, possibly
#	interrupted by a control flow statement (e.g. branch, switch, or
#	similar).
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## BasicBlockSeq.pm was written by Christian A. Schreiner at University of Utah.  
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

use TypeInteger;
use instruction;

## ****************************************************************************
## package identification

package BasicBlockSeq;
use parent qw ( RegContext );

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
      $pkgname= "BasicBlockSeq";

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
# Notes: reads command line arguments
#
# Warnings: 
#
# Inputs: 
#   $perl_class: class info (provided by PERL)
#   $parentBasicBlock: the basic block (or basic block sequence) invoking this
#	one, or 
#	undef if this is the first basic block of a function.
#   $optHashref: reference to a hash of options.  This parameter must be 
#	present, but may be empty.  Valid options are:
#	startPoison (boolean): true if the block should set a variable to a 
#		poison value early in the block.
#       numSteps (unsigned): the number of steps (instructions) the block 
#		should contain.  The actual number may be slightly higher 
#		in order to do data conversions and other semantic 
#		housekeeping.
#	startType (TypeInteger instance): initial integer type for the 
#		block.  This is required if the block has no parent.  If the
#		block has a parent, this defaults to the current type of
#		the parent.
#	stopType (TypeInteger instance): final integer type for the block. 
#		If omitted, defaults to startType.
#       ftnName (string): name of the function for which this BasicBlockSeq 
#		is geing generated #;;
# 
# Outputs: none
#   
# Return Value: a reference to the new instance
#   
# ============================================================================
sub new
{{
   my( $perl_class, $parentBasicBlock, $optHashref )= @_;
   print "starting BasicBlockSeq::new(~)\n";;

   my $this= {};
   bless $this, $perl_class;

   if ( !exists($optHashref->{'ftnName'}) )  {
      Carp::confess( "no ftnName specified" );;
   }
   if ( $optHashref->{'ftnName'} eq "" )  {
      Carp::confess( "ftnName specified as a null string" );;
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # copy over the options, element by element

   # set the default values
   $this->{'optHashref'}= {
			    'startPoison' => $main::FALSE,
			    'numSteps' => 10,
			    'startType' => undef,
			    'stopType' => undef, 
			    'ftnName' => 'unknown_ftn', #;;
			   };

   # replace defaults with whatever options were handed in
   foreach my $opt ( keys( %{$this->{'optHashref'}} ) )  {
      if ( exists($$optHashref{$opt}) )  {
	 $this->{'optHashref'}->{$opt}= $optHashref->{$opt}; 
      }
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # main initialization based on the parent block
   if ( defined($parentBasicBlock) )  {
      $parentBasicBlock->incrementSubBlock();
      $this->{'numSteps'}= int($parentBasicBlock->{'remainingSteps'}/ 3);
      if ( defined( $this->{'optHashref'}->{'numSteps'} ) )  {
	 $this->{'numSteps'}= $this->{'optHashref'}->{'numSteps'}; 
      } else {
	 $this->{'numSteps'}= int($parentBasicBlock->{'remainingSteps'}/ 3);
      }
      $this->{'indent'}= $parentBasicBlock->{'indent'} . "  ";
      if ( defined( $this->{'optHashref'}->{'startType'} ) )  {
	 $this->{'startType'}= $this->{'optHashref'}->{'startType'}; 
      } else {
	 $this->{'startType'}= $parentBasicBlock->{'currentType'};
      }
      $this->{'regPrefix'}= $parentBasicBlock->{'regPrefix'} . 
	    $parentBasicBlock->{'subBlock'};
      $this->{'labelPrefix'}= $parentBasicBlock->{'labelPrefix'} . 
	    $parentBasicBlock->{'subBlock'};
   } else {
      if ( defined( $this->{'optHashref'}->{'numSteps'} ) )  {
	 $this->{'numSteps'}= $this->{'optHashref'}->{'numSteps'}; 
      } else {
	 # we need a number of steps
	 die $main::scriptname . ": internal error 2015apr09_115148. \n";
      }
      $this->{'indent'}= "  ";
      if ( defined( $this->{'optHashref'}->{'startType'} ) )  {
	 $this->{'startType'}= $this->{'optHashref'}->{'startType'}; 
      } else {
	 # no known starting type
	 die $main::scriptname . ": internal error 2015apr09_114443.\n";
      }
      $this->{'regPrefix'}= ""; 
	    # LLVM IR rules require the first register to be named %1, with no
	    # prefix.  For consistency, other registers in this block don't
	    # have a prefix, either.
      $this->{'labelPrefix'}= "l_";
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # other main initialization 
   $this->{'parentBasicBlock'}= $parentBasicBlock;
   $this->{'subBlockNum'}= 0;
   $this->{'subBlock'}= "-";
   $this->{'currentType'}= $this->{'startType'};
   if ( defined( $this->{'optHashref'}->{'stopType'} ) )  {
      $this->{'stopType'}= $this->{'optHashref'}->{'stopType'}; 
   } else {
      $this->{'stopType'}= $this->{'startType'};
   }
   if ( $this->{'numSteps'} < 1 )  { 
      $this->{'numSteps'}= 1; 
   }
   $this->{'remainingSteps'}= $this->{'numSteps'};
   
   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # initialize superclass with stuff derived from the above
   $this->initRegContext( $this->{'regPrefix'} );

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # clean up and return
   print "stopping BasicBlockSeq::new(~)\n";;
   return $this;
}}

## ===========================================================================
## Subroutine incrementSubBlock()
## ===========================================================================
# Description: increments the subblock designation
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work with (provided by PERL)
# 
# Outputs: none
#   
# Return Value: TRUE
#   
# ============================================================================
sub incrementSubBlock
{{
   my( $this )= @_;

   use constant DIGITS=> qw ( 
	 a b c d e f g h i j k l m n o p q r s t u v w x y z );
   use constant BASE=> scalar(DIGITS);
   $this->{'subBlockNum'}++;

   my $rest= $this->{'subBlockNum'};
   my $st= "";

   while ( $rest > 0 )  {
      $st= DIGITS->[$rest % BASE] . $st;
      $rest= $rest / BASE;
   }

   # a subblock always begins with a capital letter
   substr($st, $[)= uc( substr($st, $[) );

   # clean up and return
   $this->{'subBlock'}= $st;
   return $main::TRUE;
}}

## ===========================================================================
## Subroutine generate()
## ===========================================================================
# Description: generates the block
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work with
# 
# Outputs: none
#   
# Return Value: a list with these elements:
#   string containing pre-function definitions related to the generated 
#	instructions
#   string containing the instruction generated
#
# ============================================================================
sub generate
{{
   my( $this )= @_;
   print "starting BasicBlockSeq::generate(~)\n";;

   our ( $definitions, $instructions );

   if ( !defined($this->{'parentBasicBlock'}) )  {
      # A basic block beginning a function must initially set at least
      # 2 registers, so later opcodes requiring 2 operands will have
      # them available.  If there aren't enough arguments provided, create
      # enough registers to compensate.
      for ( my $ii= $this->numArgs(); $ii < 2; $ii++ )  {
	 my ( $def, $inst )= instruction::generate_const_inst( $this );
	 $definitions.= $def;
	 $instructions.= $inst;
      }
   }

   if ( $this->{'optHashref'}->{'startPoison'} )  {
     $instructions.= "  " . $this->getRegName() . 
	   "= sub nuw " . $this->currentType()->getName() . 
	    " 0, 1 ; generates POISON \n";
     # TODO2: replace the above operands with random numbers
   }

   while ( $this->{'remainingSteps'} > 0 )  {
      print "remainingSteps=" . $this->{'remainingSteps'} . ".\n";;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      my( $def, $inst )= instruction::generate_one_inst( $this );
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      $definitions.= $def;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      {
	 # TODO: maybe move this to a method called by the instr. generator
	 $this->{'remainingSteps'}--;
	 $instructions.= $this->{'indent'} . "; step \n";
      }
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      $instructions.= $inst;
      $this->carpIfRegNumReset(  $this,
	    "recent instructions= <<EOF \n" . $instructions . "\nEOF\n" );
      if ( $inst =~ m/%1\D.*%1\D/ )  {
	 Carp::confess( $main::scriptname . 
	    ": internal error 2015apr10_102650 " .
	    "(two %1s in inst)" );;
      }
      if ( $instructions =~ m/%1\D.*%1\D/ )  {
	 Carp::confess( $main::scriptname . 
	    ": internal error 2015apr10_102820 " .
	    "(two %1s in instructions)" );;
      }
   }

   if ( $this->{'currentType'}->compareTo( $this->{'stopType'} ) != 0 )  {
      # TODO2: add code here to convert the last 1 or 2 registers to
      # the stop type.
      die $main::scriptname . ": internal error 2015apr09_114820.\n";
   }
 
   # clean up and return
   print "stopping BasicBlockSeq::generate(~)\n";;
   return ( $definitions, $instructions );
}}

## ===========================================================================
## short getter routines
## ===========================================================================
# Description: return the corresponding field
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance whose field will be returned
# 
# Return Value: the field value
#   
# ============================================================================
# template is 5 lines long
#sub name
#{{
#   my( $this )= @_;
#   return $this->{'xx'};
#}}

# returns the TypeInteger instance defining the stop type
sub getStopType
{{
   my( $this )= @_;
   return $this->{'stopType'};
}}

sub indent
{{
   my( $this )= @_;
   return $this->{'indent'};
}}

sub getStartPoison
{{
   my( $this )= @_;
   return $this->{'optHashref'}->{'startPoison'};
}}

sub numRemainingSteps
{{
   my( $this )= @_;
   return $this->{'remainingSteps'};
}}

sub currentType
{{
   my( $this )= @_;
   return $this->{'currentType'};
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

