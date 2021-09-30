# 集成到项目
本文介绍如何在你的APP中快速实现多人音视频通话功能
## 前提条件
在开始集成前，请确保您已完成以下操作：
- 在云信控制台创建应用，并获取对应的 AppKey，可参考[本文](../../快速开始/创建应用和服务开通.md)。
- 为此应用开通以下相关服务与抄送：
  - 产品服务：  
    - 音视频通话 2.0
  - 产品功能：   
    - 音视频通话 2.0 的云端录制和抄送功能。
- 如需使用美颜功能请联系[相芯](https://www.faceunity.com/)获取美颜证书

## 开发环境 
在开始运行示例项目之前，请确保开发环境满足以下要求：

| 环境要求         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| JDK 版本         | 1.8.0 及以上版本                                             |
| Android API 版本 | API 23、Android 6.0 及以上版本                               |
| CPU架构          | ARM64、ARMV7                                                 |
| IDE              | Android Studio                                               |
| 其他             | 依赖 Androidx，不支持 support 库。Android 系统 4.3 或以上版本的移动设备。 |  
## NERTC SDK集成步骤
多人音视频通话功能需要使用NERTC SDK。集成NERTC SDK请参考以下文档
- [NERTC SDK集成](https://doc.yunxin.163.com/docs/jcyOTA0ODM/DkyMDM2Mzk?platformId=50002)

## 集成多人音视频通话参数设置页
- 参考[音视频参数设置页VideoRoomSetActivity](https://github.com/netease-kit/NEGroupCall/blob/master/Android/biz-video-group/src/main/java/com/netease/biz_video_group/yunxin/voideoGroup/ui/VideoRoomSetActivity.java)
- 音视频参数设置参考rtcSetting属性
  ```
   private RtcSetting rtcSetting=new RtcSetting();
  ```
## 集成多人音视频通话页
- 参考[多人通话页VideoRoomSetActivity](https://github.com/netease-kit/NEGroupCall/blob/master/Android/biz-video-group/src/main/java/com/netease/biz_video_group/yunxin/voideoGroup/ui/VideoMeetingRoomActivity.java)
- SDK初始化,参考initData()方法
  ```
    private void initData() {

        if (roomInfo != null) {
            appKey = roomInfo.nrtcAppKey;
            long begin = System.currentTimeMillis();
            setupNERtc(roomInfo.nrtcAppKey);
            long end = System.currentTimeMillis();
            long setupNeRtccost = end - begin;
            TempLogUtil.log("setupNeRtc Cost:"+setupNeRtccost);
            joinChannel(roomInfo.avRoomCheckSum, roomInfo.avRoomCName, roomInfo.avRoomUid);
            long joinChannelCost = System.currentTimeMillis() - end;
            TempLogUtil.log("joinChannel Cost:"+joinChannelCost);
            neRtcEx.setStatsObserver(NERtcStatsDelegateManager.getInstance());
        }
        long beautyBegin = System.currentTimeMillis();
        mFuRender = new FURenderer
                .Builder(this)
                .maxFaces(1)
                .inputImageOrientation(getCameraOrientation(Camera.CameraInfo.CAMERA_FACING_FRONT))
                .inputTextureType(FURenderer.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE)
                .setOnFUDebugListener(this)
                .setOnTrackingStatusChangedListener(this)
                .build();

        mFuRender.setBeautificationOn(true);
        long beautyInitCost = System.currentTimeMillis() - beautyBegin;
        TempLogUtil.log("mFuRender Init Cost:"+beautyInitCost);

        neRtcEx.setVideoCallback(neRtcVideoFrame -> {
            if (ivBeauty.isSelected()) {
                if (isFirstInit){
                    if (rtcHandler==null){
                        rtcHandler=new Handler(Looper.myLooper());
                    }
                    mFuRender.onSurfaceCreated();
                    isFirstInit=false;
                    return false;
                }
                //此处可自定义第三方的美颜实现
                neRtcVideoFrame.textureId = mFuRender.onDrawFrame(neRtcVideoFrame.data, neRtcVideoFrame.textureId,
                        neRtcVideoFrame.width, neRtcVideoFrame.height);

                neRtcVideoFrame.format = NERtcVideoFrame.Format.TEXTURE_RGB;
            }
            return ivBeauty.isSelected();
        }, true);
    }
  ```
- 加入房间,参考joinChannel()方法
  ```
    private void joinChannel(String token, String channelName, long uid) {
        NERtcEx.getInstance().joinChannel(token, channelName, uid);
    }
  ```  
- 离开房间，参考leave()方法
  ```
    private void leave() {
        neRtcEx.leaveChannel();
        status = STATUS_CALL_END;
    }
  ```    
- 销毁实例，参考onDestroy()方法
  ```
     @Override
    protected void onDestroy() {
        if (neRtcEx != null) {
            //关掉美颜
            neRtcEx.setVideoCallback(null, false);
            neRtcEx.release();
        }
        if (rtcHandler!=null&&mFuRender!=null){
            rtcHandler.post(() -> mFuRender.onSurfaceDestroyed());
        }

        NERtcStatsDelegateManager.getInstance().clearAll();
        super.onDestroy();
    }
  ```   

  ## 查看音视频实时数据
  - 参考[StateDialog](https://github.com/netease-kit/NEGroupCall/blob/master/Android/biz-video-group/src/main/java/com/netease/biz_video_group/yunxin/voideoGroup/ui/StateDialog.java)