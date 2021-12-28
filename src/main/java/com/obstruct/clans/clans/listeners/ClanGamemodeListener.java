package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.events.ClanMoveEvent;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class ClanGamemodeListener extends SpigotModule<ClanManager> implements Listener {

    public ClanGamemodeListener(ClanManager manager) {
        super(manager, "ClanGamemodeListener");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClanMove(ClanMoveEvent event) {
        handleGamemode(event.getPlayer(), event.getLocTo());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        handleGamemode(event.getPlayer(), event.getPlayer().getLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        handleGamemode(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        handleGamemode(event.getPlayer(), event.getRespawnLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            handleGamemode(((Player) event.getEntered()).getPlayer(), event.getEntered().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVehicleExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            handleGamemode(((Player) event.getExited()).getPlayer(), event.getExited().getLocation());
        }
    }

    private void handleGamemode(Player player, Location location) {
        Clan clanTo = getManager().getClan(location);
        Clan clan = getManager().getClan(player.getUniqueId());
        if (getManager(SiegeManager.class).isSieging(clan, clanTo)) {
            if (player.getGameMode() == GameMode.ADVENTURE) {
                player.setGameMode(GameMode.SURVIVAL);
            }
            return;
        }
        if (clanTo == null || clanTo.getName().equalsIgnoreCase("Fields")) {
            if (player.getGameMode() == GameMode.ADVENTURE) {
                player.setGameMode(GameMode.SURVIVAL);
            }
            return;
        }
        if (!clanTo.equals(clan) && !clanTo.getName().equalsIgnoreCase("Fields") &&
                player.getGameMode() == GameMode.SURVIVAL)
            player.setGameMode(GameMode.ADVENTURE);
    }
}