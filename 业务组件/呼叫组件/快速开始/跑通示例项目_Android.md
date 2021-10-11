# 跑通Android示例项目

网易智慧企业 在 GitHub 上提供一个开源的呼叫组件示例项目 [VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-Android-Java)。本文介绍如何快速跑通该示例项目，体验呼叫音视频通话功能。

##  前提条件
在开始运行示例项目之前，请确保您已完成以下操作：
联系云信商务获取开通以下权限，并联系技术支持配置产品服务和功能

  -  完成[应用创建和服务开通](../应用创建和服务开通.md)；
  - 获取新建应用的 AppKey；

## 开发环境

| 环境要求         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| JDK 版本         | 1.8.0 及以上版本                                             |
| Android API 版本 | API 23、Android 6.0 及以上版本                               |
| CPU架构          | ARM64、ARMV7                                                 |
| IDE              | Android Studio                                               |
| 其他             | 依赖 Androidx，不支持 support 库。Android 系统 4.3 或以上版本的移动设备。 |

## 操作步骤

  1.  配置示例项目参考以下步骤配置示例项目：

   - 克隆[VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-Android-Java )仓库至本地。

   - 找到 app 下的 build.gradle 文件替换自己的 AppKey；

     ```groovy
     def appKey = "Here, please fill your appKey!!!"
     // app key for code
     defaultConfig {
     		buildConfigField "String", "APP_KEY", "\"${appKey}\""
     }
     ```
  2. 编译并运行示例项目。

<img src="../images/image-20210927094610691.png" width="30%"/>
