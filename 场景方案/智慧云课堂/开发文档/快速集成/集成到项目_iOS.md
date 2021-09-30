# 集成到项目
## 复用UI
### 步骤1:集成组件

1. 使用Xcode创建工程，进入`工程名.xcodeproj`同级目录，复制示例项目中的Modules至当前目录。

2. 执行`pod init`，创建Podfile文件。

3. 编辑Podfile文件并执行`pod install`：

   ```objc
   platform :ios, '10.0'
   target '工程名' do
     use_frameworks!
     pod 'EduUI', :path => 'Modules/EduUI/EduUI.podspec'
     pod 'EduLogic', :path => 'Modules/EduLogic/EduLogic.podspec'
     pod 'NEWhiteBoard', :path => 'Modules/NEWhiteBoard/NEWhiteBoard.podspec'
     pod 'NEScreenShareBroadcaster', '~> 0.1.0'
   ```

4. 以创建一对一场景老师页面为例，导入`EduUI/Room/Viewcontroller/NEEduOneMemberTeacherVC.h`,创建`NEEduOneMemberTeacherVC`对象即可，具体使用方式可参考示例项目。

## 自定义UI


### 步骤1:集成组件

1. 使用Xcode创建工程，进入`工程名.xcodeproj`同级目录，复制示例项目中的Modules至当前目录。

2. 执行`pod init`，创建Podfile文件。

3. 编辑Podfile文件并执行`pod install`：

   ```objc
   platform :ios, '10.0'
   target '工程名' do
     use_frameworks!
     pod 'EduLogic', :path => 'Modules/EduLogic/EduLogic.podspec'
     pod 'NEWhiteBoard', :path => 'Modules/NEWhiteBoard/NEWhiteBoard.podspec'
     pod 'NEScreenShareBroadcaster', '~> 0.1.0'
   ```

### 步骤2:添加权限

1. 在`Info.plist`文件中添加相机、麦克风访问权限：

   ```
   Privacy - Camera Usage Description
   Privacy - Microphone Usage Description
   ```

2. 在工程的`Signing&Capabilities`添加`Background Modes`，并勾选`Audio、Airplay、and Picture in Picture`。


### 步骤3:初始化EduLogic组件

```objc
NEEduKitOptions *option = [[NEEduKitOptions alloc] init];
option.authorization = [KeyCenter authorization];
option.baseURL = [KeyCenter baseURL];
[[EduManager shared] setupAppKey:[KeyCenter appKey] options:option];
```

### 步骤4:用户登录

```objc
__weak typeof(self)weakSelf = self;
[[EduManager shared] login:nil success:^(NEEduUser * _Nonnull user) {
  __strong typeof(self)strongSelf = weakSelf;
  //登录成功，创建房间
  [strongSelf createRoom];
} failure:^(NSError * _Nonnull error) {
        //登录失败处理逻辑
}];
```

### 步骤5:创建房间

```objc
NEEduRoom *room = [[NEEduRoom alloc] init];
    room.roomName = [NSString stringWithFormat:@"%@的课堂",self.nicknameView.text];
    room.sceneType = self.lessonType;
    switch (room.sceneType) {
        case NEEduSceneType1V1:
        {
            room.configId = 5;
        }
            break;
        case NEEduSceneTypeSmall:
        {
            room.configId = 6;
        }
            break;
        case NEEduSceneTypeBig:
        {
            room.configId = 7;
        }
            break;
        default:
            break;
    }
    room.roomUuid = [NSString stringWithFormat:@"%@%d",self.lessonIdView.text,room.configId];
    __weak typeof(self)weakSelf = self;
    [[EduManager shared].roomService createRoom:room completion:^(NEEduCreateRoomRequest *result,NSError * _Nonnull error) {
       // 创建结果处理
    }];
```

### 步骤6:加入房间

```objc
NEEduEnterRoomParam *room = [[NEEduEnterRoomParam alloc] init];
    room.autoPublish = YES;
    room.autoSubscribeVideo = YES;
    room.autoSubscribeAudio = YES;
    room.roomUuid = resRoom.roomUuid;
    room.roomName = resRoom.roomName;
    room.sceneType = self.lessonType;
    room.role = NEEduRoleTypeStudent;
    room.userName = @"";
    __weak typeof(self)weakSelf = self;
    [[EduManager shared] enterClassroom:room success:^(NEEduRoomProfile * _Nonnull roomProfile) {
        // 进入成功处理
    } failure:^(NSError * _Nonnull error) {
        // 进入失败处理
}
```

## 示例项目结构：

| 文件夹/文件               | 说明              |
| ------------------------- | ----------------- |
| WisdomEducation/keyCenter | 配置AppKey        |
| WisdomEducation/Enter     | 教师\学生登录页面 |
| EduLogic                  | 课堂页面逻辑模块  |
| EduUI                     | 课堂页面UI模块    |
| NEWhiteBoard              | 白板功能模块      |

## 自定义课堂UI

如果 示例代码 中默认实现的 UI 不符合您的预期，您可以按需实现自己的用户界面，即只使用我们封装好的组件所提供的音视频能力，自行实现 UI 部分。

教育组件功能模块：  
<image width="70%" src="../../Images/ios_layer.png">

**EduUI：**包含教育组件的UI的实现，包括一对一教学、多人小班课、互动大班课、直播大班课等场景的ViewController、View以及model部分。

**EduLogic：**是依赖云信的音视频SDK、IMSDK以及白板SDK对于教育逻辑的实现，分别对应NEEduRtcService、NEEduIMService、NEWhiteBoard。

**NEEduRtcService：**是音视频服务，提供可供 App 调用的音视频相关方法。

**NEEduIMService：**是IM服务，提供可供 App 调用的即时通信、聊天室相关方法。

**NEWhiteBoard：**是互动白板服务，提供可供 App 调用的互动白板相关方法。