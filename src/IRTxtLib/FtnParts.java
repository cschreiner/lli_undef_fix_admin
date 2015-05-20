/* 
 * Program Name: lli_undef_fix
 *
 * File Name: FtnParts.java
 *
 * File Description: class FtnParts
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

import java.util.*;

import IRTxtLib.*;

// ****************************************************************************
// File's primary class: FtnParts
// ****************************************************************************
/** 
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class FtnParts 
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

   /** holds the function's return type */
   public TypeInteger retType;

   /** holds data about each argument to the function */
   public RegWithType args[];

   /** holds the name of the function */
   public String name;

   /** holds the whole function as a single string, including any known
    * comments.
    */
   public String ftnSt; 

   // TODO: add fields to hold statements.  For example:
   // String statements[];


   /* =========================================================================
    * constructors
    * =========================================================================
    */

   // -------------------------------------------------------------------------
   // FtnParts()
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
   private FtnParts()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for FtnParts." );
      System.exit(-127);
   }}

   // ------------------------------------------------------------------------
   // FtnParts()
   // ------------------------------------------------------------------------
   /** commonly used constructor 
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
    * @param ftnSt - the whole function, stored as a single string
    * 
    * @return - n/a (it's a constructor!)
    *
    * @throws 
    */
   public FtnParts(String ftnSt )
   {{
      this.ftnSt= ftnSt;
      retType= null;
      args= null;
      name= null;

      parse();
   }}


   /* =========================================================================
    * methods
    * =========================================================================
    */

   // ------------------------------------------------------------------------
   // parse()
   // ------------------------------------------------------------------------
   /** parses the ftnSt field to be able to populate the other fields 
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
   private void parse()
   {{
      // TODO2: create a better tokenizer than this
      String tokens[]= tokenize();

      if ( IRTxtLib.arg_verbosity >= 2 ) {
	 for( int ii= 0; ii < tokens.length; ii++ ) {
	    System.out.println( "token=\""+ tokens[ii]+ "\"" );
	 }
      } else {
	 System.out.println ( "found verbosity= "+ IRTxtLib.arg_verbosity );;
      }

      // TODO: add more code here
      int currentToken= 0;

      currentToken= 0; // TODO: make this meaningful


      // clean up and return
      return;
   }}

   // ------------------------------------------------------------------------
   // tokenize()
   // ------------------------------------------------------------------------
   /**  converts the function text into an array of tokens
    * 
    * TODO2: create a better tokenizer than this
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
   private String[] tokenize()
   {{
      // TODO2: create a better tokenizer than this
      String chunks[]= ftnSt.split( "\\b" );

      if ( IRTxtLib.arg_verbosity >= 2 ) {
	 for( int ii= 0; ii < chunks.length; ii++ ) {
	    System.out.println( "chunk=\""+ chunks[ii]+ "\"" );
	 }
      } else {
	 System.out.println ( "found verbosity= "+ IRTxtLib.arg_verbosity );;
      }

      Vector<String> tokenPartVec= new Vector<String>();
      for( int ii= 0; ii < chunks.length; ii++ )  {
         String tokenParts[]= chunks[ii].split(" ");; // TODO: make this meaningful
      }



      int currentChunk= 0;

      currentChunk= 0; // TODO: make this meaningful

      return new String[]{ }; // TODO: make this meaningful
   }}

   // ------------------------------------------------------------------------
   // removeInitialToken()
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
   private String removeInitialToken()
   {{
      final String TOKENS[]= { "(", ")", ",", "@", "%", "{", "}" };

      return ""; // TODO: make this meaningful 
   }}



/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class FtnParts


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

