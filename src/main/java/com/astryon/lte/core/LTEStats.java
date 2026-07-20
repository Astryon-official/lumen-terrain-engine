package com.astryon.lte.core;

public class LTEStats {

    private static long completedChunks = 0;


    public static void chunkCompleted() {

        completedChunks++;

    }


    public static long getCompletedChunks() {

        return completedChunks;

    }


    public static void reset() {

        completedChunks = 0;

    }

}
