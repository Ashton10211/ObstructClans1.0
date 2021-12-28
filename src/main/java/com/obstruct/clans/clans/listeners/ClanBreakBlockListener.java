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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ClanBreakBlockListener extends SpigotModule<ClanManager> implements Listener {

    public ClanBreakBlockListener(ClanManager manager) {
        super(manager, "ClanBreakBlockListener");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        Clan lClan = getManager().getClan(block.getLocation());
        if (lClan == null) {
            return;
        }
        Player player = event.getPlayer();
        Clan clan = getManager().getClan(player.getUniqueId());
        if (!lClan.equals(clan)) {
            Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
            if (client.isAdministrating()) {
                return;
            }
            ClanRelation clanRelation = getManager().getClanRelation(clan, lClan);
            if (getManager(SiegeManager.class).isSieging(clan, lClan)) {
                return;
            }
            UtilMessage.message(player, "Clans", "You cannot break " + ChatColor.GREEN + UtilFormat.cleanString(block.getType().name()) + ChatColor.GRAY + " in " + clanRelation.getSuffix() + (lClan.isAdmin() ? "" : "Clan ") + lClan.getName() + ChatColor.GRAY + ".");
            event.setCancelled(true);
        } else if (lClan.equals(clan) && clan.getClanMember(player.getUniqueId()).getMemberRole() == MemberRole.RECRUIT) {
            UtilMessage.message(player, "Clans", "Clan Recruits cannot break blocks.");
            event.setCancelled(true);
        }
    }
}