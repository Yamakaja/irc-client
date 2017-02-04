package me.yamakaja.irc.client.network.event.channel;

import me.yamakaja.irc.client.chat.ChatChannel;
import net.lahwran.fevents.Event;

import java.util.Date;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class TopicEvent extends Event {

    private ChatChannel channel;

    public TopicEvent(ChatChannel channel) {
        this.channel = channel;
    }

    public ChatChannel getChannel() {
        return channel;
    }

    public String getTopic() {
        return channel.getTopic();
    }

    public Date getSetTime() {
        return channel.getTopicTime();
    }

    public String getTopicSetter() {
        return channel.getTopicSetter();
    }

}
