package com.astryon.lte.terrain;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTracker {

    private final Map<UUID, ChunkPosData> players = new HashMap<>();


    public void updatePlayer(ServerPlayer player) {

        UUID uuid = player.getUUID();

        int chunkX = player.chunkPosition().x();
        int chunkZ = player.chunkPosition().z();


        ChunkPosData old = players.get(uuid);


        if (old == null ||
                old.x != chunkX ||
                old.z != chunkZ) {


            players.put(uuid,
                    new ChunkPosData(chunkX, chunkZ));


            System.out.println(
                    "[LTE] Player "
                    + player.getName().getString()
                    + " moved to chunk "
                    + chunkX + "," + chunkZ
            );


            ChunkPredictor.predict(
                    player,
                    chunkX,
                    chunkZ
            );
        }
    }



    private static class ChunkPosData {

        int x;
        int z;


        ChunkPosData(int x,int z){
            this.x=x;
            this.z=z;
        }
    }
}
