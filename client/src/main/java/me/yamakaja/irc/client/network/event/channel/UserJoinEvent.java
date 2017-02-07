package me.yamakaja.irc.client.network.event.channel;

import me.yamakaja.irc.client.chat.ChatChannel;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class UserJoinEvent extends Event {

    private String user;
    private ChatChannel channel;

    public UserJoinEvent(String user, ChatChannel channel) {
        this.user = user;
        this.channel = channel;
    }

    /**
     * @return The user who joined
     */
    public String getUser() {
        return user;
    }

    /**
     * @return The channel they joined
     */
    public ChatChannel getChannel() {
        return channel;
    }
}
