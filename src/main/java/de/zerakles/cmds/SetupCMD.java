package de.zerakles.cmds;

import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

public class SetupCMD implements CommandExecutor {
    private Data getData(){
        return getClan().data;
    }
    private Clan getClan(){
        return Clan.getClan();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("clan.setup")){
                if(args.length == 0){
                    sendHelp(player);
                    return true;
                }
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("bank")){
                        createBank(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("organic")){
                        createOrganic(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("mining")){
                        createMining(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("building")){
                        createBuilding(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("pvp")){
                        createPvp(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("travel")){
                        createTravel(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("quest")){
                        createQuest(player);
                        return true;
                    }

                    if(args[0].equalsIgnoreCase("save")){
                        saveVillagers(player);
                        return true;
                    }
                    sendHelp(player);
                    return true;
                }
            }
        }
        return false;
    }

    private void saveVillagers(Player player) {
        if(getData().suppliesBoy != null && getData().organicBoy != null &&
                getData().bankBoy != null && getData().pvpBoy != null &&
                getData().miningBoy != null && getData().TravelBoys.size() > 0){
            getClan().villagerLoader.createConfig();
            player.sendMessage(getData().prefix + "§aVillager saved!");
            return;
        }
        player.sendMessage(getData().prefix + "§cSet all Villagers first!");
        return;
    }

    private void createBank(Player player) {
        if(getData().bankBoy != null){
            getData().bankBoy.damage(40);
            Location location = player.getLocation();
            getData().BankShop = location;
            getData().bankBoy = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
            getData().bankBoy.setAdult();
            getData().bankBoy.setCustomName("§c§lBank Shop");
            getData().bankBoy.setCustomNameVisible(true);
            return;
        }
        Location location = player.getLocation();
        getData().BankShop = location;
        Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
        villager.setAdult();
        villager.setCustomName("§c§lBank Shop");
        villager.setCustomNameVisible(true);
        getData().bankBoy = villager;
    }
    private void createOrganic(Player player) {
        if(getData().organicBoy != null){
            getData().organicBoy.damage(40);
            Location location = player.getLocation();
            getData().OrganicProduce = location;
            getData().organicBoy = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
            getData().organicBoy.setAdult();
            getData().organicBoy.setCustomName("§a§lOrganic Produce");
            getData().organicBoy.setCustomNameVisible(true);
            return;
        }
        Location location = player.getLocation();
        getData().OrganicProduce = location;
        Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
        villager.setAdult();
        villager.setCustomName("§a§lOrganic Produce");
        villager.setCustomNameVisible(true);
        getData().organicBoy = villager;
    }
    private void createMining(Player player) {
        if(getData().miningBoy != null){
            getData().miningBoy.damage(40);
            Location location = player.getLocation();
            getData().MiningShop = location;
            getData().miningBoy = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
            getData().miningBoy.setAdult();
            getData().miningBoy.setCustomName("§e§lMining Shop");
            getData().miningBoy.setCustomNameVisible(true);
            return;
        }
        Location location = player.getLocation();
        getData().MiningShop = location;
        Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
        villager.setAdult();
        villager.setCustomName("§e§lMining Shop");
        villager.setCustomNameVisible(true);
        getData().miningBoy = villager;
    }
    private void createBuilding(Player player) {
        if(getData().suppliesBoy != null){
            getData().suppliesBoy.damage(40);
            Location location = player.getLocation();
            getData().BuildingSupplies = location;
            getData().suppliesBoy = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
            getData().suppliesBoy.setAdult();
            getData().suppliesBoy.setCustomName("§6§lBuilding Supplies");
            getData().suppliesBoy.setCustomNameVisible(true);
            return;
        }
        Location location = player.getLocation();
        getData().BuildingSupplies = location;
        Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
        villager.setAdult();
        villager.setCustomName("§6§lBuilding Supplies");
        villager.setCustomNameVisible(true);
        getData().suppliesBoy = villager;
    }
    private void createPvp(Player player) {
        if(getData().pvpBoy != null){
            getData().pvpBoy.damage(40);
            Location location = player.getLocation();
            getData().PvpGear = location;
            getData().pvpBoy = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
            getData().pvpBoy.setAdult();
            getData().pvpBoy.setCustomName("§4§lPvp Gear");
            getData().pvpBoy.setCustomNameVisible(true);
            return;
        }
        Location location = player.getLocation();
        getData().PvpGear = location;
        Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
        villager.setAdult();
        villager.setCustomName("§4§lPvp Gear");
        villager.setCustomNameVisible(true);
        getData().pvpBoy = villager;
    }

    private void createQuest(Player player) {
        if(getData().questBoy != null){
            getData().questBoy.damage(40);
            Location location = player.getLocation();
            getData().QuestManager = location;
            getData().questBoy = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
            getData().questBoy.setAdult();
            getData().questBoy.setCustomName("§4§lQuest Manager");
            getData().questBoy.setCustomNameVisible(true);
            return;
        }
        Location location = player.getLocation();
        getData().QuestManager = location;
        Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
        villager.setAdult();
        villager.setCustomName("§4§lQuest Manager");
        villager.setCustomNameVisible(true);
        getData().questBoy = villager;
    }


    private void createTravel(Player player) {
       Villager villager = Bukkit.getWorld(player.getLocation().getWorld().getName()).spawn(player.getLocation(), Villager.class);
       villager.setCustomName("§b§lTravel");
       villager.setCustomNameVisible(true);
       villager.setCanPickupItems(false);
       villager.setAdult();
       getData().TravelBoys.add(villager);
       getData().Travel.add(player.getLocation());
    }

    private void sendHelp(Player player) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(getData().prefix + "§e/setup bank");
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/setup bank"));
        player.spigot().sendMessage(textComponent);
        TextComponent textComponent1 = new TextComponent();
        textComponent1.setText(getData().prefix + "§e/setup organic");
        textComponent1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/setup organic"));
        player.spigot().sendMessage(textComponent1);
        TextComponent textComponent2 = new TextComponent();
        textComponent2.setText(getData().prefix + "§e/setup mining");
        textComponent2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/setup mining"));
        player.spigot().sendMessage(textComponent2);
        TextComponent textComponent3 = new TextComponent();
        textComponent3.setText(getData().prefix + "§e/setup building");
        textComponent3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/setup building"));
        player.spigot().sendMessage(textComponent3);
        TextComponent textComponent4 = new TextComponent();
        textComponent4.setText(getData().prefix + "§e/setup pvp");
        textComponent4.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/setup pvp"));
        player.spigot().sendMessage(textComponent4);
        TextComponent textComponent5 = new TextComponent();
        textComponent5.setText(getData().prefix + "§e/setup travel");
        textComponent5.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/setup travel"));
        player.spigot().sendMessage(textComponent5);
        return;
    }
}
