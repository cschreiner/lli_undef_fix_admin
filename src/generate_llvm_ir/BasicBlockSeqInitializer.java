/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: BasicBlockSeqInitializer.java
   *
   * File Description: class BasicBlockSeqInitializer
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
import generate_llvm_ir.TypeInteger;

// ****************************************************************************
// File's primary class: BasicBlockSeqInitializer
// ****************************************************************************
/*** holds values used to initialize a BasicBlockSeq.  All members are
   * public, so the user can set them at will.
   * </ul>
   */
public class BasicBlockSeqInitializer 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
      * class variables
      * =======================================================================
      */

   /* =========================================================================
      * instance variables
      * =======================================================================
      */
   /** true if the block should set a register to a poison value early
    * in the block.
    */
   public boolean startPoison;

   /**  the number of steps (instructions) the block 
    * should contain.  The actual number may be slightly
    * higher in order to do data conversions and other semantic 
    * housekeeping. Must be > 0.
    */
   public int numSteps;

   /** initial integer type for the block.  This is required if the
    * block has no parent.  If the block has a parent, this defaults to
    * the current type of the parent.
    */
   public TypeInteger startType;

   /** final integer type for the block.  If this is the first block
    * of a function, this must be set to the function's return type.  If
    * omitted, defaults to startType.  
    */ 
   public TypeInteger stopType;

   /** name of the function in which this BasicBlockSeq resides.  If
    * omitted, is set to a special "unknown" value.  If this block has a
    * parent, any value set here will be overridden with the parent's
    * value.
    */
   public String ftnName;

   /* =========================================================================
      * constructors
      * =======================================================================
      */

   // -------------------------------------------------------------------------
   // BasicBlockSeqInitializer()
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
   private BasicBlockSeqInitializer()
   {{
      startPoison= false;
      numSteps= 10;
      startType= null;
      stopType= null;
      ftnName= "(unknown ftn)";
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */


/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class BasicBlockSeqInitializer


/* ****************************************************************************
   * templates 
   * **************************************************************************
   */

/* ****************************************************************************
   *   end of file
   * **************************************************************************
   */

