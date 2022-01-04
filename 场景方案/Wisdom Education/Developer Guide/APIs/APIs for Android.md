## Android APIs
This topic provides [API References](https://dev.yunxin.163.com/docs/interface/NERTC_SDK/EDU/html/index.html) for Wisdom Education.
### API overview

The APIs supported by the NEEduLogic component are described in the following table:

- **NEEduUiKit** singleton class that provides basic capabilities such as SDK configuration and SDK initialization, and gets NEEduManager at the same time. 

| Name | Description|
| ------------------------------------------------------ | ----------------- |
| config(context: Application, eduOptions: NEEduOptions) | Configures the SDK. |
| init()                                                 | Initializes the component.        |
| enterClass(neEduClassOptions: NEEduClassOptions)       | Joins a class.          |

- **NEEduManager** singleton class, using various business services provided by SDK.

| Name | Description|
| --------------------- | ---------------- |
| getRoomService        | Gets the class management service |
| getMemberService      | Gets the class member service|
| getRtcService         | Gets the audio and video service   |
| getIMService          | Gets the messaging service |
| getShareScreenService | Gets the screen sharing service |
| getBoardService       | Gets the whiteboard service     |
| getHandsUpService     | Gets the hand-raising service |
| destroy()             | Destroys the object         |

- **NEEduRoomService** class management class

| Name | Description|
| ----------------------- | ---------------------- |
| startClass              | The teacher starts a class.           |
| finishClass:            | The teacher ends a class           |
|                         |                        |
| Callback method | Description |
| onCurrentRoomInfo       | The notification is delivered if the class profile is changed. |
| onRoomStatesChange:     | The notification is delivered if the room status is changed.           |
| onNetworkQualityChange: | The notification is delivered if the network quality is changed.     |

-**NEEduMemberService** ***Class member*** **management class **

| Name | Description|
| ------------------------ | ------------------------ |
| getMemberList            | Gets a list of members in the class |
| getLocalUser             | Gets the current member            |
|                          |                          |
| Callback method | Description |
| onMemberJoin             | The notification is delivered if the member online status is changed.     |
| onMemberLeave            | The notification is delivered if a member leaves the class.        |
| onMemberPropertiesChange | The notification is delivered if the properties of a member are changed        |

-**NEEduRtcService** Audio and video management class. 

| Name | Description|
| ---------------------- | -------------------------------------- |
| muteAllAudio:          | All members are muted.                         |
| updateRtcAudio:        | Sets the audio, and turns on or off devices                  |
| enableLocalVideo:      | Enables or disables local video stream, and turns on or off devices             |
| updateRtcVideo:        | Sets the video view for the members                    |
| updateRtcSubVideo:     | Sets the video substream                          |
| localUserVideoEnable:  | Publishes or unpublishes the local video stream without turning on or off devices         |
| localUserAudioEnable:  | Publishes or unpublishes local audio streams without turning on or off devices |
| remoteUserVideoEnable: | The teacher enables or disables the remote video stream.                   |
| remoteUserAudioEnable: | The teacher enables or disables the remote audio stream.             |
| destroy                | Leaves the class                       |
|                        |                                        |
| Callback method | Description |
| onMuteAllAudio:        | The message is delivered if all members are muted.|
| onStreamChange:        | The message is delivered if stream status (audio, video, substream video) is changed|

-**NEEduIMService** Chat management class. 

| Name | Description|
| --------------------------- | --------------------------- |
| sendMessage:                | Sends a message.                  |
| muteAllChat:                | Mutes all members|
| enterChatRoom:              | Joins a chat room                 |
| exitChatRoom:               | Leaves the chat room                 |
|                             |                             |
| Callback method | Description |
| onReceiveMessage:           | The notification is delivered if a message is sent.              |
| onMessageStatusChange:      | The notification is delivered if an image message is sent.      |
| onAttachmentProgressChange: | The notification is delivered for the upload or download progress of the message attachment. |

-**NEEduShareScreenService** Screen sharing management class. 

| Name | Description|
| ------------------- | -------------------------------- |
| grantPermission:    | Authorizes or revokes the screen sharing permissions of authorized members|
| shareScreen         | Enables screen sharing without enabling or disabling screen capture   |
| finishShareScreen | Disables screen sharing without enabling or disabling screen capture  |
| startScreenCapture | Starts screen sharing |
| stopScreenCapture   | Stops screen sharing  |
|                     |                                  |
| Callback method | Description |
| onPermissionGranted | The screen sharing permissions have changed |
| onScreenShareChange | The screen sharing status has changed |

-**NEEduBoardService** Whiteboard management class. 

| Name | Description|
| -------------------- | ---------------------------- |
| grantPermission:     | Authorizes or revokes the whiteboard permissions of authorized members|
| initBoard | Initializes a whiteboard instance.|
| setEnableDraw | Specifies whether to allow drawing |
|                      |                              |
| Callback method | Description |
| onPermissionGranted: | The notification is delivered if the whiteboard permissions are changed|

-**NEEduHandsUpService** Hand raising (applicable to the large class scenarios ) management class.

| Name | Description|
| --------------------- | ------------------------ |
| getHandsUpApplyList:  | Gets the details of the members who send hand-raising requests|
| getOnStageMemberList | Gets the details of members on stage |
| handsUpStateChange | Changes the hand-raising state |
|                       |                          |
| Callback method | Description |
| onHandsUpStateChange: | The student status on stage changes |
