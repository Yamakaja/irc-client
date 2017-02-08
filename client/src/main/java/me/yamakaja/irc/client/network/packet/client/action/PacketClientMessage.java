package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class PacketClientMessage extends PacketAction {

    private String target;
    private String message;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.sender = split[0].substring(1);
        this.target = split[2];
        this.message = data.substring(data.indexOf(':', 1) + 1, data.length());
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.PRIVMSG;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }
}
