# 跑通Windows示例项目


网易智慧企业 在 GitHub 上提供一个开源的网易会议组件示例项目 [NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Windows_macOS)。本文介绍如何快速跑通该示例项目，体验 在线会议功能。示例代码中包含了详细的API调用场景、参数封装以及回调处理。 你也可以直接体验我们的线上[网易会议](https://meeting.163.com/)产品。

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
  - [网易会议账户](##示例项目会议账号)

## 开发环境

在开始运行示例项目之前，请确保开发环境满足以下要求：

| 环境要求 | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| 操作系统   | Windows 7 或更高版本 |
| IDE | 基于 Qt 5.6 或更高版本的 Qt Creator  |
|  其他     |有效的开发者签名、设备权限设置   |


## 操作步骤

此项目使用 Qt5 C++ 项目构建，可同时运行在 Windows/macOS 系统中，示例代码包含了接口的调用方式及时机、参数指定、回调处理等。

1. 创建云信项目和获取 APPKEY

   - 参考文档 [应用创建和服务开通](../../../云信控制平台/应用创建和服务开通.md)
2. 配置示例项目
   考以下步骤配置示例项目：
   1. 从GitHub中下载源码。[源码地址](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Windows_macOS)
   2. 在NEMeeting/SampleCode/Windows_macOS/目录下，使用 Qt creator 打开 NEMeeting-SDK-Sample.pro文件。
   3. 点击 Projects，在 Build&Run 下选择构建工具。例如 x86-windows-msvc0217-pe-64bit 或 x86-windows-msvc0217-pe-32bit。
   4. 选择 Release 作为构建选项，点击构建按钮进行构建。
   5. 构建完成后，点击运行按钮运行项目。 在界面上输入AppKey、账号ID和密码，点击 LOGIN 即可进入会议主页。
   	<img src="../images/macos_windows_demo.png"/>

## 示例项目账号

 - 账号及密码：使用网易会议 PaaS 服务提供的创建会议账号接口完成创建，并通过对应的账号 ID 和 Token 来完成登录鉴权。

此示例中，以上所需参数均通过 UI 方式提供开发者指定来体验完整会议功能。



