#!/usr/bin/perl

$|= 1;
our $need_to_stop= 0;

$SIG{INT}= \&catch_sigINT;

our $count= 0;
while ( 1 )  {
   print "pid= $$, count= $count\n";
   $count++;
   sleep(1);

   if ( $need_to_stop )  {
      print "stopping. \n";
      exit 0;
   }
}

sub catch_sigINT
{{
   $main::need_to_stop= 1;
}}
