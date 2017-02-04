package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.packet.EndOfMotdEvent;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.motd.PacketClientMotdLine;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class MotdPacketHandler extends ChannelInboundHandlerAdapter {

    @Inject
    private IRCNetworkClient client;

    private List<String> motd = new LinkedList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket)msg;

        switch(packet.getPacketType()) {
            case RPL_MOTDSTART:
                motd.clear();
                return;
            case RPL_MOTD:
                motd.add(((PacketClientMotdLine)packet).getLine());
                return;
            case RPL_ENDOFMOTD:
                client.getEventBus().callEventAsync(new EndOfMotdEvent(motd));
                return;
        }

        super.channelRead(ctx, msg);
    }

}
