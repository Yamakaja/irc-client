package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.command.PacketClientNames;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class NamReplyReceiveEvent extends PacketEvent<PacketClientNames> {

    private PacketClientNames packet;

    @Override
    public void setPacket(PacketClientNames packet) {
        this.packet = packet;
    }

    @Override
    public PacketClientNames getPacket() {
        return packet;
    }

}
