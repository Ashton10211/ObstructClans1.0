package de.zerakles.clanapi.legendaries.magneticblade;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Display;
import de.zerakles.utils.Utils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MagneticMaulListener implements Listener {
    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    private HashMap<String, Float> charges = new HashMap<>();

    private HashMap<String, Float> smoother = new HashMap<>();

    private HashMap<Legend, Integer> cd = new HashMap<>();

    public HashMap<Legend, Player> MagneticMauls = new HashMap<>();

    public int getDamage(){
        if(MagneticMauls.size() > 0){
            for (Legend legend:MagneticMauls.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: MagneticMauls.keySet()
        ) {
            if(!itemStack.getItemMeta().hasLore()){
                return false;
            }
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: MagneticMauls.keySet()
        ) {
            if(MagneticMauls.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
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
        if(cd != 0){
            for(int s = 0; s<cd; s++){
                green = green + "▌ ";
            }
        }
        int c = 10-cd;
        for(int s = 0; s<c; s++){
            red = red + "▌ ";
        }
        return green + red;
    }

    public  Legend legend(ItemStack itemStack){
        for (Legend legend:MagneticMauls.keySet()
        ) {
            if(legend.getItemStack().equals(itemStack)){
                return legend;
            }
        }
        return null;
    }

    public MagneticMaulListener(){
        loop();
    }

    public void loop(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    ItemStack item = Utils.getItemInHand(p);
                    if(!haveLegendary(p)){
                        return;
                    }
                    if (!isCorrectItem(item, p))
                        charges.remove(p.getName());
                    try {
                        if (smoother.get(p.getName()) > 0.6F)
                            for (Entity e : p.getNearbyEntities(7.0D, 7.0D, 7.0D)) {
                                if (e == p)
                                    continue;
                                if (e.isDead())
                                    continue;
                                if (!(e instanceof LivingEntity))
                                    continue;
                                if (e instanceof Player) {
                                    Player pl = (Player)e;
                                    if (!p.canSee(pl))
                                        continue;
                                }
                                if (getLookingAt(p, e)) {
                                    Vector vec = p.getLocation().toVector().subtract(e.getLocation().toVector()).normalize();
                                    Vector newVec = vec.multiply(0.7D);
                                    Vector useCharge = newVec.multiply(1.2D).multiply(Math.min(0.34D, Math.max(getCharge(p), 0.23D)));
                                    e.setVelocity(useCharge);
                                }
                            }
                    } catch (NullPointerException nullPointerException) {}
                    if (isCorrectItem(item,p)) {
                        onUpdate(p);
                        continue;
                    }
                    memoryRemove(p);
                }
            }
        },0,1);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityPunchEntity(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause().getCause() != null &&
                e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            e.getEntity().setLastDamageCause(new EntityDamageEvent(e.getDamager(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, e.getDamage()));
            return;
        }
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            ItemStack item = Utils.getItemInHand(p);
            if (isCorrectItem(item,p))
                if (e.getEntity() instanceof LivingEntity) {
                    if (e.getEntity() instanceof Player && ((
                            (Player)e.getEntity()).getGameMode() == GameMode.CREATIVE || (
                            (Player)e.getEntity()).getGameMode() == GameMode.SPECTATOR))
                        return;
                    e.setCancelled(true);
                    LivingEntity l = (LivingEntity)e.getEntity();
                    l.setLastDamageCause(new EntityDamageEvent((Entity)p, EntityDamageEvent.DamageCause.SUICIDE, getDamage()));
                    l.damage(getDamage(), (Entity)p);
                    Vector vec = p.getLocation().toVector()
                            .subtract(e.getEntity().getLocation().toVector()).normalize().add(new Vector(0.0D, 0.4D, 0.0D)).multiply(0.4D);
                    e.getEntity().setVelocity(vec);
                }
        }
    }

    public void onUpdate(Player p) {
        if (!charges.containsKey(p.getName()) && isCorrectItem(Utils.getItemInHand(p),p))
            charges.put(p.getName(), 0.0F);
        if (!smoother.containsKey(p.getName()) && isCorrectItem(Utils.getItemInHand(p),p))
            smoother.put(p.getName(), 0.0F);
        if ((Float) smoother.get(p.getName()) == 0.0F || (Float) charges.get(p.getName()) <= 0.13D) {
            Charge(p);
        } else if ((Float) smoother.get(p.getName()) != 0.0F) {
            charges.put(p.getName(),
                    (float) Math.max(0.0D, getCharge(p) - 0.017D));
        }
        if (isCorrectItem(Utils.getItemInHand(p),p)) {
            smoother.put(p.getName(), (float) Math.max(0.0D, (Float) smoother.get(p.getName()) - 0.5D));
            float cdF = getCharge(p)*10;
            int cd = (int) cdF;
            String cdS = barCooldown(cd);
            showActionbar(p, cdS);
        }
        for (Legend legend : MagneticMauls.keySet()){
            if(lastTick.containsKey(legend)){
                if(lastTick.get(legend)+1000< System.currentTimeMillis()){
                    if(cd.get(legend)!= 0){
                        cd.replace(legend, cd.get(legend)-1);
                        return;
                    }
                }
            }
        }
    }

    public HashMap<Legend, Long> lastTick = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR ||
                e.getAction() == Action.RIGHT_CLICK_BLOCK)
            if (isCorrectItem(Utils.getItemInHand(e.getPlayer()), e.getPlayer())) {
                List<Entity> tempList = new ArrayList<>();
                for (Entity ent : e.getPlayer().getNearbyEntities(7.0D, 7.0D, 7.0D)) {
                    if (!(ent instanceof LivingEntity))
                        continue;
                    Legend legend = legend(e.getPlayer().getItemInHand());
                    if(cd.containsKey(legend)){
                        if(cd.get(legend) == 10){
                            e.getPlayer().sendMessage(getData().prefix + "§7This §aMagneticMaul have to recharge!");
                            return;
                        }
                        if(lastTick.get(legend) + 1000L < System.currentTimeMillis()){
                            cd.replace(legend, cd.get(legend)+1);
                            lastTick.remove(legend);
                            lastTick.put(legend, System.currentTimeMillis());
                        }
                    } else {
                        lastTick.put(legend, System.currentTimeMillis());
                        cd.put(legend, 0);
                    }
                    if (ent.isDead())
                        continue;
                    if (getLookingAt(e.getPlayer(), ent)) {
                        Vector vec = e.getPlayer().getLocation().toVector().subtract(ent.getLocation().toVector())
                                .normalize();
                        Vector newVec = vec.multiply(0.7D);
                        Vector useCharge = newVec.multiply(1.2D).multiply(Math.min(0.15D, getCharge(e.getPlayer())));
                        ent.setVelocity(useCharge);
                        tempList.add(ent);
                    }
                }
                smoother.put(e.getPlayer().getName(), Math.min(5.0F, (Float) smoother.get(e.getPlayer().getName()) + 2.0F));
            }
    }

    public boolean Charge(Player player) {
        if (!charges.containsKey(player.getName()))
            charges.put(player.getName(), 0.0F);
        float charge = (Float) charges.get(player.getName());
        charge = (float)Math.min(1.0D, charge + 0.013D);
        charges.put(player.getName(), charge);
        charge = charge *10;
        int cd = (int) charge;
        String cdS = barCooldown(cd);
        showActionbar(player,cdS);
        return (charge >= 1.0F);
    }

    public float getCharge(Player player) {
        if (!charges.containsKey(player.getName()))
            return 0.0F;
        return (Float) charges.get(player.getName());
    }

    private boolean getLookingAt(Player player, Entity ent) {
        Location eye = player.getLocation();
        Vector toEntity = ent.getLocation().toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());
        return (dot > 0.7D);
    }

    private void memoryRemove(Player p) {
        if (smoother.containsKey(p.getName()))
            smoother.remove(p.getName());
    }

    @EventHandler
    public void quitE(PlayerQuitEvent playerQuitEvent){
        quit(playerQuitEvent.getPlayer());
    }
    public void rel() {}

    public void quit(Player player) {
        charges.remove(player.getName());
        smoother.remove(player.getName());
    }

    public void clearMem() {
        charges.clear();
        smoother.clear();
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
