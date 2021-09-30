# 跑通示例项目
本工程是网易云信提供的基础多人音视频通话场景解决方案

##  前提条件

在开始运行示例项目之前，请确保您已完成以下操作：
1. 创建应用并开通所需功能以及服务 [参考文档](应用创建和服务开通.md)。
2. 在官网首页通过 QQ、在线消息或电话等方式联系商务经理，申请 App Key
3. 如需使用美颜功能请联系[相芯](https://www.faceunity.com/)获取美颜证书,如果不需要美颜功能，可参考[本文](../进阶功能/../开发文档/进阶功能/美颜_Android.md)删除美颜功能 

## 开发环境 
在开始运行示例项目之前，请确保开发环境满足以下要求：

| 环境要求         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| JDK 版本         | 1.8.0 及以上版本                                             |
| Android API 版本 | API 23、Android 6.0 及以上版本                               |
| CPU架构          | ARM64、ARMV7                                                 |
| IDE              | Android Studio                                               |
| 其他             | 依赖 Androidx，不支持 support 库。Android 系统 4.3 或以上版本的移动设备。 |

## 运行示例项目
1. GitHub下载源代码 [源码](https://github.com/netease-kit/NEGroupCall/tree/master/Android)
2. 导入Android Studio
3. 找到config文件夹下的test.properties文件和online.properties文件替换自己的APP_KEY，请[联系云信商务经理](https://yunxin.163.com/bizQQWPA.html)开通音视频功能
   
    ```
    test.properties文件
    APP_KEY="请输入你的app key"
    BASE_URL=http://yiyong-qa.netease.im/

    online.properties文件
    APP_KEY="请输入你的app key"
    BASE_URL=https://yiyong.netease.im/

    ```
  
4. 如果需要使用美颜功能请使用自己的相芯证书替换lib-beauty-faceunity/src/main/java/com/beautyFaceunity/authpack.java 证书文件
  
    ```
    package com.faceunity;

    import java.security.MessageDigest;

    public class authpack {

    }
    ```

5. 运行在自己的Android设备上
  
    <image width=30% src="../images/首页.png">