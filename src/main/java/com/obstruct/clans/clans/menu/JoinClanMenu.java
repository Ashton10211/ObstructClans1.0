package com.obstruct.clans.clans.menu;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.commands.ClansCommandManager;
import com.obstruct.clans.clans.commands.subcommands.ClanClaimCommand;
import com.obstruct.clans.clans.commands.subcommands.ClanJoinCommand;
import com.obstruct.clans.clans.events.ClanJoinEvent;
import com.obstruct.core.spigot.menu.Button;
import com.obstruct.core.spigot.menu.Menu;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JoinClanMenu extends Menu<ClanManager> {

    public JoinClanMenu(ClanManager manager, Player player) {
        super(manager, player, 54, "Join Clan", new Button[0]);
        buildPage();
        construct();
    }

    @Override
    public void buildPage() {
        List<Clan> clans = new ArrayList<>();
        for (Clan clan : getManager().getClanMap().values()) {
            if(clan.getInviteeMap().containsKey(getPlayer().getUniqueId()) && !UtilTime.elapsed(clan.getInviteeMap().get(getPlayer().getUniqueId()), 300000L)) {
                clans.add(clan);
            }
        }
        addButton(new Button(this, 4, new ItemStack(Material.BED), ChatColor.GRAY + "Go Back", new String[0]) {
            @Override
            public void onButtonClick(Player player, ClickType clickType) {
                getPlayer().openInventory(new ManageClanMenu(getManager(), player).getInventory());
            }
        });
        if(clans.isEmpty()) {
            addButton(new Button(this, 22, new ItemStack(Material.BOOK), ChatColor.RED + ChatColor.BOLD.toString() + "You have no Clan Invitations!", new String[0]));
        } else {
            for (Clan clan : clans) {
                addButton(new Button(this, 9 + clans.indexOf(clan), new ItemStack(Material.BOOK), ChatColor.GREEN + ChatColor.BOLD.toString() + clan.getName(), new String[0]){
                    @Override
                    public void onButtonClick(Player player, ClickType clickType) {
                        getManager().getManager(ClansCommandManager.class).getModule(ClanJoinCommand.class).execute(player, new String[] { "join", clan.getName() });
                        player.closeInventory();
                    }
                });
            }
        }
    }
}