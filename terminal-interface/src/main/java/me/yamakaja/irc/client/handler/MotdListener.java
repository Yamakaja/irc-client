package me.yamakaja.irc.client.handler;

import com.google.inject.Inject;
import me.yamakaja.irc.client.network.IRCNetworkClient;
import me.yamakaja.irc.client.network.event.packet.EndOfMotdEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class MotdListener implements Listener {

    @Inject
    private IRCNetworkClient client;

    @EventHandler
    public void onMotdFinish(EndOfMotdEvent e) {
        client.getMotd().forEach(System.out::println);
    }

}
