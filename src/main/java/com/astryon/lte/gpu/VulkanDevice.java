package com.astryon.lte.gpu;

import java.util.UUID;

public class VulkanDevice {

    private String name;
    private String vendor;
    private String driver;
    private UUID uuid;

    private long memory;

    private boolean computeSupported;
    private boolean graphicsSupported;

    private int computeQueues;

    private double benchmarkScore;

}
