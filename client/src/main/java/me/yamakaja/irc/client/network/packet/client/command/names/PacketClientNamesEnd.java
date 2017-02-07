package me.yamakaja.irc.client.network.packet.client.command.names;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class PacketClientNamesEnd extends PacketCommandResponse {

    private String channel;

    @Override
    public void read(String data) {
        this.channel = data.split(" ")[3];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_ENDOFNAMES;
    }

    public String getChannel() {
        return channel;
    }
}
