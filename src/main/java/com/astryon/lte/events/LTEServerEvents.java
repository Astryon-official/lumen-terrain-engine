package com.astryon.lte.events;

import com.astryon.lte.chunk.ChunkPredictor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class LTEServerEvents {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (var player : server.getPlayerList().getPlayers()) {

                ChunkPredictor.predictPlayer(player);

            }

        });

    }
}
