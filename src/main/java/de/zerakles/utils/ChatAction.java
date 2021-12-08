package de.zerakles.utils;

public enum ChatAction {
    CHAT_MESSAGE((byte)0),
    SERVER_MESSAGE((byte)1),
    ACTION_BAR((byte)2);

    private byte value;

    ChatAction(byte valuee) {
        value = valuee;
    }

    public byte getValue() {
        return value;
    }
}
