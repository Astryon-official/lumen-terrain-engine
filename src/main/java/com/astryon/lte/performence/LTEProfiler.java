package com.astryon.lte.performance;

public class LTEProfiler {

    private static long chunksProcessed = 0;
    private static long totalTime = 0;

    public static void recordChunk(long time) {
        chunksProcessed++;
        totalTime += time;
    }

    public static void printStats() {

        if (chunksProcessed == 0) {
            return;
        }

        long average = totalTime / chunksProcessed;

        System.out.println(
            "[LTE] Chunks: "
            + chunksProcessed
            + " Average processing time: "
            + average
            + "ms"
        );
    }
}