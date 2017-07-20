@cd ..
@cd ..

@color 0E
@title template-project

@call bin/startPath.bat

"%JAVA_HOME%/bin/java" -version

@rem ** Append the libraries that are used by the started application:
@set CLASSPATH=%CUSLIBPATH%/template-project.jar;%CLASSPATH%
@set CLASSPATH=./build/classes/;%CLASSPATH%
echo %CLASSPATH%

@set JVMARGS=
@set JVMARGS=%JVMARGS% -showversion 
@set JVMARGS=%JVMARGS% -Dsystemagmbh.propertypath=./cfg/template-project
@set JVMARGS=%JVMARGS% -Dsystemagmbh.applicationname=template-project
@set JVMARGS=%JVMARGS% -Dinstance=0 

"%JAVA_HOME%/bin/java" %JVMARGS% de.systemagmbh.products.clientserver.standard.CSysApplication

pause
