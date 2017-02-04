package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.server.ServerDisconnectEvent;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class ChannelCloseHandler extends ChannelInboundHandlerAdapter {

    @Inject
    IRCClient client;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.getEventBus().callEventAsync(new ServerDisconnectEvent());
    }
}
