set JAVA_HOME=E:\JAVA\jdk18
rem set JAVA_HOME=c:\Program Files\Java\jdk1.8.0_65\

call mvn -X eclipse:clean eclipse:eclipse -DdownloadSources=true -DdownloadSources > debug.log
pause