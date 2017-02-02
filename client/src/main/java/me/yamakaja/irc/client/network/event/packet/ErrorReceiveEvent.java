package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.PacketClientError;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class ErrorReceiveEvent extends PacketEvent<PacketClientError> {

    private PacketClientError packet;

    @Override
    public PacketClientError getPacket() {
        return packet;
    }

    @Override
    public void read(PacketClientError packet) {
        this.packet = packet;
    }

}
