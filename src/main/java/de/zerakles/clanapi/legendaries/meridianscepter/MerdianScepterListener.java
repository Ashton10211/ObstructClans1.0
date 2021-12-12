package de.zerakles.clanapi.legendaries.meridianscepter;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.clanapi.legendaries.meridianscepter.utils.MerdianScepterShot;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class MerdianScepterListener implements Listener {

    private static boolean targetEnt;

    private static ArrayList<MerdianScepterShot> shots = new ArrayList<>();

    private HashMap<Legend, Integer> cooldown = new HashMap<>();

    private int damage = 11;

    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public HashMap<Legend, Player> Scepter = new HashMap<>();

    public int getDamage(){
        if(Scepter.size() > 0){
            for (Legend legend:Scepter.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: Scepter.keySet()
        ) {
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: Scepter.keySet()
        ) {
            if(Scepter.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
        }
        return false;
    }

    public MerdianScepterListener(){
        loop();
    }

    public static boolean isTargetEnt() {
        return targetEnt;
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
        int c = 3-cd;
        for(int s = 0; s<c; s++){
            green = green + "▌ ";
        }
        return green + red;
    }

    public  Legend legend(ItemStack itemStack){
        for (Legend legend:Scepter.keySet()
        ) {
            if(legend.getItemStack().equals(itemStack)){
                return legend;
            }
        }
        return null;
    }

    int tick = 0;

    public void loop(){

        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                tick = tick+1;
                if (!shots.isEmpty()) {
                    ArrayList<MerdianScepterShot> copy = (ArrayList<MerdianScepterShot>)shots.clone();
                    for (MerdianScepterShot shot : copy) {
                        if (!shot.getArrow().isDead() && !shot.isGone())
                            shot.update();
                    }
                }
                for (MerdianScepterShot shot : shots){
                    if(shot.getArrow().isOnGround()){
                        shot.delete();
                    }
                    if(tick == 20) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                           // all.playSound(shot.getArrow().getLocation(), Sound.SHOOT_ARROW, 1F, 1F);
                        }
                    }
                }
                for (Legend s : cooldown.keySet()) {
                    Player p = Scepter.get(s);
                    if (cooldown.get(s) == 0) {
                        cooldown.remove(s);
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "You can use " + ChatColor.GREEN + "MeridianScepter");
                        if (isCorrectItem(Utils.getItemInHand(p),p))
                           showActionbar(p, "§e§lLegendary Recharged");
                        continue;
                    }
                    if (isCorrectItem(Utils.getItemInHand(p),p)) {
                        int cd = cooldown.get(s);
                        String cdS = barCooldown(cd);
                        showActionbar(p, cdS);
                        if(tick == 20) {
                            cooldown.replace(s, cooldown.get(s) - 1);
                        }
                    }
                }
                if(tick == 20){
                    tick = 0;
                }
            }
        },0,1);
    }
    public static void removeShot(MerdianScepterShot shot) {
        if (!shots.contains(shot))
            return;
        shots.remove(shot);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                isCorrectItem(Utils.getItemInHand(p),p)) {
            if (p.getLocation().getBlock().isLiquid()) {
                p.sendMessage(getData().prefix + ChatColor.GRAY + "You cannot use " +
                        ChatColor.GREEN + Scepter.get(p).getName() + ChatColor.GRAY +
                        " in water.");
                return;
            }
            if (cooldown.containsKey(legend(e.getPlayer().getItemInHand())))
                return;
            if (Utils.is1_8()) {
                p.playSound(p.getLocation(), Sound.valueOf("BLAZE_BREATH"), 1.0F, 0.0F);
            } else {
                p.playSound(p.getLocation(), Sound.valueOf("ENTITY_BLAZE_AMBIENT"), 1.0F, 0.0F);
            }
            Legend legend = legend(e.getPlayer().getItemInHand());
            MerdianScepterShot shot = new MerdianScepterShot(getClan(), p);
            cooldown.put(legend, 3);
            shots.add(shot);
            shot.launch();
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            for (MerdianScepterShot shot : shots) {
                if (shot.getArrow() == arrow) {
                    Block hitBlock = e.getEntity().getLocation().getBlock();
                    if (hitBlock != null && hitBlock.getType() != Material.AIR) {
                        if (hitBlock.getType() == Material.STATIONARY_LAVA ||
                                hitBlock.getType() == Material.STATIONARY_WATER ||
                                hitBlock.getType() == Material.WATER ||
                                hitBlock.getType() == Material.LAVA)
                            return;
                        arrow.teleport(new Location(arrow.getWorld(), 0.0D, -10.0D, 0.0D));
                        if (shot.isToRemove())
                            continue;
                        shot.delete();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow)e.getEntity();
            for (MerdianScepterShot shot : shots) {
                if (shot.getArrow() == arrow) {
                    if (shot.isToRemove())
                        return;
                    shot.delete();
                }
            }
        }
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow)e.getDamager();
                for (MerdianScepterShot shot : shots) {
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
            for (MerdianScepterShot shot : shots) {
                if (shot.getArrow() == arrow) {
                    if (shot.getShooter() == struckEnt) {
                        e.setCancelled(true);
                        continue;
                    }
                    arrow.setKnockbackStrength(0);
                    arrow.teleport(new Location(arrow.getWorld(), 0.0D, -10.0D, 0.0D));
                    String string = struckEnt.getType().toString().toLowerCase().replace("_", " ");
                    shot.getShooter().sendMessage(ChatColor.BLUE + "Clans> " +
                            ChatColor.GRAY + "You struck " + ChatColor.YELLOW + string +
                            ChatColor.GRAY + " with your " + ChatColor.YELLOW + "MeridianScepter" + ChatColor.GRAY +
                            ".");
                    }
                    e.setCancelled(true);
                    shot.delete();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
                        if (struckEnt.isDead())
                            return;
                        struckEnt.damage(6);
                        struckEnt.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0));
                        struckEnt.getWorld().strikeLightningEffect(struckEnt.getLocation());
                    },60L);
                }
            }
        }


    public void quit(Player player) {}

    public void clearMem() {
        this.cooldown.clear();
        shots.clear();
    }
}
