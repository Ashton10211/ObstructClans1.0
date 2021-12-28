package com.obstruct.clans.shops;

import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ShopManager extends SpigotManager<SpigotModule<?>> {

    private final Set<Shop> shops;

    public ShopManager(SpigotBasePlugin plugin) {
        super(plugin, "ShopManager");
        this.shops = new HashSet<>();

        getManager(MongoManager.class).getMorphia().map(Shop.class);
        getManager(MongoManager.class).getDatastore().ensureIndexes();

        loadShops();
    }

    private synchronized void loadShops() {
        for (Shop shop : getManager(MongoManager.class).getDatastore().find(Shop.class)) {
            getShops().add(shop);
            shop.spawn();
        }
        log("Shops", "Loaded " + ChatColor.YELLOW + getShops().size() + ChatColor.GRAY + " Shops.");
    }

    @Override
    public void registerModules() {
    }
}
