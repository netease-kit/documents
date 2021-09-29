# RoomKit组件

RoomKit SDK提供了一套简单易用的接口，允许开发者通过调用NEMeeting SDK(以下简称SDK)提供的API，快速地集成音视频房间功能至现有 Flutter应用中。

## 准备工作

1. 将此包用作库依赖它
   运行此命令（需要安装 flutter）：

```yaml
 flutter pub add yunxin_room_kit
```
2. 这将在您的包的 pubspec.yaml 中添加这样的一行（并运行一个隐式flutter pub get）：

```yaml
dependencies:
  yunxin_room_kit: ^0.1.1-rc.0
```

或者，您的编辑器可能支持flutter pub get。

3. 导入它

现在在您的 Da rt 代码中，您可以使用：

```dart
import 'package:yunxin_room_kit/room_kit.dart';
import 'package:yunxin_room_kit/room_service.dart';
```

4. SDK初始化

   > 在使用SDK其他功能之前首先需要完成SDK初始化。代码示例如下：

```dart
String? customServerConfig = await NEMeetingPlugin().getAssetService().loadCustomServer();
var initRes = await _roomKit.initialize(NERoomKitConfig(
      appKey: 'Your appKey',
      serverConfig: customServerConfig,
      reuseNIM: config.reuseNIM,
      extras: config.extras,
      aLoggerConfig: config.aLoggerConfig,
    ));
```

5. 调用相关接口完成特定功能，详情请参考API文档。

- [登录鉴权](#登录鉴权)

```dart
  /// 登录鉴权。在已登录状态下可以创建和加入房间，但在未登录状态下只能加入房间
  ///
  /// * [accountId]   登录accountId
  /// * [token]     登录令牌
NERoomKit.instance.getAuthService().loginWithToken(String accountId, String token);

 /// 登录鉴权。在已登录状态下可以创建和加入房间，但在未登录状态下只能加入房间
  ///
  /// * [username]   登录账号
  /// * [password]   登录密码
NERoomKit.instance.getAuthService().loginWithAccount(String username, String password);

 /// 自动登录
NERoomKit.instance.getAuthService().tryAutoLogin();
```

- [创建房间](#创建房间)

```dart
 final _roomService =  NERoomKit.instance.getRoomService();
    var result = await _roomService.startRoom(
      param,
      NEStartRoomOptions(
        noAudio: opts.noAudio,
        noVideo: opts.noVideo,
        noCloudRecord: opts.noCloudRecord,
        audioAINSEnabled: opts.audioAINSEnabled,
        noChat: opts.noChat,
      ),
    );
```



- [加入房间](#加入房间)

```dart
 final _roomService =  NERoomKit.instance.getRoomService();
   var result = await _roomService.joinRoom(
      param,
      NEJoinRoomOptions(
        noAudio: opts.noAudio,
        noVideo: opts.noVideo,
        audioAINSEnabled: opts.audioAINSEnabled,
      ),
    );
```

- [注销登录](#注销)

```dart
 /// 注销当前已登录的账号
NERoomKit.instance.getAuthService().logout();
```



## 业务开发

### 初始化

#### 描述

在使用SDK其他接口之前，首先需要完成初始化操作。

#### 业务流程

1. 配置初始化相关参数

```dart

var config = NERoomKitConfig(
      appKey: 'Your appKey',
      serverConfig: customServerConfig,/// 私有化配置
      reuseNIM: false, /// 默认IM复用为false
      extras: config.extras, /// 额外参数
      aLoggerConfig: config.aLoggerConfig,/// 默认日志配置
    );
```

2. 调用接口并进行回调处理，该接口无额外回调结果数据

```da
String? customServerConfig = await NEMeetingPlugin().getAssetService().loadCustomServer();
var initRes = await _roomKit.initialize(config);
```

### 登录鉴权

#### 描述

请求SDK进行登录鉴权，只有完成SDK登录鉴权才允许创建房间。SDK提供了多种登录方式可供选择，不同的登录接口需要不同的入参数。说明如下：

| 登录方式     | 说明                                        | 接口                          | 参数                    | 其他                                                       |
| :----------- | :------------------------------------------ | :---------------------------- | :---------------------- | :--------------------------------------------------------- |
| Token登录    | 无                                          | `NERoomKit#login`             | accountId、accountToken | 账号信息需要从房间服务器获取，由接入方自行实现相关业务逻辑 |
| SSOToken登录 | 无                                          | `NERoomKit#loginWithSSOToken` | ssoToken                | 无                                                         |
| 自动登录     | SDK尝试使用最近一次成功登录过的账号信息登录 | `NERoomKit#tryAutoLogin`      | 无                      | 无                                                         |

下面就`Token登录`方式说明SDK登录逻辑，其他登录方式同理。

#### 业务流程

1. 获取登录用账号ID和Token。Token由网易房间应用服务器下发，但SDK不提供对应接口获取该信息，需要开发者自己实现。

```dart
String accountId = "accountId";
String accountToken = "accountToken";
```

2. 登录并进行回调处理，该接口无额外回调结果数据

```dart
  var loginResult = await _roomKit.getAuthService().loginWithToken(accountId, token);
	if(loginResult.code=200 ){
  		/// 登录成功
	} else {	
  	 /// 登录失败
	}
```

#### 注意事项

- SDK不提供账号注册机制，第三方应用集成SDK时需要为第三方应用的用户帐号绑定网易房间系统中企业管理员开通的员工帐号，第三方应用的用户帐号和企业员工帐号是1:1映射的。

### 加入房间

#### 描述

在已登录或未登录的状态下，加入一个当前正在进行中的房间。

#### 业务流程

1. 配置加入房间用的相关参数

   ```dart
   var  params = NEJoinMeetingParams(
                   meetingId: meetingId, displayName: nickName);
   var option = NEJoinRoomOptions(
           noAudio:  noAudio,
           noVideo:  noVideo,
           audioAINSEnabled:  audioAINSEnabled,
         )
   ```

2. 调用接口并进行回调处理。该接口无额外回调结果数据，可根据错误码判断是否成功

```dart
final _roomService =  NERoomKit.instance.getRoomService();    
var result = await _roomService.joinRoom(
      param,
      option,
    );
```

3. 加入房间成功后，根据可以调用  NERoomKit.instance.getInRoomService()获取房间内服务。

#### 注意事项

- 房间号不能为空，需要配置为真实进行中的房间ID
- 该接口支持登录和未登录状态调用

### 创建预约房间

#### 描述

在已登录状态下，预约一个房间

#### 业务流程

1. 创建预约房间

```dart
   NERoomKit.instance.getPreRoomService().scheduleRoom(meetingItem).then((result) {
      LoadingUtil.cancelLoading();
      scheduling = false;
      if (result.code == HttpCode.success && result.data != null) {
        /// 预约成功
      } else {
        ///预约失败
      }
    });
```

### 取消预约房间

#### 描述

在已登录状态下，取消一个预约房间

#### 业务流程

1. 取消预约房间

```dart
 var result =  NERoomKit.instance.getPreRoomService().cancelRoom(uniqueId);
```

#### 注意事项

- 取消预约房间的参数meetingUniqueId是服务端返回的唯一码，从NEMeetingItem#getMeetingUniqueId处获取。
- 房间状态在进行中、已回收状态或者已超过结束时间，是无法取消。

### 查询预定房间信息

#### 描述

在已登录状态下，查询一个预约房间

#### 业务流程

1. 查询预定房间信息

```dart
var result = NERoomKit.instance.getPreRoomService().getRoomItemByUniqueId(uniqueId);
```

#### 注意事项

- 查询预约房间的参数roomUniqueId是服务端返回的唯一码。

### 查询特定状态下的预约房间列表

#### 描述

在已登录状态下，查询特定状态下的预约房间列表

#### 业务流程

1. 查询预约房间列表

```dart
var result = NERoomKit.instance.getPreRoomService().getRoomList(status);
```

#### 注意事项

- 查询特定
  下的预约房间列表在时间范围上，是默认返回最近一周的。

### 注销

#### 描述

请求SDK注销当前已登录账号，返回未登录状态。

#### 业务流程

1. 调用接口并进行回调处理。该接口无额外回调结果数据，可根据错误码判断是否成功

```dart
    final NERoomKit _roomKit = NERoomKit.instance;
    var result = await _roomKit.getAuthService().logout();
```

#### 注意事项

- 账号注销后，登录状态被清空，不再允许创建房间

### 使用房间设置服务

#### 描述

通过房间设置服务，可设置和查询用户的当前房间设置，如入会时的音视频开关、房间持续时间的显示等。

#### 业务流程

1. 获取房间设置服务

```dart
  final settingsService = NERoomKit.instance.getSettingsService();
 ///查询是否开启美颜功能
  bool isBeautySupported = settingsService.isBeautySupported();

  /// 是否允许开启直播
  bool isLiveStreamSupported();

  /// 查询白板服务开关状态
  /// return true-打开，false-关闭
  bool isWhiteboardSupported = settingsService.isWhiteboardSupported();

  ///查询是否为当前用户启用了云端录制，房间内本地属性
  bool isCloudRecordSupported = settingsService.isCloudRecordSupported();

  ///查询当前应用是否支持聊天室功能
  bool isChatroomSupported = settingsService.isChatroomSupported();

  /// 屏幕共享功能是否可用
  /// Android 均支持
  /// iOS 12以上才支持
  bool isScreenShareSupported  = settingsService.isScreenShareSupported();
```

#### 注意事项

- 针对已登录用户而言，每个用户有自己独立的一份房间设置；其他所有未登录用户、匿名用户共享一份房间设置。
- 美颜服务开通官网咨询渠道：[云信官网](http://yunxin.163.com/)