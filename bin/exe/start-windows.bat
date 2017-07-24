@cd ..
@cd ..

@color 0E
@title Image-Cataloger

@call bin/startPath.bat

@rem ** Append the libraries that are used by the started application:
@set CLASSPATH=%CUSLIBPATH%/Image-Cataloger.jar;%CLASSPATH%

@set JVMARGS=
@set JVMARGS=%JVMARGS% -showversion 
@set JVMARGS=%JVMARGS% -Xmx120M 

"%JAVA_HOME%/bin/java" %JVMARGS% edu.oregonstate.forestry.server.CMain

pause
