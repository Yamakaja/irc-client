package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.network.event.channel.ChannelNamesEvent;
import me.yamakaja.irc.client.network.event.channel.TopicEvent;
import me.yamakaja.irc.client.network.event.channel.UserJoinEvent;
import me.yamakaja.irc.client.network.event.channel.UserPartEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientJoin;
import me.yamakaja.irc.client.network.packet.client.action.PacketClientPart;
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

        ChatChannel channel;
        switch (((ClientboundPacket) msg).getPacketType()) {
            case RPL_TOPIC: {
                handleTopic((PacketClientTopic) msg);
                return;
            }
            case RPL_TOPICINFO: {
                PacketClientTopicSetInformation packet = (PacketClientTopicSetInformation) msg;
                channel = client.getChannel(packet.getChannel());
                channel.setTopicSetter(packet.getSetter());
                channel.setTopicTime(packet.getTime());
                client.getEventBus().callEventAsync(new TopicEvent(channel));
                return;
            }
            case RPL_NAMREPLY: {
                PacketClientNames packet = (PacketClientNames) msg;
                channel = client.getChannel(packet.getChannel());
                channel.addUsers(packet.getNicks());
                return;
            }
            case RPL_ENDOFNAMES: {
                PacketClientNamesEnd packet = (PacketClientNamesEnd) msg;
                channel = client.getChannel(packet.getChannel());
                channel.finishUsers();
                client.getEventBus().callEventAsync(new ChannelNamesEvent(channel));
                return;
            }
            case JOIN: {
                PacketClientJoin packet = (PacketClientJoin) msg;

                if (client.isUser(packet.getSender())) {
                    client.getChannel(packet.getChannel()).setJoined(true);
                }

                client.getEventBus().callEventAsync(new UserJoinEvent(packet.getSender(), client.getChannel(packet.getChannel())));
                return;
            }
            case PART: {
                PacketClientPart packet = (PacketClientPart) msg;

                if (client.isUser(packet.getSender())) {
                    client.getChannel(packet.getChannel()).setJoined(false);
                }

                client.getEventBus().callEventAsync(new UserPartEvent(packet.getSender(), client.getChannel(packet.getChannel()), packet.getReason()));
                return;
            }
        }

        super.channelRead(ctx, msg);
    }

    private void handleTopic(PacketClientTopic packetClientTopic) {
        client.getChannel(packetClientTopic.getChannel()).setTopic(packetClientTopic.getTopic());
    }

}
