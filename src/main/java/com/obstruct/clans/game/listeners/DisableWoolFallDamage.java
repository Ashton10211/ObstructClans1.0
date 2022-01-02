package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.combat.event.CustomDamageEvent;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DisableWoolFallDamage extends SpigotModule<GameManager> implements Listener {

    public DisableWoolFallDamage(GameManager manager) {
        super(manager, "DisableWoolFallDamage");
    }

    @EventHandler
    public void onWoolFall(CustomDamageEvent event) {
        if (event.getDamageePlayer() != null && event.getDamageCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getDamagee();
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WOOL || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.CARPET)
                event.setCancelled(true);
        }
    }
}