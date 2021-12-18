package de.zerakles.clanapi.classes.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.classes.Manager;
import de.zerakles.clanapi.classes.effects.EnumEffect;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AssassinListener implements Listener {

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

    private void removeSmokedPlayer(Player player){
        smokeDelay.remove(player);
        return;
    }

    private void removeWasDamaged(Player player){
        wasDamaged.remove(player);
        return;
    }

    public void removeSilenceCD(Player player){
        silenceCooldown.remove(player);
        return;
    }

    public void removeNoNockBackCD(Player player){
        noKnockBackCD.remove(player);
        return;
    }

    public void removeNoKnockBack(Player player){
        noKnockBack.remove(player);
        return;
    }

    public HashMap<Player, Integer>silenceCooldown = new HashMap<>();
    public ArrayList<Player>silenceArrow = new ArrayList<>();

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(!(entityDamageByEntityEvent.getEntity() instanceof Player))
            return;
        if(!(entityDamageByEntityEvent.getDamager() instanceof  Arrow))
            return;
        Player shooter = (Player) ((Arrow) entityDamageByEntityEvent.getDamager()).getShooter();
        if(!silenceArrow.contains(shooter))
            return;
        Player player = ((Player) entityDamageByEntityEvent.getEntity()).getPlayer();
        getManager().effectManager.registerEffect(EnumEffect.SILENCED, player);
        return;
    }

    @EventHandler
    public void silenceBow(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        Action action = playerInteractEvent.getAction();
        ItemStack itemStack = player.getItemInHand();
        if(action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK)
            return;
        if(!getManager().hasKit(player))
            return;
        if(isSilenced(player))
            return;
        if(!canUse(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
            return;
        if(silenceCooldown.containsKey(player))
            return;
        if(!(itemStack.getType().equals(Material.BOW)))
            return;
        silenceArrow.add(player);
        silenceCooldown.put(player, 10);
        player.sendMessage(getManager().prefix + "§7Your next §aArrow §7have a §5silence §aeffect §7for your enemy!");
        return;
    }

    public void loop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for(Player player : smokeDelay.keySet()){
                    if(smokeDelay.get(player) == 0){
                        removeSmokedPlayer(player);
                        continue;
                    }
                    smokeDelay.replace(player, smokeDelay.get(player)-1);
                }
                for(Player player : wasDamaged.keySet()){
                    if(wasDamaged.get(player) - System.currentTimeMillis() > 3000) {
                        removeWasDamaged(player);
                        continue;
                    }
                }
                for (Player all:Bukkit.getOnlinePlayers()
                     ) {
                    if(getManager().hasKit(all)){
                        if(getManager().getKit(all).equalsIgnoreCase("Assassin")){
                            if(!smokeDelay.containsKey(all) && !wasDamaged.containsKey(all)){
                                isVanished.add(all);
                            }
                        }
                    }
                }
                for (Player player : silenceCooldown.keySet()){
                    if(silenceCooldown.get(player) == 0){
                        removeSilenceCD(player);
                        continue;
                    }
                    silenceCooldown.replace(player, silenceCooldown.get(player)-1);
                    continue;
                }
                for(Player player : noKnockBackCD.keySet()){
                    if(noKnockBackCD.get(player) == 0){
                        removeNoNockBackCD(player);
                        continue;
                    }
                    noKnockBackCD.replace(player, noKnockBackCD.get(player)-1);
                    continue;
                }
                for(Player player : noKnockBack.keySet()){
                    if(noKnockBack.get(player) == 0){
                        removeNoKnockBack(player);
                        continue;
                    }
                    noKnockBack.replace(player,noKnockBack.get(player)-1);
                    continue;
                }
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(!getManager().hasKit(player))
                        return;
                    if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
                        return;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,40, 2));
                }
            }
        },0,20);
    }

    @EventHandler
    public void onFall(EntityDamageEvent entityDamageEvent){
        if(!(entityDamageEvent.getEntity() instanceof Player))
            return;
        if(!entityDamageEvent.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            return;
        Player player = (Player) entityDamageEvent.getEntity();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
            return;
        entityDamageEvent.setCancelled(true);
    }

    public Player checkEyeLocation(Player player){
        Entity entity = getTargetEntity(Player.class, 2, player);
        if(entity!=null){
            return (Player) entity;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> E getTargetEntity(Class<E> clazz, double range, Player player) {
        Vector dir = player.getLocation().getDirection();
        for (Location loc = player.getEyeLocation().clone(); player.getEyeLocation().distanceSquared(loc) <= range * range; loc.add(dir)) {
            if (loc.getBlock().getType().isOccluding()) return null; // Block is in the way
            for (Entity entity : getNearbyEntities(clazz, loc, 1.25D)) {
                if (entity != player) return (E) entity;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> Set<E> getNearbyEntities(Class<E> clazz, Location loc, double radius) {
        Set<E> tmp = new HashSet<>();
        int cRad = radius < 16 ? 1 : (int)((radius - (radius % 16)) / 16);
        for (int x = -cRad; x < cRad; x ++) {
            for (int z = -cRad; z < cRad; z ++) {
                for (Entity entity : new Location(loc.getWorld(), loc.getX() + (x * 16), loc.getY(), loc.getZ() + (z * 16)).getChunk().getEntities()) {
                    if (entity.getLocation().distanceSquared(loc) <= radius * radius
                            && clazz.isInstance(entity)) {
                        tmp.add((E) entity);
                    }
                }
            }
        }
        return tmp;
    }

    public HashMap<Player, Integer>noKnockBackCD = new HashMap<>();
    public HashMap<Player, Integer>noKnockBack = new HashMap<>();

    @EventHandler
    public void knockBackInteract(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(!(entityDamageByEntityEvent.getDamager() instanceof Player))
            return;
        Player player = (Player) entityDamageByEntityEvent.getDamager();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
            return;
        if(!noKnockBack.containsKey(player))
            return;
        entityDamageByEntityEvent.getEntity().getVelocity().multiply(0);
        return;
    }

    @EventHandler
    public void noKnockBack(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
            return;
        if(isSilenced(player))
            return;
        if(!canUse(player))
            return;
        if(!isSword(itemStack))
            return;
        if(noKnockBackCD.containsKey(player))
            return;
        player.sendMessage(getManager().prefix + "§7Your attacks dont do any knockback for 10sec!");
        noKnockBack.put(player, 10);
        noKnockBackCD.put(player, 20);
    }

    @EventHandler
    public void kickInteract(PlayerInteractEvent playerInteractEvent){
        /*

         */
    }

    public void kick(Player player){
        Vector vector = player.getVelocity();
        vector.setY(vector.getY()+2);
        player.setVelocity(vector);
        player.damage(7);
    }

    int tickController = 0;
    public void hardLoop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                tickController++;
                for (Player player:isVanished
                     ) {
                    for(Player all: Bukkit.getOnlinePlayers()){
                        all.hidePlayer(player);
                    }
                }
                if(tickController == 20){
                    for (Player pl : Bukkit.getOnlinePlayers()
                        ) {
                        if(bonusDamage.containsKey(pl)) {
                            if(bonusDamage.get(pl)==0){
                                continue;
                            }
                            bonusDamage.replace(pl, bonusDamage.get(pl) - 1);
                        }
                    }
                }
            }
        },0,1);
    }

    public ArrayList<Player>isVanished = new ArrayList<>();
    public HashMap<Player, Long>wasDamaged = new HashMap<>();
    public HashMap<Player, Integer>smokeDelay = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent){
        if(!(entityDamageEvent.getEntity() instanceof  Player)){
            return;
        }
        Player player = (Player) entityDamageEvent.getEntity();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
            return;
        if(isSilenced(player))
            return;
        if(!canUse(player))
            return;
        if(isVanished.contains(player)){
            isVanished.remove(player);
            for(Player all : Bukkit.getOnlinePlayers()){
                all.showPlayer(player);
            }
        }
        if(wasDamaged.containsKey(player)){
            wasDamaged.replace(player, wasDamaged.replace(player, System.currentTimeMillis()));
        } else {
            wasDamaged.put(player, System.currentTimeMillis());
        }
    }

    public HashMap<Player, Integer>bonusDamage = new HashMap<>();

    @EventHandler
    public void passivA(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(!(entityDamageByEntityEvent.getEntity() instanceof  Player)){
            return;
        }
        Player player = ((Player) entityDamageByEntityEvent.getEntity()).getPlayer();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Assassin"))
            return;
        if(isSilenced(player))
            return;
        if(bonusDamage.containsKey(player)){
            if(bonusDamage.get(player) == 20)
                bonusDamage.replace(player, 20);
            bonusDamage.replace(player, bonusDamage.get(player)+1);
        } else {
            bonusDamage.put(player, 1);
        }
        entityDamageByEntityEvent.setDamage(bonusDamage.get(player));
    }
}
