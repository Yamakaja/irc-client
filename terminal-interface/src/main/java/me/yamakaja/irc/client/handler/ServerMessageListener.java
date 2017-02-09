package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.packet.*;
import me.yamakaja.irc.client.network.event.server.ServerNoticeEvent;
import me.yamakaja.irc.client.network.event.server.ServerUsersInfoEvent;
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

    @EventHandler
    public void onUsers(GroupedUsersOnlineEvent e) {
        System.out.println("Users: " + e.getPacket().getUsers() + ", " + e.getPacket().getOthersType() + ": " + e.getPacket().getOthers());
    }

    @EventHandler
    public void onGlobalUsers(GlobalUsersOnlineEvent e) {
        System.out.println("[Global] " + e.getPacket().getCurrent() + " (Max: " + e.getPacket().getMax() + ")");
    }

    @EventHandler
    public void onLocalUsers(LocalUsersOnlineEvent e) {
        System.out.println("[LOCAL] " + e.getPacket().getCurrent() + " (Max: " + e.getPacket().getMax() + ")");
    }

    @EventHandler
    public void onConnectionInfo(ServerUsersInfoEvent e) {
        System.out.println("Server is directly connected to " + e.getPacket().getServers() + " other server(s)");
    }

    @EventHandler
    public void onOperators(OperatorCountEvent e) {
        System.out.println("Ops: " + e.getPacket().getCount());
    }

    @EventHandler
    public void onChannels(ChannelCountEvent e) {
        System.out.println("Channels: " + e.getPacket().getCount());
    }

}
