# AgentX 智能部署脚本

## 🚀 概述

AgentX 提供了智能化的部署解决方案，支持多种部署模式，满足不同场景需求：

- **`deploy.sh`**: 🧠 智能部署脚本，支持5种部署模式选择
- **`setup-external-db.sh`**: 🗄️ 数据库独立安装脚本
- **`start-dev.sh`**: 🔧 开发模式，智能依赖检查，快速迭代
- **`start.sh`**: 🏭 生产模式，完整构建，稳定运行

## 前置要求

- Docker (已安装并启动)
- Docker Compose 
- Git (用于自动克隆API网关项目)

## 🚀 快速开始

### 🧠 智能部署 (推荐)

```bash
# 一键智能部署，按需选择模式
./bin/deploy.sh
```

**智能部署特性：**
- 🎯 5种部署模式选择
- 🔧 自动生成配置文件
- 🗄️ 数据库部署选项
- 📊 智能服务健康检查
- ⚡ 按需资源分配

### 🗄️ 数据库独立部署

```bash
# 生产环境数据库初始化
./bin/setup-external-db.sh
```

**数据库独立部署特性：**
- 🔒 数据安全性最高
- ⚡ 性能最优
- 🛠️ 维护简单
- 💾 备份恢复便捷

### 🔧 传统部署方式

```bash
# 开发模式
./bin/start-dev.sh

# 生产模式  
./bin/start.sh
```

## 📋 部署模式详解

### 1. 🔥 完整模式 (Full)
**适用场景**: 功能演示、完整体验
- ✅ 包含所有服务和组件
- ✅ 内置数据库容器
- ✅ 高可用API网关
- ❌ 资源消耗较高

### 2. ⚡ 简化模式 (Simple)  
**适用场景**: 个人使用、轻量部署
- ✅ 核心功能完整
- ✅ 资源消耗适中
- ✅ 部署简单快速
- ❌ 无高可用特性

### 3. 🏭 生产模式 (Production)
**适用场景**: 正式环境、高可靠性
- ✅ 数据库独立部署
- ✅ 数据安全性高
- ✅ 性能最佳
- ⚠️ 需要预先配置数据库

### 4. 🧪 开发模式 (Development)
**适用场景**: 开发调试、代码修改
- ✅ 支持热重载
- ✅ 开发工具集成
- ✅ 调试友好
- ❌ 不适合生产环境

### 5. 🛠️ 自定义模式 (Custom)
**适用场景**: 特殊需求、灵活配置
- ✅ 完全自定义
- ✅ 按需选择组件
- ✅ 灵活配置
- ⚠️ 需要一定技术基础

## 🏗️ 服务架构

| 服务 | 端口 | 说明 | 完整模式 | 简化模式 | 生产模式 | 开发模式 |
|------|------|------|---------|---------|---------|---------|
| 前端应用 | 3000 | Next.js 用户界面 | ✅ | ✅ | ✅ | ✅ |
| 后端API | 8080 | Spring Boot 核心服务 | ✅ | ✅ | ✅ | ✅ |
| API网关 | 8081 | 高可用网关服务 | ✅ | ❌ | ❌ | ❌ |
| MCP网关 | 8005 | MCP协议网关 | ✅ | ✅ | ✅ | ✅ |
| PostgreSQL | 5432 | 主数据库 | 容器 | 容器 | 外部 | 容器 |

## 项目结构

```
AgentX/
├── bin/                          # 启动脚本
│   ├── start-dev.sh             # 开发模式启动
│   └── start.sh                 # 生产模式启动
├── AgentX/                      # 后端服务源码
├── agentx-frontend-plus/        # 前端服务源码
├── API-Premium-Gateway/         # API网关 (自动克隆)
├── docs/sql/                    # 数据库初始化脚本
├── docker-compose.yml           # 生产环境配置
└── docker-compose.dev.yml       # 开发环境配置
```

## 常用命令

### 服务管理

```bash
# 查看服务状态
docker compose -f docker-compose.dev.yml ps

# 停止所有服务
docker compose -f docker-compose.dev.yml down

# 查看服务日志
docker compose -f docker-compose.dev.yml logs -f [服务名]

# 重启特定服务
docker compose -f docker-compose.dev.yml restart agentx-backend
```

### 快速重启命令

```bash
# 重启后端服务 (代码修改后)
docker compose -f docker-compose.dev.yml restart agentx-backend

# 重启前端服务
docker compose -f docker-compose.dev.yml restart agentx-frontend

# 重启API网关
docker compose -f docker-compose.dev.yml restart api-gateway
```

## 默认账号

系统启动后自动创建以下测试账号：

| 角色 | 邮箱 | 密码 |
|------|------|------|
| 管理员 | admin@agentx.ai | admin123 |
| 测试用户 | test@agentx.ai | test123 |

⚠️ **重要**: 生产环境请立即修改默认密码并删除测试账号！

## 故障排查

### 常见问题

1. **端口被占用**
   ```bash
   # 检查端口占用
   lsof -i :3000,8080,8081,5432
   
   # 停止占用服务
   docker compose -f docker-compose.dev.yml down
   ```

2. **镜像构建失败**
   ```bash
   # 清理所有容器和镜像，重新开始
   docker compose -f docker-compose.dev.yml down -v --remove-orphans
   docker system prune -f
   
   # 重新启动
   ./bin/start-dev.sh
   ```

3. **API网关克隆失败**
   ```bash
   # 手动克隆API网关项目
   git clone https://github.com/lucky-aeon/API-Premium-Gateway.git
   ```

4. **数据库连接问题**
   ```bash
   # 重置数据库
   ./bin/start-dev.sh
   # 选择 'y' 重新初始化数据库
   ```

### 日志查看

```bash
# 查看所有服务日志
docker compose -f docker-compose.dev.yml logs -f

# 查看特定服务日志
docker compose -f docker-compose.dev.yml logs -f agentx-backend
docker compose -f docker-compose.dev.yml logs -f agentx-frontend
docker compose -f docker-compose.dev.yml logs -f api-gateway
docker compose -f docker-compose.dev.yml logs -f postgres
```

## 开发工作流

### 推荐的开发流程

1. **首次启动**
   ```bash
   git clone <项目地址>
   cd AgentX
   ./bin/start-dev.sh
   ```

2. **日常开发**
   ```bash
   # 修改代码后重启相关服务
   docker compose -f docker-compose.dev.yml restart agentx-backend
   ```

3. **测试新功能**
   ```bash
   # 完全重启环境
   ./bin/start-dev.sh
   ```

### 性能优化

- 首次启动需要下载依赖，耗时较长（约5-10分钟）
- 后续启动使用缓存，速度很快（约1-2分钟）
- 代码修改后只需重启对应服务，无需重新构建

## 技术说明

### 依赖缓存机制

- **Maven缓存**: 通过 `agentx-maven-cache` volume 持久化
- **NPM缓存**: 通过 `agentx-npm-cache` volume 持久化
- **镜像缓存**: 智能检查镜像存在性，避免重复构建

### 配置文件动态更新

启动脚本会自动：
- 克隆API网关项目到正确位置
- 更新docker-compose文件中的路径引用
- 确保所有服务使用正确的配置

## 支持

如果遇到问题：

1. 查看本文档的故障排查部分
2. 检查服务日志：`docker compose -f docker-compose.dev.yml logs -f`
3. 提交Issue并附上错误日志

---

**Happy Coding! 🚀** 