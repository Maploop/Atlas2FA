
# Atlas2FA API

![badge](https://img.shields.io/github/v/release/Maploop/Atlas2FA)
[![](https://jitpack.io/v/Maploop/Atlas2FA.svg)](https://jitpack.io/#Maploop/Atlas2FA)

Advanced 2FA Library for java
[Java Docs](https://maploop.github.io/Atlas2FA/)

## Getting started
#### Import Atlas 2FA into your project

[![](https://jitpack.io/v/Maploop/Atlas2FA.svg)](https://jitpack.io/#Maploop/Atlas2FA)

Replace **Tag** with the version you see above this line
<details>
    <summary>Maven</summary>

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
   <groupId>com.github.Maploop</groupId>
   <artifactId>Atlas2FA</artifactId>
   <version>Tag</version>
</dependency>
```
</details>
<details>
    <summary>Gradle</summary>

```
implementation 'com.github.Maploop:Atlas2FA:Tag'
```
</details>

## Generating a QR Code
```java
TwoFactorAuth auth = new TwoFactorAuth("clientName").label("username of your user");

// You need to save this secret key that's generated for your user
String secretKey = auth.generateSecret();

// After all of that we need to use the simple method to generate the QR Code
String imageURL = auth.generateQRCode();
BufferedImage asImage = auth.generateQRCodeAsImage();

// You can also get a scaled instance of the BufferedImage like this (width, height)
BufferedImage asImage = auth.generateQRCodeAsImage(500, 500);
```

## Validating a 6-digit Code
```java
// The 6-digit code the user provides
String code = "255656";

// Secret must be the secret you saved for this user once you generated it
// Code can be a string or an integer
boolean isValidCode = TwoFactorAPI.isValidKey(secret, code);
```
