# 客户端方案

## 功能实现流程
### 直播基础功能
1. 主播开播流程   
![主播开播](../image/anchor_create_live_room.png)
* 客户端请求应用服务器创建房间   
```kotlin
//lib_live_room_service/repository/LiveRoomApi.kt
@POST("/live/v1/create")
    suspend fun createRoom(
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Response<LiveInfo>
```
* 应用服务器通过请求IM服务器和rtc服务器分别创建IM聊天室和rtc 房间   
* 客户端判断返回值，如果失败退出，成功则分别加入rtc房间和聊天室，以上如果有任何一处失败都直接回调onError，然后退出   
```kotlin
//lib_live_room_service/impl/LiveRoomServiceImpl.kt
    /**
     * 主播加入房间（聊天室和rtc）
     */
    private fun anchorJoinRoom(liveInfo: LiveInfo, callback: NetRequestCallback<LiveInfo>) {
        this.liveInfo = liveInfo
        val rtcResult = engine.joinChannel(
            liveInfo.anchor.roomCheckSum,
            liveInfo.live.roomCname,
            liveInfo.anchor.roomUid!!
        )
        if (rtcResult == NERtcConstants.ErrorCode.OK) {
            joinChatRoom(liveInfo, callback)
        } else {
            callback.error(rtcResult, "join rtc channel failed")
        }
    }
```
* 创建房间成功后开始添加推流任务
```kotlin
//lib_live_room_service/impl/LiveStream.kt
 /**
         * 添加推流任务
         *
         * @param liveRecoder
         * @return
         */
        fun addLiveStreamTask(liveRecoder: LiveStreamTaskRecorder): Int {
            //初始化task
            val liveTask = getStreamTask(liveRecoder)
            liveTask.layout = getSignalAnchorStreamLayout(liveRecoder)
            ALog.d(
                LOG_TAG,
                "addLiveStreamTask recoder = $liveRecoder"
            )
            val ret: Int = NERtcEx.getInstance().addLiveStreamTask(
                liveTask
            ) { s: String?, code: Int ->
                if (code == RtcCode.LiveCode.OK) {
                    ALog.d(
                        LOG_TAG,
                        "addLiveStream success : taskId " + liveRecoder.taskId
                    )
                } else {
                    ALog.d(
                        LOG_TAG,
                        "addLiveStream failed : taskId " + liveRecoder.taskId + " , code : " + code
                    )
                }
            }
            if (ret != 0) {
                ALog.d(
                    LOG_TAG,
                    "addLiveStream failed : taskId " + liveRecoder.taskId + " , ret : " + ret
                )
            }
            return ret
        }
```
2. 观众加入直播房间   
* 观众通过调用应用服务器提供的joinRoom接口获取房间信息
```kotlin
//lib_live_room_service/repository/LiveRoomApi.kt
    /**
     * enter room
     */
    @POST("/live/v1/join")
    suspend fun enterRoom(
        @Body params: Map<String, @JvmSuppressWildcards Any>
    ): Response<LiveInfo>
```
* 加入聊天室
```kotlin
//lib_live_room_service/impl/LiveRoomServiceImpl.kt
/**
     * 加入聊天室
     */
    private fun joinChatRoom(liveInfo: LiveInfo, callback: NetRequestCallback<LiveInfo>) {
        ChatRoomControl.joinChatRoom(
            liveInfo.live.chatRoomId,
            if (isAnchor) liveInfo.anchor else liveInfo.joinUserInfo!!,
            isAnchor,
            object : NetRequestCallback<Unit> {
                override fun error(code: Int, msg: String) {
                    callback.error(code, msg)
                }

                override fun success(info: Unit?) {
                    callback.success(liveInfo)
                }

            })
    }
```
* 通过播放器拉流
```kotlin
//biz_live/yunxin/live/audience/utils/PlayerControl.kt
 /**
     * 进行播放准备，设置 拉流地址，视频渲染区域
     *
     * @param url        拉流地址
     * @param renderView 视频渲染区域
     */
    fun prepareToPlay(url: String?, renderView: TextureView?) {
        currentUrl = url
        this.renderView = renderView
        renderView?.visibility = View.VISIBLE
        doPreparePlayAction()
    }
```
3. 观众打赏流程   
![观众打赏流程图](../image/reward.png)
* 观众调用应用服务器提供的打赏接口打赏
```kotlin
//lib_live_room_service/repository/LiveRoomApi.kt
    /**
     * audience reward to anchor
     */
    @POST("/live/v1/reward")
    suspend fun reward(
        @Body params: Map<String, @JvmSuppressWildcards Any>
    ): Response<Unit>
```
* 应用服务器通过聊天室消息将打赏信息同步给房间里的所有人
```kotlin
//lib_live_room_service/chatroom/control/ChatRoomControl.kt
  /**
     * 聊天室服务回调监听（IM SDK）
     */
    private val chatRoomMsgObserver: Observer<MutableList<ChatRoomMessage>> =
        Observer { chatRoomMessages ->
            if (chatRoomMessages.isEmpty()) {
                return@Observer
            }
            if (liveUser == null) {
                return@Observer
            }
            for (message in chatRoomMessages) {
                //...
                // 打赏
                val attachStr = message.attachStr
                val jsonObject: JsonObject = GsonUtils.fromJson<JsonObject>(
                    attachStr,
                    JsonObject::class.java
                )
                val type = jsonObject["type"].asInt
                if (type == Constants.MsgType.MSG_TYPE_REWARD) {
                    val rewardInfo: RewardInfo =
                        GsonUtils.fromJson(attachStr, RewardInfo::class.java)
                    delegate?.onUserReward(rewardInfo)
                }

            }
        }
```
### PK直播
1. 开始PK   
![](../image/startPk.png)
* 主播A通过调用应用服务器接口发起Pk请求
```kotlin
//lib_live_pk_service/impl/PkServiceImpl.kt
/**
     * request Pk for other anchor
     * accountId:the anchor you want pk
     */
    override fun requestPk(accountId: String, callback: NetRequestCallback<Unit>) {
        targetAccId = accountId
        pkScope?.launch {
            Request.request(
                { PkRepository.pkAction(Constants.PkAction.PK_INVITE, accountId) },
                success = {
                    callback.success()
                },
                error = { code: Int, msg: String ->
                    callback.error(code, msg)
                }
            )
        }
//ib_live_pk_service/repository/PkApi.kt
     /**
     * Pk action
     */
    @POST("/pk/v1/inviteControl")
    suspend fun pkAction(
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Response<Unit>   
```
* 主播B通过解析应用服务器发送的透传消息接收PK请求并响应   
* 同意后双发开始跨频道转发
```kotlin
//lib_live_room_service/impl/LiveRoomServiceImpl.kt
override fun startChannelMediaRelay(token: String, channelName: String, uid: Long): Int {
        //初始化目标房间结构体
        val addRelayConfig = NERtcMediaRelayParam().ChannelMediaRelayConfiguration()
        //设置目标房间1
        val dstInfoA = NERtcMediaRelayParam().ChannelMediaRelayInfo(token, channelName, uid)
        addRelayConfig.setDestChannelInfo(channelName, dstInfoA)
        //开启转发
        return NERtcEx.getInstance().startChannelMediaRelay(addRelayConfig)
    }
```
* 应用服务器通过抄送接收双发跨频道转发的消息判断PK开始
* 应用服务器通过发送聊天室消息告诉双发PK开始
* 双发主播更新推流信息
```kotlin
//lib_live_room_service/impl/LiveRoomServiceImpl.kt
/**
     * update push stream task
     */
    override fun updateLiveStream(liveRecoder: LiveStreamTaskRecorder): Int {
        val task = LiveStream.getStreamTask(liveRecoder)
        task.layout = when (liveRecoder.type) {
            Constants.LiveType.LIVE_TYPE_DEFAULT -> {
                LiveStream.getSignalAnchorStreamLayout(liveRecoder)
            }
            Constants.LiveType.LIVE_TYPE_PK -> {
                LiveStream.getPkLiveStreamLayout(liveRecoder)
            }
            Constants.LiveType.LIVE_TYPE_SEAT -> {
                LiveStream.getSeatLiveStreamLayout(liveRecoder)
            }
            else -> null
        }
        return LiveStream.updateStreamTask(task)
    }
//lib_live_room_service/impl/LiveStream.kt
fun updateStreamTask(task: NERtcLiveStreamTaskInfo): Int {
            val ret: Int = NERtcEx.getInstance().updateLiveStreamTask(
                task
            ) { s: String?, code: Int ->
                if (code == RtcCode.LiveCode.OK) {
                    ALog.d(
                        LOG_TAG,
                        "updateStreamTask success : taskId " + task.taskId
                    )
                } else {
                    ALog.d(
                        LOG_TAG,
                        "updateStreamTask failed : taskId " + task.taskId + " , code : " + code
                    )
                }
            }
            if (ret != 0) {
                ALog.d(
                    LOG_TAG,
                    "updateStreamTask failed : taskId " + task.taskId + " , ret : " + ret
                )
            }
            return ret
        }
```
2. 结束PK
![](../image/endPk.png)
* pk倒计时结束，有主播主动结束PK或应用服务器收到某个主播rtc断开抄送都会触发Pk结束流程
* 应用服务器通过聊天室发送结束Pk的消息，主播端收到后结束跨频道转发，并更新推流任务   
### 连麦直播   
接入麦位组件实现，参考[麦位组件](../../../业务组件/麦位组件/README.md)
