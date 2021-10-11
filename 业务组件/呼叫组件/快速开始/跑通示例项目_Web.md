# 跑通Web示例项目

网易智慧企业 在 GitHub 上提供一个即使通讯示例项目 [NIM_Web_Demo](https://github.com/netease-kit/NIM_Web_Demo)，该项目使用了呼叫组件实现音视频呼叫和通话。本文介绍如何快速跑通该示例项目，体验呼叫音视频通话功能。

##  前提条件
在开始运行示例项目之前，请确保您已完成以下操作：
联系云信商务获取开通以下权限，并联系技术支持配置产品服务和功能

  -  完成[应用创建和服务开通](../应用创建和服务开通.md)；
  - 获取新建应用的 AppKey；

## 开发环境
| 环境要求         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| node 版本         | 12.0.0 及以上版本                                             |


## 操作步骤
1. 将项目clone到本地
2. 找到`webdemo/imNew/js/config.js`，填入您自己申请的应用appkey
```js
var configMap = {
  dev: {
    appkey: '', // 填入您申请的appkey
    url: 'https://apptest.netease.im',
    chatroomList: 'https://apptest.netease.im/api/chatroom/homeList',
    chatroomAddr: 'https://apptest.netease.im/api/chatroom/requestAddress',
    authService: 'https://yiyong-qa.netease.im',
  },
  test: {
    appkey: '', // 填入您申请的appkey
    url: 'https://apptest.netease.im',
    chatroomList: 'https://apptest.netease.im/api/chatroom/homeList',
    chatroomAddr: 'https://apptest.netease.im/api/chatroom/requestAddress',
    authService: 'https://yiyong-qa.netease.im',
  },
  pre: {
    appkey: '', // 填入您申请的appkey
    url: 'https://apptest.netease.im',
    chatroomList: 'https://apptest.netease.im/api/chatroom/homeList',
    chatroomAddr: 'https://apptest.netease.im/api/chatroom/requestAddress',
    authService: 'https://yiyong.netease.im',
  },
  online: {
    appkey: '', // 填入您申请的appkey
    url: 'https://app.netease.im',
    chatroomList: 'https://app.netease.im/api/chatroom/homeList',
    chatroomAddr: 'https://app.netease.im/api/chatroom/requestAddress',
    authService: 'https://yiyong-qa.netease.im',
  }
}
```
3. 进入demo，命令行输入`node app`
4. 在浏览器中访问 http://127.0.0.1:8182/webdemo/imNew/login.html
[image-20211011-173310.png](../images/image-20211011-173310.png)

