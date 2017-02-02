package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import net.lahwran.fevents.Event;

/**
 * Created by Yamakaja on 01.02.17.
 */
public abstract class PacketEvent<T extends ClientboundPacket> extends Event {

    public abstract void read(T packet);

    public abstract T getPacket();

}
