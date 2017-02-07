package me.yamakaja.irc.client.network.event.channel;

import me.yamakaja.irc.client.chat.ChatChannel;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 06.02.17.
 */
public class UserPartEvent extends Event {

    private String user;
    private ChatChannel channel;
    private String reason;

    public UserPartEvent(String user, ChatChannel channel, String reason) {
        this.user = user;
        this.channel = channel;
        this.reason = reason;
    }

    public String getUser() {
        return user;
    }

    public ChatChannel getChannel() {
        return channel;
    }

    public String getReason() {
        return reason;
    }
}
