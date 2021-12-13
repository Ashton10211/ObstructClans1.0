package de.zerakles.clanapi.classes;

import de.zerakles.clanapi.classes.listener.KitSelector;
import de.zerakles.clanapi.classes.listener.MageListener;
import de.zerakles.main.Clan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    public Manager(){
        loadListener();
    }

    private ArrayList<Player>inKit = new ArrayList<>();
    private HashMap<Player, String> kit = new HashMap<>();

    public String getKit(Player player){
        return kit.get(player);
    }

    public boolean hasKit(Player player){
        return inKit.contains(player);
    }

    private void removeKit(Player player){
        kit.remove(player);
    }

    private boolean kitContains(Player player){
        return kit.containsKey(player);
    }

    private void addKit(Player player, String kit){
        this.kit.put(player, kit);
    }

    public void registerKit(Player player, String kit){
        if(kitContains(player)){
            removeKit(player);
            addKit(player,kit);
        }
        addKit(player, kit);
    }

    public void loadListener(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new KitSelector(), Clan.getClan());
        pluginManager.registerEvents(new MageListener(), Clan.getClan());
    }

    public boolean fullKit(Player player, String kit){
        if(kit.equalsIgnoreCase("Mage")){
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
