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

public class SupplieShopListener implements Listener {
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
    public void onBankShop(InventoryClickEvent inventoryClickEvent)throws NullPointerException{
        if(inventoryClickEvent.getWhoClicked() instanceof Player){
            if(inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§8Building Supplies")){
                inventoryClickEvent.setCancelled(true);
                Player player = (Player) inventoryClickEvent.getWhoClicked();
                if(inventoryClickEvent.getCurrentItem().getType() == Material.AIR){
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getType() == null){
                    return;
                }
                if(inventoryClickEvent.getCurrentItem() == null
                        || inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() == null
                        || inventoryClickEvent.getCurrentItem().getItemMeta() == null){
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lStone")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.STONE, 64, (short)0, "§fStone"), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.STONE, 1, (short)0, "§fStone"), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.STONE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.STONE, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lSmooth Brick")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.SMOOTH_BRICK, 64, (short)0, "§fSmooth Brick"), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.SMOOTH_BRICK, 1, (short)0, "§fSmooth Brick"), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.SMOOTH_BRICK, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.SMOOTH_BRICK, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCobblestone")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.COBBLESTONE, 64, (short)0, "§fCobblestone"), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.COBBLESTONE, 1, (short)0, "§fCobblestone"), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.COBBLESTONE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.COBBLESTONE, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLog1")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.LOG, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.LOG, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.LOG, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LOG, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLog2")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.LOG, 64, (short) 1), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.LOG, 1, (short) 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.LOG, 1, (short) 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LOG, 1, (short) 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLog3")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.LOG, 64, (short) 2), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.LOG, 1, (short) 2), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.LOG, 1, (short) 2), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LOG, 1, (short) 2), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLog4")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.LOG, 64, (short) 3), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.LOG, 1, (short) 3), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.LOG, 1, (short) 3), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LOG, 1, (short) 3), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLog5")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.LOG_2, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.LOG_2, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.LOG_2, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LOG_2, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lLog6")){
                    int cost = 100;
                    int earn = 20;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.LOG_2, 64, (short) 1), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.LOG_2, 1, (short) 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.LOG_2, 1, (short) 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.LOG_2, 1, (short) 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lSand")){
                    int cost = 20;
                    int earn = 4;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.SAND, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.SAND, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.SAND, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.SAND, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lGlass")){
                    int cost = 30;
                    int earn = 6;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.GLASS, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.GLASS, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.GLASS, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.GLASS, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lSandstone")){
                    int cost = 80;
                    int earn = 16;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.SANDSTONE, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.SANDSTONE, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.SANDSTONE, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.SANDSTONE, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lDirt")){
                    int cost = 10;
                    int earn = 2;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.DIRT, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.DIRT, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.DIRT, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.DIRT, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lNether Brick")){
                    int cost = 50;
                    int earn = 10;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.NETHER_BRICK, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.NETHER_BRICK, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.NETHER_BRICK, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.NETHER_BRICK, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lQuartz Block")){
                    int cost = 75;
                    int earn = 15;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.QUARTZ_BLOCK, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.QUARTZ_BLOCK, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.QUARTZ_BLOCK, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.QUARTZ_BLOCK, 1), player, earn);
                    return;
                }
                if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lClay")){
                    int cost = 30;
                    int earn = 6;
                    if(inventoryClickEvent.getClick().isLeftClick() && inventoryClickEvent.getClick().isShiftClick()){
                        int newCost = cost*64;
                        buyItem(getItemStack(Material.CLAY, 64), player, newCost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        buyItem(getItemStack(Material.CLAY, 1), player, cost);
                        return;
                    }
                    if(inventoryClickEvent.getClick().isRightClick() && inventoryClickEvent.getClick().isShiftClick()){
                        sellAllItems(getItemStack(Material.CLAY, 1), player, earn);
                        return;
                    }
                    sellItem(getItemStack(Material.CLAY, 1), player, earn);
                    return;
                }
                return;
            }
            return;
        }
        return;
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
