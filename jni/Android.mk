LOCAL_PATH := $(call my-dir)
 
# Create BitmapUtils library
 
include $(CLEAR_VARS)
 
LOCAL_LDLIBS    := -llog -ljnigraphics
 
LOCAL_MODULE    := blur
LOCAL_MODULE_TAGS	:= optional
LOCAL_SRC_FILES := blur.c
 
LOCAL_CFLAGS    =  -ffast-math -O3 -funroll-loops
 
include $(BUILD_SHARED_LIBRARY)

# Second 
include $(CLEAR_VARS)

# (最终so文件名是libfirst_jni.so）

LOCAL_MODULE    := first_jni 

# (C代码)
LOCAL_SRC_FILES := ndkdemo.c 
include $(BUILD_SHARED_LIBRARY)