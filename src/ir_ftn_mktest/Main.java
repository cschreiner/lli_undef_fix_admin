/* 
 * Program Name: lli_undef_fix
 *
 * File Name: Main.java
 *
 * File Description: class Main
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

import java.io.*;

import IRTxtLib.*;

//import java.util.*;
//import java.applet.Applet;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.Color.*;
//import java.awt.geom.*;


// ****************************************************************************
// File's primary class: Main
// ****************************************************************************
/** provides a main function for the program
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class Main 
{

/* ############################################################################
 * primary class' direct contents
 * ############################################################################
 */
   
   /* =========================================================================
    * class variables
    * =========================================================================
    */
   public final static String PROGRAM_NAME= "ir_ftn_mktest";
   static String arg_ir_filename= "";
   static int arg_verbosity= 0;
   static String arg_outDirname= "";
   static int arg_numCalls= 5;

   /* =========================================================================
    * instance variables
    * =========================================================================
    */


   /* =========================================================================
    * constructors
    * =========================================================================
    */


   // -------------------------------------------------------------------------
   // Main()
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
   private Main()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for Main." );
      System.exit(-127);
   }}


   /* =========================================================================
    * methods
    * =========================================================================
    */

   // ------------------------------------------------------------------------
   // main()
   // ------------------------------------------------------------------------
   /**  starts the program
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
    * @param argv - an array of strings with the command line arguments
    * 
    * @return - 
    *
    * @throws 
    */
   public static void main( String args[] )
   {{
      final int EXPECTED_NUM_ARGS= 4;

      /* ..............................................................
	 set up command line arguments
       */

      if ( args.length != EXPECTED_NUM_ARGS ) {
         System.err.print( "expected "+ EXPECTED_NUM_ARGS+ 
			   " arguments, but found "+ args.length+ ".\n" );
	 System.exit( -1 );
      }

      arg_verbosity= Integer.parseInt( args[0] );
      arg_ir_filename= args[1];
      arg_outDirname= args[2];
      arg_numCalls= Integer.parseInt( args[3] );

      /* ..............................................................
	 set up globals
       */
      IRTxtLib.programName= PROGRAM_NAME;
      IRTxtLib.arg_verbosity= arg_verbosity;

      /* ..............................................................
       */
      checkArgsAndSetUp();
      startWork();
   }}

   // ------------------------------------------------------------------------
   // checkArgsAndSetUp()
   // ------------------------------------------------------------------------
   /**  checks command line args
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
   private static void checkArgsAndSetUp()
   {{
      File outDir= new File( arg_outDirname );

      if ( outDir.exists() ) {
	 if ( !outDir.isDirectory() ) {
	    System.err.println( PROGRAM_NAME+ 
				": output directory is not a directory; ");  
	    System.err.println( "\t"+ "dir=\""+ arg_outDirname+ "\", " );
	    System.err.println( "\t"+ "please specify an empty directory." );
	    System.exit(-1);
	 }
	 if ( outDir.list().length > 0 ) {
	    System.err.println( PROGRAM_NAME+ 
				": output directory is not empty; ");  
	    System.err.println( "\t"+ "dir=\""+ arg_outDirname+ "\", " );
	    System.err.println( "\t"+ "please specify an empty directory." );
	    System.exit(-1);
	 }
      } else {
	 outDir.mkdir();
      }
   }}


   // ------------------------------------------------------------------------
   // startWork()
   // ------------------------------------------------------------------------
   /**  starts the main work of the program
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
    * @throws 
    */
   private static void startWork()
   {{
      String ftnTxt= null;
      FileToFtnParser parser= new FileToFtnParser( arg_ir_filename );
      String hashes39= "#######################################";

      while( true )  {
	 ftnTxt= parser.parseFtn();
	 if ( ftnTxt == null ) { break; }
	 if ( arg_verbosity > 1 )  {
	    System.out.println ( hashes39+ hashes39 );
	    System.out.println ( "found function: <<EOF " );
	    System.out.println ( ftnTxt );
	    System.out.println ( "EOF" );
	 }

	 FtnParts ftnParts= new FtnParts( ftnTxt );

	 if ( arg_verbosity > 0 ) {
            System.out.println( "---" );
	    System.out.println( "function: " );
	    System.out.println( ftnParts.toString() );
	 }

	 TestGenerator generator= new TestGenerator( ftnParts, arg_numCalls );
	 String outFilename= arg_outDirname+ File.separator+ 
	       ftnParts.getName()+ ".ll";
	 generator.generate( outFilename );
      }

   }}


/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class Main


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

