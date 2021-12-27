package com.obstruct.clans.pillage;

import com.obstruct.clans.clans.Clan;

public class Siege {

    private final Clan pillager;
    private final Clan pillagee;
    private final long start;
    private long lastAnnounce;
    private final long length;

    public Siege(Clan pillager, Clan pillagee, long length) {
        this.start = System.currentTimeMillis();
        this.lastAnnounce = System.currentTimeMillis();
        this.pillager = pillager;
        this.pillagee = pillagee;
        this.length = length;
    }

    public Clan getPillager() {
        return this.pillager;
    }

    public Clan getPillagee() {
        return this.pillagee;
    }

    public long getStart() {
        return this.start;
    }

    public long getLastAnnounce() {
        return this.lastAnnounce;
    }

    public void setLastAnnounce(long lastAnnounce) {
        this.lastAnnounce = lastAnnounce;
    }

    public long getTimeRemaining() {
        return Math.max(0L, getStart() + getLength() - System.currentTimeMillis());
    }

    public long getLength() {
        return this.length;
    }
}
