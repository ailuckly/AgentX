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
 - [x] MCP Gateway
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

  * **Node.js & npm**: 推荐使用 LTS 版本。
  * **Java Development Kit (JDK)**: JDK 17 或更高版本。
  * **Docker & Docker Compose**: 用于部署数据库和其他依赖服务。

### 💻 本地启动

#### 1\. 克隆仓库

```bash
git clone https://github.com/your-username/AgentX.git # 替换为实际的仓库地址
cd AgentX
```

#### 2\. 启动数据库 (PostgreSQL)

进入 `script` 目录，并执行启动脚本。此脚本将使用 Docker Compose 启动一个 PostgreSQL 容器并初始化数据库。

```bash
cd script
chmod +x setup_with_compose.sh
./setup_with_compose.sh
```

成功启动后，您将看到 PostgreSQL 的连接信息：

```
🎉 PostgreSQL 容器已成功启动！
容器名称: agentx-postgres
连接信息:
  主机: localhost
  端口: 5432
  用户: postgres
  密码: postgres
  数据库: agentx
  连接URL: jdbc:postgresql://localhost:5432/agentx

你可以使用以下命令连接到数据库:
  docker exec -it agentx-postgres psql -U postgres -d agentx

✅ 数据库初始化完成！
```

#### 3\. 启动后端服务 (AgentX Java Application)

返回项目根目录，进入 `AgentX` 目录，并使用 Maven 或 Gradle（如果使用）构建并运行后端服务。

```bash
cd ../AgentX
# 如果是Maven项目，通常是
./mvnw clean install
./mvnw spring-boot:run
# 或者根据实际的jar包路径运行
# java -jar target/AgentX-0.0.1-SNAPSHOT.jar # 替换为实际的jar包名称
```

后端服务启动后，通常会监听 `8080` 端口。

#### 4\. 启动前端服务 (AgentX-Frontend-Plus)

返回项目根目录，进入 `agentx-frontend-plus` 目录，安装依赖并启动前端服务。

```bash
cd ../agentx-frontend-plus
npm install --legacy-peer-deps
npm run dev
```

前端服务启动后，通常会监听 `3000` 端口。

### ⚙️ 常用 Docker Compose 命令

在 `script` 目录下：

  * **启动所有服务**: `./setup_with_compose.sh` (首次运行或需要重新初始化数据库时推荐)
  * **启动/重启服务 (不初始化数据库)**: `docker-compose up -d`
  * **停止所有服务**: `docker-compose down`
  * **查看服务状态**: `docker ps`
  * **查看数据库日志**: `docker logs agentx-postgres`

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

