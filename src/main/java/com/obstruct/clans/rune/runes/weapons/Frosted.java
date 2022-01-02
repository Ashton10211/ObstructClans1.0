package com.obstruct.clans.rune.runes.weapons;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import com.obstruct.core.spigot.utility.UtilMath;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Frosted extends Rune {

    public Frosted(RuneManager manager) {
        super(manager, "Frosted");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.RED + getName() + " ";
    }

    @Override
    public double getModifier() {
        return UtilTime.trim(UtilMath.randomDouble(1,3), 0);
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
