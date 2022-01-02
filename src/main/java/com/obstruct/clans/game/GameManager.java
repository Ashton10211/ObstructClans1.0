package com.obstruct.clans.game;

import com.obstruct.clans.game.listeners.*;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;

public class GameManager extends SpigotManager<SpigotModule<?>> {

    public GameManager(SpigotBasePlugin plugin) {
        super(plugin, "GameManager");
    }

    @Override
    public void registerModules() {
        addModule(new CustomDayNight(this));
        addModule(new DisableAnvil(this));
        addModule(new DisableBeacon(this));
        addModule(new DisableBedrock(this));
        addModule(new DisableBlockBurn(this));
        addModule(new DisableBlockIgnite(this));
        addModule(new DisableBoneMeal(this));
        addModule(new DisableBrewing(this));
        addModule(new DisableBucket(this));
        addModule(new DisableCraftingBow(this));
        addModule(new DisableCraftingCompass(this));
        addModule(new DisableCraftingDispenser(this));
        addModule(new DisableCraftingFishingRod(this));
        addModule(new DisableCraftingGoldenApple(this));
        addModule(new DisableCraftingPiston(this));
        addModule(new DisableCraftingTNT(this));
        addModule(new DisableCreatureSpawn(this));
        addModule(new DisableDispenser(this));
        addModule(new DisableEnderChest(this));
        addModule(new DisableObsidian(this));
        addModule(new DisablePlacingSkyChest(this));
        addModule(new DisableSandGravel(this));
        addModule(new DisableWeather(this));
        addModule(new DisableWoodenDoors(this));
        addModule(new DisableWoolFallDamage(this));
        addModule(new HandleAnimalDrops(this));
        addModule(new HandleArrowDespawn(this));
        addModule(new HandleBlockPlaceHeight(this));
        addModule(new HandleCraftingIronDoors(this));
        addModule(new HandleItemNameChange(this));
        addModule(new HandleShootingBow(this));
        addModule(new InstantRespawn(this));
        addModule(new LimitCreatureSpawns(this));
        addModule(new WaterBlock(this));
        addModule(new WebBreak(this));
    }
}