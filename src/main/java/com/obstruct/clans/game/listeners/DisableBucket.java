package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

public class DisableBucket extends SpigotModule<GameManager> implements Listener {

    public DisableBucket(GameManager manager) {
        super(manager, "DisableBucket");
    }

    @EventHandler
    public void handleBucket(PlayerBucketEmptyEvent event) {
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(event.getPlayer().getUniqueId());
        if (client.isAdministrating()) {
            return;
        }
        event.setCancelled(true);
        UtilMessage.message(event.getPlayer(), "Game", "Your " + ChatColor.YELLOW + "Bucket" + ChatColor.GRAY + " broke!");
        event.getPlayer().setItemInHand(new ItemStack(Material.IRON_INGOT, event.getPlayer().getItemInHand().getAmount() * 3));
        event.getBlockClicked().getState().update();
    }

    @EventHandler
    public void handleBucket(PlayerBucketFillEvent event) {
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(event.getPlayer().getUniqueId());
        if (client.isAdministrating()) {
            return;
        }
        event.setCancelled(true);
        UtilMessage.message(event.getPlayer(), "Game", "Your " + ChatColor.YELLOW + "Bucket" + ChatColor.GRAY + " broke!");
        event.getPlayer().setItemInHand(new ItemStack(Material.IRON_INGOT, event.getPlayer().getItemInHand().getAmount() * 3));
        event.getBlockClicked().getState().update();
    }
}
