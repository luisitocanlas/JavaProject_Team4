@echo off
cls
rem   In this example, any third-party library JARs are located in 'lib' and added to the classpath [-classpath option].
rem   Remove this "lib\*" entry from the classpath if you're not using any, which results in the following:
rem   java -classpath javapoke-1.0.jar com.javapoke.client.Main

rem   Note that your application JAR stays on the classpath, regardless of whether you're using additional libraries or not.

java -classpath out\production\JavaProject_Team4;"lib\*" -splash:images/java.png com.javapoke.client.Main