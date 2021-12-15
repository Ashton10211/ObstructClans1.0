package de.zerakles.clanapi.classes.effects;

import de.zerakles.main.Clan;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EffectListener implements Listener {

    EffectManager getEffectManager(){
        return Clan.getClan().manager.effectManager;
    }

    @EventHandler
    public void onFrozen(PlayerMoveEvent playerMoveEvent){
        if(getEffectManager().hasEffect(playerMoveEvent.getPlayer(), EnumEffect.FROZEN)){
            playerMoveEvent.getPlayer().teleport(playerMoveEvent.getFrom());
            return;
        }
    }

}
