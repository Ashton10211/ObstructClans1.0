package com.obstruct.clans.rune.runes.weapons;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Haste extends Rune {

    public Haste(RuneManager manager) {
        super(manager, "Haste");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.RED + getName() + " ";
    }

    @Override
    public double getModifier() {
        return 0;
    }

    @Override
    public String getLore(double modifier) {
        //Add into rune framework to support multiple modifiers
        //Create a nms tag based on specific rune and add tag to item rune list
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return UtilItem.isSword(itemStack.getType());
    }
}