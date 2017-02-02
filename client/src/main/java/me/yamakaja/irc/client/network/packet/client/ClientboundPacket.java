package me.yamakaja.irc.client.network.packet.client;

/**
 * Created by Yamakaja on 01.02.17.
 */
public abstract class ClientboundPacket {

    public abstract void read(String data);

    public abstract ClientboundPacketType getPacketType();



}
