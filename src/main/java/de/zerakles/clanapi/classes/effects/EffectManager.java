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

    public void loop(){
        HashMap<Player, ArrayList<Effect>>toRemove = new HashMap<>();
        for (Player pl:effectHashMap.keySet()
             ) {
            ArrayList<Effect> e = effectHashMap.get(pl);
            ArrayList<Effect> toRemoves = new ArrayList<>();
            for (Effect ef:e
                 ) {
                if(ef.getEffectLength() == 0){
                    toRemoves.add(ef);
                }
                ef.setEffectLength(ef.getEffectLength()-1);
            }
            toRemove.put(pl, toRemoves);
        }
        for (Player pl:toRemove.keySet()
             ) {
            ArrayList<Effect> rm = toRemove.get(pl);
            for (Effect e:rm
                 ) {
                effectHashMap.get(pl).remove(e);
            }
        }
    }

    public void registerEffect(EnumEffect enumEffect, Player p){
        Effect effect = new Effect(enumEffect, enumEffect.toString().toUpperCase(), 1, 40);
        if(effectHashMap.containsKey(p)){
            ArrayList<Effect>es = effectHashMap.get(p);
            es.add(effect);
            effectHashMap.replace(p, es);
            return;
        }
        ArrayList<Effect>ef = new ArrayList<>();
        ef.add(effect);
        effectHashMap.put(p, ef);
        return;
    }
}
