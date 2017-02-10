package me.yamakaja.irc.bot;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.netty.channel.ChannelFuture;
import me.yamakaja.irc.bot.listener.CommandListener;
import me.yamakaja.irc.bot.listener.NickServAvailabilityListener;
import me.yamakaja.irc.bot.listener.RegistrationListener;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.util.NameUtils;
import net.lahwran.fevents.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Yamakaja on 10.02.17.
 */
@Singleton
public class IRCBot {

    private String nick;
    private String nickServPassword;

    private String host;
    private int port;

    private IRCClient client;

    private List<String> channels = new ArrayList<>();

    @Inject
    private Injector injector;

    public IRCBot() {
    }

    public void setNick(String nick, String nickServPassword) {
        this.nick = nick;
        this.nickServPassword = nickServPassword;
    }

    public void setRemote(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @throws RuntimeException If the connection failed
     */
    public void launch() {
        client = injector.getInstance(IRCClient.class);
        client.setRemote(host, port);

        registerListeners();

        if (!client.connect())
            throw new RuntimeException("An error occurred while trying to launch!");

        client.setNick(nick);
        client.sendUser("bot", (byte) 0, "*", "I'm a bot, made by Yamakaja");
    }

    public String getNick() {
        return nick;
    }

    public String getNickServPassword() {
        return nickServPassword;
    }

    public IRCClient getClient() {
        return client;
    }

    public Injector getInjector() {
        return injector;
    }

    @SuppressWarnings("unchecked")
    private void registerListeners() {
        Class<Listener>[] classes = new Class[]{
                NickServAvailabilityListener.class,
                CommandListener.class,
                RegistrationListener.class
        };
        Arrays.stream(classes).forEach(clazz -> client.getEventBus().registerListener(injector.getInstance(clazz)));
    }

    public ChannelFuture getCloseFuture() {
        return client.getCloseFuture();
    }

    public boolean hasAdminPrivileges(String user) {
        return NameUtils.getNick(user).equals("Yamakaja");
    }

    public void addChannel(String name) {
        this.channels.add(name);
    }

    public List<String> getChannels() {
        return channels;
    }

}
