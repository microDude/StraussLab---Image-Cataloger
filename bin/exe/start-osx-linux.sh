#!/bin/sh

cd ..
cd ..

. bin/startPath.sh

export CLASSPATH=$CUSLIBPATH/Image-Cataloger.jar:$CLASSPATH  

export JVMARGS=
export JVMARGS="$JVMARGS -showversion"
export JVMARGS="$JVMARGS -Xmx120M"

java $JVMARGS edu.oregonstate.forestry.server.CMain

read -n1 -r -p "Press any key to continue..."