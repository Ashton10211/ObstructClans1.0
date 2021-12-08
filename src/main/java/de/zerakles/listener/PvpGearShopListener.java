package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PvpGearShopListener implements Listener {
    private Data getData(){
        return Clan.getClan().data;
    }

    private  Clan getClan(){
        return Clan.getClan();
    }

    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    @EventHandler
    public void onBankShop(InventoryClickEvent inventoryClickEvent)throws NullPointerException {
        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
            if (inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§8Pvp Gear")) {
                inventoryClickEvent.setCancelled(true);
                Player player = (Player) inventoryClickEvent.getWhoClicked();
                if (inventoryClickEvent.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getType() == null) {
                    return;
                }
                if (inventoryClickEvent.getCurrentItem() == null
                        || inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() == null
                        || inventoryClickEvent.getCurrentItem().getItemMeta() == null) {
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Helmet")) {
                    int cost = 2500;
                    int earn = 500;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_HELMET, 1, "§a§lMage Helmet"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_HELMET, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_HELMET, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Helmet")) {
                    int cost = 2500;
                    int earn = 500;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.LEATHER_HELMET, 1, "§a§lAssassin Helmet"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.LEATHER_HELMET, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LEATHER_HELMET, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Helmet")) {
                    int cost = 2500;
                    int earn = 500;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.CHAINMAIL_HELMET, 1, "§a§lRanger Helmet"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.CHAINMAIL_HELMET, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.CHAINMAIL_HELMET, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Helmet")) {
                    int cost = 2500;
                    int earn = 500;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_HELMET, 1, "§a§lKnight Helmet"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_HELMET, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_HELMET, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Helmet")) {
                    int cost = 2500;
                    int earn = 500;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND_HELMET, 1, "§a§lBrute Helmet"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND_HELMET, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND_HELMET, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Chestplate")) {
                    int cost = 4000;
                    int earn = 800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND_CHESTPLATE, 1, "§a§lBrute Chestplate"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND_CHESTPLATE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND_CHESTPLATE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Chestplate")) {
                    int cost = 4000;
                    int earn = 800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_CHESTPLATE, 1, "§a§lMage Chestplate"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_CHESTPLATE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_CHESTPLATE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Chestplate")) {
                    int cost = 4000;
                    int earn = 800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.LEATHER_CHESTPLATE, 1, "§a§lAssassin Chestplate"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.LEATHER_CHESTPLATE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LEATHER_CHESTPLATE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Chestplate")) {
                    int cost = 4000;
                    int earn = 800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.CHAINMAIL_CHESTPLATE, 1, "§a§lRanger Chestplate"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.CHAINMAIL_CHESTPLATE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.CHAINMAIL_CHESTPLATE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Chestplate")) {
                    int cost = 4000;
                    int earn = 800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_CHESTPLATE, 1, "§a§lKnight Chestplate"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_CHESTPLATE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_CHESTPLATE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Leggins")) {
                    int cost = 3500;
                    int earn = 700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_LEGGINGS, 1, "§a§lMage Leggins"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_LEGGINGS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_LEGGINGS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Leggins")) {
                    int cost = 3500;
                    int earn = 700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.LEATHER_LEGGINGS, 1, "§a§lAssassin Leggins"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.LEATHER_LEGGINGS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LEATHER_LEGGINGS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Leggins")) {
                    int cost = 3500;
                    int earn = 700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.CHAINMAIL_LEGGINGS, 1, "§a§lRanger Leggins"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.CHAINMAIL_LEGGINGS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.CHAINMAIL_LEGGINGS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Leggins")) {
                    int cost = 3500;
                    int earn = 700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_LEGGINGS, 1, "§a§lKnight Leggins"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_LEGGINGS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_LEGGINGS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Leggins")) {
                    int cost = 3500;
                    int earn = 700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND_LEGGINGS, 1, "§a§lBrute Leggins"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND_LEGGINGS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND_LEGGINGS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMage Boots")) {
                    int cost = 2000;
                    int earn = 400;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_BOOTS, 1, "§a§lMage Boots"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_BOOTS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_BOOTS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAssassin Boots")) {
                    int cost = 2000;
                    int earn = 400;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.LEATHER_BOOTS, 1, "§a§lAssassin Boots"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.LEATHER_BOOTS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LEATHER_BOOTS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRanger Boots")) {
                    int cost = 2000;
                    int earn = 400;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.CHAINMAIL_BOOTS, 1, "§a§lRanger Boots"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.CHAINMAIL_BOOTS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.CHAINMAIL_BOOTS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lKnight Boots")) {
                    int cost = 2000;
                    int earn = 400;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_BOOTS, 1, "§a§lKnight Boots"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_BOOTS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_BOOTS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrute Boots")) {
                    int cost = 2000;
                    int earn = 400;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND_BOOTS, 1, "§a§lBrute Boots"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND_BOOTS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND_BOOTS, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lIron Sword")) {
                    int cost = 1000;
                    int earn = 200;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_SWORD, 1, "§a§lIron Sword"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_SWORD, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_SWORD, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lPower Sword")) {
                    int cost = 9000;
                    int earn = 1800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND_SWORD, 1, "§a§lPower Sword"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND_SWORD, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND_SWORD, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBooster Sword")) {
                    int cost = 9000;
                    int earn = 1800;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_SWORD, 1, "§a§lBooster Sword"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_SWORD, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_SWORD, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lIron Axe")) {
                    int cost = 1500;
                    int earn = 300;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_AXE, 1, "§a§lIron Axe"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_AXE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_AXE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lPower Axe")) {
                    int cost = 13500;
                    int earn = 2700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND_AXE, 1, "§a§lPower Axe"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND_AXE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND_AXE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBooster Axe")) {
                    int cost = 13500;
                    int earn = 2700;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_AXE, 1, "§a§lBooster Axe"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_AXE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_AXE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lStandard Bow")) {
                    int cost = 175;
                    int earn = 35;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.BOW, 1, "§a§lStandard Bow"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.BOW, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.BOW, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lArrows")) {
                    int cost = 20;
                    int earn = 2;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.ARROW, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.ARROW, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.ARROW, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.ARROW, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lStandard Mount")) {
                    int cost = 150000;
                    int earn = 300;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_BARDING, 1, "§a§lStandard Mount"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_BARDING, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_BARDING, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lClass Shop")) {
                    int cost = 30000;
                    int earn = 0;
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.ENCHANTMENT_TABLE, 1, "§a§lClass Shop"), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.ENCHANTMENT_TABLE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.ENCHANTMENT_TABLE, 1), player, earn);
                    return;
                }
            }
        }
    }
    public void buyItem(ItemStack itemStack, Player player, int amount){
        if(getClanAPI().getGold(player)>amount || getClanAPI().getGold(player) == amount){
            getClanAPI().updateGold(player, getClanAPI().getGold(player)-amount);
            ArrayList<String> lore =new ArrayList<>();
            itemStack.getItemMeta().setLore(lore);
            player.getInventory().addItem(itemStack);
            player.sendMessage(getData().prefix + getData().itemClaimed);
            return;
        } else {
            player.sendMessage(getData().prefix + getData().dontHaveEnoughGold);
        }
    }

    public void sellItem(ItemStack itemStack, Player player, int earn){
        for (ItemStack item : player.getInventory().getContents()){
            if(item == null){
                continue;
            }
            if(item.getType() == itemStack.getType()){
                int a = item.getAmount();
                if(a>1){
                    a=a-1;
                    item.setAmount(a);
                    getClanAPI().updateGold(player, getClanAPI().getGold(player)+earn);
                    player.sendMessage(getData().prefix + getData().addGold.replaceAll("%gold%", earn +""));
                    return;
                }
                player.getInventory().remove(item);
                getClanAPI().updateGold(player, getClanAPI().getGold(player)+earn);
                player.sendMessage(getData().prefix + getData().addGold.replaceAll("%gold%", earn + ""));
                return;
            }
        }
    }

    public void sellAllItems(ItemStack itemStack, Player player, int earn){
        for (ItemStack item : player.getInventory().getContents()){
            if(item == null){
                continue;
            }
            if(item.getType() == itemStack.getType()){
                int a = item.getAmount();
                player.getInventory().remove(item);
                getClanAPI().updateGold(player, getClanAPI().getGold(player)+a*earn);
                player.sendMessage(getData().prefix + getData().addGold.replaceAll("%gold%", a*earn + ""));
                return;
            }
        }
    }

    public ItemStack getItemStack(Material material, int amount){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);
        return itemStack;
    }

    public ItemStack getItemStack(Material material, int amount, short durability){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability(durability);
        itemStack.setAmount(amount);
        return itemStack;
    }

    public ItemStack getItemStack(Material material, int amount, String displayName){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);
        return itemStack;
    }


    public ItemStack getItemStack(Material material, int amount, short durability, String displayName){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability(durability);
        itemStack.setAmount(amount);
        return itemStack;
    }
}
