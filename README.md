T1 后端云 Android SDK 开发文档

本文档是 T1 后端云 官方提供的 Android SDK，方便 Android 开发人员快速使用 T1 进行后端开发。

# 准备工作

## 环境
 jdk：8+
 
## SDK 导入
gradle
```gradle
 dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://www.jitpack.io' }
		}
	}

  dependencies {
		implementation '等待更新'
	}
 
```

## 类库说明 

- 初始化 SDK

```java
 import net.t1y.v5.*;
```
```java
 // 初始化 SDK 配置
 T1Cloud.init(context,new Option(......));
 /**
 Option 构造函数参数说明（按顺序）：
 Url（必填） : String类型  您的域名，开发环境可使用Option.URL_DEFAULT
 Application_ID（必填） ：int类型 即Application ID（应用ID），请从T1后端云后台获取。
 APIKey（必填）：string类型  即 API_Key , 请从T1后端云后台获取。
 onSecretKeyGetInterface（选填）：OnSecretKeyGetInterface类型（接口），获取SecretKey的接口，可将SecretKey通过三方工具加密后，由该接口发起解密并回调。如：（）->return decrypt.code(xxxxxxxxx);
 secretKey(选填）：String类型 即SecretKey ，当启用其他混淆、加密、加固等防御手段后，可无需OnSecretKeyGetInterface，直接输入secretKey
 */
```

- 创建一条数据

```java

```

- 删除一条数据

```java

```

- 更新一条数据

```java

```

- 查询一条数据

```java

```

- 查询全部数据（分页查询）

```java

```

## 运行效果
