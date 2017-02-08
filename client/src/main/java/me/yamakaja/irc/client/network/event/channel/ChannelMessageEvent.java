package me.yamakaja.irc.client.network.event.channel;

import me.yamakaja.irc.client.chat.ChatChannel;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class ChannelMessageEvent extends Event {

    private String sender;
    private String message;
    private ChatChannel channel;

    public ChannelMessageEvent(String sender, String message, ChatChannel channel) {
        this.sender = sender;
        this.message = message;
        this.channel = channel;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public ChatChannel getChannel() {
        return channel;
    }

}
