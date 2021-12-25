package com.obstruct.clans.map.event;

import com.obstruct.clans.map.data.ExtraCursor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.map.MapCursorCollection;

import java.util.ArrayList;
import java.util.List;

public class MinimapExtraCursorEvent
        extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final MapCursorCollection cursors;

    public MinimapExtraCursorEvent(Player player, MapCursorCollection cursors, int scale) {
        this.cursor = new ArrayList();


        this.player = player;
        this.cursors = cursors;
        this.scale = scale;
    }

    private final int scale;
    private final List<ExtraCursor> cursor;

    public int getScale() {
        return this.scale;
    }


    public MapCursorCollection getCursorCollection() {
        return this.cursors;
    }


    public List<ExtraCursor> getCursors() {
        return this.cursor;
    }


    public Player getPlayer() {
        return this.player;
    }


    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}
