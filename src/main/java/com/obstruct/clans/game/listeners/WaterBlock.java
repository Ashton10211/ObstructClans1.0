package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class WaterBlock extends SpigotModule<GameManager> implements Listener {

    public WaterBlock(GameManager manager) {
        super(manager, "WaterBlock");
    }

    @EventHandler
    public void onLapisBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.LAPIS_BLOCK) {
            return;
        }
        event.getBlock().setTypeId(8);
        event.getBlock().getState().update();
        event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.STEP_SOUND, 8);
        event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.SPLASH, 2.0F, 1.0F);
    }
}