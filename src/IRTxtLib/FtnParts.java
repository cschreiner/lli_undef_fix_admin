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
/** Converts a lexed (aka tokenized) LLVM IR function and splits it up
 * into parts, such as the function name, its arguments, return type,
 * body, and so forth.
 *
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
   private TypeInteger retType;

   /** holds data about each argument to the function */
   private RegWithType args[];

   /** holds the name of the function */
   private String name;

   /** holds the whole function as a single string, including any known
    * comments.
    */
   private String ftnSt; 

   // TODO3: add fields to hold statements.  For example:
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
      // ............................................................
      // set up
      IRTokenizer tokenizer= new IRTokenizer( ftnSt );
      IRToken[] chunks= tokenizer.lex();  // one chunk == one token

      if ( IRTxtLib.arg_verbosity >= 2 ) {
	 for( int ii= 0; ii < chunks.length; ii++ ) {
	    System.out.println( "chunk=\""+ chunks[ii].toString()+ "\"" );
	 }
      }

      Vector<RegWithType> argVec= new Vector<RegWithType>();
      int argNum= 0; /* Apparently the LLVM spec says that the first
		      * unnamed argument is %0. If there are no
		      * unnamed arguments, then %0 apparently is
		      * applied as a label for the first basic block
		      * of the function. -- CAS 2015may28
		      */
      int firstBodyChunk= 0; // the first chunk that contains the function body

      int currentChunk= 0; 

      // ............................................................
      // begin parsing
      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( chunks[currentChunk].type != IRTokenType.WORD ||
	   !chunks[currentChunk].txt.equals("define") ) {
	 throw new Error( "expected \"define\", found \""+ 
			  chunks[currentChunk].txt+ "\"" );
      }
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( chunks[currentChunk].type != IRTokenType.WORD ) {
	 throw new Error( "Expected a type name, found \""+ 
			  chunks[currentChunk].txt+ "\"." );
      }
      String retTypeName= chunks[currentChunk].txt;
      retType= new TypeInteger( retTypeName );
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( chunks[currentChunk].type != IRTokenType.ADDR ) {
	 throw new Error( "expected \"@<ftn_name>\", found \""+ 
			  chunks[currentChunk].txt+ "\"" );
      }
      name= chunks[currentChunk].txt;
      currentChunk++;

      currentChunk= skipWhitespaceChunks( chunks, currentChunk );

      if ( chunks[currentChunk].type != IRTokenType.PUNCT ||
	   !chunks[currentChunk].txt.equals( "(" ) ) {
	 throw new Error( "expected \"(\", found \""+ 
			  chunks[currentChunk].txt+ "\"" );
      }
      currentChunk++;

      while ( !chunks[currentChunk].txt.equals( ")" ) ) {
	 currentChunk= skipWhitespaceChunks( chunks, currentChunk );

	 String argTypeName= chunks[currentChunk].txt;
	 String argName= "";
	 argName= "%"+ argNum;
	 /* TODO2: add code here to set argName to the given argument name iff
	  * one is specified.
	  */

	 argNum++;
	 argVec.add( new RegWithType( argName, 
				      new TypeInteger(argTypeName) ) );
	 currentChunk++;

	 currentChunk= skipWhitespaceChunks( chunks, currentChunk );

	 if ( chunks[currentChunk].type == IRTokenType.PUNCT &&
	      chunks[currentChunk].txt.equals( "," ) ) {
	    currentChunk++;
	    continue; 
	 } else if ( chunks[currentChunk].type == IRTokenType.PUNCT &&
		     chunks[currentChunk].txt.equals( ")" ) ) {
	    break;
	 } else {
	    throw new Error( "expected \",\" or \")\", found \""+ 
			     chunks[currentChunk].txt+ "\"" );
	 }

      }

      currentChunk++;

      /* Ignore the rest of the chunks for now.  They hold the function body,
       * which we don't need till later.
       */
      firstBodyChunk= currentChunk;

      // ............................................................
      // store what we've parsed out
      args= argVec.toArray( new RegWithType[0] );

      // ............................................................
      // figure out what registers are used and which aren't
      for ( int arg= 0; arg < args.length; arg++ )  {
	 args[arg].isUsed= false;
	 for ( int cc= firstBodyChunk; cc < chunks.length; cc++ ) {
	    if ( chunks[cc].type == IRTokenType.REG ) {
	       if ( chunks[cc].txt.equals(args[arg].regName) ) {
		  args[arg].isUsed= true;
		  break;
	       }
	    }
	 }
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
   private int skipWhitespaceChunks( IRToken[] chunks, int start )
   {{
      int ii= start;
      while ( chunks[ii].type == IRTokenType.SPACE ) {
         ii++;
      }
      return ii;
   }}

   // ------------------------------------------------------------------------
   // toString()
   // ------------------------------------------------------------------------
   /** generates a string representation of this instance: the name of
    * the function, its return type, info on its arguments, etc.
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

   // ------------------------------------------------------------------------
   // getNameWithoutSigil()
   // ------------------------------------------------------------------------
   /**  Returns the function name without its preceeding sigil (usually an @)
    *   This can be useful when generating filenames from the function name.
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
   public String getNameWithoutSigil()
   {{
      /* TODO3: do something to check that this removes all preceeding
       * non-alphabetic characters.
       */
      return name.substring(1);
   }}

   // ------------------------------------------------------------------------
   // trivially simple setter/getter methods
   // ------------------------------------------------------------------------
   /**  @return the function's return type */
   public TypeInteger getRetType()
   {{ return retType; }}

   /**  @return an array whose elements describe the arguments to the 
	function 
   */
   public RegWithType[] getArgs()
   {{ return args; }}

   /**  @return the name of the function */
   public String getName()
   {{ return name; }}

   /**  @return the original string defining the function */
   public String getFtnSt()
   {{ return ftnSt; }}

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

