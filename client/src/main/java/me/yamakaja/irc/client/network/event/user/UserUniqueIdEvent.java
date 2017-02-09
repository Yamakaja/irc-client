package me.yamakaja.irc.client.network.event.user;

import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class UserUniqueIdEvent extends Event {

    private String uniqueId;

    public UserUniqueIdEvent(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

}
