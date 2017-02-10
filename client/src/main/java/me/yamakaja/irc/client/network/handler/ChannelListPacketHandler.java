package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.network.event.channel.ChannelListEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.list.PacketClientChannelsEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class ChannelListPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCClient client;

    private List<ChatChannel> channels = new LinkedList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        switch (((ClientboundPacket) msg).getPacketType()) {
            case RPL_LISTSTART: {
                channels.clear();
                return;
            }
            case RPL_LIST: {
                PacketClientChannelsEntry packet = (PacketClientChannelsEntry) msg;
                ChatChannel channel = client.getChannel(packet.getChannel());
                channel.setTopic(packet.getTopic());
                channel.setModes(packet.getModes());
                channel.setUserCount(packet.getUsers());
                channels.add(channel);
                return;
            }
            case RPL_LISTEND: {
                client.getEventBus().callEventAsync(new ChannelListEvent(channels));
                return;
            }
        }

        super.channelRead(ctx, msg);
    }

}
