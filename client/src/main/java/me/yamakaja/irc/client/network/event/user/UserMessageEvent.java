package me.yamakaja.irc.client.network.event.user;

import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class UserMessageEvent extends Event {

    private String sender;
    private String message;

    public UserMessageEvent(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

}
