package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

public class InstantRespawn extends SpigotModule<GameManager> implements Listener {

    public InstantRespawn(GameManager manager) {
        super(manager, "InstantRespawn");
    }

    @EventHandler
    public void respawn(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (p.isInsideVehicle()) {
            Entity mount = p.getVehicle();
            mount.eject();
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> {
            PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);

            (((CraftPlayer) p).getHandle()).playerConnection.a(packet);
            p.setVelocity(new Vector(0, 0, 0));
        }, 2L);
    }
}