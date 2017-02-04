package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.channel.ChannelNamesEvent;
import me.yamakaja.irc.client.network.event.channel.TopicEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.names.PacketClientNames;
import me.yamakaja.irc.client.network.packet.client.command.names.PacketClientNamesEnd;
import me.yamakaja.irc.client.network.packet.client.command.topic.PacketClientTopic;
import me.yamakaja.irc.client.network.packet.client.command.topic.PacketClientTopicSetInformation;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class ChannelPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket) msg;

        ChatChannel channel;
        switch (packet.getPacketType()) {
            case RPL_TOPIC:
                handleTopic((PacketClientTopic) packet);
                return;
            case RPL_TOPICINFO:
                PacketClientTopicSetInformation info = (PacketClientTopicSetInformation) msg;
                channel = client.getChannel(info.getChannel());
                channel.setTopicSetter(info.getSetter());
                channel.setTopicTime(info.getTime());
                client.getEventBus().callEventAsync(new TopicEvent(channel));
                return;
            case RPL_NAMREPLY:
                PacketClientNames names = (PacketClientNames) msg;
                channel = client.getChannel(names.getChannel());
                channel.addUsers(names.getNicks());
                return;
            case RPL_ENDOFNAMES:
                PacketClientNamesEnd namesEnd = (PacketClientNamesEnd) msg;
                channel = client.getChannel(namesEnd.getChannel());
                channel.finishUsers();
                client.getEventBus().callEventAsync(new ChannelNamesEvent(channel));
                return;
        }

        super.channelRead(ctx, msg);
    }

    private void handleTopic(PacketClientTopic packetClientTopic) {
        client.getChannel(packetClientTopic.getChannel()).setTopic(packetClientTopic.getTopic());
    }

}
