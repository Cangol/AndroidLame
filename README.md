# AndroidLame
[ ![Download](https://api.bintray.com/packages/cangol/maven/AndroidLame/images/download.svg) ](https://bintray.com/cangol/maven/AndroidLame/_latestVersion)
[![Build Status](https://travis-ci.org/Cangol/AndroidLame.svg?branch=master)](https://travis-ci.org/Cangol/AndroidLame)

# lame
[lame 官网](http://lame.sourceforge.net/)
本项目主要方便大家在android上使用lame。  
Android使用AudioRecord录音时不支持mp3格式，需要先录制为raw格式，然后在使用lame转换成mp3

## 引用方式
Maven

      <dependency>
          <groupId>mobi.cangol.mobile</groupId>
          <artifactId>lame</artifactId>
          <version>1.0.1</version>
          <type>pom</type>
      </dependency>
Gradle

      compile 'mobi.cangol.mobile:lame:1.0.1'
直接下载jar和so文件使用
    
     https://github.com/Cangol/AndroidLame/tree/master/downloads
## 使用方法

    LameUtils lameUtils = new LameUtils(1, 16000, 96);
    boolean result = lameUtils.raw2mp3(sourcePath, targetPath);
