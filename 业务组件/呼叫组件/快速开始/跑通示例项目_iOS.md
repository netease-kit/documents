# 跑通iOS示例项目

网易智慧企业 在 GitHub 上提供一个开源的呼叫组件示例项目 [VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-iOS-ObjC)。本文介绍如何快速跑通该示例项目，体验呼叫音视频通话功能。

##  前提条件
  - Xcode 10 及以上版本
  - iOS 9.0 及以上版本的 iOS 设备。
  - iOS 开发者账号，能够生成项目证书。
  - iOS真机，内部引用的音视频SDK无法在模拟器上运行。
  - 有效的 网易云信 开发者账号。

## 操作步骤
  1. 创建云信项目和获取 APPKEY。
       - 参考文档 [快速集成#集成到项目_iOS#准备工作](../开发文档/快速集成/集成到项目_iOS.md)
  2.  配置示例项目参考以下步骤配置示例项目：

   - 克隆[VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-iOS-ObjC)仓库至本地。

   - 将自己的 appkey 告知 so 人员添加对应 demo 的体验权限；

   - 修改项目中的 bundleid，bundleid 为 云信后台申请appkey所填写bundleid；

   - 找到工程中AppKey.h文件替换自己的 kAppKey；

     ```objc

     // app key for code
     static NSString * const kAppKey = @"your self app key!!!";

     ```
  3. 编译并运行示例项目。
