package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class ClanInteractListener extends SpigotModule<ClanManager> implements Listener {

    private final Material[] disallow = {Material.CHEST, Material.TRAPPED_CHEST, Material.LEVER, Material.WOOD_BUTTON, Material.STONE_BUTTON, Material.FURNACE, Material.FENCE_GATE, Material.WORKBENCH, Material.DISPENSER, Material.BED, Material.WORKBENCH, Material.BURNING_FURNACE, Material.WOODEN_DOOR, Material.WOOD_DOOR, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.TRAP_DOOR, Material.BREWING_STAND, Material.BREWING_STAND_ITEM};

    public ClanInteractListener(ClanManager manager) {
        super(manager, "ClanInteractListener");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Clan lClan = getManager().getClan(block.getLocation());
        if (lClan == null) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = getManager().getClan(player.getUniqueId());
        if (!lClan.equals(clan)) {
            if (getManager(SiegeManager.class).isSieging(clan, lClan)) {
                return;
            }
            Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
            if (client.isAdministrating()) {
                return;
            }
            if (!Arrays.asList(disallow).contains(block.getType())) {
                return;
            }
            ClanRelation clanRelation = getManager().getClanRelation(clan, lClan);
            UtilMessage.message(player, "Clans", "You cannot use " + ChatColor.GREEN + UtilFormat.cleanString(block.getType().name()) + ChatColor.GRAY + " in " + clanRelation.getSuffix() + (lClan.isAdmin() ? "" : "Clan ") + lClan.getName() + ChatColor.GRAY + ".");
            event.setCancelled(true);
        } else if (clan.getClanMember(player.getUniqueId()).getMemberRole() == MemberRole.RECRUIT) {
            if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
                return;
            }
            UtilMessage.message(player, "Clans", "Clan Recruits cannot access " + ChatColor.GREEN + UtilFormat.cleanString(block.getType().toString()) + ChatColor.GRAY + ".");
            event.setCancelled(true);
        }
    }
}