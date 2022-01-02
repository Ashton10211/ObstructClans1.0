package com.obstruct.clans.rune.runes.armor;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilMath;
import com.obstruct.core.spigot.utility.UtilTime;
import net.minecraft.server.v1_8_R3.ItemArmor;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Padded extends Rune {

    public Padded(RuneManager manager) {
        super(manager, "Padded");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.GREEN + getName() + " ";
    }

    @Override
    public double getModifier() {
        return UtilTime.trim(UtilMath.randomDouble(1.0D, 4.0D), 1);
    }

    @Override
    public String getLore(double modifier) {
        return "-" + modifier + " damage taken from falls";
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
