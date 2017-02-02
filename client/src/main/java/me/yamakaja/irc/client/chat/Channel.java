package me.yamakaja.irc.client.chat;

import com.google.inject.Inject;
import me.yamakaja.irc.client.network.IRCNetworkClient;

import java.util.List;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class Channel {

    private String name;

    private List<User> users;

    @Inject
    private IRCNetworkClient ircClient;

}
