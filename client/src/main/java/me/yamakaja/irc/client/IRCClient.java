package me.yamakaja.irc.client;

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
import me.yamakaja.irc.client.network.event.server.ServerConnectEvent;
import me.yamakaja.irc.client.network.handler.IRCClientChannelInitializer;
import me.yamakaja.irc.client.network.packet.server.*;
import net.lahwran.fevents.ThreadedEventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yamakaja on 01.02.17.
 */
@Singleton
public class IRCClient {

    private String host;
    private int port;

    private Channel networkChannel;

    private ThreadedEventBus eventBus;

    private Map<String, ChatChannel> channels = new HashMap<>();

    private String user;
    private String serverHost;
    private String displayedUser;

    private String created;

    private String daemon;

    private String userModes;
    private String channelModes;

    private List<String> serverOptions = new ArrayList<>();

    @Inject
    private Injector injector;

    public IRCClient() {
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

    public void shutdown() {
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

    /**
     * Sends a USER packet to the server with the required arguments
     *
     * @param name     The username / ident
     * @param mode     The initial client modes
     * @param host     *
     * @param realname The "realname" displayed in WHOIS
     */
    public void sendUser(String name, byte mode, String host, String realname) {
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
        ChatChannel channel = new ChatChannel(name);
        injector.injectMembers(channel);
        channels.put(name, channel);
        return channel;
    }

    /**
     * @param user Checks if the passed user is the irc bot. This takes either the full [nick]![ident]@[host] or just a nick
     * @return Whether or not the checked user actually is the bot
     * @throws NullPointerException when given a null user
     */
    public boolean isUser(String user) {
        if (user.contains("!")) {
            return user.equals(displayedUser) || user.equals(this.user);
        } else {
            return user.equals(this.user.substring(0, this.user.indexOf('!')));
        }
    }

    /**
     * Internal use only!
     *
     * @param serverHost
     */
    public void setServerName(String serverHost) {
        this.serverHost = serverHost;
    }

    /**
     * Usually the server you actually connected to within a network
     *
     * @return The name of the server you're connected to
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * @return The full user and host mask
     */
    public String getUser() {
        return user;
    }

    /**
     * Internal use only!
     *
     * @param user The new hostmask to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return A string that represents the date the irc server was started, not when it was first launched. Could be used for uptime measurement
     */
    public String getCreated() {
        return created;
    }

    /**
     * Internal use only!
     *
     * @param created Creation time
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The servers irc daemon (version)
     */
    public String getDaemon() {
        return daemon;
    }

    /**
     * Internal use only!
     *
     * @param daemon version
     */
    public void setDaemon(String daemon) {
        this.daemon = daemon;
    }

    /**
     * @return The host displayed by the server. May differ from the actual user hostname (Hostmasking / IP Protection)
     */
    public String getDisplayedUser() {
        return displayedUser;
    }

    /**
     * Internal use only!
     *
     * @param displayedUser host
     */
    public void setDisplayedUser(String displayedUser) {
        this.displayedUser = displayedUser;
    }

    /**
     * @return Whether or not the displayed hostname differs from the users actual hostname
     */
    public boolean hasDifferentDisplayHostname() {
        return displayedUser != null && !displayedUser.equals(user.substring(user.indexOf('@') + 1));
    }

    public void sendWhois(String nick) {
        networkChannel.writeAndFlush(new PacketServerWhois(nick));
    }

    public String getUserModes() {
        return userModes;
    }

    public void setUserModes(String userModes) {
        this.userModes = userModes;
    }

    public String getChannelModes() {
        return channelModes;
    }

    public void setChannelModes(String channelModes) {
        this.channelModes = channelModes;
    }

    public List<String> getServerOptions() {
        return serverOptions;
    }

    public void sendPacket(ServerboundPacket packet) {
        networkChannel.writeAndFlush(packet);
    }

    public void setModes(String target, boolean add, String modes) {
        networkChannel.writeAndFlush(new PacketServerMode(target, add, modes));
    }

    /**
     * Send a private message to somebody
     *
     * @param target  Who to send it to
     * @param message The message to send
     */
    public void message(String target, String message) {
        networkChannel.writeAndFlush(new PacketServerMessage(target, message));
    }

    public ChannelFuture getCloseFuture() {
        return networkChannel.closeFuture();
    }

}
