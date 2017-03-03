@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\com\github\austinv11\Discord4j\2.7.0\Discord4j-2.7.0.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.21\slf4j-api-1.7.21.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.4.5\httpcore-4.4.5.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.5.2\httpclient-4.5.2.jar;"%REPO%"\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;"%REPO%"\commons-codec\commons-codec\1.9\commons-codec-1.9.jar;"%REPO%"\org\apache\httpcomponents\httpmime\4.5.2\httpmime-4.5.2.jar;"%REPO%"\commons-io\commons-io\2.5\commons-io-2.5.jar;"%REPO%"\org\eclipse\jetty\websocket\websocket-client\9.4.0.v20161208\websocket-client-9.4.0.v20161208.jar;"%REPO%"\org\eclipse\jetty\jetty-util\9.4.0.v20161208\jetty-util-9.4.0.v20161208.jar;"%REPO%"\org\eclipse\jetty\jetty-io\9.4.0.v20161208\jetty-io-9.4.0.v20161208.jar;"%REPO%"\org\eclipse\jetty\jetty-client\9.4.0.v20161208\jetty-client-9.4.0.v20161208.jar;"%REPO%"\org\eclipse\jetty\jetty-http\9.4.0.v20161208\jetty-http-9.4.0.v20161208.jar;"%REPO%"\org\eclipse\jetty\websocket\websocket-common\9.4.0.v20161208\websocket-common-9.4.0.v20161208.jar;"%REPO%"\org\eclipse\jetty\websocket\websocket-api\9.4.0.v20161208\websocket-api-9.4.0.v20161208.jar;"%REPO%"\net\jodah\typetools\0.4.8\typetools-0.4.8.jar;"%REPO%"\org\apache\commons\commons-lang3\3.5\commons-lang3-3.5.jar;"%REPO%"\com\google\code\gson\gson\2.8.0\gson-2.8.0.jar;"%REPO%"\net\java\dev\jna\jna\4.2.2\jna-4.2.2.jar;"%REPO%"\com\googlecode\soundlibs\mp3spi\1.9.5.4\mp3spi-1.9.5.4.jar;"%REPO%"\com\googlecode\soundlibs\jlayer\1.0.1.4\jlayer-1.0.1.4.jar;"%REPO%"\org\jcraft\jorbis\0.0.17\jorbis-0.0.17.jar;"%REPO%"\jflac\jflac\1.3\jflac-1.3.jar;"%REPO%"\com\googlecode\soundlibs\tritonus-share\0.3.7.4\tritonus-share-0.3.7.4.jar;"%REPO%"\org\tritonus\tritonus-dsp\0.3.6\tritonus-dsp-0.3.6.jar;"%REPO%"\BarrosCompany\ModestoDiscord\0.0.1-SNAPSHOT\ModestoDiscord-0.0.1-SNAPSHOT.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="Bot" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" BarrosCompany.ModestoDiscord.ModestoBot %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
