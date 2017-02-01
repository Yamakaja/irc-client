package me.yamakaja.irc.client.network;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import me.yamakaja.irc.client.network.event.ServerConnectEvent;
import me.yamakaja.irc.client.network.event.ServerDisconnectEvent;
import me.yamakaja.irc.client.network.codec.PacketDecoder;
import me.yamakaja.irc.client.network.codec.PacketEncoder;
import me.yamakaja.irc.client.network.handler.IRCClientChannelInitializer;
import me.yamakaja.irc.client.network.handler.InboundPacketHandler;
import me.yamakaja.irc.client.network.handler.PingPacketHandler;
import me.yamakaja.irc.client.network.packet.server.PacketServerNick;
import me.yamakaja.irc.client.network.packet.server.PacketServerRaw;
import me.yamakaja.irc.client.network.packet.server.PacketServerUser;
import net.lahwran.fevents.ThreadedEventBus;

/**
 * Created by Yamakaja on 01.02.17.
 */
@Singleton
public class IRCNetworkClient {

    private String host;
    private int port;

    private Channel channel;

    private ThreadedEventBus eventBus;

    @Inject
    private Injector injector;

    public IRCNetworkClient() {
        eventBus = new ThreadedEventBus();
    }

    public void setRemote(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        EventLoopGroup eventLoopGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(injector.getInstance(IRCClientChannelInitializer.class))
                    .remoteAddress(host, port);
            ChannelFuture future = bootstrap.connect();
            this.channel = future.channel();
            boolean success = future.sync().isSuccess();
            if (success)
                eventBus.callEvent(new ServerConnectEvent());
            return success;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void cleanup() {
        try {
            channel.disconnect();
            channel.eventLoop().parent().shutdownGracefully().syncUninterruptibly();
            eventBus.callEvent(new ServerDisconnectEvent());
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public ThreadedEventBus getEventBus() {
        return eventBus;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setNick(String name) {
        channel.writeAndFlush(new PacketServerNick(name));
    }

    public void setUser(String name, byte mode, String host, String realname) {
        channel.writeAndFlush(new PacketServerUser(name, mode, host, realname));
    }

    public void sendRaw(String command) {
        channel.writeAndFlush(new PacketServerRaw(command));
    }

    public boolean isConnected() {
        return channel.isOpen();
    }
}
