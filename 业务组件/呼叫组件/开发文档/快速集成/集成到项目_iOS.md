# 实现通话呼叫

针对已经集成 IM sdk 的用户若希望快速实现音视频通话功能，可通过集成呼叫组件完成实现。

## 注意事项

1.  若用户已经集成了 IM Sdk 或 NERTc Sdk 需参考**[产品动态](../../产品动态/产品动态-iOS.md)** 关于 sdk 版本的映射关系，确保使用的 sdk 版本和组件版本一致。
2.  若用户未接入 IM Sdk 可参考[官网](https://doc.yunxin.163.com/docs/TM5MzM5Njk/DQ5MTA5ODQ?platformId=60278)完成 IM Sdk 的接入。

**用户完成上述两点注意后继续参考下面内容。**

## 准备工作

1.  环境准备

    1.  Xcode 10 及以上版本；
    2.  iOS 9.0 及以上版本的 iOS 设备；
    3.  CocoaPods；

2.  开通相关功能

    **注：如果已有相应的应用，可在原应用上申请开通【音视频通话 2.0】及【信令】或 【IM 专业版】功能。**

    针对新应用可按照如下操作实现功能开通。

    网易云控制台，点击【应用】>【创建】创建自己的 App，在【功能管理】中申请开通如下功能

    1.  若仅使用呼叫功能，则开通

        1.  【信令】
        2.  【音视频通话 2.0】
        3.  【安全模式】- 组件支持使用安全模式以及非安全模式，开启安全模式请咨询相应 SO。

    2.  若还需使用话单功能，则需要开通
        1.  【IM 专业版】
        2.  【G2 话单功能】-云信控制台-音视频通话 2.0-功能配置-话单配置-开启话单类型消息抄送。

3.  在控制台中【appkey 管理】获取 appkey。

## 集成说明

### 1. 引入

> 建议使用 CocoaPods 进行管理，在`Podfile`中添加：

```ruby
pod 'NERtcCallKit'
```

> 组件依赖 NERtcSDK 的特定版本，请**_不要_**在 Podfile 中指定 NERtcSDK 的版本

### 2. 初始化

组件实现为单实例，通过接口 `NERtcCallKit.sharedInstance` 获取此实例，调用实例方法 `setupAppKey` 完成初始化。

```objc
#import <NERtcCallKit/NERtcCallKit.h>
@interface SomeViewController()<NERtcCallKitDelegate>
@end

@implementation SomeViewController

- (void)viewDidLoad {
    [self setupSDK];
}

- (void)setupSDK {
    NERtcCallOptions *option = [NERtcCallOptions new];
    option.APNSCerName = kAPNSCerName;
    option.supportAutoJoinWhenCalled = YES;
    NERtcCallKit *callkit = [NERtcCallKit sharedInstance];
    [callkit setupAppKey:kAppKey options:option];

    // 请求 rtc token 服务，若非安全模式则不需设置
    callkit.tokenHandler = ^(uint64_t uid, void (^complete)(NSString *token, NSError *error)) {

        /* 获取token以及回传给SDK token 的示例

        NETokenTask *task = [NETokenTask taskWithUid:[NSString stringWithFormat:@"%llu",uid] withAppkey:kAppKey];
        [task postWithCompletion:^(NSDictionary * _Nullable data, NSError * _Nullable error) {
            if (error == nil && data && [data isKindOfClass:[NSDictionary class]]) {
                NSNumber *code = data[@"code"];
                NSString *checksum = data[@"checksum"];
                if (code.intValue == 200 && checksum) {
                    complete(checksum,nil);
                    return;
                }
            }
            complete(nil,error);
        }]; */
    };
}

@end
```

> 注：setupAppKey 方法为使用组件前 **_必须_** 调用的方法，若不调用，会发生不可预知的问题!

### 3.登录/登出

**若已经在 app 内实现了 NIMSDK 登录/登出逻辑，则不必调用相应的登录/登出接口，直接跳过此章节。**

否则，可使用组件的`-[NERtcCallKit login:]` 进行登录，同样可使用`-[NERtcCallKit logout:]`进行登出，**_登出或未进行登录则不能进行呼叫_**。

```objc
[[NERtcCallKit sharedInstance] login:@"im accid" token:@"im token" completion:^(NSError * _Nullable error) {

}];

[[NERtcCallKit sharedInstance] logout:^(NSError * _Nullable error) {

}];
```

### 4. 实现一对一呼叫

无论是一对一通话还是群组通话，在呼叫或收到呼叫邀请时需要设置相应的回调监听，用于接收对应通话的控制消息。首先在需要收到监听的地方实现`NERtcCallKitDelegate`

```objc
@interface SomeViewController: UIViewController <NERtcCallKitDelegate>

- (void)dealloc {
    [NERtcCallKit.sharedInstance removeDelegate:self];
}

- (void)viewDidLoad {
    [NERtcCallKit.sharedInstance addDelegate:self];
}

// 主叫发起呼叫
- (void)didCall {
    [[NERtcCallKit sharedInstance] addDelegate:self];
    [NERtcCallKit sharedInstance].timeOutSeconds = 30;
    NSError *error;
    [[NERtcCallKit sharedInstance] setLoudSpeakerMode:YES error:&error];
    [[NERtcCallKit sharedInstance] enableLocalVideo:YES];
    [[NERtcEngine sharedEngine] adjustRecordingSignalVolume:200];
    [[NERtcEngine sharedEngine] adjustPlaybackSignalVolume:200];
    [[NERtcCallKit sharedInstance] call:@"im Accid" type: NERtcCallTypeVideo completion:^(NSError * _Nullable error) {
        NSLog(@"call error code : %@", error);

        /* 设置视频通话的 view
        if (self.callType == NERtcCallTypeVideo) {
            [[NERtcCallKit sharedInstance] setupLocalView:self.bigVideoView.videoView];
        } */
        if (error) {
            /// 对方离线时 通过APNS推送 UI不弹框提示
            if (error.code == 10202 || error.code == 10201) {
                return;
            }

            if (error.code == 21000 || error.code == 21001) {
                //呼叫失败销毁当前通话页面
            }
        }
    }];
}

#pragma mark - NERtcVideoCallDelegate
// 被叫实现监听回调
- (void)onInvited:(NSString *)invitor
          userIDs:(NSArray<NSString *> *)userIDs
      isFromGroup:(BOOL)isFromGroup
          groupID:(nullable NSString *)groupID
             type:(NERtcCallType)type
       attachment:(nullable NSString *)attachment {

    [NIMSDK.sharedSDK.userManager fetchUserInfos:@[invitor] completion:^(NSArray<NIMUser *> * _Nullable users, NSError * _Nullable error) {
        if (error) {
            NSLog(@"fetchUserInfo failed : %@", error);
        }else {
            /*
            // 解析数据
            NIMUser *imUser = users.firstObject;
            NEUser *remoteUser = [[NEUser alloc] init];
            remoteUser.imAccid = imUser.userId;
            remoteUser.mobile = imUser.userInfo.mobile;
            remoteUser.avatar = imUser.userInfo.avatarUrl;

            // 调出通话界面，需要用户根据G2的音视频API自己实现，或者参考示例代码
            NECallViewController *callVC = [[NECallViewController alloc] init];
            callVC.localUser = [NEAccount shared].userModel;
            callVC.remoteUser = remoteUser;
            callVC.status = NERtcCallStatusCalled;
            callVC.callType = type;
            callVC.modalPresentationStyle = UIModalPresentationOverFullScreen;
            [self.navigationController presentViewController:callVC animated:YES completion:nil];
             */
        }
    }];
}

// 当被叫 onInvited 回调发生，调用 accept 接听呼叫
- (void)acceptCall {
    [[NERtcCallKit sharedInstance] accept:^(NSError * _Nullable error) {
        if (error) {
            NSLog(@"接听失败 : %@", error);
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                // 销毁当前通话页面
            });
        }else {
            /* 设置rtc视频通话的view
            [[NERtcCallKit sharedInstance] setupLocalView:self.smallVideoView.videoView];
            [[NERtcCallKit sharedInstance] setupRemoteView:self.bigVideoView.videoView forUser:@"对端 im accid"];
             */
        }
    }];
}

// 主叫被叫结束通话
- (void)hangup{
    [[NERtcCallKit sharedInstance] hangup:^(NSError * _Nullable error) {

    }];
}

@end
```
