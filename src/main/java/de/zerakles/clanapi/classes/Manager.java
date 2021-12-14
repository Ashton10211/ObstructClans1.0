package de.zerakles.clanapi.classes;

import de.zerakles.clanapi.classes.effects.EffectManager;
import de.zerakles.clanapi.classes.listener.KitSelector;
import de.zerakles.clanapi.classes.listener.MageListener;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    public EffectManager effectManager;
    public MageListener mageListener;

    public String prefix = ChatColor.DARK_BLUE + "Class>";

    public Manager(){
        effectManager = new EffectManager();
        mageListener = new MageListener();
        mageListener.loop();
        loadListener();
        loop();
    }

    public ArrayList<Player>inKit = new ArrayList<>();
    public HashMap<Player, String> kit = new HashMap<>();

    private Data getData(){
        return Clan.getClan().data;
    }

    public String getKit(Player player){
        return kit.get(player);
    }

    public boolean hasKit(Player player){
        return inKit.contains(player);
    }

    private void removeKit(Player player){
        kit.remove(player); inKit.remove(player);
    }

    public boolean kitContains(Player player){
        return kit.containsKey(player);
    }

    public void addKit(Player player, String kitS){
        kit.put(player, kitS);
        inKit.add(player);
    }

    public void loop(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Clan.getClan(), new Runnable() {
            @Override
            public void run() {
                for (Player player:inKit
                     ) {
                    String kit = getKit(player);
                    if(!fullKit(player, kit)){
                        player.sendMessage(prefix + "§7You don't have all items of your kit equipped.");
                        player.sendMessage(prefix + "§7Kit was §cremoved§7!");
                        removeKit(player);
                    }
                }
            }
        },0,20);
    }

    public void registerKit(Player player, String kit){
        if(!fullKit(player, kit)){
            player.sendMessage(prefix + "§7You need to equip a full §a" + kit.toUpperCase() + " §7Kit.");
            return;
        }
        if(kitContains(player)){
            removeKit(player);
            addKit(player,kit);
        }
        addKit(player, kit);
        player.sendMessage(prefix + "§7Kit was equipped!");
    }

    public void loadListener(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new KitSelector(), Clan.getClan());
        pluginManager.registerEvents(mageListener, Clan.getClan());
    }

    public boolean fullKit(Player player, String kit){
        if(kit.equalsIgnoreCase("Mage")){
            if(player.getInventory().getHelmet()==null){
                return false;
            }
            if(player.getInventory().getChestplate()==null){
                return false;
            }
            if(player.getInventory().getLeggings()==null){
                return false;
            }
            if(player.getInventory().getBoots()==null){
                return false;
            }
            if((player.getInventory().getHelmet().getType() == Material.GOLD_HELMET) &&
                    (player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Helmet"))
                            && (player.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE)
                            && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Chestplate"))
                            && (player.getInventory().getLeggings().getType() == Material.GOLD_LEGGINGS)
                            && (player.getInventory().getLeggings().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Leggins"))
                            && (player.getInventory().getBoots().getType() == Material.GOLD_BOOTS)
                            && (player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Boots"))){
                return true;
            }
            return false;
        }
        if(kit.equalsIgnoreCase("Assassin")){
            if((player.getInventory().getHelmet().getType() == Material.LEATHER_HELMET) &&
                    (player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Helmet"))
                    && (player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE)
                    && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Chestplate"))
                    && (player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS)
                    && (player.getInventory().getLeggings().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Leggins"))
                    && (player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS)
                    && (player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Boots"))){
                return true;
            }
            return false;
        }
        if(kit.equalsIgnoreCase("Ranger")){
            if((player.getInventory().getHelmet().getType() == Material.CHAINMAIL_HELMET) &&
                    (player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Helmet"))
                    && (player.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE)
                    && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Chestplate"))
                    && (player.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS)
                    && (player.getInventory().getLeggings().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Leggins"))
                    && (player.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS)
                    && (player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Boots"))){
                return true;
            }
            return false;
        }
        if(kit.equalsIgnoreCase("Knight")){
            if((player.getInventory().getHelmet().getType() == Material.IRON_HELMET) &&
                    (player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Helmet"))
                    && (player.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE)
                    && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Chestplate"))
                    && (player.getInventory().getLeggings().getType() == Material.IRON_CHESTPLATE)
                    && (player.getInventory().getLeggings().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Leggins"))
                    && (player.getInventory().getBoots().getType() == Material.IRON_BOOTS)
                    && (player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Boots"))){
                return true;
            }
            return false;
        }
        if(kit.equalsIgnoreCase("Brute")){
            if((player.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) &&
                    (player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Helmet"))
                    && (player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE)
                    && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Chestplate"))
                    && (player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS)
                    && (player.getInventory().getLeggings().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Leggins"))
                    && (player.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS)
                    && (player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Boots"))){
                return true;
            }
            return false;
        }
        return false;
    }
}
