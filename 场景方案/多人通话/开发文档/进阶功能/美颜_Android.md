# 美颜功能
## 如何使用美颜功能？
目前Demo的美颜功能是基于相芯科技的美颜SDK实现的
### 准备工作
1. 联系[相芯](https://www.faceunity.com)获取美颜证书
2. 替换lib-beauty-faceunity/src/main/java/com/beautyFaceunity/authpack.java 证书文件
### 初始化美颜功能
```
 mFuRender = new FURenderer
                .Builder(this)
                .maxFaces(1)
                .inputImageOrientation(getCameraOrientation(Camera.CameraInfo.CAMERA_FACING_FRONT))
                .inputTextureType(FURenderer.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE)
                .setOnFUDebugListener(this)
                .setOnTrackingStatusChangedListener(this)
                .build();

        mFuRender.setBeautificationOn(true);

```
### 开启/关闭美颜功能
```
// ivBeauty.isSelected()  true表示开启美颜,false表示关闭美颜
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

```
### 释放美颜相关资源
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

        super.onDestroy();
    }

```

## 如何从工程中删除美颜功能？
如果您的实际项目中用不到美颜相关功能，可参考以下说明进行删除
1. 从setting.gradle文件中删除lib-beauty-faceunity
2. 删除lib-beauty-faceunity 目录
3. 删除biz-video-group、lib-modularity两个module的build.gradle文件中对lib-beauty-faceunity的依赖
4. 删除lib-modularity/src/main/java/com/netease/yunxin/nertc/module/base/sdk/NESdkBase.java中的initFaceunity方法
5. 删除美颜和滤镜相关的UI
