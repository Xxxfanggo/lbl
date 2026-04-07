# Redis 面试题总结

> 来源：JavaGuide (javaguide.cn)
> 整理：Claude

---

## 目录

1. [Redis 基础](#redis-基础)
2. [Redis 数据类型](#redis-数据类型)
3. [Redis 持久化](#redis-持久化)
4. [Redis 线程模型](#redis-线程模型)
5. [Redis 内存管理](#redis-内存管理)
6. [Redis 事务](#redis-事务)
7. [Redis 性能优化](#redis-性能优化)
8. [Redis 缓存问题](#redis-缓存问题)
9. [Redis 集群](#redis-集群)
10. [Redis 命令速查](#redis-命令速查)

---

## Redis 基础

### Redis 是什么？

Redis（Remote Dictionary Server）是一个基于 C 语言编写的开源内存数据库，使用 BSD 协议实现。它支持丰富的数据结构如 String、Hash、List、Set、Zset 等，常用于缓存、消息队列、分布式锁等场景。

### Redis 有什么特点？

1. **内存存储**：数据存储在内存中，读写性能极高
2. **多种数据结构**：支持 String、Hash、List、Set、Zset 等
3. **持久化**：支持 RDB 和 AOF 两种持久化方式
4. **高可用**：支持主从复制、哨兵、集群等模式
5. **单线程**：采用单线程模型，避免锁竞争

### Redis 和 Memcached 的区别？

| 特性 | Redis | Memcached |
|------|-------|-----------|
| 数据类型 | 丰富（5种基本+3种特殊） | 仅字符串 |
| 持久化 | 支持 RDB 和 AOF | 不支持 |
| 集群 | 原生支持集群模式 | 需要二次开发 |
| 线程模型 | 单线程 + I/O 多路复用 | 多线程 |

---

## Redis 数据类型

### 5 种基本数据类型

#### String（字符串）

- **实现**：SDS（简单动态字符串）
- **特点**：二进制安全，可存储任何数据
- **命令**：`SET`、`GET`、`MSET`、`MGET`、`INCR`、`DECR`
- **场景**：缓存、计数器、分布式锁、Session

#### List（列表）

- **实现**：QuickList（3.2+），之前是 ZipList 或 LinkedList
- **特点**：双向链表，支持两端操作
- **命令**：`LPUSH`、`RPUSH`、`LPOP`、`RPOP`、`LRANGE`
- **场景**：消息队列、最新列表、简单分页

#### Hash（哈希）

- **实现**：ZipList（小数据量）或 Dict（大数据量）
- **特点**：键值对映射，适合存储对象
- **命令**：`HSET`、`HGET`、`HMSET`、`HMGET`、`HGETALL`
- **场景**：对象存储、购物车

#### Set（集合）

- **实现**：Intset（纯整数）或 Dict
- **特点**：无序唯一，支持交并差操作
- **命令**：`SADD`、`SMEMBERS`、`SINTER`、`SUNION`、`SDIFF`
- **场景**：标签、好友关系、抽奖

#### Zset（有序集合）

- **实现**：ZipList（小数据量）或 SkipList + Dict
- **特点**：有序唯一，通过 score 排序
- **命令**：`ZADD`、`ZRANGE`、`ZREVRANK`、`ZSCORE`
- **场景**：排行榜、优先级队列

### 3 种特殊数据类型

#### Bitmap（位图）

- **实现**：String 类型，支持位操作
- **命令**：`SETBIT`、`GETBIT`、`BITOP`、`BITCOUNT`
- **场景**：签到统计、用户在线状态、UV 统计

#### HyperLogLog（基数统计）

- **特点**：概率算法，节省内存，用于估计集合中不重复元素数量
- **命令**：`PFADD`、`PFCOUNT`、`PFMERGE`
- **场景**：独立访客统计

#### GEO（地理坐标）

- **实现**：Zset，底层使用 GeoHash 编码
- **命令**：`GEOADD`、`GEOPOS`、`GEODIST`、`GEORADIUS`
- **场景**：附近的人、LBS 应用

---

## Redis 持久化

### 两种持久化方式

#### RDB（Redis Database）

- **原理**：定时快照，将内存数据全量写入二进制文件
- **触发**：
  - 手动：`SAVE`（阻塞）、`BGSAVE`（后台异步）
  - 自动：`save m n`（m 秒内 n 次修改）
- **优点**：恢复速度快，适合备份
- **缺点**：可能丢失最后一次快照后的数据

#### AOF（Append Only File）

- **原理**：记录所有写操作命令到日志文件
- **刷盘策略**：
  - `always`：每次写入都刷盘，最安全但最慢
  - `everysec`：每秒刷盘，推荐
  - `no`：由系统决定刷盘时机
- **重写**：`BGREWRITEAOF` 压缩日志文件
- **优点**：数据安全性更高
- **缺点**：文件体积大，恢复速度慢

### 如何选择？

- **仅做缓存**：可以不用持久化
- **同时开启**：Redis 启动时优先加载 AOF（数据更完整）
- **建议**：RDB + AOF 组合使用

---

## Redis 线程模型

### 单线程模型

Redis 4.0 之前完全是单线程模型（核心网络模块）。

**为什么单线程这么快？**
1. **内存操作**：数据存在内存，I/O 不是瓶颈
2. **I/O 多路复用**：使用 epoll/select/kqueue 实现高效 I/O
3. **避免锁竞争**：单线程无上下文切换开销
4. **高效数据结构**：C 语言实现的各种数据结构

### 6.0 多线程

Redis 6.0 引入了多线程，用于处理网络 I/O（协议解析），但核心命令执行仍是单线程。

### 多线程 vs 单线程

| 版本 | 模式 | 说明 |
|------|------|------|
| 4.0 之前 | 完全单线程 | 核心网络模块单线程 |
| 6.0 | 部分多线程 | 网络 I/O 多线程，命令执行单线程 |
| 后续版本 | 持续优化 | 多线程仅用于提升网络吞吐 |

---

## Redis 内存管理

### 内存淘汰策略

当内存达到上限时，Redis 提供了 8 种淘汰策略：

| 策略 | 说明 |
|------|------|
| `noeviction` | 不淘汰，写入返回错误（默认） |
| `volatile-lru` | LRU 算法淘汰设置了过期时间的键 |
| `allkeys-lru` | LRU 算法淘汰所有键 |
| `volatile-lfu` | LFU 算法淘汰设置了过期时间的键 |
| `allkeys-lfu` | LFU 算法淘汰所有键 |
| `volatile-random` | 随机淘汰设置了过期时间的键 |
| `allkeys-random` | 随机淘汰所有键 |
| `volatile-ttl` | 淘汰 TTL 最小的键 |

### 内存碎片

- **原因**：键删除后，内存未必立即归还系统
- **处理**：`MEMORY PURGE` 命令手动清理碎片
- **查看**：`INFO memory` 查看碎片率

### 内存占用

- **纯缓存**：`maxmemory` 设置合理大小
- **内存优化**：使用合适的数据结构，避免 bigkey

---

## Redis 事务

### MULTI / EXEC

Redis 的事务是一组命令的集合，命令被批量发送给 Redis 执行：

```bash
MULTI
SET key1 value1
SET key2 value2
GET key1
EXEC
```

### DISCARD

事务执行前取消所有命令：

```bash
MULTI
SET key1 value1
DISCARD
```

### WATCH

乐观锁，监控一个或多个键，如果在事务执行前被修改，事务将被取消：

```bash
WATCH key1
GET key1
MULTI
SET key1 newvalue
EXEC
```

### 错误处理

- **语法错误**：事务中任何命令失败都不会执行
- **运行时错误**：执行失败的命令会被跳过，其他命令继续执行

### 与 ACID 的对比

| ACID 特性 | Redis 事务支持情况 |
|-----------|------------------|
| 原子性 | 支持（命令批量执行） |
| 一致性 | 不保证（错误命令不执行） |
| 隔离性 | 通过 WATCH 实现 |
| 持久性 | 不支持（内存操作） |

---

## Redis 性能优化

### 慢查询定位

1. **开启慢查询日志**：`slowlog-log-slower-than` 设置阈值
2. **查看慢查询**：`SLOWLOG GET`
3. **分析**：`SLOWLOG LEN` 查看数量

### Bigkey 问题

**什么是 Bigkey？**
- String 类型 value > 10MB
- Hash/Set/Zset 元素数量 > 100000

**危害**：
- 占用内存大
- 网络传输慢
- 操作耗时（阻塞）

**如何发现**：
```bash
redis-cli --bigkeys
redis-cli --scan | xargs redis-cli --latency
```

**如何处理**：
- 压缩大字符串
- 拆分 Hash（field 分桶）
- 定期清理过期数据

### 缓存命中率

- **查看**：`INFO stats` 中的 `keyspace_hits/misses`
- **优化**：
  - 合理设置 TTL
  - 避免冷数据占用缓存
  - 使用合适的数据结构

### 常见优化手段

1. **使用连接池**：避免频繁建立连接
2. **Pipeline**：批量执行命令，减少 RTT
3. **避免大 key**：拆分或压缩
4. **合理设置 TTL**：避免僵尸数据
5. **使用恰当数据类型**：如用 Zset 代替 List 排序

---

## Redis 缓存问题

### 缓存穿透

**问题**：查询不存在的数据，每次都打到数据库。

**解决方案**：
1. **缓存空值**：将空结果缓存短时间
2. **布隆过滤器**：判断 key 是否存在
3. **参数校验**：拦截非法请求

### 缓存击穿

**问题**：热点 key 过期瞬间，大量请求击穿到数据库。

**解决方案**：
1. **互斥锁**：只允许一个线程重建缓存
2. **热点数据永不过期**：定期更新
3. **限流降级**：保护数据库

### 缓存雪崩

**问题**：大量 key 同时过期或 Redis 宕机。

**解决方案**：
1. **过期时间随机化**：避免同时过期
2. **高可用架构**：主从 + 哨兵 / 集群
3. **限流降级**：保护数据库
4. **提前演练**：做好应急预案

### 如何保证双写一致性？

1. **Cache Aside**（最常用）：
   - 读：先读缓存，缓存没有则读数据库并更新缓存
   - 写：先更新数据库，再删除缓存

2. **注意问题**：
   - 并发下可能短暂不一致
   - 删除缓存失败会导致数据不一致
   - 建议：延迟双删或消息队列补偿

---

## Redis 集群

### 主从复制

**原理**：
1. 主节点写数据
2. 从节点通过 `SYNC` 或 `PSYNC` 命令同步数据
3. 从节点默认为只读

**配置**：
```bash
# 从节点配置
replicaof masterip masterport
```

**缺点**：主节点宕机需要手动切换

### 哨兵模式

**作用**：
1. 监控主从节点健康状态
2. 自动故障转移
3. 通知应用新的主节点地址

**工作原理**：
1. 哨兵进程监控所有节点
2. 发现主节点宕机，通过投票选择新的主节点
3. 通知所有从节点切换
4. 告知应用新的主节点地址

### Cluster 集群

**原理**：
- 数据分片（16384 个槽位）
- 每个节点负责一部分槽位
- 客户端可连接任意节点

**槽位计算**：`CRC16(key) % 16384`

**特点**：
- 数据自动分片
- 自动故障转移
- 支持动态扩容缩容

### 三种模式对比

| 模式 | 复制方式 | 故障自动切换 | 数据分片 |
|------|---------|-------------|---------|
| 主从 | 异步 | 否 | 否 |
| 哨兵 | 异步 | 是 | 否 |
| Cluster | 异步 | 是 | 是 |

### 分布式锁

**实现**：
```bash
SET lock_key unique_value NX PX 30000
```

**注意**：
- value 要唯一（释放锁时验证）
- 设置过期时间防止死锁
- RedLock 算法提高可靠性

---

## Redis 命令速查

### 基础命令

```bash
PING                    # 测试连接
SELECT db              # 切换数据库
DBSIZE                  # 键数量
FLUSHDB                 # 清空当前库
FLUSHALL                # 清空所有库
```

### String 命令

```bash
SET key value           # 设置值
GET key                 # 获取值
MSET key1 val1 key2 val2  # 批量设置
MGET key1 key2          # 批量获取
INCR key                # 递增
DECR key                # 递减
EXPIRE key seconds      # 设置过期
TTL key                 # 查看剩余时间
```

### Hash 命令

```bash
HSET key field value    # 设置字段
HGET key field          # 获取字段
HMSET key f1 v1 f2 v2   # 批量设置
HMGET key f1 f2         # 批量获取
HGETALL key             # 获取所有字段
HDEL key field          # 删除字段
```

### List 命令

```bash
LPUSH key value         # 头部插入
RPUSH key value         # 尾部插入
LPOP key                # 头部弹出
RPOP key                # 尾部弹出
LRANGE key start end    # 范围查询
```

### Set 命令

```bash
SADD key member         # 添加
SMEMBERS key            # 获取所有
SISMEMBER key member    # 是否存在
SINTER key1 key2        # 交集
SUNION key1 key2        # 并集
SDIFF key1 key2         # 差集
```

### Zset 命令

```bash
ZADD key score member    # 添加
ZRANGE key start end    # 范围查询
ZREVRANGE key start end # 倒序查询
ZSCORE key member       # 获取分数
ZRANK key member        # 获取排名
```

### 持久化命令

```bash
SAVE                    # 同步保存 RDB
BGSAVE                  # 后台异步保存
LASTSAVE                # 上次保存时间
BGREWRITEAOF           # 重写 AOF
```

### 服务器命令

```bash
INFO                    # 信息
INFO memory             # 内存信息
SLOWLOG GET             # 慢查询日志
CONFIG GET name         # 获取配置
CONFIG SET name value   # 设置配置
```

---

## 常见面试题精选

### Q: Redis 为什么这么快？

内存操作 + I/O 多路复用 + 单线程无锁竞争 + 高效数据结构。

### Q: Redis 和 Memcached 最大的区别？

数据类型（Redis 5种 + 3种特殊 vs Memcached 仅字符串）、持久化支持、集群模式。

### Q: Redis 持久化方式？选哪个？

RDB（定时快照）和 AOF（记录操作日志）。建议同时开启，Redis 启动时优先加载 AOF。

### Q: 缓存穿透、击穿、雪崩的区别？

- 穿透：查询不存在的数据 → 布隆过滤器 + 缓存空值
- 击穿：热点 key 过期瞬间 → 互斥锁 + 永不过期
- 雪崩：大量 key 同时过期或 Redis 宕机 → 过期时间随机 + 高可用架构

### Q: Redis 的数据过期策略？

被动删除（访问时检查）、主动删除（定时任务）、内存淘汰策略（LRU/LFU/Random/TTL）。

### Q: Redis 事务和传统数据库事务的区别？

Redis 事务不支持回滚，不保证原子性，但通过 WATCH 实现乐观锁。

### Q: 如何保证 Redis 和数据库双写一致性？

Cache Aside 模式 + 延迟双删 + 消息队列补偿。

---

> 以上内容整理自 JavaGuide (javaguide.cn)
