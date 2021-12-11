package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Titles;
import de.zerakles.utils.ZoneTypes;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;


public class InventoryMasterListener implements Listener {

    @EventHandler
    public void onManagerInv(InventoryClickEvent inventoryClickEvent){
        if(inventoryClickEvent.getWhoClicked() instanceof Player){
            if(inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§l§8Manage Clan")){
                inventoryClickEvent.setCancelled(true);
                if((inventoryClickEvent.getCurrentItem() != null) && (inventoryClickEvent.getCurrentItem().getType() != Material.AIR)){
                    if(inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()){
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§aJoin Clan")){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            Clan.getClan().getClanAPI().openJoinClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                            return;
                        }
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§aCreate Clan")){
                            TextComponent message = new net.md_5.bungee.api.chat.TextComponent("§bClick here to create a Clan!");
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/clan create"));
                            Player player = ((Player) inventoryClickEvent.getWhoClicked()).getPlayer();
                            player.spigot().sendMessage(message);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        return;
    }

    private ClanAPI getClanApi(){
        return Clan.getClan().getClanAPI();
    }

    private Data getData(){
        return Clan.getClan().data;
    }

    private void invitePlayer(Player player, String playerName) {
        if(Bukkit.getPlayer(playerName)!=null){
            if(getClanApi().playerExists(player.getUniqueId().toString())){
                Player secondPlayer = Bukkit.getPlayer(playerName);
                if(!getClanApi().playerExists(secondPlayer.getUniqueId().toString())){
                    if(getData().clanInvitations.containsKey(secondPlayer)){
                        ArrayList<String> clans = getData().clanInvitations.get(secondPlayer);
                        if(clans.size() == 20){
                            player.sendMessage(getData().prefix + getData().toMuchInvitations);
                            return;
                        }
                        String clanName = getClanApi().getClan(player.getUniqueId().toString());
                        if(clans.contains(clanName)){
                            player.sendMessage(getData().prefix + getData().alreadyHaveBeenInvited);
                            return;
                        }
                        clans.add(clanName);
                        getData().clanInvitations.remove(secondPlayer);
                        getData().clanInvitations.put(secondPlayer, clans);
                        player.sendMessage(getData().prefix + getData().hasBeenInvited);
                        secondPlayer.sendMessage(getData().prefix + getData().newClanInvitation);
                        return;
                    } else {
                        ArrayList<String> clans = new ArrayList<>();
                        String clanName = getClanApi().getClan(player.getUniqueId().toString());
                        clans.add(clanName);
                        getData().clanInvitations.put(secondPlayer, clans);
                        player.sendMessage(getData().prefix + getData().hasBeenInvited);
                        secondPlayer.sendMessage(getData().prefix + getData().newClanInvitation);
                        return;
                    }
                } else {
                    player.sendMessage(getData().prefix + getData().alreadyHaveAClan);
                    return;
                }
            } else {
                player.sendMessage(getData().prefix + getData().dontHaveAClan);
                return;
            }
        } else {
            player.sendMessage(getData().prefix + getData().notOnline);
        }
        return;
    }

    @EventHandler
    public void onClanInvites(InventoryClickEvent inventoryClickEvent){
        if(inventoryClickEvent.getWhoClicked() instanceof Player){
            if(inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§l§8Invite Players")){
                inventoryClickEvent.setCancelled(true);
                if((inventoryClickEvent.getCurrentItem() != null) && (inventoryClickEvent.getCurrentItem().getType() != Material.AIR)){
                    if(inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()){
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§7← Go Back")){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            Clan.getClan().getClanAPI().openManageClanEx(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                            return;
                        }
                        if(inventoryClickEvent.getCurrentItem().getType() == Material.SKULL_ITEM){
                            for (Player all: Bukkit.getOnlinePlayers()
                                 ) {
                                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                        .equalsIgnoreCase("§l§a" + all.getName())){
                                    if(!getClanApi().playerExists(all.getUniqueId().toString())){
                                        invitePlayer(((Player) inventoryClickEvent.getWhoClicked()).getPlayer(), all.getName());
                                        return;
                                    }
                                }
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        return;
    }

    @EventHandler
    public void onClanManageEx(InventoryClickEvent inventoryClickEvent){
        if(inventoryClickEvent.getWhoClicked() instanceof Player){
            if(inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§l§8Manage Clan")){
                inventoryClickEvent.setCancelled(true);
                if((inventoryClickEvent.getCurrentItem() != null) && (inventoryClickEvent.getCurrentItem().getType() != Material.AIR)){
                    if(inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()){
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§aInvites")){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            Clan.getClan().getClanAPI().openInviteToClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                            return;
                        }
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§aLeave")){
                            leaveClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer(), getClanApi().getClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer().getUniqueId().toString()));
                            return;
                        }
                        if(inventoryClickEvent.getCurrentItem().getType() == Material.SKULL_ITEM){
                            if(inventoryClickEvent.getClick().isRightClick()){
                                for(Player all: Bukkit.getOnlinePlayers()){
                                    if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§a" + all.getName())){
                                        demoteClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer(), all.getName());
                                        return;
                                    }
                                }
                            } else if(inventoryClickEvent.getClick().isLeftClick()){
                                for(Player all: Bukkit.getOnlinePlayers()){
                                    if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§a" + all.getName())){
                                        promoteClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer(), all.getName());
                                        return;
                                    }
                                }
                            }
                        }
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§aTerritory")){
                            if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                                unClaimZone(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                                return;
                            }
                            if(inventoryClickEvent.getClick().isShiftClick() && inventoryClickEvent.getClick().isRightClick()){
                                unClaimAll(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                                return;
                            }
                            if(inventoryClickEvent.getClick().isLeftClick()){
                                claimPlayer(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        return;
    }

    @EventHandler
    public void onJoinInv(InventoryClickEvent inventoryClickEvent){
        if(inventoryClickEvent.getWhoClicked() instanceof Player){
            if(inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§l§8Join Clan")){
                inventoryClickEvent.setCancelled(true);
                if((inventoryClickEvent.getCurrentItem() != null) && (inventoryClickEvent.getCurrentItem().getType() != Material.AIR)){
                    if(inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()){
                        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§7← Go Back")){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            Clan.getClan().getClanAPI().openManageClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                            return;
                        }
                        if(inventoryClickEvent.getCurrentItem().getType() == Material.PAPER){
                            ArrayList<String> invs = getData().clanInvitations.get(((Player) inventoryClickEvent.getWhoClicked()).getPlayer());
                            for (String string: invs
                                 ) {
                                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().contains(string)){
                                    joinClan(((Player) inventoryClickEvent.getWhoClicked()).getPlayer(), string);
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        return;
    }

    private void demoteClan(Player player, String clanName) {
        if(Bukkit.getPlayer(clanName)!=null){
            Player target = Bukkit.getPlayer(clanName);
            if(!getClanApi().playerExists(player.getUniqueId().toString())){
                player.sendMessage(getData().prefix + getData().dontHaveAClan);
                return;
            }
            if(!getClanApi().playerExists(target.getUniqueId().toString())){
                player.sendMessage(getData().prefix + getData().notInYourClan);
                return;
            }
            if(!getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString()) ||
                    getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.ADMIN.toString())){
                player.sendMessage(getData().prefix + getData().noPerms);
                return;
            }
            if(getClanApi().getClan(player.getUniqueId().toString()) == getClanApi().getClan(target.getUniqueId().toString())){
                player.sendMessage(getData().prefix + getData().notInYourClan);
                return;
            }
            String currentTitle = getClanApi().getTitle(target.getUniqueId().toString());
            if(currentTitle.equalsIgnoreCase(Titles.MEMBER.toString())){
                getClanApi().updateTitle(target.getUniqueId().toString(), Titles.RECRUIT.toString());
                player.sendMessage(getData().prefix + getData().demoted.replaceAll("%player%",
                        target.getName()).replaceAll("%title%", Titles.RECRUIT.toString()));
                target.sendMessage(getData().prefix + getData().gotDemoted.replaceAll("%title%",
                        Titles.RECRUIT.toString()));
                return;
            }
            if(currentTitle.equalsIgnoreCase(Titles.ADMIN.toString())
                    && getClanApi().getTitle(player.getUniqueId().toString())
                    .equalsIgnoreCase(Titles.LEADER.toString())){
                getClanApi().updateTitle(target.getUniqueId().toString(), Titles.MEMBER.toString());
                getClanApi().updateClanOwner(getClanApi().getClan(player.getUniqueId().toString()), target.getUniqueId().toString());
                player.sendMessage(getData().demoted.replaceAll("%player%", target.getName()).replaceAll("%title%", Titles.MEMBER.toString()));
                target.sendMessage(getData().gotDemoted.replaceAll("%title%", Titles.MEMBER.toString()));
                return;
            }
            player.sendMessage(getData().prefix + getData().noPerms);
            return;
        } else {
            player.sendMessage(getData().prefix + getData().notOnline);
            return;
        }
    }

    private void promoteClan(Player player, String clanName) {
        if(Bukkit.getPlayer(clanName)!=null){
            Player target = Bukkit.getPlayer(clanName);
            if(!getClanApi().playerExists(player.getUniqueId().toString())){
                player.sendMessage(getData().prefix + getData().dontHaveAClan);
                return;
            }
            if(!getClanApi().playerExists(target.getUniqueId().toString())){
                player.sendMessage(getData().prefix + getData().notInYourClan);
                return;
            }
            if(!getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString()) ||
                    getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.ADMIN.toString())){
                player.sendMessage(getData().prefix + getData().noPerms);
                return;
            }
            if(getClanApi().getClan(player.getUniqueId().toString()) == getClanApi().getClan(target.getUniqueId().toString())){
                player.sendMessage(getData().prefix + getData().notInYourClan);
                return;
            }
            String currentTitle = getClanApi().getTitle(target.getUniqueId().toString());
            if(currentTitle.equalsIgnoreCase(Titles.RECRUIT.toString())){
                getClanApi().updateTitle(target.getUniqueId().toString(), Titles.MEMBER.toString());
                player.sendMessage(getData().prefix + getData().promoted.replaceAll("%player%",
                        target.getName()).replaceAll("%title%", Titles.MEMBER.toString()));
                target.sendMessage(getData().prefix + getData().gotPromoted.replaceAll("%title%",
                        Titles.MEMBER.toString()));
                return;
            }
            if(currentTitle.equalsIgnoreCase(Titles.MEMBER.toString())){
                getClanApi().updateTitle(target.getUniqueId().toString(), Titles.ADMIN.toString());
                player.sendMessage(getData().prefix + getData().promoted.replaceAll("%player%",
                        target.getName()).replaceAll("%title%", Titles.ADMIN.toString()));
                target.sendMessage(getData().prefix + getData().gotPromoted.replaceAll("%title%",
                        Titles.ADMIN.toString()));
                return;
            }
            if(currentTitle.equalsIgnoreCase(Titles.ADMIN.toString())
                    && getClanApi().getTitle(player.getUniqueId().toString())
                    .equalsIgnoreCase(Titles.LEADER.toString())){
                getClanApi().updateTitle(player.getUniqueId().toString(), Titles.ADMIN.toString());
                getClanApi().updateTitle(target.getUniqueId().toString(), Titles.LEADER.toString());
                getClanApi().updateClanOwner(getClanApi().getClan(player.getUniqueId().toString()), target.getUniqueId().toString());
                player.sendMessage(getData().gotDemoted.replaceAll("%title%", Titles.ADMIN.toString()));
                target.sendMessage(getData().gotPromoted.replaceAll("%title%", Titles.LEADER.toString()));
                return;
            }
            player.sendMessage(getData().prefix + getData().noPerms);
            return;
        } else {
            player.sendMessage(getData().prefix + getData().notOnline);
            return;
        }
    }

    private void joinClan(Player player, String clanName) {
        if(getClanApi().playerExists(player.getUniqueId().toString())){
            player.sendMessage(getData().prefix + getData().alreadyHaveAClan);
            return;
        }
        if(!getClanApi().clanExists(clanName)){
            player.sendMessage(getData().prefix + getData().clanDoesNotExist);
            return;
        }
        if(!getData().clanInvitations.containsKey(player)){
            player.sendMessage(getData().prefix + getData().didntSendYouAnInvitation);
            return;
        }
        HashMap<String, String> members = getClanApi().getAllClanMembers(clanName);
        if(members.size() == 20){
            player.sendMessage(getData().prefix + getData().clanIsFull);
            return;
        }
        ArrayList<String> clans = getData().clanInvitations.get(player);
        if(!clans.contains(clanName)){
            player.sendMessage(getData().prefix + getData().didntSendYouAnInvitation);
            return;
        }
        getClanApi().createPlayer(player.getUniqueId().toString(), clanName, Titles.RECRUIT.toString());
        player.sendMessage(getData().prefix + getData().youJoinedAClan.replaceAll("%clan%", clanName));
        for (Player player1:Bukkit.getOnlinePlayers()
        ) {
            if(members.containsKey(player1.getUniqueId().toString())){
                player1.sendMessage(getData().prefix + getData().playerJoinedYourClan.replaceAll("%player%", player.getName()));
            }
        }
        return;
    }

    private void leaveClan(Player player, String clanName) {
        if(!getClanApi().playerExists(player.getUniqueId().toString())){
            player.sendMessage(getData().prefix + getData().dontHaveAClan);
            return;
        }
        if(clanName.equalsIgnoreCase(getClanApi().getClan(player.getUniqueId().toString()))){
            getClanApi().deletePlayer(player.getUniqueId().toString());
            if(getData().inClanChat.contains(player)){
                getData().inClanChat.remove(player);
            }
            if(getClanApi().getOwner(clanName).equalsIgnoreCase(player.getUniqueId().toString())){
                HashMap<String, String> players = new HashMap<>();
                for (String string:players.keySet()
                ) {
                    getClanApi().deletePlayer(string);
                }
                getClanApi().deleteClan(clanName);
            }
            player.sendMessage(getData().prefix + getData().leftYourClan);
        }
    }

    private void claimPlayer(Player player) {
        if(getClanApi().playerExists(player.getUniqueId().toString())){
            if(getClanApi().isSpawn(player.getLocation())){
                player.sendMessage(getData().prefix + getData().cantClaimTheSpawn);
                return;
            }
            if(getClanApi().getChunkOwner(player.getLocation()) != null){
                player.sendMessage(getData().prefix + getData().chunkAlreadyClaimed);
                return;
            }
            String clan = getClanApi().getClan(player.getUniqueId().toString());
            HashMap<String, String> members = getClanApi().getAllClanMembers(clan);
            int i = members.size();
            ArrayList<Chunk> chunks = getClanApi().getChunks(clan);
            int b = 0;
            if(members == null){
                i = 1;
            }
            if(chunks != null){
                b = chunks.size();
            }
            if(i*3>b){
                getClanApi().registerChunk(player.getLocation(), clan);
                player.sendMessage(getData().prefix + getData().chunkHaveBeenClaimed);
                return;
            }
            player.sendMessage(getData().prefix + getData().clanReachedMaximumOfChunks);
            return;
        }
    }

    private void unClaimAll(Player player) {
        if(getClanApi().clanExists(getClanApi().getClan(player.getUniqueId().toString()))) {
            if (!getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString())){
                player.sendMessage(getData().prefix + getData().noPerms);
                return;
            }
            ArrayList<Chunk> chunks = getClanApi().getChunks(getClanApi().getClan(player.getUniqueId().toString()));
            for (Chunk chunk:chunks
            ) {
                getClanApi().deleteChunk(chunk, getClanApi().getClan(player.getUniqueId().toString()));
                continue;
            }
            player.sendMessage(getData().prefix + getData().chunksDeleted);
            return;
        }
    }

    private void unClaimZone(Player player) {
        if(getClanApi().clanExists(getClanApi().getClan(player.getUniqueId().toString()))) {
            if (!getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString())) {
                player.sendMessage(getData().prefix + getData().noPerms);
                return;
            }
            if(getClanApi().getZone(player.getLocation(), player) == ZoneTypes.CLAN){
                getClanApi().deleteChunk(player.getLocation(), getClanApi().getClan(player.getUniqueId().toString()));
                player.sendMessage(getData().prefix + getData().chunkDeleted);
                return;
            } else {
                player.sendMessage(getData().prefix + getData().notYourChunk);
                return;
            }
        }
    }
}