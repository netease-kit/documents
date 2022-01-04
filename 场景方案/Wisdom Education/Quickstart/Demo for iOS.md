# Running the demo project

Wisdom Education is provided as a PaaS solution to the online interactive class scenario developed by CommsEase. Based on the typical scenarios of online education, CommsEase offers the open source project. Wisdom Education enables you to implement the one-to-one tutoring, breakout class, interactive large class, and live large class. You can also customize the sample code provided by CommsEase to suit your business requirements, or integrate the IM SDK, Audio & Video Call SDK, and Interactive Whiteboard SDK with your app project to deliver remote learning services.



This topic describes how to compile and implement the demo project developed for iOS apps. You can use typical features for online learning scenarios.

## Prerequisites

Before you start running the demo project, make sure you have completed the following operations:  
Contact CommsEase sales to get the following permissions, and contact technical support to configure products and services.

### The following services and message delivery are activated for this solution:

* [Create a project and activate required services](../Creating Projects and Activating Services.md)
* To configure the CommsEase console, see [Service Configuration](../Service Configuration.md)

## Considerations

- If you want to run the demo projects locally to try Wisdom Education, you can use the trial account. The trial account has the required permissions and message delivery service. The trial class duration lasts 30 minutes.
- The trial account is used only for trial and testing. Do not use the trial account in the production environment.

## Development environment
Before you run the demo project, make sure that the development environment meets the following requirements:

| Environment requirement | Description |
| -------- | -------------------------------------------------------- |
| iOS | v10.0 or later                                                |
| CPU Architecture | ARM64 and ARMV7 |
| IDE      | XCode                                                     |
| Others     | Install CocoaPods, and a device with iOS 9.0 or later. |

## Run the demo project

1. Get the demo project.
Download the demo project or sample code on the [Sample Code](https://netease.im/edu#page4) page.

2. Open the terminal in the folder where the Podfile is located, and run `pod install`.

3. After the installation is complete, open the `NLiteAVDemo.xcworkspace` project using Xcode.

4. Configure relevant fields in the demo project.

   If you want to develop your application based on the demo project, change the following fields in `keyCenter.m` with your information.

   | Configuration item | Description |
   | ------------- | ------------------------------------------ |
   | appId | The application ID. You can view the AppKey in the CommsEase console. |
   | authorization | The verification parameter in the request header used for requests to call the server APIs.     |

  > Note:
  > If you want to run the demo project locally, you can use the [trial account](https://github.com/netease-kit/WisdomEducation/tree/main/Wisdom_Education_Docs/Wisdom Cloud Class Experience Account.md). The class duration with the trial account lasts 30 minutes.

5. Select a device to use the demo app.     
   <image width="25%" src="../Images/enterroom.png">
   
## What’s next

If you have learned the features of Wisdom Education, you can try to integrate [Wisdom Education with your project](../Docs/README.md).

