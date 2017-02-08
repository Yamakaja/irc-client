package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class PacketClientHost extends PacketCommandResponse {

    private String serverHost;
    private String daemon;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        serverHost = split[6].substring(0, split[6].length() - 1);
        daemon = split[9];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_YOURHOST;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getDaemon() {
        return daemon;
    }

}
