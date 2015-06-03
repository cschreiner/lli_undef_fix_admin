#!/bin/sh
# test run_ir_ftn_mktest_lli's ability to deal with an optimized .ll file
# whose exit status indicates a problem.

scriptname=run_ir_ftn_mktest_lli..o3_status.sh

if [ -z "$TESTDRIVER_CASE_WORKDIR" ]; then
   export TESTDRIVER_CASE_WORKDIR=`pwd`/tmp/${scriptname}_`date +%Y%b%d_%H%M%S`_$$
   mkdir $TESTDRIVER_CASE_WORKDIR
   echo "had to create TESTDRIVER_CASE_WORKDIR="
   echo "  " \"$TESTDRIVER_CASE_WORKDIR\"
fi

# -----------------------------------------------------------------------------
# main script

run_ir_ftn_mktest_lli --master $TESTDRIVER_CASE_WORKDIR \
      --parent $TESTDRIVER_CASE_WORKDIR \
      --o3-script false \
      test/data/func-3-2/001.ll

(cd $TESTDRIVER_CASE_WORKDIR/001.ll; cat *.report)


# -----------------------------------------------------------------------------
# end of script

