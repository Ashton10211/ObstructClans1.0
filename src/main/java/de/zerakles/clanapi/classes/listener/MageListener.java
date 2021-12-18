package de.zerakles.clanapi.classes.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.classes.Manager;
import de.zerakles.clanapi.classes.effects.EnumEffect;
import de.zerakles.clanapi.legendaries.meridianscepter.utils.MerdianScepterShot;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
                    if (getManager().getKit(player).equalsIgnoreCase("Mage")) {
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
                    prisonCooldown.replace(player, prisonCooldown.get(player)-1);
                    continue;
                }
                for (Player pl :toRemovePrisonCD
                    ) {
                    prisonCooldown.remove(pl);
                }
            }
        }, 0, 20);
    }

    public void hardLoop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for (MerdianScepterShot arrow : arrowPlayerHashMap.keySet()){
                    if(arrow.getArrow().isOnGround()){
                        arrow.getArrow().remove();
                    }else{
                        for(Player all:Bukkit.getOnlinePlayers())
                            all.playEffect(arrow.getArrow().getLocation(), Effect.BLAZE_SHOOT, 9999);
                    }
                }
                if (!arrowPlayerHashMap.isEmpty()) {

                    ArrayList<MerdianScepterShot> copy = new ArrayList<>();
                    for (MerdianScepterShot shot:arrowPlayerHashMap.keySet()
                         ) {
                        copy.add(shot);
                    }
                    for (MerdianScepterShot shot : copy) {
                        if (!shot.getArrow().isDead() && !shot.isGone())
                            shot.update();
                    }
                }
                for (MerdianScepterShot shot : arrowPlayerHashMap.keySet()){
                    if(shot.getArrow().isOnGround()){
                        shot.delete();
                    }
                }
            }
        },0,1);
    }

    public HashMap<Player, Integer> prisonCooldown = new HashMap<>();
    public HashMap<MerdianScepterShot,Player> arrowPlayerHashMap = new HashMap<>();

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
    public void onInteractTwo(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getPlayer().getItemInHand();
        if(playerInteractEvent.getAction() != Action.RIGHT_CLICK_AIR && playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if(!getManager().hasKit(player))
            return;
        if(isSilenced(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Mage"))
            return;
        if(!isSword(itemStack))
            return;
        if(!canUse(player))
            return;
        if(prisonCooldown.containsKey(player)){
            player.sendMessage(getManager().prefix + "§7Spell is on cooldown! CD:§e" + prisonCooldown.get(player));
            return;
        }
        MerdianScepterShot shot = new MerdianScepterShot(getClan(), player);
        shot.launch();
        player.sendMessage(getManager().prefix + "§7Shot!");
        arrowPlayerHashMap.put(shot, player);
        prisonCooldown.put(player, 15);
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow)e.getDamager();
                for (MerdianScepterShot shot : arrowPlayerHashMap.keySet()) {
                    if (shot.getArrow() == arrow) {
                        arrow.teleport(new Location(arrow.getWorld(), 0.0D, -10.0D, 0.0D));
                        arrow.setKnockbackStrength(0);
                        shot.delete();
                    }
                }
            }
            return;
        }
        if (e.getDamager() instanceof Arrow &&
                e.getEntity() instanceof LivingEntity) {
            Arrow arrow = (Arrow)e.getDamager();
            LivingEntity struckEnt = (LivingEntity)e.getEntity();
            if (struckEnt.isDead())
                return;
            for (MerdianScepterShot shot : arrowPlayerHashMap.keySet()) {
                if (shot.getArrow() == arrow) {
                    if (shot.getShooter() == struckEnt) {
                        e.setCancelled(true);
                        continue;
                    }
                    arrow.setKnockbackStrength(0);
                    arrow.teleport(new Location(arrow.getWorld(), 0.0D, -10.0D, 0.0D));
                    String string = struckEnt.getType().toString().toLowerCase().replace("_", " ");
                }
                if(e.getEntity() instanceof Player) {
                    e.setCancelled(true);
                    shot.delete();
                    arrowPlayerHashMap.remove(arrow);
                    arrow.remove();
                    Bukkit.getConsoleSender().sendMessage("hit");
                    getManager().effectManager.registerEffect(EnumEffect.FROZEN, (Player) e.getEntity());
                    e.getEntity().sendMessage(getManager().prefix + "§7You are §aFROZEN. §7You cant move for 2 Seconds!");
                }
            }
        }
    }

    public ArrayList<Player>bonusMagic = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getPlayer().getItemInHand();
        if(!canUse(player))
            return;
        if(playerInteractEvent.getAction() != Action.RIGHT_CLICK_AIR && playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Mage"))
            return;
        if(!isAxe(itemStack))
            return;
        if(!canUse(player)){
            return;
        }
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
