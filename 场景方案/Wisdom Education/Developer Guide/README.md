# Overview

## Scenarios overview

Wisdom Education is the solution to the online interactive class scenario provided by CommsEase. The solution integrates the IM SDK, Audio & Video Call SDK and Interactive Whiteboard SDK. Multiple class modes are provided based on the need of the remote learning scenario.

Wisdom Education is built for online tutoring and offers different class types for teaching scenarios, Clients from different platforms can access the service. Remote learning features, such as cloud recording, whiteboard interaction, and active users analytics are supported. In addition to the basic real-time interactive online teaching functionalities, Wisdom Education also allows you to add flexible extensions and helps quick deployment of your dedicated interactive teaching platform.

Wisdom Education provides four types of class modes: One-to-one tutoring, breakout class, interactive large class, and live large class. Wisdom Education can meet general needs of the remote learning industry and is suitable for K12 education, online tutoring, music training, language training for overseas study and more.

## Class modes

Wisdom Education supports the following class modes, and you can also design a mode based on actual business requirements.

- One-to-one tutoring

    One-to-one tutoring allows a teacher to perform online exclusive tutoring for one student. Teachers and students can interact with each other in real time using audio & video calls.
- Breakout class

    One-to-N interactive breakout class allows a teacher to give lectures online for multiple students. Two to six students can join a class.  In most cases, 1 to 2, 1 to 4, and 1 to 6 are the popular use cases. Teachers give lectures online and students can speak and ask questions in real time, Students can also interact with each other in real time.
- Interactive large class

    In an interactive large class, one teacher delivers lessons in a class and thousands of students watch the session online. Students can raise their hands to request speaking on stage. Students and teachers can interact with each other in real time using audio & video calls.
- Live large classes

    In a live large class, one teacher delivers lessons in a class and thousands of students watch the session online. The teacher streams audio and video content and the students pull the stream to watch the class. This method benefits low cost but comes with a slight delay. The mode allows teachers to deliver live interactive lessons with whiteboard and screen sharing. Teachers and students can also chat with each other.

## Architecture

### One-to-one tutoring, breakout class, and interactive large class

![](https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdoc%2FSolutions-WisdomEducation-Arch01.png)

One-to-one tutoring, breakout class, and interactive large class use audio & video calls for interaction. Teachers and students must join the audio and video room and interactive whiteboard room, Screen sharing is delivered in a substream. Teachers and students can interact using audio and video calls, interactive whiteboard, and screen sharing in real time.


### Live large class

![](https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdoc%2FSolutions-WisdomEducation-Arch02.png)

Only the teacher will join the audio & video room for a live large class. Students can watch the live content, such as audio and video stream, whiteboard, and screen sharing in stream-pull mode. Teachers and students can join the chat room and interact with each other.

## Features

<style>
table th:first-of-type {
    width: 30%;
}
</style>

| Feature | Description |
|:---|:---|
| Audio & Video Call | Teachers and students can send and receive audio and video streams. |
| Screen sharing | Teachers and students can share their screens, windows or browser interfaces during the class. |
| Interactive whiteboard| The interactive whiteboard can be used in online education, vocational education, K12 academic tutoring and more. Teachers can use teaching aids such as drawing, text, straight lines, and laser pointers to outline the main points when the courseware is displayed. Developers can use the interactive drawing, real-time synchronization, courseware sharing, recording and playback features provided by interactive whiteboard to meet the needs of online education with low-latency and intensive interaction. |
| Chat interaction | Teachers and students can send text and image messages to interact in the class. |
| Raising hands | In an interactive large class, students cannot send audio and video streams or operate the whiteboard by default. Students can raise their hands to request for speaking. If the teacher agrees, they can send the stream and interact with the teacher. |
| The teacher’s access control over students| The teacher has privilege control over students, including granting or revoking the whiteboard editing permissions, granting or revoking the screen sharing permissions, enabling or disabling audio and video, muting all students, and banning all chats. |
| Custom class UI | Wisdom Education provides UIKit. Developers can create a custom UI for the app using UIKit, or build the proprietary UI. |
| Recording and playback| During the class, the server will start recording the audio and video content, whiteboard content, and screen sharing content. When the class ends, students can watch the recorded content for review after class. |


## Platform support

| Role | Android | iOS | Web | macOS | Windows |
|---|:---:|:---:|:---:|:---:|:---:|
| Teacher client | √ | √ | √ | √ | √ |
| Student client | √ | √ | √ | √ | √ |

::: note note
The macOS and Windows clients are developed using Electron.
:::

