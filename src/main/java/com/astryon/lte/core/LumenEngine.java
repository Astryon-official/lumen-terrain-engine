package com.astryon.lte.core;

public class LumenEngine {

    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) {
            return;
        }

        initialized = true;

        System.out.println("[LTE] Lumen Terrain Engine Core initialized.");
        System.out.println("[LTE] Terrain processing systems ready.");
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
