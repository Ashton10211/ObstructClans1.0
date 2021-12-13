package de.zerakles.clanapi.ImmortalPackage;

import de.zerakles.clanapi.legendaries.giantbroadsword.GiantBroadSword;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import de.zerakles.clanapi.legendaries.giantbroadsword.GiantBroadSword;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ImmortalChestListener implements Listener {


    @EventHandler




    public void onPlayerClick(PlayerInteractEvent event){
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§aReinforced Rune");
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§6Ancient Rune";
        UUID uuid = UUID.randomUUID();
        lore.add("§e" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        List<String> lore2 = new ArrayList<>();
        lore2.add(ChatColor.LIGHT_PURPLE + "This is a Immortal Package, ");
        lore2.add(ChatColor.LIGHT_PURPLE + "you have a chance to obtain special abilities ");
        lore2.add("§e" + uuid);
        ItemStack stack = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Immortal Packages");
        meta.setLore(lore2);
        stack.setItemMeta(meta);
        Player player = event.getPlayer();
        Random rand = new Random();
        int  n = rand.nextInt(100) + 100;
        System.out.println("This is a  test3");
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
            System.out.println("This is a  test3");
        if (player.getItemInHand().getItemMeta().equals(stack)) {
            System.out.println("This is a  test");
            player.getInventory().remove(stack);
            System.out.println("This is a  test2");
            if (n > 100)

                player.getInventory().addItem(itemStack);
            player.sendMessage(ChatColor.RED + "Packages> " + ChatColor.YELLOW + "You have been given a Ancient Rune ");
        } else {
            player.sendMessage(ChatColor.RED + "Packages> " + ChatColor.YELLOW + "You have been given a -0.5 Reinforced Knight Set");        }
    }
}



