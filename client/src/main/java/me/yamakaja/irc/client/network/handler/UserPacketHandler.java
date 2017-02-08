package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.channel.ChannelMessageEvent;
import me.yamakaja.irc.client.network.event.user.UserMessageEvent;
import me.yamakaja.irc.client.network.event.user.UserNickEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientMessage;
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
                PacketClientNick nickPacket = (PacketClientNick) packet;
                client.getEventBus().callEventAsync(new UserNickEvent(nickPacket.getSender(), nickPacket.getTo()));
                break;
            }
            case PRIVMSG: {
                PacketClientMessage message = (PacketClientMessage) packet;
                if (message.getTarget().startsWith("#"))
                    client.getEventBus().callEventAsync(new ChannelMessageEvent(message.getSender(), message.getMessage(), client.getChannel(message.getTarget())));
                else
                    client.getEventBus().callEventAsync(new UserMessageEvent(message.getSender(), message.getMessage()));
                return;
            }
        }

        super.channelRead(ctx, msg);
    }
}
