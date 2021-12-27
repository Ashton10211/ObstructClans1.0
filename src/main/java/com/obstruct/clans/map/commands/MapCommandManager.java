package com.obstruct.clans.map.commands;

import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilItem;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MapCommandManager extends CommandManager {

    public MapCommandManager(SpigotBasePlugin plugin) {
        super(plugin, "MapCommandManager");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new SaveMapCommand(this));
        addModule(new LoadMapCommand(this));
    }

    class BaseCommand extends Command<Player> {
        public BaseCommand(CommandManager manager) {
            super(manager, "BaseCommand");
            setCommand("map");
            setIndex(0);
            setRequiredArgs(0);
        }


        public boolean execute(Player player, String[] args) {
            if (player.getInventory().contains(Material.MAP)) {
                UtilMessage.message(player, "Clans", "You already have a map in your inventory.");
                return false;
            }
            if (player.getInventory().firstEmpty() == -1) {
                UtilMessage.message(player, "Clans", "You do not have enough space in your inventory for a map.");
                return false;
            }
            ItemStack is = new ItemStack(Material.MAP);
            is.setDurability((short) 0);
            player.getInventory().addItem(UtilItem.updateNames(is));

            UtilMessage.message(player, "Clans", "A map was added to your inventory.");
            return true;
        }
    }
}