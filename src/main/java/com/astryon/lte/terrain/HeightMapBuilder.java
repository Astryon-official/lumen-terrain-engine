package com.astryon.lte.terrain;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.block.state.BlockState;

public class HeightMapBuilder {

    public static HeightMap build(ChunkAccess chunk) {

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;

        double totalHeight = 0;
        int samples = 0;

        int[][] heights = new int[16][16];

        int startX = chunk.getPos().getMinBlockX();
        int startZ = chunk.getPos().getMinBlockZ();


        for (int x = 0; x < 16; x++) {

            for (int z = 0; z < 16; z++) {

                int surfaceY = findSurface(
                    chunk,
                    startX + x,
                    startZ + z
                );

                heights[x][z] = surfaceY;

                minHeight = Math.min(minHeight, surfaceY);
                maxHeight = Math.max(maxHeight, surfaceY);

                totalHeight += surfaceY;
                samples++;
            }
        }


        double average = totalHeight / samples;

        double variation = maxHeight - minHeight;


        return new HeightMap(
            heights,
            minHeight,
            maxHeight,
            average,
            variation
        );
    }


    private static int findSurface(
            ChunkAccess chunk,
            int x,
            int z
    ) {


	int top = 320;

        for (int y = top; y >= -64; y--) {

            BlockState state =
                chunk.getBlockState(
                    new BlockPos(x, y, z)
                );


            if (!state.isAir()) {
                return y;
            }
        }


        return -64;
    }
}
