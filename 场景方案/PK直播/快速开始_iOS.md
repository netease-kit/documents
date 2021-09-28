# 快速开始_iOS

## 开发环境
在开始运行示例项目之前，请确保开发环境满足以下要求：
* iOS 10系统及以上。

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
3. 找到AppKey.h 文件替换自己的appkey,配置对应的后台地址。
  * 此处的appkey请联系技术支持同学确认后台开通相关功能
4. 如果需要使用美颜功能请使用自己的相芯证书替换authpack.h的内容
5. 在xcode中执行run操作。

