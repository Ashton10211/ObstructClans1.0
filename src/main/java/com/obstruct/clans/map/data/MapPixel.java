package com.obstruct.clans.map.data;

public class MapPixel {
    protected byte color;
    private short averageY;

    public MapPixel(byte color, short averageY) {
        this.color = color;
        this.averageY = averageY;
    }


    public short getAverageY() {
        return this.averageY;
    }


    public byte getColor() {
        return this.color;
    }


    public void setColor(byte color) {
        this.color = color;
    }


    public void setAverageY(short averageY) {
        this.averageY = averageY;
    }
}
