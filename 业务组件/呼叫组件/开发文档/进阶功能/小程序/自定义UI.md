## 自定义UI

#### 因为小程序是引入组件代码，所以可以通过组件的wxml文件和wxss文件直接修改UI

**示例：**

> 建议通过添加自定义class的方式去覆盖原生样式内容

```
// ../components/nertc-call/nertc-call.wxml
<cover-view class="tools custom-tools">
  ...
</cover-view>

// ../components/nertc-call/nertc-call.wxss
.custom-tools {
    background-color: #fff; // 修改背景色
    border-radius: 4rpx; // 修改圆角
}
```
