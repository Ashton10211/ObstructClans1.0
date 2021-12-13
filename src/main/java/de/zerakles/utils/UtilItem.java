package de.zerakles.utils;

import org.bukkit.Material;

public class UtilItem {

    public static boolean isArmour(final Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case DIAMOND_HELMET:
            case LEATHER_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case LEATHER_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case DIAMOND_BOOTS:
                return true;
        }
        return false;
    }
}