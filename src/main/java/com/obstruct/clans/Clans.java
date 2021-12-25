package com.obstruct.clans;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.commands.ClansCommandManager;
import com.obstruct.clans.map.MapManager;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.clans.scoreboard.ClanScoreboard;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.scoreboard.ScoreboardManager;

public class Clans extends SpigotBasePlugin {
    @Override
    public void registerManagers() {
        addManager(new ClanManager(this));
        addManager(new MapManager(this));

        addManager(new ClansCommandManager(this));
        addManager(new PillageManager(this));

        addModuleToManager(new ClanScoreboard(getManager(ScoreboardManager.class)));
    }
}