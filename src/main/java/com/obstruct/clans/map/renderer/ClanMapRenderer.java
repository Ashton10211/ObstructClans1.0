package com.obstruct.clans.map.renderer;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.map.MapManager;
import com.obstruct.clans.map.data.ChunkData;
import com.obstruct.clans.map.data.MapSettings;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ClanMapRenderer extends MapRenderer {

    private final MapManager mapManager;

    public ClanMapRenderer(MapManager mapManager) {
        super(true);
        this.mapManager = mapManager;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.MAP) {
            return;
        }
        MapSettings mapSettings = this.mapManager.getMapSettings().get(player.getUniqueId());
        MapSettings.Scale s = mapSettings.getScale();

        boolean hasMoved = this.mapManager.hasMoved(player);
        if (!hasMoved && !mapSettings.isUpdate()) {
            return;
        }

        MapCursorCollection cursors = mapCanvas.getCursors();
        while (cursors.size() > 0) {
            cursors.removeCursor(cursors.getCursor(0));
        }

        int scale = 1 << s.getValue();

        int centerX = player.getLocation().getBlockX();
        int centerZ = player.getLocation().getBlockZ();

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                mapCanvas.setPixel(i, j, mapCanvas.getBasePixel(i, j));
            }
        }

        if (s == MapSettings.Scale.FAR) {
            centerX = 0;
            centerZ = 0;
        }

        ClanManager manager = this.mapManager.getManager(ClanManager.class);

        for (ChunkData chunkData : mapSettings.getClanMapData()) {
            if (!chunkData.getWorld().equals(player.getWorld().getName())) {
                continue;
            }
            Clan clan = manager.getClan(chunkData.getClan());
            if (clan == null) {
                continue;
            }
            int bx = chunkData.getX() << 4;
            int bz = chunkData.getZ() << 4;

            int pX = (bx - centerX) / scale + 64;
            int pZ = (bz - centerZ) / scale + 64;

            boolean admin = clan.isAdmin();

            for (int cx = 0; cx < 16 / scale; cx++) {
                for (int cz = 0; cz < 16 / scale; cz++) {
                    if (pX + cx >= 0 && pX + cx < 128 && pZ + cz >= 0 && pZ + cz < 128) {
                        if (s.ordinal() <= MapView.Scale.CLOSE.ordinal() || admin) {
                            mapCanvas.setPixel(pX + cz, pZ + cz, chunkData.getColor());
                        }
                        if (s.ordinal() < MapView.Scale.NORMAL.ordinal() || admin) {
                            if (cx == 0 && !chunkData.getBlockFaceSet().contains(BlockFace.WEST)) {
                                mapCanvas.setPixel(pX + cx, pZ + cz, chunkData.getColor());
                            }
                            if (cx == 16 / scale - 1 && !chunkData.getBlockFaceSet().contains(BlockFace.EAST)) {
                                mapCanvas.setPixel(pX + cx, pZ + cz, chunkData.getColor());
                            }
                            if (cz == 0 && !chunkData.getBlockFaceSet().contains(BlockFace.NORTH)) {
                                mapCanvas.setPixel(pX + cx, pZ + cz, chunkData.getColor());
                            }
                            if (cz == 16 / scale - 1 && !chunkData.getBlockFaceSet().contains(BlockFace.SOUTH)) {
                                mapCanvas.setPixel(pX + cx, pZ + cz, chunkData.getColor());
                            }
                        } else {
                            mapCanvas.setPixel(pX + cx, pZ + cz, chunkData.getColor());
                        }
                    }
                }
            }
        }
        this.mapManager.updateLastMoved(player);
        mapSettings.setUpdate(false);
    }
}
