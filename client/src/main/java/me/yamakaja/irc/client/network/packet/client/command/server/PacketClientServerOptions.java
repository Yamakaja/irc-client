package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class PacketClientServerOptions extends PacketCommandResponse {

    private String[] options;

    @Override
    public void read(String data) {
        String[] split = data.substring(0, data.indexOf(':', 1) - 2).split(" ");
        options = new String[split.length - 3];
        System.arraycopy(split, 3, options, 0, split.length - 3);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_SERVEROPTIONS;
    }

    public String[] getOptions() {
        return options;
    }

}
