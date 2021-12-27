package com.obstruct.clans.gamer;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.codec.LocationConverter;
import com.obstruct.clans.gamer.listeners.GamerListener;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class GamerManager extends SpigotManager<SpigotModule<?>> {

    private final Map<UUID, Gamer> gamerMap;

    public GamerManager(SpigotBasePlugin plugin) {
        super(plugin, "GamerManager");
        this.gamerMap = new HashMap<>();

        getManager(MongoManager.class).getMorphia().map(Gamer.class);
        getManager(MongoManager.class).getDatastore().ensureIndexes();

        //Need to convert this to work with the client loader when a player joins eventually.
        loadGamers();
    }

    @Override
    public void registerModules() {
        addModule(new GamerListener(this));
    }

    private synchronized void loadGamers() {
        for (Gamer gamer : getManager(MongoManager.class).getDatastore().find(Gamer.class)) {
            addGamer(gamer);
        }
        log("Gamer", "Loaded " + ChatColor.YELLOW + getGamerMap().size() + ChatColor.GRAY + " Gamers.");
    }

    public void addGamer(Gamer gamer) {
        getGamerMap().put(gamer.getUuid(), gamer);
    }
    //Method to get Gamers data.
    //getGamer(player);

    public Gamer getGamer(Player player) {
        return getGamer(player.getUniqueId());
    }
    //Method to get Gamers data.
    //getGamer(uuid);

    public Gamer getGamer(UUID uuid) {
        return gamerMap.get(uuid);
    }
}