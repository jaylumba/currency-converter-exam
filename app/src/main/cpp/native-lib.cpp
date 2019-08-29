#include <jni.h>
#include <string>

extern "C" {
    JNIEXPORT jstring JNICALL
    Java_com_jcl_exam_emapta_application_MyApplication_getSecretKey(
            JNIEnv *env,
            jobject /* this */) {
        std::string secretKey = "3E83203809A94F2D083793BB924FC75DB55FBA9691CC3AEBB9C49FC44BE5B413";
        return env->NewStringUTF(secretKey.c_str());
    }

    JNIEXPORT jstring JNICALL
    Java_com_jcl_exam_emapta_di_AppModule_getBaseUrl(
            JNIEnv *env,
            jobject /* this */) {
        std::string baseUrl = "http://api.evp.lt/";
        return env->NewStringUTF(baseUrl.c_str());
    }


}

