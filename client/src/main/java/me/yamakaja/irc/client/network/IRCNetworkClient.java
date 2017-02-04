package me.yamakaja.irc.client.network;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.chat.ChatUser;
import me.yamakaja.irc.client.network.event.ServerConnectEvent;
import me.yamakaja.irc.client.network.handler.IRCClientChannelInitializer;
import me.yamakaja.irc.client.network.packet.server.PacketServerNick;
import me.yamakaja.irc.client.network.packet.server.PacketServerRaw;
import me.yamakaja.irc.client.network.packet.server.PacketServerUser;
import me.yamakaja.irc.client.network.packet.server.PacketServerWhois;
import net.lahwran.fevents.ThreadedEventBus;

import java.util.*;

/**
 * Created by Yamakaja on 01.02.17.
 */
@Singleton
public class IRCNetworkClient {

    private String host;
    private int port;

    private Channel networkChannel;

    private ThreadedEventBus eventBus;

    private Map<String, ChatChannel> channels = new HashMap<>();
    private Map<String, ChatUser> users = new HashMap<>();

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
            this.networkChannel = future.channel();
            boolean success = future.sync().isSuccess();
            if (success)
                this.eventBus.callEvent(new ServerConnectEvent());
            return success;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void cleanup() {
        try {
            if (networkChannel.isOpen())
                networkChannel.writeAndFlush(new PacketServerRaw("QUIT"));
            networkChannel.disconnect();
            networkChannel.eventLoop().parent().shutdownGracefully().syncUninterruptibly();
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
        networkChannel.writeAndFlush(new PacketServerNick(name));
    }

    public void setUser(String name, byte mode, String host, String realname) {
        networkChannel.writeAndFlush(new PacketServerUser(name, mode, host, realname));
    }

    public void sendRaw(String command) {
        networkChannel.writeAndFlush(new PacketServerRaw(command));
    }

    public boolean isConnected() {
        return networkChannel.isOpen();
    }

    public Map<String, ChatChannel> getChannels() {
        return channels;
    }

    public ChatChannel getChannel(String name) {
        if (channels.containsKey(name))
            return channels.get(name);
        ChatChannel channel = injector.getInstance(ChatChannel.class);
        channels.put(name, channel);
        return channel;
    }

    public ChatUser getUser(String nick) {
        if (users.containsKey(nick))
            return users.get(nick);
        ChatUser user = injector.getInstance(ChatUser.class);
        users.put(nick, user);
        return user;
    }

    public void sendWhois(String nick) {
        networkChannel.writeAndFlush(new PacketServerWhois(nick));
    }

}
