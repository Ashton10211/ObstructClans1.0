package com.obstruct.clans.rune.runes.armor;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.combat.event.CustomDamageEvent;
import com.obstruct.core.spigot.utility.UtilMath;
import com.obstruct.core.spigot.utility.UtilTime;
import net.minecraft.server.v1_8_R3.ItemArmor;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Reinforced extends Rune implements Listener {

    public Reinforced(RuneManager manager) {
        super(manager, "Reinforced");
    }

    @EventHandler
    public void onCustomDamage(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player damagee = event.getDamageePlayer();
        if (damagee == null) {
            return;
        }
        if (event.getDamageCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }
        for (ItemStack itemStack : damagee.getEquipment().getArmorContents()) {
            if (getManager().hasRune(this, itemStack)) {
                double modifier = getManager().getModifier(this, itemStack);
                event.setDamage(event.getDamage() - modifier);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return ChatColor.GREEN + getName() + " ";
    }

    @Override
    public double getModifier() {
        return UtilTime.trim(UtilMath.randomDouble(0.5D, 1.0D), 1);
    }

    @Override
    public String getLore(double modifier) {
        return "-" + modifier + " damage taken from melee";
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