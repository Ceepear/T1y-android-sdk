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
## 添加依赖

```gradle
implementation 'com.squareup.okhttp3:okhttp:4.11.0'
implementation 'com.google.code.gson:gson:2.10.1'
```

## 配置权限

```xml
<!-- 访问网络权限 -->
<uses-permission android:name="android.permissionINTERNET" />
<!-- 访问网络状态权限 -->
<uses-permission android:name="android.permissionACCESS_NETWORK_STATE" />
<!-- 访问Wi-Fi状态权限 -->
<uses-permission android:name="android.permissionACCESS_WIFI_STATE" />
```

## 配置允许 HTTP 访问

如果你的应用目标为 Android 9（API 级别 28）或更高版本，还需要在清单文件中明确声明允许使用 HTTP 的域名。这可以通过在 `AndroidManifest.xml` 中的 `<application>` 元素内部添加以下内容来完成：

```xml
android:usesCleartextTraffic="true"
```

## 类库说明 
- 回调线程问题

   目前DataBean、T1YQuery的回调均在主线程，无需另外进行activity.runOnUiThread 或 view.post 或 handler.sendMessage
- 初始化 SDK
```java
 import net.t1y.v5.android.*;
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
 不用担心内存泄漏问题！静态字段会被在程序关闭时设为null
 */
```
- 拿到T1YClient
```java
  //如果不想要通过DataBean的方式可以使用
 T1YClient client = T1Cloud.client();
  //拿到T1YClient对象
```
-例子Class
```java
    public class User extends DataBean {
    private String username;
    private int age = -1;
    private long QQ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public long getQQ() {
        return QQ;
    }

    public void setQQ(long QQ) {
        this.QQ = QQ;
    }

    public void setAge(int age) {
        if(age != -1){
            updateCache("age",age);
            //如果不想要提交整个对象修改，可在每个set方法内，执行一次 updateCache缓存更新。
            //注意如果使用了updateCache那请全部set方法内都使用，不然未执行updateCache的内容需要再提交一次update
        }
        this.age = age;
    }
    }
```
- 创建一条数据

```java
   User user = new User();
   user.create(new CreateCallback() {
            @Override
            public void onCallback(int code, String msg) {
                 //可以在这里直接执行UI更新等。
                 //200 成功，之后可直接操作user
                 //400 失败，排查原因后，再次执行create
            }
        });
```

- 删除一条数据

```java
 user.delete(new DeleteCallback() {
            @Override
            public void onCallback(int code, String msg) {
                 //200 成功，之后对象内的objectId将自动删除
                 //400 失败，排查原因后，再次执行delete
            }
        });
```

- 更新一条数据

```java
 user.setAge(11);
 user.update(new UpdateCallback() {
            @Override
            public void onCallback(int code, String msg) {
                 //200 成功
                 //400 失败，排查原因后，再次执行update
            }
        });
```

- 查询一条数据

```java
   T1YQuery query = new T1YQuery();
   query.getDataById(User.class,objectID, new QueryCallback<User>() {
            @Override
            public void onCallback(int code, String msg, User data) {
                
            }
   });
```

- 查询全部数据（分页查询）

```java
  query.getDataAll(User.class, 1, 10, new QueryCallback<List<User>>() {
            @Override
            public void onCallback(int code, String msg, List<User> data) {
                
            }
        });
```

- 批量删除
  
```java
  DeleteBatch<User> userDeleteBatch = new DeleteBatch<>();
  //可复用
  userDeleteBatch.put(user1);
  userDeleteBatch.put(user2);
  query.deleteAll(userDeleteBatch, new DeleteCallback() {
            @Override
            public void onCallback(int code, String msg) {
                
            }
        });
```
- 批量添加
```java
  CreateBatch<User> userCreateBatch = new CreateBatch<>();
  //可复用
  userCreateBatch.put(user1);
  userCreateBatch.put(user2);
  query.createAll(userCreateBatch, new CreateCallback() {
            @Override
            public void onCallback(int code, String msg) {
                
            }
        });
```

## 运行效果
