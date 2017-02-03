package me.yamakaja.irc.client.network.packet.client.command.whois;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.CommandResponse;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class PacketClientWhoisEnd extends CommandResponse {

    private String nick;

    @Override
    public void read(String data) {
        nick = data.split(" ")[3];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_ENDOFWHOIS;
    }

    public String getNick() {
        return nick;
    }
}
