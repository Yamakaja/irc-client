package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;

/**
 * Created by Yamakaja on 06.02.17.
 */
public abstract class PacketAction extends ClientboundPacket {

    protected String sender;

    public String getSender() {
        return sender;
    }

}
