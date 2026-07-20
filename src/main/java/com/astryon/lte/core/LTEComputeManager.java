package com.astryon.lte.core;

import com.astryon.lte.terrain.TerrainProfile;
import com.astryon.lte.terrain.TerrainProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LTEComputeManager {

    private static final int THREADS =
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2);


    private static final ExecutorService CPU_POOL =
            Executors.newFixedThreadPool(THREADS);



    public static void submitTerrainTask(
            int chunkX,
            int chunkZ,
            TerrainProfile profile
    ) {


        CPU_POOL.submit(() -> {

            long start = System.nanoTime();


            System.out.println(
                "[LTE] CPU COMPUTE START: "
                + chunkX
                + ", "
                + chunkZ
            );


            /*
             * Send terrain data into
             * the CPU processing pipeline.
             */
	System.out.println(
    	"[LTE] Legacy compute path bypassed"
	);


            long end = System.nanoTime();


            double ms =
                    (end - start) / 1_000_000.0;


            System.out.println(
                "[LTE] CPU COMPUTE COMPLETE: "
                + chunkX
                + ", "
                + chunkZ
                + " ("
                + ms
                + " ms)"
            );


        });

    }



    public static void shutdown() {

        CPU_POOL.shutdown();

    }

}
