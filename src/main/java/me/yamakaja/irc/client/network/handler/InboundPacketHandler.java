package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class InboundPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCNetworkClient ircClient;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket) msg;
        ircClient.getEventBus().callEventAsync(packet.getPacketType().getEvent(packet));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("An exception occured:");
        cause.printStackTrace();
    }
}
