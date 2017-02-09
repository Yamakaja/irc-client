package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientGlobalUsers extends PacketCommandResponse {

    private int current;
    private int max;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");

        current = Integer.parseInt(split[6]);
        max = Integer.parseInt(split[9]);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_USERSGLOBAL;
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

}
