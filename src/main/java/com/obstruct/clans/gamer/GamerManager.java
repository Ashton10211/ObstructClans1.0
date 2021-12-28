package com.obstruct.clans.gamer;

import com.obstruct.clans.gamer.listeners.GamerListener;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.spigot.common.LoadDataCallback;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class GamerManager extends SpigotManager<SpigotModule<?>> implements LoadDataCallback {

    private final Map<UUID, Gamer> gamerMap;

    public GamerManager(SpigotBasePlugin plugin) {
        super(plugin, "GamerManager");
        this.gamerMap = new HashMap<>();

        getManager(MongoManager.class).getMorphia().map(Gamer.class);
    }

    @Override
    public void registerModules() {
        addModule(new GamerListener(this));
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

    @Override
    public synchronized void run(Client client) {
        Gamer first = getManager(MongoManager.class).getDatastore().createQuery(Gamer.class).field("uuid").equal(client.getUuid()).first();
        if(first == null) {
            Gamer data = new Gamer(client.getUuid());
            getManager(MongoManager.class).getDatastore().save(data);
            addGamer(data);
        } else {
            addGamer(first);
        }
    }
}