package com.obstruct.clans.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obstruct.clans.map.data.MapPixel;
import com.obstruct.clans.map.data.MapSettings;
import com.obstruct.clans.map.listeners.MapListener;
import com.obstruct.clans.map.nms.NMSHandler;
import com.obstruct.clans.map.renderer.ClanMapRenderer;
import com.obstruct.clans.map.renderer.MinimapRenderer;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilTime;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Getter
public class MapManager extends SpigotManager<SpigotModule<?>> {

    private final Map<UUID, MapSettings> mapSettings;
    private final NMSHandler nmsHandler;

    public MapManager(SpigotBasePlugin plugin) {
        super(plugin, "MapManager");
        this.mapSettings = new HashMap<>();
        this.nmsHandler = new NMSHandler();
    }

    @Override
    public void initialize(SpigotBasePlugin plugin) {
        super.initialize(plugin);

        loadMap();

        new BukkitRunnable() {
            @Override
            public void run() {
                saveMapData();
            }
        }.runTaskTimer(getPlugin(), 6000L, 6000L);
    }

    @Override
    public void registerModules() {
        addModule(new MapListener(this));
    }

    private synchronized void loadMap() {
        try {
            deleteFolder(new File("./world/data"));
            File file = new File("./world/data/map_0.dat");
            file.getParentFile().mkdirs();

            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            MapView map = Bukkit.getServer().getMap((short) 0);
            if (map == null) {
                map = Bukkit.createMap(Bukkit.getWorld("world"));
            }
            if (!(map.getRenderers().get(0) instanceof MinimapRenderer)) {
                for (MapRenderer r : map.getRenderers()) {
                    map.removeRenderer(r);
                }
                MinimapRenderer renderer = new MinimapRenderer(this);
                map.addRenderer(renderer);
                map.addRenderer(new ClanMapRenderer(this));
            }
            loadMapData((MinimapRenderer)map.getRenderers().get(0));
        } catch (Exception e) {
        }
    }

    public synchronized void loadMapData(MinimapRenderer minimapRenderer) {
        final long l = System.currentTimeMillis();

        final File file = new File(getPlugin().getDataFolder().getPath(), "map.json");
        if(!file.exists()) {
            file.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));

            jsonObject.forEach((key, value) -> {
                minimapRenderer.getWorldCacheMap().put(key.toString(), new TreeMap<>());
                ((JSONObject) value).forEach((o, o2) -> {
                    minimapRenderer.getWorldCacheMap().get(key.toString()).put(Integer.parseInt((String) o), new TreeMap<>());
                    ((JSONObject) o2).forEach((o1, o21) -> {
                        JSONObject jsonObject1 = (JSONObject) o21;
                        minimapRenderer.getWorldCacheMap().get(key.toString()).get(Integer.parseInt((String) o)).put(Integer.parseInt((String) o1), new MapPixel((byte) (long)jsonObject1.get("color"), (short) (long)jsonObject1.get("averageY")));
                    });
                });
            });
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded map data in " + UtilTime.getTime(System.currentTimeMillis() - l, UtilTime.TimeUnit.SECONDS, 2));
    }

    private void deleteFolder(File folder) {
        if (!folder.exists()) {
            return;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    public boolean hasMoved(Player player) {
        if (!getMapSettings().containsKey(player.getUniqueId())) {
            return false;
        }
        MapSettings mapData = getMapSettings().get(player.getUniqueId());
        int distX = Math.abs(mapData.getMapX() - player.getLocation().getBlockX());
        int distZ = Math.abs(mapData.getMapZ() - player.getLocation().getBlockZ());
        int scale = 1 << mapData.getScale().getValue();
        return (distX >= scale || distZ >= scale);
    }

    public void updateLastMoved(Player player) {
        if (!getMapSettings().containsKey(player.getUniqueId())) {
            return;
        }
        final MapSettings mapData = getMapSettings().get(player.getUniqueId());
        mapData.setMapX(player.getLocation().getBlockX());
        mapData.setMapZ(player.getLocation().getBlockZ());
    }

    public void saveMapData() {
        new BukkitRunnable() {
            @Override
            public void run() {
                final long l = System.currentTimeMillis();

                System.out.println("Saving map data...");

                MapView map = Bukkit.getMap((short) 0);
                if (map == null) {
                    map = Bukkit.createMap(Bukkit.getWorld("world"));
                }
                MinimapRenderer minimapRenderer = (MinimapRenderer) map.getRenderers().get(0);

                try {
                    final File file = new File(getPlugin().getDataFolder().getPath(), "map.json");
                    if (!file.exists()) {
                        file.getParentFile().mkdir();
                        file.createNewFile();
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(file, minimapRenderer.getWorldCacheMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Saved map data in " + UtilTime.getTime(System.currentTimeMillis() - l, UtilTime.TimeUnit.SECONDS, 2));
            }
        }.runTaskAsynchronously(getPlugin());
    }
}