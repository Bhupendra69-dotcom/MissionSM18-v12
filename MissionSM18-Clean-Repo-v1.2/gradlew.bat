@echo off
setlocal
set GRADLE_VERSION=8.9
set GRADLE_HOME_BASE=%USERPROFILE%\.gradle\mission-sm18-wrapper
set GRADLE_HOME=%GRADLE_HOME_BASE%\gradle-%GRADLE_VERSION%
set GRADLE_ZIP=%GRADLE_HOME_BASE%\gradle-%GRADLE_VERSION%-bin.zip
if not exist "%GRADLE_HOME%\bin\gradle.bat" (
  if not exist "%GRADLE_ZIP%" powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile '%GRADLE_ZIP%'"
  if exist "%GRADLE_HOME_BASE%\unpack" rmdir /s /q "%GRADLE_HOME_BASE%\unpack"
  mkdir "%GRADLE_HOME_BASE%\unpack"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Force '%GRADLE_ZIP%' '%GRADLE_HOME_BASE%\unpack'"
  if exist "%GRADLE_HOME%" rmdir /s /q "%GRADLE_HOME%"
  move "%GRADLE_HOME_BASE%\unpack\gradle-%GRADLE_VERSION%" "%GRADLE_HOME%"
  rmdir /s /q "%GRADLE_HOME_BASE%\unpack"
)
call "%GRADLE_HOME%\bin\gradle.bat" %*
endlocal
