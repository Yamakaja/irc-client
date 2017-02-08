package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.packet.ServerMessageEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class ServerMessageListener implements Listener {

    @EventHandler
    public void onMessage(ServerMessageEvent e) {
        System.err.println(" <-- " + e.getPacket().getMessage());
    }

}
