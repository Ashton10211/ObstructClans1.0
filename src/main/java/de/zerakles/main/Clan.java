package de.zerakles.main;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.alligatorstooth.AlligatorsToothListener;
import de.zerakles.clanapi.legendaries.giantbroadsword.GiantBroadSwordListener;
import de.zerakles.clanapi.legendaries.hyperaxe.HyperAxeListener;
import de.zerakles.clanapi.legendaries.lance.LanceListener;
import de.zerakles.clanapi.legendaries.magneticblade.MagneticMaulListener;
import de.zerakles.clanapi.legendaries.meridianscepter.MerdianScepter;
import de.zerakles.clanapi.legendaries.meridianscepter.MerdianScepterListener;
import de.zerakles.clanapi.legendaries.runedpickaxe.RunedPickaxeListener;
import de.zerakles.clanapi.legendaries.sxytheofthefallenlord.ScytheOfTheFallenLordListener;
import de.zerakles.clanapi.legendaries.windblade.WindBladeListener;
import de.zerakles.clanapi.quests.QuestListener;
import de.zerakles.clanapi.raids.Maze.Maze;
import de.zerakles.clanapi.raids.alter.alter;
import de.zerakles.cmds.*;
import de.zerakles.config.Config;
import de.zerakles.config.Shop;
import de.zerakles.config.VillagerLoader;
import de.zerakles.listener.*;
import de.zerakles.mysql.MySQL;
import de.zerakles.utils.Data;
import de.zerakles.utils.ScoreboardMaster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Clan extends JavaPlugin {

    private static Clan clan;
    private static ClanAPI clanAPI;
    private Config config = new Config();
    public Shop shop = new Shop();

    public VillagerLoader villagerLoader = new VillagerLoader();

    public Data data;

    public static Clan getClan() {
        return clan;
    }

    public ClanAPI getClanAPI(){
        return clanAPI;
    }

    public MySQL mySQL;
    public AlligatorsToothListener alligatorsToothListener;
    public GiantBroadSwordListener giantbroadswordListener;
    public HyperAxeListener hyperAxeListener;
    public MagneticMaulListener magneticMaul;
    public RunedPickaxeListener runedPickaxe;
    public WindBladeListener windBladeListener;
    public ScytheOfTheFallenLordListener scytheOfTheFallenLordListener;
    public MerdianScepterListener meridianscepter;
    public LanceListener lanceListener;

    public ScoreboardMaster scoreboardMaster = new ScoreboardMaster();
    @Override
    public void onEnable() {
        clan = this;
        data = new Data();
        villagerLoader.loadConfig();
        config.loadConfig();
        shop.loadConfig();
        mySQL = new MySQL(data.host, data.port, data.database, data.user, data.password);
        clanAPI = new ClanAPI();
        clanAPI.startChunkClearer();
        clanAPI.startVillagerChecker();
        getClanAPI().createTable();
        alligatorsToothListener = new AlligatorsToothListener();
        giantbroadswordListener = new GiantBroadSwordListener();
        hyperAxeListener = new HyperAxeListener();
        magneticMaul = new MagneticMaulListener();
        runedPickaxe = new RunedPickaxeListener();
        windBladeListener = new WindBladeListener();
        scytheOfTheFallenLordListener = new ScytheOfTheFallenLordListener();
        meridianscepter = new MerdianScepterListener();
        lanceListener = new LanceListener();
        loadCommands();
        loadListeners();
        super.onEnable();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for(Player all: Bukkit.getOnlinePlayers()){
                    scoreboardMaster.sendScoreboard(all);
                }
            }
        },0,20*60*3);
    }

    @Override
    public void onDisable() {
        if(data.suppliesBoy == null){
            return;
        }
        for (Villager villager: data.TravelBoys){
            villager.damage(40);
        }
        data.suppliesBoy.damage(40);
        data.pvpBoy.damage(40);
        data.organicBoy.damage(40);
        data.miningBoy.damage(40);
        data.bankBoy.damage(40);
        data.questBoy.damage(40);

        super.onDisable();
    }

    private void loadCommands(){
        getCommand("clan").setExecutor(new ClanCommand());
        getCommand("c").setExecutor(new ClanChat());
        getCommand("a").setExecutor(new AcceptCommand());
        getCommand("ac").setExecutor(new AllyChat());
        getCommand("shop").setExecutor(new ShopCMD());
        getCommand("setup").setExecutor(new SetupCMD());
        getCommand("legendary").setExecutor(new LegendaryCMD());
    }

    private void loadListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryMasterListener(), getClan());
        pm.registerEvents(new JoinEvent(), getClan());
        pm.registerEvents(new BreakListener(), getClan());
        pm.registerEvents(new ChatListener(), getClan());
        pm.registerEvents(new DamageHandler(), getClan());
        pm.registerEvents(new MovementListener(), getClan());
        pm.registerEvents(new VillagerListener(), getClan());
        pm.registerEvents(new BankShopListener(), getClan());
        pm.registerEvents(new QuestListener(), getClan());

        pm.registerEvents(new SupplieShopListener(), getClan());
        pm.registerEvents(new OrganicShopListener(), getClan());
        pm.registerEvents(new MiningShopListener(), getClan());
        pm.registerEvents(new PvpGearShopListener(), getClan());
        pm.registerEvents(new TravelLIstener(), getClan());
        pm.registerEvents(new SoupListener(), getClan());
        pm.registerEvents(new alter(), getClan());
        pm.registerEvents(new Maze(), getClan());


        pm.registerEvents(alligatorsToothListener, getClan());
        pm.registerEvents(giantbroadswordListener, getClan());
        pm.registerEvents(hyperAxeListener, getClan());
        pm.registerEvents(magneticMaul, getClan());
        pm.registerEvents(runedPickaxe, getClan());
        pm.registerEvents(windBladeListener, getClan());
        pm.registerEvents(scytheOfTheFallenLordListener, getClan());
        pm.registerEvents(meridianscepter, getClan());
        pm.registerEvents(lanceListener, getClan());
    }
}
