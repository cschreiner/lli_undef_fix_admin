#!/bin/sh
# Description: test run_ir_ftn_mktest_lli's ability to handle an add overflow
# introduced at -O3.

scriptname=run_ir_ftn_mktest..add_overflow_03.sh

if [ -z "$TESTDRIVER_CASE_WORKDIR" ]; then
   export TESTDRIVER_CASE_WORKDIR=`pwd`/tmp/${scriptname}_`date +%Y%b%d_%H%M%S`_$$
   mkdir $TESTDRIVER_CASE_WORKDIR
   echo "had to create TESTDRIVER_CASE_WORKDIR="
   echo "  " \"$TESTDRIVER_CASE_WORKDIR\"
fi

# ===========================================================================
# main script

run_ir_ftn_mktest_lli --master $TESTDRIVER_CASE_WORKDIR \
      --parent $TESTDRIVER_CASE_WORKDIR \
      --o3-ll data/ir_ftn_mktest/add_overflow.ll \
      data/funcs-3-2/00a.ll

(cd $TESTDRIVER_CASE_WORKDIR/00a.ll; cat *.report)

# ===========================================================================
# end of script

