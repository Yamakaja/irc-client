package me.yamakaja.irc.client.handler;

import com.google.inject.Inject;
import me.yamakaja.irc.client.IRCClient;
import me.yamakaja.irc.client.network.event.channel.UserJoinEvent;
import me.yamakaja.irc.client.network.event.channel.UserPartEvent;
import me.yamakaja.irc.client.network.event.user.UserNickEvent;
import me.yamakaja.irc.client.util.NameUtils;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class UserListener implements Listener {

    @Inject
    private IRCClient client;

    @EventHandler
    public void onJoin(UserJoinEvent e) {
        if(client.isUser(e.getUser())) {
            System.out.println("Joined " + e.getChannel().getName() + "!");
            return;
        }

        System.out.println("[" + e.getChannel().getName() + "] [+] " + NameUtils.getNick(e.getUser()) + " (" + NameUtils.stripNick(e.getUser()) + ")");
    }

    @EventHandler
    public void onPart(UserPartEvent e) {
        if(client.isUser(e.getUser())) {
            System.out.println("Left " + e.getChannel().getName() + "!");
            return;
        }

        System.out.println("[" + e.getChannel().getName() + "] [-] " + NameUtils.getNick(e.getUser()) + " (" + NameUtils.stripNick(e.getUser()) + ")" + (e.getReason() != null ? " " + e.getReason() : ""));
    }

    @EventHandler
    public void onNickChange(UserNickEvent e) {
        System.out.println(e.getUser() + " --> " + e.getTo());
    }

}
