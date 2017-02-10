package me.yamakaja.irc.client.network.event.channel;

import me.yamakaja.irc.client.chat.ChatChannel;
import net.lahwran.fevents.Event;

import java.util.List;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class ChannelListEvent extends Event {

    private List<ChatChannel> channels;

    public ChannelListEvent(List<ChatChannel> channels) {
        this.channels = channels;
    }

    public List<ChatChannel> getChannels() {
        return channels;
    }

}
