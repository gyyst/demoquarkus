# JAVA_HOME 错误解决指南

## 问题描述

在 Windows 环境下进行 Quarkus 原生构建时，可能遇到以下错误：

```
Error: JAVA_HOME not found in your environment. 
Please set the JAVA_HOME variable in your environment to match the 
location of your Java installation.
```

## 解决方案

### 在 GitHub Actions 中的解决方案

我们已经在配置文件中实施了多层环境变量设置来解决这个问题：

#### 1. 步骤级别的环境变量设置
```yaml
- name: Build Windows native image with MSVC
  shell: cmd
  run: |
    # 构建命令
  env:
    GRAALVM_HOME: ${{ steps.setup-graalvm.outputs.graalvm-home }}
    JAVA_HOME: ${{ steps.setup-graalvm.outputs.graalvm-home }}
```

#### 2. 批处理脚本中的变量设置
```yaml
- name: Build Windows native image with MSVC
  shell: cmd
  run: |
    set JAVA_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}
    set GRAALVM_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}
    set PATH=${{ steps.setup-graalvm.outputs.graalvm-home }}\bin;%PATH%
    ./mvnw.cmd package -Pnative -DskipTests
```

#### 3. 验证环境变量
```yaml
- name: Verify Java and GraalVM installation
  shell: cmd
  run: |
    echo "GraalVM Home: ${{ steps.setup-graalvm.outputs.graalvm-home }}"
    set JAVA_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}
    echo "JAVA_HOME=%JAVA_HOME%"
    java -version
    native-image --version
```

### 本地开发环境的解决方案

如果在本地 Windows 环境遇到此问题：

#### 1. 临时设置（当前会话有效）
```cmd
set JAVA_HOME=C:\path\to\graalvm-jdk-21
set GRAALVM_HOME=C:\path\to\graalvm-jdk-21
set PATH=%GRAALVM_HOME%\bin;%PATH%
```

#### 2. 永久设置（系统级别）
1. 右键点击"此电脑" → "属性"
2. 点击"高级系统设置"
3. 点击"环境变量"
4. 在"系统变量"中新建或编辑：
   - `JAVA_HOME`: `C:\path\to\graalvm-jdk-21`
   - `GRAALVM_HOME`: `C:\path\to\graalvm-jdk-21`
5. 编辑 `PATH` 变量，添加：`%GRAALVM_HOME%\bin`

#### 3. PowerShell 设置
```powershell
$env:JAVA_HOME = "C:\path\to\graalvm-jdk-21"
$env:GRAALVM_HOME = "C:\path\to\graalvm-jdk-21"
$env:PATH = "$env:GRAALVM_HOME\bin;$env:PATH"
```

## 验证设置

### 检查环境变量
```cmd
echo %JAVA_HOME%
echo %GRAALVM_HOME%
echo %PATH%
```

### 验证 Java 和 GraalVM
```cmd
java -version
native-image --version
```

预期输出应该显示 GraalVM 版本信息。

## 常见问题

### 1. 环境变量为空的问题

**问题表现**:
```
"JAVA_HOME="
"GRAALVM_HOME="
```

**原因分析**:
- GitHub Actions 中的环境变量传递机制问题
- Windows 批处理脚本中的 `set` 命令语法问题
- 环境变量在不同步骤间未正确传递

**解决方案**:

1. **使用引号包裹环境变量设置**:
   ```cmd
   set "JAVA_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}"
   set "GRAALVM_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}"
   ```

2. **在每个步骤中同时设置 env 和批处理脚本变量**:
   ```yaml
   env:
     JAVA_HOME: ${{ steps.setup-graalvm.outputs.graalvm-home }}
     GRAALVM_HOME: ${{ steps.setup-graalvm.outputs.graalvm-home }}
   run: |
     set "JAVA_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}"
     set "GRAALVM_HOME=${{ steps.setup-graalvm.outputs.graalvm-home }}"
   ```

3. **使用绝对路径验证工具**:
   ```cmd
   "%JAVA_HOME%\bin\java" -version
   "%GRAALVM_HOME%\bin\native-image" --version
   ```

### 2. 其他常见问题

### 1. 路径包含空格
如果路径包含空格，需要使用引号：
```cmd
set "JAVA_HOME=C:\Program Files\GraalVM\graalvm-jdk-21"
```

### 2. 路径分隔符
Windows 使用反斜杠 `\` 作为路径分隔符：
```cmd
set JAVA_HOME=C:\graalvm\graalvm-jdk-21
```

### 3. 权限问题
确保对 GraalVM 安装目录有读取权限。

## 故障排查步骤

1. **检查 GraalVM 安装**
   ```cmd
   dir "C:\path\to\graalvm-jdk-21\bin\java.exe"
   dir "C:\path\to\graalvm-jdk-21\bin\native-image.exe"
   ```

2. **检查环境变量设置**
   ```cmd
   set | findstr JAVA
   set | findstr GRAALVM
   ```

3. **测试 Maven 构建**
   ```cmd
   mvnw.cmd clean compile
   ```

4. **测试原生构建**
   ```cmd
   mvnw.cmd package -Pnative -DskipTests
   ```

## 相关文件

这个问题的解决方案已经集成到以下文件中：
- `d:\JavaProject\demoquarkus\.github\workflows\native-build.yml`
- `d:\JavaProject\demoquarkus\.github\workflows\windows-native-alternative.yml`

## 获取帮助

如果问题仍然存在：

1. 检查 GitHub Actions 日志中的详细错误信息
2. 确认 GraalVM 版本与配置中的版本一致（JDK 21）
3. 尝试使用备用工作流文件进行构建
4. 查看完整的环境变量输出进行调试