package de.zerakles.config;

import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Villager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VillagerLoader {
    private Data getData(){
        return Clan.getClan().data;
    }
    public void loadConfig() {
        File file = new File(Clan.getClan().getDataFolder() + "/villager.yml");
        if (!file.exists()) {
            return;
        }
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        Location bankShop;
        Location suppliesShop;
        Location pvpShop;
        Location organicShop;
        Location miningShop;
        Location questManager;
        String worldName;
        double x;
        double y;
        double z;
        double yaw;
        double pitch;

        worldName =fileConfiguration.getString("Villager.BankShop.WorldName");
        x=fileConfiguration.getDouble("Villager.BankShop.X");
        y=fileConfiguration.getDouble("Villager.BankShop.Y");
        z=fileConfiguration.getDouble("Villager.BankShop.Z");
        yaw=fileConfiguration.getDouble("Villager.BankShop.Yaw");
        pitch=fileConfiguration.getDouble("Villager.BankShop.Pitch");

        bankShop = Bukkit.getWorld(worldName).getSpawnLocation();
        bankShop.setX(x);
        bankShop.setY(y);
        bankShop.setZ(z);
        bankShop.setPitch((float) pitch);
        bankShop.setYaw((float) yaw);

        worldName =fileConfiguration.getString("Villager.SuppliesShop.WorldName");
        x=fileConfiguration.getDouble("Villager.SuppliesShop.X");
        y=fileConfiguration.getDouble("Villager.SuppliesShop.Y");
        z=fileConfiguration.getDouble("Villager.SuppliesShop.Z");
        yaw=fileConfiguration.getDouble("Villager.SuppliesShop.Yaw");
        pitch=fileConfiguration.getDouble("Villager.SuppliesShop.Pitch");


        suppliesShop = Bukkit.getWorld(worldName).getSpawnLocation();
        suppliesShop.setX(x);
        suppliesShop.setY(y);
        suppliesShop.setZ(z);
        suppliesShop.setPitch((float) pitch);
        suppliesShop.setYaw((float) yaw);

        questManager = Bukkit.getWorld(worldName).getSpawnLocation();
        questManager.setX(x);
        questManager.setY(y);
        questManager.setZ(z);
        questManager.setPitch((float) pitch);
        questManager.setYaw((float) yaw);

        worldName =fileConfiguration.getString("Villager.QuestManager.WorldName");
        x=fileConfiguration.getDouble("Villager.QuestManager.X");
        y=fileConfiguration.getDouble("Villager.QuestManager.Y");
        z=fileConfiguration.getDouble("Villager.QuestManager.Z");
        yaw=fileConfiguration.getDouble("Villager.QuestManager.Yaw");
        pitch=fileConfiguration.getDouble("Villager.QuestManager.Pitch");



        worldName =fileConfiguration.getString("Villager.PvpGear.WorldName");
        x=fileConfiguration.getDouble("Villager.PvpGear.X");
        y=fileConfiguration.getDouble("Villager.PvpGear.Y");
        z=fileConfiguration.getDouble("Villager.PvpGear.Z");
        yaw=fileConfiguration.getDouble("Villager.PvpGear.Yaw");
        pitch=fileConfiguration.getDouble("Villager.PvpGear.Pitch");

        pvpShop = Bukkit.getWorld(worldName).getSpawnLocation();
        pvpShop.setX(x);
        pvpShop.setY(y);
        pvpShop.setZ(z);
        pvpShop.setPitch((float) pitch);
        pvpShop.setYaw((float) yaw);

        worldName =fileConfiguration.getString("Villager.OrganicShop.WorldName");
        x=fileConfiguration.getDouble("Villager.OrganicShop.X");
        y=fileConfiguration.getDouble("Villager.OrganicShop.Y");
        z=fileConfiguration.getDouble("Villager.OrganicShop.Z");
        yaw=fileConfiguration.getDouble("Villager.OrganicShop.Yaw");
        pitch=fileConfiguration.getDouble("Villager.OrganicShop.Pitch");

        organicShop = Bukkit.getWorld(worldName).getSpawnLocation();
        organicShop.setX(x);
        organicShop.setY(y);
        organicShop.setZ(z);
        organicShop.setPitch((float) pitch);
        organicShop.setYaw((float) yaw);

        worldName =fileConfiguration.getString("Villager.MiningShop.WorldName");
        x=fileConfiguration.getDouble("Villager.MiningShop.X");
        y=fileConfiguration.getDouble("Villager.MiningShop.Y");
        z=fileConfiguration.getDouble("Villager.MiningShop.Z");
        yaw=fileConfiguration.getDouble("Villager.MiningShop.Yaw");
        pitch=fileConfiguration.getDouble("Villager.MiningShop.Pitch");

        miningShop = Bukkit.getWorld(worldName).getSpawnLocation();
        miningShop.setX(x);
        miningShop.setY(y);
        miningShop.setZ(z);
        miningShop.setPitch((float) pitch);
        miningShop.setYaw((float) yaw);

        int i = 0;
        i = fileConfiguration.getInt("Villager.Travel.Count");
        ArrayList<Location> travelLocations = new ArrayList<>();

        for(int a = 0; a<i; a++){
            String worldName2 = fileConfiguration.getString("Villager.Travel." + a +  ".WorldName");
            double x2 = fileConfiguration.getDouble("Villager.Travel." + a +  ".X");
            double y2 = fileConfiguration.getDouble("Villager.Travel." + a +  ".Y");
            double z2 = fileConfiguration.getDouble("Villager.Travel." + a +  ".Z");
            double yaw2 = fileConfiguration.getDouble("Villager.Travel." + a +  ".Yaw");
            double pitch2 = fileConfiguration.getDouble("Villager.Travel." + a +  ".Pitch");
            Location loc = Bukkit.getWorld(worldName2).getSpawnLocation();
            loc.setYaw((float) yaw2);
            loc.setPitch((float)pitch2);
            loc.setY(y2);
            loc.setZ(z2);
            loc.setX(x2);
            travelLocations.add(loc);
        }

        getData().BankShop = bankShop;
        getData().Travel = travelLocations;
        getData().PvpGear = pvpShop;
        getData().MiningShop = miningShop;
        getData().BuildingSupplies = suppliesShop;
        getData().OrganicProduce = organicShop;
        getData().QuestManager = questManager;
        Villager miningBoy =  miningShop.getWorld().spawn(miningShop, Villager.class);
        Villager bankBoy =  bankShop.getWorld().spawn(bankShop, Villager.class);
        Villager questBoy =  questManager.getWorld().spawn(questManager, Villager.class);

        Villager pvpBoy =  pvpShop.getWorld().spawn(pvpShop, Villager.class);
        Villager buildBoy =  suppliesShop.getWorld().spawn(suppliesShop, Villager.class);
        Villager organicBoy =  organicShop.getWorld().spawn(organicShop, Villager.class);
        getData().bankBoy = bankBoy;
        getData().bankBoy.setAdult();
        getData().bankBoy.setCustomName("§c§lBank Shop");
        getData().bankBoy.setCustomNameVisible(true);
        getData().questBoy = questBoy;
        getData().questBoy.setAdult();
        getData().questBoy.setCustomName("§c§lQuest Manager");
        getData().questBoy.setCustomNameVisible(true);
        getData().miningBoy = miningBoy;
        getData().miningBoy.setAdult();
        getData().miningBoy.setCustomName("§e§lMining Shop");
        getData().miningBoy.setCustomNameVisible(true);
        getData().organicBoy = organicBoy;
        getData().organicBoy.setAdult();
        getData().organicBoy.setCustomName("§a§lOrganic Produce");
        getData().organicBoy.setCustomNameVisible(true);
        getData().pvpBoy = pvpBoy;
        getData().pvpBoy.setAdult();
        getData().pvpBoy.setCustomName("§4§lPvp Gear");
        getData().pvpBoy.setCustomNameVisible(true);
        getData().suppliesBoy = buildBoy;
        getData().suppliesBoy.setAdult();
        getData().suppliesBoy.setCustomName("§6§lBuilding Supplies");
        getData().suppliesBoy.setCustomNameVisible(true);

        for (Location location: travelLocations
             ) {
            Villager travelBoy = location.getWorld().spawn(location, Villager.class);
            travelBoy.setCustomName("§b§lTravel");
            travelBoy.setCustomNameVisible(true);
            travelBoy.setCanPickupItems(false);
            travelBoy.setAdult();
            getData().TravelBoys.add(travelBoy);
            continue;
        }

        return;
    }
    public void createConfig() {
        File file = new File(Clan.getClan().getDataFolder() + "/villager.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        Villager villagerBank = getData().bankBoy;
        Villager villagerQuest = getData().questBoy;
        Villager villagerSupplies = getData().suppliesBoy;
        Villager villagerPvp = getData().pvpBoy;
        Villager villagerOrganic = getData().organicBoy;
        Villager villagerMining = getData().miningBoy;
        Location bankShop = getData().BankShop;
        Location questManager = getData().QuestManager;
        Location suppliesShop = getData().BuildingSupplies;
        Location pvpGear = getData().PvpGear;
        Location organicShop = getData().OrganicProduce;
        Location miningShop = getData().MiningShop;
        villagerBank.damage(40);
        villagerSupplies.damage(40);
        villagerPvp.damage(40);
        villagerOrganic.damage(40);
        villagerMining.damage(40);
        villagerQuest.damage(40);

        String worldName = bankShop.getWorld().getName();
        double x = bankShop.getX();;
        double y = bankShop.getY();;
        double z = bankShop.getZ();
        double pitch = bankShop.getPitch();
        double yaw = bankShop.getYaw();

        fileConfiguration.set("Villager.BankShop.WorldName", worldName);
        fileConfiguration.set("Villager.BankShop.X", x);
        fileConfiguration.set("Villager.BankShop.Y", y);
        fileConfiguration.set("Villager.BankShop.Z", z);
        fileConfiguration.set("Villager.BankShop.Yaw", yaw);
        fileConfiguration.set("Villager.BankShop.Pitch", pitch);

        worldName = questManager.getWorld().getName();
         x = questManager.getX();;
         y = questManager.getY();;
         z = questManager.getZ();
         pitch = questManager.getPitch();
         yaw = questManager.getYaw();

        fileConfiguration.set("Villager.QuestManager.WorldName", worldName);
        fileConfiguration.set("Villager.QuestManager.X", x);
        fileConfiguration.set("Villager.QuestManager.Y", y);
        fileConfiguration.set("Villager.QuestManager.Z", z);
        fileConfiguration.set("Villager.QuestManager.Yaw", yaw);
        fileConfiguration.set("Villager.QuestManager.Pitch", pitch);


        worldName = suppliesShop.getWorld().getName();
        x = suppliesShop.getX();;
        y = suppliesShop.getY();;
        z = suppliesShop.getZ();
        pitch = suppliesShop.getPitch();
        yaw = suppliesShop.getYaw();

        fileConfiguration.set("Villager.SuppliesShop.WorldName", worldName);
        fileConfiguration.set("Villager.SuppliesShop.X", x);
        fileConfiguration.set("Villager.SuppliesShop.Y", y);
        fileConfiguration.set("Villager.SuppliesShop.Z", z);
        fileConfiguration.set("Villager.SuppliesShop.Yaw", yaw);
        fileConfiguration.set("Villager.SuppliesShop.Pitch", pitch);

        worldName = pvpGear.getWorld().getName();
        x = pvpGear.getX();;
        y = pvpGear.getY();;
        z = pvpGear.getZ();
        pitch = pvpGear.getPitch();
        yaw = pvpGear.getYaw();

        fileConfiguration.set("Villager.PvpGear.WorldName", worldName);
        fileConfiguration.set("Villager.PvpGear.X", x);
        fileConfiguration.set("Villager.PvpGear.Y", y);
        fileConfiguration.set("Villager.PvpGear.Z", z);
        fileConfiguration.set("Villager.PvpGear.Yaw", yaw);
        fileConfiguration.set("Villager.PvpGear.Pitch", pitch);

        worldName = organicShop.getWorld().getName();
        x =organicShop.getX();;
        y = organicShop.getY();;
        z = organicShop.getZ();
        pitch = organicShop.getPitch();
        yaw = organicShop.getYaw();

        fileConfiguration.set("Villager.OrganicShop.WorldName", worldName);
        fileConfiguration.set("Villager.OrganicShop.X", x);
        fileConfiguration.set("Villager.OrganicShop.Y", y);
        fileConfiguration.set("Villager.OrganicShop.Z", z);
        fileConfiguration.set("Villager.OrganicShop.Yaw", yaw);
        fileConfiguration.set("Villager.OrganicShop.Pitch", pitch);

        worldName = miningShop.getWorld().getName();
        x = miningShop.getX();;
        y = miningShop.getY();;
        z = miningShop.getZ();
        pitch = miningShop.getPitch();
        yaw = miningShop.getYaw();

        fileConfiguration.set("Villager.MiningShop.WorldName", worldName);
        fileConfiguration.set("Villager.MiningShop.X", x);
        fileConfiguration.set("Villager.MiningShop.Y", y);
        fileConfiguration.set("Villager.MiningShop.Z", z);
        fileConfiguration.set("Villager.MiningShop.Yaw", yaw);
        fileConfiguration.set("Villager.MiningShop.Pitch", pitch);

        ArrayList<Villager> travelVillagers = getData().TravelBoys;
        for (Villager villager:travelVillagers
             ) {
            villager.damage(40);
            continue;
        }
        ArrayList<Location> travelLocations = getData().Travel;

        int i = travelLocations.size();

        fileConfiguration.set("Villager.Travel.Count", i);

        int a = 0;

        for (Location location1: travelLocations
             ) {
            fileConfiguration.set("Villager.Travel." + a +  ".WorldName", location1.getWorld().getName());
            fileConfiguration.set("Villager.Travel." + a +  ".X", location1.getX());
            fileConfiguration.set("Villager.Travel." + a +  ".Y", location1.getY());
            fileConfiguration.set("Villager.Travel." + a +  ".Z", location1.getZ());
            fileConfiguration.set("Villager.Travel." + a +  ".Yaw", location1.getYaw());
            fileConfiguration.set("Villager.Travel." + a +  ".Pitch", location1.getPitch());
            a++;
            continue;
        }
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}