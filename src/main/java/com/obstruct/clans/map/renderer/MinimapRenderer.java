package com.obstruct.clans.map.renderer;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.map.MapManager;
import com.obstruct.clans.map.data.*;
import com.obstruct.clans.map.event.MinimapExtraCursorEvent;
import com.obstruct.clans.map.event.MinimapPlayerCursorEvent;
import com.obstruct.clans.map.nms.INMSHandler;
import com.obstruct.clans.map.nms.MaterialMapColorInterface;
import com.obstruct.clans.map.nms.NMSHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.map.*;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

@Getter
public class MinimapRenderer extends MapRenderer implements Listener {

    protected Queue<Coords> queue = new LinkedList<>();
    protected Map<String, Map<Integer, Map<Integer, MapPixel>>> worldCacheMap;
    private final INMSHandler nms = new NMSHandler();

    protected MapManager mapManager;

    public MinimapRenderer(MapManager mapManager) {
        super(true);
        this.mapManager = mapManager;
        this.worldCacheMap = new TreeMap<>();

        (new QueueHandler(this)).runTaskTimer(mapManager.getPlugin(), 5L, 5L);

        mapManager.getPlugin().getServer().getPluginManager().registerEvents(this, mapManager.getPlugin());
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        int centerX = player.getLocation().getBlockX();
        int centerZ = player.getLocation().getBlockZ();

        MapSettings mapSettings = this.mapManager.getMapSettings().get(player.getUniqueId());

        int scale = 1 << mapSettings.getScale().getValue();

        if (mapSettings.getScale() == MapSettings.Scale.FAR) {
            centerX = 0;
            centerZ = 0;
        }

        if (!this.worldCacheMap.containsKey(player.getWorld().getName())) {
            this.worldCacheMap.put(player.getWorld().getName(), new TreeMap<>());
        }
        Map<Integer, Map<Integer, MapPixel>> cacheMap = this.worldCacheMap.get(player.getWorld().getName());

        boolean hasMoved = this.mapManager.hasMoved(player);

        if (hasMoved || mapSettings.isUpdate()) {
            for (int i = 0; i < 128; i++) {
                for (int j = 0; j < 128; j++) {
                    mapCanvas.setPixel(i, j, (byte) 0);
                }
            }
            int locX = centerX / scale - 64;
            int locZ = centerZ / scale - 64;
            for (int i = 0; i < 128; i++) {
                for (int j = 0; j < 128; j++) {
                    int x = (locX + i) * scale;
                    int z = (locZ + j) * scale;

                    if (locX + i < 0 && (locX + i) % scale != 0)
                        x--;
                    if (locZ + j < 0 && (locZ + j) % scale != 0)
                        z--;
                    if (cacheMap.containsKey(x) && cacheMap.get(x).containsKey(z)) {
                        final MapPixel mapPixel = cacheMap.get(x).get(z);
                        short prevY = getPrevY(x, z, player.getWorld().getName(), scale);

                        double d2 = (mapPixel.getAverageY() - prevY) * 4.0D / (scale + 4) + ((i + j & 1) - 0.5D) * 0.4D;
                        byte b0 = 1;

                        if (d2 > 0.6D) {
                            b0 = 2;
                        }
                        if (d2 < -0.6D) {
                            b0 = 0;
                        }
                        mapCanvas.setPixel(i, j, (byte) (mapPixel.getColor() + b0));
                    } else {
                        for (int k = -scale; k < scale; k++) {
                            for (int l = -scale; l < scale; l++) {
                                handlePixel(cacheMap, x + k, z + l, player);
                            }
                        }
                    }
                }
            }
        }
        handleCursors(mapCanvas, player, scale, centerX, centerZ);
    }

    private void addToQueue(Coords coords) {
        if (!getQueue().contains(coords)) {
            getQueue().add(coords);
        }
    }

    private void handleBlockEvent(Block block) {
        addToQueue(new Coords(block.getX(), block.getZ(), block.getWorld().getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockPlaceEvent e) {
        handleBlockEvent(e.getBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockFromToEvent e) {
        handleBlockEvent(e.getBlock());
        handleBlockEvent(e.getToBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockPhysicsEvent e) {
        switch (e.getChangedType()) {
            case LAVA:
            case WATER:
                handleBlockEvent(e.getBlock());
                break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockBreakEvent e) {
        handleBlockEvent(e.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockBurnEvent e) {
        handleBlockEvent(e.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockFadeEvent e) {
        handleBlockEvent(e.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockFormEvent e) {
        handleBlockEvent(e.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockGrowEvent e) {
        handleBlockEvent(e.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(BlockSpreadEvent e) {
        handleBlockEvent(e.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockEvent(EntityBlockFormEvent e) {
        handleBlockEvent(e.getBlock());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCursor(MinimapExtraCursorEvent e) {
        Player player = e.getPlayer();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().equals(player.getWorld())) {
                float yaw = p.getLocation().getYaw();
                if (yaw < 0.0F) {
                    yaw += 360.0F;
                }
                byte direction = (byte) (int) ((Math.abs(yaw) + 11.25D) / 22.5D);
                if (direction > 15) {
                    direction = 0;
                }
                int x = p.getLocation().getBlockX();
                int z = p.getLocation().getBlockZ();

                Clan aClan = this.mapManager.getManager(ClanManager.class).getClan(player);
                Clan bClan = this.mapManager.getManager(ClanManager.class).getClan(p);
                MinimapPlayerCursorEvent event = null;
                if (aClan == null) {
                    if (player == p) {
                        event = new MinimapPlayerCursorEvent(player, p, true, MapCursor.Type.WHITE_POINTER);
                    }
                } else if (bClan != null) {
                    if (aClan == bClan) {
                        event = new MinimapPlayerCursorEvent(player, p, true, MapCursor.Type.BLUE_POINTER);
                    } else if (aClan.isAllied(bClan)) {
                        event = new MinimapPlayerCursorEvent(player, p, true, MapCursor.Type.GREEN_POINTER);
                    }
                }

                if (event != null) {
                    Bukkit.getPluginManager().callEvent(event);
                    e.getCursors().add(new ExtraCursor(x, z, (player == p || event.isCursorShown()), event.getType(), direction, p.getWorld().getName(), true));
                }
            }
        }
    }

    private void handleCursors(MapCanvas canvas, Player player, int scale, int centerX, int centerZ) {
        MapCursorCollection cursors = canvas.getCursors();
        while (cursors.size() > 0) {
            cursors.removeCursor(cursors.getCursor(0));
        }
        MinimapExtraCursorEvent e = new MinimapExtraCursorEvent(player, cursors, scale);
        Bukkit.getServer().getPluginManager().callEvent(e);
        for (ExtraCursor c : e.getCursors()) {
            if (!c.getWorld().equalsIgnoreCase(player.getWorld().getName())) {
                continue;
            }

            int x = (c.getX() - centerX) / scale * 2;
            int z = (c.getZ() - centerZ) / scale * 2;

            if (Math.abs(x) > 127) {
                if (c.isShownOutside()) {
                    x = (x < 0) ? -128 : 127;
                } else {
                    continue;
                }
            }

            if (Math.abs(z) > 127) {
                if (c.isShownOutside()) {
                    z = (z < 0) ? -128 : 127;
                } else {
                    continue;
                }
            }
            cursors.addCursor(x, z, c.getDirection(), c.getType().getValue(), c.isVisible());
        }
    }

    private short getPrevY(int x, int z, String world, int scale) {
        Map<Integer, Map<Integer, MapPixel>> cacheMap = this.worldCacheMap.get(world);

        if (cacheMap.containsKey(x + -scale) && cacheMap.get(x + -scale).containsKey(z + -scale)) {
            return ((cacheMap.get(x + -scale)).get(z + -scale)).getAverageY();
        }
        if (cacheMap.containsKey(x + scale) && cacheMap.get(x + scale).containsKey(z + scale)) {
            return (cacheMap.get(x + scale)).get(z + scale).getAverageY();
        }
        if (cacheMap.containsKey(x + -scale) && cacheMap.get(x + -scale).containsKey(z + scale)) {
            return (cacheMap.get(x + -scale)).get(z + scale).getAverageY();
        }
        if (cacheMap.containsKey(x + -scale) && cacheMap.get(x + -scale).containsKey(z + scale)) {
            return (cacheMap.get(x + -scale)).get(z + scale).getAverageY();
        }
        return 0;
    }


    private void handlePixel(Map<Integer, Map<Integer, MapPixel>> cacheMap, int x, int z, Player player) {
        if (!cacheMap.containsKey(x)) {
            cacheMap.put(x, new TreeMap<>());
        }
        if (!cacheMap.get(x).containsKey(z)) {

            int y = player.getWorld().getHighestBlockYAt(x, z) + 1;

            Block b = player.getWorld().getBlockAt(x, y, z);

            if (!b.getChunk().isLoaded()) {
                return;
            }
            while (b.getY() > 0 && this.nms.getBlockColor(b) == this.nms.getColorNeutral()) {
                b = player.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
            }
            short avgY = 0;
            avgY = (short) (avgY + b.getY());

            MaterialMapColorInterface mainColor = this.nms.getBlockColor(b);

            (cacheMap.get(x)).put(z, new MapPixel((byte) (mainColor.getM() * 4), avgY));
        }
    }
}
