package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.server.*;

import java.util.Arrays;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class InitialPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        switch (((ClientboundPacket) msg).getPacketType()) {
            case RPL_WELCOME:
                client.setUser(((PacketClientWelcome) msg).getUser());
                return;
            case RPL_YOURHOST: {
                PacketClientHost packet = (PacketClientHost) msg;
                client.setServerName(packet.getServerHost());
                client.setDaemon(packet.getDaemon());
                return;
            }
            case RPL_CREATED:
                client.setCreated(((PacketClientCreated) msg).getCreationTime());
                return;
            case RPL_MYINFO: {
                PacketClientServerInfo packet = (PacketClientServerInfo) msg;
                client.setServerName(packet.getName());
                client.setDaemon(packet.getVersion());
                client.setUserModes(packet.getUserModes());
                client.setChannelModes(packet.getChannelModes());
                return;
            }
            case RPL_SERVEROPTIONS:
                client.getServerOptions().addAll(Arrays.asList(((PacketClientServerOptions) msg).getOptions()));
                return;
            case RPL_DISPHOST:
                client.setDisplayedUser(client.getUser().substring(0, client.getUser().indexOf('@') + 1) + ((PacketClientDisplayedHost)msg).getDisplayedHost());
                return;
        }

        super.channelRead(ctx, msg);
    }
}
