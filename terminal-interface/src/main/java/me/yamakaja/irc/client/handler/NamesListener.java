package me.yamakaja.irc.client.handler;

import me.yamakaja.irc.client.network.event.channel.ChannelNamesEvent;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class NamesListener implements Listener {

    @EventHandler
    public void onNamesEvent(ChannelNamesEvent event) {
        System.out.println("Users in " + event.getChannel().getName() + ": " + event.getChannel().getUsers());
    }

}
