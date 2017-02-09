package me.yamakaja.irc.client.network.event.server;

import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class ServerNoticeEvent extends Event {

    private String target;
    private String message;

    public ServerNoticeEvent(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

}
