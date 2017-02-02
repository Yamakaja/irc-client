package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.PacketClientMessage;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class ServerMessageReceiveEvent extends PacketEvent<PacketClientMessage> {

    private PacketClientMessage message;

    @Override
    public void read(PacketClientMessage packet) {
        this.message = packet;
    }

    @Override
    public PacketClientMessage getPacket() {
        return message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
