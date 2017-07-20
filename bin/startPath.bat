@rem set JAVA_HOME=@java.dir@

@rem ** Define some useful path shortcuts:
@set STARTPATH=./

@set CUSLIBPATH=%STARTPATH%lib/customer
@set EXTLIBPATH=%STARTPATH%lib/external
@set NATLIBPATH=%STARTPATH%lib/native
@set SYSLIBPATH=%STARTPATH%lib/systema
@set UTILIBPATH=%STARTPATH%lib/util


@rem ** Empty the classpath:
@set CLASSPATH=;

@rem ** Append the third party libraries that are used by (all) applications:
@set CLASSPATH=%EXTLIBPATH%/activemq-all-5.10.1.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/jtds-1.3.1.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/ant-contrib.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/resolver.jar;%CLASSPATH%

@set CLASSPATH=%NATLIBPATH%/junit-4.12.jar;%CLASSPATH%

@rem ** Append the components that are used by (all) applications:
@set CLASSPATH=%SYSLIBPATH%/bus-jms-active-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/config-nxml-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/csfw-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/db-requester-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/jbasin-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/jtools-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/logger-file-o-s.jar;%CLASSPATH%
@set CLASSPATH=%SYSLIBPATH%/msg-vfei-o-s.jar;%CLASSPATH%

@rem ** Append the startpath for loading the language files (ResourceBundles) to the classpath(!):
@set CLASSPATH=%STARTPATH%res;%CLASSPATH%

@rem ** Set the path to native libraries:
@set PATH=%JAVA_HOME%/bin;%NATLIBPATH%;%PATH%

@set classpath
