@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Launcher startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and LAUNCHER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="--module-path \"C:\Users\Maxime\.jdks\zulu8.66.0.15-ca-fx-jdk8.0.352-win_x64\bin\" --add-modules javafx.controls"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Launcher-1.0.jar;%APP_HOME%\lib\openauth-1.1.4.jar;%APP_HOME%\lib\fontawesomefx-8.9.jar;%APP_HOME%\lib\flowupdater-1.7.2.jar;%APP_HOME%\lib\openlauncherlib-3.2.5.jar;%APP_HOME%\lib\oshi-core-5.8.7.jar;%APP_HOME%\lib\gson-2.9.1.jar;%APP_HOME%\lib\flowmultitools-1.4.0.jar;%APP_HOME%\lib\annotations-23.0.0.jar;%APP_HOME%\lib\json-20220320.jar;%APP_HOME%\lib\jna-platform-5.10.0.jar;%APP_HOME%\lib\jna-5.10.0.jar;%APP_HOME%\lib\slf4j-api-1.7.32.jar;%APP_HOME%\lib\commons-compress-1.21.jar


@rem Execute Launcher
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %LAUNCHER_OPTS%  -classpath "%CLASSPATH%" fr.universecorp.launcher.Main %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable LAUNCHER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%LAUNCHER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
