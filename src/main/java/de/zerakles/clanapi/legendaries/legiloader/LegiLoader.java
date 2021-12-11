package de.zerakles.clanapi.legendaries.legiloader;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.clanapi.legendaries.alligatorstooth.AlligatorsToothListener;
import de.zerakles.clanapi.legendaries.giantbroadsword.GiantBroadSwordListener;
import de.zerakles.clanapi.legendaries.hyperaxe.HyperAxeListener;
import de.zerakles.clanapi.legendaries.lance.LanceListener;
import de.zerakles.clanapi.legendaries.magneticblade.MagneticMaulListener;
import de.zerakles.clanapi.legendaries.meridianscepter.MerdianScepterListener;
import de.zerakles.clanapi.legendaries.runedpickaxe.RunedPickaxeListener;
import de.zerakles.clanapi.legendaries.sxytheofthefallenlord.ScytheOfTheFallenLordListener;
import de.zerakles.clanapi.legendaries.windblade.WindBladeListener;
import de.zerakles.main.Clan;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class LegiLoader implements Listener {

    private boolean doubleLegi;

    private UUID loaderUuid;

    private void sendError(String Error){
        Bukkit.getConsoleSender().sendMessage(Clan.getClan().data.prefix +"§c" +Error);
        return;
    }

    private void sendInfo(String Info){
        Bukkit.getConsoleSender().sendMessage(Clan.getClan().data.prefix +"§e" +Info);
        return;
    }

    private void sendSuccess(String Success){
        Bukkit.getConsoleSender().sendMessage(Clan.getClan().data.prefix +"§a" +Success);
        return;
    }

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

    public void saveLegendary(){
        saveA();
        saveGi();
        saveHy();
        saveLa();
        saveMa();
        saveMe();
        saveRu();
        saveSc();
        saveWi();
    }

    private void saveA() {
        AlligatorsToothListener alligatorsToothListener = Clan.getClan().alligatorsToothListener;
        for (Player player : alligatorsToothListener.AlligatorThooths.keySet()
             ) {
            AlligatorsTooths.put(alligatorsToothListener.AlligatorThooths.get(player), player);
            sendInfo("Find " + alligatorsToothListener.AlligatorThooths.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveGi() {
        GiantBroadSwordListener alligatorsToothListener = Clan.getClan().giantbroadswordListener;
        for (Player player : alligatorsToothListener.GiantBroadSwords.keySet()
        ) {
            GiantBroadSwords.put(alligatorsToothListener.GiantBroadSwords.get(player), player);
            sendInfo("Find " + alligatorsToothListener.GiantBroadSwords.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveHy() {
        HyperAxeListener hyperAxeListener = Clan.getClan().hyperAxeListener;
        for(Player player : hyperAxeListener.HyperAxes.keySet()){
            HyperAxes.put(hyperAxeListener.HyperAxes.get(player), player);
            sendInfo("Find " + hyperAxeListener.HyperAxes.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveLa() {
        LanceListener lance = Clan.getClan().lanceListener;
        for(Player player : lance.Lances.keySet()){
            Lances.put(lance.Lances.get(player), player);
            sendInfo("Find " + lance.Lances.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveMa() {
        MagneticMaulListener hyperAxeListener = Clan.getClan().magneticMaul;
        for(Player player : hyperAxeListener.MagneticMauls.keySet()){
            MagneticMauls.put(hyperAxeListener.MagneticMauls.get(player), player);
            sendInfo("Find " + hyperAxeListener.MagneticMauls.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveMe() {
        MerdianScepterListener hyperAxeListener = Clan.getClan().meridianscepter;
        for(Player player : hyperAxeListener.Scepter.keySet()){
            MeridianScepters.put(hyperAxeListener.Scepter.get(player), player);
            sendInfo("Find " + hyperAxeListener.Scepter.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveRu() {
        RunedPickaxeListener hyperAxeListener = Clan.getClan().runedPickaxe;
        for(Player player : hyperAxeListener.RunedPickaxes.keySet()){
            RunedPickAxes.put(hyperAxeListener.RunedPickaxes.get(player), player);
            sendInfo("Find " + hyperAxeListener.RunedPickaxes.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveSc() {
        ScytheOfTheFallenLordListener hyperAxeListener = Clan.getClan().scytheOfTheFallenLordListener;
        for(Player player : hyperAxeListener.ScytheOfs.keySet()){
            Scyth.put(hyperAxeListener.ScytheOfs.get(player), player);
            sendInfo("Find " + hyperAxeListener.ScytheOfs.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    private void saveWi() {
        WindBladeListener hyperAxeListener = Clan.getClan().windBladeListener;
        for(Player player : hyperAxeListener.WindBlades.keySet()){
            WindBlade.put(hyperAxeListener.WindBlades.get(player), player);
            sendInfo("Find " + hyperAxeListener.WindBlades.get(player).getName()
                    + " for " + player.getName() + ".");
            continue;
        }
        return;
    }

    public HashMap<Legend, Player> AlligatorsTooths = new HashMap<>();
    public HashMap<Legend, Player> GiantBroadSwords = new HashMap<>();
    public HashMap<Legend, Player> HyperAxes = new HashMap<>();
    public HashMap<Legend, Player> Lances = new HashMap<>();
    public HashMap<Legend, Player> MagneticMauls = new HashMap<>();
    public HashMap<Legend, Player> MeridianScepters = new HashMap<>();
    public HashMap<Legend, Player> RunedPickAxes = new HashMap<>();
    public HashMap<Legend, Player> Scyth = new HashMap<>();
    public HashMap<Legend, Player> WindBlade = new HashMap<>();

    public HashMap<Legend, String> AlligatorsToothsS = new HashMap<>();
    public HashMap<Legend, String> GiantBroadSwordsS = new HashMap<>();
    public HashMap<Legend, String> HyperAxesS = new HashMap<>();
    public HashMap<Legend, String> LancesS = new HashMap<>();
    public HashMap<Legend, String> MagneticMaulsS = new HashMap<>();
    public HashMap<Legend, String> MeridianSceptersS = new HashMap<>();
    public HashMap<Legend, String> RunedPickAxesS = new HashMap<>();
    public HashMap<Legend, String> ScythS = new HashMap<>();
    public HashMap<Legend, String> WindBladeS = new HashMap<>();
}
