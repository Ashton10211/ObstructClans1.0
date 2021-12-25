package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.blockregen.BlockRegenManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClanClaimCommand extends Command<Player> {

    public ClanClaimCommand(CommandManager manager) {
        super(manager, "ClaimClanCommand");
        setCommand("claim");
        setIndex(1);
        setRequiredArgs(1);
    }

    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
        boolean admin = client.isAdministrating();
        Chunk chunk = player.getLocation().getChunk();
        if (!admin) {
            if (player.getWorld().getEnvironment() != World.Environment.NORMAL) {
                UtilMessage.message(player, "Clans", "You can only claim in the Overworld.");
                return false;
            }
            if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
                UtilMessage.message(player, "Clans", "You must be an Admin or higher to claim Territory.");
                return false;
            }
            if (clan.getClaims().size() >= clan.getMaxClaims()) {
                UtilMessage.message(player, "Clans", "Your Clan cannot claim anymore Territory.");
                return false;
            }
        }
        if (clan.getClaims().contains(UtilFormat.chunkToString(chunk))) {
            UtilMessage.message(player, "Clans", "Your Clan already owns this Territory.");
            return false;
        }
        Clan target = getManager(ClanManager.class).getClan(chunk);
        if (!admin) {
            if (target != null) {
                UtilMessage.message(player, "Clans", "This Territory is owned by " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                return false;
            }
            Set<String> clanSet = new HashSet<>();
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    Chunk lChunk = player.getWorld().getChunkAt(chunk.getX() + x, chunk.getZ() + z);
                    if (getManager(ClanManager.class).getClan(lChunk) != null) {
                        clanSet.add(getManager(ClanManager.class).getClan(lChunk).getName());
                    }
                }
            }
            if (clanSet.stream().anyMatch(s -> !clan.getName().equals(s))) {
                UtilMessage.message(player, "Clans", "You cannot claim this Territory, it causes a box.");
                UtilMessage.message(player, "Clans", "This means a Territory has all sides claimed.");
                return false;
            }
        }
        if (clan.getClaims().size() > 0) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    Chunk lChunk = player.getWorld().getChunkAt(chunk.getX() + x, chunk.getZ() + z);
                    if (getManager(ClanManager.class).getClan(lChunk) != null) {
                        claimChunk(player, clan, chunk);
                        return true;
                    }
                }
            }
        } else {
            claimChunk(player, clan, chunk);
            return true;
        }
        UtilMessage.message(player, "Clans", "You need to claim next to your own territory.");
        return false;
    }

    private void claimChunk(Player player, Clan clan, Chunk chunk) {
        clan.getClaims().add(UtilFormat.chunkToString(chunk));
        UtilMessage.message(player, "Clans", "You claimed Territory " + ChatColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + ")" + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " claimed Territory " + ChatColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + ")" + ChatColor.GRAY + ".", player.getUniqueId());
        getManager(BlockRegenManager.class).outlineChunk(chunk, Material.GLOWSTONE);

        for (Entity entity : chunk.getEntities()) {
            if(entity instanceof Player) {

            }
        }

        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
    }
}