## 概述

云视频客服 Rest API 是提供给开发者接入云视频客服开放平台的入口。
开发者可以通过云视频客服 API 进行二次开发，例如登录。

## 变更记录

| 日期 | 版本 | 变更内容 |
| :------: | :------: | :------: |
| 2021-09-27 | 0.0.1 | 初稿 |

## 准备工作

接入云视频客服 Saas 服务前需要联系客服获取 AppKey

## API 网关

网关地址：


## 签名验证

Rest API对每个访问请求进行身份验证，验证失败的请求无法调用API接口。

签名用的请求头参数

| 参数 | 类型 | 必选 | 描述 |
| :------: | :------: | :------: | :------: |
| AppKey  | String | 是 | 平台分配的应用appkey |
| Nonce | String | 是 | 随机8位Integer正整数（最大长度128个字符）,例如12345678 |
| CurTime | String| 是 | 当前 UNIX 时间戳，可记录发起 API 请求的时间。例如1594639036，单位为秒。注意：如果与服务器时间相差超过1分钟，会引起签名过期错误。 |
| CheckSum | String | 是 | SHA1(AppSecret + Nonce + CurTime)，三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)，注意AppSecret为AppKey对应的秘钥 [签名代码](#sign_code) |

## 公共响应参数
| 参数 | 类型 | 描述 |
| :------: | :------: | :------: |
| code  | Integer | 状态码，200：表示请求处理成功 |
| msg | String | 错误信息 |
| requestId | String | 请求流水号 |
| costTime | String | 请求响应时间，单位：毫秒 |
| ret | Object | 响应数据，业务参数都是放在这个对象内的 |

## Rest APIs

### 会议账号创建

1. 接口描述  
    注册创建一个会议账号
    
2. 接口请求地址
    ```
    POST https://{host}/v1/account/create HTTP/1.1
    Content-Type: application/json;charset=utf-8
    ```
3. 输入参数

    | 参数 | 类型 | 必选 | 描述 |
    | :------: | :------: | :------: | :------: |
    | imAccid  | String | 否 | 复用的imAccid |
    | imToken  | String | 否 | 复用的imAccid的Token |

Request Body示例
```json
{
  "imAccid": "abcdefghijk",
  "imToken": "qwer1234"
}
```

4. 输出参数

    `以下是公共响应参数的ret属性内包含的参数`
    
    | 参数 | 类型 | 描述 |
    | :------: | :------: | :------: |
    | accountId | String | 会议用户账号ID |
    | accountToken | String | 会议用户账号令牌 |


### 通过ImAccid查询会议账号

1. 接口描述  
   通过ImAccid查询本企业下的一个账号

2. 接口请求地址
    ```
    POST https://{host}/v1/account/getByImAccid HTTP/1.1
    Content-Type: application/json;charset=utf-8
    ```
3. 输入参数

   | 参数 | 类型 | 必选 | 描述 |
   | :------: | :------: | :------: | :------: |
   | imAccid  | String | 是 | imAccid |

Request Body示例
```json
{
  "imAccid": "abcdefghijk"
}
```

4. 输出参数

   `以下是公共响应参数的ret属性内包含的参数`

   | 参数 | 类型 | 描述 |
   | :------: | :------: | :------: |
   | accountId | String | 会议用户账号ID |
   | accountToken | String | 会议用户账号令牌 |


### 通过AccountId查询会议账号

1. 接口描述  
   通过AccountId查询本企业下的一个账号

2. 接口请求地址
    ```
    POST https://{host}/v1/account/getByAccountId HTTP/1.1
    Content-Type: application/json;charset=utf-8
    ```
3. 输入参数

   | 参数 | 类型 | 必选 | 描述 |
   | :------: | :------: | :------: | :------: |
   | accountId  | String | 是 | accountId |

Request Body示例
```json
{
  "accountId": "abcdefghijk"
}
```

4. 输出参数

   `以下是公共响应参数的ret属性内包含的参数`

   | 参数 | 类型 | 描述 |
   | :------: | :------: | :------: |
   | accountId | String | 用户账号ID |
   | accountToken | String | 用户账号令牌 |

###### 
## 签名代码Demo

<span id="sign_code" />

```java

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSumBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CheckSumBuilder.class);

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String getCheckSum(String nonce, String curTime, String appSecret) {
        String plaintext = appSecret + nonce + curTime;
        return encode(plaintext, "SHA1");
    }

    public static String getCheckSum(String nonce, String curTime, String appSecret, String data) {
        String plaintext = appSecret + nonce + curTime + data;
        return encode(plaintext, "SHA1");
    }

    // 计算并获取md5值
    public static String getMD5(String requestBody) {
        return encode(requestBody, "md5");
    }

    private static String encode(String plaintext, String method) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(method);
            messageDigest.update(plaintext.getBytes(StandardCharsets.UTF_8));
            return getFormattedText(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("Encode error {} msg {}", plaintext, e.getMessage());
        }

        return "";
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    protected static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (byte aByte : bytes) {
            buf.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return buf.toString();
    }
}

```
