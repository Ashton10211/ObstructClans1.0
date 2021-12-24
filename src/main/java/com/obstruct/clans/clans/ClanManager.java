package com.obstruct.clans.clans;

import com.obstruct.clans.clans.codec.LocationConverter;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.shared.utility.UtilJava;
import com.obstruct.core.spigot.client.SpigotClientManager;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClanManager extends SpigotManager<SpigotModule<?>> {

    //Clans are stored in a hashmap for instant access correlating to their clan name;
    //Use clanMap.get("ClanName"); to get any clan.
    //clanMap.containsKey("ClanName") to see if it exists.
    private final HashMap<String, Clan> clanMap;

    public ClanManager(SpigotBasePlugin plugin) {
        super(plugin, "ClanManager");
        this.clanMap = new HashMap<>();

        getManager(MongoManager.class).getMorphia().map(Clan.class);
        getManager(MongoManager.class).getMorphia().getMapper().getConverters().addConverter(new LocationConverter());
        getManager(MongoManager.class).getDatastore().ensureIndexes();

        loadClans();
    }

    @Override
    public void registerModules() {

    }

    //This gets called on startup. Uses the main thread to load the clans,
    //So server wont start until all clans are loaded, Wont take longer than a second.
    //Synchronized means it will run this method and wont run any other code until this is completed.
    private synchronized void loadClans() {
        for (Clan clan : getManager(MongoManager.class).getDatastore().find(Clan.class)) {
            addClan(clan);
        }
    }

    //Method to remove Clan from memory.
    //removeClan(clan);
    public void removeClan(Clan clan) {
        getClanMap().remove(clan.getName().toLowerCase());

        getExecutorService().execute(() -> {
            clan.getEnemyMap().forEach((s, integer) -> {
                Clan enemy = getClan(s);
                enemy.getEnemyMap().remove(clan.getName());
                saveClan(enemy);
            });
            clan.getAllianceMap().forEach((s, aBoolean) -> {
                Clan alliance = getClan(s);
                alliance.getAllianceMap().remove(clan.getName());
                saveClan(alliance);
            });
        });
    }

    //Method to delete Clan from MongoDB.
    //deleteClan(clan);
    public void deleteClan(Clan clan) {
        Clan copy = getManager(MongoManager.class).getDatastore().createQuery(Clan.class).field("name").equal(clan.getName()).first();
        getManager(MongoManager.class).getDatastore().delete(copy);
    }

    //Method to save Clans;
    //saveClan(clan);
    public void saveClan(Clan clan) {
        getManager(MongoManager.class).getDatastore().save(clan);
    }

    //Method to add new Clans;
    //addClan(new Clan("ClanName"));
    public void addClan(Clan clan) {
        getClanMap().put(clan.getName().toLowerCase(), clan);
    }

    //Method to get a Clan based off its name.
    //getClan("ClanName");
    public Clan getClan(String name) {
        return getClanMap().get(name.toLowerCase());
    }

    //Method to get a Players Clan from a UUID;
    //getClan(player.getUniqueId());
    //Loops through the members and check if the player exists in it.
    public Clan getClan(UUID uuid) {
        for (Clan clan : getClanMap().values()) {
            if (clan.getClanMember(uuid) != null) {
                return clan;
            }
        }
        return null;
    }

    //Method to get a Players Clan from a Player;
    //Uses the same method as above;
    public Clan getClan(Player player) {
        return getClan(player.getUniqueId());
    }

    //Method to get a Clan based on their Chunk. AKA their claim.
    //Loops through their territory and finds if it equals the given chunk.
    public Clan getClan(Chunk chunk) {
        for (Clan clan : getClanMap().values()) {
            if (clan.getClaims().contains(UtilFormat.chunkToString(chunk))) {
                return clan;
            }
        }
        return null;
    }

    //Method to get a Clan using autofill
    //So if you type the start of a Clans name it'll find it based on that.
    //e.g Clans name is Test, You can type "Te" and it will find it. If there
    //are multiple clans like Test1 it'll return a list to the player of the available clans.
    //Must run Asynchronously or it will most likely lag the server
    public Clan searchClan(Player player, String input, boolean inform) {
        //Checks if the Clan Name is equal to what they typed to prevent unnecessary searching.
        if (getClanMap().containsKey(input.toLowerCase())) {
            return getClanMap().get(input.toLowerCase());
        }
        //Searches through the clans in the database and adds to an ArrayList.
        ArrayList<Clan> clans = UtilJava.searchCollection(getClanMap().values(), clan -> {
            //Checks if the Clans name contains the name they attempted to input.
            return clan.getName().toLowerCase().contains(input.toLowerCase());
        });
        //Searches through the Clients in the database.
        Client client = getManager(SpigotClientManager.class).searchClient(player, input, false);
        if (client != null) {
            Clan c = getClan(client.getUuid());
            if (c != null) {
                clans.add(c);
            }
        }
        if (clans.size() == 1) {
            return clans.get(0);
        }
        if (clans.size() > 1) {
            if (inform)
                UtilMessage.message(player, "Clan Search", ChatColor.YELLOW.toString() + clans.size() + ChatColor.GRAY + " matches found [" + clans.stream().map(c -> ChatColor.YELLOW + c.getName()).collect(Collectors.joining(ChatColor.GRAY + ", ")) + ChatColor.GRAY + "]");
        } else if (inform) {
            UtilMessage.message(player, "Clan Search", ChatColor.YELLOW.toString() + clans.size() + ChatColor.GRAY + " matches found [" + ChatColor.YELLOW + input + ChatColor.GRAY + "]");
        }
        return null;
    }

    public final Client searchMember(Player player, String name, boolean inform) {
        Clan clan = getClan(player.getUniqueId());
        List<Client> members = clan.getMembers().stream().map(clanMember -> getManager(MongoManager.class).getDatastore().createQuery(Client.class).field("uuid").equal(clanMember.getUuid()).first()).collect(Collectors.toList());
        if (members.stream().anyMatch(client -> client.getName().equalsIgnoreCase(name))) {
            return members.stream().filter(client -> client.getName().equalsIgnoreCase(name)).findFirst().get();
        }
        members.removeIf(client -> !client.getName().toLowerCase().contains(name.toLowerCase()));
        if (members.size() == 1)
            return members.get(0);
        if (members.size() > 1) {
            if (inform) {
                UtilMessage.message(player, "Member Search", ChatColor.YELLOW.toString() + members.size() + ChatColor.GRAY + " matches found [" + members.stream().map(client -> ChatColor.YELLOW + client.getName()).collect(Collectors.joining(ChatColor.GRAY + ", ")) + ChatColor.GRAY + "]");
            }
        } else {
            UtilMessage.message(player, "Member Search", ChatColor.YELLOW.toString() + members.size() + ChatColor.GRAY + " matches found [" + ChatColor.YELLOW + name + ChatColor.GRAY + "]");
        }
        return null;
    }

    public ClanRelation getClanRelation(Clan clan, Clan other) {
        if (other instanceof AdminClan) {
            return ClanRelation.ADMIN;
        }
        if (clan == null || other == null) {
            return ClanRelation.NEUTRAL;
        }
        if (clan.equals(other)) {
            return ClanRelation.SELF;
        }
        if (clan.isTrusted(other)) {
            return ClanRelation.ALLY_TRUSTED;
        }
        if (clan.isAllied(other)) {
            return ClanRelation.ALLY;
        }
        if (clan.isEnemy(other)) {
            return ClanRelation.ENEMY;
        }
        return ClanRelation.NEUTRAL;
    }
}
