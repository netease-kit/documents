# iOS 升级指引

iOS 升级会议组件版本只需修改项目 'Podfile' 脚本文件中依赖的会议组件版本号即可：

```ruby
target 'App' do
  use_frameworks!
  ## 指定要依赖的会议组件版本
  pod 'NEMeetingSDK', '~> 2.0.0'
end
```

