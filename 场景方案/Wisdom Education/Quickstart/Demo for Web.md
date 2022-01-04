# Running the Demo Project

Wisdom Education is provided as a PaaS solution to the online interactive class scenario developed by CommsEase. Based on the typical scenarios of online education, CommsEase offers the open source project. Wisdom Education enables you to implement the one-to-one tutoring, breakout class, interactive large class, and live large class. You can also customize the sample code provided by CommsEase to suit your business requirements, or integrate the IM SDK, Audio & Video Call SDK, and Interactive Whiteboard SDK with your app project to deliver remote learning services.

This topic describes how to compile and implement the demo project developed for Web services. You can use typical features for online learning scenarios.

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
| ---------------- | ------------------------------------------------------------ |
| Security | HTTPS or local connection localhost/127.0.0.1 is available.                                          |
| Browser| Chrome 74 and later. Safari 12 and later.                               |
| Node | Not earlier than v12. v14.16.0 is recommended. |

## Run the Web sample code

1. Get the demo project.
Download the demo project or sample code on the [Sample Code](https://netease.im/edu#page4) page.

2. Configure relevant fields in the demo project.

    If you want to develop your application based on the demo project, change the following fields in `.env.development` or `.env.production` with your information.

    | Configuration item  | Description |
    | ------------- | ------------------------------------------- |
    | REACT_APP_SDK_APPKEY        | The AppKey for the project. You can view the AppKey in the CommsEase console. |
    | REACT_APP_SDK_AUTHORIZATION | The verification parameter in the request header used for requests to call the server APIs. |
    
  > Note:
  > If you want to run the demo project locally, you can use the [trial account](https://github.com/netease-kit/WisdomEducation/tree/main/Wisdom_Education_Docs/Wisdom Cloud Class Experience Account.md). The class duration with the trial account lasts 30 minutes.

3. Navigate into the `Wisdom_Educaiton_Web` directory, install dependencies and start the project.

    ```
    cd Wisdom_Educaiton_Web
    npm install               // install dependencies
    npm start         // start the development environment
    ```
    After running the command, you can use the demo app.
    <image width="70%" src="../Images/web_login.png">
    
4. Build the project.

    ```
    npm run start:pro         // Start the production environment
    npm run build:dev         // Build the testing environment
    npm run build             // Build the production environment
    ```
