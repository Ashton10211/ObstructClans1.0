package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DisableBedrock extends SpigotModule<GameManager> implements Listener {

    public DisableBedrock(GameManager manager) {
        super(manager, "DisableBedrock");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.getType() == Material.BEDROCK) {
            UtilMessage.message(player, "Game", "You cannot place " + ChatColor.YELLOW + UtilFormat.cleanString(block.getType().name()) + ChatColor.GRAY + ".");
            event.setCancelled(true);
        }
    }
}
