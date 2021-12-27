package com.obstruct.clans.gamer.listeners;

import com.obstruct.clans.gamer.Gamer;
import com.obstruct.clans.gamer.GamerManager;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GamerListener extends SpigotModule<GamerManager> implements Listener {

    public GamerListener(GamerManager manager) {
        super(manager, "GamerListener");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Gamer gamer = getManager().getGamer(player);
        if(gamer != null) {
            gamer.setLastLogin(System.currentTimeMillis());
        } else {
            gamer = new Gamer(player.getUniqueId());
            getManager().getGamerMap().put(player.getUniqueId(), gamer);
        }
        getExecutorService().execute(() -> getManager(MongoManager.class).getDatastore().save(getManager().getGamer(player)));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Gamer gamer = getManager().getGamer(player);
        //Shouldn't ever equal null but just in case.
        if(gamer == null) {
            return;
        }
        gamer.setTimePlayed(gamer.getTimePlayed() + (System.currentTimeMillis() - gamer.getLastLogin()));
        gamer.setLastOnline(System.currentTimeMillis());
        getExecutorService().execute(() -> getManager(MongoManager.class).getDatastore().save(getManager().getGamer(player)));
    }
}