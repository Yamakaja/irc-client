package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.whois.*;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class WhoisPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCNetworkClient client;

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
        }

        super.channelRead(ctx, msg);
    }

    private void handleWhoisAway(PacketClientWhoisAway packet) {
        client.getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisOperator(PacketClientWhoisOperator packet) {
        client.getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisIdle(PacketClientWhoisIdle packet) {
        client.getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisChannels(PacketClientWhoisChannels packet) {
        client.getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisServer(PacketClientWhoisServer packet) {
        client.getUser(packet.getNick()).apply(packet);
    }

    private void handleWhoisUser(PacketClientWhoisUser packet) {
        client.getUser(packet.getNick()).apply(packet);
    }
}
