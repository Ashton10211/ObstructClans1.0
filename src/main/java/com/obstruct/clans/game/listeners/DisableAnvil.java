package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.shared.update.Update;
import com.obstruct.core.shared.update.Updater;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisableAnvil extends SpigotModule<GameManager> implements Updater {

    public DisableAnvil(GameManager manager) {
        super(manager, "DisableAnvil");
    }

    @EventHandler
    public void AnvilDisable(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getClickedBlock().getType() != Material.ANVIL) {
            return;
        }
        UtilMessage.message(event.getPlayer(), "Game", ChatColor.YELLOW + UtilFormat.cleanString(event.getClickedBlock().getType().toString()) + ChatColor.GRAY + " is disabled.");
        event.setCancelled(true);
    }
}
