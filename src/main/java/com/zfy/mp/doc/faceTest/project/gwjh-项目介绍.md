# 公文交换平台 - 项目介绍

## 一、项目架构

```
┌─────────────────────────────────────────────────────────────────┐
│                        微服务架构                                  │
│                                                                  │
│  ┌─────────────┐                                               │
│  │   客户端     │  (Admin管理后台 / App / 外部政务系统)           │
│  └──────┬──────┘                                               │
│         │ HTTP                                                 │
│         ▼                                                       │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              smart-cloud-gateway（API网关）               │   │
│  │           基于Spring Cloud Gateway + Nacos               │   │
│  │         统一鉴权 / 路由转发 / 限流 / 日志                  │   │
│  └────────────────────────┬────────────────────────────────┘   │
│                           │                                      │
│         ┌─────────────────┼─────────────────┐                    │
│         │                 │                 │                    │
│         ▼                 ▼                 ▼                    │
│  ┌────────────┐   ┌────────────┐   ┌─────────────────────┐      │
│  │smart-module│   │smart-module│   │smart-module-official│      │
│  │ -system    │   │ -infra     │   │ -doc（公文交换核心）  │      │
│  │ (用户/权限)│   │ (基础设施)  │   │                     │      │
│  └────────────┘   └────────────┘   └─────────────────────┘      │
│                                                │                 │
│                              ┌─────────────────┼─────────────┐    │
│                              │                 │             │    │
│                              ▼                 ▼             ▼    │
│                     ┌────────────┐   ┌────────────┐  ┌──────────┐│
│                     │发文管理     │   │收文管理    │  │阅文管理  ││
│                     │Outgoing    │   │Receiving  │  │Reading  ││
│                     └────────────┘   └────────────┘  └──────────┘│
└─────────────────────────────────────────────────────────────────┘
```

**架构说明：**
- **smart-cloud-gateway**：API网关，统一鉴权、路由、限流
- **smart-module-system**：系统模块，用户、角色、权限、组织、字典
- **smart-module-infra**：基础设施，文件、字典、多租户
- **smart-module-official-doc**：公文交换核心业务模块（发文/收文/阅文/统计）

---

## 二、核心技术栈

| 层级 | 技术 |
|------|------|
| 基础框架 | Spring Boot 2.7.18 + Spring Cloud 2021.0.9 |
| Java版本 | Java 11 |
| ORM | MyBatis-Plus 3.5.7 + dynamic-datasource 4.3.1 |
| 注册/配置中心 | Alibaba Nacos |
| 服务保障 | Redisson 3.36.0（分布式锁）、Resilience4j（熔断降级） |
| 定时任务 | XXL-Job 2.3.1 |
| RPC框架 | OpenFeign |
| 接口文档 | Swagger 2.2.8 + Knife4j |
| 工具库 | Hutool、Lombok、MapStruct、EasyExcel |

---

## 三、smart-module-official-doc 核心业务

### 3.1 模块分层

```
smart-module-official-doc/
├── smart-module-official-doc-api        # API层：枚举、常量、DTO
├── smart-module-official-doc-server     # Server层：启动类、配置
└── smart-module-official-doc-service    # Service层：核心业务(296个Java文件)
    ├── controller/admin/official/       # 公文管理
    ├── controller/admin/outgoing/       # 发文管理
    ├── controller/admin/receiver/       # 收文管理
    ├── controller/admin/reading/        # 阅文管理
    ├── controller/admin/statistics/     # 统计
    ├── service/official/                # 公文服务
    ├── service/outgoing/                # 发文服务
    ├── service/receiving/               # 收文服务
    ├── service/exchange/                # 数据交换（核心）
    └── dal/dataobject/                  # 实体类
```

### 3.2 核心功能模块

| 模块 | 说明 |
|------|------|
| **发文管理** | 公文创建、编辑、审核、发布、撤回、归档 |
| **收文管理** | 公文接收、签收、退回、反馈 |
| **阅文管理** | 领导阅文、阅文进度跟踪 |
| **数据交换** | 跨系统数据互通（XML格式） |
| **公文统计** | 收发文件统计、逾期统计 |

### 3.3 公文操作类型

| 操作 | 枚举值 | 说明 |
|------|--------|------|
| SEND_METHOD | 11 | 文件发送 |
| RETRUE_METHOD | 13 | 文件退回 |
| FEEDBACK_METHOD | 14 | 文件反馈 |
| BACK_METHOD | 15 | 文件退文 |
| URGING_METHOD | 17 | 文件催办 |
| SIGN_METHOD | 18 | 文件签收 |
| ADDITION_METHOD | 19 | 文件增发 |
| REISSUE_METHOD | 20 | 文件补发 |
| DELAY_METHOD | 21 | 文件延期 |
| READING_METHOD | 22 | 领导阅文 |
| RECYCLE_METHOD | 12 | 文件回收 |

### 3.4 公文状态流转

```
STAGING(暂存) → ADUIT(待审核) → PUBLISHING(待发布) → PUBLISHED(已发布)
                                                       ↓
                                                  FORWARD(转发)
                                                       ↓
                                               RECYCLE(已撤回) / ARCHIVE(已归档)
```

### 3.5 核心实体设计

| 实体 | 说明 |
|------|------|
| **OfficialDocumentEntity** | 公文主表 |
| **OutgoingDocumentEntity** | 发文记录表 |
| **ReceivingDocumentEntity** | 收文记录表 |
| **MainReceiverUnitEntity** | 主送单位表 |
| **CopyUnitEntity** | 抄送单位表 |
| **OfficialDocumentAttachmentEntity** | 公文附件表 |
| **OfficialDocumentOperateRecordEntity** | 操作记录表 |
| **ReadingDocumentEntity** | 阅文表 |
| **ReceiptEntity** | 回执表 |

---

## 四、STAR法则项目介绍

### 【Situation - 背景】

公文交换平台是为政府机关/大型企事业单位设计的政务协同系统，实现电子公文的创建、发送、接收、签收、退回、归档全流程数字化管理。平台支持多级组织架构对接，实现跨单位公文流转，日均处理公文3000+份，对接外部政务系统10+个。

### 【Task - 任务】

作为核心开发成员，负责**公文交换核心模块（发文/收文/阅文/数据交换）**的设计与开发，基于Spring Cloud微服务架构，实现与外部政务系统的数据互通，支持多租户隔离、分布式事务、限流熔断等企业级特性。

### 【Action - 行动】

**微服务架构设计：**
- 基于Spring Cloud + Nacos + Gateway构建微服务架构，实现服务注册发现、配置中心、API网关统一管理
- 采用dynamic-datasource实现多数据源切换，支持读写分离
- 引入Redisson分布式锁，保障并发场景下数据一致性
- 封装Resilience4j熔断降级策略，保障系统高可用

**发文管理模块：**
- 设计公文多级审核流程，支持草稿、审核、发布、撤回、归档状态流转
- 实现公文附件在线预览（Word/PDF），支持大文件分片上传
- 封装公文模板引擎，支持标准公文格式一键生成
- 操作记录完整追溯，每一步操作均有日志记录

**收文管理模块：**
- 实现公文自动分发机制，根据主送/抄送单位自动创建收文记录
- 支持签收、退回、反馈多种处理方式
- 未签收公文自动催办提醒，支持多轮催办
- 签收率/退回率等统计数据实时展示

**阅文管理模块：**
- 实现领导阅文顺序/会签两种模式
- 阅文进度实时跟踪，未阅文人员自动提醒
- 阅文意见在线批注，支持手写签名

**数据交换模块（核心亮点）：**
- 设计基于XML的数据交换标准（ExchangeCenterAcceptController）
- 实现MethodServiceFactory工厂模式，根据操作类型动态路由到对应处理器
- 支持DocumentSendMethodServiceImpl（发送）、DocumentSignMethodServiceImpl（签收）、DocumentReturnMethodServiceImpl（退回）等10+种操作类型
- 对接外部政务系统，实现跨平台公文自动交换
- 数据交换日志全程记录，支持异常自动告警

**系统安全设计：**
- 基于OAuth2 Token统一身份认证，JWT令牌验签
- 网关层SmartTokenAuthenticationFilter全局拦截，LoadingCache缓存用户信息
- 数据权限精细化控制，支持按部门/角色/数据范围多维度配置
- 操作日志完整记录，支持审计追溯

### 【Result - 结果】

- 系统平稳运行2年+，日均处理公文3000+份
- 公文流转周期从线下3-5天缩短至线上2小时内
- 对接外部政务系统10+个，数据交换成功率99.9%
- 签收率达95%+，平均处理时长降低70%
- 支持多租户隔离，承载3个下属单位同时使用

---

## 五、简历写法示例

```text
公文交换平台 | 核心开发
项目架构：Spring Cloud微服务 + Nacos + Gateway + MyBatis-Plus
技术栈：Spring Boot 2.7、Spring Cloud 2021、Nacos、Redis、MyBatis-Plus、
        Redisson、XXL-Job、OpenFeign、Knife4j
项目描述：面向政府机关/大型企事业单位的政务协同系统，实现电子公文创建、发送、接收、
         签收、退回、归档全流程数字化，日均处理公文3000+，对接外部政务系统10+个。
个人职责：
  - 负责公文交换核心模块（发文/收文/阅文/数据交换）设计与开发
  - 设计基于XML的跨系统数据交换方案，实现与10+个外部政务系统无缝对接
  - 基于MethodServiceFactory工厂模式封装10+种公文操作类型处理器
  - 引入Redisson分布式锁保障并发安全，接口响应时间降低60%
  - 基于Spring Cloud Gateway实现统一鉴权，支持OAuth2 Token验证
  - 封装公文多级审核流程，审核周期从3-5天缩短至2小时内
```

---

## 六、核心API接口

| Controller | 路径 | 说明 |
|------------|------|------|
| ExchangeCenterAcceptController | `/ctrl/exCenterAcceptController/` | 交换中心数据接收（外部系统对接） |
| OutgoingDocumentRestController | `/official/document/outgoing/` | 发文管理 |
| ReceivingDocumentRestController | `/official/document/receive/` | 收文管理 |
| ReadingDocumentRestController | `/official/document/reading/` | 阅文管理 |
| DocumentStatisticsRestController | `/official/document/statistics/` | 公文统计 |

---

## 七、数据交换流程（外部系统对接）

```
外部政务系统
      │
      │ XML数据 POST /ctrl/exCenterAcceptController/accept
      ▼
ExchangeCenterAcceptController
      │
      │ MethodServiceFactory 根据method字段路由
      ▼
┌─────────────────────────────────────────────────────┐
│              MethodServiceFactory                   │
│                                                      │
│  ┌──────────────────┐  ┌──────────────────┐        │
│  │ DocumentSend     │  │ DocumentSign     │        │
│  │ MethodServiceImpl│  │ MethodServiceImpl│        │
│  │ (发送 11)        │  │ (签收 18)        │        │
│  └──────────────────┘  └──────────────────┘        │
│                                                      │
│  ┌──────────────────┐  ┌──────────────────┐        │
│  │ DocumentReturn   │  │ DocumentFeedback │        │
│  │ MethodServiceImpl│  │ MethodServiceImpl│        │
│  │ (退回 13)        │  │ (反馈 14)        │        │
│  └──────────────────┘  └──────────────────┘        │
│                    ... 10+种操作类型                   │
└─────────────────────────────────────────────────────┘
      │
      │ 处理完成后写入 ReceivingDocumentEntity
      ▼
   数据持久化
```

---

## 八、项目亮点总结

1. **微服务架构**：Spring Cloud + Nacos实现服务治理，Gateway统一入口
2. **多租户隔离**：一套系统支撑多个下属单位独立使用
3. **跨系统互通**：XML数据交换标准，支持10+个外部政务系统对接
4. **工厂模式设计**：MethodServiceFactory动态路由，扩展性强
5. **分布式锁保障**：Redisson锁保证并发安全
6. **完整操作审计**：每步操作均有记录，支持追溯
