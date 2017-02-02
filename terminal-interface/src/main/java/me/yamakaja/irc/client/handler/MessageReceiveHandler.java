package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.packet.ServerMessageReceiveEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class MessageReceiveHandler implements Listener {

    @EventHandler
    public void onMessageReceive(ServerMessageReceiveEvent e) {

    }

}
