/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: RegWithType.java
   *
   * File Description: class RegWithType
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
package IRTxtLib;

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

import IRTxtLib.*;

// ****************************************************************************
// File's primary class: RegWithType
// ****************************************************************************
/** stores a register's name with its data type
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class RegWithType 
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
   public String regName;
   public TypeInteger type;

   /* optional metadata about the register */
   public boolean isUsed;

   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // RegWithType()
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
   private RegWithType()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for RegWithType." );
      System.exit(-127);
   }}

   // -------------------------------------------------------------------------
   // RegWithType()
   // -------------------------------------------------------------------------
   /*** commonly used constructor
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
      * @param name - the name of the register
      * @param type - the data type of the register
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   public RegWithType( String name, TypeInteger type )
   {{
      this.regName= name;
      this.type= type;

      if ( name == null || "".equals(name) )  {;;
         throw new Error( IRTxtLib.programName+ 
	       ": found a null register at 2015apr9_194003.\n" );
      }
      if ( type == null )  {
         throw new Error( IRTxtLib.programName+ 
	       ": internal warning 2015apr09_170526 (null TypeInteger)\n" );
      }

      /* we assume that variables are used until we establish otherwise */
      isUsed= true;
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // toString()
   // ------------------------------------------------------------------------
   /**  
    * <ul>
    * <li> Detailed Description: 
    *	The string is one line long, and does not contain any newlines.
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * <li> No inputs.
    * </ul>
    * 
    * @return - a string representation
    *
    * @throws 
    */
   public String toString()
   {{
      StringBuffer retVal= new StringBuffer("");
      retVal.append( "reg "+ regName+ ": " );
      retVal.append( "type="+ type.getName()+ "," );
      retVal.append( "flags=" );
      retVal.append( isUsed ? "U" : "u" );
      retVal.append( "." );
   }}


/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class RegWithType


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

