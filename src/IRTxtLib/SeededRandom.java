/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: SeededRandom.java
   *
   * File Description: class SeededRandom
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


// ****************************************************************************
// File's primary class: SeededRandom
// ****************************************************************************
/*** creates random numbers from a seed
 *
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: nextVal= current * a constant + a constant.
 *	The implementation could use some tightening up.
 * </ul>
 */
public class SeededRandom 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */
   // instance for everyone to use
   public static SeededRandom x= new SeededRandom();  


   /* =========================================================================
      * instance variables
      * =======================================================================
      */
   private int seed;
   private int currentVal;


   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // SeededRandom()
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
   public SeededRandom()
   {{
      //System.err.println ( "Internal error: "+
      //   "unexpected call to default constructor for SeededRandom." );
      //System.exit(-127);

      String envSeed= System.getenv( "SEED" );
      if ( envSeed == null ) {
	 Random randomizer= new Random();
	 seed= randomizer.nextInt();
      } else {
	 seed= Integer.parseInt( envSeed );
      }
      currentVal= seed;

      next();
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // next()
   // ------------------------------------------------------------------------
   /**  computes the next random number
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
   private void next()
   {{
      long tmp= currentVal* 260726541+ 1195963154;

      /* This breaks up patterns of all even numbers or all odd numbers,
       * depending on the evenness of the original seed.
       */
      long evenness= (tmp & 0x80000000) >> 31;
      tmp+= evenness;

      // clean up and return
      currentVal= (int)(tmp & (long)0x7fffffff);
   }}

   // ------------------------------------------------------------------------
   // nextInt()
   // ------------------------------------------------------------------------
   /**  gets the next integer from this random number generator
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
    * @param max - the (max value+1) for the random number
    * 
    * @return a random integer such that 0 <= n < max 
    *
    * @throws 
    */
   public int nextInt( int max )
   {{
      next();

      // TODO2: make this more uniform, in case currentVal+ max > INT_MAX
      return (int)( currentVal % max );  
   }}

   // ------------------------------------------------------------------------
   // nextLong()
   // ------------------------------------------------------------------------
   /**  gets the next integer from this random number generator
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
    * @param max - the (max value+1) for the random number
    * 
    * @return a random integer such that 0 <= n < max 
    *
    * @throws 
    */
   public int nextLong( long max )
   {{
      next();
      long upper= currentVal;
      next();
      long lower= currentVal;
      long longVal= (upper << 32)+ lower;

      // TODO2: make this more uniform, in case currentVal+ max > INT_MAX
      return (int)( longVal % max );  
   }}

   // ------------------------------------------------------------------------
   // nextDouble()
   // ------------------------------------------------------------------------
   /** generates a random double between 0 and 1 
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
    * @return a double d such that 0 <= d < 1
    *
    * @throws 
    */
   public double nextDouble()
   {{
      next();

      // TODO2: see if there is a way to do this with longs for more
      // "randomness".
      double retVal= currentVal / Integer.MAX_VALUE;
      while ( retVal < 0 ) {
	 retVal+= 1.0;
      }
      while ( retVal > 0 ) {
	 retVal-= 1.0;
      }
      return retVal;
   }}

   // ------------------------------------------------------------------------
   // quick getter methods
   // ------------------------------------------------------------------------
   /** returns the original seed  */
   public int getSeed()
   {{ return seed; }}

/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class SeededRandom


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

