---
name: dependency-audit
description: Systematic workflow to audit, evaluate, and clean up Maven/Gradle dependencies — identify unused or replaceable libraries and their transitive chains
source: auto-skill
extracted_at: '2026-06-27T10:02:00.000Z'
---

# 依赖审计与清理流程

从 `pom.xml` 出发，逐项审查 compile scope 依赖，判断是否需要保留、内联或删除。

## 审计流程

```
mvn dependency:tree
  ↓
对每个 compile scope 的直接依赖：
  1. grep 查找使用者
  2. 评估是否可内联（见 inline-library-replacement）
  3. 评估是否可删除（无使用者/死代码）
  4. 检查传递依赖树大小
  ↓
删除 → mvn compile → mvn test
  ↓
mvn dependency:tree | grep 确认传递依赖同步清除
```

## 快速审计命令

```bash
# 1. 只看 compile scope 的直接依赖
mvn dependency:tree -Dscope=compile -Dincludes='*:*'

# 2. 查某个依赖的所有使用者
grep -r "import com.example.lib" src/main/java

# 3. 查该依赖的传递链
mvn dependency:tree | grep -B5 "com.example.lib"

# 4. 删除后确认连带清理
mvn dependency:tree | grep -E "(lib|transitive1|transitive2)"
```

## 审计清单

对每个 compile 依赖回答：

| 问题 | 判断 |
|---|---|
| 有多少 `.java` 文件 import 了它？ | `grep -rl "import <pkg>" src/main/java` |
| 是反射/SPI 调用吗？ | 检查 `META-INF/services`、`Class.forName` |
| 是否可用 JDK 内置替代？ | Map/List、`javax.crypto`、`java.time`、`HttpURLConnection` |
| 是否可用项目内已有依赖替代？ | 如 `UNet` 替代 HTTP、`org.json` 替代 Jackson |
| 传递依赖树有多大？ | `mvn dependency:tree \| grep -c` |
| 传递依赖中有 CVE 吗？ | Maven Central / OSS Index |

## 常见可删除模式

### 1. 死代码依赖
```bash
# 发现：commons-exec 只有 WeiXinMediaUtils.java 使用
# 确认：WeiXinMediaUtils 无外部调用者
# 结论：两者皆可删
```

### 2. 云 SDK（核心逻辑 ≤ 1 个类）
```bash
# 确认：仅 SmsTencentImpl.java 使用 tencentcloud-sdk
# 评估：可用 UNet + TC3 签名实现（~120 行）
# 结论：删除 SDK + 5 个传递依赖
```

### 3. 仅用简单数据结构的库
```bash
# 确认：仅 UploadResourcesCached 使用 Guava Cache
# 评估：功能 = 100 条上限 + 10 分钟 TTL + 日志
# 实现：ConcurrentHashMap + ScheduledExecutorService（~60 行）
# 结论：删除 guava + 6 个传递依赖
```

### 4. 格式库（简单输出格式）
```bash
# 确认：仅 Outlook.java 使用 ical4j 生成 .ics
# 评估：iCalendar 是纯文本 RFC，字符串拼接即可
# 实现：StringBuilder（~80 行）
# 结论：删除 ical4j + 2 个传递依赖
```

## 保留判定

满足以下任一条件时**不删除**：

1. **广泛使用**：5+ 个文件依赖 → 替换成本高
2. **复杂实现**：密码学、协议解析、并发工具 → 自制易出错
3. **标准化依赖**：领域标准库（Servlet API、SLF4J、JUnit）
4. **运行时必需**：SPI 实现（`jakarta.activation:angus-activation`，即使只有 `jakarta.activation.DataHandler` 一处使用也不删）

### 例子
```
jakarta.mail → 多个文件使用 → 保留
angus-activation → jakarta.activation API 的唯一 SPI 实现 → 保留
swagger-annotations → 8 个 DAO 文件，114 处注解 → 询问用户后保留
```

## 本项目审计结果（本次对话）

| 操作 | 直接依赖 | 传递依赖数 |
|---|---|---|
| ✂️ 删除 | `aliyun-java-sdk-*` × 3 | 0（后续被 Tea SDK 替代） |
| ✂️ 删除 | `dysmsapi20170525` | 6 |
| ✂️ 删除 | `alidns20150109` | 6 |
| ✂️ 删除 | `tencentcloud-sdk-java-sms` | 4 |
| ✂️ 删除 | `ical4j` | 2 |
| ✂️ 删除 | `commons-exec` | 0 |
| ✂️ 删除 | `guava` | 6 |
| 📌 保留 | `angus-activation` | — (SPI 实现) |
| 📌 保留 | `swagger-annotations` | — (114 处，用户确认) |

**compile scope 从 20+ 降至 12 个直接依赖。**
