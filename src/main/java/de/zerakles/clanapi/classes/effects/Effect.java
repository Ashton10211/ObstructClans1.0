package de.zerakles.clanapi.classes.effects;

public class Effect {

    private EnumEffect enumEffect;

    private String effectName;

    private int effectLevel;

    private int effectLength;

    public Effect(EnumEffect enumEffect, String effectName, int effectLevel, int effectLength){
        this.enumEffect = enumEffect;
        this.effectName = effectName;
        this.effectLevel = effectLevel;
        this.effectLength = effectLength;
        return;
    }

    public EnumEffect getEnumEffect() {
        return enumEffect;
    }

    public int getEffectLength() {
        return effectLength;
    }

    public int getEffectLevel() {
        return effectLevel;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectLength(int effectLength) {
        this.effectLength = effectLength;
    }

    public void setEffectLevel(int effectLevel) {
        this.effectLevel = effectLevel;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public void setEnumEffect(EnumEffect enumEffect) {
        this.enumEffect = enumEffect;
    }
}
