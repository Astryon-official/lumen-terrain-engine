package com.astryon.lte.terrain;

public class TerrainProfile {

    private final int minHeight;
    private final int maxHeight;
    private final double averageHeight;
    private final double variation;

    private final double roughness;
    private final double slope;
    private final double complexity;

    private final String type;

    public TerrainProfile(
            int minHeight,
            int maxHeight,
            double averageHeight,
            double variation,
            double roughness,
            double slope,
            double complexity,
            String type
    ) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.averageHeight = averageHeight;
        this.variation = variation;

        this.roughness = roughness;
        this.slope = slope;
        this.complexity = complexity;

        this.type = type;
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

    public double getRoughness() {
        return roughness;
    }

    public double getSlope() {
        return slope;
    }

    public double getComplexity() {
        return complexity;
    }

    public String getType() {
        return type;
    }


    public void print() {

        System.out.println("[LTE] Terrain Profile");
        System.out.println("Min Height: " + minHeight);
        System.out.println("Max Height: " + maxHeight);
        System.out.println("Average Height: " + averageHeight);
        System.out.println("Variation: " + variation);
        System.out.println("Roughness: " + roughness);
        System.out.println("Slope: " + slope);
        System.out.println("Complexity: " + complexity);
        System.out.println("Type: " + type);
    }
}
