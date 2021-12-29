package com.obstruct.clans.clans.menu;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanMember;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.commands.ClansCommandManager;
import com.obstruct.clans.clans.commands.subcommands.*;
import com.obstruct.core.spigot.common.fancy.FancyMessage;
import com.obstruct.core.spigot.menu.Button;
import com.obstruct.core.spigot.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageClanMenu extends Menu<ClanManager> {

    public ManageClanMenu(ClanManager manager, Player player) {
        super(manager, player, 54, "Manage Clan", new Button[0]);
        buildPage();
        construct();
    }

    @Override
    public void buildPage() {
        Clan clan = getManager().getClan(getPlayer());
        if (clan == null) {
            addButton(new Button(this, 21, new ItemStack(Material.BOOK_AND_QUILL),
                    ChatColor.GREEN + ChatColor.BOLD.toString() + "Create Clan", new String[]{
                    ChatColor.GRAY + "To create a clan type",
                    ChatColor.RED + "/c create <ClanName>"
            }) {
                @Override
                public void onButtonClick(Player player, ClickType clickType) {
                    new FancyMessage(ChatColor.AQUA + "Click here to create a Clan!").suggest("/c create ").send(player);
                    player.closeInventory();
                }
            });

            addButton(new Button(this, 23, new ItemStack(Material.PRISMARINE), ChatColor.GREEN + ChatColor.BOLD.toString() + "Join Clan", new String[0]) {
                @Override
                public void onButtonClick(Player player, ClickType clickType) {
                    player.openInventory(new JoinClanMenu(getManager(), player).getInventory());
                }
            });
            return;
        }
        final List<ClanMember> members = new ArrayList<>(clan.getMembers());
        Collections.sort(members, (o1, o2) -> o2.getMemberRole().compareTo(o1.getMemberRole()));

        List<String> inviteLore = new ArrayList<>();

        inviteLore.add("");
        inviteLore.add(ChatColor.GRAY + "Clans can have a max size of " + ChatColor.YELLOW + "8" + ChatColor.GRAY + " members");
        inviteLore.add(ChatColor.GRAY + "You currently have " + ChatColor.YELLOW + members.size() + ChatColor.GRAY + " members");
        inviteLore.add(ChatColor.GRAY + "More members in your clan will allow you to");
        inviteLore.add(ChatColor.GRAY + "claim more land, but will also increase your Energy drain per minute.");
        if (clan.hasMemberRole(getPlayer(), MemberRole.ADMIN)) {
            inviteLore.add("");
            inviteLore.add(ChatColor.YELLOW + "Left-Click: " + ChatColor.WHITE + "Invite Player");
        }

        addButton(new Button(this, 0, new ItemStack(Material.PRISMARINE), 1, (byte) 1, ChatColor.GREEN + ChatColor.BOLD.toString() + "Invites", inviteLore) {
            @Override
            public void onButtonClick(Player player, ClickType clickType) {
                if (clickType != ClickType.LEFT) {
                    return;
                }
                if (!clan.hasMemberRole(getPlayer(), MemberRole.ADMIN)) {
                    return;
                }
                player.openInventory(new InvitePlayersMenu(getManager(), player).getInventory());
            }
        });

        List<String> claimLore = new ArrayList<>();

        claimLore.add("");
        claimLore.add(ChatColor.GRAY + "Every land claim represents a 16x16 chunk");
        claimLore.add(ChatColor.GRAY + "Your clan can claim a maximum of " + ChatColor.YELLOW + clan.getMaxClaims() + ChatColor.GRAY + " chunks");
        claimLore.add(ChatColor.GRAY + "You currently have " + ChatColor.YELLOW + clan.getClaims().size() + ChatColor.GRAY + " chunk(s) claimed");
        claimLore.add(ChatColor.GRAY + "Increase max claims with more clan members");
        claimLore.add(ChatColor.GRAY + "Energy cost will increase with more land claimed.");

        if (clan.hasMemberRole(getPlayer().getUniqueId(), MemberRole.ADMIN)) {
            claimLore.add("");
            claimLore.add(ChatColor.YELLOW + "Left-Click: " + ChatColor.WHITE + "Claim Land");
            claimLore.add(ChatColor.YELLOW + "Shift-Left-Click: " + ChatColor.WHITE + "Unclaim Land");
            claimLore.add(ChatColor.YELLOW + "Shift-Right-Click: " + ChatColor.WHITE + "Unclaim All Land");
        }

        addButton(new Button(this, 2, new ItemStack(Material.PRISMARINE), ChatColor.GREEN + ChatColor.BOLD.toString() + "Territory", claimLore) {
            @Override
            public void onButtonClick(Player player, ClickType clickType) {
                if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
                    return;
                }
                if (clickType == ClickType.LEFT) {
                    getManager().getManager(ClansCommandManager.class).getModule(ClanClaimCommand.class).execute(player, new String[]{"claim"});
                } else if (clickType == ClickType.SHIFT_LEFT) {
                    getManager().getManager(ClansCommandManager.class).getModule(ClanUnclaimCommand.class).execute(player, new String[]{"unclaim"});
                } else if (clickType == ClickType.SHIFT_RIGHT) {
                    getManager().getManager(ClansCommandManager.class).getModule(ClanUnclaimCommand.class).execute(player, new String[]{"unclaim", "all"});
                }
            }
        });

        addButton(new Button(this, 4, new ItemStack(Material.SEA_LANTERN), ChatColor.GREEN + ChatColor.BOLD.toString() + "Energy", new String[]{
                        "",
                        ChatColor.GRAY + "Energy is the currency used to upkeep",
                        ChatColor.GRAY + "your clan. Energy drains over time and",
                        ChatColor.GRAY + "you will need to buy more energy at the",
                        ChatColor.GRAY + "NPC in the Shops. More land increases the",
                        ChatColor.GRAY + "rate the energy drains at.",
                        "",
                        ChatColor.YELLOW + "Energy: " + ChatColor.WHITE + clan.getEnergyString()
                }));

        List<String> disbandLore = new ArrayList<>();

        disbandLore.add("");
        disbandLore.add(ChatColor.YELLOW + "Shift-Left-Click: " + ChatColor.WHITE + "Leave Clan");
        if(clan.hasMemberRole(getPlayer().getUniqueId(), MemberRole.LEADER)) {
            disbandLore.add(ChatColor.YELLOW + "Shift-Right-Click: " + ChatColor.WHITE + "Disband Clan");
        }
        addButton(new Button(this, 6, new ItemStack(Material.PRISMARINE), 1, (byte) 2, ChatColor.GREEN + ChatColor.BOLD.toString() + "Leave", disbandLore) {
            @Override
            public void onButtonClick(Player player, ClickType clickType) {
                if (clickType == ClickType.SHIFT_LEFT) {
                    getManager().getManager(ClansCommandManager.class).getModule(ClanLeaveCommand.class).execute(player, new String[]{"leave"});
                } else if (clickType == ClickType.SHIFT_RIGHT) {
                    getManager().getManager(ClansCommandManager.class).getModule(ClanDisbandCommand.class).execute(player, new String[0]);
                }
                getPlayer().closeInventory();
            }
        });

        addButton(new Button(this, 8, new ItemStack(Material.LAVA_BUCKET), ChatColor.GREEN + ChatColor.BOLD.toString() + "Commands", new String[]

                {
                        "",
                        ChatColor.YELLOW + "/c help " + ChatColor.WHITE + "Lists Clans Commands",
                        ChatColor.YELLOW + "/c ally <clan> " + ChatColor.WHITE + "Request an alliance with another Clan.",
                        ChatColor.YELLOW + "/c neutral <clan> " + ChatColor.WHITE + "Revoke ally.",
                        ChatColor.YELLOW + "/c sethome " + ChatColor.WHITE + "Set Home Bed",
                        ChatColor.YELLOW + "/c home" + ChatColor.WHITE + " Teleport to Clan Home Bed",
                        ChatColor.YELLOW + "/map " + ChatColor.WHITE + "Give yourself a World Map",
                }));

        int slot = 0;

        for (
                ClanMember member : members) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            skullMeta.setOwner(member.getPlayerName());
            item.setItemMeta(skullMeta);

            String name = ChatColor.RED + ChatColor.BOLD.toString() + member.getPlayerName();
            if (member.isOnline()) {
                name = ChatColor.GREEN + ChatColor.BOLD.toString() + member.getPlayerName();
            }
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.YELLOW + "Role: " + ChatColor.WHITE + member.getMemberRole());

            if (!getPlayer().getUniqueId().equals(member.getUuid())) {
                if (clan.hasMemberRole(getPlayer(), MemberRole.ADMIN)) {
                    if (clan.getClanMember(getPlayer().getUniqueId()).getMemberRole().ordinal() > member.getMemberRole().ordinal()) {
                        lore.add(ChatColor.YELLOW + "Left-Click: " + ChatColor.WHITE + "Promote");
                        lore.add(ChatColor.YELLOW + "Right-Click: " + ChatColor.WHITE + "Demote");
                        lore.add(ChatColor.YELLOW + "Shift-Right-Click: " + ChatColor.WHITE + "Kick");
                    }
                }
            }
            addButton(new Button(this, 18 + slot, item, 1, (byte) 3, name, lore) {
                @Override
                public void onButtonClick(Player player, ClickType clickType) {
                    if (clickType == ClickType.LEFT) {
                        getManager().getManager(ClansCommandManager.class).getModule(ClanPromoteCommand.class).execute(player, new String[]{"promote", member.getPlayerName()});
                    } else if (clickType == ClickType.RIGHT) {
                        getManager().getManager(ClansCommandManager.class).getModule(ClanDemoteCommand.class).execute(player, new String[]{"demote", member.getPlayerName()});
                    } else if (clickType == ClickType.SHIFT_RIGHT) {
                        getManager().getManager(ClansCommandManager.class).getModule(ClanKickCommand.class).execute(player, new String[]{"kick", member.getPlayerName()});
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.openInventory(new ManageClanMenu(getManager(), getPlayer()).getInventory());
                        }
                    }.runTaskLater(getManager().getPlugin(), 2L);
                }
            });
            slot++;
        }
    }
}