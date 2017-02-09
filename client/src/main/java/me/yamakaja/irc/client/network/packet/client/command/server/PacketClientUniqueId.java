package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientUniqueId extends PacketCommandResponse {

    private String uniqueId;

    @Override
    public void read(String data) {
        this.uniqueId = data.split(" ")[3];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_UNIQUEID;
    }

    public String getUniqueId() {
        return uniqueId;
    }

}
