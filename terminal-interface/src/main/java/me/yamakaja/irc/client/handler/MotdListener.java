package me.yamakaja.irc.client.handler;

import com.google.inject.Inject;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.server.MotdEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class MotdListener implements Listener {

    @Inject
    private IRCClient client;

    @EventHandler
    public void onMotdFinish(MotdEvent e) {
        e.getMotd().forEach(System.out::println);
        System.out.println(client.getUser() + " on " + client.getHost() + " running " + client.getDaemon() + " (running since " + client.getCreated() + ")");
        System.out.println("Server options: ");
        System.out.println(client.getServerOptions().toString());
        System.out.println("Available user modes: " + client.getUserModes());
        System.out.println("Available channel modes: " + client.getChannelModes());
    }

}
