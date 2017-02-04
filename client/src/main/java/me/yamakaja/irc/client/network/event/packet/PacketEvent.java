package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 01.02.17.
 */
public abstract class PacketEvent<T extends ClientboundPacket> extends Event {

    private T packet;

    public void setPacket(T packet) {
        this.packet = packet;
    }

    public T getPacket() {
        return packet;
    }

}
