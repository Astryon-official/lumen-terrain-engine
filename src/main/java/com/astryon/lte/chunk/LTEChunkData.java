package com.astryon.lte.chunk;

public class LTEChunkData {

    public final int x;
    public final int z;

    /*
     * 16x16 height values for the chunk.
     * One value per X/Z column.
     */
    public final int[] heightmap;

    /*
     * Placeholder for future biome information.
     */
    public int[] biomeIds;

    /*
     * Indicates whether CPU preparation has finished.
     */
    public boolean cpuPrepared;

    /*
     * Indicates whether GPU processing has finished.
     */
    public boolean gpuProcessed;


    public LTEChunkData(int x, int z) {

        this.x = x;
        this.z = z;

        this.heightmap = new int[16 * 16];

        this.biomeIds = null;

        this.cpuPrepared = false;
        this.gpuProcessed = false;

    }

}
