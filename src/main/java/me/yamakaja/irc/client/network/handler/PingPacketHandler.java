package me.yamakaja.irc.client.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.PacketClientPing;
import me.yamakaja.irc.client.network.packet.server.PacketServerPong;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PingPacketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(((ClientboundPacket) msg).getPacketType() == ClientboundPacketType.PING) {
            ctx.channel().writeAndFlush(new PacketServerPong((PacketClientPing)msg)).sync();
        }
        super.channelRead(ctx, msg);
    }
}
