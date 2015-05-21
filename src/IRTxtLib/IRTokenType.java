/* 
 * Program Name: lli_undef_fix
 *
 * File Name: IRTokenType.java
 *
 * File Description: class IRTokenType
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
//import java.applet.Applet;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.Color.*;
//import java.awt.geom.*;


// ****************************************************************************
// File's primary class: IRTokenType
// ****************************************************************************
/** enumeration defining different types of tokens
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public enum IRTokenType {
   /** any valid token constant i must make TOK_MIN <= i true */
   MIN ("MIN"), 
   /** it is not yet known what this token is */
   UNKNOWN ("UNKNOWN"), 
   /** string literals, delimited by "s at the ends */
   STRING ("STRING"), 
   /** register names, like %33 and %sub */
   REG ("REG"), 
   /** address names, like @ftn1 and @MAX_LENGTH */
   ADDR ("ADDR"), 
   /** numeric literals, like 5 and -327 */
   NUM ("NUM"), 
   /** punctuation is things like (, ), commas, and such. */
   PUNCT ("PUNCT"), 
   /** all sequences of whitespace */
   SPACE ("SPACE"), 
   /** a keyword, such as 'define', 'mul', or 'nsw' */
   WORD ("WORD"),
   /** all comments */
   COMMENT ("COMMENT"), 
   /** a comdat name, per LLVM IR LangRef, e.g. "$something" */
   COMDAT_NAME ("COMDAT_NAME"),
   /** an attribute group id, e.g. "#17" */
   ATTR_GROUP_ID ("ATTR_GROUP_ID"),
   /** any valid token constant i must make i < MAX true */
   MAX ("MAX") };

   private final String name;

   // ------------------------------------------------------------------------
   // IRTokenType
   // ------------------------------------------------------------------------
   IRTokenType( String name ) 
   {
      this.name= name;
   }

   // ------------------------------------------------------------------------
   // toString()
   // ------------------------------------------------------------------------
   /**  returns a string representation of the token type
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
   public String toString()
   {{
      return name;
   }}



/* ****************************************************************************
 *   end of file
 * ****************************************************************************
 */

