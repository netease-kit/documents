# 跑通Android示例项目

  网易智慧企业 在 GitHub 上提供一个开源的云视频客服组件示例项目 [NEVCE]。本文介绍如何快速跑通该示例项目，体验远程视频面签等功能。

示例项目包含的功能如下：

- 通过账号、密码完成VCE SDK登录鉴权；注销登录
- 客户呼叫排队
- 坐席签入、签出，办理业务

### 运行示例程序

开发者根据个人需求，补充完成示例项目后，即可运行并体验云视频客服功能。

| 功能 | 云视频客服AppKey | 云视频客服账号 |
| :--: | :--------------: | :------------: |
| 客户 |       需要       |     不需要     |
| 坐席 |       需要       |      需要      |

##  前提条件

在开始运行示例项目之前，请确保您已完成以下操作：
1. 创建应用并开通所需功能以及服务 [参考文档](../应用创建和服务开通.md)。
2. 在官网首页通过 QQ、在线消息或电话等方式联系商务经理，申请云视频客服组件 App Key

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

      - 克隆[NEVCE](https://github.com/netease-kit/NEVCE/tree/main/SampleCode/Android) 仓库至本地.

      - 找到`NEVCE/SampleCode/Android` 示例项目文件夹，在 `app/src/main/res/values/strings.xml` 文件中填写你从云信控制台获取的AppKey

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
     在app/build.gradle文件中已经添加了VCESDK和NIM-SDK依赖

       ```
     //NEVCE SDK
     implementation 'com.netease.yunxin:vcelib:1.0.5'
       ```

  4. 编译并运行示例项目

     连接上 Android 设备后，用 Android Studio 打开 `NEVCE/SampleCode/Android`  示例项目，然后编译并运行示例项目。运行效果如下图所示

     <image width="25%" src="../images/demo_vce_main_page.png">

## 示例项目登录账号

为了体验示例项目中的功能，在以上完善应用Appkey的基础上，还需要使用云视频客服账号完成SDK的登录鉴权。

VCESDK的登录鉴权需要提供一个有效的云视频客服账号(可使用网易云视频客服PaaS服务提供的创建账号接口完成创建)，并通过对应的账号ID和TOKEN来完成登录鉴权。

在此示例项目中，开发者需要实现`com.netease.vcelib.demo.data.DefaultDataRepository#getSDKAuthInfo`方法，返回当前对应AppKey下一个真实有效的账号的ID和TOKEN，它们会被用来作为参数完成SDK中 login 接口的调用。

完成账号的登录实现后，运行示例项目，可体验完整的云视频客服功能。

