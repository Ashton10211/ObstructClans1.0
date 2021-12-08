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

public class MiningShopListener implements Listener {
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
            if (inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§8Mining Shop")) {
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
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lIron Ingot")) {
                    int cost = 500;
                    int earn = 100;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.IRON_INGOT, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.IRON_INGOT, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.IRON_INGOT, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.IRON_INGOT, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lGold Ingot")) {
                    int cost = 500;
                    int earn = 100;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.GOLD_INGOT, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.GOLD_INGOT, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.GOLD_INGOT, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GOLD_INGOT, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lDiamond")) {
                    int cost = 500;
                    int earn = 100;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.DIAMOND, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.DIAMOND, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.DIAMOND, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIAMOND, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLeather")) {
                    int cost = 500;
                    int earn = 100;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.LEATHER, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.LEATHER, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.LEATHER, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LEATHER, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCoal")) {
                    int cost = 50;
                    int earn = 10;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.COAL, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.COAL, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.COAL, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.COAL, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lRedstone")) {
                    int cost = 10;
                    int earn = 2;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.REDSTONE, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.REDSTONE, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.REDSTONE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.REDSTONE, 1), player, earn);
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLapis Block")) {
                    int cost = 500;
                    int earn = 100;
                    if (inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        int newCost = cost * 64;
                        buyItem(getItemStack(Material.LAPIS_BLOCK, 64), player, newCost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isLeftClick()) {
                        buyItem(getItemStack(Material.LAPIS_BLOCK, 1), player, cost);
                        return;
                    }
                    if (inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()) {
                        sellAllItems(getItemStack(Material.LAPIS_BLOCK, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LAPIS_BLOCK, 1), player, earn);
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
