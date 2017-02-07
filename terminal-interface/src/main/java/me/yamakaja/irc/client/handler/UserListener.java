package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.channel.UserJoinEvent;
import me.yamakaja.irc.client.network.event.channel.UserPartEvent;
import me.yamakaja.irc.client.network.event.user.UserNickEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class UserListener implements Listener {

    @EventHandler
    public void onJoin(UserJoinEvent e) {
        System.out.println(e.getChannel().getName() + " +++ " + e.getUser());
    }

    @EventHandler
    public void onPart(UserPartEvent e) {
        System.out.println(e.getChannel().getName() + " --- " + e.getUser() + (e.getReason() != null ? " " + e.getReason() : ""));
    }

    @EventHandler
    public void onNickChange(UserNickEvent e) {
        System.out.println(e.getUser() + " --> " + e.getTo());
    }

}
