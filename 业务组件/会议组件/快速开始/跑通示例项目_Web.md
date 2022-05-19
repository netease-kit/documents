
网易云信在 GitHub 上提供一个开源的网易会议组件示例项目 [NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/Web)。本文介绍如何快速跑通该示例项目，体验在线会议功能。示例代码中包含了详细的 API 调用场景、参数封装以及回调处理。

该示例项目包含的功能如下：

- 通过账号、密码完成 NEMeeting 登录鉴权、注销登录。
- 创建会议、加入会议。
- 会议内提供的其他功能 (如会议控制、屏幕共享等)。

##  前提条件

在开始运行示例项目之前，请确保您已完成以下操作：

1. 已创建 NERoom 应用并开通相关能力。

2. 在网易云信控制台的 NERoom 列表中获取指定应用的 App Key。

3. 通过网易会议服务端的[创建会议账号接口](/docs/TE5OTgwODE/DU3NTczMTg)创建一个有效的会议账号，获取 accountId 和 accountToken，用于完成登录鉴权。

## 开发环境

在开始运行示例项目之前，请您准备以下开发环境：

| 名称         | 具体要求                                                         |
| ---------------- | ------------------------------------------------------------ |
| chrome         | 74及以上                                         |
| Safari | 12及以上                              |
| Node | 8及以上                              |
| 其他             | 待验证 |

## 操作步骤

1. 配置示例项目。具体步骤如下。

    1. 克隆 [NEMeeting](https://github.com/netease-kit/NEMeeting/tree/main/SampleCode/Web) 仓库至本地。

    2. 进入项目SampleCode/Web目录，运行以下命令进行依赖安装。
    ```
    npm install
    ```

    3. 依赖安装完成后，编辑index.html页面，在init方法中写入对应 APP Key，在login方法中写入对应accountId 和 accountToken。

2. 在项目根目录运行以下命令，启动静态资源服务。
    ```
    node app.js
    ```
3. 服务启动后在浏览器窗口地址输入：http://localhost:3001，依次点击初始化 > 登录 > 创建按钮即可进入会议主页。
   效果图如下所示：

<img style="width:30%" src="https://yx-web-nosdn.netease.im/common/489344aaf58e1303c9a3f57d1b78022b/demo.jpg" alt="image" />
