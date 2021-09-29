# 美颜功能
## 如何使用美颜功能？
目前Demo的美颜功能是基于相芯科技的美颜SDK实现的
### 准备工作
1. 联系[相芯](https://www.faceunity.com)获取美颜证书
2. 替换authpack.h证书文件
### 初始化美颜功能
```
[[FURenderer shareRenderer] setupWithData:nil dataSize:0 ardata:nil authPackage:&g_auth_package authSize:sizeof(g_auth_package) shouldCreateContext:YES];

```
### 设置美颜参数
```
- (void)setBeautyParam:(NETSBeautyParam *)param
{
    if ([param.mParam isEqualToString:@"cheek_narrow"] || [param.mParam isEqualToString:@"cheek_small"]) {
        // 程度值 只去一半
        [[NETSFUManger shared] setParamItemAboutType:NETSFUNamaHandleTypeBeauty name:param.mParam value:param.mValue * 0.5];
    } else if([param.mParam isEqualToString:@"blur_level"]) {
        // 磨皮 0~6
        [[NETSFUManger shared] setParamItemAboutType:NETSFUNamaHandleTypeBeauty name:param.mParam value:param.mValue * 6];
    } else {
        [[NETSFUManger shared] setParamItemAboutType:NETSFUNamaHandleTypeBeauty name:param.mParam value:param.mValue];
    }
}

```
### 重置美颜
```
   - (void)resetSkinParams
{
    self.skinParams = [self originSkinParams];
    for (NETSBeautyParam *param in self.skinParams) {
        [self setBeautyParam:param];
    }
}

```
### 设置滤镜
```
- (void)setFilterParam:(NETSBeautyParam *)param
{
    int handle = items[NETSFUNamaHandleTypeBeauty];
    [FURenderer itemSetParam:handle withName:@"filter_name" value:[param.mParam lowercaseString]];
    [FURenderer itemSetParam:handle withName:@"filter_level" value:@(param.mValue)]; //滤镜程度
    
    self.seletedFliter = param;
}
```
### 重置滤镜
```
- (void)resetFilters
{
    self.filters = [self originFilters];
    self.seletedFliter = [self.filters firstObject];
    
    [self setFilterParam:self.seletedFliter];
}
```

## 如何从工程中删除美颜功能？
如果您的实际项目中用不到美颜相关功能，可参考以下说明进行删除
1. 工程文件中删除NLiteAVDemo/beauty文件夹
2. 去除podfile文件中对于美颜的SDk依赖，删除或者注释掉 pod 'Nama-lite', '7.3.2'内容。
3. 删除美颜SDK中初始化的内容
4. 删除美颜和滤镜相关的UI
