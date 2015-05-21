/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: TypeInteger.java
   *
   * File Description: class TypeInteger
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
import java.util.*;
//import java.applet.Applet;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.Color.*;
//import java.awt.geom.*;

import IRTxtLib.*;

// ****************************************************************************
// File's primary class: TypeInteger
// ****************************************************************************
/** Holds information on a bitwidth, such as its maximum and minimum
 *	integer values, a random number generator, and so forth.
 *
 * TODO3: adjust this (and the rest of this class) to handle 64+-bit ints.
 * This will be non-trivial under java, as LLVM IR integers are unsigned, and
 * a java signed long can only get up to (2**63-1).
 * 
 * <ul>
 * <li> Detailed Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class TypeInteger 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
    * class variables
    * =========================================================================
    */
   public static final int MAX_WIDTH= 32;
   public static final int MIN_WIDTH= 1;
   public static final int PREFERRED_MIN_WIDTH= 6;

   public static final TypeInteger NULL= constructNull();

   /* =========================================================================
    * instance variables
    * =========================================================================
    */
   int bitWidth;
   long maxVal, minVal;
   String name;

   /* =========================================================================
    * constructors
    * =========================================================================
    */

   // -------------------------------------------------------------------------
   // TypeInteger()
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
      * <li> No inputs.  The bitWidth is chosen at random.
      * </ul>
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   public TypeInteger()
   {{
      //System.err.println ( "Internal error: "+
      //   "unexpected call to default constructor for TypeInteger." );
      //System.exit(-127);

      bitWidth=SeededRandom.x.nextInt(MAX_WIDTH- PREFERRED_MIN_WIDTH)+
	    PREFERRED_MIN_WIDTH;

      constructorHelper();
   }}

   // -------------------------------------------------------------------------
   // TypeInteger(int)
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
      * @param targetWidth - the preferred bitWidth to work with.
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   public TypeInteger( int targetWidth )
   {{
      bitWidth= targetWidth;
      constructorHelper();
   }}

   // -------------------------------------------------------------------------
   // TypeInteger(String)
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
      * @param name - the official LLVM name for the integer type 
      *		(i.e. i followed by the bitwidth)
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   public TypeInteger( String name )
   {{
      if ( !name.matches("i[0-9]+") )  {
	 throw new Error( IRTxtLib.programName+ 
	       ": internal error 2015amay21_092612, "+
	       "unrecognized type name \""+ name+ "\". \n" );
      }

      bitWidth= Integer.parseInt( name.substring(1) );
      constructorHelper();
   }}

   // ------------------------------------------------------------------------
   // constructNull()
   // ------------------------------------------------------------------------
   /** factory method to create the NULL instance 
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
    * @return - a NULL instance
    *
    * @throws 
    */
   private static TypeInteger constructNull()
   {{
      TypeInteger retVal= new TypeInteger();

      /* set these to nonsensical values */
      retVal.bitWidth= -1;
      retVal.maxVal= -1;
      retVal.minVal= -1;
      retVal.name= "null_TypeInteger";

      return retVal;
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // constructorHelper()
   // ------------------------------------------------------------------------
   /**  initializes fields common to most (all) constructors
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
   private void constructorHelper()
   {{
      // check the bitwidth
      if ( (bitWidth < MIN_WIDTH) || (bitWidth > MAX_WIDTH) )  {
	 throw new Error( IRTxtLib.programName+ 
	       ": internal error 2015apr24_122941, bitWidth out of range. \n" );
      }

      maxVal= 1;
      for (int ii= 1; ii < bitWidth; ii++ )  {
	 maxVal*= 2;
      }
      maxVal-= 1;

      minVal= 0;
      name= "i"+ bitWidth;
      return;
   }}

   // ------------------------------------------------------------------------
   // getRandVal()
   // ------------------------------------------------------------------------
   /**  Gets a random value for this size of integer, using the full range 
    *	of the possible integer values.
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
    * @return - per description
    *
    * @throws 
    */
   public long getRandVal()
   {{
      return getRandVal(0);
   }}

   // ------------------------------------------------------------------------
   // getRandVal(int)
   // ------------------------------------------------------------------------
   /**  returns a random value for this size of integer
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
    * @param margin - 
    *	Do not return a value within this distance of the min or max value.
	Often specified as 0.  This guarantees that the value returned can be 
	incremented or decremented by up to this amount without overflow.
    * 
    * @return - 
    *
    * @throws 
    */
   public long getRandVal( int margin )
   {{
      long range= maxVal- minVal- 2* margin;

      if ( false ) {
	 System.out.print( "max=\""+ maxVal+ "\", min=\""+ minVal+
			   "\", margin=\""+ margin+ "\"\n" );
	 System.out.print( "   range=\""+ range+ "\"\n" );
      }

      // TODO2: this is trying to implement the following.  Remove the kludge.
      // long val= randomNumGenerator.nextLong( range )+ minVal+ margin;
      long val= SeededRandom.x.nextLong(range)+ minVal+ margin;

      return val;
   }}

   // ------------------------------------------------------------------------
   // getRandShiftVal()
   // ------------------------------------------------------------------------
   /** Returns a random number from 0 to this instance's bitWidth. 
    * This is the legal range that a register may be shifted right or left.
    * 
    * <ul>
    * <li> Detailed Description: 
    *	Strange, shifting by the width of the register is sometimes allowed 
    *	and sometimes not.  For example, 
    *	   %9= lshr i13 %8, 13
    *	   %11= ashr  i13 %10, 13
    *	are ok, but
    *	   %9= lshr exact i13 %8, 13
    *	and
    *	   %11= ashr exact i13 %10, 13
    *	are not.  Even though the 'exact' flag is supposted to generate
    *	poison if 0 bits are shifted out, its presence causes
    *	execution to halt with an "overshift" error.  Officially, shifting by 
    *	the register width or larger yields an undefined result (NOT undefined 
    *	behavior). Is the fact that this is sometimes allowed and sometimes 
    *	not an error in the interpreter? TODO2: look into this.  If
    *	the result is undefined, I assume the interpreter should replaced it 
    *	with a random value of appropriate width.
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * <li> No inputs.
    * </ul>
    * 
    * @return per description
    *
    * @throws 
    */
   public int getRandShiftVal()
   {{
      int val= SeededRandom.x.nextInt( bitWidth ); 
      return val;
   }}

   // ------------------------------------------------------------------------
   // compareTo()
   // ------------------------------------------------------------------------
   /**  Compares this instance to another one.  They are equal iff LLVM IR
    *  considers a register of one instance's type to be of the same type as a
    *  variable of another instance's type.  This means that the bitWidths are
    *  equal.
    * 
    * <ul>
    * <li> Note: TODO3: if we add non-integer types in the future, add
    *	something somewhere to insure both instances being compared are
    *	integers.
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    * 
    * @param that - the other instance to compare to.
    *
    * @return 
    *   -1 if this < that
    *   0 if this == that
    *   +1 if this > that
    *
    * @throws 
    */
   public int compareTo( TypeInteger that )
   {{
      if ( this.bitWidth == that.bitWidth ) { return 0; }
      if ( this.bitWidth < that.bitWidth ) { return -1; }
      if ( this.bitWidth > that.bitWidth ) { return +1; }
      throw new Error( IRTxtLib.programName+ 
		       ": internal error 2015apr24_125802. \n" );
   }}

   // ========================================================================
   // Short get subroutines
   // ========================================================================
   /** @return the bitwidth */
   int getBitWidth() {{ return bitWidth; }}

   /** @return the maximum value of this integer type */
   long getMaxVal() {{ return maxVal; }}

   /** @return the minimum value of this integer type */
   long getMinVal() {{ return minVal; }}

   /** @return the name of this type */
   String getName() {{ return name; }}

   // ========================================================================
   // Short set subroutines
   // ========================================================================


/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class TypeInteger


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

