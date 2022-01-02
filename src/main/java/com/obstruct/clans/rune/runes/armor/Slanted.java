package com.obstruct.clans.rune.runes.armor;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilMath;
import com.obstruct.core.spigot.utility.UtilTime;
import net.minecraft.server.v1_8_R3.ItemArmor;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Slanted extends Rune {

    public Slanted(RuneManager manager) {
        super(manager, "Slanted");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.GREEN + getName() + " ";
    }

    @Override
    public double getModifier() {
        return UtilTime.trim(UtilMath.randomDouble(0.5D, 2.0D), 1);
    }

    @Override
    public String getLore(double modifier) {
        return "-" + modifier + " damage taken from projectiles";
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemArmor;
    }
}