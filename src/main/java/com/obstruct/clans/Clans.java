package com.obstruct.clans;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.commands.ClansCommandManager;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;

public class Clans extends SpigotBasePlugin {
    @Override
    public void registerManagers() {
        addManager(new ClanManager(this));

        addManager(new ClansCommandManager(this));
        addManager(new PillageManager(this));
    }
}