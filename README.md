# AndroidLame

##lame
[lame 官网](http://lame.sourceforge.net/)
本项目主要方便大家在android上使用lame。  
Android使用AudioRecord录音时不支持mp3格式，需要先录制为raw格式，然后在使用lame转换成mp3

##引用方式
Maven

     <dependency>
         <groupId>mobi.cangol.mobile</groupId>
         <artifactId>lame</artifactId>
         <version>1.0.0/version>
         <type>aar</type>
     </dependency>
Gradle
 
    compile 'mobi.cangol.mobile:lame:1.0.0@aar'
##使用方法：

    LameUtils lameUtils = new LameUtils(1, 16000, 96);
    boolean result = lameUtils.raw2mp3(sourcePath, targetPath);