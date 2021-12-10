package de.zerakles.clanapi.legendaries.legiloader;

import de.zerakles.clanapi.legendaries.Legend;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class LegiLoader {

    private boolean doubleLegi;

    private UUID loaderUuid;

    public LegiLoader(UUID loaderUuid, boolean doubleLegi){
        this.doubleLegi = doubleLegi;
        this.loaderUuid = loaderUuid;
        return;
    }

    public boolean isDoubleLegi() {
        return doubleLegi;
    }

    public UUID getLoaderUuid() {
        return loaderUuid;
    }

    public void setDoubleLegi(boolean doubleLegi) {
        this.doubleLegi = doubleLegi;
    }

    public void setLoaderUuid(UUID loaderUuid) {
        this.loaderUuid = loaderUuid;
    }

    public HashMap<Legend, Player> AlligatorsTooths = new HashMap<>();
}
