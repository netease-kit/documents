# Android 升级指引

Android 升级会议组件版本只需修改项目 'app/build.gradle' 脚本文件中依赖的会议组件版本号即可：

```groovy
dependencies {
  // 使用 2.0.0 版本的会议组件	
  implementation 'com.netease.yunxin:vcelib:1.0.5'
}
```


# iOS 升级指引

iOS 升级会议组件版本只需修改项目中 `Podfile` 文件中会议组件的版本号即可：

```ruby
target 'Runner' do
	## 使用 2.0.0 版本的会议组件
	pod 'NEVCESDK', '1.0.5'
end	
```

# Windows/MacOS 升级指引

// TODO

# Web 升级指引

// TODO
