package com.obstruct.clans.clans;

import org.bukkit.ChatColor;

public enum ClanRelation {
    SELF(ChatColor.DARK_AQUA, ChatColor.AQUA, (byte) 126),
    NEUTRAL(ChatColor.GOLD, ChatColor.YELLOW, (byte) 74),
    ENEMY(ChatColor.DARK_RED, ChatColor.RED, (byte) 17),
    SIEGE(ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, (byte) 66),
    ALLY(ChatColor.DARK_GREEN, ChatColor.GREEN, (byte) 134),
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