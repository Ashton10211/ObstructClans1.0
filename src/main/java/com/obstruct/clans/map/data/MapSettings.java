package com.obstruct.clans.map.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MapSettings {

    private final Set<ChunkData> clanMapData;
    private boolean update;
    private Scale scale;
    private int mapX;
    private int mapZ;
    protected long lastUpdated;

    public MapSettings(int lastX, int lastZ) {
        this.clanMapData = new HashSet<>();
        this.update = false;
        this.scale = Scale.CLOSEST;
        this.lastUpdated = System.currentTimeMillis();
        this.mapX = lastX;
        this.mapZ = lastZ;
    }

    public enum Scale {
        CLOSEST(0),
        CLOSE(1),
        NORMAL(2),
        FAR(3),
        FARTHEST(4);

        private final byte value;


        Scale(int value) {
            this.value = (byte) value;
        }


        public byte getValue() {
            return this.value;
        }
    }

    public Scale setScale(Scale scale) {
        this.scale = scale;
        return scale;
    }
}
