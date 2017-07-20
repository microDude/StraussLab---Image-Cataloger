#!/bin/sh

cd ..
cd ..

. bin/startPath.sh

export CLASSPATH=$CUSLIBPATH/template-project.jar:$CLASSPATH 
export CLASSPATH=./build/classes:$CLASSPATH 

export JVMARGS=
export JVMARGS="$JVMARGS -showversion"
export JVMARGS="$JVMARGS -Dsystemagmbh.propertypath=./cfg/template-project"
export JVMARGS="$JVMARGS -Dsystemagmbh.applicationname=template-project" 
export JVMARGS="$JVMARGS -Dinstance=0" 

java $JVMARGS de.systemagmbh.products.clientserver.standard.CSysApplication