package me.yamakaja.irc.client.handler;

import com.google.inject.Inject;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.ServerConnectEvent;
import me.yamakaja.irc.client.network.event.ServerDisconnectEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class ServerConnectionEventHandler implements Listener {

    @Inject
    private IRCNetworkClient client;

    @EventHandler
    public void onServerConnect(ServerConnectEvent e) {
        System.out.println("Connected to " + client.getHost() + ":" + client.getPort());
    }

    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent e) {
        System.out.println("Disconnected from " + client.getHost() + ":" + client.getPort());
        System.out.println("Press enter to exit!");
    }

}
