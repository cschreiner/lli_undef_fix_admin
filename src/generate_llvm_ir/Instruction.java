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
   static Random randomizer= new Random();

   //Vector<Opcode> opcodes= new Vector<Opcode>();
   private static boolean initialized= false;
   private final static Opcodes[] opcodes= {
      new OpcodeCharacteristics( 
				"add",  // name
				"arith", // type
				"generate_arith_inst", // genFtn
				new String[] { "nsw", "nuw" } ), // flags
      new OpcodeCharacteristics( 
				"sub",  // name
				"arith", // type
				"generate_arith_inst", // genFtn
				new String[] { "nsw", "nuw" } ), // flags
//;; TODO: reinstate these opcodes
//;;      new OpcodeCharacteristics( 
//;;				"mul",  // name
//;;				"arith", // type
//;;				"generate_arith_inst", // genFtn
//;;				new String[] { "nsw", "nuw" } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"sdiv",  // name
//;;				"arith", // type
//;;				"generate_arith_inst", // genFtn
//;;				new String[] { "exact" } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"udiv",  // name
//;;				"arith", // type
//;;				"generate_arith_inst", // genFtn
//;;				new String[] { "exact" } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"srem",  // name
//;;				"arith", // type
//;;				"generate_arith_inst", // genFtn
//;;				new String[] { } ), // flags
//;;      new OpcodeCharacteristics( 
//;;				"urem",  // name
//;;				"arith", // type
//;;				"generate_arith_inst", // genFtn
//;;				new String[] { } ), // flags
      new OpcodeCharacteristics( 
				"and",  // name
				"arith", // type
				"generate_arith_inst", // genFtn
				new String[] { } ), // flags
      new OpcodeCharacteristics( 
				"or",  // name
				"arith", // type
				"generate_arith_inst", // genFtn
				new String[] { } ), // flags
      new OpcodeCharacteristics( 
				"xor",  // name
				"arith", // type
				"generate_arith_inst", // genFtn
				new String[] { } ), // flags
      new OpcodeCharacteristics( 
				"shl",  // name
				"shift", // type
				"generate_arith_inst", // genFtn
				new String[] { "nsw", "nuw" } ), // flags
      new OpcodeCharacteristics( 
				"lshr",  // name
				"shift", // type
				"generate_arith_inst", // genFtn
				new String[] { "exact" } ), // flags
      new OpcodeCharacteristics( 
				"ashr",  // name
				"shift", // type
				"generate_arith_inst", // genFtn
				new String[] { "exact" } ), // flags
      new OpcodeCharacteristics( 
				"storeload",  // name
				"storeload", // type
				"generate_storeload_inst", // genFtn
				new String[] { } ), // flags
      new OpcodeCharacteristics( 
				"call",  // name
				"call", // type
				"generate_call_inst", // genFtn
				new String[] { } ), // flags
      // template is 5 lines long
      //new OpcodeCharacteristics( 
      //			  "call",  // name
      //			  "call", // type
      //			  "generate_call_inst", // genFtn
      //			  new String[] { } ), // flags
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

      System.out.print( "starting instruction.generate_one_inst(~) \n" );;

      OpcodeCharacteristics opcode= null;
      {
	 int opcodeIdx= randomizer.nextInt(opcodes.length);
         opcode= opcodes[opcodeIdx];
	 if ( Main.debugFlag )  {
	    System.out.print( "selected opcode=\""+ opcode.name+ "\"\n" );
	 }
      }

      basicBlock.carpIfRegNumReset( $basicBlock,
	    "(from beginning of instruction.generate_one_inst)\n" );;

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
	  throw new Error( Main.PROGRAM_NAME+  
	       ": don't recognize opcode type for \""+ opcode.name+ "\", \""+ 
	       opcode.type+ 
	       "\"(message 2015apr23_111740)\n" );
      }

   basicBlock.carpIfRegNumReset( basicBlock,
	 "last instruction=\""+ opcode.name+ "\"\n" );;
   System.out.print( "stopping instruction.generate_one_inst(~) \n" );;
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

      String addrName= AddrName.get("var");
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
	    $storeFlags+ $basicBlock.currentType().getName()+ " "+
	    $srcReg+ ", "+ 
	    basicBlock.currentType().getName()+ "* "+ addrName+ "\n" );
      insts.append( "  "+ destReg+ "= load "+
	    basicBlock.currentType().getName()+
	    "* "+ addrName+ " \n" );
      /* TODO2: consider adding an "align 4" or similar to the load and 
	 store instructions, possibly as an optional flag.
      */

   basicBlock.reportType( destReg, basicBlock.currentType() );
   
   basicBlock.carpIfRegNumReset( basicBlock,
	 "at end of instruction.generate_storeload_insts("+ opcode.name+ 
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
	 flags.append( opcode.flags[ii]+ " " );
      }
   } 

   String operand1= basicBlock.getPrevRegName(1);

   // operand2 is a constant
   String operand2= basicBlock.currentType().getRandShiftVal();
   
   StringBuffer inst= new StringBuffer("");
   inst.append( "  "+ destReg+ "= "+ opcode.name+ " "+
	 flags+ basicBlock.currentType().getName()+ " "+
	 operand1+ ", "+ operand2+ "\n" );

   basicBlock.reportType( destReg, basicBlock.currentType() );
   $basicBlock.carpIfRegNumReset( basicBlock,
	 "at end of instruction.generate_shift_insts("+ opcode.name+ ")\n" );;
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
	 // operand will be a constant
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
	    "at end of instruction.generate_arith_insts("+ opcode.name+ 
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
	    basicBlock.currentType().getName()+ " " + 
	    basicBlock.currentType().getRandVal() + ", 0 \n";
      basicBlock.reportType( regName, basicBlock.currentType() );
      basicBlock.carpIfRegNumReset( basicBlock,
	    "at end of instruction.generate_const_insts()\n" );;
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

      System.out.print( "starting instruction.generate_call_inst(~)\n" );;

      if ( ! "call".equals(opcode.name) )  {
	 throw new Error( Main.PROGRAM_NAME+
			  ": internal error 2015apr9_162208, code=\""+ 
			  opcode.name+ "\". \n" );
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // set up everything but the arguments
      String ftnName= AddrName.get("ftn"); // TODO: finish fixing this
					  // CAS: what is there still to fix?
      System.out.print( "Instruction.generateCallInst(~) generating ftn \""+ 
			ftnName+ "\"\n" );;
      TypeInteger retType= basicBlock.currentType();

      int numSteps= basicBlock.numRemainingSteps()/ 3;
      if ( numSteps < 2 )  {
	 numSteps= 2;
	 /* TODO2: consider returning a NO_OP in this case, and make the
	    caller then choose a different instruction.
	 */
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // set up arguments
      final int MAX_NUM_ARGS= 3;

      // should yield something in range 0...MAX_NUM_ARGS.
      int numArgs= randomizer.nextInt( MAX_NUM_ARGS+1 );
      // TODO: make allAboutArgVector into a simple array.
      Vector<RegWithType> allAboutArgVector= new Vector<RegWithType>(numArgs);
      TypeInteger argTypeArray[]= new TypeInteger[numArgs];

      if ( numArgs >= 1 )  {
	 RegWithType allAbout1Arg= basicBlock.getPrevRegWithType(1);
	 allAboutArgVector[0]= allAbout1Arg;
      }
      for ( int ii= 1; ii < numArgs; ii++ )  {
	 RegWithType allAbout1Arg= basicBlock.getRecentRegWithType();
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

	 if ( newFtnChunk.definitions.matches( ".*%1\\D.*%1\\D.*" ) )  {
	    throw new Error( Main.PROGRAM_NAME+
			     ": internal error 2015apr10_100654 "+
			     "(two %1s in newFtn defs)" );;
	 }
	 if ( newFtnChunk.instructions.matches( ".*%1\\D.*%1\\D.*" ) )  {
	    throw new Error( Main.PROGRAM_NAME+
			     ": internal error 2015apr10_101421 "+
			     "(two %1s in newFtn insts)" );;
	 }

	 definitions.append( newFtnChunk.definitions ); 
	 definitions.append( newFtnChunk.instructions ); 
	 if ( definitions.toString().matches("%1\\D.*%1\\D") )  {
	    throw new Error( Main.PROGRAM_NAME+ 
			     ": internal error 2015apr10_100943 "+
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
	 "at end of instruction.generate_call_insts("+ opcode.name+ ")\n" );;
      System.out.print( "stopping instruction.generate_call_inst(~)\n" );;
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

