package com.obstruct.clans.scoreboard;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanMember;
import com.obstruct.clans.clans.ClanRelation;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

    public void updateSideBar(Player player) {
        updateSideBar(player, player.getLocation());
    }

    private void updateSideBar(Player player, Location location) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("info");
        if(objective != null) {
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
        Score gold = objective.getScore("TODO");

        Score blank2 = objective.getScore(getBlank(4));
        Score tTerritory = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Territory");
        Score territory = objective.getScore("Wilderness");
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
            if(clan == null) {
                if(scoreboard.getTeam(noneTeam) == null) {
                    scoreboard.registerNewTeam(noneTeam);
                }
                scoreboard.getTeam(noneTeam).setPrefix(ChatColor.YELLOW + "");
                scoreboard.getTeam(noneTeam).addPlayer(player);
                continue;
            }
            addClan(scoreboard, clan, clanManager.getClan(online));
            scoreboard.getTeam(clan.getName()).addPlayer(player);
        }
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeams().forEach(Team::unregister);
        for (Clan c : clanManager.getClanMap().values()) {
            if(c.isOnline()) {
                addClan(scoreboard, c, clanManager.getClan(player.getUniqueId()));
                Team team = scoreboard.getTeam(c.getName());
                for (ClanMember member : c.getMembers()) {
                    Player p = Bukkit.getPlayer(member.getUuid());
                    if(p != null) {
                        team.addPlayer(p);
                    }
                }
            }
        }
        ClientDataRepository clientDataRepository = getManager(RedisManager.class).getModule(ClientDataRepository.class);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (clanManager.getClan(online.getUniqueId()) == null) {
                Client onlineClient = clientDataRepository.getClient(online.getUniqueId());
                if(onlineClient.hasRank(Rank.MEDIA, false)) {
                    updateRank(onlineClient);
                    continue;
                }
                if(scoreboard.getTeam(noneTeam) == null) {
                    scoreboard.registerNewTeam(noneTeam);
                }
                scoreboard.getTeam(noneTeam).setPrefix(ChatColor.YELLOW + "");
                scoreboard.getTeam(noneTeam).addPlayer(online);
            }
        }
    }

    private void updateRank(Client client) {
        if(client.hasRank(Rank.MEDIA, false)) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = online.getScoreboard();
                String str = client.getRank().getTrimmedRankPrefix();
                if(scoreboard.getTeam(str) == null) {
                    scoreboard.registerNewTeam(str);
                    scoreboard.getTeam(str).setPrefix(client.getRank().getTag(true) + " " + ChatColor.YELLOW);
                }
                scoreboard.getTeam(str).addPlayer(client.getPlayer());
            }
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = online.getScoreboard();
                if(scoreboard.getTeam(noneTeam) == null) {
                    scoreboard.registerNewTeam(noneTeam);
                    scoreboard.getTeam(noneTeam).setPrefix(ChatColor.YELLOW + "");
                }
                scoreboard.getTeam(noneTeam).addPlayer(client.getPlayer());
            }
        }
    }

    private void addClan(Scoreboard scoreboard, Clan clan, Clan other) {
        if(scoreboard.getTeam(clan.getName()) == null) {
            scoreboard.registerNewTeam(clan.getName());
        }
        ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, other);
        scoreboard.getTeam(clan.getName()).setPrefix(clanRelation.getPrefix() + clan.getTrimmedName() + clanRelation.getSuffix() + " ");
    }

    public void updateRelation(Clan clan) {
        ClanManager manager = getManager(ClanManager.class);
        for (ClanMember member : clan.getMembers()) {
            Player player = Bukkit.getPlayer(member.getUuid());
            if(player == null) {
                continue;
            }
            for (Team team : player.getScoreboard().getTeams()) {
                if(team.getName().equals(noneTeam)) {
                    team.setPrefix(ChatColor.YELLOW + "");
                    continue;
                }
                Clan other = manager.getClan(team.getName());
                if(other != null) {
                    ClanRelation clanRelation = manager.getClanRelation(clan, other);
                    team.setPrefix(clanRelation.getPrefix() + other.getTrimmedName() + clanRelation.getSuffix() + "");
                    team.setSuffix("");
                    if(clanRelation == ClanRelation.ENEMY) {
                        updateDominancePoints(clan, other);
                    }
                }
            }
        }
    }

    public void removePlayer(Player player) {
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if(clan != null) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = online.getScoreboard();
                for (Team team : scoreboard.getTeams()) {
                    if(team.getName().equals(clan.getName())) {
                        team.removePlayer(player);
                        if(!clan.isOnline()) {
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
                if(team.getName().equals(clan.getName())) {
                    team.unregister();
                    for (ClanMember member : clan.getMembers()) {
                        Player player = Bukkit.getPlayer(member.getUuid());
                        if (player != null) {
                            addNone(player);
                        }
                    }
                }
            }
        }
    }

    private void addNone(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if(online.getScoreboard().getTeam(noneTeam) == null) {
                online.getScoreboard().registerNewTeam(noneTeam);
                online.getScoreboard().getTeam(noneTeam).setPrefix(ChatColor.YELLOW.toString());
            }
            online.getScoreboard().getTeam(noneTeam).addPlayer(player);
        }
        updateRank(getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId()));
    }

    private void updateDominancePoints(Clan clan, Clan other) {
        if(getManager(ClanManager.class).getClanRelation(clan,other) != ClanRelation.ENEMY) {
            return;
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (Team team : online.getScoreboard().getTeams()) {
                if(team.getName().equals(noneTeam) || team.getName().contains("@")) {
                    continue;
                }
                team.setSuffix(" " + getEnemyString(clan, other));
            }
        }
    }

    private String getEnemyString(Clan clan, Clan other) {
        if(clan.getEnemyMap().get(other.getName()) < other.getEnemyMap().get(clan.getName())) {
            return ChatColor.RED + "-" + other.getEnemyMap().get(clan.getName());
        }
        if(clan.getEnemyMap().get(other.getName()) > other.getEnemyMap().get(clan.getName())) {
            return ChatColor.GREEN + "+" + clan.getEnemyMap().get(other.getName());
        }
        return ChatColor.YELLOW + "" + clan.getEnemyMap().get(other.getName());
    }

    @Override
    public ScoreboardPriority getScoreboardPriority() {
        return ScoreboardPriority.MONITOR;
    }
}
