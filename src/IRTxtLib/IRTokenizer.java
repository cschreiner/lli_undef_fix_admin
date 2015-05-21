/* 
 * Program Name: lli_undef_fix
 *
 * File Name: IRTokenizer.java
 *
 * File Description: class IRTokenizer
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
   * $Source
  cas_tmpl.java,v $
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

import IRTxtLib.*;

// ****************************************************************************
// File's primary class: IRTokenizer
// ****************************************************************************
/** splits a string of IR text into tokens.
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class IRTokenizer 
{

/* ############################################################################
 * primary class' direct contents
 * ############################################################################
 */

   /* =========================================================================
    * class variables
    * =========================================================================
    */

   /* =========================================================================
    * instance variables
    * =========================================================================
    */
   String txt;
   IRTokenType state;
   int idx;  // which character we're looking at
   Vector<IRToken> tokenVec; // all the tokens we've already found
   StringBuffer tokenTxt; // gathers text of the token currently being extracted

   /* =========================================================================
    * constructors
    * =========================================================================
    */

   // -------------------------------------------------------------------------
   // IRTokenizer()
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
   private IRTokenizer()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for IRTokenizer." );
      System.exit(-127);
   }}

   // -------------------------------------------------------------------------
   // IRTokenizer(String)
   // -------------------------------------------------------------------------
   /** commonly used constructor
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
   public IRTokenizer( String txt )
   {{
      this.txt= txt;
      state= IRTokenType.UKNOWN;
      ii= 0;
      tokenVec= new Vector<IRToken>();
      tokenTxt= new StringBuffer("");
   }}


   /* =========================================================================
    * methods
    * =========================================================================
    */

   // ------------------------------------------------------------------------
   // lex()
   // ------------------------------------------------------------------------
   /**  splits text into lexicagraphical tokens (i.e. "tokenize" the text)
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
    * @param txt - the text to process
    * 
    * @return - 
    *
    * @throws 
    */
   public IRToken[] lex()
   {{
      while ( state != IRTokenType.MAX ) {
	 switch ( state ) {
	 case UNKNOWN:
	    lexInStateUnknown();
	    break;
	 case STRING:
	    lexInStateString();
	    break;
	 case REG:
	    lexInStateRegAddrEtc();
	    break;
	 case ADDR:
	    lexInStateRegAddrEtc();
	    break;
	 case NUM:
	    lexInStateNum();
	    break;
	 case PUNCT:
	    lexInStatePunct();
	    break;
	 case SPACE:
	    lexInStateSpace();
	    break;
	 case COMMENT:
	    lexInStateComment();
	    break;
	 case COMDATE_NAME:
	    lexInStateRegAddrEtc();
	    break;
	 case ATTR_GROUP_ID:
	    lexInStateAttrGroupId();
	    break;
	 case MAX:
	    break;
	 } // switch
      }

   }}

   // ------------------------------------------------------------------------
   // lexInStateUnknown()
   // ------------------------------------------------------------------------
   /**  handles lexing when instate UNKNOWN
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
   private void lexInStateUnknown()
   {{
      char ch= txt.charAt(idx );
      switch ( ch ) {
      case '"':
	 finishToken( IRTokenType.STRING );
	 tokenTxt.append(ch);
	 return;
      case '%':
	 finishToken( IRTokenType.REG );
	 tokenTxt.append(ch);
	 return;
      case '@':
	 finishToken( IRTokenType.ADDR );
	 tokenTxt.append(ch);
	 return;
      case '-':
      case '0': case '1': case '2': case '3': case '4': 
      case '5': case '6': case '7': case '8': case '9': 
	 finishToken( IRTokenType.NUM );
	 tokenTxt.append(ch);
	 return;
      case ',': case '!': case '*': case '=': 
      case '(': case ')': 
      case '[': case ']': 
      case '{': case '}': 
      case '<': case '>': 
	 finishToken( IRTokenType.PUNCT );
	 tokenTxt.append(ch);
	 return;
      case ' ': case '\t': 
	 finishToken( IRTokenType.SPACE );
	 tokenTxt.append(ch);
	 return;
      case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': 
      case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n': 
      case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u': 
      case 'v': case 'w': case 'x': case 'y': case 'z': 
	 finishToken( IRTokenType.WORD );
	 tokenTxt.append(ch);
	 return;
      case ';':
	 finishToken( IRTokenType.COMMENT );
	 tokenTxt.append(ch);
	 return;
      case '$':
	 finishToken( IRTokenType.COMDATE_NAME );
	 tokenTxt.append(ch);
	 return;
      case '#':
	 finishToken( IRTokenType.ATTR_GROUP_ID );
	 tokenTxt.append(ch);
	 return;
      } // switch

      throw new Error( "Internal error 2015may21_120736: "+ 
		       "do not recognize char '"+ ch+ "' at position "+ idx+ 
		       "." );
   }}

   // ------------------------------------------------------------------------
   // finishToken()
   // ------------------------------------------------------------------------
   /**  finishes out the current token, and resets this instance's internal
    *  state to start processing the next token.
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
    * @param nextState - the next state to go to
    * 
    * @return - 
    *
    * @throws 
    */
   private void finishToken( IRTokenType nextState )
   {{
      if ( tokenTxt.length() > 0 ) {
	 IRToken tok= new IRToken();
	 tok.type= state;
	 tok.txt= tokenTxt.toString();
	 tokenVec.add( tok );
	 tokenTxt= new StringBuffer("");
      }

      state= nextState;
   }}



/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class IRTokenizer


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

