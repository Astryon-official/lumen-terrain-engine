package com.astryon.lte.gpu;

public interface VulkanManager {

    boolean initialize();

    void shutdown();

    boolean isAvailable();

    String getGpuName();

    int getComputeQueueCount();

}
