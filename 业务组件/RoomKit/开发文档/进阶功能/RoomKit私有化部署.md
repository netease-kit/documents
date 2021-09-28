组件化SDK支持私有化服务，支持接入在企业内部部署的私有会议服务器。开启私有化服务的步骤如下：
 
# 1.部署私有化会议服务器
 
开通会议私有化服务首先需要在企业内部部署企业专用的会议服务器，以供会议SDK连接。该部署过程需要企业与私有化服务团队协作完成。
 
# 2.各端SDK初始化
 
私有服务器部署完成之后，私有化服务团队会提供给企业对应的会议私有化配置文件`server.conf`(该配置文件在不同SDK场景下使用时可能需要重新命名)。各端SDK在初始化时，需要进行相关的配置，以便SDK能接入到目标私有服务器上。
 
## Flutter

 
- 将配置文件重命名为 `server.conf`，并在构建前添加到 `assets` 资源根目录下（不要使用多级目录）
- 修改SDK初始化逻辑，打开 `NEMeetingSDKConfig#useAssetServerConfig` 初始化配置，代码如下:
 
```dart
    /// 该配置需要设置为true，SDK会自动解析 server.conf 配置文件
      String? customServerConfig = await NEMeetingPlugin().getAssetService().loadCustomServer();
    var initRes = await _roomKit.initialize(NERoomKitConfig(
      appKey: "Your appKey",
      serverConfig: customServerConfig,
    ));
```

 
## Windows & MacOS
- 重命名并拷贝配置文件到指定目录：将配置文件重命名为`server.config`, Windows下在 NetEaseMeetingHost.exe 程序同级目录下放入 server.config 文件；MacOS 下在 NetEaseMeetingHost.app 应用 Contents/MacOS 目录下放入 server.config 文件。
- 修改SDK初始化逻辑，代码如下：
  
```c++
NEMeetingSDKConfig config;

QString displayName = QObject::tr("Your_Product_Name");

QByteArray byteDisplayName = displayName.toUtf8();

config.getAppInfo()->ProductName(byteDisplayName.data());

config.getAppInfo()->OrganizationName("Your_Company_Name");

config.getAppInfo()->ApplicationName("Your_App_Name");

config.setDomain("Your domain");
//开启私有化配置
config.setUseAssetServerConfig(true);
	
NEMeetingSDK::getInstance()->initialize(config, callback)
```