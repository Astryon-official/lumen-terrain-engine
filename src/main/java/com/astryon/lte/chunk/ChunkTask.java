package com.astryon.lte.chunk;

public class ChunkTask {

    public final int x;
    public final int z;

    public ChunkState state;

    public final LTEChunkData data;

    public ChunkTask(int x, int z) {

        this.x = x;
        this.z = z;

        this.state = ChunkState.PREDICTED;

        this.data = new LTEChunkData(x, z);

    }

}
