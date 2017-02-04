package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.command.whois.PacketClientWhoisEnd;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class EndOfWhoisEvent extends PacketEvent<PacketClientWhoisEnd> {

    public String getNick() {
        return getPacket().getNick();
    }

}
