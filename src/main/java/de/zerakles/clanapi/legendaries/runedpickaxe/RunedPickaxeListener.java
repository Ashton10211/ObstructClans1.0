package de.zerakles.clanapi.legendaries.runedpickaxe;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class RunedPickaxeListener implements Listener {
    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public HashMap<Legend, Player> RunedPickaxes = new HashMap<>();
    public int getDamage(){
        if(RunedPickaxes.size() > 0){
            for (Legend legend:RunedPickaxes.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: RunedPickaxes.keySet()
        ) {
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: RunedPickaxes.keySet()
        ) {
            if(RunedPickaxes.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
        }
        return false;
    }

    public RunedPickaxeListener(){
        loop();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockMine(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (isCorrectItem(p.getItemInHand(),p))
            b.breakNaturally();
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
        for (Legend legend:RunedPickaxes.keySet()
        ) {
            if(legend.getItemStack().equals(itemStack)){
                return legend;
            }
        }
        return null;
    }

    private HashMap<Legend, Integer> cooldown = new HashMap<>();

    private HashMap<String, Integer> instantMining = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = Utils.getItemInHand(p);
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (isCorrectItem(item,p)) {
                if (p.getLocation().getBlock().isLiquid()) {
                    p.sendMessage(getData().prefix + ChatColor.GRAY + "You cannot use " +
                            ChatColor.GREEN + "§aRunedPickaxe" + ChatColor.GRAY +
                            " in water.");
                    return;
                }
                Legend legend = legend(item);
                if (cooldown.containsKey(legend)){
                    p.sendMessage(getClan().data.prefix + "§7You have a cooldown on this §RunedPickaxe. §eCD§7: §a"
                            + cooldown.get(legend));
                    return;
                }
                if (instantMining.containsKey(p.getName()))
                    return;
                instantMining.put(p.getName(), 12);
                p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                Display.displayTitleAndSubtitle(p, " ", ChatColor.WHITE + "Instant mine enabled for " + ChatColor.YELLOW +
                        "12 Seconds", 5, 30, 5);
                p.sendMessage(getData().prefix + ChatColor.GRAY + "You used " + ChatColor.GREEN +
                        "Instant Mine");
            }
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK &&
                isCorrectItem(item,p)) {
            if (instantMining.containsKey(p.getName())) {
                if (e.isCancelled())
                    return;
                BlockBreakEvent newEvent = new BlockBreakEvent(e.getClickedBlock(), e.getPlayer());
                Bukkit.getServer().getPluginManager().callEvent(newEvent);
            }
        }
    }
    int tick = 0;
    public void loop(){
       Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
           @Override
           public void run() {
               tick++;
               for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                   if (!instantMining.containsKey(p.getName()))
                       if (isCorrectItem(p.getItemInHand(),p))
                           p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 103));
               }
               for (String s : instantMining.keySet()) {
                   Player p = Bukkit.getServer().getPlayer(s);
                   if (p == null || !p.isOnline()) {
                       instantMining.remove(s);
                       continue;
                   }
                   if (instantMining.get(s) == 0) {
                       instantMining.remove(s);
                       Legend legend = legend(p.getItemInHand());
                       cooldown.put(legend,4);
                       continue;
                   }
                   if (isCorrectItem(p.getItemInHand(), p)) {
                       int ins = instantMining.get(s);
                       String insS = barCooldown(ins);
                       showActionbar(p, insS);
                       if(tick == 20){
                           instantMining.replace(s, instantMining.get(s)-1);
                       }
                   }
               }
               for (Legend s : cooldown.keySet()) {
                   Player p = RunedPickaxes.get(s);
                   if (p == null || !p.isOnline()) {
                       cooldown.remove(s);
                       continue;
                   }
                   if (cooldown.get(s) == 0) {
                       cooldown.remove(s);
                       p.sendMessage(getData().prefix + ChatColor.GRAY +
                               "You can use " + ChatColor.GREEN + "Instant Mine");
                       if (isCorrectItem(p.getItemInHand(),p))
                           showActionbar(p, "§6§lInstant Mine Recharged");
                       if (Utils.is1_8()) {
                           p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                           continue;
                       }
                       p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 5.0F, 1.0F);
                       continue;
                   }
                   if (isCorrectItem(p.getItemInHand(),p)) {
                       int cd = cooldown.get(s);
                       String cdS = barCooldown(cd);
                       showActionbar(p, cdS);
                       if(tick == 20){
                           cooldown.replace(s, cooldown.get(s)-1);
                       }
                   }
               }
               if(0 == 20){
                   tick = 0;
               }
           }
       },0,1);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(!(entityDamageByEntityEvent.getDamager() instanceof  Player)){
            return;
        }
        if(haveLegendary((Player) entityDamageByEntityEvent.getDamager())){
            if(checkItemInHand(((Player) entityDamageByEntityEvent.getDamager()).getItemInHand(), (Player) entityDamageByEntityEvent.getDamager())){
                entityDamageByEntityEvent.setDamage(getDamage());
            }
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
