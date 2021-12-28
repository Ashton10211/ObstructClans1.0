package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.Listener;

public class ClanSkillListener extends SpigotModule<ClanManager> implements Listener {

    public ClanSkillListener(ClanManager manager) {
        super(manager, "ClanSkillListener");
    }

//    @EventHandler
//    public void onSkillActivate(SkillActivateEvent event) {
//        Clan clan = ((ClanManager)getManager()).getClan(event.getPlayer().getLocation());
//        if (clan != null && clan.isAdmin() && clan.isSafe(event.getPlayer().getLocation())) {
//            UtilMessage.message(event.getPlayer(), "Restriction", "You are not allowed to cast abilities here!");
//            event.setCancelled(true);
//        }
//    }
//
//    @EventHandler
//    public void onSkillBlockEffect(SkillChangeBlockEvent event) {
//        Clan clan = ((ClanManager)getManager()).getClan(event.getBlock().getLocation());
//        if (clan != null && clan.isAdmin() && clan.isSafe(event.getBlock().getLocation())) {
//            event.setCancelled(true);
//        }
//    }
//
//    @EventHandler
//    public void onSkillEntityEffect(SkillEffectEntityEvent event) {
//        Clan clan = ((ClanManager)getManager()).getClan(event.getPlayer());
//        if (clan == null) {
//            return;
//        }
//        for (Player member : clan.getOnlinePlayers()) {
//            event.getFriendlyEntities().add(member);
//        }
//        for (String c : clan.getAllianceMap().keySet()) {
//            for (Player player : ((ClanManager)getManager()).getClan(c).getOnlinePlayers())
//                event.getFriendlyEntities().add(player);
//        }
//    }
}
