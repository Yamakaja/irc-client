package me.yamakaja.irc.client.network.packet.client.command.list;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketClientChannelsBegin extends PacketCommandResponse {

    @Override
    public void read(String data) {

    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_LISTSTART;
    }

}
