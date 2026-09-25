@echo off
for /f "usebackq tokens=1* delims==" %%i in ("..\.env") do (
    set "%%i=%%j"
)
.\mvnw.cmd spring-boot:run
