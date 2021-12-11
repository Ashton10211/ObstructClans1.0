package de.zerakles.utils;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ScoreboardMaster {

    private Clan getClan(){
        return Clan.getClan();
    }

    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }

    private Data getData(){
        return getClan().data;
    }

    public void sendScoreboard(Player player){
        Scoreboard board = new Scoreboard();
        ScoreboardObjective obj = board.registerObjective("clans", IScoreboardCriteria.b);
        obj.setDisplayName("§6§lClans §e§lAlpha");
        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective displayObjective = new PacketPlayOutScoreboardDisplayObjective(1, obj);

        ScoreboardScore s1 = new ScoreboardScore(board, obj, " ");

        ScoreboardScore s2 = new ScoreboardScore(board, obj, "§e§lClan");
        String clan = "§7No Clan";
        if(getClanAPI().playerExists(player.getUniqueId().toString())){
            clan = "§b"+getClanAPI().getClan(player.getUniqueId().toString());
        }
        ScoreboardScore s3 = new ScoreboardScore(board, obj, clan);
        ScoreboardScore s4 = new ScoreboardScore(board, obj, "  ");
        ScoreboardScore s5 = new ScoreboardScore(board, obj, "§6§lGold");

        int gold;
        if(getClanAPI().hasGold(player)){
             gold = getClanAPI().getGold(player);
        } else {
            gold = 0;
            getClanAPI().insertGold(player, 0);
            getClanAPI().insertDailyQuest(player, 0);
        }
        ScoreboardScore s6 = new ScoreboardScore(board, obj, "§e"+gold);
        ScoreboardScore s7 = new ScoreboardScore(board, obj, "   ");
        ScoreboardScore s8 = new ScoreboardScore(board, obj, "§e§lTerritory");
        ZoneTypes zoneTypes = getClanAPI().getZone(player.getLocation(), player);
        String zone = "";
        if(zoneTypes == ZoneTypes.SPAWN){
            zone = "§bSpawn";
        }
        if(zoneTypes == ZoneTypes.WILDERNESS){
            zone = "§7Wilderness";
        }
        if(zoneTypes == ZoneTypes.CLAN){
            zone = "§bClan";
        }
        if(zoneTypes == ZoneTypes.ENEMYCLAN){
            zone = "§cEnemy Clan";
        }
        if(zoneTypes == ZoneTypes.SHOP){
            zone = "§bShop";
        }

        ScoreboardScore s9 = new ScoreboardScore(board, obj, zone);
        ScoreboardScore s10 = new ScoreboardScore(board, obj, "    ");

        s1.setScore(9);
        s2.setScore(8);
        s3.setScore(7);
        s4.setScore(6);
        s5.setScore(5);
        s6.setScore(4);
        s7.setScore(3);
        s8.setScore(2);
        s9.setScore(1);
        s10.setScore(0);

        PacketPlayOutScoreboardScore ps1 = new PacketPlayOutScoreboardScore(s1);
        PacketPlayOutScoreboardScore ps2 = new PacketPlayOutScoreboardScore(s2);
        PacketPlayOutScoreboardScore ps3 = new PacketPlayOutScoreboardScore(s3);
        PacketPlayOutScoreboardScore ps4 = new PacketPlayOutScoreboardScore(s4);
        PacketPlayOutScoreboardScore ps5 = new PacketPlayOutScoreboardScore(s5);
        PacketPlayOutScoreboardScore ps6 = new PacketPlayOutScoreboardScore(s6);
        PacketPlayOutScoreboardScore ps7 = new PacketPlayOutScoreboardScore(s7);
        PacketPlayOutScoreboardScore ps8 = new PacketPlayOutScoreboardScore(s8);
        PacketPlayOutScoreboardScore ps9 = new PacketPlayOutScoreboardScore(s9);
        PacketPlayOutScoreboardScore ps10 = new PacketPlayOutScoreboardScore(s10);
        sendPacket(removePacket,player);
        sendPacket(createPacket, player);
        sendPacket(displayObjective, player);
        sendPacket(ps1, player);
        sendPacket(ps2, player);
        sendPacket(ps3, player);
        sendPacket(ps4, player);
        sendPacket(ps5, player);
        sendPacket(ps6, player);
        sendPacket(ps7, player);
        sendPacket(ps8, player);
        sendPacket(ps9, player);
        sendPacket(ps10, player);
        return;
    }

    private void sendPacket(Packet<?> packet, Player player){
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        return;
    }

}
