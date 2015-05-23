#!/bin/sh
# basic test of ir_ftn_mktest

$PROJ_ROOT/admin/bin/ir_ftn_mktest $PROJ_ROOT/test/data/funcs-3-2/000.ll linus

for file in `find linus -type f | sort `; do
   echo '#################################################################'
   echo file $file:
   echo " "
   cat $file
   echo " "
   echo '#################################################################'
done
echo " "
echo '(end of output')

