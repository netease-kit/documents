# 概述

## 产品介绍

网易会议NEMeeting SDK(以下简称SDK)提供了一套简单易用的接口，允许开发者通过调用 SDK提供的API，快速地集成音视频会议功能至现有应用中。为企业打造专属的会议能力，卓越的音视频性能，丰富的会议协作能力，坚实的会议安全保障，提升协作效率，满足大中小会议全场景需求。提供全套开放、简单、安全的视频会议服务。您可以使用进行远程音视频会议、在线协作、会管会控、会议录制、指定邀请、布局管理等。

## 功能特性

<table>
 <tr>
 	<td width="100px">功能分类</td>
	<td width="100px" >功能</td>
	<td>功能描述</td>
 </tr>
  <tr>
 	<td>基础功能 </td>
	<td>语音/视频通话</td>
	<td>支持一对一或多人间的语音/视频通话功能，并进行音视频实时切换。支持纯转发会议或者讨论式会议。</td>
  </tr>
  <tr>
    <td rowspan="3">协作功能 </td>
	<td>实时消息</td>
	<td>主持人和与会人在会议过程中发送实时文字消息进行互动。</td>
 </tr>
   <tr>
	<td>白板共享</td>
	<td>主持人在白板上书写，有助于提升协作效率；其他与会人也可使用白板与主持人进行实时互动。支持白板双指缩放大小及移动位置。</td>
 </tr>
   <tr>
	<td>屏幕共享</td>
	<td>主持人或与会人将自己屏幕的内容分享给其他与会人观看，提高沟通效率。</td>
 </tr>
  <tr>
    <td rowspan="3">管理功能 </td>
	<td>会议控制</td>
	<td>可区分主持人和与会人员角色权限，显示与会人员列表及音视频状态。主持人可以管理与会人在会议过程中发送音、视频的权限，例如全体静音、单独关闭某与会人员的摄像头或麦克风、移出房间、设置与会人员开启摄像头或麦克风需审批等。提供进出会议人员通知，可以设置房间超过预定人数后关闭通知。</td>
 </tr>
   <tr>
	<td>会议邀请</td>
	<td>一键获取会议名称、密码，邀请他人参与会议。</td>
 </tr>
   <tr>
	<td>视图切换</td>
	<td>可以设置演讲者视图或平铺视图。支持自动切换视频视图和音频视图。</td>
 </tr>
</table>

## 产品架构
1. 会议组件架构图

    <image width="60%" src="./images/meeting_sdk_structure.png">

   会议SDK全局接口，提供初始化、管理其他会议相关子服务的能力

2. 会议系统时序图   

    <image width="60%" src="./images/meeting_flow_chart.png">

## 体验应用
### 下载体验包
<table>
    <tr>
    <th width=100>平台</th>
    <th width=200>下载地址</th>
   </tr>
    <tr>
    <td >iOS</td>
    <td > <a href=https://apps.apple.com/cn/app/%E7%BD%91%E6%98%93%E4%BC%9A%E8%AE%AE/id1525524757>下载</a></td>
   </tr>
   <tr>
    <td>Android</td>
    <td> <a href=https://yx-web.nos-hz.163yun.com/package/meeting_mobile_online.apk >下载</a></td>
   </tr>
   <tr>
    <td>macOS</td>
    <td> <a href=https://yx-web.nos-hz.163yun.com/package/meeting_macos_online.dmg>下载</a></td>
   </tr>
   <tr>
    <td >Windows</td>
    <td><a href=https://yx-web.nos-hz.163yun.com/package/meeting_win_online.exe>下载</a></td>
   </tr>
   <tr>
    <td>TV</td>
      <td> <a href= https://yx-web.nos-hz.163yun.com/package/1610715275/meeting_tv_20210115160553_758967a_common.apk>下载</a></td>
   </tr>
</table>


### 效果展示
- 视频会议页面

    <image width="25%" src="./images/demo_meeting_camera.jpeg">

- 视频会议共享屏幕

    <image width="25%" src="./images/demo_meeting_share_screen.jpeg">

- 视频会议白板

    <image width="25%" src="./images/demo_meeting_whiteborad.jpeg">

- 视频会议聊天

    <image width="25%" src="./images/demo_meeting_chat.jpeg">

- 视频会议成员管理

    <image width="25%" src="./images/demo_meeting_user_list.jpeg">

- 视频会议其他功能

    <image width="25%" src="./images/demo_meeting_other.jpeg">

