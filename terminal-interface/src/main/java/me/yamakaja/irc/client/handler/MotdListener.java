package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.server.MotdEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class MotdListener implements Listener {

    @EventHandler
    public void onMotdFinish(MotdEvent e) {
        e.getMotd().forEach(System.out::println);
    }

}
