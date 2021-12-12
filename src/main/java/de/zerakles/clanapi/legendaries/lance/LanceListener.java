package de.zerakles.clanapi.legendaries.lance;

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
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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

    public HashMap<Legend, Player> Lances = new HashMap<>();

    public int getDamage(){
        if(Lances.size() > 0){
            for (Legend legend:Lances.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: Lances.keySet()
        ) {
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: Lances.keySet()
        ) {
            if(Lances.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
        }
        return false;
    }

    public LanceListener() {
        loop();
    }

    public void loop() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {

                for (Legend s : Cooldown.keySet()) {
                    Player p = Lances.get(s);
                    if (p == null || !p.isOnline()) {
                        Cooldown.remove(s);
                        continue;
                    }
                    if ((System.currentTimeMillis() - Cooldown.get(s)) / 1000L > 12L) {
                        Cooldown.remove(s);
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "You can use " + ChatColor.GREEN + "Charge");
                        if (isCorrectItem(p.getItemInHand(), p))
                            showActionbar(p, "§6§lCharge recharged");
                        p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                        continue;
                    }
                    if (isCorrectItem(p.getItemInHand(),p)) {
                        int cd = Cooldown.get(s);
                        String cdS  = barCooldown(cd);
                        showActionbar(p, cdS);
                        continue;
                    }
                }
                for (Legend legend: Cooldown.keySet()
                ) {
                    Cooldown.replace(legend,Cooldown.get(legend) -1);
                }
            }
        }, 0, 1);
    }

    private boolean isCorrectItem(ItemStack item, Player p) {
        if (haveLegendary(p)) {
            if (!item.hasItemMeta()) {
                return false;
            }
            if (checkItemInHand(item, p)) {
                return true;
            }
            return false;
        }
        return false;
    }

    ArrayList<Player> firstHit = new ArrayList<>();
    HashMap<Player, Long> stunnedPlayer = new HashMap<>();
    HashMap<Legend, Integer> Cooldown = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent playerInteractEvent) {
        if (isCorrectItem(playerInteractEvent.getPlayer().getItemInHand(), playerInteractEvent.getPlayer())) {
            Player player = playerInteractEvent.getPlayer();
            ItemStack itemStack = player.getItemInHand();
            if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR
                    || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Legend legend = legend(itemStack);
                if (Cooldown.containsKey(legend)) {
                    if (Cooldown.containsKey(legend)){
                        player.sendMessage(getClan().data.prefix + "§7You have a cooldown on this §aHyperAxe. §eCD§7: §a"
                                + Cooldown.get(legend));
                        return;
                    }
                } else {
                    Cooldown.put(legend, 12);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 6, 8));
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
                    e.setDamage(getDamage()+2);
                } else {
                    e.setDamage(getDamage());
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
        for (Legend legend:Lances.keySet()
        ) {
            if(legend.getItemStack().equals(itemStack)){
                return legend;
            }
        }
        return null;
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
