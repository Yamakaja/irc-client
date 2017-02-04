package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.motd.PacketClientMotdLine;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class MotdPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCNetworkClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket)msg;

        switch(packet.getPacketType()) {
            case RPL_MOTDSTART:
                client.getMotd().clear();
                return;
            case RPL_MOTD:
                client.getMotd().add(((PacketClientMotdLine)packet).getLine());
                return;
        }

        super.channelRead(ctx, msg);
    }

}
