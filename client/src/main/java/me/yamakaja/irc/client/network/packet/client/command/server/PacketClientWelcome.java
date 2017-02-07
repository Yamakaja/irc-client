package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class PacketClientWelcome extends PacketCommandResponse {

    private String user;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.user = split[split.length - 1];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_WELCOME;
    }

    public String getUser() {
        return user;
    }

}
