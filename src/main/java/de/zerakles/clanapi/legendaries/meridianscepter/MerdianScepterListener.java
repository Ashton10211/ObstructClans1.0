package de.zerakles.clanapi.legendaries.meridianscepter;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.clanapi.legendaries.meridianscepter.utils.MerdianScepterShot;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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

    private HashMap<String, Long> cooldown = new HashMap<>();

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

    public HashMap<Player, Legend> Scepter = new HashMap<>();

    public MerdianScepterListener(){
        loop();
    }

    public static boolean isTargetEnt() {
        return targetEnt;
    }

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(Scepter.containsKey(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(item.getItemMeta().equals(Scepter.get(p).getItemStack().getItemMeta())){
                return true;
            }
            return false;
        }
        return false;
    }
    public void loop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                if (!shots.isEmpty()) {
                    ArrayList<MerdianScepterShot> copy = (ArrayList<MerdianScepterShot>)shots.clone();
                    for (MerdianScepterShot shot : copy) {
                        if (!shot.getArrow().isDead() && !shot.isGone())
                            shot.update();
                    }
                }
                for (String s : cooldown.keySet()) {
                    Player p = Bukkit.getServer().getPlayer(s);
                    if ((System.currentTimeMillis() - cooldown.get(s) / 1000L > 1L)) {
                        cooldown.remove(s);
                        p.sendMessage(getData().prefix + ChatColor.GRAY +
                                "You can use " + ChatColor.GREEN + Scepter.get(p));
                        if (isCorrectItem(Utils.getItemInHand(p),p))
                            Display.display(ChatColor.GREEN + Scepter.get(p).getName() + " Recharged", p);
                        continue;
                    }
                    if (isCorrectItem(Utils.getItemInHand(p),p)) {
                        Double x = 2.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - cooldown.get(p.getName())) / 100L);
                        double divide = (System.currentTimeMillis() - cooldown.get(s)) / 2000.0D;
                        String[] zz = x.toString().replace('.', '-').split("-");
                        String concat = String.valueOf(zz[0]) + "." + zz[1].charAt(0);
                        Display.displayProgress(Scepter.get(p).getName(), divide,
                                ChatColor.WHITE + " " + concat + " Seconds", false, p);
                    }
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
            if (this.cooldown.containsKey(p.getName()))
                return;
            if (Utils.is1_8()) {
                p.playSound(p.getLocation(), Sound.valueOf("BLAZE_BREATH"), 1.0F, 0.0F);
            } else {
                p.playSound(p.getLocation(), Sound.valueOf("ENTITY_BLAZE_AMBIENT"), 1.0F, 0.0F);
            }
            MerdianScepterShot shot = new MerdianScepterShot(getClan(), p);
            this.cooldown.put(p.getName(), System.currentTimeMillis());
            shots.add(shot);
            shot.launch();
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow)e.getEntity();
            for (MerdianScepterShot shot : shots) {
                if (shot.getArrow() == arrow) {
                    if (Utils.is1_8()) {
                        Block block = null;
                        Block hitBlock = null;
                        block = shot.getArrow().getLocation().getBlock();
                        Material type = null;
                        type = block.getType();
                        hitBlock = null;
                        Class<?> movingObjectPosition = Utils.getNmsClass("MovingObjectPosition");
                        Class<?> blockPositionn = Utils.getNmsClass("BlockPosition");
                        Constructor<?> construct = Utils.getConstructor(movingObjectPosition, Entity.class);
                        Object position = Utils.callConstructor(construct, arrow);
                        Object posType = Utils.getFieldAndValue(movingObjectPosition, "type", position);
                        if (posType == Utils.getEnumConstant(movingObjectPosition, "BLOCK")) {
                            Object blockposition = Utils.getAndInvokeMethod(movingObjectPosition, "a",
                                    new Class[0], position);
                            hitBlock = e.getEntity().getWorld().getBlockAt(new Location(e.getEntity().getWorld(), (Double) Utils.getAndInvokeMethod(blockPositionn, "getX",
                                    new Class[0], blockposition, new Object[0]), (Double) Utils.getAndInvokeMethod(blockPositionn, "getY",
                                    new Class[0], blockposition, new Object[0]), (Double) Utils.getAndInvokeMethod(blockPositionn, "getZ",
                                    new Class[0], blockposition, new Object[0])));
                        }
                        if (hitBlock != null &&
                                hitBlock.getType() != Material.AIR) {
                            if (type == Material.STATIONARY_LAVA ||
                                    type == Material.STATIONARY_WATER ||
                                    type == Material.WATER ||
                                    type == Material.LAVA)
                                return;
                            arrow.teleport(new Location(arrow.getWorld(), 0.0D, -10.0D, 0.0D));
                            if (shot.isToRemove())
                                continue;
                            shot.delete();
                        }
                        continue;
                    }
                    Block output = (Block)Utils.getAndInvokeMethod(ProjectileHitEvent.class, "getHitBlock",
                            new Class[0], e, new Object[0]);
                    if (output != null && output.getType() != Material.AIR) {
                        if (output.getType() == Material.STATIONARY_LAVA ||
                                output.getType() == Material.STATIONARY_WATER ||
                                output.getType() == Material.WATER ||
                                output.getType() == Material.LAVA)
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
                    arrow.teleport(new Location(arrow.getWorld(), 0.0D, -10.0D, 0.0D));
                    if (struckEnt instanceof Player) {
                        Player struck = (Player)struckEnt;
                        shot.getShooter().sendMessage(ChatColor.BLUE + "Clans> " +
                                ChatColor.GRAY + "You struck " + ChatColor.YELLOW + struck.getName() +
                                ChatColor.GRAY + " with your " + ChatColor.YELLOW + Scepter.get(struck).getName() + ChatColor.GRAY +
                                ".");
                        struck.sendMessage(ChatColor.BLUE + "Clans> " +
                                ChatColor.YELLOW + shot.getShooter().getName() + ChatColor.GRAY +
                                " hit you with a " + ChatColor.YELLOW + Scepter.get(struck).getName() + ChatColor.GRAY +
                                ".");
                    } else {
                        String string = struckEnt.getType().toString().toLowerCase().replace("_", " ");
                        shot.getShooter().sendMessage(ChatColor.BLUE + "Clans> " +
                                ChatColor.GRAY + "You struck " + ChatColor.YELLOW + string +
                                ChatColor.GRAY + " with your " + ChatColor.YELLOW + Scepter.get((Player) struckEnt).getName() + ChatColor.GRAY +
                                ".");
                    }
                    e.setCancelled(true);
                    Player p = shot.getShooter();
                    shot.delete();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
                        if (p.isDead())
                            return;
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0));
                        p.getWorld().strikeLightningEffect(p.getLocation());
                        p.damage(this.damage, (Entity)p);
                    },60L);
                }
            }
        }
    }

    public void quit(Player player) {}

    public void clearMem() {
        this.cooldown.clear();
        shots.clear();
    }
}
