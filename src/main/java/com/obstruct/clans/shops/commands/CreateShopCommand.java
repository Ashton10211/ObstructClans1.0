package com.obstruct.clans.shops.commands;

import com.obstruct.clans.shops.Shop;
import com.obstruct.clans.shops.ShopManager;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CreateShopCommand extends Command<Player> {

    public CreateShopCommand(CommandManager manager) {
        super(manager, "CreateShopCommand");
        setIndex(1);
        setRequiredArgs(3);
        setCommand("create");
    }

    @Override
    public boolean execute(Player player, String[] args) {
        String shopName = args[1];
        EntityType entityType = EntityType.valueOf(args[2]);
        getManager(MongoManager.class).getDatastore().save(new Shop(player.getLocation(), shopName, entityType));
        return false;
    }
}