package me.yamakaja.irc.client.chat;

import com.google.inject.Inject;
import me.yamakaja.irc.client.network.IRCNetworkClient;

import java.util.List;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class ChatChannel {

    private String name;
    private String topic;

    private List<ChatUser> users;

    @Inject
    private IRCNetworkClient ircClient;

    public ChatChannel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(List<ChatUser> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatChannel that = (ChatChannel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return topic != null ? topic.equals(that.topic) : that.topic == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        return result;
    }
}
