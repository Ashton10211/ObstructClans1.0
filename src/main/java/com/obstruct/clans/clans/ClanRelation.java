package com.obstruct.clans.clans;

import org.bukkit.ChatColor;

public enum ClanRelation {
    SELF(ChatColor.DARK_AQUA, ChatColor.AQUA, (byte) 126),
    NEUTRAL(ChatColor.GOLD, ChatColor.YELLOW, (byte) 74),
    ENEMY(ChatColor.DARK_RED, ChatColor.RED, (byte) 17),
    ALLY(ChatColor.DARK_GREEN, ChatColor.GREEN, (byte) 122),
    ALLY_TRUSTED(ChatColor.GREEN, ChatColor.DARK_GREEN, (byte) 123),
    ADMIN(ChatColor.WHITE, ChatColor.WHITE, (byte) 58);

    private final ChatColor prefix;
    private final ChatColor suffix;
    private final byte mapColor;

    ClanRelation(ChatColor prefix, ChatColor suffix, byte mapColor) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.mapColor = mapColor;
    }

    public byte getMapColor() {
        return this.mapColor;
    }

    public ChatColor getPrefix() {
        return this.prefix;
    }

    public ChatColor getSuffix() {
        return this.suffix;
    }
}