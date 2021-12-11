package de.zerakles.config;

import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    public void loadConfig(){
        File file = new File(Clan.getClan().getDataFolder() + "/config.yml");
        if(!file.exists()){
            createConfig();
            return;
        }
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        getData().prefix = fileConfiguration.getString("Clan.Prefix").replaceAll("&", "§") + " ";
        getData().database = fileConfiguration.getString("Clan.MySQL.Database");
        getData().alreadyHaveAClan = fileConfiguration.getString("Clan.Messages.alreadyHaveAClan").replaceAll("&", "§");
        getData().clanWithThisNameAlreadyExists = fileConfiguration.getString("Clan.Messages.clanWithThisNameAlreadyExists").replaceAll("&", "§");
        getData().demoted = fileConfiguration.getString("Clan.Messages.demoted").replaceAll("&", "§");
        getData().promoted = fileConfiguration.getString("Clan.Messages.promoted").replaceAll("&", "§");
        getData().gotDemoted = fileConfiguration.getString("Clan.Messages.gotDemoted").replaceAll("&", "§");
        getData().gotPromoted = fileConfiguration.getString("Clan.Messages.gotPromoted").replaceAll("&", "§");
        getData().newOwner = fileConfiguration.getString("Clan.Messages.newOwner").replaceAll("&", "§");
        getData().noPerms = fileConfiguration.getString("Clan.Messages.noPerms").replaceAll("&", "§");
        getData().notOnline = fileConfiguration.getString("Clan.Messages.notOnline").replaceAll("&", "§");
        getData().user = fileConfiguration.getString("Clan.MySQL.User");
        getData().password = fileConfiguration.getString("Clan.MySQL.Password");
        getData().host = fileConfiguration.getString("Clan.MySQL.Host");
        getData().port = fileConfiguration.getString("Clan.MySQL.Port");
        getData().resourcepack = fileConfiguration.getString("Clan.Config.Resourcepack");
        getData().nowAllys = fileConfiguration.getString("Clan.Messages.nowAlly").replaceAll("&", "§");
        getData().talkingInAllChat = fileConfiguration.getString("Clan.Messages.talkingInAllChatNow").replaceAll("&", "§");
        getData().talkingInAllyChat = fileConfiguration.getString("Clan.Messages.talkingInAllyChatNow").replaceAll("&","§");
        getData().talkingInClanChat = fileConfiguration.getString("Clan.Messages.talkingInClanChatNow").replaceAll("&","§");
        getData().dontHaveAClan = fileConfiguration.getString("Clan.Messages.dontHaveAClan").replaceAll("&","§");
        getData().mustBeAPlayer = fileConfiguration.getString("Clan.Messages.mustBeAPlayer").replaceAll("&", "§");
        getData().chunksDeleted = fileConfiguration.getString("Clan.Messages.ChunksDeleted").replaceAll("&", "§");
        getData().chunkDeleted = fileConfiguration.getString("Clan.Messages.ChunkDeleted").replaceAll("&", "§");
        getData().notYourChunk = fileConfiguration.getString("Clan.Messages.NotYourChunk").replaceAll("&", "§");
        getData().noLongerAllys = fileConfiguration.getString("Clan.Messages.noLongerAllys").replaceAll("&", "§");
        getData().clanIsNotAlly = fileConfiguration.getString("Clan.Messages.clanIsNotAlly").replaceAll("&", "§");
        getData().cantHaveMoreAlly = fileConfiguration.getString("Clan.Messages.cantHaveMoreAlly").replaceAll("&", "§");
        getData().clickToBeAnAllyOf = fileConfiguration.getString("Clan.Messages.clickToBeAnAllyOf").replaceAll("&", "§");
        getData().requestSend = fileConfiguration.getString("Clan.Messages.requestSend").replaceAll("&", "§");
        getData().clanDoesNotExist = fileConfiguration.getString("Clan.Messages.clanDoesNotExist").replaceAll("&", "§");
        getData().playerWasKicked = fileConfiguration.getString("Clan.Messages.playerWasKicked").replaceAll("&", "§");
        getData().toMuchInvitations = fileConfiguration.getString("Clan.Messages.toMuchInvitations").replaceAll("&", "§");
        getData().alreadyHaveBeenInvited = fileConfiguration.getString("Clan.Messages.alreadyHaveBeenInvited").replaceAll("&", "§");
        getData().hasBeenInvited = fileConfiguration.getString("Clan.Messages.hasBeenInvited").replaceAll("&", "§");
        getData().newClanInvitation = fileConfiguration.getString("Clan.Messages.newClanInvitation").replaceAll("&", "§");
        getData().notInYourClan = fileConfiguration.getString("Clan.Messages.notInYourClan").replaceAll("&", "§");
        getData().leftYourClan = fileConfiguration.getString("Clan.Messages.leftYourClan").replaceAll("&", "§");
        getData().didntSendYouAnInvitation = fileConfiguration.getString("Clan.Messages.didntSendYouAnInvitation").replaceAll("&", "§");
        getData().clanIsFull = fileConfiguration.getString("Clan.Messages.clanIsFull").replaceAll("&", "§");
        getData().youJoinedAClan = fileConfiguration.getString("Clan.Messages.youJoinedAClan").replaceAll("&", "§");
        getData().playerJoinedYourClan = fileConfiguration.getString("Clan.Messages.playerJoinedYourClan").replaceAll("&", "§");
        getData().cantClaimTheSpawn = fileConfiguration.getString("Clan.Messages.cantClaimTheSpawn").replaceAll("&", "§");
        getData().chunkAlreadyClaimed = fileConfiguration.getString("Clan.Messages.chunkAlreadyClaimed").replaceAll("&", "§");
        getData().chunkHaveBeenClaimed = fileConfiguration.getString("Clan.Messages.chunkHaveBeenClaimed").replaceAll("&", "§");
        getData().clanReachedMaximumOfChunks = fileConfiguration.getString("Clan.Messages.clanReachedMaximumOfChunks").replaceAll("&", "§");
        getData().noHomeRegistered = fileConfiguration.getString("Clan.Messages.noHomeRegistered").replaceAll("&", "§");
        getData().homeRegistered = fileConfiguration.getString("Clan.Messages.homeRegistered").replaceAll("&", "§");
        getData().playerKilledByPlayer = fileConfiguration.getString("Clan.Messages.playerKilledByPlayer").replaceAll("&", "§");
        getData().playerDeath = fileConfiguration.getString("Clan.Messages.playerDeath").replaceAll("&", "§");
        getData().chatFormat = fileConfiguration.getString("Clan.Messages.chatFormat").replaceAll("&", "§");
        getData().itemClaimed = fileConfiguration.getString("Clan.Messages.itemClaimed").replaceAll("&", "§");
        getData().dontHaveEnoughGold = fileConfiguration.getString("Clan.Messages.dontHaveEnoughGold").replaceAll("&", "§");
        getData().addGold = fileConfiguration.getString("Clan.Messages.addGold").replaceAll("&", "§");
        getData().NeedClaimNextToOwnLand = fileConfiguration.getString("Clan.Messages.NeedClaimNextToOwnLand").replaceAll("&", "§");

        return;
    }

    private Data getData(){
        return Clan.getClan().data;
    }

    private void createConfig() {
        File file = new File(Clan.getClan().getDataFolder() + "/config.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        fileConfiguration.set("Clan.Prefix" , "&9Clan>");
        fileConfiguration.set("Clan.MySQL.User", "clan");
        fileConfiguration.set("Clan.MySQL.Password", "system");
        fileConfiguration.set("Clan.MySQL.Database", "clansystem");
        fileConfiguration.set("Clan.MySQL.Host", "localhost");
        fileConfiguration.set("Clan.MySQL.Port", "3306");
        fileConfiguration.set("Clan.Messages.alreadyHaveAClan", "&cPlayer already have a clan!");
        fileConfiguration.set("Clan.Messages.clanWithThisNameAlreadyExists", "&cThis name is already taken!");
        fileConfiguration.set("Clan.Messages.demoted", "&cPlayer %player% got demoted %title%!");
        fileConfiguration.set("Clan.Messages.promoted", "&aPlayer %player% got promoted to %title%!");
        fileConfiguration.set("Clan.Messages.gotDemoted", "&cYou got demoted to %title%!");
        fileConfiguration.set("Clan.Messages.gotPromoted", "&aYou got promoted to %title%!");
        fileConfiguration.set("Clan.Messages.newOwner", "&c%player% is now the new owner of your clan!");
        fileConfiguration.set("Clan.Messages.noPerms", "&cYou dont have permissions to do this!");
        fileConfiguration.set("Clan.Messages.notOnline", "&cPlayer must be online!");
        fileConfiguration.set("Clan.Messages.nowAlly", "&7Clan &e%clan% &7and §e%allyclan% &7are allys now!");
        fileConfiguration.set("Clan.Messages.talkingInAllChatNow", "&cYou are talking in all chat now!");
        fileConfiguration.set("Clan.Messages.talkingInAllyChatNow", "&aYou are talking in Ally Chat now!");
        fileConfiguration.set("Clan.Messages.talkingInClanChatNow", "&aYou are talking in Clan Chat now!");
        fileConfiguration.set("Clan.Messages.dontHaveAClan", "&cYou dont have a Clan!");
        fileConfiguration.set("Clan.Messages.mustBeAPlayer", "&cYou must be a Player!");
        fileConfiguration.set("Clan.Messages.ChunksDeleted", "&aChunks deleted!");
        fileConfiguration.set("Clan.Messages.ChunkDeleted", "&aChunk deleted!");
        fileConfiguration.set("Clan.Messages.NotYourChunk", "&cThis is not your chunk!");
        fileConfiguration.set("Clan.Messages.noLongerAllys", "&7Clan &e%clan% &7and &e%allyclan% &7are no longer allys!");
        fileConfiguration.set("Clan.Messages.clanIsNotAlly", "&cThis Clan isnt your ally!");
        fileConfiguration.set("Clan.Messages.cantHaveMoreAlly", "&cYou cant have more then 1 Ally!");
        fileConfiguration.set("Clan.Messages.clickToBeAnAllyOf", "&bClick to be an Ally of %clan%.");
        fileConfiguration.set("Clan.Messages.requestSend", "&aRequest send!");
        fileConfiguration.set("Clan.Messages.clanDoesNotExist", "&cClan does not exist!");
        fileConfiguration.set("Clan.Messages.playerWasKicked", "&cPlayer was kicked!");
        fileConfiguration.set("Clan.Messages.toMuchInvitations", "&cAlready have the maximum of Invitations!");
        fileConfiguration.set("Clan.Messages.alreadyHaveBeenInvited", "&cThis player already has been invited in you Clan!");
        fileConfiguration.set("Clan.Messages.hasBeenInvited", "&aPlayer has been invited!");
        fileConfiguration.set("Clan.Messages.newClanInvitation", "&aYou have a new Invitation!");
        fileConfiguration.set("Clan.Messages.notInYourClan", "&cThis Player have to be in your Clan first!");
        fileConfiguration.set("Clan.Messages.leftYourClan", "&cYou left your clan!");
        fileConfiguration.set("Clan.Messages.didntSendYouAnInvitation", "&cThis Clan didnt send you an Invitation!");
        fileConfiguration.set("Clan.Messages.youJoinedAClan", "&aYou joined Clan &e%clan%&a.");
        fileConfiguration.set("Clan.Messages.playerJoinedYourClan", "&aPlayer &e%player% &ajoined your Clan!");
        fileConfiguration.set("Clan.Messages.cantClaimTheSpawn", "&cYou cant claim the Spawn!");
        fileConfiguration.set("Clan.Messages.chunkHaveBeenClaimed", "&aChunk has been claimed!");
        fileConfiguration.set("Clan.Messages.clanReachedMaximumOfChunks", "&cClan already reaches maximum of Chunks!");
        fileConfiguration.set("Clan.Messages.noHomeRegistered", "&cYou didnt registered a home!");
        fileConfiguration.set("Clan.Messages.homeRegistered", "&aYou registered a home!");
        fileConfiguration.set("Clan.Messages.chunkAlreadyClaimed", "&cThis Chunk is already claimed!");
        fileConfiguration.set("Clan.Messages.playerKilledByPlayer", "&e%player% &7was slain by &e%killer%.");
        fileConfiguration.set("Clan.Messages.playerDeath", "&e%player% &7died.");
        fileConfiguration.set("Clan.Messages.chatFormat", "§6[§5%Clan§6]§e%player%§8:§7%message%".replaceAll("§", "&"));
        fileConfiguration.set("Clan.Messages.itemClaimed", "&aItem claimed!".replaceAll("§", "&"));
        fileConfiguration.set("Clan.Messages.dontHaveEnoughGold", "§cYou dont have enought Gold!".replaceAll("§", "&"));
        fileConfiguration.set("Clan.Messages.addGold", "§aYou got §e%gold%g".replaceAll("§", "&"));
        fileConfiguration.set("Clan.Messages.clanIsFull", "§cClan is full!!".replaceAll("§", "&"));
        fileConfiguration.set("Clan.Messages.NeedClaimNextToOwnLand", "§cYou need to claim next to your own land!".replaceAll("§", "&"));
        //resourcepack
        fileConfiguration.set("Clan.Config.Resourcepack", "https://download.mc-packs.net/pack/188af22290f4266667f1b265fc57481dbbc0965a.zip");

        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getData().NeedClaimNextToOwnLand = "§cYou need to claim next to your own land!";
        getData().addGold = "§aYou got §e%gold%g";
        getData().itemClaimed = "§aItem claimed!";
        getData().dontHaveEnoughGold = "§cYou dont have enought Gold!";
        getData().chatFormat = "§6[§5%Clan%§6]§e%player%§8:§7%message%";
        getData().playerDeath = "§e%player% §7died.";
        getData().playerKilledByPlayer = "§e%player% §7was slain by §e%killer%.";
        getData().chunkAlreadyClaimed = "§cThis Chunk is already claimed!";
        getData().homeRegistered = "§aYou registered a home!";
        getData().noHomeRegistered = "§cYou didnt registered a home!";
        getData().clanReachedMaximumOfChunks = "§cClan already reaches maximum of Chunks!";
        getData().chunkHaveBeenClaimed ="§aChunk has been claimed!";
        getData().cantClaimTheSpawn = "§cYou cant claim the Spawn!";
        getData().playerJoinedYourClan = "§aPlayer §e%player% §ajoined your Clan!";
        getData().youJoinedAClan = "§aYou joined Clan §e%clan%§a.";
        getData().didntSendYouAnInvitation = "§cThis Clan didnt send you an Invitation!!";
        getData().clanIsFull = "§cClan is full!!";
        getData().leftYourClan = "§cYou left your clan!";
        getData().notInYourClan = "§cThis Player have to be in your Clan first!";
        getData().newClanInvitation = "§aYou have a new Invitation";
        getData().hasBeenInvited = "§aPlayer has been invited!";
        getData().alreadyHaveBeenInvited = "§cThis player already has been invited in your Clan!";
        getData().toMuchInvitations = "§cAlready have the maximum of Invitations!";
        getData().playerWasKicked = "§cPlayer was kicked!";
        getData().clanDoesNotExist = "§cClan does not exist!";
        getData().requestSend = "§aRequest send!";
        getData().clickToBeAnAllyOf = "§bClick to be an Ally of %clan%.";
        getData().cantHaveMoreAlly = "§cYou cant have more then 1 Ally!";
        getData().clanIsNotAlly = "§cThis Clan isnt your ally!";
        getData().notYourChunk = "§cThis is not you chunk!";
        getData().noLongerAllys = "§7Clan §e%clan% §7and §e%allyclan% §7are no longer allys!";
        getData().chunkDeleted= "§aChunk deleted!";
        getData().chunksDeleted= "§aChunks deleted!";
        getData().mustBeAPlayer= "§cYou must be a Player!";
        getData().dontHaveAClan = "§cYou dont have a Clan!";
        getData().talkingInClanChat = "§aYou are talking in Clan Chat now!";
        getData().talkingInAllyChat = "§aYou are talking in Ally Chat now!";
        getData().talkingInAllChat = "§cYou are talking in all chat now!";
        getData().nowAllys = "§7Clan §e%clan% §7and §e%allyclan% §7are allys now!";
        getData().prefix = "§9Clan> ";
        getData().database = "clansystem";
        getData().alreadyHaveAClan = "§cPlayer already have a clan!";
        getData().clanWithThisNameAlreadyExists = "§cThis name is already taken!";
        getData().demoted = "§cPlayer %player% got demoted to %title%!";
        getData().promoted = "§aPlayer %player% got promoted to %title%!";
        getData().gotDemoted = "§cYou got demoted to %title%!";
        getData().gotPromoted = "§aYou got promoted to %title%!";
        getData().newOwner = "§c%player% is now the new owner of your clan!";
        getData().noPerms = "§cYou dont have permissions to do this!";
        getData().notOnline = "§cPlayer must be online!";
        getData().user = "clan";
        getData().password = "system";
        getData().host = "localhost";
        getData().port = "3306";
        getData().resourcepack = "https://download.mc-packs.net/pack/188af22290f4266667f1b265fc57481dbbc0965a.zip";
        return;
    }
}
