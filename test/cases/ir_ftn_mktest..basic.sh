#!/bin/sh
# basic test of ir_ftn_mktest

export SEED=1 # make sure the random number generator works repeatably

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

