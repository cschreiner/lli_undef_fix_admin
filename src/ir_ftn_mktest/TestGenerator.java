/* 
 * Program Name: lli_undef_fix
 *
 * File Name: TestGenerator.java
 *
 * File Description: class TestGenerator
 *	(list here the package-scope classes in this file)
 *
 * lli_undef_fix was written by Christian A. Schreiner at University of
 * Utah.  Copyright (C) 2015-2015 by University of Utah.  All rights
 * reserved.  You may use, examine, or modify this file only in accordance
 * with the GNU Public License, or, alternately, by special written
 * arrangement with the author.  This file comes with no warranties.  If
 * you use it and something breaks, you are solely responsible for
 * cleaning up afterwards.
 */

/*.. Configuration Management Information:
   * 
   * $Source: /net/home/cas/templates/auto_uut/RCS/cas_tmpl.java,v $
   * $File: $
   * $Author: cas $
   * $Date: 2015/05/20 07:15:19 $
   * $Revision: 1.4 $
   * 
   */

/* ****************************************************************************
   *   package
   * **************************************************************************
   */
package ir_ftn_mktest;

/* ****************************************************************************
 *   imports
 * ****************************************************************************
 */
//import java.util.*;

import java.io.*;

import IRTxtLib.*;

// ****************************************************************************
// File's primary class: TestGenerator
// ****************************************************************************
/** creates a test program for one function
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class TestGenerator 
{

/* ############################################################################
 * primary class' direct contents
 * ############################################################################
 */
   
   /* =========================================================================
    * class variables
    * =========================================================================
    */

   /* =========================================================================
    * instance variables
    * =========================================================================
    */

   FtnParts ftnParts;
   int numCalls;

   /* =========================================================================
    * constructors
    * =========================================================================
    */

   // -------------------------------------------------------------------------
   // TestGenerator()
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
   private TestGenerator()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for TestGenerator." );
      System.exit(-127);
   }}

   // -------------------------------------------------------------------------
   // TestGenerator( FtnParts, int )
   // -------------------------------------------------------------------------
   /** commonly used constructor
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
    * @param ftnParts - a FtnParts instance with info about the function to
    *	test
    * @param numCalls - the number of times to call the function
    *
    * @return - n/a (it's a constructor!)
    *
    * @throws
    */
   public TestGenerator( FtnParts ftnParts, int numCalls )
   {{
      this.ftnParts= ftnParts;
      this.numCalls= numCalls;
   }}


   /* =========================================================================
    * methods
    * =========================================================================
    */

   // ------------------------------------------------------------------------
   // generate( String filename )
   // ------------------------------------------------------------------------
   /**  generates the tests in the specified file
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
   public void generate( String filename )
   {{
      String code= generateCode();

      if ( Main.arg_verbosity >= 1 ) {
         System.out.println( "code: <<EOF" );
         System.out.println( code );
         System.out.println( "EOF" );
      }

      // TODO: add code here to write to the file
      try {
	 FileWriter writer= new FileWriter( filename );
	 writer.write( code );
	 writer.close();
      } catch ( IOException ex ) {
	 System.err.println( 
	       "Problem while writing the test program to its file, " );
	 System.err.println( "\t"+ "file=\""+ filename+ "\", \n" );
	 System.err.println( "\t"+ ex.getMessage()+ ".\n" );
      }

      return;
   }}

   // ------------------------------------------------------------------------
   // generateCode()
   // ------------------------------------------------------------------------
   /**  generates the test code
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * <li> TODO2: consider having the generated program write out an "end 
    *	of output" message before it terminates.
    *
    * <li> No inputs.
    * </ul>
    * 
    * @return a String containing the generated LLVM IR code
    *
    * @throws 
    *
    * A example of a program generated by this function reads:
..............................................................................

@printf_call_num_st = private unnamed_addr constant [13 x i8] c"call '%d': \0A\00"
@printf_poison_free_st = private unnamed_addr constant [37 x i8] c"this prints if poison-free: '0x%x' \0A\00"

; external declaration of the printf(~) function
declare i32 @printf(i8* nocapture readonly, ...)


; the function-under-test:
define i2 @func3000(i2, i1, i4, i2, i1, i4, i2, i1, i4, i2, i1, i4) {
  %13 = select i1 %1, i4 -8, i4 %2
  %14 = select i1 %1, i4 %13, i4 -7
  %15 = trunc i4 %14 to i2
  ret i2 %15
}
; ModuleID = 'opt-fuzz'

define i32 @main() {
  ; %convert [? x i8]* to i8*
  %printf_call_num_st_i8 = getelementptr [13 x i8]* @printf_call_num_st, i64 0, i64 0
  %printf_poison_free_st_i8 = getelementptr [37 x i8]* @printf_poison_free_st, i64 0, i64 0
  %1= add i32 0, 0

  ; call 0
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 0 )
  %result0= call i2 @func3000(i2 -2, i1 -1, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0  )
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result0 )

  ; call 1
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 1 )
  %result1= call i2 @func3000(i2 -2, i1 -1, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0  )
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result1 )

  ret i32 0
}

..............................................................................
    */
   private String generateCode()
   {{
      StringBuffer retVal= new StringBuffer("");
      SeededRandom randomizer= new SeededRandom();

      retVal.append( ";; Warning: AUTOMATICALLY GENERATED CODE \n" );
      retVal.append( ";; !! -- Do _NOT_ hand edit! -- !! \n" );
      retVal.append( ";; Generator: "+ Main.PROGRAM_NAME+ "\n" );
      retVal.append( ";; Seed: "+ randomizer.getSeed()+ "\n" );
      retVal.append( "\n\n" );

      // generate declarations that all programs need
      retVal.append( "@printf_call_num_st = private unnamed_addr constant "+ 
	    "[13 x i8] c\"call '%d': \\0A\\00\"\n" );
      retVal.append( "@printf_poison_free_st = private unnamed_addr constant "+
	    "[37 x i8] c\"this prints if poison-free: '0x%x' \\0A\\00\"" );

      retVal.append( "\n" );
		    
      retVal.append( "; external declaration of the printf(~) function \n" );

      retVal.append( "declare i32 @printf(i8* nocapture readonly, ...)\n" );
      retVal.append( "\n\n" );

      // insert the function-under-test
      retVal.append( "; the function-under-test: \n" );
      retVal.append( ftnParts.getFtnSt() );
      retVal.append( "\n\n" );

      // main function
      final String indent= "  ";
      final String mainReturnTypeName= "i32";
      retVal.append( "define "+ mainReturnTypeName+ " @main() { \n" );
      retVal.append( indent+ "; %convert [? x i8]* to i8* \n" );
      retVal.append( indent+ "%printf_call_num_st_i8 = getelementptr "+ 
	    "[13 x i8]* @printf_call_num_st, i64 0, i64 0 \n" );
      retVal.append( indent+ "%printf_poison_free_st_i8 = getelementptr "+ 
	    "[37 x i8]* @printf_poison_free_st, i64 0, i64 0\n" );
      retVal.append( indent+ "%1= add i32 0, 0 \n" );

      for ( int ii= 0; ii < numCalls; ii++ )  {
         retVal.append( "\n" );
         retVal.append( indent+ "; call "+ ii+ "\n" );
	 retVal.append( indent+ "call i32 (i8*, ...)* @printf("+ 
	       "i8* %printf_call_num_st_i8, i32 "+ ii+ " )\n" );
         String resultReg= "%result"+ ii;
	 retVal.append( indent+
			resultReg+ "= call "+ ftnParts.getRetType().getName()+ 
			" "+ ftnParts.getName()+ "(" );
	 for( int arg= 0; arg < ftnParts.getArgs().length; arg++ ) {
	    retVal.append( ftnParts.getArgs()[arg].type.getName()+ " " );
	    if ( ftnParts.getArgs()[arg].isUsed ) {
	       retVal.append( ftnParts.getArgs()[arg].type.getRandVal() );
	    } else {
	       retVal.append( "0" );
	    }
	    if ( (arg+1) < ftnParts.getArgs().length ) {
	       // all args are followed by a comma, except for the last one.
	       retVal.append( "," );
	    }
	    retVal.append( " " );
	 }
	 retVal.append( " ) \n" );
	 retVal.append( indent+ 
			"call i32 (i8*, ...)* @printf("+
			"i8* %printf_poison_free_st_i8, "+ 
			ftnParts.getRetType().getName()+ " "+ resultReg+ 
			" )\n" );
      }
      retVal.append( "\n\n" );

      retVal.append( indent+ "ret "+ mainReturnTypeName+ " 0 \n" );
      retVal.append( "} \n" );
      retVal.append( "\n\n" );
      retVal.append( "; end of file \n\n" );

      // clean up and return
      return retVal.toString();
   }}



/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class TestGenerator


/* ****************************************************************************
 * templates 
 * ****************************************************************************
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
 * ****************************************************************************
 */

