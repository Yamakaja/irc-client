package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientLocalServerConnectionInfo extends PacketCommandResponse {

    private int clients;
    private int servers;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        clients = Integer.parseInt(split[5]);
        servers = Integer.parseInt(split[8]);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_LUSERME;
    }

    public int getClients() {
        return clients;
    }

    public int getServers() {
        return servers;
    }

}
