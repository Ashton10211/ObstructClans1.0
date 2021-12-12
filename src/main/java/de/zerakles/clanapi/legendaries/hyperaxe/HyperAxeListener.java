package de.zerakles.clanapi.legendaries.hyperaxe;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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

    public HashMap<Legend, Player> HyperAxes = new HashMap<>();

    public int getDamage(){
        if(HyperAxes.size() > 0){
            for (Legend legend:HyperAxes.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: HyperAxes.keySet()
        ) {
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: HyperAxes.keySet()
        ) {
            if(HyperAxes.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
        }
        return false;
    }

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
                for (Legend s : cooldown.keySet()) {
                    Player p = HyperAxes.get(s);
                    if (p == null || !p.isOnline()) {
                        cooldown.remove(s);
                        continue;
                    }
                    if (cooldown.get(s) == 0) {
                        cooldown.remove(s);
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "You can use " + ChatColor.GREEN + "Hyper Rush");
                        if (isCorrectItem(p.getItemInHand(), p))
                            showActionbar(p, "§6§lHyper Rush Recharged");
                            p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                            continue;
                    }
                    if (isCorrectItem(p.getItemInHand(),p)) {
                        int cd = cooldown.get(s);
                        String cdS  = barCooldown(cd);
                        showActionbar(p, cdS);
                        continue;
                    }
                }
                for (Legend legend: cooldown.keySet()
                     ) {
                    cooldown.replace(legend,cooldown.get(legend) -1);
                }
            }
        },0,20);
    }

    private void showActionbar(Player player, String text) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    private String barCooldown(int cd){
        String green = "§a§l";
        String red = "§c§l";
        for(int s = 0; s<cd; s++){
            red = red + "▌ ";
        }
        int c = 12-cd;
        for(int s = 0; s<c; s++){
            green = green + "▌ ";
        }
        return green + red;
    }

    public  Legend legend(ItemStack itemStack){
        for (Legend legend:HyperAxes.keySet()
             ) {
            if(legend.getItemStack().equals(itemStack)){
                return legend;
            }
        }
        return null;
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
                e.setDamage(getDamage());
            } else if (e.getEntity() instanceof LivingEntity) {
                LivingEntity entLiv = (LivingEntity)e.getEntity();
                if (toRemove.containsKey((LivingEntity) e.getEntity())) {
                    entLiv.setMaximumNoDamageTicks(20);
                    toRemove.remove((LivingEntity) e.getEntity());
                }
            }
        }
    }

    private HashMap<Legend, Integer> cooldown = new HashMap<>();

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
            Legend legend = legend(item);
            if (cooldown.containsKey(legend)){
                p.sendMessage(getClan().data.prefix + "§7You have a cooldown on this §aHyperAxe. §eCD§7: §a"
                        + cooldown.get(legend));
                return;
            }
            cooldown.put(legend, 12);
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
            livingEntity.setMaximumNoDamageTicks(20);
            toRemove.remove(livingEntity);
        }
    }

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(haveLegendary(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(checkItemInHand(item, p)){
                return true;
            }
            return false;
        }
        return false;
    }

}
