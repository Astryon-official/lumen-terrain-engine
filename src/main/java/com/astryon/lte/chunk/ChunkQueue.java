package com.astryon.lte.chunk;

import java.util.LinkedList;
import java.util.Queue;

public class ChunkQueue {

    private static final Queue<ChunkTask> queuedChunks = new LinkedList<>();

    private static final int MAX_QUEUE_SIZE = 512;


    public static void addChunk(int chunkX, int chunkZ) {

        if (queuedChunks.size() >= MAX_QUEUE_SIZE) {

            System.out.println("[LTE] Queue limit reached. Skipping chunk: "
                    + chunkX + ", " + chunkZ);

            return;

        }


        ChunkTask task = new ChunkTask(chunkX, chunkZ);


        queuedChunks.add(task);


        System.out.println("[LTE] Added chunk to queue: "
                + chunkX + ", " + chunkZ);

    }


    public static ChunkTask getNextChunk() {

        return queuedChunks.poll();

    }


    public static boolean contains(int chunkX, int chunkZ) {

        for (ChunkTask task : queuedChunks) {

            if (task.x == chunkX && task.z == chunkZ) {

                return true;

            }

        }

        return false;

    }


    public static int getSize() {

        return queuedChunks.size();

    }

}
