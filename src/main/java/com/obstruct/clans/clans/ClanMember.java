package com.obstruct.clans.clans;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
//Class for Clan Members
public class ClanMember {

    //UUID to identify which Player it is without storing the whole player object.
    private UUID uuid;
    //MemberRole is the Clan Rank of a Player. Recruit, Member, Admin, Leader.
    private MemberRole memberRole;

    //If using Morphia you must have a empty constructor. Will explain Morphia
    public ClanMember() {
    }

    public ClanMember(UUID uuid, MemberRole memberRole) {
        this.uuid = uuid;
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
}