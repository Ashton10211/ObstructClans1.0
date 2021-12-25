package com.obstruct.clans.map.data;

public class Coords {
    private final int x;
    private final int z;
    private final String world;

    public Coords(int x, int z, String world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }


    public int getX() {
        return this.x;
    }


    public int getZ() {
        return this.z;
    }


    public String getWorld() {
        return this.world;
    }
}
