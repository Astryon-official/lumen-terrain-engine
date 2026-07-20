package com.astryon.lte.chunk;

import com.astryon.lte.terrain.TerrainProcessor;
import com.astryon.lte.core.LTEStats;
import com.astryon.lte.compute.LumenChunkComputeData;

public class ChunkProcessor {

    private static final int MAX_CHUNKS_PER_TICK = 4;


    public static void process() {

        int processed = 0;


        while (processed < MAX_CHUNKS_PER_TICK) {

            ChunkTask task = ChunkQueue.getNextChunk();

            if (task == null) {
                break;
            }


            System.out.println(
                "[LTE] Preparing chunk: "
                + task.x + ", " + task.z
            );


            // CPU TERRAIN PREPARATION

            task.state = ChunkState.PREPARING_CPU;


            System.out.println(
                "[LTE] CPU preparation started: "
                + task.x + ", " + task.z
            );


	LumenChunkComputeData computeData =
        	new LumenChunkComputeData(task.data);

	TerrainProcessor.prepare(computeData);


            // GPU PROCESSING STAGE

            task.state = ChunkState.PROCESSING_GPU;


            System.out.println(
                "[LTE] GPU processing started: "
                + task.x + ", " + task.z
            );


            // GPU processing will be connected here in 2.0.0
            task.data.gpuProcessed = true;



            // COMPLETE

            task.state = ChunkState.COMPLETE;


            CompletedChunkCache.markCompleted(task.x, task.z);

            LTEStats.chunkCompleted();


            System.out.println(
                "[LTE] Chunk completed: "
                + task.x + ", " + task.z
            );


            try {

                Thread.sleep(5);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

            }


            processed++;

        }

    }

}
