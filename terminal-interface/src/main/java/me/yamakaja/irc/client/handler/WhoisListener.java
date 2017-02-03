package me.yamakaja.irc.client.handler;

import com.google.inject.Inject;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.packet.EndOfWhoisEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class WhoisListener implements Listener {

    @Inject
    private IRCNetworkClient client;

    @EventHandler
    public void onWhois(EndOfWhoisEvent e) {
        System.out.println(client.getUser(e.getNick()).toString());
    }

}
