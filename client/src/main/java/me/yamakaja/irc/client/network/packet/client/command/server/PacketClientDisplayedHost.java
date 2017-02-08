package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

import static me.yamakaja.irc.client.network.packet.client.ClientboundPacketType.RPL_DISPHOST;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class PacketClientDisplayedHost extends PacketCommandResponse {

    private String displayedHost;

    @Override
    public void read(String data) {
        displayedHost = data.split(" ")[3];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return RPL_DISPHOST;
    }

    public String getDisplayedHost() {
        return displayedHost;
    }

}
