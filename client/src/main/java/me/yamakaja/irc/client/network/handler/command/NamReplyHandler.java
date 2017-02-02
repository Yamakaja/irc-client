package me.yamakaja.irc.client.network.handler.command;

import me.yamakaja.irc.client.network.event.packet.NamReplyReceiveEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class NamReplyHandler implements Listener {

    @EventHandler
    public void onNamReply(NamReplyReceiveEvent e) {
        System.out.println(e.getPacket().getChannel() + ": " + e.getPacket().getNicks());
    }

}
