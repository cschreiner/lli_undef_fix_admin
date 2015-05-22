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
    * <li> No inputs.
    * </ul>
    * 
    * @return a String containing the generated LLVM IR code
    *
    * @throws 
    */
   private String generateCode()
   {{
      StringBuffer retVal= new StringBuffer("");
      SeededRandom randomizer= new SeededRandom();

      retVal.append( ";; Warning: AUTOMATICALLY GENERATED CODE \n" );
      retVal.append( ";; !! Do _NOT_ hand edit!! \n" );
      retVal.append( ";; Generator: "+ Main.PROGRAM_NAME+ "\n" );
      retVal.append( ";; Seed: "+ randomizer.getSeed()+ "\n" );
      retVal.append( "\n\n" );

      // generate declarations that all programs need
      retVal.append( "@printf_st = private unnamed_addr constant [37 x i8] "+
		     "c\"this prints if poison-free: '0x%x' \\0A\\00\"\n" );
      retVal.append( "; external declaration of the printf(~) function \n" );

      retVal.append( "declare i32 @printf(i8* nocapture readonly, ...)\n" );
      retVal.append( "\n\n" );

      // main function
      final String indent= "  ";
      final String mainReturnTypeName= "i32";
      retVal.append( "define "+ mainReturnTypeName+ " @main() { \n" );
      retVal.append( indent+ "; %convert [? x i8]* to i8* \n" );
      retVal.append( indent+ "%printf_st_i8 = "+ 
		     "getelementptr [37 x i8]* @printf_st, i64 0, i64 0 \n" );
      retVal.append( indent+ "%1= add 0, 0 \n" );

      for ( int ii= 0; ii < numCalls; ii++ )  {
         retVal.append( "\n" );
         retVal.append( indent+ "; call "+ ii+ "\n" );
         String resultReg= "%result"+ ii;
	 retVal.append( indent+
			resultReg+ "= call "+ ftnParts.getRetType().getName()+ 
			" "+ ftnParts.getName()+ "(" );
	 for( int arg= 0; arg < ftnParts.getArgs().length; arg++ ) {
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
			"call i32 (i8*, ...)* @printf(i8* %printf_st_i8, "+ 
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

