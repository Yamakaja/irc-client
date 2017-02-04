package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.command.motd.PacketClientMotdEnd;

import java.util.List;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class EndOfMotdEvent extends PacketEvent<PacketClientMotdEnd> {

    private List<String> motd;

    public EndOfMotdEvent(List<String> motd) {
        this.motd = motd;
    }

    public List<String> getMotd() {
        return motd;
    }

}
