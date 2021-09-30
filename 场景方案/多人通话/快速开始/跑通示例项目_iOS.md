# 跑通示例项目
本工程是网易云信提供的基础多人音视频通话场景解决方案

##  前提条件

在开始运行示例项目之前，请确保您已完成以下操作：
1. 创建应用并开通所需功能以及服务 [参考文档](应用创建和服务开通.md)。
2. 在官网首页通过 QQ、在线消息或电话等方式联系商务经理，申请 App Key
3. 如需使用美颜功能请联系[相芯](https://www.faceunity.com/)获取美颜证书,如果不需要美颜功能，可参考[本文](../进阶功能/../开发文档/进阶功能/美颜_iOS.md)删除美颜功能 

## 开发环境 
在开始运行示例项目之前，请确保开发环境满足以下要求：
* Xcode 11 及以上版本
* iOS 10.0 及以上版本的 iOS 设备


## 示例项目结构
|  目录   | 说明  |
|  ----  | ----  |
| NEGroupCall-iOS  | 应用主入口包含外部页面框架。 |
| NEGroupCall-iOS-Class | 项目主要功能逻辑，包含用户登录、注册、多人通话等 |
| NEGroupCall-iOS-Macro  | 宏定义|
| NEGroupCall-iOS-Class-Service  | 网络请求相关 |
| NEGroupCall-iOS-Base  | 基础类 |
| NEGroupCall-iOS-Support  | 分类和工具集 |
| NEGroupCall-iOS-Class-beauty  | 美颜lib，基于相芯 |

## 运行示例源码
1. GitHub下载源代码 [源码](https://github.com/netease-kit/NEGroupCall/tree/master/iOS)
2. 下载后，cd到NEGroupCall-master/iOS目录下，执行pod install。若不是最新版本，可通过命令pod repo update 更新本地版本。
3. 打开项目中AppKey.h文件，替换其中的kAppKey，kNertcAppkey，kApiHost。
* 此处所需替换的信息请联系技术支持同学，确认后台开通相关功能
4. 如果需要使用美颜功能请使用自己的相芯证书替换authpack.h证书文件
5. 运行在自己的iOS设备上