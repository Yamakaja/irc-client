package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

import static me.yamakaja.irc.client.network.packet.client.ClientboundPacketType.RPL_LUSERCHANNELS;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientChannels extends PacketCommandResponse {

    private int count;

    @Override
    public void read(String data) {
        this.count = Integer.parseInt(data.split(" ")[3]);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return RPL_LUSERCHANNELS;
    }

    public int getCount() {
        return count;
    }

}
