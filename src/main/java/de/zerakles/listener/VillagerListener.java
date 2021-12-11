package de.zerakles.listener;


import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class VillagerListener implements Listener {

    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }

    private Clan getClan(){
        return Clan.getClan();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractAtEntityEvent entityInteractEvent){
        if(entityInteractEvent.getRightClicked() instanceof Villager){
            entityInteractEvent.setCancelled(true);
            Villager villager = (Villager) entityInteractEvent.getRightClicked();
            Player player = entityInteractEvent.getPlayer();
            if(villager.isCustomNameVisible()){
                if(villager.getCustomName().equalsIgnoreCase("§c§lBank Shop")){
                    entityInteractEvent.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openBankShop(player);
                        }
                    },1);
                    return;
                }
                if(villager.getCustomName().equalsIgnoreCase("§a§lOrganic Produce")){
                    entityInteractEvent.setCancelled(true);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openOrganicProduce(player);
                        }
                    },1);
                    return;
                }
                if(villager.getCustomName().equalsIgnoreCase("§e§lMining Shop")){
                    entityInteractEvent.setCancelled(true);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openMiningShop(player);
                        }
                    },1);
                    return;
                }
                if(villager.getCustomName().equalsIgnoreCase("§6§lBuilding Supplies")){
                    entityInteractEvent.setCancelled(true);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openBuildingMaterials(player);
                        }
                    },1);
                    return;
                }
                if(villager.getCustomName().equalsIgnoreCase("§4§lPvp Gear")){
                    entityInteractEvent.setCancelled(true);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openPvpGear(player);
                        }
                    },1);
                    return;
                }
                if(villager.getCustomName().equalsIgnoreCase("§4§lQuest Manager")){
                    entityInteractEvent.setCancelled(true);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openQuestManager(player);
                        }
                    },1);
                    return;
                }
                if(villager.getCustomName().equalsIgnoreCase("§b§lTravel")){
                    entityInteractEvent.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getClan(), new Runnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            getClanAPI().openTravelInv(player);
                        }
                    },1);
                    return;
                }
            }
        }
    }

}
