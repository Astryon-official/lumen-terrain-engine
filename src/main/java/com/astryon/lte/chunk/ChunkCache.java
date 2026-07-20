package com.astryon.lte.chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkCache {

    private static final Map<Long, LTEChunkData> CACHE =
            new ConcurrentHashMap<>();


    public static void store(LTEChunkData data) {

        CACHE.put(
            getKey(data.x, data.z),
            data
        );

    }


    public static LTEChunkData get(int x, int z) {

        return CACHE.get(getKey(x, z));

    }


    public static boolean contains(int x, int z) {

        return CACHE.containsKey(getKey(x, z));

    }


    public static int size() {

        return CACHE.size();

    }


    private static long getKey(int x, int z) {

        return ((long)x << 32) ^ (z & 0xffffffffL);

    }

}
