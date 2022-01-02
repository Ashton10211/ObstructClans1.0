package com.obstruct.clans;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.commands.AllyChatCommandManager;
import com.obstruct.clans.clans.commands.ClanChatCommandManager;
import com.obstruct.clans.clans.commands.ClansCommandManager;
import com.obstruct.clans.game.GameManager;
import com.obstruct.clans.gamer.GamerManager;
import com.obstruct.clans.map.MapManager;
import com.obstruct.clans.map.commands.MapCommandManager;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.clans.rune.commands.RuneCommandManager;
import com.obstruct.clans.scoreboard.ClanScoreboard;
import com.obstruct.clans.shops.ShopManager;
import com.obstruct.clans.shops.commands.ShopCommandManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.scoreboard.ScoreboardManager;

public class Clans extends SpigotBasePlugin {
    @Override
    public void registerManagers() {
        addManager(new ClanManager(this));
        addManager(new MapManager(this));
        addManager(new GamerManager(this));
        addManager(new GameManager(this));
        addManager(new ShopManager(this));
        addManager(new RuneManager(this));
        addManager(new SiegeManager(this));

        addManager(new RuneCommandManager(this));
        addManager(new ClansCommandManager(this));
        addManager(new ClanChatCommandManager(this));
        addManager(new AllyChatCommandManager(this));
        addManager(new ShopCommandManager(this));
        addManager(new MapCommandManager(this));

        addModuleToManager(new ClanScoreboard(getManager(ScoreboardManager.class)));
    }
}