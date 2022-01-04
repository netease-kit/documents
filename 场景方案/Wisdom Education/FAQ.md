
## FAQ

### Why can’t transcoding recordings be completed for a long time?

- Issue: Recording and playback transcoding have not ended for a long time.

- Description

  :

  - The recording permissions for CommsEase VOD are not granted.
  - Multiple transcoding tasks are listed in the queue. The current transcoding task is waiting in the queue.

- Solution:

Check whether the recording permissions for VOD are granted.



- The playback feature requires the VOD recording permissions that can be granted in the CommsEase console. To check the VOD recording permissions for the project with the specified App Key, contact [your account manager](https://yunxin.163.com/bizQQWPA.html)).

- If the recording permissions are granted, the reason why the transcoding task has not ended for a long time is that the task queue is long.

Recording transcoding for Wisdom Education has a normal priority. If numerous transcoding tasks are queued, you have to wait for a long time. You can also enable high-priority transcoding for your task based on your requirements. To enable high-priority transcoding, contact [your account manager](https://yunxin.163.com/bizQQWPA.html)).

## FAQ for Android

### Why doesn’t the app respond when a specific component API is called?

In Wisdom Education, each class is designed as a process, from `init` to `destroy` and `NEEduUiKit.init()`. The methods are called if required and observers must be configured.

```
NEEduUiKit.init(uuid, token).observeOnce(viewLifecycleOwner, initObserver)
```

### Why is the error code -1 returned when `eduUiKit.init(uuid, token)` is called

- Issue: Failed to initialize `eduUiKit`.
- Description: `init` and `destroy` are not called in pairs.
- Solution: `init` and `destroy` must be called in pairs. If an instance is initialized using `init`, you must call `destroy` before another instance is initialized by calling `init`.



### Why does the client crash when joining a live large class

- Issue:

If the teacher creates a live large class and the chat room is not enabled. At this time, the student client failed to get the information about the chat room from the server side, so the following error occurred when students join the class.

```
com.netease.yunxin.app.wisdom.edu.ui.clazz.LiveClassActivity.initEduManager(LiveClassActivity.kt:132)

com.netease.yunxin.app.wisdom.edu.ui.clazz.LiveClassActivity.onCreate(LiveClassActivity.kt:115)
```

- Description

The live large class depends on the chat room for communication. Therefore, The chat room must be enabled.

- Solution:

The teacher must enable the chat room when creating a live large class.

