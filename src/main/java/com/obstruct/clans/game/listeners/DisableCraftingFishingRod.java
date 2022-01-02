package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class DisableCraftingFishingRod extends SpigotModule<GameManager> implements Listener {

    public DisableCraftingFishingRod(GameManager manager) {
        super(manager, "DisableCraftingFishingRod");
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        if (itemType == Material.FISHING_ROD)
            e.getInventory().setResult(new ItemStack(Material.AIR));
    }
}
