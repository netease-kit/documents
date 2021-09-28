# RoomKit组件

RoomKit SDK提供了一套简单易用的接口，允许开发者通过调用NEMeeting SDK(以下简称SDK)提供的API，快速地集成音视频会议功能至现有 Flutter应用中。

## 准备工作

将此包用作库依赖它
运行此命令（需要安装 flutter）：
```
 flutter pub add yunxin_room_kit
```
这将在您的包的 pubspec.yaml 中添加这样的一行（并运行一个隐式flutter pub get）：
```
dependencies:
  yunxin_room_kit: ^0.1.1-rc.0
```

或者，您的编辑器可能支持flutter pub get。

导入它
现在在您的 Dart 代码中，您可以使用：

```
import 'package:yunxin_room_kit/room_kit.dart';
import 'package:yunxin_room_kit/room_service.dart';
```