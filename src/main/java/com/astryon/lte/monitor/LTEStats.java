package com.astryon.lte.monitor;

public class LTEStats {

    private static long chunksCompleted = 0;
    private static long totalProcessingTime = 0;


    public static synchronized void chunkCompleted(long time) {

        chunksCompleted++;
        totalProcessingTime += time;

    }


    public static long getChunksCompleted() {

        return chunksCompleted;

    }


    public static long getAverageTime() {

        if (chunksCompleted == 0) {

            return 0;

        }

        return totalProcessingTime / chunksCompleted;

    }

}
