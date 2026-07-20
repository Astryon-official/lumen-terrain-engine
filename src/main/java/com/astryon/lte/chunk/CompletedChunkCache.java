package com.astryon.lte.chunk;

import java.util.concurrent.ConcurrentHashMap;

public class CompletedChunkCache {

    private static final ConcurrentHashMap<String, Boolean> completed =
            new ConcurrentHashMap<>();


    public static void markCompleted(int x, int z) {

        completed.put(x + "," + z, true);

    }


    public static boolean isCompleted(int x, int z) {

        return completed.containsKey(x + "," + z);

    }


    public static int getSize() {

        return completed.size();

    }

}
