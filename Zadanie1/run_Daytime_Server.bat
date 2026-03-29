@echo off
javac DaytimeTCPServer.java
if errorlevel 1 goto end
java -cp "." DaytimeTCPServer
:end
pause
