package me.yamakaja.irc.client.network.event.user;

import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 06.02.17.
 */
public class UserNickEvent extends Event {

    private String user;
    private String to;

    public UserNickEvent(String user, String to) {
        this.user = user;
        this.to = to;
    }

    /**
     * @return The nick which the user has changed to
     */
    public String getTo() {
        return to;
    }

    /**
     * @return The hostmask of the user who changed nick, with their old nick
     */
    public String getUser() {
        return user;
    }

}
