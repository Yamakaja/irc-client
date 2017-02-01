package me.yamakaja.irc.client.network.packet.client;

import me.yamakaja.irc.client.network.event.packet.ErrorReceiveEvent;
import me.yamakaja.irc.client.network.event.packet.MessageReceiveEvent;
import me.yamakaja.irc.client.network.event.packet.PacketEvent;
import me.yamakaja.irc.client.network.event.packet.PingReceiveEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Yamakaja on 01.02.17.
 */
public enum ClientboundPacketType {

    PING(PacketClientPing.class, PingReceiveEvent.class),
    MESSAGE(PacketClientMessage.class, MessageReceiveEvent.class),
    ERROR(PacketClientError.class, ErrorReceiveEvent.class);

    private Class<? extends ClientboundPacket> packetClass;
    private Class<? extends PacketEvent> eventClass;

    ClientboundPacketType(Class<? extends ClientboundPacket> packetClass, Class<? extends PacketEvent> eventClass) {
        this.packetClass = packetClass;
        this.eventClass = eventClass;
    }

    public Class<? extends ClientboundPacket> getPacketClass() {
        return packetClass;
    }

    public Class<? extends PacketEvent> getEventClass() {
        return eventClass;
    }


    @Nullable
    public PacketEvent getEvent(ClientboundPacket packet) {
        try {
            PacketEvent event = eventClass.newInstance();
            event.read(packet);
            return event;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
