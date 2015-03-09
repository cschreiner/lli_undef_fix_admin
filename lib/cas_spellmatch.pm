###!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_spellmatch.pm
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description: 
#	Facilities for comparing words that are maybe slightly misspelled.
#	For example, "Smith" and "Smyth" compare as identical.
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.10 $
## $Date: 2007/03/01 22:26:23 $
## Author: $
##
## ****************************************************************************

## ****************************************************************************
## compiler directives (use's)
use strict;

## ****************************************************************************
## package identification

package cas_spellmatch::private;

## ****************************************************************************
## package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw ( $packagename );
      $packagename= "cas_spellmatch";

      # cache of all word conversions done to date: 
      # key= word, value= converted word
      use vars qw ( %cache );

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
## Subroutine cas_spellmatch::compare
## ===========================================================================
# Description: compares the spellmatch conversions of the two words, with
#	a few additional equality tests thrown in
#
# Inputs:
#   $aa, $bb: the two words to compare
# 
# Outputs:
#
# Return Value:
#   0 if $aa and $bb pass any of the equivalency tests
#   otherwise:
#   -1 if $aa's spellmatch preceeds $bb's spellmatch ($aa < $bb)
#   +1 if $aa's spellmatch succeeds $bb's spellmatch ($aa > $bb)
#
# ============================================================================
sub cas_spellmatch::compare
{{
   my( $aa, $bb )= @_;

   my ( $aa_spellmatch )= &cas_spellmatch::cached( $aa );
   my ( $bb_spellmatch )= &cas_spellmatch::cached( $bb );

   if ( $aa eq $bb ) { return 0; }

   # identify a lot of English plurals
   if ( &compare_wo_suffix( $aa, $bb, "s" ) )  { return 0; }
   if ( &compare_wo_suffix( $aa, $bb, "es" ) )  { return 0; }

   # identify Germanic forms that sometimes add an n/en
   # remember that spellmatching has converted all vowels to 'a'.
   if ( &compare_wo_suffix( $aa, $bb, "n" ) )  { return 0; }
   if ( &compare_wo_suffix( $aa, $bb, "an" ) )  { return 0; }

   # maybe sometime devise tests to identify equal "index" vs "indices",
   # and "radius" vs "radii", "Kjerstin" vs "Kersten" vs "Kyerstin" vs
   # "Carsten", &c.

   # ok, they aren't equal, see which preceeds which
   return $aa_spellmatch cmp $bb_spellmatch;
}}

## ===========================================================================
## Subroutine compare_wo_suffix
## ===========================================================================
# Description: removes the given suffix, if any, from each word, then
#	compares them
#
# Inputs:
#   $aa, $bb: the two words to compare
#   $suffix: the suffix to try to remove from each string
# 
# Outputs:
#
# Return Value: TRUE if they compare identical, otherwise FALSE
#
# ============================================================================
sub compare_wo_suffix
{{
   my( $aa, $bb, $suffix )= @_;

   my ( $new_aa )= $aa;
   my ( $new_bb )= $bb;

   $new_aa=~ s/$suffix$//;
   $new_bb=~ s/$suffix$//;

   if ( $new_aa eq $new_bb ) { return $main::TRUE };
   return $main::FALSE;
}}


## ===========================================================================
## Subroutine cas_spellmatch::cached
## ===========================================================================
# Description: same as ::simple, but caches the answer to avoid recomputation
#
# Note: consider adding ability (somehow) to map things like:
#   J vs Y: as in "Josef" vs "Yosef"
#   I vs J: as in Iovah vs Jehovah
#
# Inputs:
#   $word: a string containing the word to convert
# 
# Outputs: none
#
# Return Value: its spellmatch encoding
#
# ============================================================================
sub cas_spellmatch::cached
{{
   my($word )= @_;

   if ( ! exists ( $cache{$word} ) )  { 
      $cache{$word}= cas_spellmatch::simple ( $word );
   }

   return $cache{$word}; 
}}


## ===========================================================================
## Subroutine cas_spellmatch::simple
## ===========================================================================
# Description: converts a word into its simple spellmatch equivalent
#
# CAS todo: figure out why "John" doesn't match "Johan".
#
# Inputs:
#   $word: a string containing the word to convert
# 
# Outputs: none
#
# Return Value: its spellmatch encoding
#
# ============================================================================
sub cas_spellmatch::simple
{{
   my( $word )= @_;

   # within this routine, we think of the "encoding" as a "compression"
   my ( $compression )= lc( $word );

   # . . . . . . . . .
   # compress special sequences before anything else changes
   $compression=~ s/^mac/mc/; # for MacDonald vs McDonald

   # decide if we should interpret a "c" as a "k" or an "s"
   $compression=~ s/ce/se/g;
   $compression=~ s/ci/si/g;
   # Note: we convert remaining c's to k's later, after removing ch and such.

   # CAS note: consider adding something to map "tion", "sion" and such to a
   # common sequence.

   # . . . . . . . . .
   # compress vowels
   # note that y is considered a vowel unless at the beginning of a word
   {
      my ( $y_flag )= $main::FALSE;
 
      if ( $compression =~ m/^y/ )  {
	 $compression=~ s/^y//g;
         $y_flag= $main::TRUE;
      }
      $compression=~ tr/aeiouy/a/s;
      if ( $y_flag )  {
         $compression= "y" . $compression;
      }
   }

   # . . . . . . . . .
   # remove duplicated consonants
   $compression=~ s/ (\w) \1 /$1/xg;

   # . . . . . . . . .
   # handle special consonant groups

   $compression=~ s/sch/sh/g;
   $compression=~ s/chr/kr/g; # Fixes "Christiansen" vs "Christenson"

   # fixes "chateu" and "chalet" into what they sound like
   $compression=~ s/ch/sh/g; 

   # fixes John vs Jon, Stahl vs Stall. And yes, it can leave a "aa" behind
   # if the original character sequence was "aha".
   $compression=~ s/ah/a/g; 

   $compression=~ s/z/s/g; # fixes Janzen vs Janssen
   $compression=~ s/ph/f/g; # fixes graph vs graf
   $compression=~ s/v/f/g;  # removes ambiguities in "Steven" vs "Stefen"
   $compression=~ s/ps/s/g; # removes silent p in "psychiatrist"
   $compression=~ s/pn/n/g; # removes silent p in "pneumonia"
   $compression=~ s/pf/f/g; # removes english silent p in "Pflug" and "Pfaff"
   $compression=~ s/kn/n/g; # fixes kn in "knock", "knight"
   # note: "ck" was fixed above
   $compression=~ s/ck/k/g; # fixes ck in "knock"

   # fixes "Chudleigh", "might", "thought", "ghost", &c.
   $compression=~ s/gh//g; 

   $compression=~ s/mn/m/g; # fixes "mnemonic"

   # remove misleading suffixes
   $compression=~ s/a$//; # remove silent e's and other suffixed vowels
   
   # clean up and return
   return $compression;
}}

#template
## ===========================================================================
## Subroutine sub_name
## ===========================================================================
# Description:
#
# Inputs:
# 
# Outputs:
#
# Return Value:
#
# ============================================================================
#sub sub_name
#{{
#   my( )= @_;
#}}

## ===========================================================================
## Short get subroutines
## ===========================================================================
# Description: short subroutines that only get one field
#
# Inputs: none
#
# Return Value: the field's value
#
# ============================================================================
#sub sub_name
#{{
#   return ;
#}}

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




## ****************************************************************************
## end this package 
package main;

# the package must return 1
1;

## ****************************************************************************
## ****************************************************************************

