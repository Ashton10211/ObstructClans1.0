package com.obstruct.clans.shops.commands;

import com.obstruct.clans.shops.ShopManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import org.bukkit.entity.Player;

public class ShopCommand extends CommandManager {

    public ShopCommand(SpigotBasePlugin plugin) {
        super(plugin, "ShopCommand");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new CreateShopCommand(this));
    }

    class BaseCommand extends Command<Player> {

        public BaseCommand(CommandManager manager) {
            super(manager, "BaseCommand");
            setCommand("shop");
            setIndex(0);
        }

        @Override
        public boolean execute(Player player, String[] strings) {
            return false;
        }
    }
}