@rem set JAVA_HOME=@java.dir@

@rem ** Define some useful path shortcuts:
@set STARTPATH=./

@set CUSLIBPATH=%STARTPATH%lib/customer
@set EXTLIBPATH=%STARTPATH%lib/external
@set NATLIBPATH=%STARTPATH%lib/native
@set UTILIBPATH=%STARTPATH%lib/util


@rem ** Empty the classpath:
@set CLASSPATH=;

@rem ** Append the third party libraries that are used by (all) applications:
@set CLASSPATH=%EXTLIBPATH%/commons-codec.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/commons-collections4.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/curvesapi.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/jdom.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/log4j.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/poi.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/poi-ooxml.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/poi-ooxml-schemas.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/stax-api.jar;%CLASSPATH%
@set CLASSPATH=%EXTLIBPATH%/xmlbeans.jar;%CLASSPATH%

@rem ** Append the startpath for loading the language files (ResourceBundles) to the classpath(!):
@set CLASSPATH=%STARTPATH%resources;%CLASSPATH%

@rem ** Set the path to native libraries:
@set PATH=%JAVA_HOME%/bin;%NATLIBPATH%;%PATH%

@set classpath
