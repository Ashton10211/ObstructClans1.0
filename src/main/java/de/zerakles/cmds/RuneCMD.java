package de.zerakles.cmds;


import de.zerakles.clanapi.ImmortalPackage.ImmortalChest;
import de.zerakles.clanapi.RuneSystem.Reinforced.Reinforced;
import de.zerakles.clanapi.legendaries.alligatorstooth.Alligatorstooth;
import de.zerakles.clanapi.legendaries.giantbroadsword.GiantBroadSword;
import de.zerakles.clanapi.legendaries.hyperaxe.HyperAxe;
import de.zerakles.clanapi.legendaries.lance.Lance;
import de.zerakles.clanapi.legendaries.magneticblade.MagneticMaul;
import de.zerakles.clanapi.legendaries.meridianscepter.MerdianScepter;
import de.zerakles.clanapi.legendaries.runedpickaxe.RunedPickaxe;
import de.zerakles.clanapi.legendaries.sxytheofthefallenlord.ScytheOfTheFallenLord;
import de.zerakles.clanapi.legendaries.windblade.WindBlade;
import de.zerakles.main.Clan;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RuneCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("clan.runes")) {
            if (args.length == 0) {
                sender.sendMessage(Clan.getClan().data.prefix + "§e/rune Reinforced");
                sender.sendMessage(Clan.getClan().data.prefix + "§e/rune Immortal");

            }
            if (args.length == 1) {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("Reinforced")) {
                        Reinforced reinforced = new Reinforced(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(reinforced.rune.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Immortal")) {
                        ImmortalChest ImmortalChest = new ImmortalChest(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(ImmortalChest.rune.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("HyperAxe")) {
                        HyperAxe hyperAxe = new HyperAxe(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(hyperAxe.legend.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("MagneticMaul")) {
                        MagneticMaul magneticMaul = new MagneticMaul(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(magneticMaul.legend.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("RunedPickaxe")) {
                        RunedPickaxe runedPickaxe = new RunedPickaxe(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(runedPickaxe.legend.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Windblade")) {
                        WindBlade windBlade = new WindBlade(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(windBlade.legend.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("ScytheOfTheFallenLord")) {
                        ScytheOfTheFallenLord scytheOfTheFallenLord = new ScytheOfTheFallenLord(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(scytheOfTheFallenLord.legend.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("MeridianScepter")) {
                        MerdianScepter merdianScepter = new MerdianScepter(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(merdianScepter.legend.getItemStack());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Lance")) {
                        Lance lance = new Lance(((Player) sender).getPlayer());
                        ((Player) sender).getInventory().addItem(lance.legend.getItemStack());
                        return true;
                    }
                    sender.sendMessage(Clan.getClan().data.prefix + "§e/rune Reinforced");
                }
            }
        }
        return false;
    }
}
