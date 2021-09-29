# SeatService Android API
## 简介


## API

### 基础数据结构定义
SeatInfo
- 麦位信息    

|属性|说明|
| ---- | ----|
|seatIndex|麦序|
|requestId||
|nickName|昵称|
|avatar|头像|
|videoState|视频状态 1 打开 0：关闭|
|audioState|音频状态 1 打开 0：关闭|
|accountId|账户ID|
|status|状态|
|roomId|房间ID|
|avRoomUid|用户在rtc房间中的uid|
|attachment|自定义附件JSON|

SeatOptions   
- 初始化配置信息

|属性|说明|
| ---- | ----|
|baseUrl|HTTP请求使用的baseUrl|
|appKey| 在云信申请到的appkey|
|token|用户登录后的accessToken，用于鉴权|
|userId|当前用户的userId|
|roomId|用户已经加入的roomId|
|rtcOption|rtc 操作是否委托给麦位组件实现（仅包含上下麦时的channel操作）|

AvRoomUser
- 用户在rtc房间中的信息

|属性|说明|
| ---- | ----|
|avRoomCName|rtc的 channelName|
|avRoomUid| rtc channel中的uid|
|avRoomCheckSum|加入rtc channel时需要鉴权使用|
|channelId|rtc 的channel id|

SeatUser
- 麦位用户信息

|属性|说明|
| ---- | ----|
|accountId|用户Id|
|nickName| 昵称|
|avatar|头像|
|audio|音频状态|
|video|视频状态|
|customInfo|自定义消息|

### 麦位控制接口
SeatService

|  接口  | 说明 |参数|备注|
|  ----  | ----  | ----| ----|
| sharedInstance  | 获取实例(单例) | | 静态接口|
| destroyInstance  | 销毁实例 || 静态接口|
| setupWithOptions  | 初始化配置 |context: Context, options: SeatOptions| |
| addDelegate  | 添加代理对象(仅支持添加一个) |delegate: SeatDelegate| |
| removeDelegate  | 移除代理对象|delegate: SeatDelegate| |
| leaveSeat  | 离开麦位|params: Attachmen| |
| kickSeat  | 踢下坐席| index//序号，toAccountId// 被操作观众的userId，requestId//请求标识，attachment| index 和 toAccountId必须有一个不为空 |
| applySeat  | 申请上麦|index//麦序 | |
| acceptSeatApply  | 同意上麦请求|  index//序号，toAccountId// 被操作观众的userId，requestId//请求标识,attachment| index 和 toAccountId必须有一个不为空|
| rejectSeatApply  | 拒绝上麦请求|index//序号，toAccountId// 被操作观众的userId，requestId//请求标识,attachment| index 和 toAccountId必须有一个不为空|
| cancelSeatApply  | 取消申请|requestId//请求Id| |
| pickSeat  | 抱上坐席|index//序号，toAccountId// 被操作观众的userId，requestId//请求标识,attachment| index 和 toAccountId必须有一个不为空|
| acceptSeatPick  | 同意报麦请求|requestId//请求Id| |
| rejectSeatPick  | 拒绝报麦请求|requestId//请求Id| |
| cancelSeatPick  | 取消抱上坐席|index//序号，toAccountId// 被操作观众的userId，requestId//请求标识,attachment| index 和 toAccountId必须有一个不为空|
| setSeatCustomInfo  | 设置自定义状态|index//序号,customInfo//自定义消息,删除传null| |
| setSeatOpenState  | 开启/关闭麦位，主播调用|index//序号,openState// true开启,false关闭| |
| setSeatAudioMuteState  | 设置麦位静音状态（主播,管理员，本麦位自己）| index//序号,toAccountId//被操作者账号,state//1开0关| index 和 toAccountId必须有一个不为空|
| setSeatVideoMuteState  | 设置麦位视频状态（主播，管理员，本麦位自己）|index//序号,toAccountId//被操作者账号,state//1开0关| index 和 toAccountId必须有一个不为空|
| getAudienceList  | 获取观众列表|type//观众类型,pageSize,pageNumber| |
| seatInfos  | 获取当前坐席信息|| |

### 麦位回调接口
SeatDelegate

|  接口  | 说明 |参数|备注|
|  ----  | ----  | ----| ----|
|onSeatApplyRequest| 收到上麦申请 |event: SeatApplyEvent||
|onSeatApplyRequestCanceled|上麦申请被取消的回调|	event:SeatApplyEvent||
|onSeatPickAccepted	| 收到报麦申请同意|event: SeatPickEvent ||
| onSeatPickRejected|收到报麦申请被拒绝	| event: SeatPickEvent||
|onSeatEntered|麦位加入的回调|	event: SeatEnterEvent||
|onSeatLeft| 	麦位离开的回调	|event: SeatLeaveEvent||
|onSeatPickRequest	| 收到报麦申请	|event: SeatPickRequestEvent||
|onSeatPickRequestCanceled|抱麦申请被取消的回调	|event: SeatPickRequestEvent||
|onSeatApplyAccepted|	申请上麦被同意|	event:SeatApplyEvent||
|onSeatApplyRejected|	申请上麦被拒绝	|event: SeatApplyEvent||
|onSeatAudioMuteStateChanged|	麦位声音状态回调	|event:SeatStateChangeEvent||
|onSeatVideoMuteStateChanged|	麦位视频状态回调|	event:SeatStateChangeEvent||
|onSeatOpenStateChanged|	麦位开关状态回调	|event:SeatStateChangeEvent||

## 错误码
Reason
|  错误码  | 说明 |
|  ----  | ----  | 
|101| 超时(麦位变化原因) |
|102| 被踢下麦(下麦原因) |