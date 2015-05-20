/* 
 * Program Name: lli_undef_fix
 *
 * File Name: IRToken.java
 *
 * File Description: class IRToken
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
package IRTxtLib;

/* ****************************************************************************
 *   imports
 * ****************************************************************************
 */

//import java.util.*;


// ****************************************************************************
// File's primary class: IRToken
// ****************************************************************************
/** holds a single LLVM IR Token, and related grammatical metadata
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class IRToken 
{

/* ############################################################################
 * primary class' direct contents
 * ############################################################################
 */
   
   /* =========================================================================
    * class variables
    * =========================================================================
    */
   /** any valid token constant i must make TOK_MIN <= i true */
   public static final int TOK_MIN= 214303595;
   /** it is not yet known what this token is */
   public static final int TOK_UNKNOWN= TOK_MIN+ 0;
   /* string literals, delimited by "s at the ends */
   public static final int TOK_STRING= TOK_MIN+ 1;
   /** register names, like %33 and %addr */
   public static final int TOK_REG= TOK_MIN+ 2;
   public static final int TOK_ADDR= TOK_MIN+ 3;
   /** numeric literals, like 5 and -327 */
   public static final int TOK_NUM= TOK_MIN+ 4;
   /** punctuation is things like (, ), commas, and such. */
   public static final int TOK_PUNCT= TOK_MIN+ 5;
   /** all sequences of whitespace */
   public static final int TOK_SPACE= TOK_MIN+ 6;
   /** all comments */
   public static final int TOK_COMMENT= TOK_MIN+ 7;
   /** any valid token constant i must make i < TOK_MAX true */
   public static final int TOK_MAX= TOK_MIN+ 8;

   /* =========================================================================
    * instance variables
    * =========================================================================
    */
   int type;

   /* =========================================================================
    * constructors
    * =========================================================================
    */

   // -------------------------------------------------------------------------
   // IRToken()
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
   private IRToken()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for IRToken." );
      System.exit(-127);
   }}


   /* =========================================================================
    * methods
    * =========================================================================
    */


/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class IRToken


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

