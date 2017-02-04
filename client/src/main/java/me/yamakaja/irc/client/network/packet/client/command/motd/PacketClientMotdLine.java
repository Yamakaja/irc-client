package me.yamakaja.irc.client.network.packet.client.command.motd;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.CommandResponse;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class PacketClientMotdLine extends CommandResponse {

    private String line;

    @Override
    public void read(String data) {
        line = data.substring(data.indexOf(":", 1) + 2);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_MOTD;
    }

    public String getLine() {
        return line;
    }

}
