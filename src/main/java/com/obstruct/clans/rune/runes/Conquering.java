package com.obstruct.clans.rune.runes;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.utility.UtilItem;
import net.minecraft.server.v1_8_R3.ItemArmor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Conquering extends Rune {

    public Conquering(RuneManager manager) {
        super(manager, "Conquering");
    }

    @Override
    public String getDisplayName() {
        return null;
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
        return 2;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemArmor || UtilItem.isSword(itemStack.getType());
    }
}
