package de.zerakles.clanapi.legendaries;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class Legend {

    private String name;

    private ArrayList<String> lore;

    private ItemStack itemStack;

    private short durability;

    private int damage;

    private UUID uuid;

    private String original;

    public Legend(String name, ArrayList<String>lore,
                  ItemStack itemStack, short durability, int damage, UUID uuid, String original){
        this.durability = durability;
        this.name = name;
        this.lore = lore;
        this.itemStack = itemStack;
        this.damage = damage;
        this.uuid = uuid;
        this.original = original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginal() {
        return original;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setDurability(short durability) {
        this.durability = durability;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public short getDurability() {
        return durability;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
