package me.yamakaja.irc.bot.listener;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.network.event.server.NickServAvailableEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class NickServAvailabilityListener implements Listener {

    @Inject
    private IRCBot bot;

    private boolean loggedIn;

    @EventHandler
    public void onNickServAvaialable(NickServAvailableEvent e) {
        if(loggedIn)
            return;

        bot.getClient().message("NickServ", "IDENTIFY " + bot.getNickServPassword());
        loggedIn = true;
    }

}
