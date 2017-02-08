package me.yamakaja.irc.client.util;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class NameUtils {

    /**
     * Cuts the nick from the entire line usually sent by the irc daemon
     *
     * Example:
     * "test!webchat@SpigotMC-hv3tsd.cmtf.u11l.007a.2003.IP" ---> "test"
     *
     * @param full The full identifier
     * @return Just the nick
     */
    public static String getNick(String full) {
        return full.substring(0, full.indexOf('!'));
    }

    /**
     * Removes the nick from the entire line usually sent by the irc daemon
     *
     * Example:
     * "test!webchat@SpigotMC-hv3tsd.cmtf.u11l.007a.2003.IP" ---> "webchat@SpigotMC-hv3tsd.cmtf.u11l.007a.2003.IP"
     *
     * @param full The full identifier
     * @return Everything but [nick]!
     */
    public static String stripNick(String full) {
        return full.substring(full.indexOf('!') + 1, full.length());
    }

}
