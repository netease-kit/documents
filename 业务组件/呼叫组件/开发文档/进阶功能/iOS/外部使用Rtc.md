## 呼叫组件 与 Rtc SDK 混合使用

背景：因为某些业务场景需要Rtc与呼叫组件混合使用，由于Rtc是单代理并且只有初始化的时候可以设置代理，在初始化的其他时机设置代理不会生效，此情景下，如果是主动调用Rtc的接口不会有问题(Rtc全局单例，一个地方初始化可以不同地方使用)，但是如果需要使用回调方法处理一些业务，由于单代理的特性，如果组件内使用并设置回调代理，用户侧就难以使用，所以呼叫组件内部对Rtc回调进行了一次转发。

**注意** 下面的逻辑是在混合使用时添加的逻辑，其他使用呼叫组件的逻辑按照接入文档正常接入，和下面的逻辑不冲突。

### 具体调用逻辑

1. 设置中转代理
```objc
  //设置此代理的类即可在当前类通过呼叫组件内部中转的方式接收Rtc的回调，具体回调参考NERtcEngineDelegate
  - (void)setupDelegate {
      [[NERtcCallKit sharedInstance] setEngineDelegate:self];
  }
  // 回调示例
  - (void)onNERtcEngineUserDidJoinWithUserID:(uint64_t)userID userName:(NSString *)userName {
    //your business code
  }
```

2. 外部销毁Rtc
```objc
  //通常情况用户不需要外部销毁Rtc实例，如果外部销毁Rtc实例，在使用呼叫组件时需要重新初始化呼叫组件
  NERtcCallOptions *option = [NERtcCallOptions new];
  option.APNSCerName = @"apns cer name";
  NERtcCallKit *callkit = [NERtcCallKit sharedInstance];
  [callkit setupAppKey:@"app key" options:option];
```
