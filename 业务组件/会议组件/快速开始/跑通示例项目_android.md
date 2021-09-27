# 跑通Android示例项目

  网易智慧企业 在 GitHub 上提供一个开源的网易会议组件示例项目 [NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Android)。本文介绍如何快速跑通该示例项目，体验 在线会议功能。你也可以直接体验我们的线上[网易会议](https://meeting.163.com/)产品。

##  前提条件
  - 开发环境要求：
    - Java Development Kit
  - Android Studio 3.0 及以上
  - 如果你的网络环境部署了防火墙，请根据应用企业防火墙限制打开相关端口。
  - 一台真实的 Android 设备。部分模拟机可能存在功能缺失或者性能问题，所以推荐使用真机。
  - 有效的 网易云信 开发者账号。

## 操作步骤
  1. 创建云信项目和获取 APPKEY
       - 参考文档 [应用创建和服务开通](../../../云信控制平台/应用创建和服务开通.md)
  2. 配置示例项目
    考以下步骤配置示例项目：
   - 克隆[NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Android) 仓库至本地。
  ```
  <?xml version="1.0" encoding="utf-8"?>
       <resources>
         <!--TODO-->
         <!--Replace With Your AppKey Here-->
         <string name="appkey">Your AppKey</string>
       </resources>
  ```
   - 找到NEMeeting/SampleCode/Android 示例项目文件夹，在 app/src/main/res/values/strings.xml 文件中填写你从云信控制台获取的AppKey
  AppKey的申请和声明后，运行示例项目可体验“加入会议”功能，但无法使用“登录”、“创建会议”功能。

  3. 集成SDK说明
	

	
  4. 编译并运行示例项目
