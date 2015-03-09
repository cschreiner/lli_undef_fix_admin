#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_ltgt_macro
##
## Copyright (C) 2000-2007 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
## Description:
#	routines to expand ltgt macros 
#
#   "ltgt" stands for "less-than greater-than", and refers to macros
#	represented as "<macro_name>".  A "<" preceeded by a
#	backslash does not delimit the start of a macro name; instead, 
#	the backslash is deleted.
#
#   Not all of this is implemented the way I'd ideally like it to be;
#	see my "todo:" notes for details.
#
#   CAS: todo: remove the above paragraph when all the other "todo:"'s are
#	fixed.
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.10 $
## $Date: 2007/01/12 04:36:53 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## package identification

#package cas_ltgt_macro::private;


## ****************************************************************************
## package BEGIN and END functions

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
      require cas_exit;

      # ----------------------------------------------------------------------
      # package-specific constants
      $packagename= "cas_ltgt_macro";

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
## Subroutine cas_ltgt_macro::expand()
## ===========================================================================
# Description: expands all the macros found in the input string, except those
#	preceeded by a backslash
#
# Method:
#
# Notes:
#
# Warnings:
#	Does not replace "\<" with "<", as that would prevent nested macro
#	expansion.
#
# Inputs:
#   $in_st: the input string, which presumably contains the macros to expand
#   $macro_hash_ref: a reference to a hash.  Each key is the name of a macro,
#	and the key's value is what that macro should expand to.
# 
# Outputs: none
#
# Return Value:  ( $out_st, $count )
#   where $out_st is a copy of $in_st with the macros expanded
#   and $count is the number of macros that were expanded
#
# ============================================================================
sub cas_ltgt_macro::expand()
{{
   my($st, $macro_hash_ref )= @_;

   my ( $count );

   # expand the macros
   ( $st, $count) = &expand_once ( $st, $macro_hash_ref );

   # remove "escaped" macro opening delimiters ( "\<" )
   $st= &remove_escaped_delimiters( $st );

   return ( $st, $count );
}}


## ===========================================================================
## Subroutine cas_ltgt_macro::expand_repeatedly
## ===========================================================================
# Description: expands all the macros found in the input string, just as
#	expand() does.  However, it searches the input string repeatedly
#	until no more macros remain to be expanded.  This feature handles
#	nested macros; i.e. if a macro expands into a string containing 
#	more macros, the function will also expand the new macros.
#
# Method:
#
# Notes: 
#	To prevent infinite looping if a macro proves recursive, 
#	I'm artificially limiting us to 2000 replacement passes.
#
# Warnings:
#
# Inputs:
#   $in_st: the input string, which presumably contains the macros to expand
#   $macro_hash_ref: a reference to a hash.  Each key is the name of a macro,
#	and the key's value is what that macro should expand to.
# 
# Outputs: none
#
# Return Value:  ( $out_st, $count )
#   where $out_st is a copy of $in_st with the macros expanded
#   and $count is the number of macros that were expanded
#
# ============================================================================
sub cas_ltgt_macro::expand_repeatedly
{{
   my($st, $macro_hash_ref )= @_;

   # ----------------------------------------
   # initialize

   my ( $LOOP_LIMIT )= 2000;
   my ( $loop_count );
   my ( $out_st, $expansion_count, $delta );

   $expansion_count= 0;

   # ----------------------------------------
   # expand the macros
   for ( $loop_count= 0; $loop_count < $LOOP_LIMIT; $loop_count++ )  {
      ($st, $delta)= &expand_once($st, $macro_hash_ref);
      if ( $main::debug_flag && $main::TRUE )  {
         print "delta= \"$delta\" \n";
         print "st= \"$st\" \n";
      }
      if ( $delta == 0 )  {
         goto EXPANSIONS_DONE_LABEL;
      }

      $expansion_count+= $delta;

   } #for

   # we exceeded out loop count; assume we have a recursive macro somewhere
   # CAS: todo: find a better way of doing this, and then remove the 
   #   note in the subroutine preamble.
   &cas_exit::panic ( $packagename, 
	 "Looped $loop_count times while replacing macros; " .
	 "this exceeds the limit of $LOOP_LIMIT. ",
         "Is one of the macros recursing? "  );


   # ----------------------------------------
   # clean up

   EXPANSIONS_DONE_LABEL:
   # remove "escaped" macro opening delimiters ( "\<" )
   $st= &remove_escaped_delimiters( $st );

   # return results
   return ( $st, $expansion_count );

}}


## ===========================================================================
## Subroutine expand_once()
## ===========================================================================
# Description: expands all the macros found in the input string, except those
#	preceeded by a backslash
#
# Method:
#
# Notes:  This function is intended only for use inside this package.
#
# Warnings:
#
# Inputs:
#   $in_st: the input string, which presumably contains the macros to expand
#   $macro_hash_ref: a reference to a hash.  Each key is the name of a macro,
#	and the key's value is what that macro should expand to.
# 
# Outputs: none
#
# Return Value:  ( $out_st, $count )
#   where $out_st is a copy of $in_st with the macros expanded
#   and $count is the number of macros that were expanded
#
# ============================================================================
sub expand_once()
{{
   my($in_st, $macro_hash_ref )= @_;

   my ( $macro_name, $search_pattern );
   my ( $out_st, $count, $delta );
   
   $out_st= $in_st;
   $count= 0;

   foreach $macro_name ( keys %$macro_hash_ref )  {
      $search_pattern= '<' . $macro_name . '>'; 
      $delta= 
	    $out_st =~ 
	    s/([^\\])$search_pattern/$+$$macro_hash_ref{$macro_name}/g;
      if ( $delta ne "" )  { $count += $delta; }   
   }

   return ( $out_st, $count );
}}


## ===========================================================================
## Subroutine remove_escaped_delimiters()
## ===========================================================================
# Description:
#	removes escaped macro delimiters; you can't do this until all
#	possible macros have been expanded, or any macros that the
#	delimiters happen to delimit would get expanded in the next expansion 
#	pass...
#
# Method: change all sequences of '\<' to '<'
#
# Notes:
#
# Warnings:
#
# Inputs: 
#	$st: the string to remove the escapes from
# 
# Outputs: none
#
# Return Value: the string with the escapes removed
#
# ============================================================================
sub remove_escaped_delimiters
{{
   my($st )= @_;

   $st=~ s/\\\</\</g;

   return $st;
}}
 


#template
## ===========================================================================
## Subroutine sub_name
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
# Outputs:
#
# Return Value:
#
# ============================================================================
#sub sub_name
#{{
#   my( )= @_;
#}}





## ****************************************************************************
## end this package 
package main;

# the package must return 1
1;

