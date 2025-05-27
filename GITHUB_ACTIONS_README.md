# GitHub Actions for Quarkus Native Build

本项目包含了用于构建 Quarkus 原生二进制镜像的 GitHub Actions 工作流配置。

## 工作流说明

### 文件位置
`.github/workflows/native-build.yml`

### 触发条件
- 推送到 `main` 或 `master` 分支
- 针对 `main` 或 `master` 分支的 Pull Request
- 手动触发 (workflow_dispatch)

### 构建任务

#### 1. build-native
使用 GraalVM 构建原生二进制文件：
- 设置 GraalVM JDK 21
- 缓存 Maven 依赖
- 编译和测试代码
- 构建原生二进制文件
- 上传构建产物

#### 2. build-native-container
在容器中构建原生镜像：
- 使用容器化构建原生二进制
- 构建 Docker 镜像
- 保存并上传 Docker 镜像

## 本地测试

### 构建原生二进制
```bash
./mvnw package -Pnative -DskipTests
```

### 容器化构建
```bash
./mvnw package -Pnative -DskipTests -Dquarkus.native.container-build=true
```

### 构建 Docker 镜像
```bash
docker build -f src/main/docker/Dockerfile.native -t quarkus-native:latest .
```

### 运行 Docker 容器
```bash
docker run -i --rm -p 8080:8080 quarkus-native:latest
```

## 注意事项

1. **GraalVM 版本**: 工作流使用 GraalVM Community Edition JDK 21
2. **构建时间**: 原生镜像构建可能需要较长时间（通常 5-15 分钟）
3. **内存要求**: 原生构建需要足够的内存，GitHub Actions 提供的环境通常足够
4. **产物保留**: 构建产物保留 30 天

## 自定义配置

如需修改配置，可以调整以下参数：
- Java 版本
- GraalVM 版本
- 缓存策略
- 产物保留时间
- Docker 镜像标签

## 故障排除

如果构建失败，请检查：
1. 代码是否通过测试
2. 依赖是否兼容 GraalVM
3. 是否有原生镜像不支持的特性
4. 内存是否足够