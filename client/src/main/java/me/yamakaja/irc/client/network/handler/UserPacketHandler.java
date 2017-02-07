package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.user.UserNickEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientNick;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class UserPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket) msg;

        switch (packet.getPacketType()) {
            case NICK: {
                PacketClientNick nickPacket = (PacketClientNick)packet;
                client.getEventBus().callEventAsync(new UserNickEvent(nickPacket.getSender(), nickPacket.getTo()));
                break;
            }
        }

        super.channelRead(ctx, msg);
    }
}
