package me.yamakaja.irc.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.yamakaja.irc.client.handler.*;
import me.yamakaja.irc.client.network.event.server.ServerConnectEvent;
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
        IRCClient ircClient = injector.getInstance(IRCClient.class);
        ircClient.setRemote(host, port);

        Arrays.stream(new Class[]{ServerConnectionEventHandler.class, WhoisListener.class, MotdListener.class, NamesListener.class, UserListener.class})
                .forEach(listener -> ircClient.getEventBus().registerListener((Listener) injector.getInstance(listener)));

        if (!ircClient.connect()) {
            System.out.println("An error occurred while trying to connect!");
            return;
        }

        ircClient.setNick("Yamakaja_");
        ircClient.sendUser("Yamakaja__", (byte) 0, "*", "It's da fake Yamakaja!");

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
