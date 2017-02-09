package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.packet.ServerMessageEvent;
import me.yamakaja.irc.client.network.event.server.ServerNoticeEvent;
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

    @EventHandler
    public void onNotice(ServerNoticeEvent e) {
        System.out.println("[NOTICE] <" + e.getTarget() + "> " + e.getMessage());
    }

}
