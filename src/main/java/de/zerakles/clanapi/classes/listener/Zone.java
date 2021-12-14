package de.zerakles.clanapi.classes.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Zone {

    private Location location;

    private Player player;

    private String clanName;

    public int liveTime;

    public Zone(Location location, Player player, String clanName, int liveTime){
        this.location = location;
        this.player = player;
        this.clanName = clanName;
        this.liveTime = liveTime;
        return;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public Location getLocation() {
        return location;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
