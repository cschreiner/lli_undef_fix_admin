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

   private static BasicBlock lastBasicBlock= null;

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
	 this.prefix= prefix;
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
      retNum= 1; // per LLVM IR spec, the first register must be %1.
      regNumMayBeOne= false;
      regTypeHash= new Hashtable<String, TypeInteger>();
      numArgs= 0;
      unreadRegVec= new Vector<String>;
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
	 this.prefix= prefix;
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
      unreadRegVector.add( retVal );

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
	 # why is the register name a null string?
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_133615. \n" );
      }  

      reportRegRead( retVal ); 
      return retVal;
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
      for( String reg: regTypeHash.keys() ) {
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
      static Random randomizer= new Random();
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
	 System.out.print( "<end> len="+ knowntypeRegVector.size()+ "> \n" );
	 throw new Error( Main.PROGRAM_NAME+ 
			  ": internal error 2015apr09_133530. \n" );
      }

      // clean up and return
      return chosenReg;
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

## ===========================================================================
## Subroutine getRegType()
## ===========================================================================
# Description: returns the data type stored in a register
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on
#   $regName: the name of the register whose type should be returned
# 
# Outputs: none
#   
# Return Value: a TypeInteger instance, 
#   or undef if the register name is not recognized
#
# ============================================================================
sub getRegType
{{
   my( $this, $regName )= @_;

   my $retVal= $this->{'regTypeHashref'}->{$regName};
   return $retVal;
}}

## ===========================================================================
## Subroutine carpIfRegNumReset()
## ===========================================================================
# Description: reports an error (and probably a stack trace) if field regNum
#	got reset to 1
#
# Method: 
#
# Notes: this whole method is for debugging ;;
#
# Warnings: 
#
# Inputs: 
#   $this: the instance in question
#   $basicBlock: the basic block in question
#   $msg: a message to include in the error notification
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS, or 
#	if an error is reported, does not return
#   
# ============================================================================
sub carpIfRegNumReset
{{;;
   my( $this, $basicBlock, $msg )= @_;
   if ( !defined($last_basic_block) )  {
      $last_basic_block= $basicBlock;
   } else {
      if ( "$basicBlock" ne "$last_basic_block" )  {;;
	  my ( $mm )= "";
	 $mm.= "got a new basic block: \"$basicBlock\", \n"; 
	 $mm.= "\t" . "for ftn \"" ;
	 $mm.= $basicBlock->{'optHashref'}->{'ftnName'} . "\", \n";
	 $mm.= "\t" . "last was \"$last_basic_block\". \n";
	 $mm.= "\t" . "for ftn \"";
	 $mm.= $last_basic_block->{'optHashref'}->{'ftnName'} . "\", \n"; 
	 Carp::cluck( $mm . $msg );
      }
   }
   if ( $this->{'regNum'} == 1 and !$this->{'regNumMayBeOne'} )  {
      print $msg;
      Carp::confess ( "   regNum=1, remaining steps= " . 
	    $this->{'remainingSteps'} . ".\n" );;
   }
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: 
#   $this: the instance whose field to get
#
# Return Value: the field's value
#
# ============================================================================
# template is 5 lines long
#sub name
#{{
#   my($this)= @_;
#   return ;
#}}

# returns the number of arguments registered
sub numArgs
{{
   my($this)= @_;
   return $this->{'regNumArgs'};
}}

## ===========================================================================
## Short set subroutines
## ===========================================================================
# Description: short subroutines that only set one field
#
# Inputs: 
#   $this: the instance whose field to get
#   $xx: the field's new value
#
# Return Value: none
#
# ============================================================================
#sub name
#{{
#   my ($this, $xx)= @_;
#}}



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

#!/usr/bin/perl
#
## ****************************************************************************
## Project Name: lli_undef_fix
## Module Name: RegContext.pm
##
## Description: 
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## RegContext.pm was written by Christian A. Schreiner at University of Utah.  
## Copyright (C) 2015-2015 University of Utah.  All rights reserved. You
## may use, examine, or modify this file only in accordance with the Lesser
## GNU Public License, or, alternately, by special written arrangement with
## the author.  This file comes with no warranties.  If you use it and
## something breaks, you are solely responsible for cleaning up afterwards.
## 
## $Revision: 1.3 $
## $Date: 2014/04/04 10:25:56 $
## $Author: cas $
##
## ****************************************************************************


## ****************************************************************************
## compiler directives (use's)
use strict;
use Carp qw( confess );

use cas_listutil;

## ****************************************************************************
## package identification

package RegContext;


## ****************************************************************************
## public package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # we don't set basic constants here, as we inherit them from package main
      # But we do need to make sure they're defined properly
      if ( !defined($main::TRUE) or !defined($main::FALSE) or 
	    !defined($main::PERL_SUCCESS) or !defined($main::PERL_FAILURE) or
            !defined($main::EXIT_SUCCESS) or !defined($main::EXIT_FAILURE) or
            !defined($main::scriptname) or !defined($main::debug_flag)  )  {
         die "internal error: basic constants improperly defined ";
      }

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw( $pkgname );
      $pkgname= "RegContext";

      # ----------------------------------------------------------------------
      # other stuff

      # llvm requires the first register of a function to be %1
      use constant MIN_REG_NUM => 1;

      use vars qw ( $last_basic_block );;
      $last_basic_block= undef;
   }}

   # =========================================================================
   # subroutine END
   # =========================================================================
   sub END
   {{
      
   }}

## ****************************************************************************
## start the package

## ===========================================================================
## Subroutine new()
## ===========================================================================
# Description: creates a new instance
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $perl_class: class information (provided by PERL)
#   $prefix: prefix all variables with this string.  Should not include the 
#	sigil (%).  May be undefined if no prefix exists.
# 
# Outputs: none
#   
# Return Value: a new instance
#   
# ============================================================================
sub new
{{
   my( $perl_class, $prefix )= @_;

   my $this= {};
   bless $this, $perl_class;

   $this->init( $prefix );
   return $this;
}}

## ===========================================================================
## Subroutine initRegContext()
## ===========================================================================
# Description: initializes just as "new()" does, but acts on an
#	existing instance instead of creating a new one.
#
# Method: 
#
# Notes: 
#   * this is called "initRegContext()" instead of "init()" to
#	avoid confusion with other classes' initialization methods.
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on
#   $prefix: prefix all variables with this string.  Should not include the 
#	sigil (%).  May be undefined if no prefix exists.
# 
# Outputs: none
#   
# Return Value: TRUE
#   
# ============================================================================
sub initRegContext
{{
   my( $this, $prefix )= @_;
   $this->{'regPrefix'}= $prefix;
   $this->{'regNum'}= MIN_REG_NUM;
   $this->{'regNumMayBeOne'}= $main::TRUE;;
   $this->{'regTypeHashref'}= {}; 
	 # keys= reg names, values=TypeInteger instance
   $this->{'regNumArgs'}= 0; # the number of known arguments
   return $main::TRUE;
}}


## ===========================================================================
## Subroutine getRegName
## ===========================================================================
# Description: returns the name of a new register
#
# Inputs: 
#   $this: the instance to work on (provided by PERL)
# 
# Outputs: none
#
# Return Value: per description
#
# ============================================================================
sub getRegName
{{
   my( $this )= @_;

   my( $retVal )= "%" . $this->{'regPrefix'} . $this->{'regNum'};
   $this->{'regNum'}++;
   $this->{'regNumMayBeOne'}= $main::FALSE;;

   # note that the register exists, but we don't know its type yet.
   $this->{'regTypeHashref'}->{$retVal}= undef;

   if ( $retVal =~ m/^\s*$/ )  {
      # why is the register name a null string?
      die $main::scriptname . ": internal error 2015apr09_139631. \n";
   }
   return $retVal;
}}

## ===========================================================================
## Subroutine getPrevRegName
## ===========================================================================
# Description: gets the name of the nth previously issued register
#
# Inputs: 
#   $this: the instance to work on (provided by PERL)
#   $steps: the number of steps back to go. 0 (the default) indicates the 
#	absolute most recently issued register, 1 is the one before that, 
#	and so forth.
# 
# Outputs: none
#
# Return Value: per description
#
# ============================================================================
sub getPrevRegName
{{
   my( $this, $steps )= @_;

   my( $steps2 )= $steps;
   if ( ! defined($steps) )  { $steps2= 0; }

   if ( ($this->{'regNum'}- 1 - $steps2) < 0 )  {
      die $main::scriptname . 
	    ": internal error 2014nov24_154228, " . 
	    "codes=\"" . $this->{'regNum'} . "\", \"$steps\"\n";
   }
   my $retVal="%" . $this->{'regPrefix'} . ($this->{'regNum'}- 1- $steps2);
   if ( $retVal =~ m/^\s*$/ )  {
      # why is the register name a null string?
      die $main::scriptname . ": internal error 2015apr09_133615. \n";
   }
   return $retVal;
}}

## ===========================================================================
## Subroutine getRecentRegName
## ===========================================================================
# Description: gets the name of a recently issued register or argument.  The 
#	register/argument's type is guaranteed to be known.
#
# Inputs: 
#   $this: the instance to work on (provided by PERL)
# 
# Outputs: none
#
# Return Value: per description
#
# ============================================================================
sub getRecentRegName
{{
   my( $this )= @_;

   if ( $main::FALSE )  {
      # An old way of implementing this function that might still be useful.
      my( $limit )= 10;
      my( $max_returnable_regNum )= $this->{'regNum'}- 3;
      if ( $max_returnable_regNum < $limit )  { 
	 $limit= $max_returnable_regNum; 
      };
      my( $rr )= $max_returnable_regNum- int( rand()*$limit );
      if ( $rr < MIN_REG_NUM )  {
      die $main::scriptname . 
	       ": internal error 2014nov24_210556, code=\"$rr\"\n";
      }
      return "%" . $this->{'regPrefix'} . $rr;
   }

   # find all of the registers/arguments with known types
   my @regList;
   foreach my $reg ( keys( %{$this->{'regTypeHashref'}} ) )  {
      if ( defined($this->{'regTypeHashref'}->{$reg} ) )  {
	 push @regList, $reg;
      }
   }

   # choose one at random and return
   my $numRegs= scalar( @regList );
   if ( $numRegs < 1 )  {
      # There should be at least _some_ registers, because we forcibly create
      # 2 registers if there aren't any arguments.
      die $main::scriptname . ": internal error 2015apr09_135003. \n";
   }
   my $retVal= $regList[ int(rand()*$numRegs) ]; 
   if ( $retVal =~ m/^\s*$/ )  {
      # why is the register name a null string?
      print "reg list= < " . cas_listutil::quote2( \@regList ) . "> \n";;
      print "   num regs= $numRegs. \n";;
      die $main::scriptname . ": internal error 2015apr09_133530. \n";
   }
   return $retVal;
}}

## ===========================================================================
## Subroutine registerArg()
## ===========================================================================

# Description: registers a function argument and its type, so future
#	instructions can use it.
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work on
#   $name: name of the argument, which must not already be registered
#   $type: type of the argument (formatted as an TypeInteger instance)
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS
#   
# ============================================================================
sub registerArg
{{
   my( $this, $name, $type )= @_;

   if ( exists( $this->{'regTypeHashref'}->{$name} ) )  {
      # the argument was already registered
      die $main::scriptname . 
	    ": internal error 2015apr09_111444, code=\"$name\". \n";
   }
   $this->{'regTypeHashref'}->{$name}= $type;
   $this->{'regNumArgs'}++;
   return $main::PERL_SUCCESS;
}}

## ===========================================================================
## Subroutine reportType()
## ===========================================================================
# Description: reports the type of a register, so it can be used by future 
#	instructions
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to work on
#   $name: name of the register, which must already have been created via 
#	getName(), and not have a type reported.
#   $type: type of the register (formatted as an TypeInteger instance)
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS
#   
# ============================================================================
sub reportType
{{
   my( $this, $name, $type )= @_;

   if ( !exists( $this->{'regTypeHashref'}->{$name} ) )  {
      # the register hasn't been created yet 
      die $main::scriptname . 
	    ": internal error 2015apr09_112208, code=\"$name\". \n";
   }
   if ( defined( $this->{'regTypeHashref'}->{$name} ) )  {
      # the register already had its type reported 
      die $main::scriptname . 
	    ": internal error 2015apr09_112337, code=\"$name\". \n";
   }
   $this->{'regTypeHashref'}->{$name}= $type;
   return $main::PERL_SUCCESS;
}}

## ===========================================================================
## Subroutine getRegType()
## ===========================================================================
# Description: returns the data type stored in a register
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the instance to act on
#   $regName: the name of the register whose type should be returned
# 
# Outputs: none
#   
# Return Value: a TypeInteger instance, 
#   or undef if the register name is not recognized
#
# ============================================================================
sub getRegType
{{
   my( $this, $regName )= @_;

   my $retVal= $this->{'regTypeHashref'}->{$regName};
   return $retVal;
}}

## ===========================================================================
## Subroutine carpIfRegNumReset()
## ===========================================================================
# Description: reports an error (and probably a stack trace) if field regNum
#	got reset to 1
#
# Method: 
#
# Notes: this whole method is for debugging ;;
#
# Warnings: 
#
# Inputs: 
#   $this: the instance in question
#   $basicBlock: the basic block in question
#   $msg: a message to include in the error notification
# 
# Outputs: none
#   
# Return Value: PERL_SUCCESS, or 
#	if an error is reported, does not return
#   
# ============================================================================
sub carpIfRegNumReset
{{;;
   my( $this, $basicBlock, $msg )= @_;
   if ( !defined($last_basic_block) )  {
      $last_basic_block= $basicBlock;
   } else {
      if ( "$basicBlock" ne "$last_basic_block" )  {;;
	  my ( $mm )= "";
	 $mm.= "got a new basic block: \"$basicBlock\", \n"; 
	 $mm.= "\t" . "for ftn \"" ;
	 $mm.= $basicBlock->{'optHashref'}->{'ftnName'} . "\", \n";
	 $mm.= "\t" . "last was \"$last_basic_block\". \n";
	 $mm.= "\t" . "for ftn \"";
	 $mm.= $last_basic_block->{'optHashref'}->{'ftnName'} . "\", \n"; 
	 Carp::cluck( $mm . $msg );
      }
   }
   if ( $this->{'regNum'} == 1 and !$this->{'regNumMayBeOne'} )  {
      print $msg;
      Carp::confess ( "   regNum=1, remaining steps= " . 
	    $this->{'remainingSteps'} . ".\n" );;
   }
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: 
#   $this: the instance whose field to get
#
# Return Value: the field's value
#
# ============================================================================
# template is 5 lines long
#sub name
#{{
#   my($this)= @_;
#   return ;
#}}

# returns the number of arguments registered
sub numArgs
{{
   my($this)= @_;
   return $this->{'regNumArgs'};
}}

## ===========================================================================
## Short set subroutines
## ===========================================================================
# Description: short subroutines that only set one field
#
# Inputs: 
#   $this: the instance whose field to get
#   $xx: the field's new value
#
# Return Value: none
#
# ============================================================================
#sub name
#{{
#   my ($this, $xx)= @_;
#}}


## ===========================================================================
## Subroutine name()
## ===========================================================================
# Description: 
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   
# 
# Outputs: 
#   
#
# Return Value: 
#   
#
# ============================================================================
#sub name
#{{
#   my( )= @_;
#}}


#template is 25 lines long
## ===========================================================================
## Subroutine name()
## ===========================================================================
# Description: 
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   
# 
# Outputs: 
#   
#
# Return Value: 
#   
#
# ============================================================================
#sub name
#{{
#   my( )= @_;
#}}

## ****************************************************************************
## end this package 
package main;

# the package must return 1
1;

