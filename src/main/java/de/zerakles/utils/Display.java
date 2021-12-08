package de.zerakles.utils;

import java.lang.reflect.Constructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Display {
    private static final int BARS = 24;

    public static void display(String text, Player player) {
        sendJsonMessage(player, text, ChatAction.ACTION_BAR);
    }

    public static void sendJsonMessage(Player player, String text, ChatAction chatAction) {
        Object chat = null;
        Class[] arry = Utils.getNmsClass("IChatBaseComponent").getDeclaredClasses();
        if (arry.length == 0) {
            chat = Utils.getAndInvokeMethod(Utils.getNmsClass("ChatSerializer"), "a",
                    new Class[] { String.class }, null, new Object[] { "{\"text\":\" " + text + " " + "\"}" });
        } else {
            chat = Utils.getAndInvokeMethod(Utils.getNmsClass("IChatBaseComponent").getDeclaredClasses()[0], "a",
                    new Class[] { String.class }, null, new Object[] { "{\"text\":\" " + text + " " + "\"}" });
        }
        Constructor<?> chatConstructor = Utils.getConstructor(Utils.getNmsClass("PacketPlayOutChat"), new Class[] { Utils.getNmsClass("IChatBaseComponent"), byte.class });
        Object packet = Utils.callConstructor(chatConstructor, new Object[] { chat, Byte.valueOf(chatAction.getValue()) });
        Utils.sendPacket(player, packet);
    }

    public static void displaySubTitle(Player player, String text, int fadeIn, int stay, int fadeOut) {
        Object subtitle = null;
        Class[] arry = Utils.getNmsClass("IChatBaseComponent").getDeclaredClasses();
        if (arry.length == 0) {
            subtitle = Utils.getAndInvokeMethod(Utils.getNmsClass("ChatSerializer"), "a",
                    new Class[] { String.class }, null, new Object[] { "{\"text\":\" " + text + " " + "\"}" });
        } else {
            subtitle = Utils.getAndInvokeMethod(Utils.getNmsClass("IChatBaseComponent").getDeclaredClasses()[0], "a",
                    new Class[] { String.class }, null, new Object[] { "{\"text\":\" " + text + " " + "\"}" });
        }
        Constructor<?> subTitleConstructor = Utils.getConstructor(Utils.getNmsClass("PacketPlayOutTitle"), new Class[] { Utils.getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                Utils.getNmsClass("IChatBaseComponent"), int.class, int.class, int.class });
        Object packetSubTitle = Utils.callConstructor(subTitleConstructor, new Object[] { Utils.getEnumConstant(
                Utils.getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], "SUBTITLE"),
                subtitle,
                Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut) });
        Utils.sendPacket(player, packetSubTitle);
    }

    public static void displayTitle(Player player, String text, int fadeIn, int stay, int fadeOut) {
        Object title = null;
        Class[] arry = Utils.getNmsClass("IChatBaseComponent").getDeclaredClasses();
        if (arry.length == 0) {
            title = Utils.getAndInvokeMethod(Utils.getNmsClass("ChatSerializer"), "a",
                    new Class[] { String.class }, null, new Object[] { "{\"text\":\" " + text + " " + "\"}" });
        } else {
            title = Utils.getAndInvokeMethod(Utils.getNmsClass("IChatBaseComponent").getDeclaredClasses()[0], "a",
                    new Class[] { String.class }, null, new Object[] { "{\"text\":\" " + text + " " + "\"}" });
        }
        Constructor<?> titleConstructor = Utils.getConstructor(Utils.getNmsClass("PacketPlayOutTitle"), new Class[] { Utils.getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                Utils.getNmsClass("IChatBaseComponent"), int.class, int.class, int.class });
        Object packetTitle = Utils.callConstructor(titleConstructor, new Object[] { Utils.getEnumConstant(
                Utils.getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], "TITLE"),
                title,
                Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut) });
        Utils.sendPacket(player, packetTitle);
    }

    public static void displayTitleAndSubtitle(Player player, String titleText, String subTitleText, int fadeIn, int stay, int fadeOut) {
        displayTitle(player, titleText, fadeIn, stay, fadeOut);
        displaySubTitle(player, subTitleText, fadeIn, stay, fadeOut);
    }

    public static void customError(Exception e, boolean printStack) {
        if (printStack)
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "------------------------------");
        e.printStackTrace();
        if (printStack)
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "------------------------------");
    }

    public static void displayProgress(String prefix, double amount, String suffix, boolean progressDirectionSwap, Player... players) {
        String str = "";
        if (progressDirectionSwap)
            amount = 1.0D - amount;
        ChatColor chatColor = ChatColor.GREEN;
        boolean colorChange = false;
        for (int i = 0; i < 23; i++) {
            if (!colorChange && (i / 24.0F) >= amount) {
                str = String.valueOf(chatColor) + ChatColor.GREEN + ChatColor.BOLD;
                colorChange = true;
            }
            str = str + " |";
        }
        byte b;
        int j;
        Player[] arrayOfPlayer;
        for (j = (arrayOfPlayer = players).length, b = 0; b < j; ) {
            Player player = arrayOfPlayer[b];
            display(((prefix == null) ? "" : (prefix + ChatColor.RESET + " ")) + str + (
                    (suffix == null) ? "" : (suffix + ChatColor.RESET + " ")), player);
            b++;
        }
    }
}

