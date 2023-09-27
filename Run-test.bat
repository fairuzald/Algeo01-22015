@echo off
set SRC_DIR=src
set BIN_DIR=bin
set TOOLS_DIR=%SRC_DIR%\tools
set TOOLS_DI_TYPES=%TOOLS_DIR%\types
set CLASSPATH=%BIN_DIR%

:: Buat folder bin jika belum ada
if not exist %BIN_DIR% mkdir %BIN_DIR%

:: Compile semua file Java ke dalam folder bin
javac -d %BIN_DIR% %TOOLS_DI_TYPES%\*.java
javac -d %BIN_DIR% %TOOLS_DIR%\*.java
javac -d %BIN_DIR% %SRC_DIR%\*.java

:: Periksa exit code dari Java program
if %ERRORLEVEL% NEQ 0 (
  echo Error: Java program exited with an error code.
  exit /b %ERRORLEVEL%
)

:: Jalankan aplikasi Anda dari folder bin dengan heap size 2GB
java -Xmx2048m -cp %CLASSPATH% TestSPL

:: Tunggu sebentar sebelum menutup jendela
pause
