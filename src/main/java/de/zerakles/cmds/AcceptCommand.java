package de.zerakles.cmds;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand implements CommandExecutor {
    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }
    private Data getData(){
        return Clan.getClan().data;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length == 1){
                Player player = (Player)sender;
                if(getClanAPI().playerExists(player.getUniqueId().toString())){
                    if(getData().toAlly.containsKey(getClanAPI().getClan(player.getUniqueId().toString()))){
                        String Ally = getData().toAlly.get(getClanAPI().getClan(player.getUniqueId().toString()));
                        getData().toAlly.remove(getClanAPI().getClan(player.getUniqueId().toString()));
                        getData().allyClans.put(Ally, getClanAPI().getClan(player.getUniqueId().toString()));
                        getData().allyClans.put(getClanAPI().getClan(player.getUniqueId().toString()), Ally);
                        Bukkit.broadcastMessage(getData().prefix + getData().nowAllys
                                .replaceAll("%clan%", getClanAPI().getClan(player.getUniqueId().toString()))
                                .replaceAll("%allyclan%", Ally));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
