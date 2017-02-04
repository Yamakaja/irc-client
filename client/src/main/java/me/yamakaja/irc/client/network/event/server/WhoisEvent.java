package me.yamakaja.irc.client.network.event.server;

import me.yamakaja.irc.client.chat.ChatUser;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class WhoisEvent extends Event {

    private ChatUser user;

    public WhoisEvent(ChatUser user) {
        this.user = user;
    }

    public ChatUser getUser() {
        return user;
    }
}
