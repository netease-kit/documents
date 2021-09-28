# 跑通Android示例项目

网易智慧企业 在 GitHub 上提供一个开源的呼叫组件示例项目 [VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-Android-Java)。本文介绍如何快速跑通该示例项目，体验呼叫音视频通话功能。

##  前提条件
  - 开发环境要求：
    - Java Development Kit
  - Android Studio 4.0 及以上
  - 如果你的网络环境部署了防火墙，请根据应用企业防火墙限制打开相关端口。
  - 一台真实的 Android 设备。部分模拟机可能存在功能缺失或者性能问题，所以推荐使用真机。
  - 有效的 网易云信 开发者账号。

## 操作步骤
  1. 创建云信项目和获取 APPKEY。
       - 参考文档 [快速集成#Android#准备工作](../开发文档/快速集成/Android.md)
  2.  配置示例项目参考以下步骤配置示例项目：

   - 克隆[VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-Android-Java )仓库至本地。

   - 将自己的 appkey 告知 so 人员添加对应 demo 的体验权限；

   - 找到 app 下的 build.gradle 文件替换自己的 appkey；

     ```groovy
     
     // app key for code
     defaultConfig {
         buildConfigField "String", "APP_KEY", "your self app key!!!"
     }
     ```
  3. 编译并运行示例项目。
