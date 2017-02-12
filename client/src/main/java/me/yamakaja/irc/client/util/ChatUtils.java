package me.yamakaja.irc.client.util;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class ChatUtils {

    private ChatUtils() {
    }

    public static String getAction(String action) {
        return "\u0001ACTION " + action + '\u0001';
    }

}
