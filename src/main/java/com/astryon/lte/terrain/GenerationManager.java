package com.astryon.lte.terrain;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class GenerationManager {


    private static final ExecutorService WORKERS =
            Executors.newFixedThreadPool(4);



    public static void queueChunk(
            int x,
            int z
    ){


        System.out.println(
                "[LTE] Queued chunk "
                + x + "," + z
        );



        WORKERS.submit(() -> {


            prepareChunk(x,z);


        });


    }



    private static void prepareChunk(
            int x,
            int z
    ){


        System.out.println(
                "[LTE Worker] Preparing chunk "
                + x + "," + z
        );


        try {

            Thread.sleep(50);

        } catch(Exception ignored){}



        System.out.println(
                "[LTE Worker] Finished chunk "
                + x + "," + z
        );


    }


}
