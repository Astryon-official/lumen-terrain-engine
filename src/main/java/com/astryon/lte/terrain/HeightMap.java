package com.astryon.lte.terrain;

public class HeightMap {

    private final int[][] heights;

    private final int minHeight;
    private final int maxHeight;
    private final double averageHeight;
    private final double variation;


    public HeightMap(
            int[][] heights,
            int minHeight,
            int maxHeight,
            double averageHeight,
            double variation
    ) {
        this.heights = heights;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.averageHeight = averageHeight;
        this.variation = variation;
    }


    public int getHeight(int x, int z) {
        return heights[x][z];
    }


    public int getMinHeight() {
        return minHeight;
    }


    public int getMaxHeight() {
        return maxHeight;
    }


    public double getAverageHeight() {
        return averageHeight;
    }


    public double getVariation() {
        return variation;
    }
}
