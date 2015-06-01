#!/bin/sh
# Description: sample testdriver script

scriptname=<scriptname>

if [ -z "TESTDRIVER_CASE_WORKDIR" ]; then
   export TESTDRIVER_CASE_WORKDIR=tmp/${scriptname}_`date +Y%b%d_%H%M%S`_$$
   mkdir $TESTDRIVER_CASE_WORKDIR
   echo "had to create TESTDRIVER_CASE_WORKDIR="
   echo "  " \"$TESTDRIVER_CASE_WORKDIR\"
fi

# ===========================================================================
# main script

<main script contents> 



# ===========================================================================
# end of script

