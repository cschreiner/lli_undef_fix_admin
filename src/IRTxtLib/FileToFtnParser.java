/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: FileToFtnParser.java
   *
   * File Description: class FileToFtnParser
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
import java.io.*;

//import java.util.*;


// ****************************************************************************
// File's primary class: FileToFtnParser
// ****************************************************************************
/** parses an IR file into individual functions
 *
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class FileToFtnParser 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
     * class variables
     * ========================================================================
     */

   /* =========================================================================
    * instance variables
    * =========================================================================
    */
   String filename;
   String lastLineRead;
   boolean isClosed;
   LineNumberReader reader;

   /* =========================================================================
   * constructors
   * ==========================================================================
   */

   // -------------------------------------------------------------------------
   // FileToFtnParser()
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
   private FileToFtnParser()
   {{
      System.err.println ( IRTxtLib.programName+ ": internal error: "+
	    "unexpected call to default constructor for FileToFtnParser." );
      System.exit(-127);
   }}

   // ------------------------------------------------------------------------
   // FileToFtnParser()
   // ------------------------------------------------------------------------
   /** commonly used constructor 
    * 
    * <ul>
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    * </ul>
    *
    * @param filename - the name of the file to read and parse
    * 
    * @return - 
    *
    * @throws 
    */
   public FileToFtnParser( String filename )
   {{
      this.filename= filename;
      lastLineRead= null;
      isClosed= false;
      try {
	 reader= new LineNumberReader( new FileReader(filename) );
      } catch (IOException ex) {
         System.err.print( IRTxtLib.programName+ 
			   ": can not open IR file for reading, \n"+ 
			   "\t"+ "file=\""+ filename+ "\", \n"+
			   "\t"+ ex.getMessage()+ "\n" );
         System.exit( -1 );
      }
   }}

   /* =========================================================================
    * methods
    * =========================================================================
    */

   // ------------------------------------------------------------------------
   // parseFtn()
   // ------------------------------------------------------------------------
   /**  parses one function's text out of the file's contents
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
    * @return - the function's text, or null if EOF has been reached
    *
    * @throws 
    */
   public String parseFtn()
   {{
      if ( isClosed ) { return null; }

      StringBuffer ftnTxt= new StringBuffer("");
      String line= "";

      try {
	 if ( lastLineRead == null ) {
	    while ( true ) {
	       line= reader.readLine();
	       if( line == null ) { 
		  // we've reached EOF
		  close();
		  return ftnTxt.toString();
	       }
	       if ( isFtnHeader(line) ) { 
		  ftnTxt.append( line );
		  ftnTxt.append( "\n" );
		  break; 
	       }
	    }
	 } else {
	    ftnTxt.append( lastLineRead );
	    ftnTxt.append( "\n" );
	 }

	 while( true ) {
	    line= reader.readLine();
	    if ( line == null ) {
	       // we've reached EOF
	       close();
	       return ftnTxt.toString();
	    }
	    if ( isFtnHeader(line) )  { break; }
	    ftnTxt.append( line );
	    ftnTxt.append( "\n" );
	 }
	 lastLineRead= line;
      } catch (IOException ex)  {
	 System.err.print( IRTxtLib.programName+ 
	       ": something went wrong when reading from the IR file, \n"+
	       "\t"+ "file=\""+ filename+ "\", \n"+
	       "\t"+ ex.getMessage()+ ".\n" );
      }

      return ftnTxt.toString();
   }}


   // ------------------------------------------------------------------------
   // isFtnHeader()
   // ------------------------------------------------------------------------
   /**  returns true if the indicated text appears to start a function
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
    * @param txt - one line of the 
    * 
    * @return - 
    *
    * @throws 
    */
   private boolean isFtnHeader( String st )
   {{
      return st.matches( "\\s*"+ "define"+ "\\s+.*" );
   }}

   // ------------------------------------------------------------------------
   // close()
   // ------------------------------------------------------------------------
   /**  closes the file this instance is reading from
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
   private void close()
   {{
      try {
	 reader.close();
      } catch ( IOException ex ) {
	 System.err.print( IRTxtLib.programName+ 
			   ": can't close LLVM IR file after reading, \n"+
			   "\t"+ "file=\""+ filename+ "\", \n"+
			   "\t"+ ex.getMessage() );
	 System.exit( -1 );
      }
      isClosed= true;
      return;
   }}


/* ############################################################################
 * trivially simple subclasses
 * ############################################################################
 */

/* ****************************************************************************
 * end of primary class
 * ****************************************************************************
 */
} // end class FileToFtnParser


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

