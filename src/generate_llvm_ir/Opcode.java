/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: Opcode.java
   *
   * File Description: class Opcode
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
// File's primary class: Opcode
// ****************************************************************************
/*** holds information about an opcode
   * <ul>
   * <li> Description: 
   *
   * <li> Algorithm: 
   * </ul>
   */
public class Opcode 
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

   /* see constructor comments for info on each of these fields */
   String name; 
   String type; 
   String genFtn; 
   String[] flags;

   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // Opcode()
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
   private Opcode()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for Opcode." );
      System.exit(-127);
   }}

   // ------------------------------------------------------------------------
   // Opcode()
   // ------------------------------------------------------------------------
   /**  commonly used constructor
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
    * @param name - the name of the opcode e.g. "add"
    * @param type - the category the opcode is in e.g. 'arith'
    * @param genFtn - the name of the function to call to generate the
    * 		instruction
    * 		TODO2: change this to the name of some instance whose
    * 		interface has a standard generate(~) method for this
    * 		instruction.
    * @param flags - an array of flags that the instruction may use. 
    *
    * @throws 
    */
   public Opcode( String name, String type, String genFtn, String[] flags )
   {{
      this.name= name;
      this.type= type;
      this.genFtn= genFtn;
      this.flags= flags;
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
} // end class Opcode


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

