package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.PacketClientPing;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PingReceiveEvent extends PacketEvent<PacketClientPing> {

    private PacketClientPing packet;

    public PingReceiveEvent(PacketClientPing packet) {
        this.packet = packet;
    }

    public PingReceiveEvent() {

    }

    @Override
    public PacketClientPing getPacket() {
        return packet;
    }

    @Override
    public void read(PacketClientPing packet) {
        this.packet = packet;
    }

}
