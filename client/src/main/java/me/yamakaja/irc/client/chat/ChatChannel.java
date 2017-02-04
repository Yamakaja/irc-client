package me.yamakaja.irc.client.chat;

import com.google.inject.Inject;
import me.yamakaja.irc.client.IRCClient;

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

    public void setTopicSetter(String topicSetter) {
        this.topicSetter = topicSetter;
    }

    public void setTopicTime(Date topicTime) {
        this.topicTime = topicTime;
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

    public Date getTopicTime() {
        return topicTime;
    }

    @Override
    public String toString() {
        return "ChatChannel{" +
                "name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                ", users=" + users +
                ", topicSetter='" + topicSetter + '\'' +
                ", topicTime=" + topicTime +
                '}';
    }

    public void finishUsers() {
        this.finishedNames = true;
    }

    public void addUsers(Collection<String> usersToAdd) {
        if(finishedNames) {
            this.users.clear();
            finishedNames = false;
        }

        this.users.addAll(usersToAdd);
    }

}
