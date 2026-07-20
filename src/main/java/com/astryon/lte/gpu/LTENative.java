package com.astryon.lte.gpu;

public class LTENative {

    static {
        System.loadLibrary("LumenTerrainEngine");
    }

    public static native void gpuProcessChunk(
            int x,
            int z
    );
}
