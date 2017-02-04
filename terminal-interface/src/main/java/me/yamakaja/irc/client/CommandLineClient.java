package me.yamakaja.irc.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.yamakaja.irc.client.handler.MessageReceiveHandler;
import me.yamakaja.irc.client.handler.MotdListener;
import me.yamakaja.irc.client.handler.ServerConnectionEventHandler;
import me.yamakaja.irc.client.handler.WhoisListener;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.ServerConnectEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class CommandLineClient {

    private Injector injector = Guice.createInjector();

    private String host;
    private int port;

    public CommandLineClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void launch() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Yamakaja's commandline IRC client!");
        IRCNetworkClient ircClient = injector.getInstance(IRCNetworkClient.class);
        ircClient.setRemote(host, port);

        Arrays.stream(new Class[]{ServerConnectionEventHandler.class, MessageReceiveHandler.class, WhoisListener.class, MotdListener.class})
                .forEach(listener -> ircClient.getEventBus().registerListener((Listener) injector.getInstance(listener)));

        if (!ircClient.connect()) {
            System.out.println("An error occurred while trying to connect!");
            return;
        }

        ircClient.setNick("Yamakaja_");
        ircClient.setUser("Yamakaja__", (byte) 0, "*", "It's da fake Yamakaja!");

        while (ircClient.isConnected()) {
            ircClient.sendRaw(scanner.nextLine());
        }

        ircClient.cleanup();

    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        System.out.println("Connected successfully!");
    }

}
