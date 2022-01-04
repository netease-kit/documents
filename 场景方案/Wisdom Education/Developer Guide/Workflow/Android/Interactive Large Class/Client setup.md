This topic describes typical features provided by Wisdom Education. You can develop functionalities for interactive large class scenario based on the Wisdom Education solution. In addition, you can add extra features to suit your business requirements.

## Architecture

If the default UI component in the demo project does not meet your requirements, you can build your UI component. You can use only the audio and video call capability.

Component modules for education:

![](https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdoc%2FSolutions-WisdomEducation-Arch03.png)


- EduUI includes the ViewController, View, and model UI components for education scenarios, such as one-to-one tutoring, breakout class, interactive large class, and live large class.
- EduLogic provides business logic components with NEEduRtcService, NEEduIMService, and NEEduBoardService based on NERTC SDK, IM SDK, and Whiteboard SDK.
- NEEduRtcService is an audio and video call service. Your app can call methods supported by the service for audio and video calls.
- NEEduIMService is an IM service. Your app can call methods supported by the service for instant messaging and chat room.
- NEEduBoardService is an interactive whiteboard service. Your app can call methods supported by the service for interactive whiteboard.

## Business procedure

The following diagram shows the basic processes of starting a class:

![](https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdoc%2FSolutions-WisdomEducation-Arch01.png)


If the app client requests to join the class:
1. The App client sends a request to the app server for creating a scene configuration. Query the room based on the parameters of the configuration. If the room does not exist, a new room is created based on the configuration information. If the room already exists, you are prompted that the room already exists.
2. To join the class, check whether the account has logged in to IM. You must log in to IM before you can join the class. If the login fails, login failure is returned. Send the request to join the class using the appropriate API. You are redirected to the class page if the API call succeeds. Join the class with your online status.
3. Request a snapshot, get the chat room ID, and join the chat room
4. Initialize the class instance. After you join the whiteboard room, the whiteboard server will perform the authentication for IM account. Update the local stream and start to pull the stream.

## Client API workflow

### Integrate with your project

1. Create a project using Android Studio.
    1. Run Android Studio and create a new project by selecting **File -> New -> New Project...** in the top menu bar.
    2. Select **Phone and Tablet** -> **Empty Activity** and click **Next**.
    3. Configure project settings.
      ::: note note
      Minimum API Level is set to API 21.
      :::
    4. Click **Finish** to create the project.
2. Add dependencies.
    1. Copy the `Modules` and `config.gradle`, and `config.properties` files in the demo project to the current directory.
    2. Import `Modules` in `settings.gradle`.

        ```
        implementation project(':edu-ui')
        implementation project(':recordplay-ui')
        implementation project(':viewbinding')
        ```

    3. Modify the `app/build.gradle` file in the project directory and add dependencies related to Wisdom Education SDK.
        ```
        allprojects {
            repositories {
                google()
                jcenter()
                maven{
                    url 'https://oss.sonatype.org/content/repositories/snapshots/'
                }
            }
        }
        
        // If the message More than one file was found with OS independent path 'lib/arm64-v8a/libc  _shared.so’ appears,
        // you can add the following packageOptions configuration to the android closure in the build.gradle file of the main module
        android{
            //......
            packagingOptions {
                pickFirst 'lib/arm64-v8a/libc++_shared.so'
                pickFirst 'lib/armeabi-v7a/libc++_shared.so'
            }
        }


        dependencies {
            //......
            // Add EduUI dependencies
            implementation project(':edu-ui')
        }
        ```
    4. Create the project by clicking **Build** -> **Make Project** on the top menu bar and download dependencies.

        After the download is complete, the classes and methods for the Wisdom Education component can be imported into the code.
3. Permission configuration.

    The Wisdom Education SDK requires the application to get the following permissions:
    ```
    <!-- Network -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- Multimedia -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.Manifest.permission.READ_PHONE_STATE"/>

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    ```

    ::: note note
    The previous permissions have been declared inside the SDK. Developers do not need to redeclare these permissions in the `AndroidManifest.xml` file. However, developers must code to request permissions for the application at runtime. Runtime permissions can be requested on the home page of the app. For more information, see [Android Documentation](https://developer.android.google.cn/guide/topics/permissions/overview).
    :::

### Initialize components

1. Create global configuration.

    Create a `NEEduOptions` instance to configure global settings for the SDK and then call the `config` method to pass in the instance.

    NEEduOptions contains the following parameters:

    | Configuration item | Description |
    |---|---|
    | APP_KEY | The AppKey for the project. You can view the AppKey in the CommsEase console. |
    | BASE_URL | The URL of the app server The URL must be replaced for private server for on-premises deployment|
    | AUTHORIZATION | The verification parameter in the request header used for requests to call the server APIs. |
    | reuseIM | Specifies whether to reuse the persistent connection channel of the underlying NIM-SDK. By default, the channel is disabled. This configuration must be enabled only if the NIM-SDK is integrated and used as a separate service. Otherwise, ignore this configuration. |

    ```
    NEEduUiKit.config(
        this,
        NEEduOptions(
            BuildConfig.APP_KEY,
            BuildConfig.AUTHORIZATION,
            BuildConfig.API_BASE_URL,
            reuseIM
        )
    )
    ```
2. Initialize components.

    After the configuration is complete, create a NEEduUiKit instance and call the init method to initialize the instance. The components contain the following parameters:

    | Configuration item | Description |
    |---|---|
    | uuid | The userUuid used for authentication. Please set an empty string for anonymous logins "" |
    | token | The userToken used for authentication. Please set an empty string for anonymous logins "" |

    Sample code:

    ```
    NEEduUiKit.init(uuid, token).observeOnce(viewLifecycleOwner, initObserver)
    ```

### Joining a class

To join a class, a student or teacher can create and join a class by using the `NEEduClassOptions` instance. If the class with the class number already exists, the student or teacher will join the class. `NEEduClassOptions` contains the following parameters:

| Configuration item | Description |
|---|---|
| classId | The class ID, the unique identifier of the class |
| className | The class name |
| nickName| The user's alias in the class|
| sceneType | Class type. 4 types of classes are available: one-to-one tutoring, breakout class, interactive large class, and live large class|
| roleType | Role type. host: a teacher in an education use case. broadcaster: a student. |


Sample code:
```
eduManager.enterClass(neEduClassOptions).map {
    if (it.success()) {
        if (neEduClassOptions.roleType == NEEduRoleType.HOST) {
            when (neEduClassOptions.sceneType) {
                NEEduSceneType.ONE_TO_ONE -> {
                    OneToOneTeacherActivity.start(context)
                }
                NEEduSceneType.SMALL -> {
                    SmallClazzTeacherActivity.start(context)
                }
                NEEduSceneType.BIG -> {
                    BigClazzTeacherActivity.start(context)
                }
            }
        } else {
            when (neEduClassOptions.sceneType) {
                NEEduSceneType.ONE_TO_ONE -> {
                    OneToOneStudentActivity.start(context)
                }
                NEEduSceneType.SMALL -> {
                    SmallClazzStudentActivity.start(context)
                }
                NEEduSceneType.BIG -> {
                    BigClazzStudentActivity.start(context)
                }
            }
        }
    }
    it
}
```

### Asking questions

After the teacher and students join the class, the class starts. Wisdom Education provides a variety of class features, such as interactive whiteboard, screen sharing, hand-raising, audio and video communications.

1. Start a class.

    The teacher starts the class.
    
    Sample code:

    ```   
    // The teacher starts the class
    eduManager.getRoomService().startClass(roomUuid = eduRoom.roomUuid)
        .observe(this@BaseClassActivity, {
            ALog.i(tag, "startClazz")
        })
    ```
2. Student management.
    1. The teacher can control the cameras and microphones of the students by calling the `remoteUserVideoEnable` and `remoteUserAudioEnable` methods. If you want to interact with specified students, you can turn on the microphones of the students.
    
        Sample code:
        ```   
        // Turn on or off the cameras of the students
        eduManager.roomConfig.memberStreamsPermission()?.apply {
            val self = entryMember
            video?.let { it ->
                // Check whether you have the permissions
                if (it.hasAllPermission(self.role)) {
                    // Call remoteUserVideoEnable to turn on the camera of the student with a specified userUuid
                    eduManager.getRtcService().remoteUserVideoEnable(userUuid, true)
                        .observe(this@BaseClassActivity, {
                            // The callback returns the result.
                            ALog.i(tag, "switchRemoteUserVideo")
                            ToastUtil.showShort(R.string.operation_successful)
                        })
                }
            }
        }

        // Turn on or off audio from students
        eduManager.roomConfig.memberStreamsPermission()?.apply {
            val self = entryMember
            audio?.let { it ->
                // Check whether you have the permissions
                if (it.hasAllPermission(self.role)) {
                    // Call remoteUserAudioEnable to open the audio of the student with a specified userUuid
                    eduManager.getRtcService().remoteUserAudioEnable(member.userUuid, !member.hasAudio())
                        .observe(this@BaseClassActivity, {
                            // The callback returns the result.
                            ALog.i(tag, "switchRemoteUserAudio")
                            toastOperateSuccess()
                        })
                }
            }
        }
        ```
    2. The teacher grants students the permissions to use the whiteboard or screen sharing by calling the `grantPermission` method.
  
        Sample code:

        ```
        // Grant students the permissions to use the whiteboard.
        eduManager.roomConfig.memberPropertiesPermission()?.apply {
            val self = entryMember
            whiteboard?.let { it ->
                // Check whether you have the permissions
                if (it.hasAllPermission(self.role)) {
                    // Grant students the permissions to use the whiteboard by calling grantPermission.
                    eduManager.getBoardService().grantPermission(member.userUuid, !member.isGrantedWhiteboard())
                        .observe(this@BaseClassActivity, {
                            // The callback returns the result.
                            ALog.i(tag, "grantWhiteboardPermission")
                        })
                }
            }
        }
        ```
3. Screen sharing

    The teacher or student initiates screen sharing and shares the local screen by calling `startScreenCapture`.
    
    Sample code:

    ```
    // Start screen sharing.
    // Create a screen sharing configuration instance
    val config = NERtcScreenConfig().apply {
        contentPrefer = NERtcScreenConfig.NERtcSubStreamContentPrefer.CONTENT_PREFER_DETAILS
        videoProfile = RTCVideoProfile.kVideoProfileHD1080p
    }
    // Initiate local screen sharing
    eduManager.getShareScreenService().startScreenCapture(config, data, object :
        MediaProjection.Callback() {
        override fun onStop() {
            // The callback returns the result.
            runOnUiThread { stopLocalShareScreen() }

        }
    })
    ```
4. Online chat room

    In a breakout class, interactive large class, or live large class, instant messages are exchanged in the chat room. Teachers and students can send text or image messages in the chat room. The teacher can mute or unmute the chat room.

    Teachers and students join the chat room by calling `enterChatRoom`, and send text and image messages by calling `sendMessage`.
    
    Sample code:
    ```
    // Start a chat room
    // Create a EnterChatRoomData instance
    val data = EnterChatRoomData(activity.eduRoom?.chatRoomId())
    // Use the EnterChatRoomData instance to enter the chat room
    imService.enterChatRoom(data).observe(this, { it ->
        // The callback returns the result.
        if (it.success()) roomInfo = it.data!!.roomInfo
        it
    }

    // Send text messages
    // Create a text message
    val chatMessage = ChatRoomMessageBuilder.createChatRoomTextMessage(it.roomId, text)
    // Send a message
    imService.sendMessage(chatMessage)


    // Send image messages
    // Create an image message
    val chatMessage =
        ChatRoomMessageBuilder.createChatRoomImageMessage(it.roomId, file, file?.name)
    // Send an image message
    imService.sendMessage(chatMessage)
    ```

### 5. Raising hands

In the interactive large class scenario, students can request for broadcast permissions to interact with the whiteboard, screen sharing, and send videos and voices.

1. The student raises his hand to request for broadcast permissions.
  
    Sample code:
    ```
    handsUpStateChange(NEEduHandsUpStateValue.APPLY, entryMember.userUuid)


    private fun handsUpStateChange(state: Int, userUuid: String) {
        eduManager.getHandsUpService().handsUpStateChange(state, userUuid)
            .observe(this,
                { t ->
                    if (!t.success()) {
                        if (t.code == NEEduHttpCode.ROOM_PROPERTY_CONCURRENCY_OUT.code) {
                            ToastUtil.showShort(getString(R.string.stage_student_over_limit))
                        } else {
                            ToastUtil.showShort(getString(R.string.operate_fail))
                        }
                        ALog.w("fail to $state hands up ${t.code}")
                    }
                })
    }
    ```
2. The student cancels the request.
  
    Sample code:
    ```
    handsUpStateChange(NEEduHandsUpStateValue.STUDENT_CANCEL, entryMember.userUuid)
    ```
3. The teacher approves the request of hand-raising.
    
    Sample code:
    ```
    handsUpStateChange(NEEduHandsUpStateValue.TEACHER_ACCEPT, userUuid)
    ```
4. The teacher declines the request.
    
    Sample code:
    ```
    handsUpStateChange(NEEduHandsUpStateValue.TEACHER_REJECT, userUuid)
    ```
5. The teacher moves the student off the stage.
    
    Sample code:
    ```
    handsUpStateChange(NEEduHandsUpStateValue.TEACHER_OFF_STAGE, userUuid)
    ```
6. Monitor the status changes of students.

    Sample code:
    ```
    eduManager.getHandsUpService().onHandsUpStateChange().observe(this, { t -> onHandsUpStateChange(t) })
    ```
## 6. Recording and playback

The audio and video stream, whiteboard, and screen sharing content are recorded during the class. Teachers and students can view playbacks for review. All class modes support recording and playback. The recording feature is supported by cloud recording. The client app only needs to integrate the `Recordplay` playback component, and the code that adds dependencies is included in the component.

When the teacher starts a class, the server will automatically start recording. If the class ends, you have to wait about 20 minutes for the server to transcode the recording. After the transcoding is completed, users can watch the recorded content through the Recordplay playback component. The demo project shows how to quickly integrate the Recordplay component. Perform the following actions:
1. The `Recordplay` playback component contains three dependent modules: `recordplay-logic`, `recordplay-model`, and `recordplay-ui`. Copy `recordplay-logic`, `recordplay-model`, and `recordplay-ui` in the demo project to the current project directory.

2. Add dependencies.

    Sample code:
    ```
    implementation project(':recordplay-ui')
    ```
  3. After the class ends, the viewer calls `fetchRecord` to obtain the recent playback recording and create a player instance.
  
     Sample code:
        ```
        RecordPlayRepository.appKey = BuildConfig.APP_KEY
        RecordPlayRepository.baseUrl = BuildConfig.API_BASE_URL
        // Query the playback recordings based on roomUuid and rtcCid, and create a player instance.
        NERecordPlayUiKit.fetchRecord(roomUuid, rtcCid)
            .observeOnce(viewLifecycleOwner, initRecordObserver)
            

        // The callback returns the query result.
        private val initRecordObserver = { t: NEResult<NERecordPlayer> ->
            when {
                t.success() -> {
                    // If the API call succeeds, the playback UI appears
                    enterRecordPlay()
                }
                // The recording is not transcoded. You must wait about 20 minutes for the server to complete transcoding.
                t.code == NEEduHttpCode.NO_CONTENT.code -> {
                    ALog.i("init record failed, result $t")
                    ToastUtil.showLong(getString(R.string.course_playback_file_is_being_transcoded))
                }
                else -> {
                    // Failed to get the playback.
                    ALog.i("init record failed, result $t")
                    val tip = context?.let { NEEduErrorCode.tipsWithErrorCode(it, t.code) }
                    if (!TextUtils.isEmpty(tip)) {
                        ToastUtil.showLong(tip!!)
                    } else {
                        ToastUtil.showLong(getString(R.string.open_recordplay_fail_try_again))
                    }
                }
            }
        }
        ```

  4. If the API call is successful, you are redirected to the `NERecordActivity` playback interface in the `Recordplay` module.
  
     Sample code:
     ```
     NERecordActivity.start(requireActivity())

      <!-- Playback UI -->
     <activity
        android:name=".NERecordActivity"
        android:configChanges="keyboardHidden|screenSize|orientation"
        android:launchMode="singleTop"
        android:screenOrientation="sensorLandscape"
        android:theme="@style/Theme.EduApp.Landscape" />
     ```
  5. Initialize a player instance.
  
     Sample code:
     ```
     var recordPlayer = NERecordPlayer.instance
     recordPlayer.init(application, this)
     ```
  6. Start playback.
  
     Sample code:
     ```     
     // Check the playback status
     when (recordPlayer.getState()) {
        // The player is in the ready state and starts playing the recording
        NERecordPlayState.PREPARED, NERecordPlayState.PAUSED -> recordPlayer.start()
         ...
     }
     ```
  7. Pause the player
  
     Sample code:
     ```
     // Check the player status
     when (recordPlayer.getState()) {
         // The player is in the pause state and pauses the playback
         NERecordPlayState.PLAYING -> recordPlayer.pause()
         ...
     }
     ```
  8. Drag the position.
  
     Sample code:
     ```
     // Change the playback position based on progress. The progress is a value between 0 and 100.
     recordPlayer.seek(recordPlayer.getDuration() * progress / 100)
     ```
### The class ends

End the class by calling `NEEduUiKit.destroy()`.

Sample code:
```
eduManager.getRtcService().leave() // Leave the room.
eduManager.getMemberService().getLocalUser().let {
    if (it != null && it.hasSubVideo()) {
        stopLocalShareScreen { // If the screen sharing is enabled, close the screen sharing window.
            NEEduUiKit.destroy() // End the class
            finish()
        }
    } else {
        NEEduUiKit.destroy() // If screen sharing is disabled, end the class.
        finish()
    }
}
```


