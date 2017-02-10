package me.yamakaja.irc.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.handler.*;
import me.yamakaja.irc.client.network.event.server.ServerConnectEvent;
import me.yamakaja.irc.client.util.StringUtils;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

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

        final ChatChannel[] selectedChannel = {null};

        Map<String, BiConsumer<IRCClient, String[]>> commandProcessors = new HashMap<>();

        commandProcessors.put("help",
                (client, command) ->
                        System.out.println("Available commands: \n" +
                                "/quit                      - Exits the client\n" +
                                "/info <channel>            - Obtain server/channel information\n" +
                                "/join [channel]            - Join a channel\n" +
                                "/chat [channel]            - Select a channel for messaging\n" +
                                "/part [channel] <reason>   - Quit a channel. Optionally with reason\n" +
                                "/help                      - Shows this message")
        );

        commandProcessors.put("quit",
                (client, command) -> client.cleanup()
        );

        commandProcessors.put("info",
                (client, command) -> {
                    if (command.length > 1 && command[1].startsWith("#")) {
                        System.out.println(client.getChannel(command[1]));
                        return;
                    }

                    System.out.println(client.getUser() + " on " + client.getHost() + " running " + client.getDaemon() + " (running since " + client.getCreated() + ")");
                    System.out.println("Server options: ");
                    System.out.println(client.getServerOptions().toString());
                    System.out.println("Available user modes: " + client.getUserModes());
                    System.out.println("Available channel modes: " + client.getChannelModes());
                }
        );

        commandProcessors.put("join",
                (client, command) -> {
                    if (command.length < 2) {
                        System.out.println("Usage: /join [Channel]");
                        return;
                    }

                    if (!command[1].startsWith("#")) {
                        System.out.println("Cannot join " + command[1] + "! Not a channel!");
                        return;
                    }

                    client.getChannel(command[1]).join();
                    System.out.println("Attempting to join " + command[1] + "!");
                }
        );

        commandProcessors.put("chat",
                (client, command) -> {
                    if (command.length < 2 || !command[1].startsWith("#")) {
                        System.out.println("Usage: /chat [Channel]");
                        return;
                    }

                    selectedChannel[0] = client.getChannel(command[1]);
                }
        );

        commandProcessors.put("part",
                (client, command) -> {
                    if (command.length < 2 || !command[1].startsWith("#")) {
                        System.out.println("Usage: /part [Channel]");
                        return;
                    }

                    ChatChannel channel = client.getChannel(command[1]);
                    if (!channel.isJoined()) {
                        System.out.println("You have not joined " + command[1] + " yet!");
                        return;
                    }

                    if (command.length == 2) {
                        channel.part();
                    } else {
                        channel.part(StringUtils.join(command, " ", 2, command.length - 1));
                    }
                }
        );

        commandProcessors.put("raw",
                (client, command) -> {
                    if(command.length < 2) {
                        System.out.println("Usage: /raw <command>");
                        return;
                    }

                    client.sendRaw(StringUtils.join(command, " ", 1, command.length - 1));
                });

        String line;
        while (ircClient.isConnected()) {
            line = scanner.nextLine();

            if (line.startsWith("/")) {
                String[] commandAndArgs = line.split(" ");
                BiConsumer<IRCClient, String[]> cmd = commandProcessors.get(commandAndArgs[0].substring(1).toLowerCase());

                if (cmd == null)
                    System.out.println("Unknown command! Try /help for a list of commands!");
                else
                    cmd.accept(ircClient, commandAndArgs);

                continue;
            }


            if (selectedChannel[0] != null && selectedChannel[0].isJoined()) {
                selectedChannel[0].sendMessage(line);
            } else if (selectedChannel[0] != null) {
                System.out.println("You have to join " + selectedChannel[0].getName() + " before being able to chat!");
            }

        }

        ircClient.cleanup();
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        System.out.println("Connected successfully!");
    }

}
