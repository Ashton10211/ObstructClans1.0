package de.zerakles.cmds;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Titles;
import de.zerakles.utils.ZoneTypes;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ClanCommand implements CommandExecutor {
    private ClanAPI getClanApi(){
        return Clan.getClan().getClanAPI();
    }
    private Data getData(){
        return Clan.getClan().data;
    }
    private Clan getClan(){
        return Clan.getClan();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                handleClanCMD((Player)sender);
                return true;
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("claim")){
                    claimPlayer(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("unclaim")){
                    unClaimZone(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("home")){
                    home(player);
                    return true;
                }
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("create")){
                    createClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("home")){
                    setHome(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("join")){
                    joinClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("leave")){
                    leaveClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("promote")){
                    promoteClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("demote")){
                    demoteClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("invite")){
                    invitePlayer(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("kick")){
                    kickPlayer(player, args[0]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("neutral")){
                    neutralClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("ally")){
                    allyClan(player, args[1]);
                    return true;
                }
                if(args[0].equalsIgnoreCase("unclaim")){
                    unClaimAll(player);
                    return true;
                }
            }
        } else {
            sender.sendMessage(getData().prefix + getData().mustBeAPlayer);
        }
        return false;
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

    private void neutralClan(Player player, String clanName) {
        if(getClanApi().clanExists(clanName)) {
            if (!getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString())) {
                player.sendMessage(getData().prefix + getData().noPerms);
                return;
            }
            if(getData().allyClans.containsKey(getClanApi().getClan(player.getUniqueId().toString()))){
                if(getData().allyClans.get(getClanApi().getClan(player.getUniqueId().toString())).equalsIgnoreCase(clanName)){
                    getData().allyClans.remove(clanName);
                    getData().allyClans.remove(getClanApi().getClan(player.getUniqueId().toString()));
                    Bukkit.broadcastMessage(getData().prefix + getData().noLongerAllys
                            .replaceAll("%clan%", getClanApi().getClan(player.getUniqueId().toString())
                                    .replaceAll("%allyclan%", clanName)));
                    return;
                }
                player.sendMessage(getData().prefix + getData().clanIsNotAlly);
                return;
            }
        }
    }

    private void allyClan(Player player, String clanName) {
        if(getClanApi().clanExists(clanName)){
            if(!getClanApi().getTitle(player.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString())){
                player.sendMessage(getData().prefix +  getData().noPerms);
                return;
            }
            if(getData().allyClans.containsKey(getClanApi().getClan(player.getUniqueId().toString()))){
                player.sendMessage(getData().prefix + getData().cantHaveMoreAlly);
                return;
            }
            for (Player all: Bukkit.getOnlinePlayers()
            ) {
                if(getClanApi().playerExists(all.getUniqueId().toString())){
                    if(getClanApi().getClan(all.getUniqueId().toString()).equalsIgnoreCase(clanName)){
                        if(getClanApi().getTitle(all.getUniqueId().toString()).equalsIgnoreCase(Titles.LEADER.toString())){
                            TextComponent textComponent = new TextComponent();
                            textComponent.setText(getData().clickToBeAnAllyOf
                                    .replaceAll("%clan%",getClanApi().getClan(player.getUniqueId().toString())));
                            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/a accept"));
                            all.spigot().sendMessage(textComponent);
                        }
                    }
                }
            }
            getData().toAlly.put(clanName, getClanApi().getClan(player.getUniqueId().toString()));
            player.sendMessage(getData().prefix + getData().requestSend);
        } else {
            player.sendMessage(getData().prefix + getData().clanDoesNotExist);
        }
        return;
    }

    private void kickPlayer(Player player, String playerName) {
        if(Bukkit.getPlayer(playerName)!=null){
            if(!getClanApi().playerExists(player.getUniqueId().toString())){
                return;
            }
            if(getClanApi().getTitle(player.getUniqueId().toString()) != Titles.LEADER.toString() ||
                getClanApi().getTitle(player.getUniqueId().toString()) != Titles.ADMIN.toString()){
                    return;
            }
             if(getClanApi().playerExists(Bukkit.getPlayer(playerName).getUniqueId().toString())){
                 if(getClanApi().getClan(Bukkit.getPlayer(playerName).getUniqueId().toString())
                         == getClanApi().getClan(player.getUniqueId().toString())){
                     getClanApi().deletePlayer(Bukkit.getPlayer(playerName).getUniqueId().toString());
                     getClan().scoreboardMaster.sendScoreboard(Bukkit.getPlayer(playerName));
                     player.sendMessage(getData().prefix + getData().playerWasKicked);
                     return;
                 }
             }
        } else {
            player.sendMessage(getData().prefix + getData().notOnline);
        }
    }

    private void invitePlayer(Player player, String playerName) {
        if(Bukkit.getPlayer(playerName)!=null){
            if(getClanApi().playerExists(player.getUniqueId().toString())){
                Player secondPlayer = Bukkit.getPlayer(playerName);
                if(!getClanApi().playerExists(secondPlayer.getUniqueId().toString())){
                    if(getData().clanInvitations.containsKey(secondPlayer)){
                        ArrayList<String>clans = getData().clanInvitations.get(secondPlayer);
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
                player.sendMessage(getData().prefix + getData().notInYourClan);
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

    private void createClan(Player player, String clanName) {
        UUID uuid = UUID.randomUUID();
        if(getClanApi().clanExists(clanName)){
            player.sendMessage(getData().prefix + getData().clanWithThisNameAlreadyExists);
            return;
        }
        if(getClanApi().playerExists(player.getUniqueId().toString())){
            player.sendMessage(getData().prefix + getData().alreadyHaveAClan);
            return;
        }
        getClanApi().createClan(clanName, uuid.toString(), player.getUniqueId().toString());
        getClanApi().createPlayer(player.getUniqueId().toString(), clanName, Titles.LEADER.toString());
        player.sendMessage(getData().prefix + getData().newOwner.replaceAll("%player%", player.getName()));
        return;
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
            if(i+3>b){
                if(chunks != null){
                    boolean nearChunk = false;

                    Chunk c = player.getLocation().getChunk();

                    for (Chunk chunk : chunks){
                        for (int x = -1; x <= 1; x++) {
                            for (int z = -1; z <= 1; z++) {
                                Location newLoc = player.getLocation();
                                newLoc.setX(player.getLocation().getX() + x);
                                newLoc.setZ(player.getLocation().getZ() + z);
                                if(newLoc.getChunk().getX() == chunk.getX()
                                        && newLoc.getChunk().getZ() == chunk.getZ()){
                                    nearChunk = true;
                                    continue;
                                }
                                continue;
                            }
                            if(nearChunk == true){
                                continue;
                            }
                        }
                        if(nearChunk == true){
                            continue;
                        }
                    }

                    if(nearChunk){
                        getClanApi().registerChunk(player.getLocation(), clan);
                        player.sendMessage(getData().prefix + getData().chunkHaveBeenClaimed);
                        getClanApi().registerClaim(player.getLocation().getChunk());
                        return;
                    } else {
                        player.sendMessage(getData().prefix + getData().NeedClaimNextToOwnLand);
                        return;
                    }

                } else {
                    getClanApi().registerChunk(player.getLocation(), clan);
                    player.sendMessage(getData().prefix + getData().chunkHaveBeenClaimed);
                    getClanApi().registerClaim(player.getLocation().getChunk());
                    return;
                }
            }
            player.sendMessage(getData().prefix + getData().clanReachedMaximumOfChunks);
            return;
        }
    }

    private void home(Player player){
        if(getClanApi().hashHome(player.getUniqueId().toString())){
            player.teleport(getClanApi().getHome(player.getUniqueId().toString()));
            return;
        }
        player.sendMessage(getData().prefix + getData().noHomeRegistered);
        return;
    }

    private void setHome(Player player){
        if(getClanApi().hashHome(player.getUniqueId().toString())){
            getClanApi().deleteHome(player.getUniqueId().toString());
            getClanApi().registerHome(player.getUniqueId().toString(), player.getLocation());
            player.sendMessage(getData().prefix + getData().homeRegistered);
            return;
        }
        getClanApi().registerHome(player.getUniqueId().toString(), player.getLocation());
        player.sendMessage(getData().prefix + getData().homeRegistered);
        return;
    }

    private void handleClanCMD(Player player) {
        if(getClanApi().playerExists(player.getUniqueId().toString())){
            getClanApi().openManageClanEx(player);
            return;
        }
        getClanApi().openManageClan(player);
        return;
    }
}
