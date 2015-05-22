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
   // TestGenerator( FtnParts )
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
    *
    * @return - n/a (it's a constructor!)
    *
    * @throws
    */
   public TestGenerator( FtnParts ftnParts )
   {{
      this.ftnParts= ftnParts;
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
      retVal.append( ";; seed= "+ randomizer.getSeed() );
      retVal.append( "\n\n" );
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

