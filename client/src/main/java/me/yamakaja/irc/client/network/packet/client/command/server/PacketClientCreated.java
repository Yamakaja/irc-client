package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;
import me.yamakaja.irc.client.network.util.StringUtils;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class PacketClientCreated extends PacketCommandResponse {

    private String creationTime;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        creationTime = StringUtils.join(split, " ", 7, split.length - 1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_CREATED;
    }

    public String getCreationTime() {
        return creationTime;
    }

}
