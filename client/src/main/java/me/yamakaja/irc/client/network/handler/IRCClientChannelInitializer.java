package me.yamakaja.irc.client.network.handler;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import me.yamakaja.irc.client.network.packet.codec.PacketDecoder;
import me.yamakaja.irc.client.network.packet.codec.PacketEncoder;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class IRCClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Inject
    private Injector injector;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addFirst(new LineBasedFrameDecoder(1000, true, false))
                //Decode incoming traffic
                .addLast(injector.getInstance(PacketDecoder.class))

                //Application logic
                .addLast(injector.getInstance(PingPacketHandler.class))
                .addLast(injector.getInstance(WhoisPacketHandler.class))
                .addLast(injector.getInstance(MotdPacketHandler.class))
                .addLast(injector.getInstance(ChannelPacketHandler.class))
                .addLast(injector.getInstance(UserPacketHandler.class))
                .addLast(injector.getInstance(InitialPacketHandler.class))
                .addLast(injector.getInstance(ChannelListPacketHandler.class))

                .addLast(injector.getInstance(EventPacketHandler.class))


                .addLast(injector.getInstance(ChannelCloseHandler.class))

                //Encode outgoing traffic
                .addLast(injector.getInstance(PacketEncoder.class));
    }

}
