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

public class OrganicShopListener implements Listener {

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
            if (inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§8Organic Produce")) {
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
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lPotato Item")) {
                    int cost = 15;
                    int earn = 8;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.STONE, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.STONE, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.STONE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.STONE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMelon")) {
                    int cost = 5;
                    int earn = 3;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.MELON_STEM, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.MELON_STEM, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.MELON_STEM, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.MELON_STEM, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBread")) {
                    int cost = 30;
                    int earn = 16;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.BREAD, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.BREAD, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.BREAD, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.BREAD, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCooked Beef")) {
                    int cost = 50;
                    int earn = 27;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.COOKED_BEEF, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.COOKED_BEEF, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.COOKED_BEEF, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.COOKED_BEEF, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lGrilled Pork")) {
                    int cost = 50;
                    int earn = 27;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.GRILLED_PORK, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GRILLED_PORK, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GRILLED_PORK, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GRILLED_PORK, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCooked Chicken")) {
                    int cost = 35;
                    int earn = 19;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.COOKED_CHICKEN, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.COOKED_CHICKEN, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.COOKED_CHICKEN, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.COOKED_CHICKEN, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lFeather")) {
                    int cost = 50;
                    int earn = 10;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.FEATHER, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.FEATHER, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.FEATHER, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.FEATHER, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCarrot Item")) {
                    int cost = 10;
                    int earn = 5;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.CARROT_ITEM, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.CARROT_ITEM, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.CARROT_ITEM, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.CARROT_ITEM, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMushroom Soup")) {
                    int cost = 200;
                    int earn = 109;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.MUSHROOM_SOUP, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.MUSHROOM_SOUP, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.MUSHROOM_SOUP, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.MUSHROOM_SOUP, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lSugar Cane")) {
                    int cost = 15;
                    int earn = 3;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.SUGAR_CANE, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.SUGAR_CANE, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.SUGAR_CANE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.SUGAR_CANE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lPumpkin")) {
                    int cost = 30;
                    int earn = 6;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.SUGAR_CANE, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.SUGAR_CANE, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.SUGAR_CANE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.SUGAR_CANE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lString")) {
                    int cost = 50;
                    int earn = 10;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.STRING, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.STRING, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.STRING, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.STRING, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRotten Flesh")) {
                    int cost = 5;
                    int earn = 5;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.ROTTEN_FLESH, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.ROTTEN_FLESH, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.ROTTEN_FLESH, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.ROTTEN_FLESH, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lSpider Eye")) {
                    int cost = 5;
                    int earn = 5;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.SPIDER_EYE, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.SPIDER_EYE, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.SPIDER_EYE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.SPIDER_EYE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lBrown Mushroom")) {
                    int cost = 75;
                    int earn = 1;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.BROWN_MUSHROOM, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.BROWN_MUSHROOM, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.BROWN_MUSHROOM, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.BROWN_MUSHROOM, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRed Mushroom")) {
                    int cost = 75;
                    int earn = 1;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.RED_MUSHROOM, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.RED_MUSHROOM, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.RED_MUSHROOM, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.RED_MUSHROOM, 1), player, earn);
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
