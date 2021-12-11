package de.zerakles.clanapi.legendaries.legiloader;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.clanapi.legendaries.alligatorstooth.AlligatorsToothListener;
import de.zerakles.clanapi.legendaries.giantbroadsword.GiantBroadSwordListener;
import de.zerakles.clanapi.legendaries.hyperaxe.HyperAxeListener;
import de.zerakles.clanapi.legendaries.lance.LanceListener;
import de.zerakles.clanapi.legendaries.magneticblade.MagneticMaulListener;
import de.zerakles.clanapi.legendaries.meridianscepter.MerdianScepterListener;
import de.zerakles.clanapi.legendaries.runedpickaxe.RunedPickaxeListener;
import de.zerakles.clanapi.legendaries.sxytheofthefallenlord.ScytheOfTheFallenLord;
import de.zerakles.clanapi.legendaries.sxytheofthefallenlord.ScytheOfTheFallenLordListener;
import de.zerakles.clanapi.legendaries.windblade.WindBladeListener;
import de.zerakles.main.Clan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        sendInfo("LegiLoader " + loaderUuid + " loaded!");
        return;
    }

    public void loadLegis(){
        checkLegi(AlligatorsTooths, AlligatorsToothsS, "Alligator");
        checkLegi(GiantBroadSwords, GiantBroadSwordsS, "Giant");
        checkLegi(HyperAxes, HyperAxesS, "Hyper");
        checkLegi(Lances, LancesS, "Lance");
        checkLegi(MagneticMauls, MagneticMaulsS, "Magnetic");
        checkLegi(MeridianScepters, MeridianSceptersS, "Meridian");
        checkLegi(RunedPickAxes, RunedPickAxesS, "Runed");
        checkLegi(Scyth, ScythS, "Scyth");
        checkLegi(WindBlade, WindBladeS, "Windblade");
    }

    private Clan getClan(){
        return Clan.getClan();
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent playerDropItemEvent){
        ItemStack itemStack = playerDropItemEvent.getItem().getItemStack();
        Player player = playerDropItemEvent.getPlayer();
        if(itemStack.hasItemMeta()){
            if(itemStack.getItemMeta().hasDisplayName()){
                for (Legend legend: getClan().alligatorsToothListener.AlligatorThooths.keySet()
                     ) {
                    if(isLegend(legend, itemStack)){
                        getClan().alligatorsToothListener.AlligatorThooths.remove(legend);
                        getClan().alligatorsToothListener.AlligatorThooths.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().giantbroadswordListener.GiantBroadSwords.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().giantbroadswordListener.GiantBroadSwords.remove(legend);
                        getClan().giantbroadswordListener.GiantBroadSwords.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().hyperAxeListener.HyperAxes.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().hyperAxeListener.HyperAxes.remove(legend);
                        getClan().hyperAxeListener.HyperAxes.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().lanceListener.Lances.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().lanceListener.Lances.remove(legend);
                        getClan().lanceListener.Lances.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().magneticMaul.MagneticMauls.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().magneticMaul.MagneticMauls.remove(legend);
                        getClan().magneticMaul.MagneticMauls.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().meridianscepter.Scepter.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().meridianscepter.Scepter.remove(legend);
                        getClan().meridianscepter.Scepter.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().runedPickaxe.RunedPickaxes.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().runedPickaxe.RunedPickaxes.remove(legend);
                        getClan().runedPickaxe.RunedPickaxes.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().scytheOfTheFallenLordListener.ScytheOfs.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().scytheOfTheFallenLordListener.ScytheOfs.remove(legend);
                        getClan().scytheOfTheFallenLordListener.ScytheOfs.put(legend, player);
                        return;
                    }
                }
                for (Legend legend: getClan().windBladeListener.WindBlades.keySet()
                ) {
                    if(isLegend(legend, itemStack)){
                        getClan().windBladeListener.WindBlades.remove(legend);
                        getClan().windBladeListener.WindBlades.put(legend, player);
                        return;
                    }
                }
            }
        }
    }

    public boolean isLegend(Legend legend, ItemStack itemStack){
        if(legend.getItemStack().equals(itemStack)){
            return true;
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent){
        Player player = playerJoinEvent.getPlayer();
        String uuid = player.getUniqueId().toString();
        if(AlligatorsToothsS.size() > 0){
            for (Legend legend : AlligatorsToothsS.keySet()) {
                if(AlligatorsToothsS.get(legend).equals(uuid)){
                    Clan.getClan().alligatorsToothListener.AlligatorThooths.put(legend, player);
                    AlligatorsToothsS.remove(legend);
                    continue;
                }
            }
        }
        if(GiantBroadSwordsS.size() > 0){
            for (Legend legend : GiantBroadSwordsS.keySet()) {
                if(GiantBroadSwordsS.get(legend).equals(uuid)){
                    Clan.getClan().giantbroadswordListener.GiantBroadSwords.put(legend, player);
                    GiantBroadSwordsS.remove(legend);
                    continue;
                }
            }
        }
        if(HyperAxesS.size() > 0){
            for (Legend legend : HyperAxesS.keySet()) {
                if(HyperAxesS.get(legend).equals(uuid)){
                    Clan.getClan().hyperAxeListener.HyperAxes.put(legend, player);
                    HyperAxesS.remove(legend);
                    continue;
                }
            }
        }
        if(LancesS.size() > 0){
            for (Legend legend : LancesS.keySet()) {
                if(LancesS.get(legend).equals(uuid)){
                    Clan.getClan().lanceListener.Lances.put(legend, player);
                    LancesS.remove(legend);
                    continue;
                }
            }
        }
        if(MagneticMaulsS.size() > 0){
            for (Legend legend : MagneticMaulsS.keySet()) {
                if(MagneticMaulsS.get(legend).equals(uuid)){
                    Clan.getClan().magneticMaul.MagneticMauls.put(legend, player);
                    MagneticMaulsS.remove(legend);
                    continue;
                }
            }
        }
        if(MeridianSceptersS.size() > 0){
            for (Legend legend : MeridianSceptersS.keySet()) {
                if(MeridianSceptersS.get(legend).equals(uuid)){
                    Clan.getClan().meridianscepter.Scepter.put(legend, player);
                    MeridianSceptersS.remove(legend);
                    continue;
                }
            }
        }
        if(RunedPickAxesS.size() > 0){
            for (Legend legend : RunedPickAxesS.keySet()) {
                if(RunedPickAxesS.get(legend).equals(uuid)){
                    Clan.getClan().runedPickaxe.RunedPickaxes.put(legend, player);
                    RunedPickAxesS.remove(legend);
                    continue;
                }
            }
        }
        if(ScythS.size() > 0){
            for (Legend legend : ScythS.keySet()) {
                if(ScythS.get(legend).equals(uuid)){
                    Clan.getClan().scytheOfTheFallenLordListener.ScytheOfs.put(legend, player);
                    ScythS.remove(legend);
                    continue;
                }
            }
        }
        if(WindBladeS.size() > 0){
            for (Legend legend : WindBladeS.keySet()) {
                if(WindBladeS.get(legend).equals(uuid)){
                    Clan.getClan().windBladeListener.WindBlades.put(legend, player);
                    WindBladeS.remove(legend);
                    continue;
                }
            }
        }
    }

    private void checkLegi(HashMap<Legend, Player> alligatorsTooths, HashMap<Legend, String> alligatorsToothsS,
                           String string) {
        File file = new File(Clan.getClan().getDataFolder() + "/legis/" + string + ".yml" );
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            return;
        }

        int a = fileConfiguration.getInt("slegend.count");
        int b = fileConfiguration.getInt("legend.count");

        for(int z = 0; z < a; z++){
            String original = fileConfiguration.getString("slegend." + z + ".original");
            String holder = fileConfiguration.getString("slegend." + z + ".owner.uuid");
            if(string.equalsIgnoreCase("Alligator")){
                alligatorsToothsS.put(Alligatorstooth(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Giant")){
                alligatorsToothsS.put(Alligatorstooth(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Hyper")){
                alligatorsToothsS.put(HyperAxe(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Lance")){
                alligatorsToothsS.put(Lance(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Magnetic")){
                alligatorsToothsS.put(MagneticMaul(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Meridian")){
                alligatorsToothsS.put(MerdianScepter(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Runed")){
                alligatorsToothsS.put(RunedPickaxe(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Scyth")){
                alligatorsToothsS.put(ScytheOfTheFallenLord(original), holder);
                continue;
            }
            if(string.equalsIgnoreCase("Windblade")){
                alligatorsToothsS.put(WindBlade(original), holder);
                continue;
            }
        }

        for(int z = 0; z<b; z++){
            String original  = fileConfiguration.getString("legend." + z + ".original");
            String name = fileConfiguration.getString("legend." + z +".owner.name");
            String uuid = fileConfiguration.getString("legend." + z +".owner.uuid");
            if(string.equalsIgnoreCase("Alligator")){
                alligatorsToothsS.put(Alligatorstooth(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Giant")){
                alligatorsToothsS.put(Alligatorstooth(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Hyper")){
                alligatorsToothsS.put(HyperAxe(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Lance")){
                alligatorsToothsS.put(Lance(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Magnetic")){
                alligatorsToothsS.put(MagneticMaul(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Meridian")){
                alligatorsToothsS.put(MerdianScepter(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Runed")){
                alligatorsToothsS.put(RunedPickaxe(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Scyth")){
                alligatorsToothsS.put(ScytheOfTheFallenLord(original), uuid);
                continue;
            }
            if(string.equalsIgnoreCase("Windblade")){
                alligatorsToothsS.put(WindBlade(original), uuid);
                continue;
            }
        }
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

        legiToFile(AlligatorsTooths, AlligatorsToothsS, "Alligator");
        legiToFile(GiantBroadSwords, GiantBroadSwordsS, "Giant");
        legiToFile(HyperAxes, HyperAxesS, "Hyper");
        legiToFile(Lances, LancesS, "Lance");
        legiToFile(MagneticMauls, MagneticMaulsS, "Magnetic");
        legiToFile(MeridianScepters, MeridianSceptersS, "Meridian");
        legiToFile(RunedPickAxes, RunedPickAxesS, "Runed");
        legiToFile(Scyth, ScythS, "Scyth");
        legiToFile(WindBlade, WindBladeS, "Windblade");

        sendSuccess("All saved!");
        return;
    }

    private void legiToFile(HashMap<Legend,Player>legiHashMap,HashMap<Legend, String> legiStringHashmap, String string) {
        File file = new File(Clan.getClan().getDataFolder() + "/legis/" + string + ".yml" );
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        int i = 0;
        for (Legend legend: legiHashMap.keySet()
             ) {
            fileConfiguration.set("legend." + i + ".original", legend.getOriginal());
            fileConfiguration.set("legend." + i +".owner.name", legiHashMap.get(legend).getName());
            fileConfiguration.set("legend." + i +".owner.uuid", legiHashMap.get(legend).getUniqueId().toString());
            i++;
            continue;
        }
        fileConfiguration.set("legend.count", i);

        i = 0;
        for (Legend legend: legiStringHashmap.keySet()
        ) {
            fileConfiguration.set("slegend." + i + ".original", legend.getOriginal());
            fileConfiguration.set("slegend." + i +".owner.uuid", legiHashMap.get(legend).getUniqueId().toString());
            i++;
            continue;
        }
        fileConfiguration.set("slegend.count", i);
        try {
            fileConfiguration.save(file);
            sendSuccess(file.getAbsolutePath() + " saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void saveA() {
        AlligatorsToothListener alligatorsToothListener = Clan.getClan().alligatorsToothListener;
        for (Legend legend : alligatorsToothListener.AlligatorThooths.keySet()
             ) {
            AlligatorsTooths.put(legend, alligatorsToothListener.AlligatorThooths.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + alligatorsToothListener.AlligatorThooths.get(legend) + ".");
            continue;
        }
        return;
    }

    private void saveGi() {
        GiantBroadSwordListener alligatorsToothListener = Clan.getClan().giantbroadswordListener;
        for (Legend legend : alligatorsToothListener.GiantBroadSwords.keySet()
        ) {
            GiantBroadSwords.put(legend, alligatorsToothListener.GiantBroadSwords.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + alligatorsToothListener.GiantBroadSwords.get(legend).getName() + ".");
            continue;
        }
        return;
    }

    private void saveHy() {
        HyperAxeListener hyperAxeListener = Clan.getClan().hyperAxeListener;
        for(Legend legend : hyperAxeListener.HyperAxes.keySet()){
            HyperAxes.put(legend, hyperAxeListener.HyperAxes.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + hyperAxeListener.HyperAxes.get(legend) + ".");
            continue;
        }
        return;
    }

    private void saveLa() {
        LanceListener lance = Clan.getClan().lanceListener;
        for(Legend legend : lance.Lances.keySet()){
            Lances.put(legend, lance.Lances.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + lance.Lances.get(legend) + ".");
            continue;
        }
        return;
    }

    private void saveMa() {
        MagneticMaulListener hyperAxeListener = Clan.getClan().magneticMaul;
        for(Legend legend : hyperAxeListener.MagneticMauls.keySet()){
            MagneticMauls.put(legend, hyperAxeListener.MagneticMauls.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + hyperAxeListener.MagneticMauls.get(legend).getName() + ".");
            continue;
        }
        return;
    }

    private void saveMe() {
        MerdianScepterListener hyperAxeListener = Clan.getClan().meridianscepter;
        for(Legend legend : hyperAxeListener.Scepter.keySet()){
            MeridianScepters.put(legend, hyperAxeListener.Scepter.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + hyperAxeListener.Scepter.get(legend).getName() + ".");
            continue;
        }
        return;
    }

    private void saveRu() {
        RunedPickaxeListener hyperAxeListener = Clan.getClan().runedPickaxe;
        for(Legend legend : hyperAxeListener.RunedPickaxes.keySet()){
            RunedPickAxes.put(legend, hyperAxeListener.RunedPickaxes.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + hyperAxeListener.RunedPickaxes.get(legend).getName() + ".");
            continue;
        }
        return;
    }

    private void saveSc() {
        ScytheOfTheFallenLordListener hyperAxeListener = Clan.getClan().scytheOfTheFallenLordListener;
        for(Legend player : hyperAxeListener.ScytheOfs.keySet()){
            Scyth.put(player, hyperAxeListener.ScytheOfs.get(player));
            sendInfo("Find " + player.getName()
                    + " for " + hyperAxeListener.ScytheOfs.get(player).getName() + ".");
            continue;
        }
        return;
    }

    private void saveWi() {
        WindBladeListener hyperAxeListener = Clan.getClan().windBladeListener;
        for(Legend legend : hyperAxeListener.WindBlades.keySet()){
            WindBlade.put(legend, hyperAxeListener.WindBlades.get(legend));
            sendInfo("Find " + legend.getName()
                    + " for " + hyperAxeListener.WindBlades.get(legend).getName() + ".");
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

    public Legend Alligatorstooth(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 7);
        lore.add(" ");
        lore.add("§fThis deadly tooth was stolen from");
        lore.add("§fa best of reptillian beasts long");
        lore.add("§fago. Legends say that the holder");
        lore.add("§fis granted the underwater agility");
        lore.add("§fof an Alligator.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aswim.");
        ItemStack itemStack = new ItemStack(Material.RECORD_4);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lAlligators Tooth";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend GiantBroadSword(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 10);
        lore.add(" ");
        lore.add("§fForged in the godly mined of Plagieus");
        lore.add("§fthis sword has endured thousands of");
        lore.add("§fwars. It is sure to grant certain");
        lore.add("§fvictory in battle..");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §ashield.");
        ItemStack itemStack = new ItemStack(Material.GOLD_RECORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lGiant Broadsword";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 10;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend HyperAxe(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 6);
        lore.add(" ");
        lore.add("§fOf all the weapons known to man,");
        lore.add("§fnone is more prevalant than the");
        lore.add("§fHyper Axe. Infused with rabbit's");
        lore.add("§fspeed and pigman's ferocity, this");
        lore.add("§fblade can rip through any opponent.");
        lore.add("§Hit delay is reduced by§e 50%");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aDash.");
        ItemStack itemStack = new ItemStack(Material.RECORD_3);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lHyperAxe";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 6;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend Lance(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 7);
        lore.add(" ");
        lore.add("§fRelic of a bygone age.");
        lore.add("§fEmblazoned with cryptic runes, this");
        lore.add("§fLance bears the marks of its ancient master.");
        lore.add("§fYou feel him with you always:");
        lore.add("§fMeed his warnings and stave off the darkness.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aCharge.");
        ItemStack itemStack = new ItemStack(Material.RECORD_12);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lKnight's Greatlance";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend MagneticMaul(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 8);
        lore.add(" ");
        lore.add("§fOf all the weapons known to man,");
        lore.add("§fnone is more magnetic than the");
        lore.add("§fMagnetic Maul. Infused with a high level");
        lore.add("§f of electricity even the strongest");
        lore.add("§fguy can be pulled...");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aPull.");
        ItemStack itemStack = new ItemStack(Material.RECORD_5);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lMagnetic Maul";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 8;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid,original);
        return legend;
    }

    public Legend MerdianScepter(String  original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 3);
        lore.add(" ");
        lore.add("§fLegend says that this scepter");
        lore.add("§fwas found, and retrieved from");
        lore.add("§fthe deepest trench in all of");
        lore.add("§fMinecraftia. It is said that he");
        lore.add("§fwields this scepter holds");
        lore.add("§fthe power of Poseidon himself.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aScepter.");
        ItemStack itemStack = new ItemStack(Material.RECORD_6);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lMeridian Scepter";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 3;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend RunedPickaxe(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 3);
        lore.add(" ");
        lore.add("§fWhat an interesting design this");
        lore.add("§fpickaxe seems to have!");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aInstant Mine.");
        ItemStack itemStack = new ItemStack(Material.RECORD_7);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lRuned Pickaxe";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 3;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend ScytheOfTheFallenLord(String original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 8);
        lore.add(" ");
        lore.add("§fAn old blade fashioned of nothing more");
        lore.add("§fthan bones and cloth which served no");
        lore.add("§fpurpose. Brave adventurers however have");
        lore.add("§fimbued it with the remnant powers of a");
        lore.add("§fdark and powerful foe.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + original);
        lore.add("§f");
        lore.add("§eAttack §f to use §aFly.");
        ItemStack itemStack = new ItemStack(Material.RECORD_8);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lScythe of the Fallen Lord";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 8;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, original);
        return legend;
    }

    public Legend WindBlade(String Original) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 7);
        lore.add(" ");
        lore.add("§fLong ago, a race of cloud dwellers");
        lore.add("§fterrorized the skies. A remnant of!");
        lore.add("§ftheir tyranny, this airy blade is");
        lore.add("§fthe last surviving memorium from");
        lore.add("§ftheir final battle againts the Titans.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + Original);
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aFly.");
        ItemStack itemStack = new ItemStack(Material.GREEN_RECORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lWindblade";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        Legend legend;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, Original);
        return legend;
    }
}
