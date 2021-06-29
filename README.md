<p align="center">
![mbona](https://user-images.githubusercontent.com/63802031/123821864-702c9a80-d904-11eb-9c55-e0b0ca49c47c.png)
</p>

# swayy
[![Build Status](https://app.bitrise.io/app/c373b1aa540acc1c/status.svg?token=u-KpJIBnS_0TQUtBtYNEJQ&branch=master)](https://app.bitrise.io/app/c373b1aa540acc1c)
[![Playstore](https://img.shields.io/badge/Download-Playstore-brightgreen.svg)](https://play.google.com/store/apps/details?id=com.marknkamau.justjava)
[![latest build](https://img.shields.io/badge/Download-Latest%20build-brightgreen.svg)](https://barbet.marknjunge.com/justjava)

A buying and selling app.

The backend is written using Kotlin. See [swayy-api](https://github.com/GideonRotich/Swayy-Android).

## Features

- 100% Kotlin.
- Firebase Authentication
- Retrofit with Coroutines
- Room for local data storage.
- password Authentication.
- Firebase messaging for notifications.
- Picasso image library
- Shimmer layout

## Prerequisites

### Firebase Project

Used for messaging.

1. Create an project on [Firebase](https://console.firebase.google.com/).
2. Add an application `com.example.swayy`.


## Installation

1. Clone the repository.
2. Create a `keys.properties` file based on`keys.properties.sample`.
3. Add the `play-services.json` file from firebase to `./app`.
4. Open and build in Android Studio.

(optional) To overwrite the API url in debug mode, add `debugApiBaseUrl="http://10.0.2.2:3000"` to
local.properties. **Note:** If your url is not https, you will need to add it to
[network_security_config.xml](./core/src/main/res/xml/network_security_config.xml)

## Screenshots
![ten](https://user-images.githubusercontent.com/63802031/123820872-969e0600-d903-11eb-9b08-ce9d1653334b.png)





## Download

<a href='https://play.google.com/store/apps/details?id=com.marknkamau.justjava'>
​    <img alt='Get it on Google Play' 
​         src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'
​         height="116" width="300"/>
</a>
