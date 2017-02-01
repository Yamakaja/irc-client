package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.ServerDisconnectEvent;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class ChannelCloseHandler extends ChannelInboundHandlerAdapter {

    @Inject
    IRCNetworkClient client;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.getEventBus().callEventAsync(new ServerDisconnectEvent());
    }
}
