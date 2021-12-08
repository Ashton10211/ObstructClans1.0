package de.zerakles.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    public String prefix;
    public String noPerms;
    public String alreadyHaveAClan;
    public String notOnline;
    public String clanWithThisNameAlreadyExists;
    public String promoted;
    public String demoted;
    public String gotPromoted;
    public String gotDemoted;
    public String nowAllys;
    public String talkingInAllChat;
    public String talkingInAllyChat;
    public String talkingInClanChat;
    public String mustBeAPlayer;
    public String chunksDeleted;
    public String chunkDeleted;
    public String notYourChunk;
    public String noLongerAllys;
    public String clanIsNotAlly;
    public String cantHaveMoreAlly;
    public String clickToBeAnAllyOf;
    public String requestSend;
    public String clanDoesNotExist;
    public String playerWasKicked;
    public String toMuchInvitations;
    public String alreadyHaveBeenInvited;
    public String hasBeenInvited;
    public String newClanInvitation;
    public String dontHaveAClan;
    public String leftYourClan;
    public String didntSendYouAnInvitation;
    public String clanIsFull;
    public String youJoinedAClan;
    public String playerJoinedYourClan;
    public String cantClaimTheSpawn;
    public String chunkAlreadyClaimed;
    public String chunkHaveBeenClaimed;
    public String clanReachedMaximumOfChunks;
    public String noHomeRegistered;
    public String homeRegistered;
    public String notInYourClan;
    public String newOwner;
    public String playerKilledByPlayer;
    public String playerDeath;
    public String chatFormat;
    public String dontHaveEnoughGold;
    public String itemClaimed;
    public String addGold;

    //mysql
    public String host;
    public String port;
    public String user;
    public String password;
    public String database;

    //resourcepack
    public String resourcepack;

    //lists
    public ArrayList<Player> inClanChat = new ArrayList<>();
    public ArrayList<Player> inAllyChat = new ArrayList<>();
    public HashMap<String, String> allyClans = new HashMap<>();
    public HashMap<Player, ArrayList<String>> clanInvitations = new HashMap<>();
    public HashMap<String, String> toAlly = new HashMap<>();
    public HashMap<Player, ZoneTypes> zones = new HashMap<>();
    public Location Shop;

    //Spawn Locations
    public Location BankShop= null;
    public Villager bankBoy= null;
    public Location OrganicProduce= null;
    public Villager organicBoy= null;
    public Location MiningShop= null;
    public Villager miningBoy= null;
    public Location BuildingSupplies= null;
    public Villager suppliesBoy= null;
    public Location PvpGear= null;
    public Villager pvpBoy = null;

    //spawn Travel
    public ArrayList<Location> Travel = new ArrayList<>();
    public ArrayList<Villager> TravelBoys = new ArrayList<>();

    public String NeedClaimNextToOwnLand;
}
