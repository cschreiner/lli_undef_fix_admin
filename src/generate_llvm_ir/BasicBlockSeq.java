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
//import generate_llvm_ir.Instruction;
//import generate_llvm_ir.BasicBlockSeqInitializer;
//import generate_llvm_ir.CodeChunk;


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

   /* Note: the superclass, RegContext, maintains the prefix for register
    * names. 
    */

   /** the prefix to apply to this block's label names */
   String labelPrefix;

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
      * @param paramParentBasicBlock - the basic block (or basic block 
      *		sequence) invoking this one, or
      *		null if this is the first basic block of a function.
      *
      * @param paramOptions - a BasicBlockSeqInitializer instance holding may
      *		options configuring this BasicBlockSeq instance.  If this is
      *		undef, default values are chosen.
      * 
      * @throws 
      */
   public BasicBlockSeq( BasicBlockSeq paramParentBasicBlock, 
			 BasicBlockSeqInitializer paramOptions )
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
	 super.setPrefix(""); 
	 
	 labelPrefix= "l_";
      } else {
	 /* we DO have a parent basic block */
	 parentBasicBlock= paramParentBasicBlock;
         parentBasicBlock.incrementSubBlock();

	 if ( options.numSteps == BasicBlockSeqInitializer.NUM_STEPS_DEFAULT ) {
	    numSteps= parentBasicBlock.numSteps/ 3;
	 } else {
	    numSteps= options.numSteps;
	 }

	 indent= parentBasicBlock.indent+ INDENT_INCREMENT;
	 if ( startType == null ) {
	    startType= parentBasicBlock.startType;
	 }
	 super.setPrefix( parentBasicBlock.regPrefix()+ 
			  parentBasicBlock.childBlock );
	 labelPrefix= parentBasicBlock.labelPrefix+ 
	       parentBasicBlock.childBlock;
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // other main initialization 
      childBlockNum= 0;
      childBlock= "-";
      currentType= startType;
      if ( stopType== null ) {
	 stopType= startType;
      }
      if ( numSteps < 1 ) { numSteps= 1; }
      remainingSteps= numSteps;

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // check values
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

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // clean up and return
      System.out.print( "stopping BasicBlockSeq(~) constructor\n" );;
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
      final String DIGITS= "abcdefghijklmnopqrstuvwxyz";
      final int BASE= DIGITS.length();
      int rest= 0;

      // the reverse of the String we're trying to build
      StringBuffer revSt= new StringBuffer(""); 

      childBlockNum++;
      rest= childBlockNum;
      
      while ( rest > 0 )  {
	 revSt.append( DIGITS.charAt(rest % BASE ) );
	 rest= rest / BASE;
      }

      // a subblock always begins with a capital letter
      {
	 int lastCharIdx= revSt.length()- 1;
	 revSt.replace( lastCharIdx, lastCharIdx,
			revSt.substring(lastCharIdx).toUpperCase() );
      }

      childBlock= revSt.reverse().toString();
   }}

   // ------------------------------------------------------------------------
   // generate()
   // ------------------------------------------------------------------------
   /*** generates code for the block
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
      * @return - a code chunk with the generated code
      *
      * @throws 
      */
   public CodeChunk generate()
   {{
      StringBuffer definitions= new StringBuffer( "" );
      StringBuffer instructions= new StringBuffer( "" );
      System.out.print( "starting BasicBlockSeq.generate(~)\n" );;

      if ( parentBasicBlock == null )  {
	 /* A basic block beginning a function must initially set at least
	    2 registers, so later opcodes requiring 2 operands will have
	    them available.  If there aren't enough arguments provided, create
	    enough registers to compensate.
	 */
	 for ( int ii= numArgs(); ii < 2; ii++ )  {
	    CodeChunk cc= Instruction.generateConstInst( this, null );
	    definitions.append( cc.definitions );
	    instructions.append( cc.instructions );
	 }
      }
      
      if ( startPoison )  {
	 instructions.append( "  "+ getRegName()+
			      "= sub nuw "+ currentType().getName()+ 
			      " 0, 1 ; generates POISON \n" );
	 // TODO2: replace the above operands with random numbers
      }
      
      while ( remainingSteps > 0 )  {
	 System.out.print( "remainingSteps="+ remainingSteps+ ".\n" );;
	 carpIfRegNumReset(  this,
	       "recent instructions= <<EOF \n"+ instructions+ "\nEOF\n" );
	 CodeChunk cc2= Instruction.generateOneInst( this );
	 carpIfRegNumReset(  this,
	       "recent instructions= <<EOF \n"+ instructions+ "\nEOF\n" );
	 definitions.append( cc2.definitions );
	 carpIfRegNumReset(  this,
	       "recent instructions= <<EOF \n"+ instructions+ "\nEOF\n" );
	 {
	    // TODO: maybe move this to a method called by the instr. generator
	    remainingSteps--;
	    instructions.append( indent+ "; step \n" );
	 }
	 carpIfRegNumReset(  this,
	       "recent instructions= <<EOF \n"+ instructions+ "\nEOF\n" );
	 instructions.append( cc2.instructions ); 
	 carpIfRegNumReset(  this,
	       "recent instructions= <<EOF \n"+ instructions+ "\nEOF\n" );
	 if ( cc2.instructions.toString().matches(".*%1\\D.*%1\\D.*")  )  {
	    throw new Error( Main.PROGRAM_NAME+ ": "+ 
			     "internal error 2015apr10_102650 "+ 
			     "(two %1s in cc2.instructions)" ); //;;
	 }
	 if ( instructions.toString().matches(".*%1\\D.*%1\\D.*")  )  {
	    throw new Error( Main.PROGRAM_NAME+ ": "+
			     "internal error 2015apr10_102820 "+ 
			     "(two %1s in instructions)" ); //;;
	 }
      }
      
      if ( currentType.compareTo( stopType ) != 0 )  {
	 /* TODO2: add code here to convert the last 1 or 2 registers to
	    the stop type.  Until then, we have to give up.
	 */
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_114820.\n" );
      }
      
      // clean up and return
      System.out.print( "stopping BasicBlockSeq.generate(~)\n" );;
      return new CodeChunk ( definitions, instructions );
   }}

// ------------------------------------------------------------------------
// short getter/setter routines
// ------------------------------------------------------------------------
/** @return the type to have in use at the end of this basic block */
TypeInteger stopType()
{{ return stopType; }}

/** @return the whitespace to prefix to a line for proper indentation */
String indent()
{{ return indent; }}

/** @return whether this block should start with a poison value */
boolean getStartPoison()
{{ return startPoison; }}

/** @return the number of steps this block still has to generate */
int numRemainingSteps()
{{ return remainingSteps; }}

/** @return the data type this block is currently using */
TypeInteger currentType()
{{ return currentType; }}

/** @return the name of the function in which this this block will reside */
String ftnName()
{{ return ftnName; }}

/** @return the name of the subblock, should this block create one now */
String childBlock()
{{ return childBlock; }}


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

