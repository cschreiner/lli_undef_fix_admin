#!/usr/bin/perl
#
## ****************************************************************************
## Module Name: cas_equalfile.pm
##
## Copyright (C) 2000-2013 Christian Schreiner.  All rights reserved. You may
## use, examine, or modify this file only in accordance with the Lesser GNU
## Public License, or, alternately, by special written arrangement with the
## author.  This file comes with no warranties.  If you use it and something
## breaks, you are solely responsible for cleaning up afterwards.
##
=pod

Description:

    Routines for reading in data from an "equal" text file
 	i.e. a text file where each line contains one field, in format: 
 
 	<field_name> = <field_value>
 
 	or
 
 	<field_name> << <end_of_field_string>
 	<field_value>
 	<end_of_field_string>
 
 	if the field is more than one line long.  
 
 
    '#' begins a comment line, except inside a multiline field.
    Whitespace around the field name, a singleline field value, and the equals
 	sign or "<<" sign is ignored.  Whitespace beginning or ending a 
 	multiline field value is preserved.
 
    This module does no checking on the data read in, sorry. :-)  It does do 
 	some checking for valid field names.  
 
  Stuff to do:
  	see the end of the file

=cut
#
#
## ****************************************************************************

## ****************************************************************************
## Revision Control Information (customized for RCS)
##
## $Revision: 1.33 $
## $Date: 2013/07/15 15:58:40 $
## Author: $
##
## ****************************************************************************


## ****************************************************************************
## package identification

package cas_equalfile::internal;

use strict; 
use FileHandle;


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
            !defined($main::scriptname) or !defined($main::debug_flag) )  {
         die "internal error: basic constants improperly defined ";
      }

      # ----------------------------------------------------------------------
      # package prerequisites (use's and require's)
      require cas_exit;
      require cas_str;

      # ----------------------------------------------------------------------
      # package-specific constants
      use vars qw ( $packagename );
      $packagename= "cas_equalfile::internal";

      # ----------------------------------------------------------------------
      # other stuff

      # the name of the file we're reading
      use vars qw ( $filename );
      $filename= "";

      # a description of the file we're reading, for debugging purposes.  
      use vars qw ( $file_description );
      $file_description= "";

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
## Subroutine cas_equalfile::read
## ===========================================================================
# Description: 
#	reads in an equalfile, as defined at the beginning of this source file.
#
# Method:
#
# Notes: if an error happens, this function panics.  Maybe in the future
#	we should change this to printing an error message and returning.
#
# Warnings:
#
# Inputs: 
#   $requested_filename: the file to read
#   $requested_file_description: a description of the file used for 
#	error messages.  It must 
#	error messages. It must make sense
#       in front of the word "file"; for example, making the description
#       to "modem hardware" will cause the error messages to talk
#       about the "modem hardware file".
#   $field_description_hash_ref: a reference to a hash.  Each key in the 
#	hash is the name of a valid field.  Each key's data contains 
#	information about valid values for the field.  (E.g. is the 
#	field required, what is the field's default value, does the 
#	field have to be numeric, must the field's value be selected 
#	from a list, can the field be defined multiple times (overriding 
#	previous values or appending to previous values), &c.)  This data 
#	is ignored for now.
#
#	If this hash is empty, the function does no checking.
#
# Outputs:
#   %dest_hash: a reference to the destination hash.  This is where pairs 
#	of (fieldname, fieldvalue) are written.  This function assumes the
#	hash is initially empty.
#
# Return Value: $PERL_SUCCESS or $PERL_FAILURE
#
# ============================================================================
sub cas_equalfile::read
{{
   my($dest_hash_ref, $requested_filename, $requested_file_description, 
	 $field_description_hash_ref )= @_;
   my(@field_name_list)= keys (%$field_description_hash_ref);


   my ( $error_found ) = $main::FALSE;

   # ----------------------------------------------------------------------
   # store global data
   $filename= $requested_filename;
   $file_description= $requested_file_description;

   # ----------------------------------------------------------------------
   # open the file
   &cas_equalfile::reader::open_file( $filename, $file_description );

   # ----------------------------------------------------------------------
   # read the file contents
   my ( $line );
   my ( $field_name, $field_value );
   my ( $grep_result );
   while ( $main::TRUE )  {
      ($field_name, $field_value)= &read_next_field();
      #print "found field \"$field_name\"=\"$field_value\" \n";;

      if ( &cas_equalfile::reader::is_eof()  )  { 
         last;
      }

      # see if the field name is valid
      if ( ! exists($$field_description_hash_ref{$field_name}) )  {
	 # the field name is not valid
	 &cas_exit::panic ( $packagename,
	       "unknown field name \"$field_name\" at line " . 
	       &cas_equalfile::reader::get_line_num() . " of ",
	       "$file_description file \"$filename\" " );
      }
      if ( exists($$dest_hash_ref{$field_name}) )  {
	 # the field name is illegally repeated
	 &cas_exit::panic ( $packagename,
	       "redefinition of field name \"$field_name\" at line " . 
	       &cas_equalfile::reader::get_line_num() . " of ",
	       "$file_description file \"$filename\" " );
      }

      # store the result
      $$dest_hash_ref{$field_name}= $field_value;
   }

   # ----------------------------------------------------------------------
   # close the file
   &cas_equalfile::reader::close_file();

   # ----------------------------------------------------------------------
   # clear global data
   $filename= "";
   $file_description= "";

   # ----------------------------------------------------------------------
   # return success
   #print "read file \"$requested_filename\", " . 
   #	 scalar(keys(%$dest_hash_ref)) . " fields so far. \n";;
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine read_next_field()
## ===========================================================================
# Description: gets the next field from the file we're reading
#
# Method: reads a line and parses it into a field and a value.  Reads
#	additional lines if the field value spans more than one line.
#
# Notes: returns 2 undefined strings if EOF
#
# Warnings:
#
# Inputs: none
# 
# Outputs: none
#
# Return Value: ($field_name, $field_value)
#	strings containing the field's name and the field's value
#
# ============================================================================
sub read_next_field
{{
   #my( )= @_;

   my ( $line );
   $line= &cas_equalfile::reader::read_data_line ( );

   # see if we hit EOF
   if ( &cas_equalfile::reader::is_eof() ) { 
      return ( undef, undef );      
   }

   # ----------------------------------------------------------------------
   # parse the line into a field name and a field value
   my ( $field_name, $field_value );
   my ( $start_line_num ) = &cas_equalfile::reader::get_line_num();

   # --------------------------------------------------
   # is this a single line field or a multi-line field?
   if ( $line =~ m/^\s*\w*\s*=/ )  {
      ($field_name, $field_value)= &parse_simple_field( $line );  
   } elsif ( $line =~ m/^\s*\w*\s*\<\</ )  { 
      ($field_name, $field_value)= &parse_multiline_field($line);
   }

   # --------------------------------------------------
   # make sure the parsing functions didn't error out on us
 
   my ( $end_line_num ) = &cas_equalfile::reader::get_line_num();
   
   # check field name
   if ( !defined($field_name) )  {
      &cas_exit::panic ( $packagename,
            "internal error " . $packagename . ":230.30dec1999, \n",
	    "$file_description file ",
	    "\"$filename\" line " . &cas_equalfile::reader::get_line_num() . 
	    ". " );
      return $main::PERL_FAILURE;
   }

   # check for a missing field value
   if ( !defined($field_value) )  {
      &cas_exit::panic ( $packagename, 
            "internal error " . $packagename . ":239.30dec1999, \n",
	    "$file_description file ",
	    "\"$filename\" line " . &cas_equalfile::reader::get_line_num() . 
	    ". " );
      return $main::PERL_FAILURE;
   }

   # ----------------------------------------------------------------------
   # get rid of leading and trailing whitespace
   $field_name= &cas_str::essence( $field_name );
   $field_value= &cas_str::essence( $field_value );

   # ----------------------------------------------------------------------
   # return what we read
   return ( $field_name, $field_value );
}}

## ===========================================================================
## Subroutine parse_simple_field()
## ===========================================================================
# Description: parses a line containing a simple field
#	i.e. one of format "<field_name> = <field_value>"
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs: 
#   $line: the line that the field is on (i.e. the string containg 
#	the characters in the line, NOT the line number within the file!)
# 
# Outputs: none
#
# Return Value: ($field_name, $field_value)
#
# ============================================================================
sub parse_simple_field()
{{
   my( $line )= @_;

   # ----------------------------------------------------------------------
   # parse the line
   my( $field_name, $field_value ) = split ( /=/, $line, 2 );

   # ----------------------------------------------------------------------
   # check our parsing

   # check for a missing field value
   if ( !defined($field_value) )  {
      &cas_exit::panic ( $packagename,
	    "no field value found in $file_description file ",
	    "\"$filename\" line " . &cas_equalfile::reader::get_line_num() . 
	    ". " );
      return ( undef, undef );
   }

   # check for a missing field name (theoretically this should be impossible)
   if ( !defined($field_name) )  {
      &cas_exit::panic ( $packagename, 
	    "no field name found in $file_description file ",
	    "\"$filename\" line " . &cas_equalfile::reader::get_line_num() . 
	    ". " );
      return ( undef, undef );
   }

   # ----------------------------------------------------------------------
   # get rid of leading and trailing whitespace
   $field_name= &cas_str::essence( $field_name );
   $field_value= &cas_str::essence( $field_value );

   # ----------------------------------------------------------------------
   # return the result
   return ($field_name, $field_value );
}}

## ===========================================================================
## Subroutine parse_multiline_field()
## ===========================================================================
# Description: parses a multi-line field, i.e. a field formatted like this:
#	field_name <<unique_text
#	field_contents_line_1
#	field_contents_line_2
#	...
#	field_contents_line_n
#	unique_text
#
#    Experienced shell users will recognize the simplarity to a shell 
#	here-document.  Note that inside the function we call the 
#	unique_text the "end_of_field_marker".
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $line: the line that the field is on (i.e. the string containg 
#	the characters in the line, NOT the line number within the file!)
# 
# Outputs: none
#
# Return Value: ($field_name, $field_value)
# 
# ============================================================================
sub parse_multiline_field
{{
   my($line)= @_;

   if ( $main::debug_flag && $main::FALSE )  { 
      print "Line " . &cas_equalfile::reader::get_line_num() . 
	    "appears to start a multiline field \n";
   }

   # ----------------------------------------------------------------------
   # parse the line
   my( $field_name, $end_of_field_marker ) = split ( /\<\</, $line, 2 );

   # remove whitespace from the end-of-field marker to avoid non-obvious 
   # mismatches
   $end_of_field_marker= &cas_str::essence( $end_of_field_marker );

   # ----------------------------------------------------------------------
   # check our parsing

   # check for a missing end-of-field marker
   if ( !defined($end_of_field_marker) )  {
      &cas_exit::panic ( $packagename,
	    "no end-of-field marker found in $file_description file ",
	    "\"$filename\" line " . &cas_equalfile::reader::get_line_num() . 
	    ". " );
      return ( undef, undef );
   }

   # check for a missing field name (theoretically this should be impossible)
   if ( !defined($field_name) )  {
      &cas_exit::panic ( $packagename, 
	    "no field name found in $file_description file ",
	    "\"$filename\" line " . &cas_equalfile::reader::get_line_num() . 
	    ". " );
      return ( undef, undef );
   }

   # ----------------------------------------------------------------------
   # read in the lines containing the field's value
   my( $field_value );

   {
      my( $value_line );
      my( $safety_counter )= 0;
      my( $start_line_num )= &cas_equalfile::reader::get_line_num();
      my( $this_line_num );

      MULTILINE_LABEL: 
      while ( $main::TRUE )  {
	 $value_line= &cas_equalfile::reader::read_raw_line();

         # is this the end of the file?
         $this_line_num= &cas_equalfile::reader::get_line_num();
         if ( &cas_equalfile::reader::is_eof() )  {
            &cas_exit::panic ( $packagename, 
               "unexpected end-of-file at line $this_line_num while reading " .
               "multi-line field ",
               "beginning on line $start_line_num of $file_description ",
               "file \"$filename\"; ", 
               "expected end-of-field marker= \"$end_of_field_marker\"" );
         }

         # is this the end of the field?
         {
            my ($potential_marker)= &cas_str::essence($value_line);
            if ( $main::debug_flag && $main::FALSE )  {
	       print "potential_marker= \"$potential_marker\" \n";
            }
	    if ( $potential_marker eq $end_of_field_marker )  {
	       last MULTILINE_LABEL;
	    } 
         } 

         $field_value.= $value_line;

         # check for run-on field values
         if ( $main::debug_flag && $main::TRUE )  {
            $safety_counter++;
            if ( $safety_counter >= 100 )  {
               print $main::scriptname . ": " . $packagename . 
		     ": possible run-on multi-line field \n" . 
		     "from line $start_line_num to line $this_line_num " . 
                     "of $file_description \n" .
                     "file \"$filename\". \n";
               $safety_counter= 0;
            }
         }
            
      }
   }


   # ----------------------------------------------------------------------
   # get rid of leading and trailing whitespace
   $field_name= &cas_str::essence( $field_name );

   # ----------------------------------------------------------------------
   # return the result
   return ($field_name, $field_value );
}}

## ===========================================================================
## Subroutine write_generic()
## ===========================================================================
# Description: 
#	writes out an equalfile hash to the specified destination.  If the
#	destination is a file handle, the file must already be open for
#	writing.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $dest_ref: a reference to the requested destination.  If it is a 
#	reference to a string, the ftn will overwrite the string.  If it
#	is a filehandle (e.g. reference to an anonymous hash), the ftn
#	will write to that file.
#   $src_hash_ref: a reference to a hash containing the data to write to the
#	file.  The hash is organized the same way as the destination hash
#	($dest_hash_ref) in cas_equalfile::read().
# 
# Outputs: none
#
# Return Value:
#   PERL_SUCCESS or PERL_FAILURE
#
# ============================================================================
sub write_generic
{{
   my( $dest_ref, $src_hash_ref )= @_;

   # ----------------------------------------------------------------------
   # figure out how to write the destination

   # CAS todo: consider putting these constants into the BEGIN function to
   # save processor time
   my ( $DEST_TYPE__STRING )= 5438;  # number selected at random
   my ( $DEST_TYPE__FILEHANDLE ) = $DEST_TYPE__STRING+1;

   my ( $dest_type );
   if ( ref($dest_ref) eq "SCALAR" )  {
      $dest_type= $DEST_TYPE__STRING;
      $$dest_ref= "";
   } elsif ( ref($dest_ref) eq "FileHandle" )  {
      $dest_type= $DEST_TYPE__FILEHANDLE;
   } else {
      &cas_exit::panic ( $packagename, "Internal error 513.23nov2000, ", 
	    "Unknown type of destination, code= \"" . ref($dest_ref) . "\". " );
   }

   # ----------------------------------------------------------------------
   # write out the data

   # figure out which order to write the fields in
   my ( @field_name_list ) = sort (keys %$src_hash_ref);

   # write each field   
   my ($field, $value, $end_of_field_marker);
   foreach $field ( @field_name_list )  {
      $value= $$src_hash_ref{$field};
      if ( $value =~ m/^\S.*\S$/ && $value !~ m/\n/ )  {
         # this value can be written out on a single line
         # Reasoning: it has no preceeding or trailing whitespace, and it
         # contains only one line.
         #print "foo1";;
         #print "$field= $value \n";;
         
         if ( $dest_type == $DEST_TYPE__STRING )  {
            $$dest_ref.= "$field= $value \n";
         } elsif ( $dest_type == $DEST_TYPE__FILEHANDLE )  {
	    #$dest_ref->print "foo2";;
	    #$dest_ref->print "$field= $value \n";
	    print $dest_ref "$field= $value \n";
         };
         next;
      }

      # this value must be written out in multiline format 
      $end_of_field_marker= &generate_end_of_field_marker( $value );
      if ( $dest_type == $DEST_TYPE__STRING )  {
	 $$dest_ref.= "$field << $end_of_field_marker \n" .
	       $value . "\n" . 
	       $end_of_field_marker . " \n";
      } elsif ( $dest_type == $DEST_TYPE__FILEHANDLE )  {
	 print $dest_ref "$field << $end_of_field_marker \n" .
	       $value . "\n" . 
	       $end_of_field_marker . " \n";
      };
      
      #print "$field << $end_of_field_marker \n" .
      #      $value . "\n" . 
      #      $end_of_field_marker . " \n";;
      #if ( $dest_ref == $DEST_TYPE__FILEHANDLE )  {
      #   $dest_ref->print "$field << $end_of_field_marker \n" .
      #         $value . "\n" . 
      #         $end_of_field_marker . " \n";
      #}
   }


   # ----------------------------------------------------------------------
   # clean up and return
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine cas_equalfile::write_to_st()
## ===========================================================================
# Description: 
#	writes a equalfile into a string
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $$dest_st_ref: a reference to the destination string
#   $src_hash_ref: a reference to a hash containing the data to write to the
#	file.  The hash is organized the same way as the destination hash
#	($dest_hash_ref) in cas_equalfile::read().
# 
# Outputs: none
#
# Return Value:
#   PERL_SUCCESS or PERL_FAILURE
#
# ============================================================================
sub cas_equalfile::write_to_st
{{
   my( $dest_st_ref, $src_hash_ref )= @_;

   &cas_exit::assert ( ref($dest_st_ref) eq "SCALAR", $packagename,
         "$packagename: Internal error 615.2dec2000, code= \"" . 
	 ref($dest_st_ref) . "\". " );

   return &write_generic ( $dest_st_ref, $src_hash_ref );
}}


## ===========================================================================
## Subroutine cas_equalfile::write()
## ===========================================================================
# Description: 
#	writes out an equalfile, as defined at the beginning of this 
#	source file.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $requested_filename: the file to write to
#   $requested_file_description: a description of the file used for 
#	error messages
#   $src_hash_ref: a reference to a hash containing the data to write to the
#	file.  The hash is organized the same way as the destination hash
#	($dest_hash_ref) in cas_equalfile::read().
# 
# Outputs: none
#
# Return Value:
#   PERL_SUCCESS or PERL_FAILURE
#
# ============================================================================
sub cas_equalfile::write
{{
   my( $requested_filename, $requested_file_description, $src_hash_ref )= @_;

   # ----------------------------------------------------------------------
   # open the file
   my($tt);
   my($filehandle)= new FileHandle;
   $tt= $filehandle->open ( "> $requested_filename" );
   if ( $tt != $main::PERL_SUCCESS )  {
      &cas_exit::panic ( $packagename,
	    "problem opening $requested_file_description file for writing, ", 
            "file name= \"$requested_filename\", ", 
            "$!. " );
      return $main::PERL_FAILURE;
   }

   # ----------------------------------------------------------------------
   # write out the data

   $tt= &write_generic ( $filehandle, $src_hash_ref );
   if ( $tt == $main::PERL_SUCCESS )  {
      return $tt;
   }

   # ----------------------------------------------------------------------
   # close the file
   $tt= $filehandle->close;
   if ( $tt != $main::PERL_SUCCESS )  {
      &cas_exit::panic ( $packagename,
	    "problem closing $requested_file_description file after writing, ", 
            "file name= \"$requested_filename\", ", 
            "$!. " );
      return $main::PERL_FAILURE;
   }


   # ----------------------------------------------------------------------
   # clean up and return
   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine generate_end_of_field_marker()
## ===========================================================================
# Description:
#	Generates a string that can be used as an end-of-field marker for the
#	given field value.  The field value can't contain the end-of-field 
#	string.
#
# Method:
#	Creates a pseudorandom sequence of characters.
#
# Notes:
#
# Warnings:
#
# Inputs:
#   $value: the contents of the field to generate the marker string for.
# 
# Outputs: none
#
# Return Value:
#   the end-of-field marker string
#
# ============================================================================
sub generate_end_of_field_marker
{{
   my($value)= @_;

   my($safety_counter);
   my($marker_st);
   my(@char_list)= qw( a b c d e f g h i j k l m n o p q r s t u v w x y z 
	 0 1 2 3 4 5 6 7 8 9 );

   for ($safety_counter= 0; $safety_counter < 1000; $safety_counter++ )  {
      $marker_st= "END_" . 
	    $char_list[rand scalar(@char_list)] . #1
	    $char_list[rand scalar(@char_list)] . #2
	    $char_list[rand scalar(@char_list)] . #3
	    $char_list[rand scalar(@char_list)] . #4
	    $char_list[rand scalar(@char_list)] . #5
	    $char_list[rand scalar(@char_list)];  #6

      if ( $value !~ m/$marker_st/ )  {
         return $marker_st;
      }
   }

   # something is wrong... this is running a riddiculously long time.
   &cas_exit::panic ( $packagename, "", 
	 "Internal error 588.17sep2000, code=$safety_counter " ); 
}}


#template 22 lines long
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

## ****************************************************************************
## ****************************************************************************
## package identification

package cas_equalfile::reader::private;

use strict;

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
      $packagename= "cas_equalfile::reader::private";

      # ----------------------------------------------------------------------
      # other stuff

      # --------------------
      # this meta line number indicates we aren't reading a file at the moment
      use vars qw ( *LINE_NUM__NOT_IN_USE );
      *LINE_NUM__NOT_IN_USE= \-2; 

      # --------------------
      # package scope global variables storing info on the file we're reading 
      #  	now

      # the number of the line most _recently_ read.  The first line in the
      #  	file is line 1.
      use vars qw ( $line_num );
      $line_num= $LINE_NUM__NOT_IN_USE;

      # the file's name
      use vars qw ( $file_name );
      $file_name= "";

      # the file's description (used for error messages)
      use vars qw ( $file_description );
      $file_description= "";

      # are we at end-of-file?
      use vars qw ( $eof );
      $eof= $main::FALSE;

      # --------------------
      # done
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
## Subroutine cas_equalfile::reader::open_file()
## ===========================================================================
# Description: opens a file
#
# Inputs: 
#   $requested_file_name: the name
#   $requested_file_description: a description of the file used for error
#	messages
# 
# Outputs: none
#
# Return Value: PERL_SUCCESS or PERL_FAILURE
#
# ============================================================================
sub cas_equalfile::reader::open_file
{{
   my($requested_file_name, $requested_file_description )= @_;
   my ( $tt );

   # make sure we aren't already trying to read a file
   if ( $line_num != $LINE_NUM__NOT_IN_USE )  {
      &cas_exit::panic ( $packagename, 
	    "attempt to open file \"$requested_file_name\"",
            "while file \"$file_name\" was being read \n" );
      return $main::PERL_FAILURE;
   }

   # open the file
   $tt= open FILEHANDLE, "< $requested_file_name"; 
   if ($tt != $main::PERL_SUCCESS )  {
      &cas_exit::panic ( $packagename,
	    "problem opening $requested_file_description file for reading, ", 
            "file name= \"$requested_file_name\", ", 
            "$!. " );
      return $main::PERL_FAILURE;
   }

   # reset information about the file
   $line_num= 0;
   $file_name= $requested_file_name;
   $file_description= $requested_file_description;
   $eof= $main::FALSE;

   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine cas_equalfile::reader::close_file()
## ===========================================================================
# Description: closes the file, and resets the line number
#
# Inputs: none
# 
# Outputs: none
#
# Return Value: PERL_SUCCESS
#
# ============================================================================
sub cas_equalfile::reader::close_file
{{
   #my( )= @_;

   my ($tt);

   # close the file
   $tt= close FILEHANDLE;
   if ($tt != $main::PERL_SUCCESS )  {
      &cas_exit::panic ( $packagename, 
	    "problem closing $file_description file after reading, ", 
            "file name= \"$file_name\", ", 
            "$!. " );
      return $main::PERL_FAILURE;
   }

   # reset info about the file
   $line_num= $LINE_NUM__NOT_IN_USE;
   $file_description= "";
   $file_name= "";
   $eof= $main::FALSE;

   return $main::PERL_SUCCESS;
}}


## ===========================================================================
## Subroutine cas_equalfile::reader::read_data_line()
## ===========================================================================
# Description: reads in the next line from the specified file handle.
#	Skips over blank lines and comment lines (beginning with '#').
#
# Inputs: none
# 
# Outputs: none
#
# Return Value: the line read, or the undefined value if EOF
#
# ============================================================================
sub cas_equalfile::reader::read_data_line()
{{
   my ( $raw_line );
   while ( $main::TRUE )  {
      $raw_line= &cas_equalfile::reader::read_raw_line();      
      if ( !defined($raw_line) )  {
         # we hit end-of-file
         return undef;
      }
      
      # classify the line
      if ( $raw_line =~ m/^\s*#/ )  {
         # it is a comment
         next;   
      }
      if ( $raw_line =~ m/^\s*$/ )  {
         # it is a blank line
         next;
      }

      # apparently the line is worth returning
      return $raw_line;
   }
}}


## ===========================================================================
## Subroutine cas_equalfile::reader::read_raw_line()
## ===========================================================================
# Description: reads in the next line from the specified file handle, 
#	without interpreting it or skipping anything.  Sets the $line_num
#	variable appropriately.
#
# Inputs: none
# 
# Outputs: none
#
# Return Value: the line read, or the undefined value if EOF
#
# ============================================================================
sub cas_equalfile::reader::read_raw_line()
{{
   #local( *FILEHANDLE_ref )= @_;

   my ( $raw_line );

   $raw_line= <FILEHANDLE>;
   $line_num++;

   # check for end-of-file
   if ( !defined($raw_line) )  {
      # we hit end-of-file
      $eof= $main::TRUE;
      return undef;
   }

   return $raw_line;
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
sub cas_equalfile::reader::get_line_num
{{
   return $line_num;
}}

sub cas_equalfile::reader::is_eof
{{
   return $eof;
}}

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
## ****************************************************************************
## ****************************************************************************

# return 1
1;


__END__

=pod

Stuff to do:

* (2apr2013) HIGH PRIORITY: Rewrite this to be an object-oriented class.
	Allow overriding the data source, so we can read data from a string as
	easily as from a file.  This should allow retiring
	cas_equalfile_st.pm.

* (31dec1999) add the ability to specify default values for missing fields

* (31dec1999) add the ability to specify that some fields are mandatory 
	and report an error if they are missing

* (31dec1999) add the ability to specify correct types of values for each 
	field, similar to the getopt::Long system.

* (11aug2001) add a function that can parse an equalfile that has already
	been read in to a string.  The ::read() routine might then use
	that function.

=cut
