@echo off
REM Windows 环境变量验证脚本
REM 用于验证 GraalVM 和 Java 环境是否正确配置

echo ======================================
echo Windows GraalVM 环境验证脚本
echo ======================================
echo.

REM 检查是否提供了 GraalVM 路径参数
if "%1"=="" (
    echo 用法: verify-env.cmd "C:\path\to\graalvm-jdk-21"
    echo 示例: verify-env.cmd "C:\graalvm\graalvm-jdk-21"
    echo.
    echo 或者使用当前 JAVA_HOME 环境变量进行验证
    if "%JAVA_HOME%"=="" (
        echo 错误: 未提供 GraalVM 路径，且 JAVA_HOME 环境变量未设置
        exit /b 1
    ) else (
        set "GRAALVM_PATH=%JAVA_HOME%"
        echo 使用当前 JAVA_HOME: %JAVA_HOME%
    )
) else (
    set "GRAALVM_PATH=%~1"
    echo 使用提供的路径: %GRAALVM_PATH%
)

echo.
echo 1. 验证 GraalVM 安装路径...
if not exist "%GRAALVM_PATH%" (
    echo ❌ 错误: 路径不存在 - %GRAALVM_PATH%
    exit /b 1
)
echo ✅ 路径存在: %GRAALVM_PATH%

echo.
echo 2. 检查关键文件...
if not exist "%GRAALVM_PATH%\bin\java.exe" (
    echo ❌ 错误: java.exe 不存在 - %GRAALVM_PATH%\bin\java.exe
    exit /b 1
)
echo ✅ java.exe 存在

if not exist "%GRAALVM_PATH%\bin\native-image.exe" (
    echo ❌ 错误: native-image.exe 不存在 - %GRAALVM_PATH%\bin\native-image.exe
    exit /b 1
)
echo ✅ native-image.exe 存在

echo.
echo 3. 设置环境变量...
set "JAVA_HOME=%GRAALVM_PATH%"
set "GRAALVM_HOME=%GRAALVM_PATH%"
set "PATH=%GRAALVM_PATH%\bin;%PATH%"

echo ✅ JAVA_HOME=%JAVA_HOME%
echo ✅ GRAALVM_HOME=%GRAALVM_HOME%
echo ✅ PATH 已更新

echo.
echo 4. 验证 Java 版本...
"%JAVA_HOME%\bin\java" -version 2>&1 | findstr /C:"GraalVM" >nul
if errorlevel 1 (
    echo ❌ 警告: Java 版本不是 GraalVM
    "%JAVA_HOME%\bin\java" -version
) else (
    echo ✅ Java 版本验证通过
    "%JAVA_HOME%\bin\java" -version
)

echo.
echo 5. 验证 native-image...
"%GRAALVM_HOME%\bin\native-image" --version 2>&1 | findstr /C:"GraalVM" >nul
if errorlevel 1 (
    echo ❌ 错误: native-image 版本验证失败
    "%GRAALVM_HOME%\bin\native-image" --version
    exit /b 1
) else (
    echo ✅ native-image 版本验证通过
    "%GRAALVM_HOME%\bin\native-image" --version
)

echo.
echo 6. 测试 Maven 包装器...
if exist "mvnw.cmd" (
    echo 找到 mvnw.cmd，测试编译...
    call mvnw.cmd --version
    if errorlevel 1 (
        echo ❌ 警告: Maven 包装器测试失败
    ) else (
        echo ✅ Maven 包装器正常工作
    )
) else (
    echo ⚠️ 未找到 mvnw.cmd，跳过 Maven 测试
)

echo.
echo 7. 生成环境变量设置脚本...
echo @echo off > set-graalvm-env.cmd
echo REM 自动生成的 GraalVM 环境变量设置脚本 >> set-graalvm-env.cmd
echo set "JAVA_HOME=%GRAALVM_PATH%" >> set-graalvm-env.cmd
echo set "GRAALVM_HOME=%GRAALVM_PATH%" >> set-graalvm-env.cmd
echo set "PATH=%GRAALVM_PATH%\bin;%%PATH%%" >> set-graalvm-env.cmd
echo echo GraalVM 环境变量已设置 >> set-graalvm-env.cmd
echo ✅ 环境变量设置脚本已生成: set-graalvm-env.cmd

echo.
echo ======================================
echo 环境验证完成！
echo ======================================
echo.
echo 要在当前会话中应用这些设置，请运行:
echo   call set-graalvm-env.cmd
echo.
echo 要永久设置环境变量，请:
echo 1. 右键点击 "此电脑" → "属性"
echo 2. 点击 "高级系统设置"
echo 3. 点击 "环境变量"
echo 4. 添加或修改以下变量:
echo    JAVA_HOME=%GRAALVM_PATH%
echo    GRAALVM_HOME=%GRAALVM_PATH%
echo    PATH (添加): %GRAALVM_PATH%\bin
echo.