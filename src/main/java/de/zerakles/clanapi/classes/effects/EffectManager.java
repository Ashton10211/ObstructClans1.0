package de.zerakles.clanapi.classes.effects;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectManager {

    private HashMap<Player, ArrayList<Effect>> effectHashMap;

    public EffectManager(){
        this.effectHashMap = new HashMap<>();
    }

    public boolean hasEffect(Player player, EnumEffect enumEffect){
        if(effectHashMap.containsKey(player)){
            for (Effect effect:effectHashMap.get(player)
                 ) {
                if(effect.getEnumEffect().equals(enumEffect)){
                    return true;
                }
            }
        }
        return false;
    }
}
