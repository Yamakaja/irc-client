package me.yamakaja.irc.client.chat;

import com.google.inject.Inject;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.packet.server.PacketServerJoin;
import me.yamakaja.irc.client.network.packet.server.PacketServerMessage;
import me.yamakaja.irc.client.network.packet.server.PacketServerPart;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class ChatChannel {

    private String name;
    private String topic;

    private List<String> users = new LinkedList<>();
    private boolean finishedNames = false;

    private String topicSetter;
    private Date topicTime;

    private boolean joined;

    @Inject
    private IRCClient ircClient;

    public ChatChannel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatChannel that = (ChatChannel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (topicSetter != null ? !topicSetter.equals(that.topicSetter) : that.topicSetter != null) return false;
        return topicTime != null ? topicTime.equals(that.topicTime) : that.topicTime == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (topicSetter != null ? topicSetter.hashCode() : 0);
        result = 31 * result + (topicTime != null ? topicTime.hashCode() : 0);
        return result;
    }

    public String getTopicSetter() {
        return topicSetter;
    }

    public void setTopicSetter(String topicSetter) {
        this.topicSetter = topicSetter;
    }

    public Date getTopicTime() {
        return topicTime;
    }

    public void setTopicTime(Date topicTime) {
        this.topicTime = topicTime;
    }

    @Override
    public String toString() {
        return "ChatChannel{" +
                "name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                ", users=" + users +
                ", finishedNames=" + finishedNames +
                ", topicSetter='" + topicSetter + '\'' +
                ", topicTime=" + topicTime +
                ", joined=" + joined +
                '}';
    }

    public void finishUsers() {
        this.finishedNames = true;
    }

    public void addUsers(Collection<String> usersToAdd) {
        if (finishedNames) {
            this.users.clear();
            finishedNames = false;
        }

        this.users.addAll(usersToAdd);
    }

    public void join() {
        if (!joined)
            ircClient.sendPacket(new PacketServerJoin(this.name));
    }

    public boolean isJoined() {
        return joined;
    }

    /**
     * Internal use only!
     *
     * @param joined joined
     */
    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    /**
     * Send a message to this channel!
     *
     * @param message The message to send
     * @throws IllegalStateException If you try to write to a channel that you haven't connected to
     */
    public void sendMessage(String message) {
        if (!joined)
            throw new IllegalStateException("Cannot write to " + this.getName() + " because it hasn't been joined yet!");

        this.ircClient.sendPacket(new PacketServerMessage(this.getName(), message));
    }

    /**
     * Leave the channel
     *
     * @throws IllegalStateException If you try to disconnect from a channel that you didn't join before
     */
    public void part() {
        this.part(null);
    }

    /**
     * Leave a channel
     *
     * @param message The part message
     * @throws IllegalStateException If you try to disconnect from a channel that you didn't join before
     */
    public void part(String message) {
        if(!this.joined)
            throw new IllegalStateException("Cannot leave " + this.name + " if you haven't joined it before!");

        this.ircClient.sendPacket(new PacketServerPart(this.name, message));
    }

}
