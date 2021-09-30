# 快速开始_iOS

互动直播示例代码提供普通的单主播直播已经PK直播和连麦直播

## 前提条件

在开始运行示例项目之前，请确保您已完成以下操作：  
联系云信商务获取开通以下权限，并联系技术支持配置产品服务和功能

* 在云信控制台创建应用，并获取对应的 AppKey。[云信控制平台](../../云信控制平台/应用创建和服务开通.md)
* 为此应用开通以下相关服务与抄送：
* [应用创建和服务开通](../应用创建和服务开通.md)
* 云信控制台配置
云信控制台配置参考[服务配置](../服务配置.md)
* 如需使用美颜功能请联系相芯获取美颜证书 [相芯](https://www.faceunity.com/)

## 开发环境

在开始运行示例项目之前，请确保开发环境满足以下要求：

| 环境要求 | 说明                                                     |
| -------- | -------------------------------------------------------- |
| iOS 版本 | 10.0以上                                                 |
| CPU 架构 | ARM64、ARMV7                                             |
| IDE      | XCode                                                    |
| 其他     | 安装 CocoaPods，准备一台 iOS 9.0 及以上版本的 iOS 设备。 |

## 示例项目结构  
|  目录   | 说明  |
|  ----  | ----  |
| NLiteAVDemo  | 应用主入口包含外部页面框架。 |
| Live | Pk功能实现 |
| Live-Anchor  | 主播业务实现 |
| Live-Audience  | 观众的业务实现 |
| Live-BaseVC  | 控制器基类 |
| beauty  | 美颜lib，基于相芯 |
| Utils-NETSRequest | 网络请求相关 |
| Live-PkLiveBusiness | 直播房间服务 |
| Live-Anchorlib-Controller-anchorMainVc-NEPkLiveViewController | Pk功能主控制器 |

## 运行示例源码 
1. GitHub下载源代码 [源码](https://github.com/netease-kit/OnlinePK/tree/dev_2.0.0/OnlinePK-iOS)
2. cd到已下载的项目路径下，执行podinstall。
3. 找到AppKey.h 文件替换自己的appkey，配置对应的后台地址。
  * 此处的appkey请联系技术支持同学确认后台开通相关功能
4. 如果需要使用美颜功能请使用自己的相芯证书替换authpack.h的内容
5. 在xcode中执行run操作。

