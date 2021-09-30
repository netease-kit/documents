# 美颜功能开启和移除
## 开启相芯美颜功能
1. 联系相芯获取美颜证书
2. 替换lib-beauty-faceunity/src/main/java/com/beautyFaceunity/authpack.java 证书文件
## 剔除美颜功能
1. setting 文件中删除lib-beauty-faceunity
2. 删除lib-beauty-faceunity 目录
3. 删除biz-live gradle文件对lib-beauty-faceunity的依赖
4. 删除biz-live/src/main/java/com/netease/biz_live/yunxin/live/ui/BeautyControl.kt文件，并删除对改文件的应用
5. 删除美颜和滤镜相关的UI
