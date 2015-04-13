/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: CodeChunk.java
   *
   * File Description: class CodeChunk
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
package <$package_name>;

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
// File's primary class: CodeChunk
// ****************************************************************************
/*** contains a chunk of code and associated definitions
   * <ul>
   * <li> Description: All fields are public.  This is intended for
   * other classes to use as a simplistic return type.
   * 
   *
   * </ul>
   */
public class CodeChunk 
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

   /** String containing the definitions related to the code */
   public String definitions;

   /** String containing the main code */
   public String instructions;


   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // CodeChunk()
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
   public CodeChunk()
   {{
      //System.err.println ( "Internal error: "+
      //	    "unexpected call to default constructor for CodeChunk." );
      //System.exit(-127);
      definitions= null;
      instructons= null;
   }}
	
   // -------------------------------------------------------------------------
   // CodeChunk( String, String )
   // -------------------------------------------------------------------------
   /*** alternate constructor
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
      * @param dd - the definitions (copied to the definitions field)
      *
      * @param ii - the main code (copied to the instructons field)
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   public CodeChunk( String dd, String ii )
   {{
      //System.err.println ( "Internal error: "+
      //	    "unexpected call to default constructor for CodeChunk." );
      //System.exit(-127);
      definitions= dd;
      instructons= ii;
   }}
	

   /* =========================================================================
      * methods
      * =======================================================================
      */


/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class CodeChunk


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

