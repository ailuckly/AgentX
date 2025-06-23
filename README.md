# AgentX - 智能对话系统平台

[](https://opensource.org/licenses/MIT)

AgentX 是一个基于大模型 (LLM) 和多能力平台 (MCP) 的智能 Agent 构建平台。它致力于简化 Agent 的创建流程，让用户无需复杂的流程节点或拖拽操作，仅通过自然语言和工具集成即可打造个性化的智能 Agent。

## 🔗 相关链接

### 📦 子仓库
- 🛡️ **高可用网关**: [API-Premium-Gateway](https://github.com/lucky-aeon/API-Premium-Gateway) - 模型高可用组件
- 🌐 **MCP网关**: [mcp-gateway](https://github.com/lucky-aeon/mcp-gateway) - MCP服务统一管理
- 🏪 **MCP社区**: [agent-mcp-community](https://github.com/lucky-aeon/agent-mcp-community) - MCP Server 开源社区

### 📚 学习资源
- 🎥 **项目教程**: [B站视频教程](https://www.bilibili.com/video/BV1qaTWzPERJ/?spm_id_from=333.1387.homepage.video_card.click)
- 📖 **详细教学**: [敲鸭社区 - code.xhyovo.cn](https://code.xhyovo.cn/)
- 🎯 **项目演示**: [在线PPT介绍](https://needless-comparison.surge.sh)

## ⏳ 功能
 - [x] Agent 管理（创建/发布）
 - [x] LLM 上下文管理（滑动窗口，摘要算法）
 - [x] Agent 策略（MCP）
 - [x] 大模型服务商
 - [x] 用户
 - [x] 工具市场
 - [x] MCP Server Community
 - [ ] MCP Gateway （等待重构）
 - [x] 预先设置工具
 - [x] Agent 定时任务
 - [ ] Agent OpenAPI
 - [x] 模型高可用组件
 - [ ] RAG
 - [ ] 计费
 - [ ] Multi Agent
 - [ ] Agent 监控

## 🚀 如何安装启动

### 🛠️ 环境准备

#### 必需环境
  * **Docker & Docker Compose**: 用于容器化部署（推荐）
  * **Git**: 用于克隆项目和子模块

#### 本地开发环境（可选）
  * **Node.js & npm**: 推荐使用 LTS 版本
  * **Java Development Kit (JDK)**: JDK 17 或更高版本

#### 系统支持
  * **Linux**: 完全支持（推荐）
  * **macOS**: 完全支持
  * **Windows**: 完全支持（Windows 10/11 + WSL2 或原生支持）

### 🐳 All-in-One Docker 部署（最简单）

**🎯 真正的一键部署**：前端 + 后端 + 数据库，一个容器搞定！

#### 🚀 快速启动（使用预构建镜像）
```bash
# 直接拉取并启动（最快方式）
docker pull ghcr.io/lucky-aeon/agentx:latest
docker run -d --name agentx -p 3000:3000 -p 8088:8088 ghcr.io/lucky-aeon/agentx:latest

# 查看启动日志
docker logs agentx -f
```

#### 🔨 本地构建启动
```bash
# 克隆仓库并构建
git clone https://github.com/lucky-aeon/AgentX.git
cd AgentX
docker build -f Dockerfile.allinone -t agentx:latest .
docker run -d --name agentx -p 3000:3000 -p 8088:8088 agentx:latest
```

#### 📁 使用配置文件部署（推荐生产环境）
```bash
# 1. 获取配置文件模板
curl -O https://raw.githubusercontent.com/lucky-aeon/AgentX/main/config-templates/production.env
mv production.env agentx.env
vim ./agentx.env  # 编辑配置

# 2. 启动容器（使用预构建镜像）
docker run -d \
  --name agentx-prod \
  -p 3000:3000 \
  -p 8088:8088 \
  -v $(pwd)/agentx.env:/app/config/agentx.env:ro \
  ghcr.io/lucky-aeon/agentx:latest
```

#### 🔗 外部数据库模式
```bash
# 1. 创建Docker网络
docker network create agentx-network

# 2. 启动PostgreSQL（如果需要）
docker run -d \
  --name postgres-db \
  --network agentx-network \
  -e POSTGRES_DB=agentx \
  -e POSTGRES_USER=agentx_user \
  -e POSTGRES_PASSWORD=your_password \
  -p 5432:5432 \
  postgres:15

# 3. 配置外部数据库
curl -O https://raw.githubusercontent.com/lucky-aeon/AgentX/main/config-templates/external-database.env
mv external-database.env agentx.env
# 编辑 agentx.env，设置 DB_HOST=postgres-db

# 4. 启动AgentX容器
docker run -d \
  --name agentx-external \
  --network agentx-network \
  -p 3000:3000 \
  -p 8088:8088 \
  -v $(pwd)/agentx.env:/app/config/agentx.env:ro \
  ghcr.io/lucky-aeon/agentx:latest
```

#### 📋 访问地址
- **前端界面**: http://localhost:3000
- **后端API**: http://localhost:8088/api/health
- **管理后台**: http://localhost:3000/admin

#### 🔐 默认账号
| 角色 | 邮箱 | 密码 |
|------|------|------|
| 管理员 | admin@agentx.ai | admin123 |
| 测试用户 | test@agentx.ai | test123 |

#### 📖 详细配置说明
查看 [config-templates/README.md](config-templates/README.md) 获取完整的配置选项和部署指南。

---

### 🐳 开发模式部署

#### 🔥 开发模式

**最佳开发体验**：代码修改自动重启容器，无需手动操作！

##### Linux/macOS 用户
```bash
# 克隆仓库
git clone https://github.com/lucky-aeon/AgentX.git
cd AgentX

# 一键启动开发模式（包含热更新功能）
./bin/start-dev.sh
```

##### Windows 用户
```cmd
# 克隆仓库
git clone https://github.com/lucky-aeon/AgentX.git
cd AgentX

# 一键启动开发模式（包含热更新功能）
bin\start-dev.bat
```

#### 🏭 生产模式

##### Linux/macOS 用户
```bash
# 生产环境启动
./bin/start.sh
```

##### Windows 用户
```cmd
# 生产环境启动
bin\start.bat
```

### 📋 开发模式服务地址

开发模式启动成功后，您可以通过以下地址访问服务：

- **前端应用**: http://localhost:3000
- **后端API**: http://localhost:8080  
- **API网关**: http://localhost:8081
- **数据库连接**: localhost:5432

⚠️ **安全提示**：首次登录后请立即修改默认密码，生产环境请删除测试账号。

### 🛠️ 开发管理命令

#### Linux/macOS 用户
```bash
# 查看服务状态
docker compose -f docker-compose.dev.yml ps

# 停止所有服务（保留容器）
./bin/stop.sh

# 删除所有容器
docker compose -f docker-compose.dev.yml down

# 查看服务日志
docker compose -f docker-compose.dev.yml logs -f [服务名]

# 重启特定服务
docker compose -f docker-compose.dev.yml restart [服务名]
```

#### Windows 用户
```cmd
# 查看服务状态
docker compose -f docker-compose.dev.yml ps

# 停止所有服务（保留容器）
bin\stop.bat

# 删除所有容器
docker compose -f docker-compose.dev.yml down

# 查看服务日志
docker compose -f docker-compose.dev.yml logs -f [服务名]

# 重启特定服务
docker compose -f docker-compose.dev.yml restart [服务名]
```

### 📝 开发模式说明

开发模式启动后会显示以下信息并询问是否启动文件监听：

```
🔥 是否立即启动文件监听？(推荐)
  - 启动后修改代码会自动重启容器
  - 可随时按 Ctrl+C 停止监听
启动文件监听? [Y/n] (默认: Y):
```

- **选择 Y**：启动文件监听，修改代码自动生效
- **选择 n**：跳过文件监听，需要手动重启服务

### 💻 本地开发启动（传统方式）

如果您更喜欢传统的本地开发方式：

#### 1\. 启动数据库

```bash
cd script
chmod +x setup_with_compose.sh
./setup_with_compose.sh
```

#### 2\. 启动后端服务

```bash
cd AgentX
./mvnw spring-boot:run
```

#### 3\. 启动前端服务

```bash
cd agentx-frontend-plus
npm install --legacy-peer-deps
npm run dev
```

## 功能介绍

## Contributors

[![AgentX](https://contrib.rocks/image?repo=lucky-aeon/agentX)](https://contrib.rocks/image?repo=lucky-aeon/agentX)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=lucky-aeon/agentX&type=Date)](https://api.star-history.com/svg?repos=lucky-aeon/agentX&type=Date)


## 联系我们

我们致力于构建一个活跃的开发者社区，欢迎各种形式的交流与合作！

### 📱 私人微信
如有技术问题或商务合作，可添加开发者微信：

<img src="docs/images/wechat.jpg" alt="私人微信" width="200"/>

### 👥 微信交流群
加入我们的技术交流群，与更多开发者一起讨论：

<img src="docs/images/group.jpg" alt="微信交流群" width="200"/>

### 📢 微信公众号
关注我们的公众号，获取最新技术动态和产品更新：

<img src="docs/images/微信公众号.jpg" alt="微信公众号" width="200"/>

---

**如果二维码过期或无法扫描，请通过私人微信联系我。**

