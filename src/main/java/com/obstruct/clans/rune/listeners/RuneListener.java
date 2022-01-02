package com.obstruct.clans.rune.listeners;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilItem;
import com.obstruct.core.spigot.utility.UtilMessage;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RuneListener extends SpigotModule<RuneManager> implements Listener {

    public RuneListener(RuneManager manager) {
        super(manager, "RuneListener");
    }

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (!(event.getClickedInventory() instanceof CraftInventoryPlayer)) {
            return;
        }
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem.getType() == Material.AIR) {
            return;
        }
        ItemStack heldItem = event.getCursor();
        if (heldItem.getType() == Material.AIR) {
            return;
        }
        Rune rune = getManager().getRune(heldItem);
        if (rune == null) {
            return;
        }
        if (!rune.canApply(currentItem)) {
            return;
        }
        List<Rune> runes = getManager().getRunes(currentItem);
        for (Rune rune1 : runes) {
            if (rune1.getID() == rune.getID()) {
                UtilMessage.message(player, "Rune", "Incompatible Rune.");
                event.setResult(Event.Result.DENY);
                return;
            }
        }
        net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(currentItem);
        NBTTagCompound compound = (nmsCopy.hasTag()) ? nmsCopy.getTag() : new NBTTagCompound();

        NBTTagList nbtBaseRunes = new NBTTagList();
        if (compound.hasKey("runes")) {
            nbtBaseRunes = (NBTTagList) compound.get("runes");
        }

        NBTTagCompound newRune = new NBTTagCompound();
        newRune.setString("runeName", rune.getName());
        double modifier = rune.getModifier();
        newRune.setDouble("runeModifier", modifier);
        nbtBaseRunes.add(newRune);

        compound.set("runes", nbtBaseRunes);
        compound.set("ench", new NBTTagList());
        nmsCopy.setTag(compound);

        ItemStack stack = UtilItem.updateNames(CraftItemStack.asBukkitCopy(nmsCopy));
        ItemMeta itemMeta = stack.getItemMeta();

        StringBuilder name = new StringBuilder(ChatColor.stripColor(itemMeta.getDisplayName()));
        List<Rune> runeList = getManager().getRunes(stack);
        for (Rune r : runeList) {
            name = new StringBuilder(name.toString().replaceAll(ChatColor.stripColor(r.getDisplayName()), ""));
        }
        runeList.sort(Comparator.comparingInt(Rune::getID).reversed());
        for (Rune r : runeList) {
            if (r.getID() == 0) {
                name.insert(0, r.getDisplayName());
            } else if (r.getID() == 1) {
                name.insert(0, r.getDisplayName());
            } else if (r.getID() == 2) {
                name.append(r.getDisplayName());
            }
        }
        itemMeta.setDisplayName(ChatColor.GOLD + ChatColor.stripColor(name.toString()));
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(0, ChatColor.WHITE + rune.getLore(modifier));
        itemMeta.setLore(lore);
        stack.setItemMeta(itemMeta);
        event.setCurrentItem(stack);
        event.setResult(Event.Result.DENY);
        ItemStack itemOnCursor = event.getWhoClicked().getItemOnCursor();
        if (itemOnCursor.getAmount() > 1) {
            itemOnCursor.setAmount(itemOnCursor.getAmount() - 1);
        } else {
            event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
        }
        player.updateInventory();
    }
}