package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilItem;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class HandleCraftingIronDoors extends SpigotModule<GameManager> implements Listener {

    public HandleCraftingIronDoors(GameManager manager) {
        super(manager, "HandleCraftingIronDoors");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void IronDoor(PrepareItemCraftEvent event) {
        if (event.getRecipe().getResult() == null) {
            return;
        }
        Material type = event.getRecipe().getResult().getType();
        if (type != Material.WOOD_DOOR && type != Material.SPRUCE_DOOR_ITEM && type != Material.JUNGLE_DOOR_ITEM && type != Material.BIRCH_DOOR_ITEM && type != Material.ACACIA_DOOR_ITEM && type != Material.DARK_OAK_DOOR_ITEM) {
            return;
        }
        CraftingInventory inv = event.getInventory();
        inv.setResult(UtilItem.updateNames(new ItemStack(Material.IRON_DOOR, 2)));
    }
}