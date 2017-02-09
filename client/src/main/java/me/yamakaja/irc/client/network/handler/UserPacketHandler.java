package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.channel.ChannelMessageEvent;
import me.yamakaja.irc.client.network.event.server.ServerNoticeEvent;
import me.yamakaja.irc.client.network.event.user.UserMessageEvent;
import me.yamakaja.irc.client.network.event.user.UserNickEvent;
import me.yamakaja.irc.client.network.event.user.UserQuitEvent;
import me.yamakaja.irc.client.network.event.user.UserUniqueIdEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientMessage;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientNick;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientNotice;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientQuit;
import me.yamakaja.irc.client.network.packet.client.command.server.PacketClientUniqueId;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class UserPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket originalPacket = (ClientboundPacket) msg;

        switch (originalPacket.getPacketType()) {
            case NICK: {
                PacketClientNick nickPacket = (PacketClientNick) originalPacket;
                client.getEventBus().callEventAsync(new UserNickEvent(nickPacket.getSender(), nickPacket.getTo()));
                break;
            }
            case PRIVMSG: {
                PacketClientMessage message = (PacketClientMessage) originalPacket;
                if (message.getTarget().startsWith("#"))
                    client.getEventBus().callEventAsync(new ChannelMessageEvent(message.getSender(), message.getMessage(), client.getChannel(message.getTarget())));
                else
                    client.getEventBus().callEventAsync(new UserMessageEvent(message.getSender(), message.getMessage()));
                return;
            }
            case QUIT: {
                PacketClientQuit packet = (PacketClientQuit) originalPacket;
                client.getEventBus().callEventAsync(new UserQuitEvent(packet.getUser(), packet.getReason()));
                return;
            }
            case NOTICE: {
                PacketClientNotice packet = (PacketClientNotice) originalPacket;
                client.getEventBus().callEventAsync(new ServerNoticeEvent(packet.getTarget(), packet.getMessage()));
                return;
            }
            case RPL_UNIQUEID: {
                client.getEventBus().callEventAsync(new UserUniqueIdEvent(((PacketClientUniqueId) msg).getUniqueId()));
                return;
            }
        }

        super.channelRead(ctx, msg);
    }

}
