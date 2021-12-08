package de.zerakles.config;

import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Shop {
    private Data getData(){
        return Clan.getClan().data;
    }
    public void loadConfig() {
        File file = new File(Clan.getClan().getDataFolder() + "/shop.yml");
        if (!file.exists()) {
            return;
        }
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        String worldName = fileConfiguration.getString("Shop.Name");
        double x = fileConfiguration.getDouble("Shop.X");
        double y = fileConfiguration.getDouble("Shop.Y");
        double z = fileConfiguration.getDouble("Shop.Z");
        double pitch = fileConfiguration.getDouble("Shop.pitch");
        double yaw = fileConfiguration.getDouble("Shop.yaw");
        Location location = Bukkit.getWorld(worldName).getBlockAt((int)x, (int)y, (int)z).getLocation();
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        location.setPitch((float) pitch);
        location.setYaw((float) yaw);
        getData().Shop = location;
        return;
    }
    public void createConfig(Location location) {
        File file = new File(Clan.getClan().getDataFolder() + "/shop.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        double pitch = location.getPitch();
        double yaw = location.getYaw();

        fileConfiguration.set("Shop.Name", worldName);
        fileConfiguration.set("Shop.X", x);
        fileConfiguration.set("Shop.Y", y);
        fileConfiguration.set("Shop.Z", z);
        fileConfiguration.set("Shop.yaw", yaw);
        fileConfiguration.set("Shop.pitch", pitch);

        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
