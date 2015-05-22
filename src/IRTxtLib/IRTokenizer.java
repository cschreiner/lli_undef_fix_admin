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

import java.util.*;

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
      state= IRTokenType.UNKNOWN;
      idx= 0;
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
         final int debugSubStLen= 30;;
         int debugSubStEnd= (idx+debugSubStLen) > txt.length() ? 
	       (txt.length()) : (idx+debugSubStLen);;
	 System.out.println ( "IRTokenizer.lex(): "+ 
			      "starting main iteration, idx="+ idx );;
	 System.out.println( "\t"+ "debugSubStEnd="+ debugSubStEnd+ 
			     ", txt length="+ txt.length() );;
	 System.out.println( "\t"+ "next chars=\""+ 
			      txt.substring( idx, debugSubStEnd )+ "\"" );;
	 determineNextState();  // determines which state to go to next
	 System.out.println ( "going to state="+ state );;

	 // sanity check against infinite iterations
	 if ( (idx >= txt.length()) && (state != IRTokenType.MAX) ) {
	    throw new Error("Internal error 2015may21_130227, "+ 
		  "code=\"idx "+ idx+ " >= length "+ txt.length()+ "." );
	 }

	 switch ( state ) {
	 case STRING:
	    lexInStateString();
	    finishToken();
	    break;
	 case REG:
	    lexInStateRegAddrEtc();
	    finishToken();
	    break;
	 case ADDR:
	    lexInStateRegAddrEtc();
	    finishToken();
	    break;
	 case NUM:
	    lexInStateNum();
	    finishToken();
	    break;
	 case PUNCT:
	    lexInStatePunct();
	    finishToken();
	    break;
	 case SPACE:
	    lexInStateSpace();
	    finishToken();
	    break;
	 case WORD:
	    lexInStateWord();
	    finishToken();
	    break;
	 case COMMENT:
	    lexInStateComment();
	    finishToken();
	    break;
	 case COMDAT_NAME:
	    /* TODO3: double check if comdat names need to accept different
	     * characters than identifiers.  If so, comdat names may need
	     * their own function here, instead of piggybacking off of the
	     * function that handles Registers and Addresses. 
	     */
	    lexInStateRegAddrEtc();
	    finishToken();
	    break;
	 case ATTR_GROUP_ID:
	    lexInStateAttrGroupId();
	    finishToken();
	    break;
	 case MAX:
	    break;
	 default:
	    throw new Error( "Internal error 2015may21_121912: "+ 
			      "don't understand state "+ state );
	 } // switch
      }

      return tokenVec.toArray( new IRToken[0] );
   }}

   // ------------------------------------------------------------------------
   // lexInStateString()
   // ------------------------------------------------------------------------
   /**  lexes out an string literal
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
   private void lexInStateString()
   {{
      boolean backslashEscape= false;
      for ( ; idx < txt.length(); idx++ ) {
	 switch ( txt.charAt(idx) ) {
	 case '\\': 
	    backslashEscape= true;
	    tokenTxt.append( txt.charAt(idx) );
	    break;
	 case '"':
	    if ( backslashEscape ) {
	       tokenTxt.append( txt.charAt(idx) );
	       backslashEscape= false;
	    } else {
	       return;
	    }
	    break;
	 default:
	    tokenTxt.append( txt.charAt(idx) );
	    backslashEscape= false;
	    break;
	 } //switch
      }

      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStateRegAddrEtc()
   // ------------------------------------------------------------------------
   /**  lexes out an register name, address name, comdat name, and such.
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
   private void lexInStateRegAddrEtc()
   {{
      for ( ; idx < txt.length(); idx++ ) {
         System.out.println( "lexInStateRegAddrEtc(): char=\""+ 
			     txt.charAt(idx)+ "\"" );;
	 switch ( txt.charAt(idx) ) {
	 case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': 
	 case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n': 
	 case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u': 
	 case 'v': case 'w': case 'x': case 'y': case 'z': 
	 case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G': 
	 case 'H': case 'I': case 'J': case 'K': case 'L': case 'M': case 'N': 
	 case 'O': case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U': 
	 case 'V': case 'W': case 'X': case 'Y': case 'Z': 
	 case '-': case '$': case '.': case '_': 

	 /* The llvm LangRef 'Identifiers' section says digits are forbidden
	  * as a first character, but identifiers like '%1' are actually
	  * common practice, and this is sanctioned elsewhere in the LangRef.
	  * Sheesh.
	  */
	 case '0': case '1': case '2': case '3': case '4': 
	 case '5': case '6': case '7': case '8': case '9': 
	    tokenTxt.append( txt.charAt(idx) );
	    break;

	 /* the LangRef says: 
	    [ ] other characters can be used in identifiers if surrounded by 
		quotes.  
	    [ ] Special characters may be embedded via "\xx" where xx is an 
		ASCII code.  
            [ ] The "\01" prefix may be used in global variables to suppress 
		name mangling.   It is unclear if this preceeds or follows 
		the @ sigil.
            This code does not support any of this, yet.  

	    TODO3: add this feature if needed.  If so, and the updates do not
	    apply to comdat names, then comdat names will need their own
	    function to replace this one.
	 */
	 default:
	    return;
	 } //switch
      }

      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStateNum()
   // ------------------------------------------------------------------------
   /**  lexes out an number
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
   private void lexInStateNum()
   {{
      for ( ; idx < txt.length(); idx++ ) {
	 if ( Character.isDigit(txt.charAt(idx) ) ) {
	    tokenTxt.append( txt.charAt(idx) );
	 } else {
	    return;
	 }
      }

      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStatePunct()
   // ------------------------------------------------------------------------
   /**  lexes out a punctuation character, or for that matter, any single
    *  character.
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
   private void lexInStatePunct()
   {{
      idx++;
      if ( idx < txt.length() )  {
	 // there are more chars to tokenize
	 return;
      }
      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStateSpace()
   // ------------------------------------------------------------------------
   /**  lexes out an sequence of whitespace (which includes end-of-line
    *  markers)
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
   private void lexInStateSpace()
   {{
      for ( ; idx < txt.length(); idx++ ) {
	 if ( Character.isWhitespace(txt.charAt(idx)) ) {
	    tokenTxt.append( txt.charAt(idx) );
	 } else {
	    return;
	 }
      }

      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStateWord()
   // ------------------------------------------------------------------------
   /**  lexes out an keyword
    * 
    * <ul>
    * <li> Detailed Description: 
    *   Recall that keywords include type names, which can have digits (e.g. 
    *	"I35").
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
   private void lexInStateWord()
   {{
      for ( ; idx < txt.length(); idx++ ) {
	 if ( Character.isJavaIdentifierPart(txt.charAt(idx)) ) {
	    tokenTxt.append( txt.charAt(idx) );
	 } else {
	    return;
	 }
      }

      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStateComment()
   // ------------------------------------------------------------------------
   /**  lexes out an comment
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
   private void lexInStateComment()
   {{
      for ( ; idx < txt.length(); idx++ ) {
	 if ( txt.charAt(idx) == '\n' || txt.charAt(idx) == '\r' ) {
	    return;
	 } else {
	    tokenTxt.append( txt.charAt(idx) );
	 }
      }

      state= IRTokenType.MAX;
      return;
   }}

   // ------------------------------------------------------------------------
   // lexInStateAttrGroupId()
   // ------------------------------------------------------------------------
   /**  lexes out an Attribute Group Id
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
    * @throws 
    */
   private void lexInStateAttrGroupId()
   {{
      for ( ; idx < txt.length(); idx++ ) {
	 if ( Character.isDigit(txt.charAt(idx)) ) {
	    tokenTxt.append( txt.charAt(idx) );
	 } else {
	    return;
	 }
      }

      state= IRTokenType.MAX;
      return;
   }}

   // template for above functions:
   // ------------------------------------------------------------------------
   // lexInStateXx()
   // ------------------------------------------------------------------------
   /**  lexes out an xx
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
   private void lexInStateXx()
   {{
      for ( ; idx < txt.length(); idx++ ) {
	 if ( false /*xxx*/ ) {
	    tokenTxt.append( txt.charAt(idx) );
	 } else {
	    return;
	 }
      }

      state= IRTokenType.MAX;
      return;
   }}


   // ------------------------------------------------------------------------
   // lexInStateUnknown()
   // ------------------------------------------------------------------------
   /**  handles lexing when in state UNKNOWN
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
   private void determineNextState()
   {{
      if ( idx >= txt.length() ) {
	 state= IRTokenType.MAX;
	 return;
      }

      char ch= txt.charAt(idx);
      switch ( ch ) {
      case '"':
	 finishToken( IRTokenType.STRING );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case '%':
	 finishToken( IRTokenType.REG );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case '@':
	 finishToken( IRTokenType.ADDR );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case '-':
      case '0': case '1': case '2': case '3': case '4': 
      case '5': case '6': case '7': case '8': case '9': 
	 finishToken( IRTokenType.NUM );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case ',': case '!': case '*': case '=': 
      case '(': case ')': 
      case '[': case ']': 
      case '{': case '}': 
      case '<': case '>': 
	 finishToken( IRTokenType.PUNCT );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case ' ': case '\t': case '\r': case '\n':
	 finishToken( IRTokenType.SPACE );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': 
      case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n': 
      case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u': 
      case 'v': case 'w': case 'x': case 'y': case 'z': 
	 finishToken( IRTokenType.WORD );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case ';':
	 finishToken( IRTokenType.COMMENT );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case '$':
	 finishToken( IRTokenType.COMDAT_NAME );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      case '#':
	 finishToken( IRTokenType.ATTR_GROUP_ID );
	 tokenTxt.append(ch);
	 idx++;
	 return;
      default:
	 //  intentionally nothing
	 break;
      } // switch

      throw new Error( "Internal error 2015may21_120736: "+ 
		       "do not recognize char '"+ ch+ "' at position "+ idx+ 
		       "." );
   }}

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
    * <li> (no parameters)
    * </ul>
    *
    * @param nextState - the next state to go to
    * 
    * @return - 
    *
    * @throws 
    */
   private void finishToken() 
   {{
      if ( tokenTxt.length() > 0 ) {
	 IRToken tok= new IRToken();
	 tok.type= state;
	 tok.txt= tokenTxt.toString();
	 tokenVec.add( tok );
	 tokenTxt= new StringBuffer("");
      }
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

