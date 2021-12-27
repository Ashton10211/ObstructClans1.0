package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.events.ClanMoveEvent;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ClanMovementListener extends SpigotModule<ClanManager> implements Listener {

    public ClanMovementListener(ClanManager manager) {
        super(manager, "ClanMovementListener");
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location from = event.getFrom();
        Clan clanTo = getManager(ClanManager.class).getClan(to);
        Clan clanFrom = getManager(ClanManager.class).getClan(from);
        if (clanTo == null && clanFrom == null) {
            return;
        }
        if (clanFrom == null || !clanFrom.equals(clanTo)) {
            Bukkit.getPluginManager().callEvent(new ClanMoveEvent(player, clanFrom, clanTo, from, to));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClanMove(ClanMoveEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Location location = event.getLocTo();
        UtilMessage.message(player, "Territory", getTerritoryString(player, location, false));
    }

    public String getTerritoryString(Player player, Location location, boolean sidebar) {
        String owner = ChatColor.YELLOW + "Wilderness";
        String append = "";
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        Clan target = getManager(ClanManager.class).getClan(location);
        if (target != null) {
            ClanRelation relation = getManager(ClanManager.class).getClanRelation(clan, target);
            owner = relation.getSuffix() + target.getName();
            if (target.isAdmin()) {
                owner = ChatColor.WHITE + target.getName();
                if (target.getName().equalsIgnoreCase("Outskirts")) {
                    owner = ChatColor.YELLOW + target.getName();
                }
                if (target.isSafe(location)) {
                    append = ChatColor.WHITE + "(" + ChatColor.AQUA + "Safe" + ChatColor.WHITE + ")";
                }
            }
            if (relation == ClanRelation.ALLY_TRUSTED) {
                append = ChatColor.WHITE + "(" + ChatColor.YELLOW + "Trusted" + ChatColor.WHITE + ")";
            }
            if (relation == ClanRelation.ENEMY && clan != null) {
                append = clan.getWarPointsString(target);
            }
            if (target.getName().equalsIgnoreCase("Fields") && !sidebar) {
                append = ChatColor.RED.toString() + ChatColor.BOLD + "                    Warning! " + ChatColor.GRAY + ChatColor.BOLD + "PvP Hotspot";
            }
        }
        return owner + " " + append;
    }
}
