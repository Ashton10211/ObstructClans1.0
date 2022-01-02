package com.obstruct.clans.rune.runes.weapons;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import com.obstruct.core.spigot.utility.UtilMath;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Flaming extends Rune {
    public Flaming(RuneManager manager) {
        super(manager, "Flaming");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.RED + getName() + " ";
    }

    @Override
    public double getModifier() {
        return UtilTime.trim(UtilMath.randomInt(1,6), 0);
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