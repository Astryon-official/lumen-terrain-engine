package com.astryon.lte.terrain;


import net.minecraft.server.level.ServerPlayer;


public class ChunkPredictor {


    private static final int PREDICTION_DISTANCE = 8;



    public static void predict(
            ServerPlayer player,
            int chunkX,
            int chunkZ
    ){


        System.out.println(
                "[LTE] Predicting future chunks for "
                + player.getName().getString()
        );


        // simple forward prediction
        // we will add velocity later


        for(int i = 1; i <= PREDICTION_DISTANCE; i++){


            int futureX = chunkX + i;


            GenerationManager.queueChunk(
                    futureX,
                    chunkZ
            );


        }


    }

}
