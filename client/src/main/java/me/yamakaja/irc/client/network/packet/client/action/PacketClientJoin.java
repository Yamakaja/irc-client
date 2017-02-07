package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

/**
 * Created by Yamakaja on 06.02.17.
 */
public class PacketClientJoin extends PacketAction {

    private String channel;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.sender = split[0].substring(1);
        this.channel = split[2].substring(1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.JOIN;
    }

    public String getChannel() {
        return channel;
    }

}
