package com.obstruct.clans.rune.runes.armor;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilMath;
import com.obstruct.core.spigot.utility.UtilTime;
import net.minecraft.server.v1_8_R3.ItemArmor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class LavaForged extends Rune {

    public LavaForged(RuneManager manager) {
        super(manager, "LavaForged");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.GREEN + "Lava Forged ";
    }

    @Override
    public double getModifier() {
        return UtilTime.trim(UtilMath.randomDouble(0.35D, 0.75D), 1);
    }

    @Override
    public String getLore(double modifier) {
        return "Reduces damage from fire and lava by " + (modifier * 100) + "%";
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemArmor;
    }
}