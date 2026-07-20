package com.astryon.lte.chunk;

import com.astryon.lte.terrain.TerrainAnalyzer;
import net.minecraft.world.level.chunk.ChunkAccess;

public class ChunkInjector {

    public static void processChunk(ChunkAccess chunk) {

        int x = chunk.getPos().x();
        int z = chunk.getPos().z();

        System.out.println(
            "[LTE] Injecting chunk data: "
            + x + ", " + z
        );

        TerrainAnalyzer.analyze(chunk);
    }
}