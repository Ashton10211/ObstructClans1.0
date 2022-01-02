package com.obstruct.clans.rune.runes.weapons;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Sharp extends Rune {

    public Sharp(RuneManager manager) {
        super(manager, "Sharp");
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
