#!/bin/sh
# basic test of ir_ftn_mktest

scriptname=ir_ftn_mktest..basic.sh

if [ -z "$TESTDRIVER_CASE_WORKDIR" ]; then
   TESTDRIVER_CASE_WORKDIR=tmp/${scriptname}_`date +%Y%b%d_%H%M%S`_$$
   mkdir $TESTDRIVER_CASE_WORKDIR
   echo using TESTDRIVER_CASE_WORKDIR=\"$TESTDRIVER_CASE_WORKDIR\"
fi

export SEED=1 # make sure the random number generator works repeatably

cd $TESTDRIVER_CASE_WORKDIR

$PROJ_ROOT/admin/bin/ir_ftn_mktest \
      $PROJ_ROOT/admin/test/data/funcs-3-2/000.ll linus

for file in `find linus -type f | sort `; do
   echo '#################################################################'
   echo '#################################################################'
   echo file $file:
   echo " "
   cat $file
   echo " "
   echo '#################################################################'
   echo '#################################################################'
done
echo " "
echo '(end of output)'

