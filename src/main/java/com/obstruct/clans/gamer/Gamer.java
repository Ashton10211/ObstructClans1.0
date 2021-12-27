package com.obstruct.clans.gamer;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.UUID;


@Getter
@Setter
@Entity("gamers")
public class Gamer {

    @Id
    private ObjectId id;

    @Indexed
    private UUID uuid;
    private int gold;
    private long timePlayed;
    private long lastLogin;
    private long lastOnline;

    public Gamer() {
    }

    public Gamer(UUID uuid) {
        this.uuid = uuid;
        this.gold = 5000;
        this.timePlayed = 0L;
        this.lastLogin = System.currentTimeMillis();
        this.lastOnline = 0L;
    }
}