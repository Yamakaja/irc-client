package me.yamakaja.irc.client.network.packet.client.command.whois;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;
import me.yamakaja.irc.client.network.util.StringUtils;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class PacketClientWhoisIdle extends PacketCommandResponse {

    private String nick;
    private String status;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");

        nick = split[3];

        status = StringUtils.join(split, " ", 4, split.length - 1).substring(1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_WHOISIDLE;
    }

    public String getNick() {
        return nick;
    }

    public String getIdleState() {
        return status;
    }
}
