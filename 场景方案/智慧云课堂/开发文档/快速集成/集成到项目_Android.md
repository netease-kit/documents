# 集成到项目
## 新建 Android 工程
   1. 运行 Android Sudio，在顶部菜单依次选择 “File -> New -> New Project...” 新建工程。
   2. 选择 'Phone and Tablet' -> 'Empty Activity' ，并单击Next。
   3. 配置工程相关信息。
   > 注意： Minimum API Level 为 API 21。

   4. 单击 'Finish'，完成工程创建。

## 添加依赖模块  
   1. 复制示例项目中的Modules和config.gradle、config.properties等相关配置文件至当前目录。
   2. settings.gradle引入Modules。

```
include ':edu-ui'
include ':edu-logic'
include ':whiteboard'
include ':im'
include ':rtc'
include ':base'
```


   3. 修改工程目录下的 'app/build.gradle' 文件，添加智慧云课堂 SDK相关的依赖。


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



// 若出现 More than one file was found with OS independent path 'lib/arm64-v8a/libc++_shared.so'.

// 可以在主 module 的 build.gradle 文件中 android 闭包内追加如下 packageOptions 配置
android{
    //......
    packagingOptions {
      pickFirst 'lib/arm64-v8a/libc++_shared.so'
      pickFirst 'lib/armeabi-v7a/libc++_shared.so'
    }
}





dependencies {
    //......
    // 添加EduUI依赖
    implementation project(':edu-ui')
}
```


   4. 在顶部菜单单击 'Build -> Make Project' 构建工程，下载依赖。依赖下载完成后即可在代码中引入 云课堂组件 中的类和方法。

## 权限配置

智慧云课堂 SDK 正常工作需要应用获取以下权限：
```
<!-- 网络相关 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- 多媒体 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.Manifest.permission.READ_PHONE_STATE"/>

<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
```
以上权限已经在SDK内部进行声明，开发者无需在`AndroidManifest.xml`文件中重新声明这些权限，但需要自己编码实现运行时的权限申请。运行时的权限可在应用首页中统一申请，详细信息请参考[Android运行时权限申请示例](https://developer.android.google.cn/guide/topics/permissions/overview)。

## 初始化组件

1. 进行全局配置

首先，创建 `NEEduOptions` 实例对 SDK 进行全局配置，然后调用 `config` 方法传入该实例。`NEEduOptions` 包含以下参数：

| 配置项        | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| APP_KEY       | 应用的 AppKey。可以在网易云信控制台中查看。                  |
| BASE_URL      | 应用服务器地址。私有化配置时需替换为私有化部署地址           |
| AUTHORIZATION | 调用服务端接口时，请求头中的校验参数。                       |
| reuseIM       | 配置是否复用底层NIM-SDK的长连接通道，默认关闭。仅当应用中同时还需独立接入和使用NIM-SDK，才需要开启该配置，其他情况下请忽略该配置。 |

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

2. 初始化

配置完成后，创建 `NEEduUiKit` 实例，调用 `init` 方法进行初始化。包含以下参数：

| 配置项 | 说明                                            |
| ------ | ----------------------------------------------- |
| uuid   | 用户鉴权userUuid。匿名登录时请设置为空字符串""  |
| token  | 用户鉴权userToken。匿名登录时请设置为空字符串"" |

示例代码：

```
NEEduUiKit.init(uuid, token).observeOnce(viewLifecycleOwner, initObserver)
```

## 学生或教师加入课堂

学生或教师加入课堂时，会使用`NEEduClassOptions`的实例创建课堂并且加入，如果对应课堂号的课堂已经存在就直接加入。`NEEduClassOptions` 包含以下参数：

| 配置项    | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| classId   | 课程号，课堂唯一标识                                         |
| className | 课程名称                                                     |
| nickName  | 用户在课堂中的昵称                                           |
| sceneType | 课堂类型，有三种类型： 1v1， 小班课， 大班课                 |
| roleType  | 角色类型：host：教育场景中映射为老师，broadcaster: 教育场景中映射为学生 |

示例代码：

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

教学双方加入教室后，开启课程。智慧云课堂提供了丰富的[课堂互动](../进阶功能/课堂互动_Android.md)能力。

## 示例项目结构

| 文件夹/文件                  | 说明              |
| -------------------------  | ----------------- |
| app                        | 壳工程        |
| base                       | 公共基础组件 |
| edu-logic                  | 教育核心业务模块  |
| edu-ui                     | UIKit组件    |
| im                         | IM服务组件      |
| rtc                        | 音视频通话服务组件      |
| whiteboard                 | 白板组件      |
| config.properties          | 定义项目需要的各种配置信息      |

## 自定义课堂UI

如果 示例代码 中默认实现的 UI 不符合您的预期，您可以按需实现自己的用户界面，即只使用我们封装好的组件所提供的音视频能力，自行实现 UI 部分。

教育组件功能模块：  
<image width="70%" src="../../Images/layer.png" alt="chat"/>

**EduUI：**包含教育组件的UI的实现，包括一对一教学、多人小班课、互动大班课、直播大班课等场景的ViewController、View以及model部分。

**EduLogic：**是依赖云信的音视频SDK、IMSDK以及白板SDK对于教育逻辑的实现，分别对应NEEduRtcService、NEEduIMService、NEEduBoardService。

**NEEduRtcService：**是音视频服务，提供可供 App 调用的音视频相关方法。

**NEEduIMService：**是IM服务，提供可供 App 调用的即时通信、聊天室相关方法。

**NEEduBoardService：**是互动白板服务，提供可供 App 调用的互动白板相关方法。
