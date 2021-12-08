package de.zerakles.clanapi.legendaries.runedpickaxe;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
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

    public HashMap<Player, Legend> RunedPickaxes = new HashMap<>();

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

    private HashMap<String, Long> cooldown = new HashMap<>();

    private HashMap<String, Long> instantMining = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = Utils.getItemInHand(p);
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (isCorrectItem(item,p)) {
                if (p.getLocation().getBlock().isLiquid()) {
                    p.sendMessage(getData().prefix + ChatColor.GRAY + "You cannot use " +
                            ChatColor.GREEN + RunedPickaxes.get(p).getName() + ChatColor.GRAY +
                            " in water.");
                    return;
                }
                if (cooldown.containsKey(p.getName())) {
                    Double x = 15.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - cooldown.get(p.getName())) / 100L);
                    String[] zz = x.toString().replace('.', '-').split("-");
                    String concat = zz[0] + "." + zz[1].charAt(0);
                    try {
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "Your cannot use " + ChatColor.GREEN + "Instant Mine" + ChatColor.GRAY +
                                " for " + ChatColor.GREEN +
                                concat + " Seconds");
                    } catch (IndexOutOfBoundsException exc) {
                        Bukkit.getServer().getLogger().warning("Index out of bounds in Runed Pickaxe msg. Should have been canceled");
                    }
                    return;
                }
                if (instantMining.containsKey(p.getName()))
                    return;
                instantMining.put(p.getName(), System.currentTimeMillis());
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

    public void loop(){
       Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
           @Override
           public void run() {
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
                   if ((System.currentTimeMillis() - instantMining.get(s)) / 1000L > 11L) {
                       instantMining.remove(s);
                       cooldown.put(p.getName(), System.currentTimeMillis());
                       continue;
                   }
                   if (isCorrectItem(p.getItemInHand(), p)) {
                       double divide = (System.currentTimeMillis() - instantMining.get(s)) / 12000.0D;
                       Display.displayProgress("Mine", divide,
                               null, true, p);
                   }
               }
               for (String s : cooldown.keySet()) {
                   Player p = Bukkit.getServer().getPlayer(s);
                   if (p == null || !p.isOnline()) {
                       cooldown.remove(s);
                       continue;
                   }
                   if ((System.currentTimeMillis() - cooldown.get(s)) / 1000L > 12L) {
                       cooldown.remove(s);
                       p.sendMessage(getData().prefix + ChatColor.GRAY +
                               "You can use " + ChatColor.GREEN + "Instant Mine");
                       if (isCorrectItem(p.getItemInHand(),p))
                           Display.display(ChatColor.GREEN + "Instant Mine " + " Recharged", p);
                       if (Utils.is1_8()) {
                           p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                           continue;
                       }
                       p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 5.0F, 1.0F);
                       continue;
                   }
                   if (isCorrectItem(p.getItemInHand(),p)) {
                       Double x = 15.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - cooldown.get(p.getName())) / 100L);
                       double divide = (System.currentTimeMillis() - cooldown.get(s)) / 15000.0D;
                       String[] zz = x.toString().replace('.', '-').split("-");
                       String concat = String.valueOf(zz[0]) + "." + zz[1].charAt(0);
                       Display.displayProgress("§e§lMine", divide,
                               ChatColor.WHITE + " " + concat + " §e§lSeconds", false, p);
                   }
               }for (Player p : Bukkit.getServer().getOnlinePlayers()) {
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
                   if ((System.currentTimeMillis() - instantMining.get(s)) / 1000L > 11L) {
                       instantMining.remove(s);
                       cooldown.put(p.getName(), System.currentTimeMillis());
                       continue;
                   }
                   if (isCorrectItem(Utils.getItemInHand(p),p)) {
                       double divide = (System.currentTimeMillis() - instantMining.get(s)) / 12000.0D;
                       Display.displayProgress("Mine", divide,
                               null, true, p);
                   }
               }
               for (String s : cooldown.keySet()) {
                   Player p = Bukkit.getServer().getPlayer(s);
                   if (p == null || !p.isOnline()) {
                       cooldown.remove(s);
                       continue;
                   }
                   if ((System.currentTimeMillis() - (Long) cooldown.get(s)) / 1000L > 14L) {
                       cooldown.remove(s);
                       p.sendMessage(getData().prefix + ChatColor.GRAY +
                               "You can use " + ChatColor.GREEN + "Instant Mine");
                       if (isCorrectItem(Utils.getItemInHand(p),p))
                           Display.display(ChatColor.GREEN + "Instant Mine " + " Recharged", p);
                       if (Utils.is1_8()) {
                           p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                           continue;
                       }
                       p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 5.0F, 1.0F);
                       continue;
                   }
                   if (isCorrectItem(Utils.getItemInHand(p),p)) {
                       Double x = 15.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - cooldown.get(p.getName())) / 100L);
                       double divide = (System.currentTimeMillis() - cooldown.get(s)) / 15000.0D;
                       String[] zz = x.toString().replace('.', '-').split("-");
                       String concat = String.valueOf(zz[0]) + "." + zz[1].substring(0, 1);
                       Display.displayProgress("Mine", divide,
                               ChatColor.WHITE + " " + concat + " Seconds", false, p);
                   }
               }for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                   if (!instantMining.containsKey(p.getName()))
                       if (isCorrectItem(Utils.getItemInHand(p),p))
                           p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 103));
               }
               for (String s : instantMining.keySet()) {
                   Player p = Bukkit.getServer().getPlayer(s);
                   if (p == null || !p.isOnline()) {
                       instantMining.remove(s);
                       continue;
                   }
                   if ((System.currentTimeMillis() - instantMining.get(s)) / 1000L > 11L) {
                       instantMining.remove(s);
                       cooldown.put(p.getName(), System.currentTimeMillis());
                       continue;
                   }
                   if (isCorrectItem(Utils.getItemInHand(p),p)) {
                       double divide = (System.currentTimeMillis() - instantMining.get(s)) / 12000.0D;
                       Display.displayProgress("Mine", divide,
                               null, true, p);
                   }
               }
               for (String s : cooldown.keySet()) {
                   Player p = Bukkit.getServer().getPlayer(s);
                   if (p == null || !p.isOnline()) {
                       cooldown.remove(s);
                       continue;
                   }
                   if ((System.currentTimeMillis() - cooldown.get(s)) / 1000L > 14L) {
                       cooldown.remove(s);
                       p.sendMessage(getData().prefix + ChatColor.GRAY +
                               "You can use " + ChatColor.GREEN + "Instant Mine");
                       if (isCorrectItem(Utils.getItemInHand(p),p))
                           Display.display(ChatColor.GREEN + "Instant Mine " + " Recharged", p);
                       if (Utils.is1_8()) {
                           p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                           continue;
                       }
                       p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 5.0F, 1.0F);
                       continue;
                   }
                   if (isCorrectItem(Utils.getItemInHand(p),p)) {
                       Double x = 15.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - cooldown.get(p.getName())) / 100L);
                       double divide = (System.currentTimeMillis() - cooldown.get(s)) / 15000.0D;
                       String[] zz = x.toString().replace('.', '-').split("-");
                       String concat = String.valueOf(zz[0]) + "." + zz[1].charAt(0);
                       Display.displayProgress("Mine", divide,
                               ChatColor.WHITE + " " + concat + " Seconds", false, p);
                   }
               }
           }
       },0,1);
    }



    private boolean isCorrectItem(ItemStack item, Player p) {
        if(RunedPickaxes.containsKey(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(item.getItemMeta().equals(RunedPickaxes.get(p).getItemStack().getItemMeta())){
                return true;
            }
            return false;
        }
        return false;
    }
}
