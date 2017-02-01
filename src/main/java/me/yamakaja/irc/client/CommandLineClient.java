package me.yamakaja.irc.client;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.yamakaja.irc.client.handler.MessageReceiveHandler;
import me.yamakaja.irc.client.handler.ServerConnectHandler;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.ServerConnectEvent;
import net.lahwran.fevents.EventHandler;

import java.util.Scanner;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class CommandLineClient {

    @Inject
    private Injector injector;

    public void launch() {
        Scanner scanner = new Scanner(System.in);

        String host;
        int port;

        System.out.println("Welcome to Yamakaja's commandline IRC client!");
        System.out.print("Host: ");
        host = scanner.nextLine();
        while (true) try {
            System.out.print("Port: ");
            port = scanner.nextInt();
            break;
        } catch (Exception ignored) {
        }

        IRCNetworkClient ircClient = injector.getInstance(IRCNetworkClient.class);
        ircClient.setRemote(host, port);
        ircClient.getEventBus().registerListener(injector.getInstance(ServerConnectHandler.class));
        ircClient.getEventBus().registerListener(injector.getInstance(MessageReceiveHandler.class));
        if (!ircClient.connect()) {
            System.out.println("An error occurred while trying to connect!");
            return;
        }

        ircClient.setNick("Yamakaja_");
        ircClient.setUser("Yamakaja_", (byte)0, "*", "It's da fake Yamakaja!");

        while(ircClient.isConnected()) {
            ircClient.sendRaw(scanner.nextLine());
        }

        System.out.println("Shutting down!");
        ircClient.disconnect();

    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        System.out.println("Connected successfully!");
    }

}
