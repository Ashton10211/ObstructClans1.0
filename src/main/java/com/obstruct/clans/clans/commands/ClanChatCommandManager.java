package com.obstruct.clans.clans.commands;

import com.obstruct.clans.clans.ChatType;
import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanChatCommandManager extends CommandManager {

    public ClanChatCommandManager(SpigotBasePlugin plugin) {
        super(plugin, "ClanChatCommandManager");
    }


    public void registerModules() {
        addModule(new BaseCommand(this));
    }

    class BaseCommand extends Command<Player> {
        public BaseCommand(CommandManager manager) {
            super(manager, "BaseCommand");
            setCommand("clanchat", "cc");
            setIndex(0);
        }


        public boolean execute(Player player, String[] args) {
            Clan clan = getManager(ClanManager.class).getClan(player);
            if (clan == null) {
                UtilMessage.message(player, "Clans", "You are not in a Clan.");
                return false;
            }
            if (args == null || args.length == 0) {
                if (getManager(ClanManager.class).getChatType(player.getUniqueId()) == ChatType.CLAN) {
                    UtilMessage.message(player, "Clan Chat", "Clan Chat: " + ChatColor.RED + "Disabled");
                    getManager(ClanManager.class).getChatTypeMap().remove(player.getUniqueId());
                } else {
                    UtilMessage.message(player, "Clan Chat", "Clan Chat: " + ChatColor.GREEN + "Enabled");
                    getManager(ClanManager.class).getChatTypeMap().put(player.getUniqueId(), ChatType.CLAN);
                }
                return true;
            }
            String msg = UtilMessage.getFinalArg(args, 0);
            clan.inform(false, null, ChatColor.AQUA + player.getName() + " " + ChatColor.DARK_AQUA + msg);
            return false;
        }
    }
}