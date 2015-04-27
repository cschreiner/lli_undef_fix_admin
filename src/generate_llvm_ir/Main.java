/*.. 
   * Project Name: lli_undef_fix
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
import java.io.*;
//import java.util.*;
//import java.applet.Applet;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.Color.*;
//import java.awt.geom.*;

import generate_llvm_ir.*;

// ****************************************************************************
// File's primary class: Main
// ****************************************************************************
/*** starts the program, parses command line args, and invokes other classes
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
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */
   public static final String PROGRAM_NAME= "generate_llvm_ir";
   public static final boolean debugFlag= false;

   /* Signifies that the bitwidth is uninialized and should be chosen at 
      random.
   */
   // -54172 was (also) chosen at random, but it must be negative.
   private static final int BITWIDTH_NULL= -54172; 

   /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      command line arguments 
   */
   public static String argOutFilename= null;

   // causes choosing a random value, later.
   public static int argBitWidth= BITWIDTH_NULL; 

   public static boolean argStartPoison= false;

   public static int argNumSteps= 10;

   /* =========================================================================
      * instance variables
      * =======================================================================
      */


   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // Main()
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
   private Main()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for Main." );
      System.exit(-127);
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // main()
   // ------------------------------------------------------------------------
   /***  starts the program
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
      * @param argv - the supplied command line arguments

      * @return - 
      *
      * @throws 
      */
   public static void main( String[] argv )
   {{
      final int expectedNumArgs= 4;
      if ( argv.length != 4 )  {
	 throw new Error( Main.PROGRAM_NAME+ ": expected exactly "+ 
			  expectedNumArgs+ " arguments. \n" ); 
      }
      /* Argument parsing is intended to mirror this PERL code:
	 "bitwidth=i" => \$arg_bitwidth,
         "start-poison" => \$arg_start_poison,
         "num-steps=i" => \$arg_num_steps,
      */
      argBitWidth= Integer.parseInt( argv[0] );
      argStartPoison= Boolean.parseBoolean( argv[1] );
      argNumSteps= Integer.parseInt( argv[2] );
      argOutFilename= argv[3];

      // .....................................................................
      //  set up constants

      // .....................................................................
      //   set up for this run


      // .....................................................................
      //   generate instructions

      TypeInteger type= null;
      if ( argBitWidth == BITWIDTH_NULL ) {
	 type= new TypeInteger();
      } else {
	 type= new TypeInteger( argBitWidth );
      }

      CodeChunk cc= Function.generate( type, "@main", new TypeInteger[]{}, 
	    argNumSteps, true, argStartPoison );

      // .....................................................................
      // write it out

      //   set up output file
      PrintWriter writer= null;
      try {
	 writer= new PrintWriter( argOutFilename );
      } catch ( FileNotFoundException ex) {
	 System.err.print( Main.PROGRAM_NAME+ 
			   ": can't open output file for writing, \n"+
			   "\t"+ "file=\"$arg_outfile_name\",\n"+
			   "\t"+ ex.getMessage()+ ". \n" );
	 System.exit( -1 );
      }

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      //   write it out
      writer.write( "; autogenerated by "+ Main.PROGRAM_NAME+ ". \n" );
      writer.write ( "\n" );

      // print declarations all programs need
      writer.write ( "@printf_st = private unnamed_addr constant [37 x i8] "+
		     "c\"this prints if poison-free: '0x%x' \\0A\\00\"\n" );
      writer.write ( "\n" );
      writer.write( "; external declaration of the printf(~) function \n" );
      writer.write( "declare i32 @printf(i8* nocapture readonly, ...)\n" );
      writer.write( "\n" );

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      // write out generated code
      writer.write( cc.definitions );
      writer.write( "\n" );
      writer.write( cc.instructions );
      writer.write( "\n" );

      // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      //   write out end of program and close
      writer.write( "\n"+ "; end of program \n"+ "\n" );
      writer.close();
      // die $main::scriptname . ": can't close output file after writing, \n".
      //  "\t" . "file=\"$arg_outfile_name\",\n" .
      //  "\t" . "$!. \n";

      // .....................................................................
      //   clean up and exit
      System.exit( 0 );
   }}



/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class Main


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

