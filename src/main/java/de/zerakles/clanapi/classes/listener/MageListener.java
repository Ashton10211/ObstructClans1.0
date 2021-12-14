package de.zerakles.clanapi.classes.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.classes.Manager;
import de.zerakles.clanapi.classes.effects.EnumEffect;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class MageListener implements Listener {

    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }

    private Data getData(){
        return Clan.getClan().data;
    }

    private Manager getManager(){
        return Clan.getClan().manager;
    }

    private Clan getClan(){
        return Clan.getClan();
    }

    public void loop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                ArrayList<Zone> zoneRemove = new ArrayList<>();
                if(zoneArrayList.size() > 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        for (Zone zone:zoneArrayList
                             ) {
                            if(zone.liveTime == 0){
                                zoneRemove.add(zone);
                                continue;
                            }
                            if(zone.liveTime>0){
                                zone.setLiveTime(zone.liveTime-1);
                            }
                            if(zone.getLocation().distance(player.getLocation()) < 7){
                                if(zone.getPlayer().getUniqueId().equals(player.getUniqueId())){
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, 1));
                                    for (Player all:Bukkit.getOnlinePlayers()
                                         ) {
                                        all.playEffect(player.getLocation(), Effect.FLYING_GLYPH, 9999);
                                        all.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                                    }
                                    continue;
                                }
                                if(getClanAPI().playerExists(player.getUniqueId().toString())){
                                    if(getClanAPI().getClan(player.getUniqueId().toString()).equalsIgnoreCase(zone.getClanName())){
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, 1));
                                        for (Player all:Bukkit.getOnlinePlayers()
                                        ) {
                                            all.playEffect(player.getLocation(), Effect.FLYING_GLYPH, 9999);
                                            all.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                                        }
                                        continue;
                                    }
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                    for (Player all:Bukkit.getOnlinePlayers()
                                    ) {
                                        all.playEffect(player.getLocation(), Effect.SLIME, 9999);
                                        all.playSound(player.getLocation(), Sound.SLIME_WALK, 1F, 1F);
                                    }
                                    continue;
                                }
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                for (Player all:Bukkit.getOnlinePlayers()
                                ) {
                                    all.playEffect(player.getLocation(), Effect.SLIME, 9999);
                                    all.playSound(player.getLocation(), Sound.SLIME_WALK, 1F, 1F);
                                }
                                continue;
                            }
                        }
                    }
                }
                for (Zone zone:zoneRemove
                     ) {
                    zoneArrayList.remove(zone);
                }
                ArrayList<Player> toRemove = new ArrayList<>();
                if(cooldown.size() > 0){
                    for (Player player:cooldown.keySet()
                         ) {
                        if(cooldown.get(player) == 0){
                            toRemove.add(player);
                        }
                        cooldown.replace(player, cooldown.get(player)-1);
                    }
                }
                for (Player player:toRemove
                     ) {
                    cooldown.remove(player);
                }
            }
        },0,20);
    }

    private boolean canUse(Player player){
        if(player.getLocation().getBlock().isLiquid()){
            player.sendMessage(getData().prefix + "§7You can't use this skill in water!");
            return false;
        }
        return true;
    }

    public boolean isSilenced(Player player){
        if(getManager().effectManager.hasEffect(player, EnumEffect.SILENCED)){
            return true;
        }
        return false;
    }

    public boolean isAxeOrSword(ItemStack itemStack){
        if((itemStack.getType().equals(Material.WOOD_AXE)) || (itemStack.getType().equals(Material.WOOD_SWORD))
                || (itemStack.getType().equals(Material.STONE_AXE)) || (itemStack.getType().equals(Material.STONE_SWORD))
                || (itemStack.getType().equals(Material.IRON_AXE)) || (itemStack.getType().equals(Material.IRON_SWORD))
                || (itemStack.getType().equals(Material.GOLD_SWORD)) || (itemStack.getType().equals(Material.GOLD_AXE))
                || (itemStack.getType().equals(Material.DIAMOND_AXE)) || (itemStack.getType().equals(Material.DIAMOND_SWORD))){
            return true;
        }
        return false;
    }

    public HashMap<Player, Integer> cooldown = new HashMap<>();
    public ArrayList<Zone> zoneArrayList = new ArrayList<>();

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent){
        Player player = playerDropItemEvent.getPlayer();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Mage"))
            return;
        if(!isAxeOrSword(playerDropItemEvent.getItemDrop().getItemStack()))
            return;
        createZone(playerDropItemEvent.getItemDrop(), player);
    }

    private void createZone(Item itemDrop, Player player) {
        if(cooldown.containsKey(player)){
            player.sendMessage(getData().prefix + "§7Spell is on cooldown. CD: §e" + cooldown.get(player) + "s");
            return;
        }
        String clanName = "";
        if(getClanAPI().playerExists(player.getUniqueId().toString())){
            clanName = getClanAPI().getClan(player.getUniqueId().toString());
        }
        Zone zone = new Zone(itemDrop.getLocation(), player, clanName, 10);
        cooldown.put(player, 12);
        zoneArrayList.add(zone);
        player.sendMessage(getData().prefix + "§7Spell §aArcticArmor §7was used.");
    }

}
