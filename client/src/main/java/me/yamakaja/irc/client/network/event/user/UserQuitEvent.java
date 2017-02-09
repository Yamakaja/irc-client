package me.yamakaja.irc.client.network.event.user;

import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class UserQuitEvent extends Event {

    private String user;
    private String reason;

    public UserQuitEvent(String user, String reason) {
        this.user = user;
        this.reason = reason;
    }

    public String getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }

}
