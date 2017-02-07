package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

/**
 * Created by Yamakaja on 06.02.17.
 */
public class PacketClientNick extends PacketAction {

    private String to;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.sender = split[0].substring(1);
        this.to = split[2];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.NICK;
    }

    public String getTo() {
        return to;
    }

}
