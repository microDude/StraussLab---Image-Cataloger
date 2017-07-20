# export JAVA_HOME=@java.dir@

# ---- Define some useful path shortcuts:
export STARTPATH=.

export CUSLIBPATH=$STARTPATH/lib/customer
export EXTLIBPATH=$STARTPATH/lib/external
export NATLIBPATH=$STARTPATH/lib/native
export SYSLIBPATH=$STARTPATH/lib/systema
export UTILIBPATH=$STARTPATH/lib/util

# ---- Empty the classpath:
export CLASSPATH=

# ---- Append the third party libraries that are used by (all) applications:
export CLASSPATH=$EXTLIBPATH/activemq-all-5.10.1.jar;$CLASSPATH
export CLASSPATH=$EXTLIBPATH/resolver.jar;$CLASSPATH
export CLASSPATH=$EXTLIBPATH/jtds-1.3.1.jar;$CLASSPATH

export CLASSPATH=$NATLIBPATH/junit-4.12.jar;$CLASSPATH

# ---- Append the components that are used by (all) applications:
export CLASSPATH=$SYSLIBPATH/bus-jms-active-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/config-nxml-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/csfw-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/db-requester-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/jbasin-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/jtools-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/logger-file-o-s.jar;$CLASSPATH
export CLASSPATH=$SYSLIBPATH/msg-vfei-o-s.jar;$CLASSPATH

# ---- Append the startpath for loading the language files (ResourceBundles) to the classpath(!):
export CLASSPATH=$STARTPATH/:$STARTPATH/res:$CLASSPATH

# ---- Set the path to native libraries:
export PATH=$JAVA_HOME/bin:$PATH

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH
