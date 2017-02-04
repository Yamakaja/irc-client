package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.server.WhoisEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class WhoisListener implements Listener {

    @EventHandler
    public void onWhois(WhoisEvent e) {
        System.out.println(e.getUser().toString());
    }

}
