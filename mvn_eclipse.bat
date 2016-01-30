rem set JAVA_HOME=E:\JAVA\jdk18
set JAVA_HOME=c:\Program Files\Java\jdk1.8.0_65\

call mvn -X eclipse:clean eclipse:eclipse -DdownloadSources=true -DdownloadSources -DresolveWorkspaceProjects=false > debug.log
pause