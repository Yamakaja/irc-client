package me.yamakaja.irc.client.network.event.server;

import net.lahwran.fevents.Event;

import java.util.List;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class MotdEvent extends Event {

    private List<String> motd;

    public MotdEvent(List<String> motd) {
        this.motd = motd;
    }

    public List<String> getMotd() {
        return motd;
    }

}
