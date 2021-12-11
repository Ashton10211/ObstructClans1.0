package de.zerakles.utils;

public enum Titles {
    LEADER, ADMIN, MEMBER, RECRUIT;

    public boolean isEnum(Titles enumTitle, String title){
        if(enumTitle.toString().equalsIgnoreCase(title)){
            return true;
        }
        return false;
    }
}
