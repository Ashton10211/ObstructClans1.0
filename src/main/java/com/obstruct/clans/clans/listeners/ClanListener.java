package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.*;
import com.obstruct.clans.clans.events.*;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.spigot.blockregen.BlockRegenManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ClanListener extends SpigotModule<ClanManager> implements Listener {

    public ClanListener(ClanManager manager) {
        super(manager, "ClanListener");
    }

    @EventHandler
    public void onClanMemberJoin(PlayerJoinEvent event) {
        Clan clan = getManager().getClan(event.getPlayer());
        if (clan == null) {
            return;
        }
        ClanMember clanMember = clan.getClanMember(event.getPlayer().getUniqueId());
        if (!clanMember.getPlayerName().equals(event.getPlayer().getName())) {
            clanMember.setPlayerName(event.getPlayer().getName());
            getManager(MongoManager.class).getDatastore().save(clan);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanAlly(ClanAllyEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = event.getClan();
        Clan target = event.getOther();

        clan.getAllianceRequestMap().remove(target.getName());
        target.getAllianceRequestMap().remove(clan.getName());
        clan.getAlliance().put(target.getName(), false);
        target.getAlliance().put(clan.getName(), false);

        UtilMessage.message(player, "Clans", "You have accepted alliance with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
        target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has accepted alliance with your Clan.");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has accepted alliance with your Clan.", player.getUniqueId());

        getExecutorService().execute(() -> {
            getManager(ClanManager.class).saveClan(clan);
            getManager(ClanManager.class).saveClan(target);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanClaim(ClanClaimEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = event.getClan();
        Chunk chunk = event.getChunk();
        if (event.isOutline()) {
            getManager(BlockRegenManager.class).outlineChunk(chunk, Material.GLOWSTONE);
        }
        clan.getClaims().add(UtilFormat.chunkToString(chunk));
        if (event.isMessage()) {
            UtilMessage.message(player, "Clans", "You claimed Territory " + ChatColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + ")" + ChatColor.GRAY + ".");
            clan.inform(true, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " claimed Territory " + ChatColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + ")" + ChatColor.GRAY + ".", player.getUniqueId());
        }
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanCreate(ClanCreateEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = event.getClan();
        clan.getMembers().add(new ClanMember(player, MemberRole.LEADER));
        getManager(ClanManager.class).addClan(clan);
        UtilMessage.broadcast("Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " formed " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanDemote(ClanDemoteEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Client target = event.getTarget();
        Clan clan = event.getClan();

        clan.getClanMember(target.getUuid()).demote();
        UtilMessage.message(player, "Clans", "You demoted " + ChatColor.AQUA + target.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + UtilFormat.cleanString(clan.getClanMember(target.getUuid()).getMemberRole().name()) + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " demoted " + ChatColor.AQUA + target.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + UtilFormat.cleanString(clan.getClanMember(target.getUuid()).getMemberRole().name()) + ChatColor.GRAY + ".", player.getUniqueId());

        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanDisband(ClanDisbandEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Player player = event.getPlayer();
        ClanManager manager = getManager(ClanManager.class);
        for (Player online : Bukkit.getOnlinePlayers()) {
            ClanRelation clanRelation = manager.getClanRelation(clan, manager.getClan(online));
            UtilMessage.message(online, "Clans", clanRelation.getSuffix() + player.getName() + ChatColor.GRAY + " has disbanded " + clanRelation.getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        }
        manager.removeClan(clan);
        getExecutorService().execute(() -> manager.deleteClan(clan));
    }

//    @EventHandler(priority = EventPriority.LOWEST)
//    public void onClanEnemy(ClanEnemyEvent event) {
//        if (event.isCancelled()) {
//            return;
//        }
//        Clan clan = event.getClan();
//        Clan target = event.getOther();
//        Player player = event.getPlayer();
//
//        if (clan.isAllied(target)) {
//            clan.getAllianceMap().remove(target.getName());
//            target.getAllianceMap().remove(clan.getName());
//        }
//        clan.getAllianceRequestMap().remove(target.getName());
//        target.getAllianceRequestMap().remove(clan.getName());
//        clan.getEnemyMap().put(target.getName(), 0);
//        target.getEnemyMap().put(clan.getName(), 0);
//
//        UtilMessage.message(player, "Clans", "You waged war with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
//        target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " waged war with your Clan.");
//        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " waged war with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
//
//        getExecutorService().execute(() -> {
//            getManager(ClanManager.class).saveClan(clan);
//            getManager(ClanManager.class).saveClan(target);
//        });
//    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanHome(ClanHomeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = event.getClan();

        player.teleport(clan.getHome().getBlock().getLocation());
        UtilMessage.message(player, "Clans", "You teleported to Clan Home.");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanInvite(ClanInviteEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Player player = event.getPlayer();
        Player target = event.getTarget();

        clan.getInviteeMap().put(target.getUniqueId(), System.currentTimeMillis());
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " invited " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to the Clan.", player.getUniqueId());
        UtilMessage.message(player, "Clans", "You invited " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        UtilMessage.message(target, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has invited you to join " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanJoin(ClanJoinEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Clan target = event.getClan();

        target.getInviteeMap().remove(player.getUniqueId());
        target.getMembers().add(new ClanMember(player, MemberRole.RECRUIT));
        UtilMessage.message(player, "Clans", "You joined " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".");
        target.inform(true, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " joined the Clan.", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(target));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanKick(ClanKickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Player player = event.getPlayer();
        Client target = event.getTarget();

        clan.getMembers().remove(clan.getClanMember(target.getUuid()));
        UtilMessage.message(player, "Clans", "You kicked " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " from the Clan.");
        if (Bukkit.getPlayer(target.getUuid()) != null) {
            UtilMessage.message(Bukkit.getPlayer(target.getUuid()), "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " kicked you from " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        }
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " kicked " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " from the Clan.", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanLeave(ClanLeaveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = event.getClan();

        clan.getMembers().remove(clan.getClanMember(player.getUniqueId()));
        UtilMessage.message(player, "Clans", "You left " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " left the Clan.", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onNeutralClan(ClanNeutralEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Clan target = event.getOther();
        Player player = event.getPlayer();
        //if (clan.isAllied(target)) {
        clan.getAlliance().remove(target.getName());
        target.getAlliance().remove(clan.getName());
        UtilMessage.message(player, "Clans", "You are now neutral with " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", "Your Clan is now neutral with " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
        target.inform(true, "Clans", "Your Clan is now neutral with " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");

        getExecutorService().execute(() -> {
            getManager(ClanManager.class).saveClan(clan);
            getManager(ClanManager.class).saveClan(target);
        });
        //return;
        //}
//        UtilMessage.message(player, "Clans", "You have accepted neutrality with " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".");
//        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " accepted neutrality with " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
//        target.inform(true, "Clans", ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + " has accepted neutrality with your Clan.");
//        target.getAllianceRequestMap().remove(clan.getName());
//        clan.getAllianceRequestMap().remove(target.getName());
//        target.getNeutralRequestMap().remove(clan.getName());
//        clan.getNeutralRequestMap().remove(target.getName());
//
//        getExecutorService().execute(() -> {
//            getManager(ClanManager.class).saveClan(clan);
//            getManager(ClanManager.class).saveClan(target);
//        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanPromote(ClanPromoteEvent event) {
        Client target = event.getTarget();
        Clan clan = event.getClan();
        Player player = event.getPlayer();

        if (clan.getClanMember(target.getUuid()).getMemberRole() == MemberRole.ADMIN) {
            clan.getClanMember(player.getUniqueId()).setMemberRole(MemberRole.ADMIN);
        }
        clan.getClanMember(target.getUuid()).promote();
        UtilMessage.message(player, "Clans", "You promoted " + ChatColor.AQUA + target.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + UtilFormat.cleanString(clan.getClanMember(target.getUuid()).getMemberRole().name()) + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " promoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + UtilFormat.cleanString(clan.getClanMember(target.getUuid()).getMemberRole().name()) + ChatColor.GRAY + ".", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanSetHome(ClanSetHomeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Player player = event.getPlayer();

        clan.setHome(player.getLocation().getBlock().getLocation());
        UtilMessage.message(player, "Clans", "You set the Clan Home at " + ChatColor.YELLOW + "(" + (int) player.getLocation().getX() + ", " + (int) player.getLocation().getZ() + ")" + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has set the Clan Home at " + ChatColor.YELLOW + "(" + (int) player.getLocation().getX() + "," + (int) player.getLocation().getZ() + ")" + ChatColor.GRAY + ".", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanTrust(ClanTrustEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Clan target = event.getOther();
        Player player = event.getPlayer();

        target.getTrustRequestMap().remove(clan.getName());
        clan.getTrustRequestMap().remove(target.getName());
        clan.getAlliance().put(target.getName(), true);
        target.getAlliance().put(clan.getName(), true);

        UtilMessage.message(player, "Clans", "You have accepted trust with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " accepted trust with your Clan.", player.getUniqueId());
        target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has accepted trust with your Clan.");

        getExecutorService().execute(() -> {
            getManager(ClanManager.class).saveClan(clan);
            getManager(ClanManager.class).saveClan(target);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanRevokeTrust(ClanRevokeTrustEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Clan target = event.getOther();
        Player player = event.getPlayer();

        clan.getAlliance().put(target.getName(), false);
        target.getAlliance().put(clan.getName(), false);
        UtilMessage.message(player, "Clans", "You have revoked trust with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has revoked trust with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
        target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has revoked trust with your Clan.");

        getExecutorService().execute(() -> {
            getManager(ClanManager.class).saveClan(clan);
            getManager(ClanManager.class).saveClan(target);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanUnclaimAll(ClanUnclaimAllEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Player player = event.getPlayer();

        clan.setHome(null);
        clan.getClaims().clear();

        UtilMessage.message(player, "Clans", "You unclaimed all land.");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " unclaimed all land.", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanUnclaim(ClanUnclaimEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan clan = event.getClan();
        Player player = event.getPlayer();
        Chunk chunk = event.getChunk();

        if (clan.getHome() != null && clan.getHome().getChunk().equals(chunk)) {
            clan.setHome(null);
        }
        clan.getClaims().remove(UtilFormat.chunkToString(chunk));
        UtilMessage.message(player, "Clans", "You unclaimed land " + ChatColor.YELLOW + "(" + chunk.getX() + "," + ChatColor.YELLOW + chunk.getZ() + ChatColor.YELLOW + ")" + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " unclaimed land " + ChatColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + ")" + ChatColor.GRAY + ".", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }
}