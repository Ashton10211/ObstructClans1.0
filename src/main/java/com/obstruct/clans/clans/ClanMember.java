package com.obstruct.clans.clans;

import com.obstruct.core.shared.utility.UtilDebug;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
//Class for Clan Members
public class ClanMember {

    //UUID to identify which Player it is without storing the whole player object.
    private UUID uuid;
    //Player name
    private String playerName;
    //MemberRole is the Clan Rank of a Player. Recruit, Member, Admin, Leader.
    private MemberRole memberRole;

    //If using Morphia you must have a empty constructor. Will explain Morphia
    public ClanMember() {
    }

    public ClanMember(Player player, MemberRole memberRole) {
        this.uuid = player.getUniqueId();
        this.playerName = player.getName();
        this.memberRole = memberRole;
    }

    public boolean hasRole(MemberRole requiredRole) {
        return getMemberRole().ordinal() >= requiredRole.ordinal();
    }

    public void promote() {
        this.memberRole = MemberRole.values()[Math.min(MemberRole.values().length, getMemberRole().ordinal() + 1)];
    }

    public void demote() {
        this.memberRole = MemberRole.values()[Math.max(0, getMemberRole().ordinal() - 1)];
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(getUuid()) != null;
    }
}