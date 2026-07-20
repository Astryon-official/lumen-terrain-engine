package com.astryon.lte.core;

import com.astryon.lte.chunk.ChunkProcessor;

public class LTEWorker extends Thread {

    private volatile boolean running = true;

    public LTEWorker() {
        super("LTE-Worker");
    }

    @Override
    public void run() {

        System.out.println("[LTE] Worker thread started.");

        while (running) {

            ChunkProcessor.process();

            try {
                Thread.sleep(50); // About 20 times per second
            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println("[LTE] Worker thread stopped.");
    }

    public void shutdown() {
        running = false;
    }
}
