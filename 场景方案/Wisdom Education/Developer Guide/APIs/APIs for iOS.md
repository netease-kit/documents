## APIs for iOS
This topic provides API references for iOS applied in the Wisdom Education scenarios.
### API overview

The APIs supported by the NEEduLogic component are described in the following table:

-`EduManager` singleton class, which sets the SDK configuration information and holds sub-function objects.

| Name| Description|
| ------------------------------- | ---------- |
| setupAppKey:options:            | Initializes the component.|
| login:success:failure:          | Logs in to an account.|
| enterClassroom:success:failure: | Joins a class.|
| setCanvasView:forMember:        | Sets a canvas.   |
| leaveClassroom                  | Leaves a classroom   |
| destoryClassroom                | Destroys the classroom.  |

-`NEEduVideoService`: Audio & video management class.

| Name| Description|
| -------------------------- | ------------------------------------------------------------ |
| setupAppkey:               | Sets the Appkey for Audio & Video Call SDK by calling the setupAppKey:options: method of EduManager. |
| joinChannel: completion:   | Joins the room by calling enterClassroom:success:failure: of EduManager.|
| setupLocalVideo:           | Sets the local video canvas by calling the setCanvasView:forMember: method of EduManager. |
| setupRemoteVideo:          | Sets the remote video canvas by calling the setCanvasView:forMember: method of EduManager. |
| setupSubStreamVideo:       | Sets the substream canvas.|
| enableLocalAudio:          | Enables or disables audio streams.|
| enableLocalVideo:          | Enables or disables video streams.|
| muteLocalVideo:            | Mutes or unmutes video streams without disabling or enabling hardware. |
| muteLocalAudio:            | Mutes or unmutes audio streams without disabling or enabling hardware. |
| subscribeVideo: forUserID: | Subscribes video streams                                              |
| subscribeAudio: forUserID: | Subscribes audio streams                                              |
| leaveChannel | Leaves the room by calling  the leaveClassroom method of EduManager.     |
| destroy | Destroys the object by calling the destroyClassroom method of EduManager.         |

-`NEEduIMService`: Chat and signaling management class.

| Name| Description|
| ---------------------------------------- | ------------------------------------------------------------ |
| setupAppkey:                             | Sets the Appkey for the IMSDK by calling the setupAppKey:options: method of EduManager. |
| login: token: completion:                | Logs in to IMSDK by calling the login:success:failure: method of EduManager.  |
| logoutWithCompletion:                    | Logs out of IMSDK.                                                  |
| enterChatRoomWithParam: success: failed: | Joins the chat room by calling the enterClassroom:success:failure: method of EduManager. |
| exitChatroom: completion:                | Leaves the chat room.                                                 |
| sendChatroomTextMessage:                 | Sends a text message.|
| sendChatroomImageMessage:                | Sends an image message.|
| fetchChatroomInfo:                       | Gets the chat room information|
| destroy                                  | Destroys the object.                                                    |
|                                          |                                                              |
| Callback method | Description |
| didSendMessage: error:                   | The message has been delivered.|
| didRecieveChatMessages:                  | The recipient has received new messages.|
| didRecieveSignalMessage: fromUser:       | The recipient has received signaling notifications. |

- `NEEduMessageService`: Signaling notification delivery class

| Name | Description|
| --------------------------------------- | --------------------------- |
| updateProfile:                          | Initializes a class profile snapshot. |
|                                         |                             |
| Callback method | Description |
| onUserInWithUser: members:              | A user joins the class.               |
| onUserOutWithUser: members:             | A user leaves the class.                |
| onVideoStreamEnable: user:              | A user enables or disables the video stream.        |
| onAudioStreamEnable: user:              | A user enables or disables the audio stream.         |
| onSubVideoStreamEnable:user:            | A user enables or disables the substream.|
| onAudioAuthorizationEnable: user:      | The permissions to enable or disable the audio stream are authorized or revoked.|
| onVideoAuthorizationEnable: user:      | The permissions to enable or disable the video stream are authorized or revoked.|
| onWhiteboardAuthorizationEnable: user:  | The permissions to enable or disable whiteboard are authorized or revoked.|
| onScreenShareAuthorizationEnable: user: | The permissions to enable or disable screen sharing are authorized or revoked.|
| onHandsupStateChange: user:             | The hand-raising status is changed.|
| onLessonStateChange: roomUuid:          | The class status is changed.|
| onLessonMuteAllAudio: roomUuid:         | The callback is triggered if all members are muted.|
| onLessonMuteAllText: roomUuid:          | The callback is triggered if all members are banned. |

  
