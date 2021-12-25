package com.obstruct.clans.map.data;

import com.obstruct.clans.map.nms.MaterialMapColorInterface;
import com.obstruct.clans.map.renderer.MinimapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Queue;

public class QueueHandler extends BukkitRunnable {
    private final MinimapRenderer minimapRenderer;

    public QueueHandler(MinimapRenderer minimapRenderer) {
        this.minimapRenderer = minimapRenderer;
    }


    public void run() {
        Queue<Coords> queue = this.minimapRenderer.getQueue();

        if (queue.isEmpty()) {
            return;
        }

        for (int i = 0; i < 64; i++) {
            Coords poll = queue.poll();

            if (poll == null) {
                break;
            }

            World world = Bukkit.getWorld(poll.getWorld());

            if (this.minimapRenderer.getWorldCacheMap().containsKey(poll.getWorld())) {

                if (((Map) this.minimapRenderer.getWorldCacheMap().get(poll.getWorld())).containsKey(Integer.valueOf(poll.getX()))) {

                    if (((Map) ((Map) this.minimapRenderer.getWorldCacheMap().get(poll.getWorld())).get(Integer.valueOf(poll.getX()))).containsKey(Integer.valueOf(poll.getZ()))) {


                        Block b = world.getBlockAt(poll.getX(), world.getHighestBlockYAt(poll.getX(), poll.getZ()), poll.getZ());

                        if (b.getChunk().isLoaded()) {


                            while (b.getY() > 0 && this.minimapRenderer.getNms().getBlockColor(b) == this.minimapRenderer.getNms().getColorNeutral()) {
                                b = world.getBlockAt(b.getX(), b.getY() - 1, b.getZ());
                            }
                            short avgY = 0;
                            avgY = (short) (avgY + b.getY());

                            MaterialMapColorInterface mainColor = this.minimapRenderer.getNms().getBlockColor(b);

                            MapPixel mapPixel = (MapPixel) ((Map) ((Map) this.minimapRenderer.getWorldCacheMap().get(b.getWorld().getName())).get(Integer.valueOf(b.getX()))).get(Integer.valueOf(b.getZ()));
                            mapPixel.setAverageY(avgY);
                            mapPixel.setColor((byte) (mainColor.getM() * 4));
                        }
                    }
                }
            }
        }
    }
}