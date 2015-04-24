/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: Instruction.java
   *
   * File Description: class Instruction
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


// ****************************************************************************
// File's primary class: Instruction
// ****************************************************************************
/** contains code to generate an LLVM instructions of various types
 *
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class Instruction 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
    * class variables
    * =======================================================================
    */
   //Vector<Opcode> opcodes= new Vector<Opcode>();
   private boolean static initialized= false;
   private final static Opcodes[] opcodes= {
      new OpcodeCharacteristics( 
				"add",  // name
				"arith", // type
				"generate_arith_inst" // genFtn
				{ "nsw", "nuw" } ), // flags
      new OpcodeCharacteristics( 
				"sub",  // name
				"arith", // type
				"generate_arith_inst" // genFtn
				{ "nsw", "nuw" } ), // flags
//;; TODO: reinstate these opcodes
//;;      new OpcodeCharacteristics( 
//;;				"mul",  // name
//;;				"arith", // type
//;;				"generate_arith_inst" // genFtn
//;;				{ "nsw", "nuw" } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"sdiv",  // name
//;;				"arith", // type
//;;				"generate_arith_inst" // genFtn
//;;				{ "exact" } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"udiv",  // name
//;;				"arith", // type
//;;				"generate_arith_inst" // genFtn
//;;				{ "exact" } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"srem",  // name
//;;				"arith", // type
//;;				"generate_arith_inst" // genFtn
//;;				{ } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"urem",  // name
//;;				"arith", // type
//;;				"generate_arith_inst" // genFtn
//;;				{ } ), // flags
      new OpcodeCharacteristics( 
				"and",  // name
				"arith", // type
				"generate_arith_inst" // genFtn
				{ } ), // flags
      new OpcodeCharacteristics( 
				"or",  // name
				"arith", // type
				"generate_arith_inst" // genFtn
				{ } ), // flags
      new OpcodeCharacteristics( 
				"xor",  // name
				"arith", // type
				"generate_arith_inst" // genFtn
				{ } ), // flags
      new OpcodeCharacteristics( 
				"shl",  // name
				"shift", // type
				"generate_arith_inst" // genFtn
				{ "nsw", "nuw" } ), // flags
      new OpcodeCharacteristics( 
				"lshr",  // name
				"shift", // type
				"generate_arith_inst" // genFtn
				{ "exact" } ), // flags
      new OpcodeCharacteristics( 
				"ashr",  // name
				"shift", // type
				"generate_arith_inst" // genFtn
				{ "exact" } ), // flags
      new OpcodeCharacteristics( 
				"storeload",  // name
				"storeload", // type
				"generate_storeload_inst" // genFtn
				{ } ), // flags
      new OpcodeCharacteristics( 
				"call",  // name
				"call", // type
				"generate_call_inst" // genFtn
				{ } ), // flags
      // template is 5 lines long
      //new OpcodeCharacteristics( 
      //			  "call",  // name
      //			  "call", // type
      //			  "generate_call_inst" // genFtn
      //			  { } ), // flags
   };

   /* =========================================================================
    * instance variables
    * =======================================================================
    */


   /* =========================================================================
    * constructors
    * =======================================================================
    */

   // -------------------------------------------------------------------------
   // Instruction()
   // -------------------------------------------------------------------------
   /** default constructor
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
   private Instruction()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for Instruction." );
      System.exit(-127);
   }}


   /* =========================================================================
    * methods
    * =========================================================================
    */

   // ------------------------------------------------------------------------
   // init()
   // ------------------------------------------------------------------------
   /**  initializes class variables.  May be called repeatedly, but only
    *	initializes the variables on the first call.  Must be called by every
    *	public method.
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
   private void init()
   {{
      initialized= true;
   }}

   // ------------------------------------------------------------------------
   // generateOneInst()
   // ------------------------------------------------------------------------
   /**  generates a randomly selected instruction
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    * 
    * @param basicBlock - the BasicBlockSeq instance with context information
    *	for the instruction.
    * 
    * @return - the instructions generated, and associated necessary 
    *	definitions
    *
    * @throws 
    */
   public type generateOneInst( BasicBlockSeq basicBlock )
   {{
      init();

      static Random randomizer= new Random();
      System.out.print( "starting instruction::generate_one_inst(~) \n" );;

      OpcodeCharacteristics opcode= null;
      {
	 int opcodeIdx= randomizer.nextInt(opcodes.length);
         opcode= opcodes[opcodeIdx];
	 if ( $main::debug_flag )  {
	    System.out.print( "selected opcode=\""+ opcode.name+ "\"\n" );
	 }
      }

      basicBlock->carpIfRegNumReset( $basicBlock,
	    "(from beginning of instruction::generate_one_inst)\n" );;

      CodeChunk cc= null;
      if ( "arith".equals(opcode.type) ) {
	 cc= generateArithInst( basicBlock, opcode );
      } else if ( "shift".equals(opcode.type) )  {
	  cc= generateShiftInst( basicBlock, opcode );
      } else if ( "storeload".equals(opcode.type) )  {
	  cc= generateStoreLoadInst( basicBlock, opcode );
      } else if ( "call".equals(opcode.type) )  {
	  cc= generateCallinst( basicBlock, opcode );
      } else {
	  throw new Error( Main::PROGRAM_NAME+  
	       ": don't recognize opcode type for \""+ opcode.name+ "\", \""+ 
	       $opcode_hash{$opcode}->{'type'}+ 
	       "\"(message 2015apr23_111740)\n" );
      }

   basicBlock.carpIfRegNumReset( basicBlock,
	 "last instruction=\""+ opcode.name+ "\"\n" );;
   System.out.print( "stopping instruction::generate_one_inst(~) \n" );;
   return cc;
}}

   // ------------------------------------------------------------------------
   // generateStoreLoadInst()
   // ------------------------------------------------------------------------
   /** Generates a store instruction, immediately followed by a load
    *	instruction.  The idea is to make sure every value gets stored also
    *	gets loaded later in the program.
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    *
    * @param basicBlock - context info for the instruction
    *
    * @param opcode - the actual opcode to generate (Admittedly this
    *	parameter is redundant, but it provides consistency with similar
    *	methods in this class.)
    * 
    * @return - the instructions generated, and associated necessary 
    *	definitions
    *
    * @throws 
    */
    public CodeChunk generateStoreLoadInst( BasicBlockSeq basicBlock, 
					    OpcodeCharacteristics opcode )
   {{           
      init();

      /* TODO2: have a package similar to RegContext that generates
       * these names and guarantees that the same name is not used
       * twice.  Maybe make the names have form
       * consonant-vowel-consonant_consonant-vowel-consonant to make
       * them pronouncible. 
       */

      String addrName= AddrName.get('var');
      String destReg= basicBlock.getRegName();
      String srcReg= basicBlock.getPrevRegName(1);
      StringBuffer defs= new StringBuffer("");
      StringBuffer insts= new StringBuffer("");


      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      defs.append( addrName+ " = global "+
		   basicBlock.currentType().getName()+ " "+
		   basicBlock.currentType().getRandVal()+ "\n" );

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      String storeFlags= " ";
      if ( Math.random() < .5 )  {
	  storeFlags= "volatile ";
      }
      // recall that flags strings must always end in a space

      insts.append( "  "+ "store "+
	    $storeFlags+ $basicBlock->currentType()->getName()+ ' '+
	    $srcReg+ ', '+ 
	    basicBlock->currentType()->getName()+ '* '+ addrName+ "\n" );
      insts.append( "  "+ destReg+ "= load "+
	    basicBlock->currentType()->getName()+
	    "* "+ addrName+ " \n" );
      /* TODO2: consider adding an 'align 4' or similar to the load and 
	 store instructions, possibly as an optional flag.
      */

   basicBlock->reportType( destReg, basicBlock->currentType() );
   
   basicBlock->carpIfRegNumReset( basicBlock,
	 "at end of instruction::generate_storeload_insts("+ opcode.name+ 
	 ")\n" );;
   return new CodeChunk( defs, insts );
}}

   // ------------------------------------------------------------------------
   // generateShiftInst()
   // ------------------------------------------------------------------------
   /** generates a shift instruction.  Shift instructions 
    *	use a register value for one argument, and randomly generated
    *	constant for the other.  This guards against the constant
    *	being larger than the number of bits in the register.
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    *
    * @param basicBlock - context info for the instruction
    *
    * @param opcode - the actual opcode to generate 
    * 
    * @return - the instructions generated, and associated necessary 
    *	definitions
    *
    * @throws 
    */
    public CodeChunk generateShiftInst( BasicBlockSeq basicBlock, 
					OpcodeCharacteristics opcode )
   {{
      init();

      String destReg= basicBlock.getRegName();
      StringBuffer flags= new StringBuffer( "" );


   for ( int ii= 0; ii < opcode.flags.length; ii++ )  {
      if ( Math.random() < .5 )  {
	 // a flag must ALWAYS end in a space. 
	 flags.append( opcode.flags[ii] . " " );
      }
   } 

   String operand1= basicBlock.getPrevRegName(1);

   // operand2 is a constant
   String operand2= basicBlock.currentType().getRandShiftVal();
   
   StringBuffer inst= new StringBuffer("");
   inst.append( "  "+ destReg+ "= "+ opcode.name+ ' '+
	 flags+ basicBlock->currentType()->getName()+ ' '+
	 operand1+ ', '+ operand2+ "\n" );

   basicBlock.reportType( destReg, basicBlock.currentType() );
   $basicBlock->carpIfRegNumReset( basicBlock,
	 "at end of instruction::generate_shift_insts("+ opcode.name+ ")\n" );;
   return new CodeChunk("", inst );
}}

   // ------------------------------------------------------------------------
   // generateArithInst()
   // ------------------------------------------------------------------------
   /** generates an arithmetic instruction.  Arithmetic instructions 
    *	use a register value for one argument, and either a register or a 
    *	literal value for the other.  These arguments can be in either order. 
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    * 
    * @param basicBlock - context info for the instruction
    *
    * @param opcode - the actual opcode to generate 
    * 
    * @return - the instructions generated, and associated necessary 
    *	definitions
    *
    * @throws 
    */
   public CodeChunk generateArithInst( BasicBlockSeq basicBlock, 
				       OpcodeCharacteristics opcode )
   {{
      init();

      String destReg= basicBlock.getRegName();
      StringBuffer flags= new StringBuffer("");

      for( int ii= 0; ii < opcode.flags.length; ii++ )  {
	 if ( Math.random() < .5 )  {
	     flags.append( opcode.flags[ii]+ " " );
	 }
      }

      String operand1= basicBlock.getPrevRegName(1);

      String operand2= "";
      if ( Math.random() < .5 )  {
	 # operand will be a constant
	 operand2= basicBlock.currentType().getRandVal();
      } else {
	 operand2= basicBlock.getRecentRegName();
      }
   
      // maybe swap operands
      if ( Math.random() < .5 )  {
	  String tmp= operand1;  
	  operand1= operand2;
	  operand2= tmp;
      }

      String inst= "  " + destReg+ "= "+ opcode.name+ " "+ 
	    flags+ basicBlock.currentType().getName()+ " "+
	    operand1+ ", "+ operand2+ "\n";

      basicBlock.reportType( destReg, basicBlock.currentType() );
      basicBlock.carpIfRegNumReset( basicBlock,
	    "at end of instruction::generate_arith_insts("+ opcode.name+ 
	    ")\n" );;
      return new CodeChunk( "", inst );
   }}

   // ------------------------------------------------------------------------
   // generateConstInst()
   // ------------------------------------------------------------------------
   /** generates a constant-assignment instruction.  A constant 
    *	assignment instruction assigns a constant to a register. 
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    * 
    * @param basicBlock - context info for the instruction
    *
    * @param opcode - the actual opcode to generate (Admittedly this
    *	parameter is redundant, but it provides consistency with similar
    *	methods in this class.)
    * 
    * @return - the instructions generated, and associated necessary 
    *	definitions
    *
    * @throws 
    */
    public CodeChunk generateConstInst( BasicBlockSeq basicBlock, 
					OpcodeCharacteristics opcode )
   {{
      init();

      retName= basicBlock.getRegName();
      String inst= basicBlock.indent()+
	    regName+ "= add "+ 
	    basicBlock.currentType().getName()+ ' ' + 
	    basicBlock.currentType().getRandVal() + ", 0 \n";
      basicBlock.reportType( regName, basicBlock.currentType() );
      basicBlock.carpIfRegNumReset( basicBlock,
	    "at end of instruction::generate_const_insts()\n" );;
      return new CodeChunk("", inst );
   }}

   // ------------------------------------------------------------------------
   // generateCallInst()
   // ------------------------------------------------------------------------
   /** generates a call instruction, and a function for it to call
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    *
    * </ul>
    * 
    * @param basicBlock - context info for the instruction
    *
    * @param opcode - the actual opcode to generate (Admittedly this
    *	parameter is redundant, but it provides consistency with similar
    *	methods in this class.)
    * 
    * @return - the instructions generated, and associated necessary 
    *	definitions
    *
    * @throws 
    */
   public CodeChunk generateCallInst( BasicBlockSeq basicBlock, 
				      OpcodeCharacteristics opcode )
   {{
      init();

      System.out.print( "starting instruction::generate_call_inst(~)\n" );;

      if ( ! "call".equals(opcode.name) )  {
	 throw new Error( Main.PROGRAM_NAME+
			  ": internal error 2015apr9_162208, code=\""+ 
			  opcode.name+ "\". \n" );
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // set up everything but the arguments
      String ftnName= AddrName.get("ftn"); // TODO: finish fixing this
					  // CAS: what is there still to fix?
      System.out.print( "Instruction::generateCallInst(~) generating ftn \""+ 
			ftnName+ "\"\n" );;
      TypeInteger retType= basicBlock.currentType();

      int numSteps= basicBlock->numRemainingSteps()/ 3;
      if ( numSteps < 2 )  {
	 numSteps= 2;
	 /* TODO2: consider returning a NO_OP in this case, and make the
	    caller then choose a different instruction.
	 */
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // set up arguments
      static final int MAX_NUM_ARGS= 3;
      static Random randomizer= new Random();

      // should yield something in range 0...MAX_NUM_ARGS.
      int numArgs= randomizer.nextInt( MAX_NUM_ARGS+1 );
      // TODO: make allAboutArgVector into a simple array.
      Vector<RegWithType> allAboutArgVector= new Vector<RegWithType>(numArgs);
      TypeInteger argTypeArray[numArgs];

      if ( numArgs >= 1 )  {
	 RegWithType allAbout1Arg= basicBlock->getPrevRegWithType(1);
	 allAboutArgVector[0]= allAbout1Arg;
      }
      for ( int ii= 1; ii < numArgs; ii++ )  {
	 RegWithType allAbout1Arg= basicBlock->getRecentRegWithType();
	 allAboutArgsArray[ii]= allAbout1Arg;
	 System.out.print( "pushing to allAboutArgsArray["+ ii+ "], "+ 
			   "type=\""+ $allAbout1Arg.type.name+ "\"\n" );;
      }

      // permute the order of the arguments
      for ( int ii= 0; ii < (2*numArgs); ii++ )  {
	 int aa= randomizer.nextInt(numArgs);
	 int bb= randomizer.nextInt(numArgs);
	 if ( aa == bb ) { continue; }
	 RegWithType tmp= allAboutArgsArray[aa];
	 $allAboutArgsArray[aa]= $allAboutArgsArray[bb];
	 $allAboutArgsArray[bb]= $tmp;
      }
      for ( ii= 0; ii < numArgs; ii++ )  {
	 argTypeArray[ii]= $allAboutArgsArray[ii].type;
	 System.out.print( "allAboutArgsArray["+ ii+ "].type=\""+
			   $allAboutArgsArray[ii].type+ "\"\n" );;
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // generate the target function
      StringBuffer definitions= new StringBuffer("");
      StringBuffer instructions= new StringBuffer("");
      {
	 CodeChunk newFtnChunk= Function.generate( retType, ftnName, 
	       argtypeArray, numSteps, false, 
	       /* TODO: find some way of ensuring a basic block uses ALL of
		* its args to compute its result, and delete this.  Until
		* then, we need this to guarantee that poison is not lost when
		* calling a function whenever the poisoned argument does not
		* happen to be called.
		*/
	       basicBlock.getStartPoison() );

	 if ( newFtnChunk.definitions.matches( ".*%1\D.*%1\D.*" ) )  {
	    throw new Error( Main.PROGRAM_NAME+
			     ": internal error 2015apr10_100654 " .
			     "(two %1s in newFtn defs)" );;
	 }
	 if ( newFtnChunk.instructions.matches( ".*%1\D.*%1\D.*" ) )  {
	    throw new Error( Main.PROGRAM_NAME+
			     ": internal error 2015apr10_101421 " .
			     "(two %1s in newFtn insts)" );;
	 }

	 definitions.append( newFtnChunk.definitions ); 
	 definitions.append( newFtnChunk.instructions ); 
	 if ( definitions.toString =~ m/%1\D.*%1\D/ )  {
	    throw new Error( Main.PROGRAM_NAME+ 
			     ": internal error 2015apr10_100943 " .
			     "(two %1s in definitions)" );;
	 }
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // generate the function call
      // 
      // we're trying to generate something like this:
      // %call = call i32* @func_2(i32 %13, i32 %12)
      String destReg= basicBlock.getRegName();
      basicBlock.reportType( destReg, retType );
      instructions.append( basicBlock.indent()+ destReg+ "= "+
			   "call "+ retType.getName()+ " "+ ftnName+ "( " );
      for ( ii= 0; ii < numArgs; ii++ )  {
	 instructions.append( allAboutArgsArray[ii].type.getName()+ " "+  
			      allAboutArgsArray[ii].regName );
	       if ( ii < (numArgs-1) )  {
		  instructions.append( ", " );
	       }
      }
      instructions.append( " ) \n" );

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // clean up and return
      basicBlock.carpIfRegNumReset( basicBlock,
	 "at end of instruction::generate_call_insts("+ opcode.name+ ")\n" );;
      System.out.print( "stopping instruction::generate_call_inst(~)\n" );;
      return new CodeChunk( definitions, instructions );
   }}


/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class Instruction


/* ****************************************************************************
   * templates 
   * **************************************************************************
   */

/* template is 22 lines long */
// ############################################################################
// class_name()
// ############################################################################
/**  
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
   /**  
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
   System.out.print( "starting instruction::generate_one_inst(~) \n";;

   my( $opcode );
   {
      my( @opcode_list )= keys %opcode_hash;
      $opcode= $opcode_list[ int( rand() * scalar(@opcode_list) ) ];
      if ( $main::debug_flag )  {
	System.out.print( "selected opcode=\"$opcode\"\n";
      }
   }

   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "(from beginning of instruction::generate_one_inst)\n" );;

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

   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "last instruction=\"$opcode\"\n" );;
   print "stopping instruction::generate_one_inst(~) \n";;
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
   
   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "at end of instruction::generate_storeload_insts($opcode)\n" );;
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
   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "at end of instruction::generate_shift_insts($opcode)\n" );;
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
   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "at end of instruction::generate_arith_insts($opcode)\n" );;
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
   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "at end of instruction::generate_const_insts()\n" );;
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
   print "starting instruction::generate_call_inst(~)\n";;

   if ( $opcode ne 'call' )  {
      die $main::scriptname . 
	    ": internal error 2015apr9_162208, code=\"$opcode\". \n";
   }

   # . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   # set up everything but the arguments
   my $ftnName= addr_name::get("ftn"); # TODO: finish fixing this
   print "instruction::generate(~) generating ftn \"$ftnName\"\n";;
   my $retType= $basicBlock->currentType();

   my $numSteps= int( $basicBlock->numRemainingSteps()/ 3 );
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
   $basicBlock->carpIfRegNumReset( $basicBlock,
	 "at end of instruction::generate_call_insts($opcode)\n" );;
   print "stopping instruction::generate_call_inst(~)\n";;
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

