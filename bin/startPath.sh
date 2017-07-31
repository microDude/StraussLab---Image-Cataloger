# export JAVA_HOME=@java.dir@

# ---- Define some useful path shortcuts:
export STARTPATH=.

export CUSLIBPATH=$STARTPATH/lib/customer
export EXTLIBPATH=$STARTPATH/lib/external
export NATLIBPATH=$STARTPATH/lib/native
export UTILIBPATH=$STARTPATH/lib/util

# ---- Empty the classpath:
export CLASSPATH=

# ---- Append the third party libraries that are used by (all) applications:
export CLASSPATH=$EXTLIBPATH/commons-codec.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/commons-collections4.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/curvesapi.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/jdom.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/log4j.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/poi.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/poi-ooxml.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/poi-ooxml-schemas.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/stax-api.jar:$CLASSPATH
export CLASSPATH=$EXTLIBPATH/xmlbeans.jar:$CLASSPATH


# ---- Append the startpath for loading the language files (ResourceBundles) to the classpath(!):
export CLASSPATH=$STARTPATH/:$STARTPATH/resources:$CLASSPATH

# ---- Set the path to native libraries:
export PATH=$JAVA_HOME/bin:$PATH

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH
