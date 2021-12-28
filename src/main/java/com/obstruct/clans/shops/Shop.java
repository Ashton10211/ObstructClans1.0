package com.obstruct.clans.shops;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Getter
@Setter
@Entity("shops")
public class Shop {

    @Id
    private ObjectId id;

    @Indexed
    private String shopName;
    private EntityType entityType;
    private Location location;

    public Shop() {
    }

    public Shop(Location location, String shopName, EntityType entityType) {
        this.location = location;
        this.shopName = shopName;
        this.entityType = entityType;
    }

    public void spawn() {
        getLocation().getWorld().spawn(getLocation(), entityType.getEntityClass());
    }
}