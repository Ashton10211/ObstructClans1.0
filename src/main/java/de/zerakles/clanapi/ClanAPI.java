package de.zerakles.clanapi;

import de.zerakles.main.Clan;
import de.zerakles.mysql.MySQL;
import de.zerakles.utils.Data;
import de.zerakles.utils.ZoneTypes;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class ClanAPI {

    private MySQL getMySQL() {
        return Clan.getClan().mySQL;
    }

    public void createTable() {
        getMySQL().update("CREATE TABLE IF NOT EXISTS clan(clan_name VARCHAR(40),"
                + " clan_owner VARCHAR(40), clan_id VARCHAR(40))");
        getMySQL().update("CREATE TABLE IF NOT EXISTS players(uuid VARCHAR(40),"
                + " clan VARCHAR(40), title VARCHAR(40));");
        getMySQL().update("CREATE TABLE IF NOT EXISTS chunks(clan VARCHAR(40),"
                + " X VARCHAR(40), Z VARCHAR(40), world_name VARCHAR(40));");
        getMySQL().update("CREATE TABLE IF NOT EXISTS gold(uuid VARCHAR(40),"
                + " gold VARCHAR(40));");
        getMySQL().update("CREATE TABLE IF NOT EXISTS dailyquest(uuid VARCHAR(40),"
                + " dailyquest VARCHAR(40));");
        getMySQL().update("CREATE TABLE IF NOT EXISTS home(uuid VARCHAR(40),worldName VARCHAR(40), X VARCHAR(40), Y VARCHAR(40)"
                + ", Z VARCHAR(40), Yaw VARCHAR(40), Pitch VARCHAR(40));");
    }

    public void deleteHome(String uuid){
        getMySQL().update("DELETE FROM home WHERE uuid='" + uuid + "';");
    }

    public Location getHome(String uuid){
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM home WHERE uuid='" + uuid + "';");
        try {
            if(resultSet.next()){
                String worldName = resultSet.getString("worldName");
                double x = resultSet.getDouble("X");
                double y = resultSet.getDouble("Y");
                double z = resultSet.getDouble("Z");
                double yaw = resultSet.getDouble("Yaw");
                double pitch = resultSet.getDouble("Pitch");
                Location location = Bukkit.getWorld(worldName).getSpawnLocation();
                location.setX(x);
                location.setY(y);
                location.setZ(z);
                location.setYaw((float) yaw);
                location.setPitch((float) pitch);
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  boolean hashHome(String uuid){
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM home WHERE uuid='" + uuid + "';");
        try {
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registerHome(String uuid, Location location){
        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        double pitch = location.getPitch();
        double yaw = location.getYaw();
        getMySQL().update("INSERT INTO home(uuid, worldName, X, Y, Z, Yaw, Pitch) VALUES"
                    + "('" + uuid + "','" + worldName + "','" + x + "','" +y+ "','" + z + "','" + yaw + "','" + pitch + "');");
        return;
    }

    public HashMap<String, String> getAllClanMembers(String clan) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM players WHERE clan='"
                + clan + "';");
        HashMap<String, String> members = new HashMap<>();
        try {
            while (resultSet.next()) {
                members.put(resultSet.getString("uuid"), resultSet.getString("title"));
            }
            return members;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public ArrayList<Chunk> getChunks(String clanName) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM chunks WHERE clan='" + clanName + "';");
        ArrayList<Chunk> chunks = new ArrayList<>();
        try {
            if (resultSet.next()) {
                int x1 = resultSet.getInt("X");
                int z1 = resultSet.getInt("Z");
                String worldName1 = resultSet.getString("world_name");
                Chunk chunk1 = Bukkit.getWorld(worldName1).getChunkAt(x1, z1);
                chunks.add(chunk1);
                while (resultSet.next()) {
                    int x = resultSet.getInt("X");
                    int z = resultSet.getInt("Z");
                    String worldName = resultSet.getString("world_name");
                    Chunk chunk = Bukkit.getWorld(worldName).getChunkAt(x, z);
                    chunks.add(chunk);
                }
                return chunks;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getChunkOwner(Location location) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM chunks WHERE X='"
                + location.getChunk().getX() + "' AND Z='" + location.getChunk().getZ()
                + "' AND world_name='" + location.getChunk().getWorld().getName() + "';");
        try {
            if (resultSet.next()) {
                return resultSet.getString("clan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerChunk(Location location, String clanName) {
        getMySQL().update("INSERT INTO chunks(clan, X, Z, world_name) VALUES ('"
                + clanName + "','" + location.getChunk().getX() + "','"
                + location.getChunk().getZ() + "','" + location.getChunk().getWorld().getName() + "');");
    }

    public void deleteChunk(Location location, String clanName) {
        getMySQL().update("DELETE from chunks WHERE world_name='"
                + location.getChunk().getWorld().getName() + "' AND X='"
                + location.getChunk().getX() + "' AND Z='" + location.getChunk().getZ() + "';");
        return;
    }

    public void deleteChunk(Chunk chunk, String clanName) {
        getMySQL().update("DELETE from chunks WHERE world_name='"
                + chunk.getWorld().getName() + "' AND X='"
                + chunk.getX() + "' AND Z='" + chunk.getZ() + "';");
        return;
    }






    public int getGold(Player player) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM gold WHERE uuid='"
                + player.getUniqueId().toString() + "';");

        try {
            if (resultSet.next()) {
                return resultSet.getInt("gold");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertGold(Player player, int gold) {
        getMySQL().update("INSERT INTO gold(uuid, gold) VALUES ('"
                + player.getUniqueId().toString() + "','" + gold + "');");
        return;
    }

    public boolean hasGold(Player player) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM gold WHERE uuid='"
                + player.getUniqueId().toString() + "';");

        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateGold(Player player, int Gold) {
        getMySQL().update("UPDATE gold SET gold='" + Gold
                + "' WHERE uuid='" + player.getUniqueId().toString() + "';");
        return;
    }

    //////

    public int getDailyQuest(Player player) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM dailyquest WHERE uuid='"
                + player.getUniqueId().toString() + "';");

        try {
            if (resultSet.next()) {
                return resultSet.getInt("dailyquest");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertDailyQuest(Player player, int dailyquest) {
        getMySQL().update("INSERT INTO dailyquest(uuid, dailyquest) VALUES ('"
                + player.getUniqueId().toString() + "','" + dailyquest + "');");
        return;
    }

    public boolean hasDailyQuest(Player player) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM dailyquest WHERE uuid='"
                + player.getUniqueId().toString() + "';");

        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateDailyQuest(Player player, int dailyquest) {
        getMySQL().update("UPDATE dailyconquest SET dailyquest='" + dailyquest
                + "' WHERE uuid='" + player.getUniqueId().toString() + "';");
        return;
    }




    HashMap<Player, Integer>hasP = new HashMap<>();
    public void startVillagerChecker(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Clan.getClan(), new Runnable() {
            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    PlayerInventory inventory = all.getInventory();
                    ItemStack[] items = inventory.getContents();
                    int has = 0;
                    for (ItemStack item : items)
                    {
                        if ((item != null) && (item.getType() == Material.MUSHROOM_SOUP) && (item.getAmount() > 0))
                        {
                            has += item.getAmount();
                        }
                    }
                    if(has > 0) {
                        if(!hasP.containsKey(all) || hasP.get(all) != has) {
                            all.getInventory().remove(Material.MUSHROOM_SOUP);
                            ArrayList<String> lore = new ArrayList<>();
                            ItemStack itemStack = getItemStack(Material.MUSHROOM_SOUP, lore, "§eMushroom §6Soup");
                            itemStack.setAmount(has);
                            all.getInventory().addItem(itemStack);
                            if(hasP.containsKey(all)){
                                hasP.remove(all);
                            }
                            hasP.put(all, has);
                        }
                    }
                }
                if(getData().bankBoy != null){
                    getData().bankBoy.teleport(getData().BankShop);
                }
                if(getData().organicBoy != null){
                    getData().organicBoy.teleport(getData().OrganicProduce);
                }
                if(getData().questBoy != null){
                    getData().questBoy.teleport(getData().QuestManager);
                }
                if(getData().miningBoy != null){
                    getData().miningBoy.teleport(getData().MiningShop);
                }
                if(getData().pvpBoy != null){
                    getData().pvpBoy.teleport(getData().PvpGear);
                }
                if(getData().suppliesBoy != null){
                    getData().suppliesBoy.teleport(getData().BuildingSupplies);
                }
                if(getData().TravelBoys.size() >  0){
                    int i = 0;
                    for (Villager villager:getData().TravelBoys
                         ) {
                        villager.teleport(getData().Travel.get(i));
                        i++;
                        continue;
                    }
                }
            }
        },0,0);
    }

    public void openManageClanEx(Player player) {
        String clanName = getClan(player.getUniqueId().toString());
        HashMap<String, String> members = getAllClanMembers(clanName);
        int i = members.size();
        Inventory inventory = Bukkit.createInventory(player, 54, "§l§8Manage Clan");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§7Clans can have a max size of §e20 §7members");
        lore.add("§7You currently have §e" + i + " §7members");
        lore.add("§7More members in you clan will allow you to");
        lore.add("§7claim more land.");
        lore.add(" ");
        lore.add("§eLeft Click §fInvite Player");
        ItemStack itemStack = getItemStack(Material.PRISMARINE, lore, "§l§aInvites");
        lore.clear();
        MaterialData materialData = itemStack.getData();
        materialData.getData();
        materialData.setData((byte) 1);
        itemStack.setDurability((short) 1);
        itemStack.setData(materialData);
        inventory.setItem(0, itemStack);
        lore.add(" ");
        lore.add("§7Every land claim represents a 16x16 chunk");
        lore.add("§7Your clan can claim a maximum of " + i * 3 + " chunks");
        ArrayList<Chunk> chunks = getChunks(clanName);
        int b = 0;
        if (chunks != null) {
            b = chunks.size();
        }
        lore.add("§7You currently have §e" + b + " §7chunk(s) claimed");
        lore.add("§7Increase max claims with more clan members");
        lore.add(" ");
        lore.add("§eLeft Click §fClaim Land");
        lore.add("§eShift-Left Click §fUnclaim Land");
        lore.add("§eShift-Right Click §fUnclaim All Land");
        ItemStack itemStack2 = getItemStack(Material.PRISMARINE, lore, "§l§aTerritory");
        MaterialData materialData2 = itemStack2.getData();
        materialData2.getData();
        materialData2.setData((byte) 0);
        itemStack2.setDurability((short) 0);
        itemStack2.setData(materialData2);
        inventory.setItem(2, itemStack2);
        lore.clear();
        lore.add(" ");
        lore.add("§eShift-Left Click §fLeave Clan");
        lore.add("§eShift-Right Click §fDisband Clan");
        ItemStack itemStack1 = getItemStack(Material.PRISMARINE, lore, "§l§aLeave");
        MaterialData materialData1 = itemStack1.getData();
        materialData1.getData();
        materialData1.setData((byte) 2);
        itemStack1.setDurability((short) 2);
        itemStack1.setData(materialData1);
        inventory.setItem(6, itemStack1);
        lore.clear();
        lore.add(" ");
        lore.add("§e/clan ally <clan> §fRequest Ally");
        lore.add("§e/clan neutral <clan> §fRevoke Ally");
        lore.add("§e/clan home <set> §fSet your home");
        lore.add("§e/clan home §fteleports to your home");
        lore.add("§e/ac §ftoggle ally chat");
        lore.add("§e/c §ftoggle clan chat");
        inventory.setItem(8, getItemStack(Material.LAVA_BUCKET, lore, "§l§aCommands"));

        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta playerHeadItemMeta = (SkullMeta) playerHead.getItemMeta();
        int heads = 18;
        for (Player all : Bukkit.getOnlinePlayers()
        ) {
            if (members.containsKey(all.getUniqueId().toString())) {
                playerHeadItemMeta.setOwner(all.getName());
                playerHeadItemMeta.setDisplayName("§l§a" + all.getName());
                lore.clear();
                lore.add(" ");
                lore.add("§eTitle: " + members.get(all.getUniqueId().toString()));
                Location location = all.getLocation();
                lore.add("§eX: " + location.getBlockX() + " Y:" + location.getBlockY() + " Z: " + location.getBlockZ());
                playerHeadItemMeta.setLore(lore);
                playerHead.setItemMeta(playerHeadItemMeta);
                inventory.setItem(heads, playerHead);
                heads++;
                members.remove(all.getUniqueId().toString());
                continue;
            }
        }

        for (OfflinePlayer all:Bukkit.getOfflinePlayers()
             ) {
            if (members.containsKey(all.getUniqueId().toString())) {
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
                ItemMeta skullMeta = skull.getItemMeta();
                lore.clear();
                lore.add(" ");
                lore.add("§eTitle: " + members.get(all.getUniqueId().toString()));
                lore.add("");
                lore.add("§cOffline");
                skullMeta.setDisplayName("§c" + all.getName());
                skullMeta.setLore(lore);
                skull.setItemMeta(skullMeta);
                inventory.setItem(heads, skull);
                heads++;
                members.remove(all.getUniqueId().toString());
                continue;
            }
        }
        player.openInventory(inventory);
        return;
    }

    public void openInviteToClan(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "§l§8Invite Players");
        ArrayList<String> lore = new ArrayList<>();
        ItemStack bed = getItemStack(Material.BED, lore, "§l§7← Go Back"); //4
        inventory.setItem(4, bed);
        int a = 18;
        int max = 36;
        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta playerHeadItemMeta = (SkullMeta) playerHead.getItemMeta();
        for (Player All : Bukkit.getOnlinePlayers()
        ) {
            if (!playerExists(All.getUniqueId().toString())) {
                if (a != 36) {
                    playerHeadItemMeta.setOwner(All.getName());
                    playerHeadItemMeta.setDisplayName("§l§a" + All.getName());
                    playerHead.setItemMeta(playerHeadItemMeta);
                    inventory.setItem(a, playerHead);
                    a++;
                    continue;
                } else {
                    return;
                }
            }
        }
        player.openInventory(inventory);
        return;
    }

    public void openManageClan(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "§l§8Manage Clan");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7To create a clan type");
        lore.add("§c/clan create <ClanName>");
        inventory.setItem(21, getItemStack(Material.BOOK_AND_QUILL, lore, "§l§aCreate Clan"));
        lore.clear();
        ItemStack itemStack = getItemStack(Material.PRISMARINE, lore, "§l§aJoin Clan");
        MaterialData materialData = itemStack.getData();
        materialData.getData();
        materialData.setData((byte) 2);
        itemStack.setDurability((short) 1);
        itemStack.setData(materialData);
        inventory.setItem(23, itemStack);

        player.openInventory(inventory);
        return;
    }

    private Data getData() {
        return Clan.getClan().data;
    }

    public void openJoinClan(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "§l§8Join Clan");
        ArrayList<String> lore = new ArrayList<>();
        ItemStack bed = getItemStack(Material.BED, lore, "§l§7← Go Back"); //4
        ItemStack book = getItemStack(Material.BOOK, lore, "§l§cYou have no Clan Invitations!"); //22
        if (getData().clanInvitations.containsKey(player)) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = paper.getItemMeta();
            ArrayList<String> invs = getData().clanInvitations.get(player); //18
            int a = 18;
            for (String clanName : invs
            ) {
                itemMeta.setDisplayName("§a" + clanName);
                paper.setItemMeta(itemMeta);
                inventory.setItem(a, paper);
                a++;
            }
        } else {
            inventory.setItem(22, book);
        }
        inventory.setItem(4, bed);
        player.openInventory(inventory);
        return;
    }

    private ItemStack getItemStack(Material material, ArrayList<String> lore, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void deletePlayer(String uuid) {
        getMySQL().update("DELETE FROM players WHERE uuid='"
                + uuid + "';");
    }

    public void updateTitle(String uuid, String title) {
        getMySQL().update("UPDATE players set title='" + title
                + "' WHERE uuid='" + uuid + "';");
    }

    public String getTitle(String uuid) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM players WHERE uuid='"
                + uuid + "';");
        try {
            if (resultSet.next()) {
                return resultSet.getString("title");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public String getClan(String uuid) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM players WHERE uuid='"
                + uuid + "';");
        try {
            if (resultSet.next()) {
                return resultSet.getString("clan");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public boolean playerExists(String uuid) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM players WHERE uuid='"
                + uuid + "';");
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }

    public void createPlayer(String uuid, String clan, String title) {
        getMySQL().update("INSERT INTO players(uuid, clan, title) VALUES ('"
                + uuid + "','" + clan + "','" + title + "');");
    }

    public void updateClanOwner(String clan_name, String clan_owner) {
        getMySQL().update("UPDATE clan set clan_owner='" + clan_owner
                + "' WHERE clan_name='" + clan_name + "';");
    }

    public void deleteClan(String clan_name) {
        getMySQL().update("DELETE FROM clan WHERE clan_name='" + clan_name + "';");
    }

    public void createClan(String clan_name, String clan_id, String clan_owner) {
        getMySQL().update("INSERT INTO clan(clan_name, clan_owner, clan_id) values ('"
                + clan_name + "','" + clan_owner + "','" + clan_id + "');");
    }

    public String getClanID(String clan_name) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM clan WHERE clan_name='"
                + clan_name + "';");
        try {
            if (resultSet.next()) {
                return resultSet.getString("clan_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwner(String clan_name) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM clan WHERE clan_name='"
                + clan_name + "';");
        try {
            if (resultSet.next()) {
                return resultSet.getString("clan_owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean clanExists(String clan_name) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM clan WHERE clan_name='"
                + clan_name + "';");
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<Chunk> checkedWilderness = new ArrayList<>();
    private HashMap<Chunk, String> loaded = new HashMap<>();

    //to not send millions of requests
    /*public ZoneTypes checkCunk(Chunk chunk, Player player){
        if(isSpawn(player.getLocation())){
            return ZoneTypes.SPAWN;
        }
        if(checkedWilderness.contains(chunk)){
            return ZoneTypes.WILDERNESS;
        }
        if(loaded.containsKey(chunk)){
            if(getClan(player.getUniqueId().toString())!=loaded.get(chunk)){
                return ZoneTypes.ENEMYCLAN;
            }
            return ZoneTypes.CLAN;
        }
        return getZone(player.getLocation(), player);
    }*/

    public void startChunkClearer() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Clan.getClan(), new Runnable() {
            @Override
            public void run() {
                checkedWilderness.clear();
                loaded.clear();
                Bukkit.getConsoleSender().sendMessage(getData().prefix + "§eLists cleared. RAM restored!");
            }
        }, 0, 1200);
    }

    public ZoneTypes getZone(Location loc, Player player) {
        if (getChunkOwner(loc) != null) {
            String Owner = getChunkOwner(loc);
            if (clanExists(Owner)) {
                loaded.put(loc.getChunk(), Owner);
                if (!playerExists(player.getUniqueId().toString())) {
                    return ZoneTypes.ENEMYCLAN;
                }
                String playerClan = getClan(player.getUniqueId().toString());
                if (playerClan.equalsIgnoreCase(Owner)) {
                    return ZoneTypes.CLAN;
                } else {
                    return ZoneTypes.ENEMYCLAN;
                }
            } else {
                checkedWilderness.add(loc.getChunk());
                deleteChunk(loc, Owner);
                return ZoneTypes.WILDERNESS;
            }
        } else {
            if(isShop(loc)){
                return ZoneTypes.SHOP;
            }
            if(isSpawn(loc)){
                return ZoneTypes.SPAWN;
            }
            return ZoneTypes.WILDERNESS;
        }
    }

    public ZoneTypes getZone(Location loc, Entity player) {
        if (getChunkOwner(loc) != null) {
            String Owner = getChunkOwner(loc);
            if (clanExists(Owner)) {
                loaded.put(loc.getChunk(), Owner);
                if (!playerExists(player.getUniqueId().toString())) {
                    return ZoneTypes.ENEMYCLAN;
                }
                String playerClan = getClan(player.getUniqueId().toString());
                if (playerClan.equalsIgnoreCase(Owner)) {
                    return ZoneTypes.CLAN;
                } else {
                    return ZoneTypes.ENEMYCLAN;
                }
            } else {
                checkedWilderness.add(loc.getChunk());
                deleteChunk(loc, Owner);
                return ZoneTypes.WILDERNESS;
            }
        } else {
            if(isShop(loc)){
                return ZoneTypes.SHOP;
            }
            if(isSpawn(loc)){
                return ZoneTypes.SPAWN;
            }
            return ZoneTypes.WILDERNESS;
        }
    }

    public boolean isShop(Location loc) {
        if(getData().Shop == null){
            return false;
        }
        if(loc.distance(getData().Shop) < 100){
            return true;
        }
        return false;
    }

    public boolean isSpawn(Location location) {
        Location spawn = Bukkit.getWorld(location.getWorld().getName()).getSpawnLocation();
        if(location.distance(spawn) < 100){
            return true;
        }
        return false;
    }
    /*

        Villager Shops

     */

    public void openBankShop(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9, "§8Bank Shop");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§fYour gold: §e" + getGold(player) + "g");
        String displayName;
        if(getGold(player)<50000){
            lore.add("§fYou don't have enough gold");
            lore.add("§fYou need §e50000§f gold to purchase a token");
            displayName = "§c§lMissing Gold!";
        } else {
            lore.add(" ");
            lore.add("§fPurchase for: §e" + 50000 + "g");
            displayName = "§6§lGoldToken";
        }
        inventory.setItem(3,getItemStack(Material.RABBIT_FOOT, lore,displayName));
        lore.clear();
        lore.add(" ");
        lore.add("§fYour Gold: §e" + getGold(player) + "g");
        lore.add("§fConversation Rate:§e 1000gems for 16000 gold");
        lore.add("§fConvert gems into gold coins");
        inventory.setItem(4, getItemStack(Material.EMERALD, lore, "§6§lConvert Gems To Gold!"));
        lore.clear();
        lore.add(" ");
        lore.add("§fYour Gold: §e" + getGold(player) + "g");
        lore.add("§fClick to exchange GoldToken for gold!");
        inventory.setItem(5, getItemStack(Material.FURNACE, lore, "§a§lCash In Gold Token"));
        player.openInventory(inventory);
        return;
    }



    public void openQuestManager(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9, "§8Quest Manager");
        ArrayList<String> lore = new ArrayList<>();
        String DisplayQuestName;
        if(getDailyQuest(player)<5){
            lore.add(" ");
            lore.add("§CBackstabber Quest");
            lore.add("§fKill 5 players with backstab §e " + getDailyQuest(player) + " kills");
            lore.add("§fReward: §e1000 gems ");
            DisplayQuestName = "§6§lDaily Quests";
            inventory.setItem(4, getItemStack(Material.EMERALD, lore, DisplayQuestName));

        } else {
            lore.add(" ");
            lore.add("§CBackstabber Quest");
            lore.add("§fYou have completed this daily quest. Come back tomorrow for another quest to complete");
            player.sendMessage(ChatColor.BLUE + "Queue Manager> " + ChatColor.YELLOW + "You have completed the Backstabber conquest. You've been rewarded with 1000 gems.");
            DisplayQuestName = "§6§lDaily Quests";
            inventory.setItem(4, getItemStack(Material.REDSTONE_BLOCK, lore, DisplayQuestName));

        }

        player.openInventory(inventory);
        return;
    }



    public  void openBuildingMaterials(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, "§8Building Supplies");
        ArrayList<String> lore = new ArrayList<>();
        int cost = 100;
        int earn = 20;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(1, getItemStack(Material.STONE, lore, "§a§lStone"));
        lore.clear();
        cost = 100;
        earn = 20;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(2, getItemStack(Material.SMOOTH_BRICK, lore, "§a§lSmooth Brick"));
        lore.clear();
        cost = 100;
        earn = 20;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(3, getItemStack(Material.SMOOTH_BRICK, lore, "§a§lSmooth Brick"));
        lore.clear();
        cost = 100;
        earn = 20;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(4, getItemStack(Material.COBBLESTONE, lore, "§a§lCobblestone"));
        lore.clear();
        cost = 100;
        earn = 20;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(10, getItemStack(Material.LOG, lore, "§a§lLog1"));
        ItemStack log = getItemStack(Material.LOG, lore, "§a§lLog2");
        log.setDurability((short) 1);
        inventory.setItem(11, log);
        log = getItemStack(Material.LOG, lore, "§a§lLog3");
        log.setDurability((short) 2);
        inventory.setItem(12, log);
        log = getItemStack(Material.LOG, lore, "§a§lLog4");
        log.setDurability((short) 3);
        inventory.setItem(13, log);
        ItemStack log2 = getItemStack(Material.LOG_2, lore, "§a§lLog5");
        inventory.setItem(14, log2);
        log2 = getItemStack(Material.LOG_2, lore, "§a§lLog6");
        log2.setDurability((short) 1);
        inventory.setItem(15, log2);
        lore.clear();
        cost = 20;
        earn = 4;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(19, getItemStack(Material.SAND, lore, "§a§lSand"));
        lore.clear();
        cost = 30;
        earn = 6;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(20, getItemStack(Material.GLASS, lore, "§a§lGlass"));
        lore.clear();
        cost = 80;
        earn = 16;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(21, getItemStack(Material.SANDSTONE, lore, "§a§lSandstone"));
        lore.clear();
        cost = 10;
        earn = 2;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(22, getItemStack(Material.DIRT, lore, "§a§lDirt"));
        lore.clear();
        cost = 50;
        earn = 10;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(23, getItemStack(Material.NETHER_BRICK, lore, "§a§lNether Brick"));
        lore.clear();
        cost = 75;
        earn = 15;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(24, getItemStack(Material.QUARTZ_BLOCK, lore, "§a§lQuartz Block"));
        lore.clear();
        cost = 30;
        earn = 6;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(25, getItemStack(Material.CLAY, lore, "§a§lClay"));
        player.openInventory(inventory);
        return;
    }



    public void openOrganicProduce(Player player){
        Inventory inventory = Bukkit.createInventory(player, 18, "§8Organic Produce");
        ArrayList<String> lore = new ArrayList<>();
        int cost = 15;
        int earn = 8;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(0, getItemStack(Material.POTATO_ITEM, lore, "§a§lPotato Item"));
        lore.clear();
        cost = 5;
        earn = 3;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(1, getItemStack(Material.MELON_STEM, lore, "§a§lMelon"));
        lore.clear();
        cost = 30;
        earn = 16;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(2, getItemStack(Material.BREAD, lore, "§a§lBread"));
        lore.clear();
        cost = 50;
        earn = 27;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(3, getItemStack(Material.COOKED_BEEF, lore, "§a§lCooked Beef"));
        lore.clear();
        cost = 50;
        earn = 27;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(4, getItemStack(Material.GRILLED_PORK, lore, "§a§lGrilled Pork"));
        lore.clear();
        cost = 35;
        earn = 19;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(5, getItemStack(Material.COOKED_CHICKEN, lore, "§a§lCooked Chicken"));
        lore.clear();
        cost = 50;
        earn = 10;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(6, getItemStack(Material.FEATHER, lore, "§a§lFeather"));
        lore.clear();
        cost = 10;
        earn = 5;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(7, getItemStack(Material.CARROT_ITEM, lore, "§a§lCarrot Item"));
        lore.clear();
        cost = 200;
        earn = 109;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(8, getItemStack(Material.MUSHROOM_SOUP, lore, "§a§lMushroom Soup"));
        lore.clear();
        cost = 15;
        earn = 3;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(9, getItemStack(Material.SUGAR_CANE, lore, "§a§lSugar Cane"));
        lore.clear();
        cost = 30;
        earn = 6;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(10, getItemStack(Material.PUMPKIN, lore, "§a§lPumpkin"));
        lore.clear();
        cost = 50;
        earn = 10;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(11, getItemStack(Material.STRING, lore, "§a§lString"));
        lore.clear();
        cost = 5;
        earn = 5;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(12, getItemStack(Material.ROTTEN_FLESH, lore, "§a§lRotten Flesh"));
        lore.clear();
        cost = 5;
        earn = 5;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(13, getItemStack(Material.SPIDER_EYE, lore, "§a§lSpider Eye"));
        lore.clear();
        cost = 75;
        earn = 1;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(14, getItemStack(Material.BROWN_MUSHROOM, lore, "§a§lBrown Mushroom"));
        lore.clear();
        cost = 75;
        earn = 1;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(15, getItemStack(Material.RED_MUSHROOM, lore, "§a§lRed Mushroom"));
        player.openInventory(inventory);
        return;
    }


    public void openMiningShop(Player player){
        Inventory inventory = Bukkit.createInventory(player, 18, "§8Mining Shop");
        ArrayList<String> lore = new ArrayList<>();
        int cost = 500;
        int earn = 100;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(1, getItemStack(Material.IRON_INGOT, lore, "§a§lIron Ingot"));
        lore.clear();
        cost = 500;
        earn = 100;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(2, getItemStack(Material.GOLD_INGOT, lore, "§a§lGold Ingot"));
        inventory.setItem(3, getItemStack(Material.DIAMOND, lore, "§a§lDiamond"));
        inventory.setItem(4, getItemStack(Material.LEATHER, lore, "§a§lLeather"));
        lore.clear();
        cost = 500/10;
        earn = 100/10;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(5, getItemStack(Material.COAL, lore, "§a§lCoal"));
        lore.clear();
        cost = 10;
        earn = 2;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(6, getItemStack(Material.REDSTONE, lore, "§a§lRedstone"));
        lore.clear();
        cost = 500;
        earn = 100;
        lore.add(" ");
        lore.add("§eLeft-Click §fto Buy §a1");
        lore.add("§fCosts §a" + cost + "g");
        lore.add(" ");
        lore.add("§eShift Left-Click §fto Buy §a64");
        lore.add("§fCosts §a" +  cost*64 + "g");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a" + earn);
        lore.add(" ");
        lore.add("§eShift Right-Click §fto Sell §aAll");
        inventory.setItem(7, getItemStack(Material.LAPIS_BLOCK, lore, "§a§lLapis Block"));
        player.openInventory(inventory);
    }
    //
    public void openPvpGear(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, "§8Pvp Gear");
        ArrayList<String> lore = new ArrayList<>();
        int cost = 2500;
        int earn = 500;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(9, getItemStack(Material.GOLD_HELMET, lore, "§a§lMage Helmet"));
        inventory.setItem(10, getItemStack(Material.LEATHER_HELMET, lore, "§a§lAssassin Helmet"));
        inventory.setItem(11, getItemStack(Material.CHAINMAIL_HELMET, lore, "§a§lRanger Helmet"));
        inventory.setItem(12, getItemStack(Material.IRON_HELMET, lore, "§a§lKnight Helmet"));
        inventory.setItem(13, getItemStack(Material.DIAMOND_HELMET, lore, "§a§lBrute Helmet"));
        lore.clear();
        cost = 4000;
        earn = 800;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(18, getItemStack(Material.GOLD_CHESTPLATE, lore, "§a§lMage Chestplate"));
        inventory.setItem(19, getItemStack(Material.LEATHER_CHESTPLATE, lore, "§a§lAssassin Chestplate"));
        inventory.setItem(20, getItemStack(Material.CHAINMAIL_CHESTPLATE, lore, "§a§lRanger Chestplate"));
        inventory.setItem(21, getItemStack(Material.IRON_CHESTPLATE, lore, "§a§lKnight Chestplate"));
        inventory.setItem(22, getItemStack(Material.DIAMOND_CHESTPLATE, lore, "§a§lBrute Chestplate"));
        lore.clear();
        cost = 3500;
        earn = 700;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(27, getItemStack(Material.GOLD_LEGGINGS, lore, "§a§lMage Leggins"));
        inventory.setItem(28, getItemStack(Material.LEATHER_LEGGINGS, lore, "§a§lAssassin Leggins"));
        inventory.setItem(29, getItemStack(Material.CHAINMAIL_LEGGINGS, lore, "§a§lRanger Leggins"));
        inventory.setItem(30, getItemStack(Material.IRON_LEGGINGS, lore, "§a§lKnight Leggins"));
        inventory.setItem(31, getItemStack(Material.DIAMOND_LEGGINGS, lore, "§a§lBrute Leggins"));
        lore.clear();
        cost = 2000;
        earn = 400;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(36, getItemStack(Material.GOLD_BOOTS, lore, "§a§lMage Boots"));
        inventory.setItem(37, getItemStack(Material.LEATHER_BOOTS, lore, "§a§lAssassin Boots"));
        inventory.setItem(38, getItemStack(Material.CHAINMAIL_BOOTS, lore, "§a§lRanger Boots"));
        inventory.setItem(39, getItemStack(Material.IRON_BOOTS, lore, "§a§lKnight Boots"));
        inventory.setItem(40, getItemStack(Material.DIAMOND_BOOTS, lore, "§a§lBrute Boots"));
        lore.clear();
        cost = 1000;
        earn = 200;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(15, getItemStack(Material.IRON_SWORD, lore, "§a§lIron Sword"));
        lore.clear();
        cost = 9000;
        earn = 1800;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(16, getItemStack(Material.DIAMOND_SWORD, lore, "§a§lPower Sword"));
        lore.clear();
        cost = 9000;
        earn = 1800;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(17, getItemStack(Material.GOLD_SWORD, lore, "§a§lBooster Sword"));
        lore.clear();
        cost = 1500;
        earn = 300;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(24, getItemStack(Material.IRON_AXE, lore, "§a§lIron Axe"));
        lore.clear();
        cost = 13500;
        earn = 2700;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(25, getItemStack(Material.DIAMOND_AXE, lore, "§a§lPower Axe"));
        lore.clear();
        cost = 13500;
        earn = 2700;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(26, getItemStack(Material.GOLD_AXE, lore, "§a§lBooster Axe"));
        lore.clear();
        cost = 175;
        earn = 35;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(33, getItemStack(Material.BOW, lore, "§a§lStandard Bow"));
        lore.clear();
        cost = 20;
        earn = 2;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(34, getItemStack(Material.ARROW, lore, "§a§lArrows"));
        lore.clear();
        cost = 150000;
        earn = 300;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(42, getItemStack(Material.IRON_BARDING, lore, "§a§lStandard Mount"));
        lore.clear();
        cost = 30000;
        earn = 0;
        lore.add("§eLeft-Click§f to Buy §a1");
        lore.add("§fCosts §a"+ cost + "g");
        lore.add( " ");
        lore.add(" ");
        lore.add("§eRight-Click §fto Sell §a1");
        lore.add("§fEarns §a"+ earn);
        lore.add(" ");
        lore.add("§eShift Right-Click§f to Sell §aAll");
        inventory.setItem(51, getItemStack(Material.ENCHANTMENT_TABLE, lore, "§a§lClass Shop"));
        player.openInventory(inventory);
        return;
    }

    public void openTravelInv(Player player){
        Inventory inventory = Bukkit.createInventory(player, 45, "§8Travel");
        ArrayList<String> lore = new ArrayList<>();
        inventory.setItem(4, getItemStack(Material.IRON_SWORD, lore, "§a§lSpawn"));
        inventory.setItem(20, getItemStack(Material.BED, lore, "§a§lHome"));
        inventory.setItem(24, getItemStack(Material.RECORD_9, lore, "§a§lShop"));
        player.openInventory(inventory);
        return;
    }

    public ArrayList<Block> cantDestroy = new ArrayList<>();

    public void registerClaim(Chunk chunk){
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (z == 15 || z == 0 || x == 15 | x == 0) {
                    final Block block = chunk.getWorld().getHighestBlockAt(chunk.getBlock(x, 0, z).getLocation());
                    if (block == null) {
                        continue;
                    }
                    block.getLocation().setY(-1);
                    Material material = block.getType();
                    block.setType(Material.GLOWSTONE);
                    cantDestroy.add(block);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Clan.getClan(), new Runnable() {
                        @Override
                        public void run() {
                            cantDestroy.remove(block);
                            block.setType(material);
                        }
                    },20*10);
                }
            }
        }
        return;
    }

    public String getChunkOwner(Chunk c) {
        ResultSet resultSet = getMySQL().getResult("SELECT * FROM chunks WHERE X='"
                + c.getX() + "' AND Z='" + c.getZ()
                + "' AND world_name='" + c.getWorld().getName() + "';");
        try {
            if (resultSet.next()) {
                return resultSet.getString("clan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}