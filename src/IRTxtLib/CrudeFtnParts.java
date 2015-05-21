/* 
 * Program Name: lli_undef_fix
 *
 * File Name: CrudeFtnParts.java
 *
 * File Description: class CrudeFtnParts
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
// File's primary class: CrudeFtnParts
// ****************************************************************************
/** 
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class CrudeFtnParts 
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
   // CrudeFtnParts()
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
   private CrudeFtnParts()
   {{
      System.err.println ( "Internal error: "+
	    "unexpected call to default constructor for CrudeFtnParts." );
      System.exit(-127);
   }}

   // ------------------------------------------------------------------------
   // CrudeFtnParts()
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
   public CrudeFtnParts(String ftnSt )
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
      /* TODO2: create a full-fledged tokenizer.  See
	 IRTxtLib/IRTokenizer.java.

	 The code below is _very_ kludged.  Among other things, it requires
	 whitespace between the parenthesis ending arguments and the body's
	 opening brace, e.g.: 

		define i12 @ftn_foo ( i7 ) {...
                                      here^
       */

      // ............................................................
      // set up
      String chunks[]= ftnSt.split( "(\\b|\\s+)" );

      if ( IRTxtLib.arg_verbosity >= 2 ) {
	 for( int ii= 0; ii < chunks.length; ii++ ) {
	    System.out.println( "chunk=\""+ chunks[ii]+ "\"" );
	 }
      } else {
	 System.out.println ( "found verbosity= "+ IRTxtLib.arg_verbosity );;
      }

      Vector<RegWithType> argVec= new Vector<RegWithType>();
      int argNum= 1;

      int currentChunk= 0; 

      // ............................................................
      // begin parsing
      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( !chunks[currentChunk].equals("define") ) {
	 throw new Error( "expected \"define\", found \""+ 
			  chunks[currentChunk]+ "\"" );
      }
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      String retTypeName= chunks[currentChunk];
      retType= new TypeInteger( retTypeName );
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( !chunks[currentChunk].matches("\\s*@") ) {
	 throw new Error( "expected \" @\", found \""+ 
			  chunks[currentChunk]+ "\"" );
      }
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( !chunks[currentChunk].matches("\\w*") ) {
	 throw new Error( "expected \"<ftn_name>\", found \""+ 
			  chunks[currentChunk]+ "\"" );
      }
      StringBuffer ftnNameBuffer= new StringBuffer( "@" );
      ftnNameBuffer.append( chunks[currentChunk] );
      name= ftnNameBuffer.toString();
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( !chunks[currentChunk].equals( "(" ) ) {
	 throw new Error( "expected \"(\", found \""+ 
			  chunks[currentChunk]+ "\"" );
      }
      currentChunk++;

      while ( !chunks[currentChunk].equals( ")" ) ) {
	 currentChunk= skipWhitespaceChunks( chunks, currentChunk );

	 String argTypeName= chunks[currentChunk];
	 String argName= "%"+ argNum;
	 argNum++;
	 argVec.add( new RegWithType( argName, 
				      new TypeInteger(argTypeName) ) );
	 currentChunk++;

	 currentChunk= skipWhitespaceChunks( chunks, currentChunk );

	 if (  chunks[currentChunk].matches( "\\s*,\\s*" ) ) {
	    currentChunk++;
	    continue; 
	 } else if ( chunks[currentChunk].matches( "\\s*\\)\\s*" ) ) {
	    break;
	 } else {
	    throw new Error( "expected \",\" or \")\", found \""+ 
			     chunks[currentChunk]+ "\"" );

	 }

      }

      /* we can discard the rest of the chunks, they hold the function
       * body, which isn't important right now.
       */

      // ............................................................
      // store what we've parsed out
      args= argVec.toArray( new RegWithType[0] );

      // ............................................................
      // figure out what registers are used and which aren't
      for ( int ii= 0; ii < args.length; ii++ )  {
	 String pattern= ".*"+ args[ii].regName+ "\\b.*";
	 System.out.println ( "checking for use of "+ args[ii].regName+ "via pattern \""+ pattern+ "\"" );;
	 args[ii].isUsed= ftnSt.matches( pattern );
      }

      // ............................................................
      // clean up and return
      return;
   }}

   // ------------------------------------------------------------------------
   // skipWhitespaceChunks()
   // ------------------------------------------------------------------------
   /**  helps parse() by finding the next non-whitespace chunk 
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
    * @param chunks - array of the chunks under examination
    * @param idx - index of the chunk where testing for whitespace should begin
    *
    * @return - index of the next non-whitespace chunk
    *
    * @throws 
    */
   private int skipWhitespaceChunks( String[] chunks, int start )
   {{
      int ii= start;
      while ( chunks[ii].matches("\\s*") ) {
         ii++;
      }
      return ii;
   }}

   // ------------------------------------------------------------------------
   // toString()
   // ------------------------------------------------------------------------
   /** generates a string representation of this instance 
    * 
    * <ul>
    * <li> Detailed Description: 
    *	The representation contains many lines, and is biased towards a 
    *	technically-inclined human.
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * <li> No inputs.
    * </ul>
    * 
    * @return - the String
    *
    * @throws 
    */
   public String toString()
   {{
      StringBuffer retVal= new StringBuffer( "" );

      retVal.append( "name=\""+ name+ "\"\n" );
      retVal.append( "retType=\""+ retType.getName()+ "\"\n" );
      retVal.append( "args: \n" );
      for ( int ii= 0; ii < args.length; ii++ )  {
	 retVal.append( ii+ ": " );
	 retVal.append( args[ii].toString()+ "\n" );
      }
      retVal.append( "EndOfRecord\n" );

      return retVal.toString();
   }}




/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class CrudeFtnParts


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

