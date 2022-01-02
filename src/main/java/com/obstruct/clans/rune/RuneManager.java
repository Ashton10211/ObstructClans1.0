package com.obstruct.clans.rune;

import com.obstruct.clans.rune.listeners.RuneListener;
import com.obstruct.clans.rune.runes.Conquering;
import com.obstruct.clans.rune.runes.armor.LavaForged;
import com.obstruct.clans.rune.runes.armor.Padded;
import com.obstruct.clans.rune.runes.armor.Reinforced;
import com.obstruct.clans.rune.runes.armor.Slanted;
import com.obstruct.clans.rune.runes.bow.*;
import com.obstruct.clans.rune.runes.weapons.*;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuneManager extends SpigotManager<SpigotModule<?>> {

    public RuneManager(SpigotBasePlugin plugin) {
        super(plugin, "RuneManager");
    }

    @Override
    public void registerModules() {
        addModule(new RuneListener(this));
        addModule(new LavaForged(this));
        addModule(new Padded(this));
        addModule(new Reinforced(this));
        addModule(new Slanted(this));
        addModule(new Heavy(this));
        addModule(new Hunting(this));
        addModule(new Inverse(this));
        addModule(new Leeching(this));
        addModule(new Recursive(this));
        addModule(new Scorching(this));
        addModule(new Slaying(this));
        addModule(new Flaming(this));
        addModule(new Frosted(this));
        addModule(new Haste(this));
        addModule(new Jagged(this));
        addModule(new Sharp(this));
        addModule(new Conquering(this));
    }

    public boolean hasRune(Rune rune, ItemStack itemStack) {
        return false;
    }

    public double getModifier(Rune rune, ItemStack itemStack) {
        return 0;
    }

    public List<Rune> getRunes(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsRune = CraftItemStack.asNMSCopy(itemStack);
        List<Rune> runes = new ArrayList<>();
        if(!nmsRune.hasTag()) {
            return runes;
        }
        if(!nmsRune.getTag().hasKey("runes")) {
            return runes;
        }
        NBTTagList runeList = (NBTTagList) nmsRune.getTag().get("runes");
        for (int i = 0; i < runeList.size(); i++) {
            NBTTagCompound c = runeList.get(i);
            String runeName = c.getString("runeName");
            double runeModifier = c.getDouble("runeModifier");
            runes.add((Rune) getModule(runeName));
        }
        return runes;
    }

    public Rune getRune(ItemStack heldItem) {
        net.minecraft.server.v1_8_R3.ItemStack nmsRune = CraftItemStack.asNMSCopy(heldItem);
        if(!nmsRune.hasTag()) {
            return null;
        }
        if(!nmsRune.getTag().hasKey("runeName")) {
            return null;
        }
        return (Rune) getModule(nmsRune.getTag().getString("runeName"));
    }
}