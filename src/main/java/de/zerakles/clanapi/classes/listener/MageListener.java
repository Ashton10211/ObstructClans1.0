package de.zerakles.clanapi.classes.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.classes.Manager;
import de.zerakles.clanapi.classes.effects.EnumEffect;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class MageListener implements Listener {

    private ClanAPI getClanAPI() {
        return Clan.getClan().getClanAPI();
    }

    private Data getData() {
        return Clan.getClan().data;
    }

    private Manager getManager() {
        return Clan.getClan().manager;
    }

    private Clan getClan() {
        return Clan.getClan();
    }

    public void loop() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                ArrayList<Zone> zoneRemove = new ArrayList<>();
                if (zoneArrayList.size() > 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        for (Zone zone : zoneArrayList
                        ) {
                            if (zone.liveTime == 0) {
                                zoneRemove.add(zone);
                                continue;
                            }
                            if (zone.liveTime > 0) {
                                zone.setLiveTime(zone.liveTime - 1);
                            }
                            if (zone.getLocation().distance(player.getLocation()) < 7) {
                                if (zone.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, 1));
                                    for (Player all : Bukkit.getOnlinePlayers()
                                    ) {
                                        all.playEffect(player.getLocation(), Effect.FLYING_GLYPH, 9999);
                                        all.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                                    }
                                    continue;
                                }
                                if (getClanAPI().playerExists(player.getUniqueId().toString())) {
                                    if (getClanAPI().getClan(player.getUniqueId().toString()).equalsIgnoreCase(zone.getClanName())) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, 1));
                                        for (Player all : Bukkit.getOnlinePlayers()
                                        ) {
                                            all.playEffect(player.getLocation(), Effect.FLYING_GLYPH, 9999);
                                            all.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                                        }
                                        continue;
                                    }
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                    for (Player all : Bukkit.getOnlinePlayers()
                                    ) {
                                        all.playEffect(player.getLocation(), Effect.SLIME, 9999);
                                        all.playSound(player.getLocation(), Sound.SLIME_WALK, 1F, 1F);
                                    }
                                    continue;
                                }
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                for (Player all : Bukkit.getOnlinePlayers()
                                ) {
                                    all.playEffect(player.getLocation(), Effect.SLIME, 9999);
                                    all.playSound(player.getLocation(), Sound.SLIME_WALK, 1F, 1F);
                                }
                                continue;
                            }
                        }
                    }
                }
                for (Zone zone : zoneRemove
                ) {
                    zoneArrayList.remove(zone);
                }
                ArrayList<Player> toRemove = new ArrayList<>();
                if (cooldown.size() > 0) {
                    for (Player player : cooldown.keySet()
                    ) {
                        if (cooldown.get(player) == 0) {
                            toRemove.add(player);
                        }
                        cooldown.replace(player, cooldown.get(player) - 1);
                    }
                }
                for (Player player : toRemove
                ) {
                    cooldown.remove(player);
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!getManager().hasKit(player))
                        continue;
                    if (!getManager().getKit(player).equalsIgnoreCase("Mage")) {
                        if (isSilenced(player))
                            continue;
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2));
                        String clan = getClanAPI().getClan(player.getUniqueId().toString());
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (getClanAPI().getClan(all.getUniqueId().toString()).equalsIgnoreCase(clan)) {
                                if (all.getLocation().distance(player.getLocation()) < 5) {
                                    all.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2));
                                }
                            }
                        }
                    }
                }
                ArrayList<Player>toRemovePrisonCD = new ArrayList<>();
                for(Player player : prisonCooldown.keySet()){
                    if(prisonCooldown.get(player) == 0){
                        toRemovePrisonCD.add(player);
                        continue;
                    }
                    prisonCooldown.remove(player, prisonCooldown.get(player)-1);
                    continue;
                }
            }
        }, 0, 20);
    }

    public void hardLoop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for (Arrow arrow : arrowPlayerHashMap.keySet()){
                    if(arrow.isOnGround()){
                        arrow.remove();
                    }else{
                        for(Player all:Bukkit.getOnlinePlayers())
                            all.playEffect(arrow.getLocation(), Effect.BLAZE_SHOOT, 9999);
                    }
                }
            }
        },0,1);
    }

    public HashMap<Player, Integer> prisonCooldown = new HashMap<>();
    public HashMap<Arrow,Player> arrowPlayerHashMap = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent entityDamageEvent){
        if(entityDamageEvent.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){
            if(entityDamageEvent.getDamager() instanceof Arrow){
                if(arrowPlayerHashMap.containsKey(entityDamageEvent.getDamager())){
                    entityDamageEvent.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent projectileHitEvent){
        if(!(projectileHitEvent instanceof Arrow))
            return;
        Arrow arrow = (Arrow) projectileHitEvent.getEntity();
        if(!arrowPlayerHashMap.containsKey(arrow))
            return;
        arrowPlayerHashMap.remove(arrow);
        arrow.remove();
        for(Player all: Bukkit.getOnlinePlayers()){
            if(all.getLocation().distance(arrow.getLocation())<1.25){
                Location location = all.getLocation();
                Location b1 = location;
                Location b2;
                Location b3 = location;
                Location b4;
                Location b5 = location;
                Location b6;
                Location b7 = location;
                Location b8;
                b1.setX(location.getX()-1);
                b1.setY(location.getY()+1);
                b2 = b1;
                b2.setY(b1.getY()+1);

                b3.setX(location.getX()+1);
                b3.setY(location.getY()+1);
                b4 = b3;
                b4.setY(b3.getY()+1);

                b5.setX(location.getZ()-1);
                b5.setY(location.getY()+1);
                b6 = b5;
                b6.setY(b5.getY()+1);

                b7.setX(location.getZ()+1);
                b7.setY(location.getY()+1);
                b8 = b7;
                b8.setY(b7.getY()+1);
                b1.getBlock().setType(Material.PACKED_ICE);
                b2.getBlock().setType(Material.PACKED_ICE);
                b3.getBlock().setType(Material.PACKED_ICE);
                b4.getBlock().setType(Material.PACKED_ICE);
                b5.getBlock().setType(Material.PACKED_ICE);
                b6.getBlock().setType(Material.PACKED_ICE);
                b7.getBlock().setType(Material.PACKED_ICE);
                b8.getBlock().setType(Material.PACKED_ICE);
                Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                    @Override
                    public void run() {
                        b1.getBlock().setType(Material.AIR);
                        b2.getBlock().setType(Material.AIR);
                        b3.getBlock().setType(Material.AIR);
                        b4.getBlock().setType(Material.AIR);
                        b5.getBlock().setType(Material.AIR);
                        b6.getBlock().setType(Material.AIR);
                        b7.getBlock().setType(Material.AIR);
                        b8.getBlock().setType(Material.AIR);
                    }
                },10);
            }
        }
    }

    @EventHandler
    public void onInteractTwo(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getPlayer().getItemInHand();
        if(!getManager().hasKit(player))
            return;
        if(isSilenced(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Mage"))
            return;
        if(!isSword(itemStack))
            return;
        if(prisonCooldown.containsKey(player)){
            player.sendMessage(getManager().prefix + "§7Spell is on cooldown! CD:§e" + prisonCooldown.get(player));
            return;
        }
        Arrow arrow = player.shootArrow();
        arrow.setKnockbackStrength(0);
        player.sendMessage(getManager().prefix + "§7Shot!");
        arrowPlayerHashMap.put(arrow, player);
        prisonCooldown.put(player, 15);
    }

    public ArrayList<Player>bonusMagic = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getPlayer().getItemInHand();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Mage"))
            return;
        if(!isAxe(itemStack))
            return;
        if(bonusMagic.contains(player)){
            player.sendMessage(getManager().prefix + "§cYour spell is already active!");
            return;
        }
        ArrayList<Player>bonus = new ArrayList<>();
        if(getClanAPI().playerExists(player.getUniqueId().toString())){
            String clan = getClanAPI().getClan(player.getUniqueId().toString());
            player.setMaxHealth(player.getMaxHealth()+4);
            bonus.add(player);
            player.sendMessage(getManager().prefix + "§aYou §7gave yourself §a+4 hearts §7for §a5min§7!");
            for(Player all : Bukkit.getOnlinePlayers()){
                if(getClanAPI().playerExists(all.getUniqueId().toString())){
                    if(getClanAPI().getClan(all.getUniqueId().toString()).equalsIgnoreCase(clan)){
                        all.setMaxHealth(player.getMaxHealth() + 4);
                        all.sendMessage(getManager().prefix + "§a" + player.getName() + " §7gave you §a+4 hearts §7for §a5min§7!");
                        bonus.add(all);
                    }
                }
            }
        } else {
            player.setMaxHealth(player.getMaxHealth()+4);
            bonus.add(player);
            player.sendMessage(getManager().prefix + "§aYou §7gave yourself §a+4 hearts §7for §a5min§7!");
        }
        bonusMagic.add(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for (Player players:bonus
                     ) {
                    if(players.isOnline()){
                        players.setMaxHealth(players.getMaxHealth() - 4);
                        players.sendMessage(getManager().prefix + "§cThe Magic leaves your little heart :c");
                    }
                }
                bonusMagic.remove(player);
            }
        },20*60*5);
    }

    private boolean canUse(Player player) {
        if (player.getLocation().getBlock().isLiquid()) {
            player.sendMessage(getManager().prefix + "§7You can't use this skill in water!");
            return false;
        }
        return true;
    }

    public boolean isSilenced(Player player) {
        if (getManager().effectManager.hasEffect(player, EnumEffect.SILENCED)) {
            return true;
        }
        return false;
    }

    public boolean isSword(ItemStack itemStack) {
        if ( (itemStack.getType().equals(Material.WOOD_SWORD))
                || (itemStack.getType().equals(Material.STONE_SWORD))
                || (itemStack.getType().equals(Material.IRON_SWORD))
                || (itemStack.getType().equals(Material.GOLD_SWORD))
                || (itemStack.getType().equals(Material.DIAMOND_SWORD))) {
            return true;
        }
        return false;
    }

    public boolean isAxeOrSword(ItemStack itemStack) {
        if ((itemStack.getType().equals(Material.WOOD_AXE)) || (itemStack.getType().equals(Material.WOOD_SWORD))
                || (itemStack.getType().equals(Material.STONE_AXE)) || (itemStack.getType().equals(Material.STONE_SWORD))
                || (itemStack.getType().equals(Material.IRON_AXE)) || (itemStack.getType().equals(Material.IRON_SWORD))
                || (itemStack.getType().equals(Material.GOLD_SWORD)) || (itemStack.getType().equals(Material.GOLD_AXE))
                || (itemStack.getType().equals(Material.DIAMOND_AXE)) || (itemStack.getType().equals(Material.DIAMOND_SWORD))) {
            return true;
        }
        return false;
    }

    public boolean isAxe(ItemStack itemStack) {
        if ((itemStack.getType().equals(Material.WOOD_AXE))
                || (itemStack.getType().equals(Material.STONE_AXE))
                || (itemStack.getType().equals(Material.IRON_AXE))
                || (itemStack.getType().equals(Material.GOLD_AXE))
                || (itemStack.getType().equals(Material.DIAMOND_AXE))) {
            return true;
        }
        return false;
    }

    public HashMap<Player, Integer> cooldown = new HashMap<>();
    public ArrayList<Zone> zoneArrayList = new ArrayList<>();

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent) {
        Player player = playerDropItemEvent.getPlayer();
        if (!getManager().hasKit(player))
            return;
        if (!getManager().getKit(player).equalsIgnoreCase("Mage"))
            return;
        if (!isAxeOrSword(playerDropItemEvent.getItemDrop().getItemStack()))
            return;
        if (isSilenced(player))
            return;
        createZone(playerDropItemEvent.getItemDrop(), player);
    }

    private void createZone(Item itemDrop, Player player) {
        if (cooldown.containsKey(player)) {
            player.sendMessage(getManager().prefix + "§7Spell is on cooldown. CD: §e" + cooldown.get(player) + "s");
            return;
        }
        String clanName = "";
        if (getClanAPI().playerExists(player.getUniqueId().toString())) {
            clanName = getClanAPI().getClan(player.getUniqueId().toString());
        }
        Zone zone = new Zone(itemDrop.getLocation(), player, clanName, 10);
        cooldown.put(player, 12);
        zoneArrayList.add(zone);
        player.sendMessage(getManager().prefix + "§7Spell §aArcticArmor §7was used.");
    }

}
