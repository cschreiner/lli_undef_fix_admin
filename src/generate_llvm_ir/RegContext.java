/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: RegContext.java
   *
   * File Description: class RegContext
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
package generate_llvm_ir;

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

import generate_llvm_ir.*;

// ****************************************************************************
// File's primary class: RegContext
// ****************************************************************************
/** information for selecting and managing register names and what type of
 *	data is in each of them.
 *
 * <ul>
 * <li> Description: 
 *
 * <li> Algorithm: 
 * </ul>
 *
 * TODO: add methods getPrevRegWithType(int) and getRecentRegWithType(void).
 * They should return RegWithTypes.
 */
public class RegContext 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */
   // llvm requires the first register of a function to be %1
   private static final int MIN_REG_NUM= 1;

   // Helps track unexpected switches to a different basic block.
   private static BasicBlockSeq lastBasicBlock= null;

   static Random randomizer= new Random();

   /* =========================================================================
      * instance variables
      * =======================================================================
      */
   /** prefix to be attatched to all register names */
   String regPrefix;

   /** the number of the next register to create */
   int regNum;

   /** used to detect when reg numbering gets reset accidentally. */
   boolean regNumMayBeOne; 

   /** keys= reg names (including the % sigil), values= the register's type */
   Hashtable<String,TypeInteger> regTypeHash;

   int numArgs;

   /** contains every register that hasn't been read yet */
   Vector<String> unreadRegVec;

   /** after initial setup is done, callers may not change it */
   boolean setupComplete;

   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // RegContext()
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
   public RegContext()
   {{
      //System.err.println ( "Internal error: "+
      //   "unexpected call to default constructor for RegContext." );
      //System.exit(-127);
      constructorHelper();
   }}

   // -------------------------------------------------------------------------
   // RegContext()
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
    * </ul>
    *
    * @param prefix - all register names will be prefixed with this 
    *		string. The string should not include the LLVM IR sigil (%).
    *		May be undefined if no prefix exists.
    * 
    * @return - n/a (it's a constructor!)
    *
    * @throws
    */
   public RegContext( String prefix )
   {{
      constructorHelper();

      if ( prefix != null ) {
	 this.regPrefix= prefix;
      }
   }}

   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // constructorHelper()
   // ------------------------------------------------------------------------
   /**  Initializer of instance variables that are initialized each way 
    *	regardless of the constructor chosen.
    * 
    * <ul>
    * <li> Note: this is called "initRegContext()" instead of "init()" to
    *	avoid confusion with other classes' initialization methods.
    *
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    *
    * @return void
    *
    * @throws 
    */
   private void constructorHelper()
   {{
      regPrefix= "";
      regNum= 1; // per LLVM IR spec, the first register must be %1.
      regNumMayBeOne= false;
      regTypeHash= new Hashtable<String, TypeInteger>();
      numArgs= 0;
      unreadRegVec= new Vector<String>();
      setupComplete= false;

      return;
   }}

   // ------------------------------------------------------------------------
   // setPrefix()
   // ------------------------------------------------------------------------
   /**  Sets the register prefix.  This is not always known when the
    *  constructor is called.
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
    * @param prefix - all register names will be prefixed with this 
    *		string. The string should not include the LLVM IR sigil (%).
    *		May be undefined if no prefix exists.
    * 
    * @return - 
    *
    * @throws 
    */
   public void setPrefix( String prefix )
   {{
      if ( setupComplete ) {
	 throw new Error( "internal error 2015apr24_095444 "+ 
			  "(late call to RegContext.setPrefix()" );
      }
      if ( prefix != null ) {
	 this.regPrefix= prefix;
      }
      setupComplete= true;
   }}

   // ------------------------------------------------------------------------
   // getRegName()
   // ------------------------------------------------------------------------
   /**  returns the name of a new register, including the sigil ("%")
    * 
    * TODO: rename this to getNewRegName()
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
   public String getRegName()
   {{
      setupComplete= true;

      String retVal= "%"+ regPrefix+ regNum;
      regNum++;
      regNumMayBeOne= false;

      // note that the register exists, but we don't know its type yet.
      regTypeHash.put( retVal, null );

      // note that this register hasn't been read yet
      unreadRegVec.add( retVal );

      if ( retVal.matches("\\s*") )  {
	 // why is the register name all whitespace?
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_139631. \n" );
      }
      return retVal;
   }}

   // ------------------------------------------------------------------------
   // getPrevRegName()
   // ------------------------------------------------------------------------
   /** Gets the name of the nth previously issued register, and marks it 
    *	as read.
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
    * @param steps - the number of setps to go back to find the register name.
    *		0 (the default) indicates the absolute most recently issued
    *		register, 1 is the register before that, and so forth.
    * 
    * @return - the name of a register
    *
    * @throws 
    */
   public String getPrevRegName( int steps )
   {{
      if ( steps < 0 ) {
	 throw new Error( Main.PROGRAM_NAME+ 
	       ": internal error 2015apr24_101438 (RegContext's steps < 0)" );
      }
      if ( (regNum- 1- steps) < 0 ) {
	 // we can't go back to a register < 0.
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2014nov24_154228, "+
			  "codes=\""+  regNum+ "\", \""+ steps+ "\"\n" );
      }

      String retVal="%"+ regPrefix+ (regNum- 1- steps);
      if ( retVal.matches("\\s*") )  {
	 // why is the register name a null string?
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_133615. \n" );
      }  

      reportRegRead( retVal ); 
      return retVal;
   }}

   // ------------------------------------------------------------------------
   // getPrevRegWithType()
   // ------------------------------------------------------------------------
   /**  acts like getPrevRegName, but returns the register's type as well.
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
    * @param steps - the number of setps to go back to find the register name.
    *	Corresponds to the steps parameter to getPrevRegName(int).
    * 
    * @return the register's name and type
    *
    * @throws 
    */
   public RegWithType getPrevRegWithType(int steps)
   {{
      String regName= getPrevRegName(steps);
      TypeInteger type= getRegType(regName);
      return new RegWithType( regName, type );
   }}

   // ------------------------------------------------------------------------
   // getRecentRegName()
   // ------------------------------------------------------------------------
   /** Gets the name of a recently issued register or argument.  The
    *	register/argument's type is guaranteed to be known.  Marks the
    *	register as read.
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
   public String getRecentRegName()
   {{
      // see if we can return a previously unread register
      if ( unreadRegVec.size() > 0 ) {
         for( int ii= 0; ii < unreadRegVec.size(); ii++ ) {
	    String reg= unreadRegVec.get(ii);
	    if ( regTypeHash.get(reg) != null ) {
	       // this register meets our criteria
	       unreadRegVec.remove(ii);
	       return reg;
	    }
	 }
      }

      // ok, we'll have to return a register that was already read.

      // find all of the registers/arguments with known types
      Vector<String> knownTypeRegVector= new Vector<String>();
      for( Enumeration<String> regEnum= regTypeHash.keys(); 
	    regEnum.hasMoreElements(); ) {
	 String reg= regEnum.nextElement();
	 TypeInteger type= regTypeHash.get(reg);
	 if ( type != null ) {
	    knownTypeRegVector.add(reg);
	 }
      }

      // choose a register at random 
      if ( knownTypeRegVector.size() < 1 )  {
	 /* There should be at least _some_ registers, because we forcibly
	    create 2 registers if there aren't any arguments.
	 */
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_135003. \n" );
      }
      String chosenReg= knownTypeRegVector.get( 
	    randomizer.nextInt( knownTypeRegVector.size() ) 
	    );

      // check it
      if ( chosenReg.matches("\\s*") )  {;;
	 // why is the register name a null string?
	 System.out.print( 
	       "Warning: Why is chosenReg a whitespace string?\n" );
	 System.out.print( "knownTypeRegVector= < " );
	 for( int ii= 0; ii < knownTypeRegVector.size(); ii++ ) {
	    System.out.print( "\""+ knownTypeRegVector.get(ii)+ "\"," );
	 }
	 System.out.print( "<end> len="+ knownTypeRegVector.size()+ "> \n" );
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_133530. \n" );
      }

      // clean up and return
      return chosenReg;
   }}

   // ------------------------------------------------------------------------
   // getRecentRegWithType()
   // ------------------------------------------------------------------------
   /**  just like getRecentRegName(), but also returns the register's type.
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
    * @return - a register name and its type info
    *
    * @throws 
    */
   public RegWithType getRecentRegWithType()
   {{
      String regName= getRecentRegName();
      TypeInteger type= getRegType(regName);
      return new RegWithType( regName, type );
   }}

   // ------------------------------------------------------------------------
   // registerArg()
   // ------------------------------------------------------------------------
   /** Registers a function argument and its type, so future instructions can
    * use it.
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
    * @param argName - the name of the argument
    *
    * @param type - the type of the argument
    * 
    * @return void
    *
    * @throws 
    */
   public void registerArg( String argName, TypeInteger type )
   {{
      if ( regTypeHash.containsKey(argName) ) {
	 // the argument was already registered
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_111444, "+ 
			  "code=\""+ argName+ "\". \n" );
      }
      regTypeHash.put( argName, type );
      unreadRegVec.add( argName );
      numArgs++;
      return;
   }}

   // ------------------------------------------------------------------------
   // reportType()
   // ------------------------------------------------------------------------
   /** Reports the type of a register, so it can be used by future 
    *   instructions.
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
    * @param regName - the name of the register, which must have already been
    *     created via getRegName(), and must not have already had a type
    *     reported.
    *
    * @param type - the type of the register
    * 
    * @return void
    *
    * @throws 
    */
   public void reportType( String regName, TypeInteger type )
   {{
      if ( ! regTypeHash.containsKey(regName) )  {
	 //  the register hasn't been created yet 
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_112208, code=\""+ 
			  regName+ "\". \n" );
      }
      if ( regTypeHash.get(regName) != null ) {
	 // the register already had its type reported 
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_112337, code=\""+ 
			  regName+ "\". \n" );
      }

      regTypeHash.put( regName, type );
      return;
   }}

   // ------------------------------------------------------------------------
   // getRegType()
   // ------------------------------------------------------------------------
   /**  returns the data type stored in a register
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
    * @param regName - the name of the register whose type is to be returned
    * 
    * @return - per description, or 
    *	null if the register name is not recognized.
    *
    * @throws 
    */
   public TypeInteger getRegType( String regName )
   {{
      return regTypeHash.get(regName);
   }}

   // ------------------------------------------------------------------------
   // reportRegRead()
   // ------------------------------------------------------------------------
   /**  reports that the given register has been read.  This helps the
    *  internal mechanism that tries to make sure every register created gets
    *  used (read) at least once.
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
    * @param reg - the name of the register that is read (includes the % sigil)
    *
    * @return - void
    *
    * @throws 
    */
   public void reportRegRead( String reg )
   {{
      for( int ii= 0; ii < unreadRegVec.size(); ii++ ) {
	 if ( unreadRegVec.get(ii).equals(reg) ) {
	    unreadRegVec.remove(ii);
	    return;
	 }
      }
      throw new Error( Main.PROGRAM_NAME+ 
	    ": internal error 2015apr27_111636, codes: \""+ reg+ "\".\n" );
   }}


   // ------------------------------------------------------------------------
   // carpIfRegNumReset()
   // ------------------------------------------------------------------------
   /** Reports an error (and probably a stack trace) if field regNum
    *	got reset to 1.
    * 
    * <ul>
    * <li> Note: this whole method is intended as an aid to debugging
    *
    * <li> Detailed Description: 
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * </ul>
    *
    * @param basicBlock - the basic block in question
    *
    * @param msg - a message to include in the error notification
    * 
    * @return void, or if an error is detected, does not return
    *
    * @throws 
    */
   public void carpIfRegNumReset( BasicBlockSeq basicBlock, String msg )
   {{
      boolean msgPrinted= false;
      if ( lastBasicBlock == null ) {
	 lastBasicBlock= basicBlock;
      } else if ( basicBlock == lastBasicBlock )  {
	 StringBuffer mm= new StringBuffer("");
	 mm.append( "got a new basic block: \""+ basicBlock.hashCode()+ 
		    "\", \n" ); 
	 mm.append( "\t"+ "for ftn \"" );
	 mm.append( basicBlock.ftnName()+ "\", \n" );
	 mm.append( "\t"+ "last was \""+ lastBasicBlock.hashCode()+ 
		    "\". \n" );
	 mm.append( "\t"+ "for ftn \"" );
	 mm.append( lastBasicBlock.ftnName()+ "\", \n" );
	 System.err.print( mm );
	 System.err.print( msg );
	 msgPrinted= true;
	 // TODO2: find some way to add a stack backtrace here 
      }
      if ( (regNum == 1) && !regNumMayBeOne )  {
	 if ( !msgPrinted) {
	    System.err.print( msg );
	 }
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr24_120228, codes: \n"+
			  "   regNum=1, remaining steps= "+
			  basicBlock.numRemainingSteps()+ ".\n" ); //;;
      }
      return; 
   }}


   // ========================================================================
   // Short get subroutines
   // ========================================================================
   /** @return the number of arguments */
   public int numArgs()
   {{ return numArgs; }}

   /** @return the register prefix */
   public String regPrefix()
   {{ return regPrefix; }}

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
} // end class RegContext


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

