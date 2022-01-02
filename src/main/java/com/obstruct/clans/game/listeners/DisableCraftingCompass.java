package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class DisableCraftingCompass extends SpigotModule<GameManager> implements Listener {

    public DisableCraftingCompass(GameManager manager) {
        super(manager, "DisableCraftingCompass");
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        if (itemType == Material.COMPASS)
            e.getInventory().setResult(new ItemStack(Material.AIR));
    }
}
