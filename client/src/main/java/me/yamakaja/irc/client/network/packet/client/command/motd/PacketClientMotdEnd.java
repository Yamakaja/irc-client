package me.yamakaja.irc.client.network.packet.client.command.motd;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class PacketClientMotdEnd extends PacketCommandResponse {

    @Override
    public void read(String data) {

    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_ENDOFMOTD;
    }

}
