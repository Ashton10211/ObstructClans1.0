package com.obstruct.clans.clans.menu;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.commands.ClansCommandManager;
import com.obstruct.clans.clans.commands.subcommands.ClanInviteCommand;
import com.obstruct.core.spigot.menu.Button;
import com.obstruct.core.spigot.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class InvitePlayersMenu extends Menu<ClanManager> {

    public InvitePlayersMenu(ClanManager manager, Player player) {
        super(manager, player, 54, "Invite Players", new Button[0]);
    }

    @Override
    protected void buildPage() {
        addButton(new Button(4, new ItemStack(Material.BED), ChatColor.GRAY + "Go Back", new String[0]) {
            @Override
            public void onButtonClick(Player player, ClickType clickType) {
                getPlayer().openInventory(new ManageClanMenu(getManager(), player).getInventory());
            }
        });
        int slot = 0;
        for (Player online : Bukkit.getOnlinePlayers()) {
            Clan clan = getManager().getClan(online);
            if (clan != null) {
                continue;
            }
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(online.getName());
            item.setItemMeta(meta);
            addButton(new Button(9 + slot, item, ChatColor.GREEN + ChatColor.BOLD.toString() + online.getName(), new String[]{"", ChatColor.YELLOW + "Left-Click: " + ChatColor.WHITE + "Invite Player"}) {
                @Override
                public void onButtonClick(Player player, ClickType clickType) {
                    getManager().getManager(ClansCommandManager.class).getModule(ClanInviteCommand.class).execute(player, new String[] { "invite", online.getName() });
                }
            });
            if(9 + slot >= 54) {
                break;
            }
            slot++;
        }
    }
}