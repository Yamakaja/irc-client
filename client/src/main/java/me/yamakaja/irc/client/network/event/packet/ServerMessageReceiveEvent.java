package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.PacketClientMessage;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class ServerMessageReceiveEvent extends PacketEvent<PacketClientMessage> {

    public String getMessage() {
        return getPacket().getMessage();
    }
}
