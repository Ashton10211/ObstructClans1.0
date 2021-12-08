package de.zerakles.cmds;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanChat implements CommandExecutor {
    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }
    private Data getData(){
        return Clan.getClan().data;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(getClanAPI().playerExists(((Player) sender).getUniqueId().toString())){
                Player player = ((Player) sender).getPlayer();
                if(getData().inClanChat.contains(player)){
                    getData().inClanChat.remove(player);
                    if(getData().inAllyChat.contains(player)){
                        getData().inAllyChat.remove(player);
                    }
                    player.sendMessage(getData().prefix + getData().talkingInAllChat);
                } else {
                    getData().inClanChat.add(player);
                    if(getData().inAllyChat.contains(player)){
                        getData().inAllyChat.remove(player);
                    }
                    player.sendMessage(getData().prefix + getData().talkingInClanChat);
                }
                return true;
            } else {
                sender.sendMessage(getData().prefix + getData().dontHaveAClan);
                return true;
            }
        }
        return false;
    }
}
