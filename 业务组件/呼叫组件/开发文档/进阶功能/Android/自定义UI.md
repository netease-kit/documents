若希望自定义呼叫UI可按照如下步骤实现

### 1. 创建呼叫 Activity 并继承 CommonCallActivity 

``` java
public class TestActivity extends CommonCallActivity {

    @Override // 布局文件
    protected int provideLayoutId() {
        return R.layout.activity_test;
    }

    @NotNull
    @Override // rtc 相关回调设置，用户可实现自己的回调，并根据 回调处理相关 UI
    protected NERTCCallingDelegate provideRtcDelegate() {
        return new NERtcCallDelegateForP2P();
    }
}
```

### 2. 实现自己的布局并调用父类方法完成呼叫与被叫页面

**详情可参考[附件](附件) （实现1v1视频通话示例）** 

用户可通过关键字 `CommonCallActivity` 过滤日志信息，示例代码中未处理接口错误情况，可根据情况自己实现。

**作为子类可使用如下方法以及参数完成相应实现。**

**CommonCallActivity**

| 方法             | 参数                            | 返回      | 说明                                                 |
| ---------------- | ------------------------------- | --------- | ---------------------------------------------------- |
| getCallParam     | -                               | CallParam | 呼叫/被叫时的必要参数                                |
| isLocalMuteAudio | -                               | Boolean   | 本地音频是否关闭采集                                 |
| isLocalMuteVideo | -                               | Boolean   | 本地视频是否关闭采集                                 |
| doMuteAudio      | -                               | -         | 修改本地当前音频采集状态，若当前为开启则执行后为关闭 |
| doMuteVideo      | -                               | -         | 修改本地当前视频采集状态，若当前为开启则执行后为关闭 |
| doSwitchCamera   | -                               | -         | 切换摄像头方向，默认为正向                           |
| doCall           | `JoinChannelCallBack`，`String` | -         | 主叫呼叫                                             |
| doCancel         | `RequestCallbackWrapper<Void>`  |           | 主叫取消                                             |
| doReject         | `RequestCallbackWrapper<Void>`  | -         | 被叫拒绝                                             |
| doAccept         | `JoinChannelCallBack`           | -         | 被叫接受                                             |
| doHangup         | `RequestCallbackWrapper<Void>`  | -         | 挂断                                                 |

| 属性      | 类型           | 说明             |
| --------- | -------------- | ---------------- |
| videocall | NERTCVideoCall | 基础呼叫组件实例 |


**CallParam**

| 方法               | 类型                  | 说明                       |
| ------------------ | --------------------- | -------------------------- |
| isCalled           | `Boolean`             | true 被叫，false 主叫      |
| getChannelType     | `int`                 | 1 - 音频通话，2- 视频通话  |
| getCallerAccId     | `String`              | 呼叫方 IM 账号Id           |
| getCurrentAccId    | `String`              | 当前用户 IM 账号 Id        |
| getCalledAccIdList | `List<String>`        | 被呼叫用户 IM 账号列表     |
| getGroupId         | `String`              | 群组 id                    |
| getExtras          | `Map<String, Object>` | 自定义扩展参数             |
| getP2pCalledAccId  | `String`              | 获取 p2p 通话的被叫账号 Id |

### 3. 注册呼叫页面

在初始化时注册如

```java
CallKitUIOptions options = new CallKitUIOptions.Builder()
		......
  	.p2pVideoActivity(TestActivity.class)// 注册对应视频呼叫页面
  	.....
		.build();

CallKitUI.init(getApplicationContext(), options);
```

### 4. 呼叫

完成上述步骤后可参考[快速集成#集成到项目#实现一对一通话](../../快速集成/集成到项目_Android.md)章节实现通话。
