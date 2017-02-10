package me.yamakaja.irc.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.yamakaja.irc.client.chat.ChatChannel;
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

        Arrays.stream(new Class[]{
                ServerConnectionEventHandler.class,
                WhoisListener.class,
                MotdListener.class,
                NamesListener.class,
                UserListener.class,
                ServerMessageListener.class
        })
                .forEach(listener -> ircClient.getEventBus().registerListener((Listener) injector.getInstance(listener)));

        if (!ircClient.connect()) {
            System.out.println("An error occurred while trying to connect!");
            return;
        }

        ircClient.setNick("Yamakaja_");
        ircClient.sendUser("Yamakaja__", (byte) 0, "*", "It's da fake Yamakaja!");

        ChatChannel selectedChannel = null;

        String line;
        loop:
        while (ircClient.isConnected()) {
            line = scanner.nextLine();

            String[] command = line.split(" ");
            switch (command[0].toLowerCase()) {
                case "/quit":
                    break loop;

                case "/info": {

                    if (command.length > 1 && command[1].startsWith("#")) {
                        System.out.println(ircClient.getChannel(command[1]));
                        continue loop;
                    }

                    System.out.println(ircClient.getUser() + " on " + ircClient.getHost() + " running " + ircClient.getDaemon() + " (running since " + ircClient.getCreated() + ")");
                    System.out.println("Server options: ");
                    System.out.println(ircClient.getServerOptions().toString());
                    System.out.println("Available user modes: " + ircClient.getUserModes());
                    System.out.println("Available channel modes: " + ircClient.getChannelModes());
                    continue loop;
                }
                case "/join": {
                    if (command.length < 2) {
                        System.out.println("Usage: /join [Channel]");
                        continue loop;
                    }

                    if (!command[1].startsWith("#")) {
                        System.out.println("Cannot join " + command[1] + "! Not a channel!");
                        continue loop;
                    }

                    ircClient.getChannel(command[1]).join();
                    System.out.println("Attempting to join " + command[1] + "!");
                    continue loop;
                }
                case "/chat": {
                    if (command.length < 2 || !command[1].startsWith("#")) {
                        System.out.println("Usage: /chat [Channel]");
                        continue loop;
                    }

                    selectedChannel = ircClient.getChannel(command[1]);
                    continue loop;
                }
                case "/part": {
                    if (command.length < 2 || !command[1].startsWith("#")) {
                        System.out.println("Usage: /part [Channel]");
                        continue loop;
                    }

                    ChatChannel channel = ircClient.getChannel(command[1]);
                    if (!channel.isJoined()) {
                        System.out.println("You have not joined " + command[1] + " yet!");
                        continue loop;
                    }

                    if(command.length == 2) {
                        channel.part();
                    } else {
                        channel.part(line.substring(line.indexOf(' ', 6) + 1));
                    }
                    continue loop;
                }
            }

            if (selectedChannel != null && selectedChannel.isJoined()) {
                selectedChannel.sendMessage(line);
            } else if (selectedChannel != null) {
                System.out.println("You have to join " + selectedChannel.getName() + " before being able to chat!");
            }

        }

        ircClient.cleanup();
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        System.out.println("Connected successfully!");
    }

}
