package me.yamakaja.irc.bot.listener;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.network.event.server.ServerUserRegisteredEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class RegistrationListener implements Listener {

    @Inject
    private IRCBot bot;

    @EventHandler
    public void onConnectionReady(ServerUserRegisteredEvent e) {
        bot.getClient().setModes(bot.getNick(), true, "B");

        bot.getChannels().forEach(channel -> bot.getClient().getChannel(channel).join());
    }

}
