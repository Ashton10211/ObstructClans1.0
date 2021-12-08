package de.zerakles.clanapi.legendaries.windblade;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class WindBladeListener implements Listener {
    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public HashMap<Player, Legend> WindBlades = new HashMap<>();

    public WindBladeListener(){
        loop();
    }

    public void loop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    doCoolDowns();
                    ItemStack item = p.getItemInHand();
                    if (isCorrectItem(item,p)) {
                        onUpdate(p);
                        continue;
                    }
                    memoryRemove(p, false);
                }
            }
        },0,1);
    }

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(WindBlades.containsKey(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(item.getItemMeta().equals(WindBlades.get(p).getItemStack().getItemMeta())){
                return true;
            }
            return false;
        }
        return false;
    }

    public static final double MAX_COUNTS = 5.0D;

    public static double VEL_MULT = 0.66D;

    public static final double CHARGE_COST = 0.02D;

    public static boolean isInfinite = false;

    public static final Object enumConst = Utils.getEnumConstant(Utils.getNmsClass("EnumParticle"), "EXPLOSION_NORMAL");

    private HashMap<String, Float> charges = new HashMap<>();

    private HashMap<String, Float> groundCounts = new HashMap<>();

    private HashMap<String, Long> cooldown = new HashMap<>();

    private HashMap<String, Float> smoother = new HashMap<>();

    private HashMap<String, Vector> veccs = new HashMap<>();

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            ItemStack item = Utils.getItemInHand(p);
            if (isCorrectItem(item,p))
                e.setDamage(WindBlades.get(p).getDamage());
        }
    }

    @EventHandler
    public void dmg(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (isCorrectItem(Utils.getItemInHand(p),p))
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
                    e.setCancelled(true);
        }
    }

    public void onUpdate(Player p) {
        if (!charges.containsKey(p.getName()))
            charges.put(p.getName(), 0.0F);
        if (Utils.onGround(p))
            Charge(p);
        if (!veccs.containsKey(p.getName()))
            veccs.put(p.getName(), null);
        if (!smoother.containsKey(p.getName())) {
            smoother.put(p.getName(), 0.0F);
        } else if (smoother.get(p.getName()) != null &&  smoother.get(p.getName()) > 0.0F) {
            if (veccs.get(p.getName()) != null) {
                if (!isInfinite)
                    charges.put(p.getName(),
                            (float) Math.max(0.0D, GetCharge(p) - 0.02D));
                p.setVelocity(veccs.get(p.getName()));
            }
        } else if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            p.setAllowFlight(true);
        } else {
            p.setAllowFlight(false);
        }
        if (!groundCounts.containsKey(p.getName()))
            groundCounts.put(p.getName(), 0.0F);
        groundCounts.put(p.getName(), Math.max(0.0F, (Float) groundCounts.get(p.getName()) - 0.15F));
        smoother.put(p.getName(), (float) Math.max(0.0D, (Float) smoother.get(p.getName()) - 0.5D));
        if (GetCharge(p) == 0.0F) {
            Display.displayProgress(null, 0.0D, null, false, p);
        } else {
            Display.displayProgress(null, GetCharge(p), null, false, p);
        }
    }

    public boolean Charge(Player player) {
        if (!charges.containsKey(player.getName()))
            charges.put(player.getName(), 0.0F);
        float charge = charges.get(player.getName());
        charge = (float)Math.min(1.0D, charge + 0.01D);
        charges.put(player.getName(), charge);
        Display.displayProgress(null, charge, null, false, player);
        return (charge >= 1.0F);
    }

    public float GetCharge(Player player) {
        if (!charges.containsKey(player.getName()))
            charges.put(player.getName(), 0.0F);
        return charges.get(player.getName());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = Utils.getItemInHand(p);
        if ((e.getAction() == Action.RIGHT_CLICK_AIR ||
                e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                isCorrectItem(item,p)) {
            if (p.getLocation().getBlock().isLiquid()) {
                p.sendMessage(getData().prefix + ChatColor.GRAY + "You cannot use " +
                        ChatColor.GREEN + "Wind Rider" + ChatColor.GRAY +
                        " in water.");
                return;
            }
            if (GetCharge(p) <= 0.0F)
                return;
            if (cooldown.containsKey(p.getName())) {
                Double x = 3.0D - Math.pow(10.0D, -1.0D) * ((System.currentTimeMillis() - cooldown.get(p.getName())) / 100L);
                String[] zz = x.toString().replace('.', '-').split("-");
                String concat = String.valueOf(zz[0]) + "." + zz[1].substring(0, 1);
                try {
                    p.sendMessage(getData().prefix + ChatColor.GRAY +
                            "Your flight powers will recharge in " + ChatColor.GREEN +
                            concat + " Seconds");
                } catch (IndexOutOfBoundsException exc) {
                    Bukkit.getServer().getLogger().warning("Index out of bounds in Wind Blade msg. Should have been canceled");
                }
                return;
            }
            windLaunch(p);
        }
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent e) {
        if ((e.getPlayer().getGameMode() == GameMode.SURVIVAL || e.getPlayer().getGameMode() == GameMode.ADVENTURE) &&
                isCorrectItem(Utils.getItemInHand(e.getPlayer()), e.getPlayer())) {
            if (smoother.get(e.getPlayer().getName()) == null)
                return;
            if (smoother.get(e.getPlayer().getName()) == 0.0F)
                return;
            e.setCancelled(true);
        }
    }

    private void windLaunch(Player p) {
        Vector vec = p.getLocation().getDirection();
        if (Double.isNaN(vec.getX()) || Double.isNaN(vec.getY()) ||
                Double.isNaN(vec.getZ()) || vec.length() == 0.0D)
            return;
        vec.normalize();
        vec.multiply(VEL_MULT);
        if (Utils.onGround(p))
            if (!groundCounts.containsKey(p.getName())) {
                groundCounts.put(p.getName(), 1.0F);
            } else {
                groundCounts.put(p.getName(), groundCounts.get(p.getName()) + 1.0F);
                if (Utils.onGround(p))
                    if (Utils.is1_8()) {
                        p.playSound(p.getLocation(), Sound.valueOf("LAVA_POP"), 13.0F, 2.0F);
                    } else {
                        p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_SNARE"), 13.0F, 2.0F);
                    }
            }
        vec = p.getLocation().getDirection();
        vec.normalize();
        vec.multiply(VEL_MULT);
        p.setVelocity(vec);
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            if (pl.getLocation().distance(p.getLocation()) < 64.0D)
                doParticles(pl, (float)p.getLocation().getX(),
                        (float)p.getLocation().getY() + 1.0F,
                        (float)p.getLocation().getZ());
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
            Player paramPlayer = p;
            if (Utils.onGround(paramPlayer))
                if (Utils.is1_8()) {
                    paramPlayer.playSound(paramPlayer.getLocation(), Sound.valueOf("LAVA_POP"), 13.0F, 2.0F);
                } else {
                    paramPlayer.playSound(paramPlayer.getLocation(), Sound.valueOf("BLOCK_NOTE_SNARE"), 13.0F, 2.0F);
                }
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if (pl.getLocation().distance(paramPlayer.getLocation()) < 64.0D)
                    doParticles(pl, (float)paramPlayer.getLocation().getX(), (float)paramPlayer.getLocation().getY() + 1.0F, (float)paramPlayer.getLocation().getZ());
            }
            Vector vec2 = p.getLocation().getDirection();
            vec2.normalize();
            vec2.multiply(VEL_MULT);
            paramPlayer.setVelocity(vec2);
        },2L);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
            Player paramPlayer = p;
            if (Utils.onGround(paramPlayer))
                if (Utils.is1_8()) {
                    paramPlayer.playSound(paramPlayer.getLocation(), Sound.valueOf("LAVA_POP"), 13.0F, 2.0F);
                } else {
                    paramPlayer.playSound(paramPlayer.getLocation(), Sound.valueOf("BLOCK_NOTE_SNARE"), 13.0F, 2.0F);
                }
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if (pl.getLocation().distance(paramPlayer.getLocation()) < 64.0D)
                    doParticles(pl, (float)paramPlayer.getLocation().getX(), (float)paramPlayer.getLocation().getY() + 1.0F, (float)paramPlayer.getLocation().getZ());
            }
            Vector vec2 = p.getLocation().getDirection();
            vec2.normalize();
            vec2.multiply(VEL_MULT);
            paramPlayer.setVelocity(vec2);
        },3L);
        if (groundCounts.get(p.getName()) > 5.0D) {
            groundCounts.put(p.getName(), 0.0F);
            p.sendMessage(getData().prefix + ChatColor.GRAY +
                    "Flight powers diminished whilst scraping the ground. Recharging in " + ChatColor.GREEN +
                    "3.0 Seconds");
            if (Utils.is1_8()) {
                p.playSound(p.getLocation(), Sound.valueOf("ANVIL_USE"), 3.0F, 1.0F);
            } else {
                p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ANVIL_USE"), 3.0F, 1.0F);
            }
            cooldown.put(p.getName(), System.currentTimeMillis());
            return;
        }
        veccs.put(p.getName(), vec);
        if (!smoother.containsKey(p.getName()))
            smoother.put(p.getName(), 0.0F);
        smoother.put(p.getName(), Math.min(5.0F, smoother.get(p.getName()) + 1.0F));
        if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
            p.setAllowFlight(true);
        if (Utils.is1_8()) {
            p.getWorld().playSound(p.getLocation(), Sound.valueOf("FIZZ"), 1.2F, 1.5F);
        } else {
            p.getWorld().playSound(p.getLocation(),
                    Sound.valueOf("BLOCK_LAVA_EXTINGUISH"), 1.2F, 1.5F);
        }
    }
    private void memoryRemove(Player p, boolean logged) {
        if (logged) {
            if (cooldown.containsKey(p.getName()))
                cooldown.remove(p.getName());
            if (charges.containsKey(p.getName()))
                charges.remove(p.getName());
        }
        if (veccs.containsKey(p.getName()))
            veccs.remove(p.getName());
        if (smoother.containsKey(p.getName())) {
            if (p.getGameMode() == GameMode.SPECTATOR || p.getGameMode() == GameMode.CREATIVE) {
                p.setAllowFlight(true);
                smoother.remove(p.getName());
            } else {
                p.setAllowFlight(false);
            }
            smoother.remove(p.getName());
        }
        if (groundCounts.containsKey(p.getName())) {
            if (logged) {
                if (groundCounts.containsKey(p.getName()))
                    groundCounts.remove(p.getName());
                return;
            }
            if (!p.getInventory().contains(WindBlades.get(p).getItemStack()) &&
                    groundCounts.containsKey(p.getName()))
                groundCounts.remove(p.getName());
        }
    }

    @EventHandler
    public void onDie(PlayerDeathEvent e) {
        Player p = e.getEntity();
        memoryRemove(p, true);
    }

    private void doCoolDowns() {
        for (String s : cooldown.keySet()) {
            if (System.currentTimeMillis() - (Long) cooldown.get(s) >= 3000L) {
                Player p = Bukkit.getServer().getPlayer(s);
                cooldown.remove(s);
                Player pl = Bukkit.getServer().getPlayer(s);
                pl.sendMessage(getData().prefix +
                        ChatColor.GRAY + "Your flight powers have replenished!");
                if (Utils.is1_8()) {
                    p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 5.0F, 1.0F);
                    continue;
                }
                p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 5.0F, 1.0F);
            }
        }
    }

    public void doParticles(Player pl, float x, float y, float z) {
        Object object = enumConst;
        Utils.sendParticles(pl,
                object,
                false,
                x,
                y,
                z,
                0.012F,
                0.2F,
                0.012F,
                0.1F,
                4,
                new int[0]);
    }

    public void quit(Player player) {
        memoryRemove(player, true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        quit(playerQuitEvent.getPlayer());
    }

    public void clearMem() {
        charges.clear();
        groundCounts.clear();
        cooldown.clear();
        smoother.clear();
        veccs.clear();
    }

}