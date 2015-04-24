/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: Function.java
   *
   * File Description: class Function
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
// File's primary class: Function
// ****************************************************************************
/*** code to generate a function(s)
 *
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class Function 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */

   /* =========================================================================
      * instance variables
      * =======================================================================
      */


   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // Function()
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
   private Function()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for Function." );
      System.exit(-127);
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   /* TODO3: consider adding a generate() method that replaces as many of
    * these parameters as possible with a BasicBlockInitializer instance.
    */

   // ------------------------------------------------------------------------
   // generate()
   // ------------------------------------------------------------------------
   /***  static method to generate the code for one function
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
      * @param retType - the function's return type
      *
      *	@param name - the function's name
      *
      * @param args - an array of TypeInteger instances giving the function's 
      *		arguments, in order.
      *
      * @param numSteps - the number of steps (instructions) the function 
      *		should have.  (Additional instructions may be needed for 
      *		semantic housekeeping.)
      *
      * @param printRetVal - should the function end with code to print its 
      *		return value.
      *
      * @param startPoison - should the function generate a poison value as 
      *		one of its first instructions.
      * 
      * @return - a code chunk with the instructions and the prerequisite 
      *		definitions.
      *
      * @throws 
      */
   public static TypeInteger generate( TypeInteger retType,
			  String name,
			  TypeInteger[] args,
			  int numSteps,
			  boolean printRetVal,
			  boolean startPoison )
   {{
      System.out.print( "starting function::generate(~)\n" );;

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // check arguments

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // set up and report arguments

      TypeInteger startType= retType;
      if ( args.length >= 1 ) {
	 // if there are arguments, start with their type
	 startType= args[ 0 ];
      }
   
      System.out.print( "creating a basic block for function \"$name\"\n" );;

      BasicBlockSeqInitializer bbi();
      bbi.startPoison= startPoison;
      bbi.numSteps= numSteps;
      bbi.startType= startType;
      bbi.stopType= retType;
      bbi.ftnName= name;

      BasicBlockSeq basicBlock( null, bbi );

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // generate function heading
      StringBuffer definitions= new StringBuffer("");
      StringBuffer instructions= new StringBuffer("");

      instructions.append( "define "+ retType.getName()+ ' '+ $name+ '( ' ); 

      {
	 System.out.print( "arg_listref=>[" );  
	 for ( int ii= 0; ii < args.length; ii++ ) {
	    System.out.print( "\""+ args[ii]+ "\"" );
	    if ( ii < (args.length-1) ) {
	       System.out.print( ", " );
	    }
	 }
	 System.out.print( " ]\n" );
	 System.out.print( "\t"+ "length="+ args.length+ "\n");
      };;

      for ( int ii= 0; ii < args.length; ii++ ) {
	 String argName= "%arg"+ (ii+ 1);
	 System.out.print( "   "+ ii+ "\n" );;
	 instructions.append( args[ii].getName()+ " "+ argName );
	 basicBlock.registerArg( argName, args[ii] );
	 if ( ii < (args.length-1) ) {
	    instructions.append( ", " );
	 }
      }

      instructions.append( " ) { \n" ); 
      instructions.append( "  ; \%convert [? x i8]* to i8* \n" );
      instructions.append( 
	    "  \%printf_st_i8 = getelementptr [37 x i8]* \@printf_st, " . 
	    "i64 0, i64 0\n" );
      instructions.append( "\n" );

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
      // generate instructions
      {
	 CodeChunk cc= basicBlock.generate();
	 if ( cc.instructions.matches( ".*%1\D.*%1\D.*" ) ) {
	    throw new Error( Main.PROGRAM_NAME+ 
			     ": internal error 2015apr10_101817 " .
			     "(two %1s in cc.instructions)" );;
	 }
	 if ( cc.definitions.matches( ".*%1\D.*%1\D.*" ) ) {
	    throw new Error( Main.PROGRAM_NAME+ 
			     ": internal error 2015apr10_101506 " .
			     "(two %1s in cc.definitions)" );;
	 }
	 definitions.append( cc.definitions );
	 instructions.append( cc.instructions );
      }
      if ( instructions.matches( ".*%1\D.*%1\D.*" ) ) {
      throw new error( Main.PROGRAM_NAME+ 
		       "(two %1s in definitions)" );;
   }
   if ( instructions.matches( ".*%1\D.*%1\D.*" ) ) {
      throw new Error( Main.PROGRAM_NAME+ 
		       ": internal error 2015apr10_100654 "+
		       "(two %1s in instructions)" );;
   }

   // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   // generate function ending

   instructions.append( "\n" );

   if ( printRetVal ) {
      instructions.append( 
	    "  call i32 (i8*, ...)* \@printf(i8* \%printf_st_i8, "+
	    basicBlock.currentType().getName()+ " "+  
	    basicBlock.getPrevRegName()+ ")\n" );
   }

   instructions+ "\n";
   instructions+ "  ; clean up and return \n";
   String retRegister= basicBlock.getPrevRegName(1);
   TypeInteger retRegisterType= basicBlock.getRegType(retRegister);
   if ( basicBlock.getStopType().compareTo(retRegisterType) != 0 )  {
      // The return value should be of the intended return type.
      throw new Error( Main.PROGRAM_NAME+ 
		       ": internal error 2015apr09_235615.\n" );
   }

   instructions.append( "  ret "+ retRegisterType+ " "+ retRegister+ " \n" );
   instructions.append( "} \n" );

   // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
   // clean up and return
   System.out.print( "stopping function::generate(~)\n" );;
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
} // end class Function


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




