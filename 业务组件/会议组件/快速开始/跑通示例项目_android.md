# 跑通Android示例项目

网易智慧企业 在 GitHub 上提供一个开源的网易会议组件示例项目 [NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Android)。本文介绍如何快速跑通该示例项目，体验 在线会议功能。示例代码中包含了详细的API调用场景、参数封装以及回调处理。 你也可以直接体验我们的线上[网易会议](https://meeting.163.com/)产品。

示例项目包含的功能如下：

- 通过账号、密码完成会议SDK登录鉴权；注销登录
- 创建会议、加入会议
- 会议内提供的其他功能(如会议控制、屏幕共享等)

### 运行示例程序

开发者根据个人需求，补充完成示例项目后，即可运行并体验会议功能。

|   功能   | 网易会议AppKey | 网易会议账号 |
| :------: | :------------: | :----------: |
| 加入会议 |      需要      |    不需要    |
| 创建会议 |      需要      |     需要     |

##  前提条件

在开始运行示例项目之前，请确保您已完成以下操作：
  - [云信开发者账号](https://id.163yun.com/register?h=media&t=media&from=nim&clueFrom=nim)
  - 在云信控制台创建应用，并获取对应的 AppKey  [参考文档](../../../云信控制平台/应用创建和服务开通.md)。
  - 为此应用开通音视频通话 2.0、IM 专业版相关服务
  - [网易会议账号](##示例项目会议账号)

## 开发环境

在开始运行示例项目之前，请确保开发环境满足以下要求：

| 环境要求         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| JDK 版本         | 1.8.0 及以上版本                                             |
| Android API 版本 | API 23、Android 6.0 及以上版本                               |
| CPU架构          | ARM64、ARMV7                                                 |
| IDE              | Android Studio                                               |
| 其他             | 依赖 Androidx，不支持 support 库。Android 系统 4.3 或以上版本的移动设备。 |

## 操作步骤
  1. 创建云信项目和获取 APPKEY
       - 参考文档 [应用创建和服务开通](../../../云信控制平台/应用创建和服务开通.md)

  2. 配置示例项目
       考以下步骤配置示例项目：
        - 克隆[NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Android) 仓库至本地.
        - 找到`NEMeeting/SampleCode/Android` 示例项目文件夹，在 `app/src/main/res/values/strings.xml` 文件中填写你从云信控制台获取的AppKey
          ```
          <?xml version="1.0" encoding="utf-8"?>
                <resources>
                 <!--TODO-->
                 <!--Replace With Your AppKey Here-->
                 <string name="appkey">Your AppKey</string>
          </resources>
          ```
        - AppKey的申请和声明后，运行示例项目可体验“加入会议”功能，但无法使用“登录”、“创建会议”功能。

  3. 集成SDK说明
	在app/build.gradle文件中已经添加了网易会议SDK和NIM-SDK依赖
	  ```
	  //NEMeeting-SDK
	  implementation 'com.netease.yunxin:meetinglib:2.0.0'
	  //NIM-SDK, 如果单独接入了云信NIM，或者需要依赖指定的NIM版本，则可根据需要进行依赖声明。
	  //正常情况下不用声明
	  implementation 'com.netease.nimlib:basesdk:8.4.6'
	  ```

  4. 编译并运行示例项目

       连接上 Android 设备后，用 Android Studio 打开 `NEMeeting/SampleCode/Android`  示例项目，然后编译并运行示例项目。运行效果如下图所示
      <image width="30%" src="../images/demo_meeting_main_page.png">

## 示例项目登录账号

为了体验示例项目中的创建会议功能，在以上完善应用Appkey的基础上，还需要使用会议账号完成SDK的登录鉴权。

会议SDK的登录鉴权需要提供一个有效的会议账号(可使用网易会议PaaS服务提供的创建会议账号接口完成创建)，并通过对应的账号ID和TOKEN来完成登录鉴权。

在此示例项目中，开发者需要实现`com.netease.meetinglib.demo.data.DefaultDataRepository#getSDKAuthInfo`方法，返回当前对应AppKey下一个真实有效的会议账号的ID和TOKEN，它们会被用来作为参数完成会议SDK中 login 接口的调用。

完成会议账号的登录实现后，运行示例项目，可体验完整的会议功能。

