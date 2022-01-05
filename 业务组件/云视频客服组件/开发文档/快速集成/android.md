# 云视频客服组件
云视频客服Android SDK提供了一套简单易用的接口，允许开发者通过调用NEVCE SDK(以下简称SDK)提供的API，快速地集成云视频客服功能至现有Android应用中。

## 准备工作
1. 环境准备
| 名称 | 要求 |
| :------ | :------ |
| JDK版本  | >1.8.0 |
| 最小Android API 版本 | API 21, Android 5.0 |
| CPU架构支持 | ARM64、ARMV7 |
| IDE | Android Studio |
| 其他 | 依赖androidx，不支持support库 |

## 集成

1. 新建Android工程

    a. 运行Android Sudio，顶部菜单依次选择“File -> New -> New Project...”新建工程，选择'Phone and Tablet' -> 'Empty Activity' 单击Next。

    ![new android project](../../images/android_create_project.png)

    b. 配置工程相关信息，请注意Minimum API Level为API 21。

    ![configure project](../../images/android_create_project_set.png)

    c. 单击'Finish'完成工程创建。

2. 添加SDK编译依赖

    修改工程目录下的'app/build.gradle'文件，添加NEVCE SDK的依赖。
    ```groovy
    dependencies {
      //声明SDK依赖，版本可根据实际需要修改
      implementation 'com.netease.yunxin:vcelib:1.0.6'
    }
    ```
    之后通过顶部菜单'Build -> Make Project'构建工程，触发依赖下载，完成后即可在代码中引入SDK中的类和方法。

3. 权限处理

    云视频客服SDK正常工作需要应用获取以下权限
    ```xml
    <!-- 网络相关 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />

    <!-- 读写外部存储 -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <!-- 多媒体 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    ```
    以上权限已经在SDK内部进行声明，开发者可以不用在```AndroidManifest.xml```文件中重新声明这些权限，但运行时的权限申请需要应用开发者自己编码实现，可在应用首页中统一申请，详情可参考[Android运行时权限申请示例](https://developer.android.google.cn/guide/topics/permissions/overview)。如果运行时对应权限缺失，SDK可能无法正常工作，如云视频客服时无图像、对方听不到己方声音等。

4. SDK初始化

    在使用SDK其他功能之前首先需要完成SDK初始化，初始化操作需要保证在**Application**的**onCreate**方法中执行。代码示例如下：
    ```java
    public class MyApplication extends Application {
    
        private static final String TAG = "MyApplication";
    
        @Override
        public void onCreate() {
            super.onCreate();
            NEVCESDKConfig config = new NEVCESDKConfig();
            config.appKey = Constants.APPKEY;
            config.appName = context.getString(R.string.app_name);
            NEGuest.getInstance().initialize(this, config, new NECallback<Void>() {
                @Override
                public void onResult(int resultCode, String resultMsg, Void resultData) {
                    if (resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                        //TODO when initialize success
                    } else {
                        //TODO when initialize fail
                    }
                }
            });
        }
    }
    ```

5. 调用相关接口完成特定功能，详情请参考API文档。

- [登录鉴权](#登录鉴权)
  
    ```java
    //Token登录
    NEGuest.getInstance().login(String account, String token, NECallback<Void> callback);
    ```
    
- [查询业务列表](#查询业务列表)
  
    ```java
    NEGuest.getInstance().queryGroupList(NECallback callback);
    ```
    
- [发起呼叫](#发起呼叫)
  
    ```java
    NEGuest.getInstance().callWithUI(CallActivity.this, "10086", "访客一号", false);
    ```
    
- [取消呼叫](#取消呼叫)

```java
    NEGuest.getInstance().cancelCall();
```

- [注销登录](#注销)
  
    ```java
    NEMeetingSDK.getInstance().logout(NECallback<Void> callback);
    ```

## 业务开发

### 初始化

#### 描述

在使用SDK其他接口之前，首先需要完成初始化操作。

#### 业务流程

1. 配置初始化相关参数

```java
NEVCESDKConfig config = new NEVCESDKConfig();
config.appKey = Constants.APPKEY; //应用AppKey
```

2. 调用接口并进行回调处理，该接口无额外回调结果数据

```java
NEGuest.getInstance().initialize(getApplication(), config, new NECallback<Void>() {
    @Override
    public void onResult(int resultCode, String resultMsg, Void result) {
        if (resultCode == NEMeetingError.ERROR_CODE_SUCCESS) {
            //初始化成功
        } else {
            //初始化失败
        }
    }
});
```

#### 注意事项

- 初始化操作需要保证在**Application**类的**onCreate**方法中执行

--------------------

### 登录鉴权

#### 描述

请求SDK进行登录鉴权，只有完成SDK登录鉴权才允许后面的业务流程。说明如下：

| 登录方式 | 说明 | 接口 | 参数 | 其他 |
| :------ | :------ | :------ | :------ | :------ |
| Token登录 | 无 | `NEGuest#login` | accountId、accountToken | 账号信息需要从服务器获取，由接入方自行实现相关业务逻辑 |

下面就`Token登录`方式说明SDK登录逻辑，其他登录方式同理。

#### 业务流程

1. 获取登录用账号ID和Token。Token由网易云视频客服应用服务器下发，但SDK不提供对应接口获取该信息，需要开发者自己实现。

```java
String accountId = "accountId";
String accountToken = "accountToken";
```

2. 登录并进行回调处理，该接口无额外回调结果数据

```java
NEGuest.getInstance().login(accountId, accountToken, new NECallback<Void>() {
    @Override
    public void onResult(int resultCode, String resultMsg, Void result) {
        if (resultCode == NEMeetingError.ERROR_CODE_SUCCESS) {
            //登录成功
        } else {
            //登录失败
        }
    }
});
```

#### 注意事项

- SDK不提供账号注册机制，第三方应用集成SDK时需要为第三方应用的用户帐号绑定网易云视频客服系统中企业管理员开通的员工帐号，第三方应用的用户帐号和企业员工帐号是1:1映射的。

--------------------

### 查询业务列表

#### 描述

在已经完成SDK登录鉴权的状态下，查询业务列表。

发起呼叫之前需知道自己想要呼叫的业务，如果业务组为固定也可以直接调用呼叫接口发起。查询业务列表的接口用于实时获取到当前支持的业务列表。

#### 调用示例

```java
NEGuest.getInstance().queryGroupList(new NECallback<List<BusinessTypeModel>>() {
    @Override
    public void onResult(int resultCode, String resultMsg, List<BusinessTypeModel> resultData) {
        if (resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
            LogUtils.d(TAG, "queryGroupList onSuccess");
            businessTypeModelList = resultData;
        } else {
            LogUtils.d(TAG, "queryGroupList failed code = " + resultCode + ", msg = " + resultMsg);
        }
    }
});
```

--------------------

### 发起呼叫

#### 描述

根据业务id发起呼叫，呼叫等待页面和通话中页面由网易接管完成，上层无需关注流程。

#### 调用示例

```java
NEGuest.getInstance().callWithUI(CallActivity.this, "10086", "访客一号", false);
```

--------------------

### 取消呼叫

#### 描述

呼叫发起过程中可以主动取消呼叫。

#### 调用示例

```java
NEGuest.getInstance().cancelCall();
```

--------------------


### 监听回调

#### 描述

可以监听云视频客服的各种回调。

#### 调用示例

```java
void addListener() {
        NEGuest.getInstance().addListener(guestListener);
    }
    
    private NEGuestListener guestListener = new NEGuestListener() {
        @Override
        public void onInviteGuestJoinRoom(String roomId) {
        // 暂时不需要关心该回调
        }
        @Override
        public void onTransfered() {
        // 被转接，自带的界面已经处理，可以不关心
        }
        @Override
        public void onCallStateChange(GuestCallState callState) {
        // 通话状态变更，自带的界面已经处理，可以不关心
        // idle = 0
        // outgoing = 1
        // incomging = 2
        // talking = 3
        }
        @Override
        public void onQueryGroupList(GroupList groupList) {
        // 接口调用已有callback，可以不关心
        }
        @Override
        public void onLoginStateChange(LoginState loginState) {
        // 登录状态变化
        // idle = 0
        // logining = 1
        // logined = 2
        // logouting = 3
        }
    };
```

--------------------

