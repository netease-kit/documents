
In a live large class, one teacher delivers lessons in a class and thousands of students watch the session online. In the live large class, teachers can stream the class using audio & video calls, whiteboard, and screen sharing. Students watch the live class by pulling the stream from the CDN with low latency. The mode is suitable for large-scale live interactive teaching activities.

The typical use cases of live large class include various large-scale live online courses such as K12 education, language training for international students, vocational skills training, exam-oriented courses, and art courses.

## Architecture


![](https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdoc%2FSolutions-WisdomEducation-Arch02.png)

## Features

<style>
table th:first-of-type {
    width: 15%;
}
table th:nth-of-type(2) {
    width: 15%;
}
table th:nth-of-type(3) {
    width: 50%;
}
</style>

| Category | Feature | Description|
| -------- | -------------- | --------------------------------------------------------------------------------------------------------------------- |
| Pre-class preparation | Class management | View class information, such as class number and name.                                                                   |
|  ^^  | Audio and video equipment tuning| The teacher can tune audio and video equipment by switching audio capture device, adjusting the focus of the camera and exposure and more. Make sure that the equipment is up and running before the class starts. |
| Class management | Real-time audio and video interaction | - Students can watch or listen to the class delivered using lecturer's audio and video in real time.           |\
| | | - Students can request to turn on microphone speak with the lecturer.            |\
| | | - All students can watch or listen to the audio and video interaction between the lecturer and student.            |
|  ^^  | Real-time messaging | Teachers and students can send or receive text messages, image messages in real time in the class. Students can ask questions online.                   |
|  ^^  | Real-time whiteboard | Teachers can use whiteboard to draw as required. The whiteboard feature supports handwriting such as brushes, graphics, text, laser pointers and more. Students can also draw freely using whiteboard in class. |
|  ^^  | Class status management| Teachers can control the start or end of the class.                                                                         |
|  ^^  | Courseware demonstration| Teachers can share lecture courseware or documents in Word, PPT and other formats online in real time. AI-enabled automatic high-definition rendering is applied. |
|  ^^  | Screen sharing| Teachers can share the desktop with students in real time using screen sharing. This improves teaching efficiency.             |
| Student management | Student engagement management | Teachers can manage the microphone and camera status of students during class.                                                 |
| ^^ | Real-time messaging | Students can send or receive text messages, image messages in real time in the class.                                     |
| ^^ | group chat interaction | Multiple students can interact with the teacher using audio and video devices.                                                       |
| Post-class review | Recording and playback | Class activities such as real-time audio and video interaction, whiteboard display, and screen sharing during the class will be recorded and stored in the cloud. Teachers and students can view replays at any time. |
|   ^^   | Key frames capture | Supplementary Enhancement Information (SEI) during class is inserted. Playbacks can quickly load key frames using SEI to generate highlights. Interactions and key content can be retrieved quickly. |
| Extra features | - | You can also add more features, such as beautify filter, voice changer, AI-enabled noise cancellation, and AI-enabled background replacement.                   |