package me.yamakaja.irc.client.network.event.channel;

import me.yamakaja.irc.client.chat.ChatChannel;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class ChannelNamesEvent extends Event {

    private ChatChannel channel;

    public ChannelNamesEvent(ChatChannel channel) {
        this.channel = channel;
    }

    public ChatChannel getChannel() {
        return channel;
    }
}
