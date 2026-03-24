# Copilot instructions for `emp-script-web`

## Start with the right mental model

This repository is a Maven-built Java 8 **shared business module library** for the EMP / EasyWeb ecosystem. It is not a complete web site and it is not a standalone Spring-style application.

When you are new to the codebase, assume:

- Java classes in this repository provide reusable business capabilities such as auth, ACL, messaging, jobs, short URLs, uploads, QR codes, document handling, and WeChat integration.
- A lot of runtime behavior is driven by **EMP XML definitions** loaded from external resources or host projects.
- Several flows reference JSP/XML paths such as `/customer/webuser/region.xml`, `/front-web/page.xml`, and `/business/login.jsp`, so missing page logic is often outside this repository.
- There is no local `src/main/webapp`. Treat this repository as a module that is embedded into a hosting servlet web project.

## Where to look first

If you are trying to understand the repository quickly, read these areas in this order:

- `com.gdxsoft.web.dao` and `com.gdxsoft.chatRoom.dao`: generated table-mapping entities and DAOs. This is the persistence foundation used by most higher-level code.
- `com.gdxsoft.web.user`, `com.gdxsoft.web.acl`, and `com.gdxsoft.api`: login, token, permission, and identity flows.
- `com.gdxsoft.web.http`: thin HTTP adapters that expose domain logic through the servlet API.
- `com.gdxsoft.web.app`: request-context helpers for device detection, language selection, and app/site branding.
- `com.gdxsoft.web.module`, `com.gdxsoft.web.doc`, and related classes that call `HtmlControl`: these are the clearest examples of Java delegating behavior into EMP XML items.

## Build and test commands

```bash
mvn -q -DskipTests compile
```

Use this as the safest baseline check after edits. It works in a clean checkout.

```bash
mvn -q -Dtest=TestResourceConfig test
```

Runs a single environment-light test that verifies EMP resource loading from `define.xml` in the dependency JAR.

```bash
mvn -q -Dtest=TestWs test
```

Runs a single websocket-related test that does not require a configured database.

```bash
mvn -q test
```

The full test suite is not hermetic. `TestJWT` reaches `SupMainDao` / `ApiMainDao` and fails unless local `ewa_conf.xml` or `ewa_conf_console.xml` plus database connections are configured.

```bash
mvn clean package
```

Packages the main JAR, source JAR, and Javadoc JAR. During `package`, Maven also creates `target\emp-script-web-last.jar` and runs an Ant copy step to `..\..\workspace.newVersion\allclass\lib\`.

## High-level architecture

The main architectural layers are:

- `com.gdxsoft.web.dao` and `com.gdxsoft.chatRoom.dao`: generated DAO layer built on `ClassDaoBase<T>` / `IClassDao<T>`.
- `com.gdxsoft.web.*`, `com.gdxsoft.api`, `com.gdxsoft.project`, `com.gdxsoft.message`, `com.gdxsoft.spider`: service and business logic built on DAOs, `RequestValue`, `DTTable`, `DataConnection`, JSON helpers, crypto helpers, and third-party SDKs.
- `com.gdxsoft.web.http`: small servlet-facing wrappers. Each endpoint implements `IHttp` and typically builds a `RequestValue`, delegates to a domain helper, and returns a response body string.
- `com.gdxsoft.web.acl`: permission checks layered on EMP `IAcl`.
- `com.gdxsoft.web.app`: environment helpers for browser/app/wechat detection and app-level UI configuration.

The most important cross-file pattern is that many user-facing flows are **not fully defined in Java**. Instead, Java passes `xmlname` and `itemname` into `HtmlControl.init(...)` and lets EMP configuration drive the actual page or action. You see this in `RegisterOrLogin`, `DoLogin`, `Login`, `DocCreate`, and `HtModule`. When a change seems incomplete in Java, check whether the real behavior lives in EMP XML.

Another important packaging detail: `pom.xml` uses `src/main/java` as both source root and resource root. Non-Java files in that tree are packaged, but `.java`, `.xml`, and `.properties` are excluded from the Maven resource copy step. EMP resources such as `define.xml` / `ewa/*.xml` are commonly loaded from dependency JARs rather than from a local `src/main/resources` tree.

## Feature-reading shortcuts

When tracing a feature, these are good starting chains:

- Login and registration: `RegisterOrLogin` -> `DoLogin` -> `Login` -> paired EMP XML item
- JWT API auth: `com.gdxsoft.api.Auth` -> `ApiMainDao` / `SupMainDao`
- HTTP extension endpoints: a class in `com.gdxsoft.web.http` -> the service/helper it instantiates -> backing DAO or utility
- Module rendering: `HtModules` / `HtModule` -> `HtmlControl.init(...)` -> EMP XML path and item

## Key conventions

- `RequestValue` is the shared context object for request parameters, session/cookie values, and SQL parameters. Follow that pattern instead of introducing separate request-context abstractions.
- DAO classes in `web.dao` are highly regular and appear generated: static SQL strings, `FIELD_LIST`, `KEY_LIST`, `getRecord(...)`, `getRecords(...)`, and `createRequestValue(...)`. Match that shape when extending the DAO layer.
- HTTP entrypoints are intentionally thin. Keep business rules in service/domain classes instead of expanding `IHttp` implementations into controller-like classes.
- Login and permission state are convention-based. `AclBase.getString(...)` only trusts EMP values tagged as encrypted-cookie or session values, and many auth-related values use `G_*` names such as `G_USR_ID`, `G_ADM_ID`, and `G_SUP_ID`.
- The codebase is configuration-driven. XML, JSON, database configuration, and runtime metadata are all part of the real behavior, so code changes often need cross-checking against external config rather than only local Java sources.
