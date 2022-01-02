package com.obstruct.clans.rune.runes.weapons;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Jagged extends Rune {

    public Jagged(RuneManager manager) {
        super(manager, "Jagged");
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
    }}
