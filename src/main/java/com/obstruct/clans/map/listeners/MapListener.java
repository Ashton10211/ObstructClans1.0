package com.obstruct.clans.map.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.events.*;
import com.obstruct.clans.map.MapManager;
import com.obstruct.clans.map.data.ChunkData;
import com.obstruct.clans.map.data.MapSettings;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class MapListener extends SpigotModule<MapManager> implements Listener {

    public MapListener(MapManager manager) {
        super(manager, "MapListener");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        loadChunks(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        getManager().getMapSettings().remove(player.getUniqueId());
        for (ItemStack value : player.getInventory().all(Material.MAP).values()) {
            player.getInventory().remove(value);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(itemStack -> (itemStack.getType() == Material.MAP));
    }

    @EventHandler
    public void onMapTransfer(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getCurrentItem().getType() == Material.MAP) {
            Inventory topInventory = event.getWhoClicked().getOpenInventory().getTopInventory();
            if (topInventory != null && topInventory.getType() != InventoryType.CRAFTING) {
                event.setCancelled(true);
            }
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item != null && item.getType() == Material.MAP) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (item != null && item.getType() == Material.MAP) {
            event.getItemDrop().remove();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanAlly(ClanAllyEvent event) {
        updateClanRelation(event.getClan(), event.getOther());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanEnemy(ClanEnemyEvent event) {
        updateClanRelation(event.getClan(), event.getOther());
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanTrust(ClanTrustEvent event) {
        updateClanRelation(event.getClan(), event.getOther());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanNeutral(ClanNeutralEvent event) {
        updateClanRelation(event.getClan(), event.getOther());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanRevokeTrust(ClanRevokeTrustEvent event) {
        updateClanRelation(event.getClan(), event.getOther());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onUnclaim(ClanUnclaimEvent event) {
        updateClaims(event.getClan());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClaim(ClanClaimEvent event) {
        updateClaims(event.getClan());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanLeave(ClanLeaveEvent event) {
        Player player = event.getPlayer();
        if (!getManager().getMapSettings().containsKey(player.getUniqueId())) {
            getManager().getMapSettings().put(player.getUniqueId(), new MapSettings(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
        }
        for (ChunkData chunkData : getManager().getMapSettings().get(player.getUniqueId()).getClanMapData()) {
            Clan clan = getManager(ClanManager.class).getClan(chunkData.getClan());
            if (clan != null && !clan.isAdmin()) {
                chunkData.setColor(ClanRelation.NEUTRAL.getMapColor());
            }
        }
        updateStatus(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisband(ClanDisbandEvent event) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!getManager().getMapSettings().containsKey(online.getUniqueId())) {
                getManager().getMapSettings().put(online.getUniqueId(), new MapSettings(online.getLocation().getBlockX(), online.getLocation().getBlockZ()));
            }
            getManager().getMapSettings().get(online.getUniqueId()).getClanMapData().removeIf(chunkData -> (getManager(ClanManager.class).getClan(chunkData.getClan()) == null));
            for (ChunkData chunkData : getManager().getMapSettings().get(online.getUniqueId()).getClanMapData()) {
                Clan clan = getManager(ClanManager.class).getClan(chunkData.getClan());
                if (clan != null && !clan.isAdmin()) {
                    chunkData.setColor(getManager(ClanManager.class).getClanRelation(clan, getManager(ClanManager.class).getClan(online.getUniqueId())).getMapColor());
                }
            }
            updateStatus(online);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinClan(ClanJoinEvent event) {
        Player player = event.getPlayer();
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            return;
        }
        if (!getManager().getMapSettings().containsKey(player.getUniqueId())) {
            getManager().getMapSettings().put(player.getUniqueId(), new MapSettings(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
        }
        for (String claim : clan.getClaims()) {
            int x = Integer.parseInt(claim.split(":")[1]);
            int z = Integer.parseInt(claim.split(":")[2]);
            getManager().getMapSettings().get(player.getUniqueId()).getClanMapData().stream().filter(chunkData -> (chunkData.getX() == x && chunkData.getZ() == z && chunkData.getClan().equals(clan.getName()))).forEach(chunkData -> chunkData.setColor(ClanRelation.SELF.getMapColor()));
        }
        for (String ally : clan.getAlliance().keySet()) {
            Clan allyClan = getManager(ClanManager.class).getClan(ally);
            ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, allyClan);
            for (String claim : allyClan.getClaims()) {
                int x = Integer.parseInt(claim.split(":")[1]);
                int z = Integer.parseInt(claim.split(":")[2]);
                getManager().getMapSettings().get(player.getUniqueId()).getClanMapData().stream().filter(chunkData -> (chunkData.getX() == x && chunkData.getZ() == z && chunkData.getClan().equals(ally))).forEach(chunkData -> chunkData.setColor(clanRelation.getMapColor()));
            }
        }
        for (String enemy : clan.getAlliance().keySet()) {
            Clan enemyClan = getManager(ClanManager.class).getClan(enemy);
            ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, enemyClan);
            for (String claim : enemyClan.getClaims()) {
                int x = Integer.parseInt(claim.split(":")[1]);
                int z = Integer.parseInt(claim.split(":")[2]);
                getManager().getMapSettings().get(player.getUniqueId()).getClanMapData().stream().filter(chunkData -> (chunkData.getX() == x && chunkData.getZ() == z && chunkData.getClan().equals(enemy))).forEach(chunkData -> chunkData.setColor(clanRelation.getMapColor()));
            }
        }
        updateStatus(player);
    }

//    @EventHandler(priority = EventPriority.MONITOR)
//    public void onClanPillageStart(ClanPillageStartEvent event) {
//        updateClanRelation(event.getPillager(), event.getPillagee());
//    }
//
//    @EventHandler(priority = EventPriority.MONITOR)
//    public void onClanPillageEnd(ClanPillageEndEvent event) {
//        updateClanRelation(event.getPillager(), event.getPillagee());
//    }

    private void updateClanRelation(Clan clan, Clan other) {
        ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, other);

        byte color = clanRelation.getMapColor();
        if (clan.getName().equals("Fields")) {
            color = 62;
        }
        if (clan.getName().equals("Red Shops") || clan.getName().equals("Red Spawn")) {
            color = 114;
        }
        if (clan.getName().equals("Blue Shops") || clan.getName().equals("Blue Spawn")) {
            color = -127;
        }
        if (clan.getName().equals("Outskirts")) {
            color = 74;
        }
        for (Player member : other.getOnlinePlayers()) {
            if (!getManager().getMapSettings().containsKey(member.getUniqueId())) {
                getManager().getMapSettings().put(member.getUniqueId(), new MapSettings(member.getLocation().getBlockX(), member.getLocation().getBlockZ()));
            }
            Set<ChunkData> clanMapData = (getManager()).getMapSettings().get(member.getUniqueId()).getClanMapData();
            clanMapData.stream().filter(chunkData -> chunkData.getClan().equals(clan.getName())).forEach(chunkData -> {
                System.out.println(clanRelation.name());
                chunkData.setColor(clanRelation.getMapColor());
            });
            if (clanMapData.stream().noneMatch(chunkData -> chunkData.getClan().equals(clan.getName()))) {
                for (String claim : clan.getClaims()) {
                    String[] split = claim.split(":");
                    clanMapData.add(new ChunkData(split[0], color, Integer.parseInt(split[1]), Integer.parseInt(split[2]), clan.getName()));
                }
            }
            updateStatus(member);
        }
        for (Player member : clan.getOnlinePlayers()) {
            if (!getManager().getMapSettings().containsKey(member.getUniqueId())) {
                getManager().getMapSettings().put(member.getUniqueId(), new MapSettings(member.getLocation().getBlockX(), member.getLocation().getBlockZ()));
            }
            Set<ChunkData> clanMapData = (getManager()).getMapSettings().get(member.getUniqueId()).getClanMapData();
            clanMapData.stream().filter(chunkData -> chunkData.getClan().equals(other.getName())).forEach(chunkData -> chunkData.setColor(clanRelation.getMapColor()));
            if (clanMapData.stream().noneMatch(chunkData -> chunkData.getClan().equals(other.getName()))) {
                for (String claim : other.getClaims()) {
                    String[] split = claim.split(":");
                    clanMapData.add(new ChunkData(split[0], color, Integer.parseInt(split[1]), Integer.parseInt(split[2]), other.getName()));
                }
            }
            updateStatus(member);
        }
    }

    private void loadChunks(Player player) {
        if (!getManager().getMapSettings().containsKey(player.getUniqueId())) {
            getManager().getMapSettings().put(player.getUniqueId(), new MapSettings(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
        }

        Set<ChunkData> chunkClaimColor = getManager().getMapSettings().get(player.getUniqueId()).getClanMapData();

        Clan pClan = getManager(ClanManager.class).getClan(player);
        for (Clan clan : getManager(ClanManager.class).getClanMap().values()) {
            ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(pClan, clan);
            for (String claim : clan.getClaims()) {

                byte color = clanRelation.getMapColor();
                if (clan.getName().equals("Fields")) {
                    color = 62;
                }
                if (clan.getName().equals("Red Shops") || clan.getName().equals("Red Spawn")) {
                    color = 114;
                }
                if (clan.getName().equals("Blue Shops") || clan.getName().equals("Blue Spawn")) {
                    color = -127;
                }
                if (clan.getName().equals("Outskirts")) {
                    color = 74;
                }
                String[] split = claim.split(":");
                ChunkData e = new ChunkData(split[0], color, Integer.parseInt(split[1]), Integer.parseInt(split[2]), clan.getName());

                for (int i = 0; i < 4; i++) {
                    BlockFace blockFace = BlockFace.values()[i];
                    Clan other = getManager(MapManager.class).getManager(ClanManager.class).getClan(player.getWorld().getName(), e.getX() + blockFace.getModX(), e.getZ() + blockFace.getModZ());
                    if (other != null && e.getClan().equals(other.getName())) {
                        e.getBlockFaceSet().add(blockFace);
                    }
                }
                chunkClaimColor.add(e);
            }
        }
        updateStatus(player);
    }

    private void updateClaims(Clan clan) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, getManager(ClanManager.class).getClan(online));

            byte color = clanRelation.getMapColor();
            if (clan.getName().equals("Fields")) {
                color = 62;
            }
            if (clan.getName().equals("Red Shops") || clan.getName().equals("Red Spawn")) {
                color = 114;
            }
            if (clan.getName().equals("Blue Shops") || clan.getName().equals("Blue Spawn")) {
                color = (byte) 129;
            }
            if (clan.getName().equals("Outskirts")) {
                color = 74;
            }
            if (!getManager().getMapSettings().containsKey(online.getUniqueId())) {
                getManager().getMapSettings().put(online.getUniqueId(), new MapSettings(online.getLocation().getBlockX(), online.getLocation().getBlockZ()));
            }
            getManager().getMapSettings().get(online.getUniqueId()).getClanMapData().removeIf(chunkData -> getManager(ClanManager.class).getClan(online.getWorld().getName(), chunkData.getX(), chunkData.getZ()) == null);
            for (String claim : clan.getClaims()) {
                String[] split = claim.split(":");
                String world = split[0];
                int x = Integer.parseInt(split[1]);
                int z = Integer.parseInt(split[2]);
                if (getManager().getMapSettings().get(online.getUniqueId()).getClanMapData().stream().noneMatch(chunkData -> (chunkData.getX() == x && chunkData.getZ() == z && chunkData.getClan().equals(clan.getName())))) {
                    ChunkData e = new ChunkData(world, color, x, z, clan.getName());
                    getManager().getMapSettings().get(online.getUniqueId()).getClanMapData().add(e);
                }
            }
            for (ChunkData chunkData : getManager().getMapSettings().get(online.getUniqueId()).getClanMapData()) {
                chunkData.getBlockFaceSet().clear();
                for (int i = 0; i < 4; i++) {
                    BlockFace blockFace = BlockFace.values()[i];
                    Clan other = getManager(MapManager.class).getManager(ClanManager.class).getClan(online.getWorld().getName(), chunkData.getX() + blockFace.getModX(), chunkData.getZ() + blockFace.getModZ());
                    if (other != null && chunkData.getClan().equals(other.getName())) {
                        chunkData.getBlockFaceSet().add(blockFace);
                    }
                }
            }
            updateStatus(online);
        }

    }

    private void updateStatus(Player player) {
        if (!getManager().getMapSettings().containsKey(player.getUniqueId())) {
            getManager().getMapSettings().put(player.getUniqueId(), new MapSettings(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
        }
        getManager().getMapSettings().get(player.getUniqueId()).setUpdate(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.MAP) {
            return;
        }
        if (!event.getAction().name().contains("RIGHT") && !event.getAction().name().contains("LEFT")) {
            return;
        }
        if (!getManager().getMapSettings().containsKey(player.getUniqueId())) {
            getManager().getMapSettings().put(player.getUniqueId(), new MapSettings(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
        }
        MapSettings mapSettings = getManager().getMapSettings().get(player.getUniqueId());

//        if (!((RechargeManager) getManager(RechargeManager.class)).use(player, "Map Zoom", 250L, false, false)) {
//            return;
//        }

        if (event.getAction().name().contains("RIGHT")) {
            MapSettings.Scale curScale = mapSettings.getScale();

            if (curScale == MapSettings.Scale.FAR) {
                return;
            }
            UtilPlayer.sendActionBar(player, createZoomBar(mapSettings.setScale(MapSettings.Scale.values()[curScale.ordinal() + 1])));
            mapSettings.setUpdate(true);
        } else if (event.getAction().name().contains("LEFT")) {
            MapSettings.Scale curScale = mapSettings.getScale();

            if (curScale == MapSettings.Scale.CLOSEST) {
                return;
            }
            UtilPlayer.sendActionBar(player, createZoomBar(mapSettings.setScale(MapSettings.Scale.values()[curScale.ordinal() - 1])));
            mapSettings.setUpdate(true);
        }
    }

    private String createZoomBar(MapSettings.Scale scale) {
        return ChatColor.WHITE + "Zoom Factor: " + ChatColor.GREEN + (1 << scale.getValue()) + "x";
    }
}