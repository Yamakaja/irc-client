package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.packet.PacketEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class EventPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCNetworkClient ircClient;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket) msg;
        PacketEvent event = packet.getPacketType().getEvent(packet);
        if (event != null)
            ircClient.getEventBus().callEventAsync(event);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("An exception occured:");
        cause.printStackTrace();
    }
}
