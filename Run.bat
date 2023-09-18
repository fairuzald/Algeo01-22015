@echo off
set SRC_DIR=src
set BIN_DIR=bin
set TOOLS_DIR=%SRC_DIR%\tools
set CLASSPATH=%BIN_DIR%

:: Buat folder bin jika belum ada
if not exist %BIN_DIR% mkdir %BIN_DIR%

:: Compile semua file Java ke dalam folder bin
javac -d %BIN_DIR% %TOOLS_DIR%\*.java
javac -d %BIN_DIR% %SRC_DIR%\*.java

:: Jalankan aplikasi Anda dari folder bin
java -cp %CLASSPATH% Main

:: Tunggu sebentar sebelum menutup jendela
pause
