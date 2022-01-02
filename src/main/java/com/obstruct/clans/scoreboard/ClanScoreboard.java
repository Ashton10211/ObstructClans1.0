package com.obstruct.clans.scoreboard;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanMember;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.events.*;
import com.obstruct.clans.clans.listeners.ClanMovementListener;
import com.obstruct.clans.gamer.GamerManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.client.Rank;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.scoreboard.ScoreboardManager;
import com.obstruct.core.spigot.scoreboard.ScoreboardPriority;
import com.obstruct.core.spigot.scoreboard.data.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

public class ClanScoreboard extends PlayerScoreboard implements Listener {

    private final String noneTeam = "@None";

    public ClanScoreboard(ScoreboardManager manager) {
        super(manager, "ClansScoreboard", "Obstruct Clans");
    }

    @Override
    public void giveNewScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        addPlayer(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updateSideBar(event.getPlayer());
    }

    @EventHandler
    public void onClanMove(ClanMoveEvent event) {
        updateSideBar(event.getPlayer(), event.getLocTo());
    }

    @EventHandler
    public void onClanEnergyUpdate(ClanEnergyUpdateEvent event) {
        for (Player online : event.getClan().getOnlinePlayers()) {
            updateSideBar(online);
        }
    }

    @EventHandler
    public void onClanClaim(ClanClaimEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof Player) {
                updateSideBar((Player) entity);
            }
        }
    }

    @EventHandler
    public void onClaimUnclaim(ClanUnclaimEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof Player) {
                updateSideBar((Player) entity);
            }
        }
    }

    @EventHandler
    public void onClanCreate(ClanCreateEvent event) {
        if (event.isCancelled()) {
            return;
        }
        for (Player online : event.getClan().getOnlinePlayers()) {
            updateSideBar(online);
        }
        addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onClanDisband(ClanDisbandEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        removeClan(event.getClan());
    }

    @EventHandler
    public void onClanJoin(ClanJoinEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onClanLeave(ClanLeaveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onClanKick(ClanKickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player target = Bukkit.getPlayer(event.getTarget().getUuid());
        if (target != null) {
            event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
            removePlayer(target);
        }
    }

    @EventHandler
    public void onClanAlly(ClanAllyEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        event.getOther().getOnlinePlayers().forEach(this::updateSideBar);
        updateRelation(event.getClan());
        updateRelation(event.getOther());
    }

    @EventHandler
    public void onClanTrust(ClanTrustEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        event.getOther().getOnlinePlayers().forEach(this::updateSideBar);
        updateRelation(event.getClan());
        updateRelation(event.getOther());
    }

    @EventHandler
    public void onClanTrustRevoke(ClanRevokeTrustEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        event.getOther().getOnlinePlayers().forEach(this::updateSideBar);
        updateRelation(event.getClan());
        updateRelation(event.getOther());
    }

    @EventHandler
    public void onClanEnemy(ClanEnemyEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        event.getOther().getOnlinePlayers().forEach(this::updateSideBar);
        updateRelation(event.getClan());
        updateRelation(event.getOther());
    }

    @EventHandler
    public void onClanNeutral(ClanNeutralEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.getClan().getOnlinePlayers().forEach(this::updateSideBar);
        event.getOther().getOnlinePlayers().forEach(this::updateSideBar);
        updateRelation(event.getClan());
        updateRelation(event.getOther());
    }

//    @EventHandler
//    public void onClanPillage(ClanPillageStartEvent event) {
//        if (event.isCancelled()) {
//            return;
//        }
//        event.getPillager().getOnlinePlayers().forEach(this::updateSideBar);
//        event.getPillagee().getOnlinePlayers().forEach(this::updateSideBar);
//        updateRelation(event.getPillager());
//        updateRelation(event.getPillagee());
//    }

//    @EventHandler
//    public void onClanPillageEnd(ClanPillageEndEvent event) {
//        if (event.isCancelled()) {
//            return;
//        }
//        event.getPillager().getOnlinePlayers().forEach(this::updateSideBar);
//        event.getPillagee().getOnlinePlayers().forEach(this::updateSideBar);
//        updateRelation(event.getPillager());
//        updateRelation(event.getPillagee());
//    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player == null) {
            return;
        }
        Player killer = player.getKiller();
        if (killer == null) {
            return;
        }
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            return;
        }
        Clan killerClan = getManager(ClanManager.class).getClan(killer);
        if (killerClan == null) {
            return;
        }
        updateDominancePoints(clan, killerClan);
    }

    public void updateSideBar(Player player) {
        updateSideBar(player, player.getLocation());
    }

    private void updateSideBar(Player player, Location location) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("info");
        if (objective != null) {
            objective.unregister();
        }
        objective = scoreboard.registerNewObjective("info", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(getDisplayName());
        Clan pClan = getManager(ClanManager.class).getClan(player);

        Score cClan = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Clan");
        Score clan = objective.getScore((pClan == null) ? "No Clan" : (ChatColor.AQUA + pClan.getName()));
        Score blank = objective.getScore(getBlank(1));
        Score eEnergy = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Clan Energy");
        Score energy = objective.getScore((pClan == null) ? "" : (ChatColor.GREEN + pClan.getEnergyString()));
        Score blank1 = objective.getScore(getBlank(2));
        Score gGold = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Gold");
        Score gold = objective.getScore(ChatColor.GOLD.toString() + getManager(GamerManager.class).getGamer(player).getGold());

        Score blank2 = objective.getScore(getBlank(4));
        Score tTerritory = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Territory");
        Score territory = objective.getScore(getManager(ClanManager.class).getModule(ClanMovementListener.class).getTerritoryString(player, location, true));
        cClan.setScore(15);
        clan.setScore(14);
        blank.setScore(13);
        if (pClan != null) {
            eEnergy.setScore(12);
            energy.setScore(11);
            blank1.setScore(10);
            gGold.setScore(9);
            gold.setScore(8);

            blank2.setScore(7);
            tTerritory.setScore(6);
            territory.setScore(5);
        } else {
            gGold.setScore(12);
            gold.setScore(11);

            blank2.setScore(10);
            tTerritory.setScore(9);
            territory.setScore(8);
        }
    }

    private void addPlayer(Player player) {
        ClanManager clanManager = getManager(ClanManager.class);
        Clan clan = clanManager.getClan(player);
        for (Player online : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = online.getScoreboard();
            if (clan == null) {
                if (scoreboard.getTeam(noneTeam) == null) {
                    scoreboard.registerNewTeam(noneTeam);
                }
                scoreboard.getTeam(noneTeam).setPrefix(ChatColor.YELLOW + "");
                if (!scoreboard.getTeam(noneTeam).hasEntry(player.getName())) {
                    scoreboard.getTeam(noneTeam).addEntry(player.getName());
                }
                continue;
            }
            addClan(scoreboard, clan, clanManager.getClan(online));
            if (!scoreboard.getTeam(clan.getName()).hasEntry(player.getName())) {
                scoreboard.getTeam(clan.getName()).addEntry(player.getName());
            }
        }
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeams().forEach(Team::unregister);
        for (Clan c : clanManager.getClanMap().values()) {
            if (c.isOnline()) {
                addClan(scoreboard, c, clanManager.getClan(player.getUniqueId()));
                Team team = scoreboard.getTeam(c.getName());
                for (ClanMember member : c.getMembers()) {
                    Player p = Bukkit.getPlayer(member.getUuid());
                    if (p != null) {
                        if (!team.hasEntry(p.getName())) {
                            team.addEntry(p.getName());
                        }
                    }
                }
            }
        }
        ClientDataRepository clientDataRepository = getManager(RedisManager.class).getModule(ClientDataRepository.class);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (clanManager.getClan(online.getUniqueId()) == null) {
                Client onlineClient = clientDataRepository.getClient(online.getUniqueId());
                if (onlineClient.hasRank(Rank.MEDIA, false)) {
                    updateRank(onlineClient);
                    continue;
                }
                if (scoreboard.getTeam(noneTeam) == null) {
                    scoreboard.registerNewTeam(noneTeam);
                    scoreboard.getTeam(noneTeam).setPrefix(ChatColor.YELLOW + "");
                }
                if (!scoreboard.getTeam(noneTeam).hasEntry(online.getName())) {
                    scoreboard.getTeam(noneTeam).addEntry(online.getName());
                }
            }
        }
    }

    private void updateRank(Client client) {
        if (getManager(ClanManager.class).getClan(client.getUuid()) != null) {
            return;
        }
        if (client.hasRank(Rank.MEDIA, false)) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = online.getScoreboard();
                String str = client.getRank().getTrimmedRankPrefix();
                if (scoreboard.getTeam(str) == null) {
                    scoreboard.registerNewTeam(str);
                    scoreboard.getTeam(str).setPrefix(client.getRank().getTag(true) + " " + ChatColor.YELLOW);
                }
                if (!scoreboard.getTeam(str).hasEntry(client.getPlayer().getName())) {
                    scoreboard.getTeam(str).addEntry(client.getPlayer().getName());
                }
            }
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = online.getScoreboard();
                if (scoreboard.getTeam(noneTeam) == null) {
                    scoreboard.registerNewTeam(noneTeam);
                    scoreboard.getTeam(noneTeam).setPrefix(ChatColor.YELLOW + "");
                }
                if (!scoreboard.getTeam(noneTeam).hasEntry(client.getPlayer().getName())) {
                    scoreboard.getTeam(noneTeam).addEntry(client.getPlayer().getName());
                }
            }
        }
    }

    private void addClan(Scoreboard scoreboard, Clan clan, Clan other) {
        if (scoreboard.getTeam(clan.getName()) == null) {
            scoreboard.registerNewTeam(clan.getName());
        }
        ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, other);
        scoreboard.getTeam(clan.getName()).setPrefix(clanRelation.getPrefix() + clan.getTrimmedName() + clanRelation.getSuffix() + " ");
    }

    public void updateRelation(Clan clan) {
        ClanManager manager = getManager(ClanManager.class);
        for (ClanMember member : clan.getMembers()) {
            Player player = Bukkit.getPlayer(member.getUuid());
            if (player == null) {
                continue;
            }
            for (Team team : player.getScoreboard().getTeams()) {
                if (team.getName().equals(noneTeam)) {
                    team.setPrefix(ChatColor.YELLOW + "");
                    continue;
                }
                Clan other = manager.getClan(team.getName());
                if (other != null) {
                    ClanRelation clanRelation = manager.getClanRelation(clan, other);
                    team.setPrefix(clanRelation.getPrefix() + other.getTrimmedName() + clanRelation.getSuffix() + " ");
                    team.setSuffix("");
                    if (clanRelation == ClanRelation.ENEMY) {
                        updateDominancePoints(clan, other);
                    }
                }
            }
        }
    }

    public void removePlayer(Player player) {
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if (clan != null) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = online.getScoreboard();
                for (Team team : scoreboard.getTeams()) {
                    if (team.getName().equals(clan.getName()) && team.hasEntry(player.getName())) {
                        team.removeEntry(player.getName());
                        if (!clan.isOnline()) {
                            team.unregister();
                        }
                    }
                }
            }
        }
        addNone(player);
    }

    public void removeClan(Clan clan) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (Team team : online.getScoreboard().getTeams()) {
                if (team.getName().equals(clan.getName())) {
                    team.unregister();
                    for (Player member : clan.getOnlinePlayers()) {
                        addNone(member);
                    }
                }
            }
        }
    }

    private void addNone(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getScoreboard().getTeam(noneTeam) == null) {
                online.getScoreboard().registerNewTeam(noneTeam);
            }
            online.getScoreboard().getTeam(noneTeam).setPrefix(ChatColor.YELLOW.toString());
            if (!online.getScoreboard().getTeam(noneTeam).hasEntry(player.getName())) {
                online.getScoreboard().getTeam(noneTeam).addEntry(player.getName());
            }
            Team entryTeam = player.getScoreboard().getEntryTeam(online.getName());
            if (!(entryTeam.getName().equals(noneTeam) || entryTeam.getName().contains("@"))) {
                Clan clan = getManager(ClanManager.class).getClan(entryTeam.getName());
                entryTeam.setPrefix(ChatColor.GOLD + clan.getTrimmedName() + ChatColor.YELLOW + " ");
                entryTeam.setSuffix("");
            }
        }
        updateRank(getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId()));
    }

    private void updateDominancePoints(Clan clan, Clan other) {
        for (Player online : clan.getOnlinePlayers()) {
            for (Team team : online.getScoreboard().getTeams()) {
                if (team.getName().equals(noneTeam) || team.getName().contains("@")) {
                    continue;
                }
                //team.setSuffix(" " + getEnemyString(clan, other));
            }
        }
        for (Player online : other.getOnlinePlayers()) {
            for (Team team : online.getScoreboard().getTeams()) {
                if (team.getName().equals(noneTeam) || team.getName().contains("@")) {
                    continue;
                }
                //team.setSuffix(" " + getEnemyString(clan, other));
            }
        }
    }

    private String getEnemyString(Clan dead, Clan killer) {
        if (dead.getWarPoints().get(killer.getName()) <= killer.getWarPoints().get(dead.getName())) {
            return ChatColor.RED + "-" + killer.getWarPoints().get(dead.getName());
        } else if (dead.getWarPoints().get(killer.getName()) > killer.getWarPoints().get(dead.getName())) {
            return ChatColor.GREEN + "+" + dead.getWarPoints().get(killer.getName());
        }
        return ChatColor.YELLOW + "" + dead.getWarPoints().get(killer.getName());
    }

    @Override
    public ScoreboardPriority getScoreboardPriority() {
        return ScoreboardPriority.MONITOR;
    }
}
