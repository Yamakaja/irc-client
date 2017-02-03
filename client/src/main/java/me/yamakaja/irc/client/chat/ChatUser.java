package me.yamakaja.irc.client.chat;

import me.yamakaja.irc.client.network.packet.client.command.whois.*;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class ChatUser {

    private String nick;
    private String ident;
    private String hostname;
    private String realName;
    private PacketClientWhoisChannels.ChannelMap channels;
    private String idleState;
    private String awayState;
    private boolean ircOperator;

    private ChatServer server;

    public String getNick() {
        return nick;
    }

    public String getIdent() {
        return ident;
    }

    public String getHostname() {
        return hostname;
    }

    public String getRealName() {
        return realName;
    }

    public void apply(PacketClientWhoisUser packet) {
        this.nick = packet.getNick();
        this.ident = packet.getIdent();
        this.hostname = packet.getHostname();
        this.realName = packet.getRealName();
    }

    public void apply(PacketClientWhoisServer packet) {
        this.server = packet.getServer();
    }

    public void apply(PacketClientWhoisChannels packet) {
        this.channels = packet.getChannels();
    }

    public void apply(PacketClientWhoisIdle packet) {
        this.idleState = packet.getIdleState();
    }

    public void apply(PacketClientWhoisAway packet) {
        this.awayState = packet.getAwayState();
    }

    public void apply(PacketClientWhoisOperator packet) {
        this.ircOperator = true;
    }

    public ChatServer getServer() {
        return server;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "nick='" + nick + '\'' +
                ", ident='" + ident + '\'' +
                ", hostname='" + hostname + '\'' +
                ", realName='" + realName + '\'' +
                ", channels=" + channels +
                ", idleState='" + idleState + '\'' +
                ", awayState='" + awayState + '\'' +
                ", ircOperator=" + ircOperator +
                ", server=" + server +
                '}';
    }

    public PacketClientWhoisChannels.ChannelMap getChannels() {
        return channels;
    }

    public String getIdleState() {
        return idleState;
    }

    public boolean isIrcOperator() {
        return ircOperator;
    }

    public boolean isAway() {
        return awayState != null;
    }

    public boolean isIdle() {
        return idleState != null;
    }

    public String getAwayState() {
        return awayState;
    }

}
