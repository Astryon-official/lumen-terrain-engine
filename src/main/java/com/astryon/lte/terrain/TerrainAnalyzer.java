package com.astryon.lte.terrain;

import net.minecraft.world.level.chunk.ChunkAccess;

public class TerrainAnalyzer {

    public static void analyze(ChunkAccess chunk) {

        long start = System.nanoTime();

        System.out.println(
            "[LTE] Analysing terrain: "
            + chunk.getPos().x()
            + ", "
            + chunk.getPos().z()
        );


        HeightMap map = HeightMapBuilder.build(chunk);


        int minHeight = map.getMinHeight();
        int maxHeight = map.getMaxHeight();

        double averageHeight = map.getAverageHeight();

        double variation = maxHeight - minHeight;


        // CPU v2 metrics (temporary calculations)
        double roughness = variation / 10.0;

        double slope = Math.atan(variation / 16.0);

        double complexity =
                (roughness * 50.0)
                + (slope * 50.0);


        String type;

        if (variation < 3) {
            type = "FLAT";
        }
        else if (variation < 15) {
            type = "ROLLING";
        }
        else {
            type = "MOUNTAIN";
        }


        TerrainProfile profile = new TerrainProfile(
                minHeight,
                maxHeight,
                averageHeight,
                variation,
                roughness,
                slope,
                complexity,
                type
        );


        profile.print();


        long end = System.nanoTime();

        System.out.println(
            "[LTE] Terrain analysis completed in "
            + ((end - start) / 1_000_000.0)
            + " ms"
        );
    }
}
