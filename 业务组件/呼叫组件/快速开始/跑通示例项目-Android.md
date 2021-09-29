# 跑通Android示例项目

网易智慧企业 在 GitHub 上提供一个开源的呼叫组件示例项目 [VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-Android-Java)。本文介绍如何快速跑通该示例项目，体验呼叫音视频通话功能。

##  前提条件
在开始运行示例项目之前，请确保您已完成以下操作：

  - [云信开发者账号](https://id.163yun.com/register?h=media&t=media&from=nim&clueFrom=nim)；
  - 在云信控制台创建应用，并获取对应的 AppKey  [参考文档](../../../云信控制平台/应用创建和服务开通.md)；
  - 为此应用开通音视频通话 2.0、IM 专业版、信令相关服务；
  - 将对应的 AppKey 通过官网通知技术支持为此 AppKey 配置对应 demo 体验权限。

## 开发环境

| 环境要求         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| JDK 版本         | 1.8.0 及以上版本                                             |
| Android API 版本 | API 23、Android 6.0 及以上版本                               |
| CPU架构          | ARM64、ARMV7                                                 |
| IDE              | Android Studio                                               |
| 其他             | 依赖 Androidx，不支持 support 库。Android 系统 4.3 或以上版本的移动设备。 |

## 操作步骤

  2.  配置示例项目参考以下步骤配置示例项目：

   - 克隆[VideoCall](https://github.com/netease-kit/NEVideoCall-1to1/tree/master/NLiteAVDemo-Android-Java )仓库至本地。

   - 找到 app 下的 build.gradle 文件替换自己的 AppKey；

     ```groovy
     def appKey = "Here, please fill your appKey!!!"
     // app key for code
     defaultConfig {
     		buildConfigField "String", "APP_KEY", "\"${appKey}\""
     }
     ```
  3. 编译并运行示例项目。

<img src="../images/image-20210927094610691.png" width="30%"/>
