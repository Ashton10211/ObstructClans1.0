package de.zerakles.clanapi.legendaries.lance;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class LanceListener implements Listener {
    private Clan getClan() {
        return Clan.getClan();
    }

    private ClanAPI getClanAPI() {
        return getClan().getClanAPI();
    }

    private Data getData() {
        return getClan().data;
    }

    public HashMap<Player, Legend> Lances = new HashMap<>();

    public LanceListener() {
        loop();
    }

    public void loop() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {

                for (Player p : Cooldown.keySet()) {
                    if (p == null || !p.isOnline()) {
                        Cooldown.remove(p);
                        continue;
                    }
                    if ((System.currentTimeMillis() - Cooldown.get(p)) / 1000L > 12L) {
                        Cooldown.remove(p);
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "You can use " + ChatColor.GREEN + "Charge");
                        if (isCorrectItem(p.getItemInHand(), p))
                            Display.display(ChatColor.GREEN + " Charge" + " Recharged", p);
                        p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                        continue;
                    }
                    if (isCorrectItem(p.getItemInHand(), p)) {
                        Double x = 16.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - (Long) Cooldown.get(p)) / 100L);
                        double divide = (System.currentTimeMillis() - (Long) Cooldown.get(p) - 4.5D) / 16000.0D;
                        String[] zz = x.toString().replace('.', '-').split("-");
                        String concat = zz[0] + "." + zz[1].charAt(0);
                        Display.displayProgress("§eCharge", divide,
                                ChatColor.WHITE + " " + concat + " §eSeconds", false, p);
                    }
                }
            }
        }, 0, 1);
    }

    private boolean isCorrectItem(ItemStack item, Player p) {
        if (Lances.containsKey(p)) {
            if (!item.hasItemMeta()) {
                return false;
            }
            if (item.getItemMeta().equals(Lances.get(p).getItemStack().getItemMeta())) {
                return true;
            }
            return false;
        }
        return false;
    }

    ArrayList<Player> firstHit = new ArrayList<>();
    HashMap<Player, Long> stunnedPlayer = new HashMap<>();
    HashMap<Player, Long> Cooldown = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent playerInteractEvent) {
        if (isCorrectItem(playerInteractEvent.getPlayer().getItemInHand(), playerInteractEvent.getPlayer())) {
            Player player = playerInteractEvent.getPlayer();
            ItemStack itemStack = player.getItemInHand();
            if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR
                    || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (Cooldown.containsKey(player)) {
                    Double x = 16.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - (Long) Cooldown.get(player.getName())) / 100L);
                    String[] zz = x.toString().replace('.', '-').split("-");
                    String concat = zz[0] + "." + zz[1].charAt(0);
                    player.sendMessage(getData().prefix + ChatColor.GRAY +
                            "Your cannot use " + ChatColor.GREEN + "Charge" + ChatColor.GRAY +
                            " for " + ChatColor.GREEN +
                            concat + " Seconds");
                    return;
                } else {
                    Cooldown.put(player, System.currentTimeMillis());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 6, 6));
                    player.playSound(player.getLocation(), Sound.HORSE_GALLOP, 1F, 1F);
                    player.sendMessage(getData().prefix + ChatColor.GRAY + "You used " + ChatColor.GREEN +
                            "Charge.");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            ItemStack item = Utils.getItemInHand(p);
            if (isCorrectItem(item, p)) {
                if(Cooldown.containsKey(p)){
                    e.setDamage(Lances.get(p).getDamage()+2);
                } else {
                    e.setDamage(Lances.get(p).getDamage());
                }
                if (!(e.getEntity() instanceof Player)) {
                    return;
                }
                if (firstHit.contains(p)) {
                    stunnedPlayer.put((Player) e.getEntity(), System.currentTimeMillis());
                    e.getEntity().setFireTicks(20 * 3);
                    p.sendMessage(getData().prefix + "§7Player stunned!");
                    firstHit.remove(p);
                } else {
                    firstHit.add(p);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        if (stunnedPlayer.containsKey(playerMoveEvent.getPlayer())) {
            if (System.currentTimeMillis() - stunnedPlayer.get(playerMoveEvent.getPlayer()) == 1L
                    || System.currentTimeMillis() - stunnedPlayer.get(playerMoveEvent.getPlayer()) / 1000L > 0.8) {
                stunnedPlayer.remove(playerMoveEvent.getPlayer());
                return;
            }
            playerMoveEvent.setCancelled(true);
            playerMoveEvent.getPlayer().sendMessage(getData().prefix + "§7You are stunned!");
            return;
        }
    }
}
