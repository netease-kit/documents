# 跑通iOS示例项目


网易智慧企业 在 GitHub 上提供一个开源的网易会议组件示例项目 [NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/iOS)。本文介绍如何快速跑通该示例项目，体验 在线会议功能。示例代码中包含了详细的API调用场景、参数封装以及回调处理。 你也可以直接体验我们的线上[网易会议](https://meeting.163.com/)产品。

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
1. 创建应用并开通所需功能以及服务 [参考文档](../应用创建和服务开通.md)。
2. 在官网首页通过 QQ、在线消息或电话等方式联系商务经理，申请会议组件 App Key

## 开发环境

在开始运行示例项目之前，请确保开发环境满足以下要求：

| 环境要求 | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| XCode    | 11.6 +                                                       |
| 调试设备 | iOS 8.0 或以上版本的设备                                     |
|  其他     |有效的开发者签名、设备权限设置   |

## 操作步骤

1. 创建云信项目和获取 APPKEY

   - 参考文档 [应用创建和服务开通](../../../云信控制平台/应用创建和服务开通.md)

2. 配置示例项目
   考以下步骤配置示例项目：

     - 克隆[NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/iOS) 仓库至本地.
     - 进入`NEMeetingDemoSampleCode/NEMeetingDemo`路径，执行
       ```
       pod install
       ```
     - Appkey是应用接入会议SDK的凭证，开发者首先需要在网易会议开发者平台完成申请，并将其填写至`NEMeetingDemo/Config.m`资源文件中的对应资源项上。
      ```xml
         NSString *const kAppKey = 请填入您的AppKey;
      ```

3. 编译并运行示例项目
     连接上 iOS 设备后，用 Xcode 打开示例项目，然后编译并运行项目。

     <image width="25%" src="../images/demo_meeting_main_page.png">


## 示例项目会议账号

为了体验示例项目中的创建会议功能，在以上完善应用Appkey的基础上，还需要使用会议账号完成SDK的登录鉴权。

会议SDK的登录鉴权需要提供一个有效的会议账号(可使用网易会议PaaS服务提供的创建会议账号接口完成创建)，并通过对应的账号ID和TOKEN来完成登录鉴权。

在此示例项目中，开发者需要在`NEMeetingDemo/Config.m`实现一以下方法

```objective-c
+ (void)queryAccountInfoWithUserName:(NSString *)userName
                            password:(NSString *)password
                          completion:(QueryAccoutInfoBlock)completion {
    <#请根据自己情况获得真正的用户id和token,并调用completion返回#>
}
```

返回当前对应AppKey下一个真实有效的会议账号的ID和TOKEN，然后调用completion回调出去，它们会被用来作为参数完成会议SDK中 login 接口的调用。

完成会议账号的登录实现后，运行示例项目，可体验完整的会议功能。
