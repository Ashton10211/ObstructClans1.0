package de.zerakles.clanapi.classes.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class KitSelector implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent){

    }

    public void openClassShop(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9, "§a§lClass Shop");
        ArrayList<String>lore = new ArrayList<>();
        //0; 1; 2 = Mage;  3=Assassin; 4=Ranger; 5=Knight; 6=Bruce; 7 8
        ItemStack mage = new ItemStack(Material.GOLD_HELMET);
        ItemMeta mageMeta = mage.getItemMeta();
        mageMeta.setDisplayName("§a§lMage");
        lore.add("");
        lore.add("§fMages are known for powerful spells");
        lore.add("§fand can fill most roles!");
        lore.add("");
        lore.add("§eGlacialPrison");
        lore.add("§eDefensiveAura");
        lore.add("§eHolyLight");
        lore.add("§eArcticArmor");
        mageMeta.setLore(lore);
        lore.clear();
        mage.setItemMeta(mageMeta);
        inventory.setItem(2, mage);
        ItemStack assassin = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta assassinMeta = assassin.getItemMeta();
        assassinMeta.setDisplayName("§a§lAssassin");
        lore.add("");
        lore.add("§fAssassins can quickly drop foes");
        lore.add("§fwith powerful combinations!");
        lore.add("");
        lore.add("§eLeap");
        lore.add("§eSilencingArrow");
        lore.add("§eRepeatedStrikes");
        lore.add("§eSmokeBomb");
        assassinMeta.setLore(lore);
        lore.clear();
        assassin.setItemMeta(assassinMeta);
        inventory.setItem(3, mage);
        ItemStack ranger = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta rangerMeta = assassin.getItemMeta();
        rangerMeta.setDisplayName("§a§lRanger");
        lore.add("");
        lore.add("§fRangers are masters of the bow,");
        lore.add("§fsniping foes from afar!");
        lore.add("");
        lore.add("§eDisengage");
        lore.add("§eWolfsFury");
        lore.add("§eRopedArrow");
        lore.add("§eVitalitySpores");
        lore.add("§eLongshot");
        rangerMeta.setLore(lore);
        lore.clear();
        ranger.setItemMeta(rangerMeta);
        inventory.setItem(4, ranger);
        ItemStack knight = new ItemStack(Material.IRON_HELMET);
        ItemMeta knightMeta = knight.getItemMeta();
        knightMeta.setDisplayName("§a§lKnight");
        lore.add("");
        lore.add("§fKnights are sturdy fighters,");
        lore.add("§fbuilt to tank damage!");
        lore.add("");
        lore.add("§eRiposte");
        lore.add("§eBullsCharge");
        lore.add("§eThorns");
        lore.add("§eSwordsmanship");
        knightMeta.setLore(lore);
        lore.clear();
        knight.setItemMeta(knightMeta);
        inventory.setItem(5, knight);
        ItemStack brute = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta bruteMeta = brute.getItemMeta();
        bruteMeta.setDisplayName("§a§lBrute");
        lore.add("");
        lore.add("§fBrutes control large crowds");
        lore.add("§fof enemies with their unique abilities!");
        lore.add("");
        lore.add("§eBattleTaunt");
        lore.add("§eSeismicSlam");
        lore.add("§eResistance");
        lore.add("§eStampede");
        bruteMeta.setLore(lore);
        lore.clear();
        brute.setItemMeta(bruteMeta);
        inventory.setItem(6, brute);
        player.openInventory(inventory);
    }

    public boolean checkItemStack(ItemStack itemStack){
        if(itemStack.getType() == Material.ENCHANTMENT_TABLE){
            if (itemStack.hasItemMeta()){
                if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§a§lClass Shop")){
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

}
