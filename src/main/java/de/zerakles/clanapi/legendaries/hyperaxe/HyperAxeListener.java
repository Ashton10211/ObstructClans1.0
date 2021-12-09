package de.zerakles.clanapi.legendaries.hyperaxe;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class HyperAxeListener implements Listener {

    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public HashMap<Player, Legend> HyperAxes = new HashMap<>();

    public HyperAxeListener(){
        loop();
    }

    public void loop(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                ArrayList<LivingEntity> remoeee = new ArrayList<>();
                for (LivingEntity ent : toRemove.keySet()) {
                    if (ent.isDead())
                        continue;
                    if (toRemove.get(ent) > 0) {
                        if (toRemove.containsKey(ent))
                            toRemove.put(ent, toRemove.get(ent) - 1);
                        continue;
                    }
                    ent.setMaximumNoDamageTicks(20);
                    if (toRemove.containsKey(ent))
                        remoeee.add(ent);
                }
                for (LivingEntity entt : remoeee)
                    toRemove.remove(entt);
                for (String s : cooldown.keySet()) {
                    Player p = Bukkit.getServer().getPlayer(s);
                    if (p == null || !p.isOnline()) {
                        cooldown.remove(s);
                        continue;
                    }
                    if ((System.currentTimeMillis() - cooldown.get(s)) / 1000L > 12L) {
                        cooldown.remove(s);
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "You can use " + ChatColor.GREEN + "Hyper Rush");
                        if (isCorrectItem(p.getItemInHand(), p))
                            Display.display(ChatColor.GREEN + " Hyper Rush" + " Recharged", p);
                            p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                            continue;
                    }
                    if (isCorrectItem(p.getItemInHand(),p)) {
                        Double x = 16.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - (Long) cooldown.get(p.getName())) / 100L);
                        double divide = (System.currentTimeMillis() - (Long) cooldown.get(s)-4.5D) / 16000.0D;
                        String[] zz = x.toString().replace('.', '-').split("-");
                        String concat = zz[0] + "." + zz[1].charAt(0);
                        Display.displayProgress("§eRush", divide,
                                ChatColor.WHITE + " " + concat + " §eSeconds", false, p);
                    }
                }
            }
        },0,1);
    }


    private HashMap<LivingEntity, Integer> toRemove = new HashMap<>();

    @EventHandler
    public void onNormalDMG(EntityDamageEvent entityDamageEvent){
        if(entityDamageEvent.isCancelled()){
            return;
        }
        if(entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK &&
                entityDamageEvent.getEntity() instanceof LivingEntity){
            LivingEntity ent = (LivingEntity)entityDamageEvent.getEntity();
            if(toRemove.containsKey(ent)){
                toRemove.remove(ent);
                ent.setMaximumNoDamageTicks(20);
            }
        }
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            ItemStack item = p.getItemInHand();
            if (isCorrectItem(item,p)) {
                if (e.getEntity() instanceof LivingEntity) {
                    if (e.getEntity() instanceof Player && ((
                            (Player)e.getEntity()).getGameMode() == GameMode.CREATIVE || (
                            (Player)e.getEntity()).getGameMode() == GameMode.SPECTATOR))
                        return;
                    LivingEntity entLiv = (LivingEntity)e.getEntity();
                    entLiv.setMaximumNoDamageTicks(7);
                    entLiv.setVelocity(new Vector(0.0D, 0.12D, 0.0D));
                    toRemove.put(entLiv, 7);
                }
                e.setDamage(HyperAxes.get(p).getDamage());
            } else if (e.getEntity() instanceof LivingEntity) {
                LivingEntity entLiv = (LivingEntity)e.getEntity();
                if (toRemove.containsKey((LivingEntity) e.getEntity())) {
                    entLiv.setMaximumNoDamageTicks(20);
                    toRemove.remove((LivingEntity) e.getEntity());
                }
            }
        }
    }

    private HashMap<String, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                isCorrectItem(item,p)) {
            if (p.getLocation().getBlock().isLiquid()) {
                p.sendMessage(getClan().data.prefix + ChatColor.GRAY + "You cannot use " +
                        ChatColor.GREEN +  "HyperAxe" + ChatColor.GRAY +
                        " in water.");
                return;
            }
            if (cooldown.containsKey(p.getName())) {
                Double x = 16.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - (Long)cooldown.get(p.getName())) / 100L);
                String[] zz = x.toString().replace('.', '-').split("-");
                String concat = zz[0] + "." + zz[1].charAt(0);
                try {
                    p.sendMessage(getData().prefix + ChatColor.GRAY +
                            "Your cannot use " + ChatColor.GREEN + "Hyper Rush" + ChatColor.GRAY +
                            " for " + ChatColor.GREEN +
                            concat + " Seconds");
                } catch (IndexOutOfBoundsException exc) {
                    Bukkit.getServer().getLogger().warning("Index out of bounds in Hyper Axe msg. Should have been canceled");
                }
                return;
            }
            cooldown.put(p.getName(), System.currentTimeMillis());
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
            p.sendMessage(getData().prefix + ChatColor.GRAY + "You used " + ChatColor.GREEN +
                    "Hyper Rush");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (toRemove.containsKey(e.getPlayer())) {
            toRemove.remove(e.getPlayer());
            e.getPlayer().setMaximumNoDamageTicks(20);
        } else {
            e.getPlayer().setMaximumNoDamageTicks(20);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        LivingEntity livingEntity = e.getEntity();
        if (toRemove.containsKey(livingEntity)) {
            if (livingEntity instanceof LivingEntity)
                livingEntity.setMaximumNoDamageTicks(20);
            toRemove.remove(livingEntity);
        }
    }

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(HyperAxes.containsKey(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(item.getItemMeta().equals(HyperAxes.get(p).getItemStack().getItemMeta())){
                return true;
            }
            return false;
        }
        return false;
    }

}
