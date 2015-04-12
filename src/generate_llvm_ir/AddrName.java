/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: AddrName.java
   *
   * File Description: class AddrName
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

import java.util.HashMap;
import java.util.Random;

// ****************************************************************************
// File's primary class: AddrName
// ****************************************************************************
/*** 
   * <ul>
   * <li> Description: class to create and manage address names
   *
   * <li> Algorithm: each address name is unique, and is a random sequence 
   *	of consonant-vowel-consonant groups, so it is somewhat pronouncable.
   * </ul>
   */
public class AddrName 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */
   private static HashMap<String,Object> addrsUsed= 
	 new HashMap<String,Object>();
   private static Random rand= new Random();

   /* =========================================================================
      * instance variables
      * =======================================================================
      */


   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // AddrName()
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
   private AddrName()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for AddrName." );
      System.exit(-127);
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // get()
   // ------------------------------------------------------------------------
   /***  generates a new unique address name.
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
      * @param prefix - the prefix to put in front of the unique address name
      * 
      * @return - an string containing an address of form "@prefix_xxxxxx", 
      *   where prefix is the specified prefix, and xxxxxx is randomly
      *   generated. The xxxxxx may have more characters than shown.
      *
      * @throws 
      */
   public static String get(String prefix )
   {{
      // TODO: this could use a serious efficiency improvement
      StringBuffer retVal= new StringBuffer("@");
      final String CONSONANTS= "bcdfghjklmnpqrstvwxz"; 
      final String VOWELS= "aeiou";
      StringBuffer core= new StringBuffer("");

      retVal.append(prefix);


      for ( int safetyCounter= 0; safetyCounter < 1000; safetyCounter++ ) {
	 for ( int ii= 0; ii < 2; ii++ ) {
	    core.append( getLetter(CONSONANTS) );
	    core.append( getLetter(VOWELS) );
	    core.append( getLetter(CONSONANTS) );
	 }
	 if ( !addrsUsed.containsKey(core) ) {
            retVal.append(core);
            return retVal.toString();
	 }
      }
      // we couldn't generate a unique sequence in a reasonable number of tries
      System.err.print( generate_llvm_ir.Main.PROGRAM_NAME +
			": internal error 2015apr09_220001. \n" );
      System.exit(-1);
      return null;
   }}

   // ------------------------------------------------------------------------
   // getLetter()
   // ------------------------------------------------------------------------
   /***  
    * @param letters - a string containing the letters to choose from
    * @return - a randomly chosen letter from the given string
    *
    * @throws 
    */
   private static char getLetter( String letters )
   {{
       Random rand= new Random();
      int length= letters.length();
      int ii= rand.nextInt(length);
      char retVal= letters.charAt(ii);
      return retVal;
   }}

/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class AddrName


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

