package com.obstruct.clans.rune.runes.bow;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Recursive extends Rune {

    public Recursive(RuneManager manager) {
        super(manager, "Recursive");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.DARK_BLUE + getName() + " ";
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
        return 1;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return UtilItem.isBow(itemStack.getType());
    }}
