package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import me.yamakaja.irc.client.network.codec.PacketDecoder;
import me.yamakaja.irc.client.network.codec.PacketEncoder;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class IRCClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Inject
    private Injector injector;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addFirst("frameDecoder", new LineBasedFrameDecoder(1000, true, false))
                .addAfter("frameDecoder", "packetDecoder", injector.getInstance(PacketDecoder.class))
                .addAfter("packetDecoder", "pingResponder", injector.getInstance(PingPacketHandler.class))
                .addAfter("pingResponder", "packetHandler", injector.getInstance(InboundPacketHandler.class))
                .addLast(injector.getInstance(ChannelCloseHandler.class))
                .addLast(injector.getInstance(PacketEncoder.class));
    }

}
