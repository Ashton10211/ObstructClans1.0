package de.zerakles.clanapi.quests;



        import de.zerakles.clanapi.ClanAPI;
        import de.zerakles.main.Clan;
        import de.zerakles.utils.Data;
        import org.bukkit.Bukkit;

        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.player.PlayerInteractEntityEvent;


public class quests implements Listener {

    private Data getData(){
        return Clan.getClan().data;
    }

    private  Clan getClan(){
        return Clan.getClan();
    }

    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }

    String ShopName = "§c§lQuest Manager";

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Bukkit.getServer().broadcastMessage(event.getRightClicked().getCustomName());
        if(event.getRightClicked().getCustomName().contains(ShopName)) {
            Bukkit.getServer().broadcastMessage("Test 1");
            Player player = event.getPlayer();
            Bukkit.getServer().broadcastMessage("Test 2");

            event.setCancelled(true);
            Bukkit.getServer().broadcastMessage("Test 3");


            }
        }
    }


