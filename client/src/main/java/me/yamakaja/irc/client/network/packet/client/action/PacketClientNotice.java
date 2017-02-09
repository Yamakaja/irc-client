package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientNotice extends PacketAction {

    private String target;
    private String message;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.target = split[2];
        this.message = data.substring(data.indexOf(':', 1) + 1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.NOTICE;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

}
