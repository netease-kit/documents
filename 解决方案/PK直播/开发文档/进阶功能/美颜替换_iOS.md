# 美颜功能开启和移除
本解决方案美颜功能基于相芯SDK实现
## 开启相芯美颜功能
1. 联系相芯获取美颜证书
2. 替换NLiteAVDemo/beauty/authpack.h 证书文件
## 剔除美颜功能
1. 工程文件中删除NLiteAVDemo/beauty文件夹
2. 去除podfile文件中对于美颜的SDk依赖，删除或者注释掉 pod 'Nama-lite', '7.3.2'内容。
3. 删除美颜SDK中初始化的内容
4. 删除美颜和滤镜相关的UI
