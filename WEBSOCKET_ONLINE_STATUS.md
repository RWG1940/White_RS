# WebSocket 在线状态功能使用说明

## 功能概述

实现了基于 Redis + WebSocket 的用户在线/离线状态显示功能：
- 用户建立 WebSocket 连接 = 在线
- 用户关闭/断线 = 离线
- 使用 Redis 存储在线状态，支持分布式部署

## 技术架构

1. **WebSocket**: 用于实时连接管理
2. **Redis**: 存储用户在线状态，支持过期自动清理
3. **JWT**: WebSocket 连接认证

## 配置说明

### 1. Redis 配置

在 `application-dev.yml` 或 `application-prod.yml` 中配置 Redis：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 5000
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
```

### 2. 确保 Redis 服务运行

开发环境需要启动 Redis 服务，默认端口 6379。

## API 接口

### 1. WebSocket 连接

**连接地址**: `ws://localhost:8080/ws/online`

**认证方式**: 
- 方式1: 通过 URL 参数传递 Token
  ```
  ws://localhost:8080/ws/online?token=YOUR_JWT_TOKEN
  ```
- 方式2: 通过请求头传递 Token
  ```
  Authorization: Bearer YOUR_JWT_TOKEN
  ```

**连接成功响应**:
```json
{
  "type": "connected",
  "message": "连接成功"
}
```

**心跳机制**:
- 客户端可以发送 `ping` 或 `heartbeat` 消息保持连接
- 服务器会响应 `pong` 消息
- 如果30分钟内没有心跳，Redis 中的在线状态会自动过期

### 2. 查询在线状态接口

所有接口需要 `ROLE_3294` 权限。

#### 2.1 检查用户是否在线
```
GET /api/online/check/{userId}
```

响应示例:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1,
    "isOnline": true
  }
}
```

#### 2.2 获取所有在线用户ID
```
GET /api/online/users
```

响应示例:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "onlineUserIds": [1, 2, 3],
    "count": 3
  }
}
```

#### 2.3 获取在线用户数量
```
GET /api/online/count
```

响应示例:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "count": 5
  }
}
```

#### 2.4 批量检查用户在线状态
```
POST /api/online/batch-check
Content-Type: application/json

[1, 2, 3, 4, 5]
```

响应示例:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "1": true,
    "2": false,
    "3": true,
    "4": false,
    "5": true
  }
}
```

## 前端使用示例

### JavaScript/TypeScript 示例

```javascript
// 获取 JWT Token（从登录接口获取）
const token = localStorage.getItem('token');

// 建立 WebSocket 连接
const ws = new WebSocket(`ws://localhost:8080/ws/online?token=${token}`);

// 连接成功
ws.onopen = function(event) {
    console.log('WebSocket 连接成功');
    
    // 定期发送心跳（每30秒）
    setInterval(() => {
        if (ws.readyState === WebSocket.OPEN) {
            ws.send('ping');
        }
    }, 30000);
};

// 接收消息
ws.onmessage = function(event) {
    const data = JSON.parse(event.data);
    console.log('收到消息:', data);
    
    if (data.type === 'connected') {
        console.log('连接成功');
    } else if (data.type === 'pong') {
        console.log('收到心跳响应');
    }
};

// 连接关闭
ws.onclose = function(event) {
    console.log('WebSocket 连接关闭');
    // 可以在这里实现重连逻辑
};

// 连接错误
ws.onerror = function(error) {
    console.error('WebSocket 错误:', error);
};
```

### Vue.js 示例

```vue
<template>
  <div>
    <p>在线状态: {{ isOnline ? '在线' : '离线' }}</p>
    <p>在线用户数: {{ onlineCount }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ws: null,
      isOnline: false,
      onlineCount: 0,
      token: localStorage.getItem('token')
    };
  },
  mounted() {
    this.connectWebSocket();
    this.loadOnlineCount();
  },
  beforeDestroy() {
    if (this.ws) {
      this.ws.close();
    }
  },
  methods: {
    connectWebSocket() {
      this.ws = new WebSocket(`ws://localhost:8080/ws/online?token=${this.token}`);
      
      this.ws.onopen = () => {
        this.isOnline = true;
        // 心跳
        setInterval(() => {
          if (this.ws.readyState === WebSocket.OPEN) {
            this.ws.send('ping');
          }
        }, 30000);
      };
      
      this.ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log('收到消息:', data);
      };
      
      this.ws.onclose = () => {
        this.isOnline = false;
        // 重连逻辑
        setTimeout(() => this.connectWebSocket(), 3000);
      };
      
      this.ws.onerror = (error) => {
        console.error('WebSocket 错误:', error);
      };
    },
    
    async loadOnlineCount() {
      try {
        const response = await fetch('/api/online/count', {
          headers: {
            'Authorization': `Bearer ${this.token}`
          }
        });
        const result = await response.json();
        this.onlineCount = result.data.count;
      } catch (error) {
        console.error('获取在线用户数失败:', error);
      }
    }
  }
};
</script>
```

## 工作原理

1. **连接建立**:
   - 客户端通过 WebSocket 连接服务器，携带 JWT Token
   - `WebSocketAuthInterceptor` 验证 Token 并提取用户信息
   - `OnlineStatusWebSocketHandler` 建立连接，将用户标记为在线（存储到 Redis）

2. **保持在线**:
   - 客户端定期发送心跳消息（ping/heartbeat）
   - 服务器收到心跳后刷新 Redis 中的过期时间
   - 如果30分钟内没有心跳，Redis 自动过期，用户被标记为离线

3. **连接断开**:
   - 客户端主动关闭或网络断开
   - `afterConnectionClosed` 或 `handleTransportError` 被调用
   - 从 Redis 中移除用户在线状态

4. **查询状态**:
   - 通过 REST API 查询 Redis 中的在线状态
   - 支持单个用户查询和批量查询

## Redis Key 说明

- `online:users`: Set 类型，存储所有在线用户ID
- `online:user:{userId}`: String 类型，存储用户在线时间戳，30分钟过期

## 注意事项

1. **Redis 必须运行**: 确保 Redis 服务正常运行
2. **Token 有效性**: WebSocket 连接需要有效的 JWT Token
3. **心跳机制**: 建议客户端每30秒发送一次心跳，避免连接被标记为离线
4. **重连机制**: 建议前端实现自动重连逻辑
5. **跨域配置**: 生产环境建议在 `WebSocketConfig` 中配置具体的允许域名，而不是 `*`

## 故障排查

1. **连接失败**:
   - 检查 Token 是否有效
   - 检查 Redis 是否运行
   - 查看服务器日志

2. **状态不准确**:
   - 检查 Redis 连接是否正常
   - 检查心跳是否正常发送
   - 检查 Redis Key 是否过期

3. **权限问题**:
   - 确保用户有 `ROLE_3294` 权限才能查询在线状态

