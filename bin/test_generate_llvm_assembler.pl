#!/usr/bin/perl
#
# ****************************************************************************
# Project Name: lli_undef_fix
# Script Name: test_generate_llvm_assembler.pl 
#
#
# For a copyright information, see the statement in the POD documentation at
# the end of this file.  
#
# Description: 
#	
#
# ****************************************************************************

# ****************************************************************************
# Revision Control Information (customized for RCS)
#
# $Revision: 1.2 $
# $Date: 2014/01/23 21:23:34 $
# $Author: cas $
#
# ****************************************************************************


# ****************************************************************************
# Compiler Directives (use's)
# make sure we are using this Perl version or later
use 5.6.0;

use strict;
use Getopt::Long;
use Config;
use File::Spec;
use FileHandle;

# ****************************************************************************
# package identification

#package test_generate_llvm_assembler;


# ****************************************************************************
# package BEGIN and END functions

   # =========================================================================
   # subroutine BEGIN
   # =========================================================================
   sub BEGIN
   {{
      # set basic constants, which should be defined in all scripts
      use vars qw ( *TRUE *FALSE *PERL_SUCCESS *PERL_FAILURE
            *EXIT_SUCCESS *EXIT_FAILURE );
      *TRUE= \1;
      *FALSE= \0;
      *PERL_SUCCESS= \1;
      *PERL_FAILURE= \0;
      *EXIT_SUCCESS= \0;
      *EXIT_FAILURE= \-1;

      use vars qw ( *HELP_TYPE_EMERGENCY *HELP_TYPE_FULL
            *HELP_TYPE_MAN  *HELP_TYPE_REVISION );
      *HELP_TYPE_EMERGENCY= \2960; # number chosen at random
      *HELP_TYPE_FULL= \2961; # one more than the above
      *HELP_TYPE_MAN= \2962; # one more than the above
      *HELP_TYPE_REVISION= \2963; # one more than the above

      # ----------------------------------------------------------------------
      # script-specific constants
      use vars qw ( *scriptname $revision *SCRIPT_COPYRIGHT_YEAR 
	    $debug_flag );
      *scriptname= \"test_generate_llvm_assembler.pl";
      {
         my ($null);
         ($null, $revision)= split / /, '$Revision $';
         $revision=~ tr/$/ /s;
      }
      *SCRIPT_COPYRIGHT_YEAR= \2014;
      $debug_flag= $FALSE;

      # ----------------------------------------------------------------------
      # set library search paths

      use FindBin;
      use lib "$FindBin::Bin/../lib";

      if ( exists($ENV{'USR_CAS_LIB'}) )  {
         push @INC, File::Spec->catdir( $ENV{'USR_CAS_LIB'} . 'perl' );
      }

      # ----------------------------------------------------------------------
      # require's and use's needed in the BEGIN block


      # ----------------------------------------------------------------------
      # script-wide config options

      # use synchronous I/O
      $|= $main::TRUE;


      # ----------------------------------------------------------------------
      # other stuff

   }}

   # =========================================================================
   # require's and use's needed by the rest of the program
   # =========================================================================

   # =========================================================================
   # subroutine END
   # =========================================================================
   sub END
   {{
      
   }}

# ****************************************************************************
# start the script

## ===========================================================================
##   set debug mode

if ( scalar(@ARGV) > 0 )  {
   if ( $#ARGV >= $[ )  { 
      if ( $ARGV[$[] eq "debug"  )  { 
	 $debug_flag= $TRUE;
	 shift @ARGV; print "$scriptname running in debug mode \n";
      } 
   } 
}

## ===========================================================================
##   get command line args

use vars qw( $need_to_exit );
$need_to_exit= $FALSE;

## -----------------------------------------------------------------------
##   set up defaults


## -----------------------------------------------------------------------
##   parse arguments that GetOpt can't handle

if ( scalar(@ARGV) > 0 )  {
   if ( $ARGV[ $[ ] eq "help" ||
	 $ARGV[ $[ ] eq "-help" )  {
      shift @ARGV;
      &give_help ( $HELP_TYPE_FULL );
   }
}

## -----------------------------------------------------------------------
##   run GetOpt

{
   my ( $arg_help, $arg_man, $arg_revision );

   eval {
      Getopt::Long::Configure ( 'no_auto_abbrev', 'no_getopt_compat',
            'permute', 'bundling', 'no_ignore_case', 'no_pass_through' );
   };
   if ( $@ ne "" )  {
      eval {
         # try again, using the older Getopt::Long call
         &Getopt::Long::config ( 'no_auto_abbrev', 'no_getopt_compat',
               'permute', 'bundling', 'no_ignore_case', 'no_pass_through' );
      };
      if ( $@ ne "" ) { die; }
   }

   GetOptions ( "help|h" => \$arg_help, 
	 "man" => \$arg_man,
	 "revision" => \$arg_revision,
	 );

   if ( $arg_help )  {
      &give_help ( $HELP_TYPE_FULL );
      $need_to_exit= $main::TRUE;
   } elsif ( $arg_man )  {
      &give_help ( $HELP_TYPE_MAN );
      $need_to_exit= $main::TRUE;
   } elsif ( $arg_revision )  {
      &give_help ( $HELP_TYPE_REVISION );
      $need_to_exit= $main::TRUE;
   }
      
}

if ( $need_to_exit )  { print "\n"; exit $EXIT_FAILURE; }


## -----------------------------------------------------------------------
##   parse arguments that GetOptions left behind

## -----------------------------------------------------------------------
##   check arguments

if ( $need_to_exit )  { print "\n"; exit $EXIT_FAILURE; }

## -----------------------------------------------------------------------
##   print out arguments
if ( $debug_flag )  {


}


## ===========================================================================
##   

## ===========================================================================
##   

## ===========================================================================
##   

## ===========================================================================
##   

## ===========================================================================
##   

## ===========================================================================
##   


# template 25 lines long
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



## ===========================================================================
## Subroutine system_wrapper()
## ===========================================================================
# Description:
#	Call the first argument the command, and the 2nd argument the 
#	alternate command.
#
#	If not running in debug mode, executes the command via a
#	system() call.
#
#	If running in debug mode, prints the command, and executes 
#	the alternate command.  If the alternate command is not present, 
#	assumes it is the same as the command.	If the alternate command is
#	a null string, does nothing and returns as if the command had 
#	succeeded.
#
# Method:
#
# Notes:
#
# Warnings:
#
# Inputs:
#	$cmd: the command described above
#	$alt_cmd: the alternate command described above
# 
# Outputs:
#	none
#
# Return Value:
#   a list, giving the following:
#   * the command's exit code 
#   * a boolean flag that is true iff the command's process dumped core
#   * the signal that caused the command's to exit (if it exited due to 
#	a signal)
#
# ============================================================================
sub system_wrapper
{{
   my ( $raw_exit_code )= 0;
   my ( $ftn_name )= "system_wrapper()";

   # parse args
   my( $cmd, $alt_cmd );
   {
      my ($cmd_index)= $[;
      my ($alt_cmd_index)= $[+ 1;
      if ( $#_ == $cmd_index)  {
	 $cmd= $_[$cmd_index];
	 $alt_cmd= $cmd;
      } elsif ( $#_ == $alt_cmd_index )  {
	 $cmd= $_[$cmd_index];
	 $alt_cmd= $_[ $alt_cmd_index ];
      } else {
	 die "$scriptname:$ftn_name: " . 
	       "internal error 202.11dec1999, code= $#_ \n";
      }
   }

   # run the command
   if ( $debug_flag )  {
      print "$scriptname:$ftn_name: would execute \"$cmd\" \n";
      if ( $alt_cmd eq "" )  {
	 $raw_exit_code= 0;
      } else {
	 $raw_exit_code= system( $alt_cmd );
      }    
   } else {
      $raw_exit_code= system( $cmd );
   }

   # divide up the exit code 
   return ( $raw_exit_code >> 8, 
	 ($raw_exit_code & 128) == 0 ? $FALSE : $TRUE,
	   $raw_exit_code & 127 );
}}



## ===========================================================================
## Subroutine give_help()
## ===========================================================================
# Description:
#	Gives a help message or a man page.
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
sub give_help
{{
   my( $help_type )= @_;
   my ($H)= "   ";

   # ----------------------------------------------------------------------
   # print revision information
   if ( $help_type == $HELP_TYPE_REVISION )  {
      print "$scriptname: revision $revision \n\n";
      exit $EXIT_SUCCESS;
   }

   # ----------------------------------------------------------------------
   # print manual page
   if ( $help_type == $HELP_TYPE_MAN )  {
      my ($cmd);
      my ( $needs_to_run )= $main::TRUE;

      # CAS todo: Need to improve ability to find the perldoc program if 
      # it isn't on the current path.  Ideally, we should look for the
      # perldoc program where the operating system found the perl 
      # executable, and if the perl executable is a symbolic link, 
      # look for the perldoc executable in the directory of the file 
      # the symbolic link points to.

      # repeatedly look for the perldoc program until it runs successfully
      foreach my $dir ( "", $Config{'bin'}, $Config{'binexp'}, 
	    $Config{'installbin'}, $Config{'installsitebin'}, 
	    $Config{'installvendorbin'}, $Config{'sitebin'}, 
	    $Config{'sitebinexp'}, $Config{'vendorbin'}, 
	    $Config{'vendorbinexp'} )  {
	 $cmd= File::Spec->catfile ( $dir, "perldoc" ) . " $0";
	 my ( $exit_status, $core_dump, $exit_signal )=
	       &system_wrapper ( $cmd, "" );
	 if ( $exit_status == 0 )  {
	    $needs_to_run= $main::FALSE;
	    last;
	 }
      }
      if ( $needs_to_run )  {
	 print "$scriptname: problem running `perldoc` to format the manual
	page.  Is perldoc in a directory on your program search PATH?  
	Use `$scriptname --help` for more information on $scriptname. \n\n";
         exit $main::EXIT_FAILURE; 
      }
      exit $main::EXIT_SUCCESS; 
   }
   

   # ----------------------------------------------------------------------
   # print header
   if ( $help_type == $HELP_TYPE_FULL )  {
      print "$scriptname: no description available \n";
      if ( $help_type == $HELP_TYPE_FULL )  {
	 print "revision $revision \n";
      }
      print "\n";
   }

   
   # ----------------------------------------------------------------------
   # print synopsis
   
   if ( $help_type == $HELP_TYPE_EMERGENCY || $help_type == $HELP_TYPE_FULL )  {
      print "Correct use: \n";
   }


   print $H . 
	 "$scriptname [debug] [-h|help|-help|--help] [--man] [--revision] \n";
   print $H . "$scriptname [debug] ? \n";
   print "\n\n";

   # ----------------------------------------------------------------------
   # exit unless we're print a man page
   if ( $help_type == $HELP_TYPE_EMERGENCY )  {
      #print "\n\n";
      exit $EXIT_FAILURE;
   }
   if ( $help_type == $HELP_TYPE_FULL )  {
      #print "\n\n";
      exit $EXIT_SUCCESS;
   }

   die "$scriptname: internal error 372.4nov2000. \n";
   
}}




# ****************************************************************************
# end this package 
package main;

__END__

=pod

=head1 NAME - test_generate_llvm_assembler.pl

=cut

#put a short (one line) description here

=pod

=head1 SYNOPSIS

=cut

# this needs to be a verbatim paragraph, but not be intented too far.
# Therefore begin each line with a space.

=pod

 test_generate_llvm_assembler.pl [debug] [-h|help|-help|--help] [--man] [--revision] 
 test_generate_llvm_assembler.pl [debug] ? 


=head1 DESCRIPTION


=head1 EXTERNAL INFLUENCES

=cut

# consider adding: NOTES, EXAMPLES, RETURN VALUES & ERROR MESSAGES

#=head1 NEW SECTION

=pod

=head1 FILES

=head1 WARNINGS AND CAVEATS

=head1 BUGS AND DEFICIENCIES

=head1 AUTHOR AND COPYRIGHT

test_generate_llvm_assembler.pl was written by Christian A. Schreiner.  Copyright (C) 
2014-2014 by University of Utah.  You may use, examine, or modify
this software only in accordance with the GNU Public License, or, alternately, 
by special arrangement with the author.  test_generate_llvm_assembler.pl is released without 
any warranties; if you use it and something breaks, you are responsible for 
cleaning up afterwards.  This notice is precautionary only and does not 
inherently imply publication or release for public use.  

=head1 SEE ALSO

perl(1), perldoc(1)

=cut

