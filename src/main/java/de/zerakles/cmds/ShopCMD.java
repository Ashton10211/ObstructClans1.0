package de.zerakles.cmds;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCMD implements CommandExecutor {
    private Data getData(){
        return Clan.getClan().data;
    }
    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                if(getData().Shop == null){
                    player.sendMessage(getData().prefix + "§cShop don't exist!");
                    return true;
                }
                player.sendMessage(ChatColor.BLUE + "Kasey> " + ChatColor.YELLOW + "I can't let you do that");
                return true;
            }
            if(args.length == 1){
                if(player.hasPermission("shop.setup")){
                    Location shop = player.getLocation();
                    getData().Shop = shop;
                    player.sendMessage(getData().prefix + "§aShop set!");
                    saveLoc(shop);
                    return true;
                }
            }
        }
        return false;
    }

    private void saveLoc(Location shop) {
        Clan.getClan().shop.createConfig(shop);
    }
}
