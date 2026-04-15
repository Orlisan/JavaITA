@echo off
set "currentDir=%~dp0"
set "currentDir=%currentDir:~0,-1%"

echo %PATH% | findstr /I /C:";%currentDir%;" >nul 2>&1
if %errorlevel% neq 0 (
    setx PATH "%PATH%;%currentDir%" /M >nul 2>&1
)

java -cp "%currentDir%\JavaITA.jar" JavaITA.Main %*
