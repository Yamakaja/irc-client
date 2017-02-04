package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.chat.ChatUser;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.server.WhoisEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.whois.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class WhoisPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    public IRCClient client;

    @Inject
    private Injector injector;

    private Map<String, ChatUser> users = new HashMap<>();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket) msg;

        switch (packet.getPacketType()) {
            case RPL_WHOISUSER:
                handleWhoisUser((PacketClientWhoisUser) packet);
                return;
            case RPL_WHOISSERVER:
                handleWhoisServer((PacketClientWhoisServer) packet);
                return;
            case RPL_WHOISCHANNELS:
                handleWhoisChannels((PacketClientWhoisChannels) packet);
                return;
            case RPL_WHOISIDLE:
                handleWhoisIdle((PacketClientWhoisIdle) packet);
                return;
            case RPL_WHOISOPERATOR:
                handleWhoisOperator((PacketClientWhoisOperator) packet);
                return;
            case RPL_AWAY:
                handleWhoisAway((PacketClientWhoisAway) packet);
                return;
            case RPL_ENDOFWHOIS:
                handleWhoisEnd((PacketClientWhoisEnd) packet);
                return;
        }

        super.channelRead(ctx, msg);
    }

    private void handleWhoisEnd(PacketClientWhoisEnd packet) {
        String nick = packet.getNick();
        client.getEventBus().callEventAsync(new WhoisEvent(getUser(nick)));
        users.remove(nick);
    }

    private void handleWhoisAway(PacketClientWhoisAway packet) {
        getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisOperator(PacketClientWhoisOperator packet) {
        getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisIdle(PacketClientWhoisIdle packet) {
        getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisChannels(PacketClientWhoisChannels packet) {
        getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisServer(PacketClientWhoisServer packet) {
        getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisUser(PacketClientWhoisUser packet) {
        getUser(packet.getNick()).apply(packet);
    }

    public ChatUser getUser(String nick) {
        if (users.containsKey(nick))
            return users.get(nick);
        ChatUser user = injector.getInstance(ChatUser.class);
        users.put(nick, user);
        return user;
    }

}
