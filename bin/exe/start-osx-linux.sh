#!/bin/sh

cd ..
cd ..

. bin/startPath.sh

export CLASSPATH=$CUSLIBPATH/Image-Cataloger.jar:$CLASSPATH  

export JVMARGS=
export JVMARGS="$JVMARGS -showversion"

java $JVMARGS edu.oregonstate.forestry.server.CMain