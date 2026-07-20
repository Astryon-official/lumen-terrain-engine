#include <jni.h>
#include "LTE.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_astryon_lte_gpu_LTENative_gpuProcessChunk(
    JNIEnv* env,
    jclass clazz,
    jint x,
    jint z
)
{
    LTE::ProcessChunk(
        x,
        z
    );
}
