/*.. 
   * Project Name: lli_undef_fix
   *
   * File Name: TypeInteger.java
   *
   * File Description: class TypeInteger
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


// ****************************************************************************
// File's primary class: TypeInteger
// ****************************************************************************
/** Holds information on a bitwidth, such as its maximum and minimum
 *	integer values, a random number generator, and so forth.
 *
 * TODO3: adjust this (and the rest of this class) to handle 64+-bit ints.
 * This will be non-trivial under java, as LLVM IR integers are unsigned, and
 * a java signed long can only get up to (2**63-1).
 * 
 * <ul>
 * <li> Detailed Description: 
 *
 * <li> Algorithm: 
 * </ul>
 */
public class TypeInteger 
{

/* ############################################################################
   * primary class' direct contents
   * ##########################################################################
   */

   /* =========================================================================
    * class variables
    * =========================================================================
    */
   public static final int MAX_WIDTH= 32;
   public static final int MIN_WIDTH= 1;
   public static final int PREFERRED_MIN_WIDTH= 6;

   private static Random randomizer= new Random();

   /* =========================================================================
    * instance variables
    * =========================================================================
    */
   long maxVal, minVal;
   String name;

   /* =========================================================================
    * constructors
    * =========================================================================
    */

   // -------------------------------------------------------------------------
   // TypeInteger()
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
      * <li> No inputs.  The bitwidth is chosen at random.
      * </ul>
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   private TypeInteger()
   {{
      //System.err.println ( "Internal error: "+
      //   "unexpected call to default constructor for TypeInteger." );
      //System.exit(-127);

      bitWidth= randomizer.nextInt(MAX_WIDTH- PREFERRED_MIN_WIDTH)+ 
	    PREF_MIN_WIDTH;

      constructorHelper();
   }}

   // -------------------------------------------------------------------------
   // TypeInteger(int)
   // -------------------------------------------------------------------------
   /*** commonly used constructor
      *
      * <ul>
      * <li> Detailed Description: 
      *
      * <li> Algorithm: 
      *
      * <li> Reentrancy: unknown
      *
      * </ul>
      * @param targetWidth - the preferred bitwidth to work with.
      * 
      * @return - n/a (it's a constructor!)
      *
      * @throws
      */
   private TypeInteger( int targetWidth )
   {{
      if ( (targetWidth < MIN_WIDTH) || (targetWidth > MAX_WIDTH) )  {
	 throw new Error( Main.PROGRAM_NAME+ 
	       ": internal error 2015apr24_122941, bitwidth out of range. \n" );
      }
      bitWidth= targetWidth;
      constructorHelper();
   }}


   /* =========================================================================
      * methods
      * =======================================================================
      */

   // ------------------------------------------------------------------------
   // constructorHelper()
   // ------------------------------------------------------------------------
   /**  initializes fields common to most (all) constructors
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
   private void constructorHelper()
   {{
      maxVal= 1;
      for (int ii= 1; ii < bitWidth; ii++ )  {
	 maxVal*= 2;
      }
      maxVal-= 1;

      minVal= 0;
      name= "i"+ bitWidth;
      return;
   }}

   // ------------------------------------------------------------------------
   // getRandVal()
   // ------------------------------------------------------------------------
   /**  Gets a random value for this size of integer, using the full range 
    *	of the possible integer values.
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
   public long getRandVal()
   {{
      return getRandVal(0);
   }}

   // ------------------------------------------------------------------------
   // getRandVal(int)
   // ------------------------------------------------------------------------
   /**  returns a random value for this size of integer
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
    * @param margin - 
    *	Do not return a value within this distance of the min or max value.
	Often specified as 0.  This guarantees that the value returned can be 
	incremented or decremented by up to this amount without overflow.
    * 
    * @return - 
    *
    * @throws 
    */
   public long getRandVal( int margin )
   {{
      long range= maxVal- minVal- 2* margin;

      if ( 0 ) {
	 System.out.print( "max=\""+ maxVal+ "\", min=\""+ minVal+
			   "\", margin=\""+ margin+ "\"\n" );
	 System.out.print( "   range=\""+ range+ "\"\n" );
      }

      long val= randomizer.nextInt( range )+ minVal+ margin;
      return val;
   }}

   // ------------------------------------------------------------------------
   // getRandShiftVal()
   // ------------------------------------------------------------------------
   /** Returns a random number from 0 to this instance's bitwidth. 
    * This is the legal range that a register may be shifted right or left.
    * 
    * <ul>
    * <li> Detailed Description: 
    *	Strange, shifting by the width of the register is sometimes allowed 
    *	and sometimes not.  For example, 
    *	   %9= lshr i13 %8, 13
    *	   %11= ashr  i13 %10, 13
    *	are ok, but
    *	   %9= lshr exact i13 %8, 13
    *	and
    *	   %11= ashr exact i13 %10, 13
    *	are not.  Even though the 'exact' flag is supposted to generate
    *	poison if 0 bits are shifted out, its presence causes
    *	execution to halt with an "overshift" error.  Officially, shifting by 
    *	the register width or larger yields an undefined result (NOT undefined 
    *	behavior). Is the fact that this is sometimes allowed and sometimes 
    *	not an error in the interpreter? TODO2: look into this.  If
    *	the result is undefined, I assume the interpreter should replaced it 
    *	with a random value of appropriate width.
    *
    * <li> Algorithm: 
    *
    * <li> Reentrancy: unknown
    *
    * <li> No inputs.
    * </ul>
    * 
    * @return per description
    *
    * @throws 
    */
   public int getRandShiftVal()
   {{
      int val= randomizer( bitWidth );
      return val;
   }}

## ===========================================================================
## Subroutine compareTo()
## ===========================================================================
# Description: compares this instance to another one
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the primary instance to act on (provided by PERL)
#   $that: the other instance to compare
# 
# Outputs: 
#   
# Return Value: 
#   -1 if this < that
#   0 if this == that
#   1 if this > that
#
# ============================================================================
sub compareTo
{{
   my( $this, $that )= @_;

   # TODO3: if we add non-integer types in the future, add something somewhere
   # to insure both instances being compared are integers.

   if ( ref($that) ne "TypeInteger" )  {
      confess ( $main::scriptname . ": internal error 2015apr10_095356, " . 
	    "code=\"" . ref($that) . "\"\n" );
   }

   return $this->getBitwidth() <=> $that->getBitwidth();
}}

## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: $perl_this: the instance in question (provided by PERL)
#
# Return Value: the field's value
#
# ============================================================================
#sub sub_name {{ my($perl_this)= @_; return $perl_this->{'xx'}; }}
sub getBitwidth {{ my($perl_this)= @_; return $perl_this->{'bitwidth'}; }}
sub getMaxVal {{ my($perl_this)= @_; return $perl_this->{'maxVal'}; }}
sub getMinVal {{ my($perl_this)= @_; return $perl_this->{'minVal'}; }}
sub getName {{ my($perl_this)= @_; return $perl_this->{'name'}; }}

## ===========================================================================
## Short set subroutines
## ===========================================================================
# Description: short subroutines that only set one field
#
# Inputs: the field's new value
#
# Return Value: none
#
# ============================================================================
#sub sub_name
#{{
#   my ($ii)= @_;
#}}




/* ############################################################################
   * trivially simple subclasses
   * ##########################################################################
   */

/* ****************************************************************************
   * end of primary class
   * **************************************************************************
   */
} // end class TypeInteger


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
## Module Name: TypeInteger.pm
##
## Description: 
#	Holds information on a bitwidth, such as its maximum and minimum
#	integer values, a random number generator, and so forth.
##
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## TypeInteger.pm was written by Christian A. Schreiner at University of Utah. 
## Copyright (C) 2014-2014 University of Utah.  All rights reserved. You
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
use Carp;

## ****************************************************************************
## package identification

package TypeInteger;


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
      $pkgname= "TypeInteger";

      # ----------------------------------------------------------------------
      # other stuff

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
## Subroutine new
## ===========================================================================
# Description: creates a new instance
#
# Inputs:
#   $class: information about the class (provided by perl)
#   $target_width: an integer indicating the preferred width to work with.
#	If undefined, a random value will be chosen.
# 
# Outputs: none
#
# Return Value: a new instance
#
# ============================================================================
sub new
{{
   my( $class, $target_width )= @_;

   my( $perl_this )= {};
   bless $perl_this, $class;

   # TODO3: adjust this to exactly handle 64+-bit integers
   use constant MAX_WIDTH => 32;
   my( $abs_min_width )= 1;
   my( $pref_min_width )= 6;
   my( $width );
   if ( $target_width eq "" )  {
      $width= int( rand()*(MAX_WIDTH-$pref_min_width) + $pref_min_width );
   } else {
      if ( $target_width < $abs_min_width or $target_width > MAX_WIDTH )  {
	 die $main::scriptname . ": bitwidth out of range. \n";
      }
      $width= $target_width;
   }
   $perl_this->{'bitwidth'}= $width;
   $perl_this->{'maxVal'}= 2** $width- 1;
   $perl_this->{'minVal'}= 0;
   $perl_this->{'name'}= "i" . $width;

   return $perl_this;
}}

## ===========================================================================
## Subroutine getRandVal
## ===========================================================================
# Description: returns a random value for this size of integer
#
# Inputs:
#   $perl_this: the instance in question (provided by PERL)
#   $margin: do not return a value within this distance of the min or max value.
#	Defaults to 0.  This guarantees that the value returned can be 
#	incremented or decremented by up to this amount without overflow.
# 
# Outputs: none
#
# Return Value: per Description
#
# ============================================================================
sub getRandVal
{{
   my( $perl_this, $margin )= @_;

   my( $margin2 )= $margin;
   if ( !defined($margin) )  { $margin2= 0; }

   my( $range )= $perl_this->{'maxVal'} - $perl_this->{'minVal'}- 2*$margin2;
   if ( $main::debug_flag )  {
     print "max=\"" . $perl_this->{'maxVal'} . "\", min=\"" . 
	   $perl_this->{'minVal'} . "\", margin2=\"$margin2\"\n";
     print "range=\"$range\"\n";
   }
   my $val= int( rand() * $range+ $perl_this->{'minVal'}+ $margin2 );

   return $val;
}}

## ===========================================================================
## Subroutine getRandShiftVal
## ===========================================================================
# Description: returns a random number from 0 to this instance's bitwidth. 
#	This is the legal range that a register may be shifted right or left.
#
# Note: strange, shifting by the width of the register is sometimes allowed 
#	and sometimes not.  For example, 
#	   %9= lshr i13 %8, 13
#	   %11= ashr  i13 %10, 13
#	are ok, but
#	   %9= lshr exact i13 %8, 13
#	and
#	   %11= ashr exact i13 %10, 13
#	are not.  Even though the 'exact' flag is supposted to generate
#	poison if 0 bits are shifted out, its presence causes
#	execution to halt with an "overshift" error.  Officially, shifting by 
#	the register width or larger yields an undefined result (NOT undefined 
#	behavior). Is the fact that this is sometimes allowed and sometimes 

#	not an error in the interpreter? TODO2: look into this.  If
#	the result is undefined, I assume the interpreter should replaced it 
#	with a random value of appropriate width.
#
# Inputs:
#   $perl_this: the instance in question (provided by PERL)
# 
# Outputs: none
#
# Return Value: a number n such that 0 <= n < bitwidth
#
# ============================================================================
sub getRandShiftVal
{{
   my( $perl_this )= @_;

   my( $val )= int( rand() * ($perl_this->{'bitwidth'}) );
   return $val;
}}

## ===========================================================================
## Subroutine compareTo()
## ===========================================================================
# Description: compares this instance to another one
#
# Method: 
#
# Notes: 
#
# Warnings: 
#
# Inputs: 
#   $this: the primary instance to act on (provided by PERL)
#   $that: the other instance to compare
# 
# Outputs: 
#   
# Return Value: 
#   -1 if this < that
#   0 if this == that
#   1 if this > that
#
# ============================================================================
sub compareTo
{{
   my( $this, $that )= @_;

   # TODO3: if we add non-integer types in the future, add something somewhere
   # to insure both instances being compared are integers.

   if ( ref($that) ne "TypeInteger" )  {
      confess ( $main::scriptname . ": internal error 2015apr10_095356, " . 
	    "code=\"" . ref($that) . "\"\n" );
   }

   return $this->getBitwidth() <=> $that->getBitwidth();
}}

## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: $perl_this: the instance in question (provided by PERL)
#
# Return Value: the field's value
#
# ============================================================================
#sub sub_name {{ my($perl_this)= @_; return $perl_this->{'xx'}; }}
sub getBitwidth {{ my($perl_this)= @_; return $perl_this->{'bitwidth'}; }}
sub getMaxVal {{ my($perl_this)= @_; return $perl_this->{'maxVal'}; }}
sub getMinVal {{ my($perl_this)= @_; return $perl_this->{'minVal'}; }}
sub getName {{ my($perl_this)= @_; return $perl_this->{'name'}; }}

## ===========================================================================
## Short set subroutines
## ===========================================================================
# Description: short subroutines that only set one field
#
# Inputs: the field's new value
#
# Return Value: none
#
# ============================================================================
#sub sub_name
#{{
#   my ($ii)= @_;
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

