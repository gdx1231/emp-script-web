---
name: java-sql-injection-fix
description: Audit and fix SQL injection vulnerabilities in Java code — quote escaping, whitelist validation, URL encoding, and string comparison bugs
source: auto-skill
extracted_at: '2026-06-27T00:16:39.538Z'
---

# Java SQL 注入审查与修复模式

## 高频 Bug：`replace("'","")` vs `replace("'","''")`

这是本项目中最常见的 SQL 注入缺陷模式。**去除引号**不等于**转义引号**。

```java
// ❌ 错误：去除单引号，改变了原始值且不安全
String w = "usr_unid='" + usrUnid.replace("'", "") + "'";

// ✅ 正确：转义单引号（SQL 标准），保留原始值
String w = "usr_unid='" + usrUnid.replace("'", "''") + "'";
```

### 审查命令

快速定位所有 `replace("'", "")` 调用：
```bash
grep -rn "replace.*\"'\".*\"\"" --include="*.java" src/
```

### 为什么 `replace("'","")` 不安全

- 输入 `admin'--` → 去除引号后变 `admin--`，在某些 SQL 方言中 `--` 是注释符
- 输入 `\' OR 1=1--` → 去除引号后变 `\ OR 1=1--`，反斜杠可能改变语义
- 改变了原始查询值，可能匹配到错误的数据

## 表名/库名参数：黑名单 → 白名单

当 SQL 中的表名或库名来自用户输入时，**黑名单过滤不充分**：

```java
// ❌ 黑名单：只拦截了引号和空格，可用 /**/ 或 ; 绕过
if (db.indexOf("'") >= 0 || db.indexOf(" ") >= 0) {
    return "error";
}
sb.append("SELECT * from " + db + ".sys_atts ...");

// ✅ 白名单：只允许字母、数字、下划线
if (!db.matches("[a-zA-Z0-9_]+")) {
    return "error";
}
sb.append("SELECT * from " + db + ".sys_atts ...");
```

## 参数拼接：URL 编码

当参数值需要拼接成 `key=value&key2=value2` 格式的字符串（如 `HtmlControl.init()` 的 paras 参数）时，必须 URL 编码：

```java
// ❌ 错误：token 中可能包含 & = # 等特殊字符，破坏参数结构
String paras = "fp_unid=" + fp_unid + "&FP_VALIDCODE=" + r_code;

// ✅ 正确：URL 编码防止参数注入
String paras = "fp_unid=" + URLEncoder.encode(fp_unid, "UTF-8")
    + "&FP_VALIDCODE=" + URLEncoder.encode(r_code, "UTF-8");
```

## Java 字符串比较：`==` vs `equals()`

```java
// ❌ 永远为 false（除非 ext 来自字符串常量池）
if (ext == "zip") { ... }

// ✅ 正确
if ("zip".equals(ext)) { ... }
```

常量放在前面（`"zip".equals(ext)`）可以避免 ext 为 null 时的 NPE。

## SQL 变量名混淆

当同一方法内构建多条 SQL（INSERT + SELECT）时，变量名容易混淆：

```java
// ❌ 典型错误：INSERT 语句 sql2 被当作 SELECT 执行
String sql2 = insertSql.toString();
conn.execute(sql2);                          // INSERT ✓
selectSql.setLength(0);
selectSql.append("SELECT ...");
DTTable tb = DTTable.getJdbcTable(sql2, conn); // 用了 sql2 而不是 selectSql ✗

// ✅ 正确：确保 SELECT 用 SELECT 的 SQL
DTTable tb = DTTable.getJdbcTable(selectSql.toString(), conn);
```

## 审查清单

审查任何 Java DAO/业务类时检查：

- [ ] 所有 `replace("'","")` → 改为 `replace("'","''")`
- [ ] 表名/库名参数是否用白名单校验（`[a-zA-Z0-9_]+`）
- [ ] 拼接到 URL 参数串的值是否 URLEncode
- [ ] 字符串比较是否用了 `==`（应为 `equals`）
- [ ] 同一方法内多条 SQL 是否用对了变量
- [ ] 用户输入是否完全未转义就拼入 SQL
- [ ] `System.out.println` 是否打印了敏感信息（凭证、响应内容）
