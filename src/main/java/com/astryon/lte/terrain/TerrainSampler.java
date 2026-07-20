package com.astryon.lte.terrain;

import net.minecraft.world.level.chunk.ChunkAccess;

public class TerrainSampler {

    public static HeightMap sample(ChunkAccess chunk) {

        return HeightMapBuilder.build(chunk);

    }

}
