# vce-sdk-web

网易云信视频客服 sdk（vce sdk），有`客户`与`客服`两个角色，以下将分别做使用说明

## 下载

```bash
$ npm install vce-sdk-web --save
```

## 引入

```ts
import { Agent, Guest } from 'vce-sdk-web'

// 初始化客服sdk实例
const agent = new Agent()
agent.init({
  appkey: '',
  debug: true,
})

// or 初始化客户sdk实例
const guest = new Guest()
guest.init({
  appkey: '',
  debug: true,
})
```

## 接口说明

### 客服

```ts
/**
 * 初始化客服
 * @param param
 * @param param.appKey appkey
 * @param param.debug 是否开启调试模式
 * @param param.baseDomain [可选] 发起请求的domain
 */
init({ appKey, debug, baseDomain, }: {
    appKey: string;
    debug: boolean;
    baseDomain?: string;
}): Promise<void>;

/**
 * 登录
 * @param param
 * @param param.username 用户名
 * @param param.password 密码
 */
login({ username, password, }: {
    username: string;
    password: string;
}): Promise<NEAccountInfo>;

/**
 * 登出
 */
logout(): Promise<void>;

/**
 * 请求业务列表
 */
queryGroupList(): Promise<GetCategoryListRes['ret']>;

/**
 * 接听并加入房间
 */
accept(): Promise<NERoomInfo>;

/**
 * 转接
 * @param groupId 业务id
 */
transfer(groupId: string): Promise<void>;

/**
 * 邀请其他客服
 * @param groupId 业务id
 */
invite(groupId: string): Promise<void>;

/**
 * 拒接
 */
reject(): Promise<void>;

/**
 * 挂断
 */
hangup(): Promise<void>;

/**
 * 离开房间
 * @param end 是否需要结束房间 true 结束；false 不结束仅离开
 */
leaveRoom(end: boolean): Promise<void>;

/**
 * 开启服务
 * @param groupIds 业务id的数组
 */
checkin(groupIds: string[]): Promise<void>;

/**
 * 关闭服务
 * @param force 是否强制关闭
 */
checkout(force: boolean): Promise<void>;

/**
 * 获取roomKit的实例，用于加入房间后的操作
 */
getRoomKit(): RoomKit | undefined;

/**
 * 销毁实例
 */
destroy(): void;
```

### 客户

```ts
/**
 * 初始化客户
 * @param param
 * @param param.appKey appkey
 * @param param.debug 是否开启调试模式
 * @param param.baseDomain [可选] 发起请求的domain
 */
init({ appKey, debug, baseDomain, }: {
    appKey: string;
    debug: boolean;
    baseDomain?: string;
}): Promise<void>;

/**
 * 登录
 * @param param
 * @param param.account
 * @param param.token
 */
login(params: {
    account: string;
    token: string;
}): Promise<NEAccountInfo>;

/**
 * 登出
 */
logout(): Promise<void>;

/**
 * 请求业务列表
 */
queryGroupList(): Promise<GetCategoryListRes['ret']>;

/**
 * 发起呼叫
 * @param groupId 业务id
 * @param isVip 是否是vip
 */
call(groupId: string, isVip?: boolean): Promise<void>;

/**
 * 挂断
 */
hangup(): Promise<void>;

/**
 * 呼叫成功后，加入房间
 * @param roomId 房间id
 */
joinRoom(roomId: string): Promise<NERoomInfo>;

/**
 * 离开房间
 */
leaveRoom(): Promise<void>;

/**
 * 获取roomKit的实例，用于加入房间后的操作
 */
getRoomKit(): RoomKit | undefined;

/**
 * 销毁客户实例
 */
destroy(): void;
```

## 事件说明

### 客服事件说明

```ts
// 客户呼入事件
agent.on('onInviteAgentJoinRoom', (params: {
  roomId: string // 房间id
  categoryList: string[] // 客户办理的业务列表
  visitorNickname: string // 客户昵称
}) => void)

// 客服登录状态改变事件
agent.on('onLoginStateChange', (loginState: LoginState) => void)

// 客服服务状态改变事件
agent.on('onCheckInStateChange', (state: boolean) => void)

// 客服呼叫状态改变事件
agent.on('onCallStateChange', (callState: CallState) => void)
```

### 客户事件说明

```ts
// 接通成功事件，可以在此时进入房间
guest.on('onInviteGuestJoinRoom', (roomId: string) => void)

// 客服转接事件
guest.on('onTransfered', () => void)

// 客户登录状态改变事件
guest.on('onLoginStateChange', (loginState: LoginState) => void)

// 客户排队状态改变事件
guest.on('onQueueStateChange', (params: {
  position: number // 当前位置
  time: number // 等待时间
}) => void)

// 客户呼叫状态改变事件
guest.on('onCallStateChange', (callState: CallState) => void)
```

### 事件中的枚举值说明

```ts
enum LoginState {
  idle, // 空闲
  logining, // 登录中
  logined, // 已经登录
  logouting, // 登出中
}

enum CallState {
  idle, // 空闲
  outgoing, // 呼出中
  incoming, // 呼入中
  talking, // 通话中
}
```

### 进入房间后

可以通过 [roomkit](../../../RoomKit/开发文档/API接口/Web.md) 来进行相应的操作
