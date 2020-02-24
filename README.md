# MyApplication
fastble功能，
pcm转MP3,
百度文字转语音



pcm转MP3，先修改jni目录下Android.mk里最后.c文件名为自己的文件名，
然后将jni 下.c和.h文件里的路径都换成自己项目目录下文件，注意包名
.换成_，完成后切换至jni目录下，执行ndk-build clean后，再执行ndk-build
编译出来的so就再obj目录下，就可以使用了。