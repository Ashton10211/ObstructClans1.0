package com.obstruct.clans.clans;

import com.obstruct.core.spigot.utility.UtilTime;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity("clans") //Entity is what database this will be saved to.
public class Clan {

    //Needs ObjectId to recognise from the database.
    @Id
    private ObjectId id;

    //Indexed is what will be saved to database.
    @Indexed
    private String name;
    private long age;
    private Location home;
    private Set<String> claims;
    private int energy;
    private HashMap<String, Boolean> allianceMap;
    private HashMap<String, Integer> enemyMap;
    private Set<ClanMember> members;
    private boolean admin, safe;

    //Transient makes sure it doesn't get saved to database. Just saves to memory.
    @Transient
    private HashMap<UUID, Long> inviteeMap;
    private HashMap<String, Long> allianceRequestMap;
    private HashMap<String, Long> trustRequestMap;
    private HashMap<String, Long> neutralRequestMap;

    //Constructor to setup the name and this also calls the empty constructor below
    //doing everything inside of the empty constructor.
    public Clan(String name) {
        this();
        this.name = name;
    }

    //If using Morphia you must have a empty constructor. Will explain Morphia
    public Clan() {
        this.age = System.currentTimeMillis();
        this.claims = new HashSet<>();
        this.inviteeMap = new HashMap<>();
        this.allianceRequestMap = new HashMap<>();
        this.trustRequestMap = new HashMap<>();
        this.neutralRequestMap = new HashMap<>();
        this.allianceMap = new HashMap<>();
        this.enemyMap = new HashMap<>();
        this.members = new HashSet<>();
        this.energy = 2400;
    }

    public boolean isSafe(Location location) {
        if (!isAdmin()) {
            return false;
        }
        if (!getName().toLowerCase().contains(" spawn")) {
            return isSafe();
        }
        return (location.getY() >= 80.0D);
    }

    //Gets ClanMember from the members Set above.
    //Loops through each member and checks if the uuid is equal to any
    //Then returns the ClanMember.
    public ClanMember getClanMember(UUID uuid) {
        for (ClanMember member : getMembers()) {
            if (member.getUuid().equals(uuid)) {
                return member;
            }
        }
        return null;
    }

    //Checks if Player has the right rank in the clan.
    //hasMemberRole(player, MemberRole.LEADER); etc
    public boolean hasMemberRole(UUID uuid, MemberRole requiredRole) {
        ClanMember clanMember = getClanMember(uuid);
        if (clanMember == null) {
            return false;
        }
        return clanMember.hasRole(requiredRole);
    }

    public boolean hasMemberRole(Player player, MemberRole memberRole) {
        return hasMemberRole(player.getUniqueId(), memberRole);
    }

    public boolean isTrusted(Clan other) {
        if (other == null) {
            return false;
        }
        if (!getAllianceMap().containsKey(other.getName())) {
            return false;
        }
        return getAllianceMap().get(other.getName());
    }

    public boolean isAllied(Clan other) {
        if (other == null) {
            return false;
        }
        return getAllianceMap().containsKey(other.getName());
    }

    public boolean isEnemy(Clan other) {
        if (other == null) {
            return false;
        }
        return getEnemyMap().containsKey(other.getName());
    }

    public void inform(boolean enablePrefix, String prefix, String message, UUID... uuid) {
        for (ClanMember member : getMembers()) {
            if (Arrays.asList(uuid).contains(member.getUuid())) {
                continue;
            }
            Player player = Bukkit.getPlayer(member.getUuid());
            if (player == null) {
                continue;
            }
            player.sendMessage((enablePrefix ? (ChatColor.BLUE + prefix + "> " + ChatColor.GRAY) : "") + message);
        }
    }

    public int getMaxClaims() {
        return 3 + getMembers().size();
    }

    public String getTrimmedName() {
        return (getName().length() > 8) ? getName().substring(0, 8) : getName();
    }

    public boolean isOnline() {
        return getMembers().stream().anyMatch(clanMember -> (Bukkit.getPlayer(clanMember.getUuid()) != null));
    }

    public String getEnergyString() {
        if (isAdmin()) {
            return "Unlimited";
        }
        double days = UtilTime.trim(getHoursOfEnergy() / 24.0D, 2.0D);
        if(days < 1.0D) {
            return UtilTime.trim(days * 24.0D, 2.0D) + " Hours";
        }
        if(days == 1.0D) {
            return days + " Day";
        }
        return days + " Days";
    }

    public double getEnergyFromHours(int hour) {
        return hour * 24.0D * (getClaims().isEmpty() ? 12.5D : (getClaims().size() * 25.0D)) / 24.0D;
    }

    public double getHoursOfEnergy() {
        return getEnergy() / (getClaims().isEmpty() ? 12.5D : getClaims().size() * 25.0D);
    }

    public Set<Player> getOnlinePlayers() {
        return getMembers().stream().filter(member -> Bukkit.getPlayer(member.getUuid()) != null).map(clanMember -> Bukkit.getPlayer(clanMember.getUuid())).collect(Collectors.toSet());
    }

    public String getDominanceString(Clan other) {
        return ChatColor.WHITE + "(" + ChatColor.GREEN + getEnemyMap().get(other.getName()) + ChatColor.WHITE + ":" + ChatColor.RED + other.getEnemyMap().get(getName()) + ChatColor.WHITE + ")" + ChatColor.RESET;
    }
}