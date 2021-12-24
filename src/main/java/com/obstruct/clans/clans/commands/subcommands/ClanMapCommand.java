package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilItem;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClanMapCommand extends Command<Player> {

    public ClanMapCommand(CommandManager manager) {
        super(manager, "ClanMapCommand");
        setCommand("map");
        setIndex(1);
        setRequiredArgs(1);
    }

    public boolean execute(Player player, String[] args) {
        if (player.getInventory().contains(Material.MAP)) {
            UtilMessage.message(player, "Clans", "You already have a Map in your Inventory.");
            return false;
        }
        if (player.getInventory().firstEmpty() == -1) {
            UtilMessage.message(player, "Clans", "You do not have enough space in your inventory for a Map.");
            return false;
        }
        ItemStack is = new ItemStack(Material.MAP);
        is.setDurability((short) 0);
        player.getInventory().addItem(UtilItem.updateNames(is));

        UtilMessage.message(player, "Clans", "A map was added to your Inventory.");
        return true;
    }
}