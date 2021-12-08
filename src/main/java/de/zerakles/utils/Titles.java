package de.zerakles.utils;

public enum Titles {
    LEADER, COLEADER, VIP, MEMBER;

    public boolean isEnum(Titles enumTitle, String title){
        if(enumTitle.toString().equalsIgnoreCase(title)){
            return true;
        }
        return false;
    }
}
