package com.obstruct.clans.rune;

import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilItem;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class Rune extends SpigotModule<RuneManager> {

    public Rune(RuneManager manager, String name) {
        super(manager, name);
    }

    public ItemStack createRuneItem() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(getDisplayName());
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Ancient Rune");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        net.minecraft.server.v1_8_R3.ItemStack nmsRune = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = (nmsRune.hasTag()) ? nmsRune.getTag() : new NBTTagCompound();
        nbtTagCompound.setString("runeName", getName());
        nmsRune.setTag(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsRune);
    }

    public abstract String getDisplayName();

    public abstract double getModifier();

    public abstract String getLore(double modifier);

    public abstract int getID();

    public abstract boolean canApply(ItemStack itemStack);
}