## CHANGELOG

# v3.4.0(Aug 31, 2022)

### New Features
* 支持聊天室图片、文件消息
* 创建/加入会议新增聊天室配置`NEMeetingOptions#chatroomConfig`
* 预约会议增加sip`NEMeetingItem#noSip`

### Behavior changes

### API Changes

### Bug Fixes
* 解决偶现加入会议卡死问题
* 解决断网重连加入会议，聊天室无法发送/接收消息问题
* 解决参会者举手，主持人共享时悬浮窗收起再展开，举手图标消失问题
* 解决创会后获取meetingStatus应该是4，偶现meetingStatus值为3问题

### Dependency Updates

### Compatibility
* Compatible with `NERTC` version `4.6.13`.
* Compatible with `NIM` version `9.1.3.1218`.
* Compatible with `WHITEBOARD` version `3.7.2`.

### Known issues

# v3.3.0(Jul 28, 2022)

### New Features

* 支持说话者提示
* 支持会议剩余时间提示: `NEMeetingOptions#showMeetingRemainingTip`
* 支持查询当前会议创建者id: `NEMeetingInfo#meetingCreatorId`
* 支持查询当前会议创建者名称: `NEMeetingInfo#meetingCreatorName`

### Behavior changes

* roomkit升级至1.5.0
* 优化音频设备默认选项

### Bug Fixes

* 解决移动端取消锁定会议，桌面端无反应问题
* 解决windows断网状态下开启音频共享，恢复网络后音频共享成功的问题
* 解决白板共享者退出房间，本端未关闭白板问题
* 解决焦点视频者退出房间，本端未退出焦点模式问题
* 解决偶现全屏状态点击离开，会议主窗口没有关闭问题
* 解决主持人操作全体打开视频，屏幕共享者的视频直接开启的问题
* 解决主持人取消联席主持人，参会者自动取消举手问题
* 解决windows共享屏幕的时候同步共享本地音频，部分设备静音后讲话其他端仍然可以听到的问题
* 解决主持人的主画面左上角无取消焦点视频按钮的问题


# v3.2.0(Jun 30, 2022)

### New Features

* 支持设置虚拟背景开关: `NEVirtualBackgroundController#enableVirtualBackground`
* 支持查询虚拟背景开关: `NEVirtualBackgroundController#isVirtualBackgroundEnabled`
* 支持设置内置虚拟背景列表: `NEVirtualBackgroundController#setBuiltinVirtualBackgrounds`
* 支持查询内置虚拟背景列表: `NEVirtualBackgroundController#getBuiltinVirtualBackgrounds`
* 支持创建会议,指定成员角色 `NEStartMeetingParams#roleBinds`
* 支持预约会议/编辑预约会议,指定成员角色 `NEMeetingItem#roleBinds`
* 支持联席主持人

### Behavior changes

* G2升级至4.6.13
* 优化成员列表展示顺序

### Bug Fixes

* 修复分享窗口时的一些缺陷

# v2.3.0(Dec 28, 2021)

### New Features

* 增加主持人视频会控功能
* 设置界面支持是否自动调节麦克风音量
* 设置界面支持设置通话音质
* 设置界面支持设置分辨率
* 增加主持人视频会控功能
* 创建会议/加入会议增加是否显示tag字段：`NEMeetingOptions#showMemberTag`
* 创建会议增加拓展字段：`NEStartMeetingParams#extraData`
* 预约会议/编辑会议增加拓展字段：`NEMeetingItem#extraData`
* 获取当前会议信息增加拓展字段：`NEMeetingInfo#extraData`
* 创建会议增加会议控制配置字段：`NEStartMeetingParams#controls`
* 预约/编辑会议增加会议控制配置字段：`NEMeetingItem#NEMeetingItemSetting#controls`
* 设置服务增加设置自动调节的开关接口：`NEAudioController::setMyAudioVolumeAutoAdjust`
* 设置服务增加获取自动调节的开关接口：`NEAudioController::isMyAudioVolumeAutoAdjust`
* 设置服务增加自动调节状态变更通知接口：`NESettingsChangeNotifyHandler::OnAudioVolumeAutoAdjustSettingsChange`
* 增加设置通话音质的接口：`NEAudioController::setMyAudioQuality`
* 增加获取通话音质接口：`NEAudioController::getMyAudioQuality`
* 增加通话音质变更通知接口：`NESettingsChangeNotifyHandler::OnAudioQualitySettingsChange`
* 增加设置回声消除的开关接口：`NEAudioController::setMyAudioEchoCancellation`
* 增加获取回声消除的开关接口：`NEAudioController::isMyAudioEchoCancellation`
* 增加回声消除状态变更通知接口：`NESettingsChangeNotifyHandler::OnAudioEchoCancellationSettingsChange`
* 增加设置启用立体音的开关接口：`NEAudioController::setMyAudioEnableStereo`
* 增加获取启用立体音的开关接口：`NEAudioController::isMyAudioEnableStereo`
* 增加启用立体音状态变更通知接口：`NESettingsChangeNotifyHandler::OnAudioEnableStereoSettingsChange`
* 增加设置远端分辨率的接口：`NEVideoController::setRemoteVideoResolution`
* 增加获取远端分辨率的接口：`NEVideoController::getRemoteVideoResolution`
* 增加远端分辨率变更通知接口：`NESettingsChangeNotifyHandler::OnRemoteVideoResolutionSettingsChange`
* 增加设置本地分辨率的接口：`NEVideoController::setMyVideoResolution`
* 增加获取本地分辨率的接口：`NEVideoController::getMyVideoResolution`
* 增加本地分辨率变更通知接口：`NESettingsChangeNotifyHandler::OnMyVideoResolutionSettingsChange`

### Behavior changes

* G2升级至4.4.8
* IM升级至8.9.1

### Bug Fixes

* 修复主持人断网，参会者加入会议，主持人联网，主持人看到参会者不在会议中问题
* 修复参会者举手，主持人断网重新入会，管理参会者上方无举手图标问题
* 修复windows下PowerPoint共享ppt时，出现画面闪烁问题

# v2.2.0(Dec 9, 2021)

### New Features

* 会议组件增加G2私有化支持
* 会议组件增加SIP开关`NEMeetingOptions::bNoSip`
* 会议组件增加AI降噪开关`NEMeetingOptions::bAudioAINSEnabled`

### Behavior changes

### Bug Fixes

* 修复组件IM私有化无效问题
* 修复windows下PPT幻灯片播放，出现共享者ppt小窗没有到底层，遮挡住ppt放映画面问题
* 修复加入会议时未开启白板，但会议中有共享白板功能的问题

# v2.0.6(Sept 28, 2021)

### New Features

* 增加会中音量检测

### Behavior changes

* G2 SDK升级至4.4.2

### Bug Fixes

* 修复mac下首次安装，音视频无法打开问题
* 修复离开会议，设置美颜不生效问题
* 修复离开会议偶先程序卡死问题
* 修复设置页面打开时，同时入会程序异常崩溃问题

# v2.0.4(Sept 9, 2021)

### New Features

### Behavior changes

* 优化音视频权限判断
* IM SDK升级至8.7.0

### Bug Fixes

* 修复异常退出重新入会，音视频状态未同步问题
* 修复云端录制失效问题

# v2.0.0(Aug 12, 2021)

### New Features

* 
* 即刻会议增加入会密码`NEMeetingParams::password`
* 接口支持结束会议 `NEMeetingService::leaveMeeting`
* 增加入会超时配置以及入会的部分具体错误信息 `NEMeetingOptions::joinTimeout`

### Behavior changes

* G2 SDK 升级到 4.3.8
* 重构 native，改名为 roomkit
* 优化 IPC 反初始化的逻辑
* 优化创建/加入会议聊天室开关

### Bug Fixes

* 修复参会者列表共享白板图标显示问题
* 修复直播“仅本企业观看”显示问题
* 修复匿名入会，昵称设置无效问题

# v1.10.0(Jul 8, 2021)

### New Features

* 创建会议增加会议场景参数，支持传入受邀用户列表: `NEStartMeetingParams::scene`
* 预约/编辑会议增加会议场景参数，支持传入受邀用户列表：`NEMeetingItemSetting::scene`
* 创建/加入会议增加自定义标签参数：`NEMeetingParams::tag`
* 会议服务，当前会议成员信息增加自定义标签参数：`NEMeetingService::getCurrentMeetingInfo#NEInMeetingUserInfo::tag`

### Behavior changesd

### Bug Fixes

# v1.9.0(May 27, 2021)

### New Features

* 会议服务，当前会议信息新增属性： `NEMeetingService::getCurrentMeetingInfo`
   * 会议成员列表：`NEMeetingInfo::userList`
   * 会议唯一ID：`NEMeetingInfo::meetingUniqueId`
   * 会议主题：`NEMeetingInfo::subject`
   * 会议密码：`NEMeetingInfo::password`
   * 会议开始时间：`NEMeetingInfo::startTime`
   * 会议预约的开始时间：`NEMeetingInfo::scheduleStartTime`
   * 会议预约的结束时间：`NEMeetingInfo::scheduleEndTime`
* 初始化增加日志配置：`NEMeetingSDKConfig::getLoggerConfig`
* 初始化支持设置运行权限：`NEMeetingSDKConfig::setRunAdmin`

### Behavior changes

* mac G2 SDK升级到4.1.1
* mac 相芯美颜SDK回退到7.2.0
* 替换日志库为yx_alog
* 更新接口文档

### Bug Fixes

* 修复成员列表排序不对问题
* 修复全体静音/取消程序卡顿问题
* 修复共享时昵称显示的问题
* 修复部分未翻译的问题
* 修复部分场景下共享时视频窗口大小不正常的问题

# v1.8.0(Apr 27, 2021)

### New Features

* 创建会议增加云端录制配置参数:  `NEStartMeetingOption::noCloudRecord`
* 会议设置服务新增白板查询接口:  `NEWhiteboardController::isWhiteboardEnabled`
* 会议设置服务新增云端录制查询接口:  `NERecordController::isCloudRecordEnabled`
* 编辑/预约会议新增参数:  `NEMeetingItem::cloudRecordOn`
* 会话服务,会议信息新增sip号: `NEMeetingService::getCurrentMeetingInfo#NEMeetingInfo::sipId`
* 会议设置服务,会议信息新增sip号: `NESettingsService::getHistoryMeetingItem#NEHistoryMeetingItem::sipId`
* 初始化配置新增保活间隔设置: `NEMeetingSDKConfig::setKeepAliveInterval`
* 共享时支持显示视频

### Behavior changes

* G2 SDK升级到4.1.0
* 相芯美颜SDK升级到7.3.0
* 白板SDK升级到3.1.0
* 会议直播视图增加共享屏幕视图
* 共享时隐藏设置/取消设置焦点视频的入口
* 优化共享时的性能

### Bug Fixes

* 修复主持人全体静音，把自己也静音的问题
* 修复共享ppt时，部分场景下对端看不到画面的问题
* 修复windows下，分辨率超过1080P桌面共享对端看到模糊的问题
* 修复主持人移交时多次提示的问题
* 修复匿名入会时，入会中状态多次通知的问题
* 修复断线重连，偶现崩溃问题

# v1.7.2(Mar 30, 2021)

### New Features

* 支持设置直播权限 `NEMeetingItem::liveWebAccessControlLevel`

### Behavior changes

* 更新白板地址

### Bug Fixes

* 修复共享屏幕下，举手提示异常问题
* 修复共享屏幕下，聊天消息数目不同步问题
* 修复移交主持人给屏幕共享者，新主持人直播窗口成员列表空白问题
* 修复授权白板权限给离开会议的参会者时的崩溃问题

# v1.7.1(Mar 17, 2021)

### New Features

### Behavior changesd

### Bug Fixes

* 修复成员加入/离开会议画面闪烁问题

# v1.7.0(Mar 17, 2021)

### New Features

* 支持Windows共享屏幕共享音频 + 流畅优先
* 支持MacOS屏幕共享流畅优先
* 支持主持人结束成员屏幕共享
* 支持聊天室文本单条右键复制
* 支持会中白板功能
    * 新增白板菜单项，控制开启白板共享和结束白板共享
    * 新增`NEMeetingOptions.noWhiteBoard`选项配置白板功能是否开启，默认开启
    * 支持配置会议视图模式`NEMeetingOptions.defaultWindowMode`，支持普通和白板模式
* 支持会中改名，通过`NEMeetingOptions.noRename`选项配置该功能是否开启，默认为开启

### Behavior changes

* G2 SDK升级到4.0.1版本
* 取消自动随机移交主持人
* 更新开发环境为VS2019 + QT5.15.0

### Bug Fixes

* 修复屏幕共享下，聊天窗口未同步消息的问题

# v1.5.2(Jan 15, 2021)

### New Features

* 支持单个用户音频订阅/取消订阅接口`NEMeetingSDK::subscribeRemoteAudioStream`
* 支持多个用户音频订阅/取消订阅接口`NEMeetingSDK::subscribeRemoteAudioStreams`
* 支持全部用户音频订阅/取消订阅接口`NEMeetingSDK::subscribeAllRemoteAudioStreams`

### Behavior changes

* 优化账号登录流程
* 调整`NEAuthService::Login`接口为不带appkey

### Bug Fixes

* 修复结束共享时视频显示为共享画面的问题
* 修复macOS下部分wps的版本播放时不能共享的问题
* 修复会议非正常退出后，关联进程没有退出的问题
* 修复画廊模式下成员列表切换会闪烁的问题
* 修复部分显卡下会崩溃的问题
* 修复windows下共享时聊天窗口闪烁的问题
* 修复结束共享时偶现的崩溃问题

# v1.5.0(Dec 21, 2020)

### New Features

* 支持视频美颜 NESettingsService.GetBeautyFaceController()
* 支持直播功能 NESettingsService.GetLiveController()
* 支持会中禁止息屏
* 支持全局静音举手功能
* 支持展示SIP客户端入会信息

### Behavior changes

* MacOS共享支持WPS
* 应用共享优化
* 支持自定义工具栏
* 适配部分分辨率下共享工具栏显示

### Bug Fixes

* 修复共享状态下断网重连后其他端画面异常问题
* 修复win7下共享崩溃问题
* 修复窗口闪烁问题
  
# v1.3.3(Nov 27, 2020)

### New Features

* 支持共享应用

### Behavior changes

* G2 SDK 升级到3.8.1
* 成员列表搜索时自动去掉首尾空格
* 会议画廊模式每页不在展示自己，只在首页展示

### Bug Fixes

* 修复多端入会互踢时偶现崩溃问题 
* 修复会议画廊模式修改数量不生效的问题
* 修复移交主持人时成员离开房间，成员再进入房间时，成员列表有重复数据

# v1.3.2(Nov 20, 2020)

### New Features

* 会议举手功能
* 支持录制配置能力

### Behavior changes

* G2 SDK 升级到3.8.0
* quick controls 1升级到quick controls 2
* 会议预约自适应窗口比例
* 调整更新升级页面视觉样式

### Bug Fixes

* 修复应用内更新无法自动
* 修复拔掉副屏，副屏无法停止共享
* 修复共享结束时收到聊天消息，消息报错问题

# v1.3.1(Nov 13, 2020)

### New Features

* 会中反馈及会后反馈
* 预约会议编辑功能
* 使用网易会议账号登录接口 `NEAuthService::loginWithNEMeeting`
* 使用 SSO token 登录接口 `NEAuthService::loginWithSSOToken`
* 自动登录接口 `NEAuthService::tryAutoLogin`
* 创建及加入会议 option 中增加 NEShowMeetingIdOption 配置会议中显示会议 ID 的策略

### Behavior changes

* `NEMeetingSDK::initialized` config 参数中增加 AppKey 参数用于设置全局默认应用 Key 信息
* `NEAuthService::logout` 新增了带默认参数的形参，用以决定在退出时是否清理 SDK 缓存的用户信息

# v1.3.0(Oct 29, 2020)

### New Features

* 个人会议短号解析能力
* 组件在 `AuthService` 中增加 `getAccountInfo` 接口用于获取用户资料信息
* 组件对私有化环境能力支持
* 共享中不显示工具条
* 预约会议详情页

### Behavior changes

* 调整入会前后的整体 UI 视觉样式
* 升级 G2 SDK 到 3.7.0

### Bug Fixes

* 匿名入会输入错误会议码后无法再次入会
* 安装包签名失败导致部分场景无法正常安装（Windows only）
* 本地视频画面无法镜像
* 多端登录后入会本地视频无法渲染
* 多拓展屏下缩放比例不一致拖动导致界面异常
* 多拓展屏下部分窗口和全局 Toast 提示不跟随窗口
* 会议持续时间计时不准确
* 断网后无法再次开启会议（macOS only）
* 屏幕共享正在讲话文案优化

#### 移除

* 组件入会过程中取消按钮

#### 废弃

* 组件原有 `AccountService` 及功能函数 `getPersonalMeetingId()` 废弃不再使用