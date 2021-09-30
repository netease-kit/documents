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