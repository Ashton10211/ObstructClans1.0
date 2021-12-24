package com.obstruct.clans.clans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminClan extends Clan {

    private boolean safe;

    public AdminClan(String name) {
        super(name);
    }
}
