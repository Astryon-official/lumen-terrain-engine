package com.astryon.lte.monitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LTEMonitor {


    private static final File LOG_FILE =
            new File("logs/lumen-terrain-engine/performance.log");


    public static void writeStats(int queueSize) {

        try {

            LOG_FILE.getParentFile().mkdirs();


            FileWriter writer = new FileWriter(LOG_FILE, true);


            writer.write(
                    "[LTE Stats] "
                    + "Chunks: " + LTEStats.getChunksCompleted()
                    + " | Queue: " + queueSize
                    + " | Avg Time: "
                    + LTEStats.getAverageTime()
                    + "ms\n"
            );


            writer.close();


        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
