package com.astryon.lte.chunk;

import net.minecraft.server.level.ServerPlayer;

public class ChunkPredictor {


    public static void predictPlayer(ServerPlayer player) {


        int chunkX = (int) player.getX() >> 4;
        int chunkZ = (int) player.getZ() >> 4;


	for (int x = -4; x <= 4; x++) {

    	for (int z = -4; z <= 4; z++) {


                int predictedX = chunkX + x;
                int predictedZ = chunkZ + z;


                if (CompletedChunkCache.isCompleted(predictedX, predictedZ)) {
                    continue;
                }


                if (!ChunkQueue.contains(predictedX, predictedZ)) {

                    ChunkQueue.addChunk(predictedX, predictedZ);

                }

            }

        }

    }

}
