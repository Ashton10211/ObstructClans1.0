package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClanIgnite extends SpigotModule<ClanManager> implements Listener {

    public ClanIgnite(ClanManager manager) {
        super(manager, "ClanIgnite");
    }

    @EventHandler
    public void blockFlint(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
            ClanManager manager = getManager(ClanManager.class);
            Clan clan = manager.getClan(event.getClickedBlock().getLocation().getChunk());
            if (clan != null && clan.isAdmin() && !client.isAdministrating()) {
                UtilMessage.message(player, "Clans", "You cannot use " + ChatColor.GREEN + UtilFormat.cleanString(player.getItemInHand().getType().name()) + ChatColor.GRAY + " in " + ChatColor.YELLOW + manager.getClanRelation(manager.getClan(player), clan).getSuffix() + clan.getName() + ChatColor.GRAY + ".");
                event.setCancelled(true);
                return;
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType() == Material.FLINT_AND_STEEL && event.getClickedBlock().getType() != Material.TNT && event.getClickedBlock().getType() != Material.NETHERRACK && !client.isAdministrating()) {
            UtilMessage.message(player, "Game", "You cannot use " + ChatColor.YELLOW + "Flint and Steel" + ChatColor.GRAY + " on this block type!");
            event.setCancelled(true);
        }
    }
}