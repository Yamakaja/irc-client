package me.yamakaja.irc.client.network.packet.server;

import me.yamakaja.irc.client.network.packet.Packet;

/**
 * Created by Yamakaja on 01.02.17.
 */
public abstract class ServerboundPacket extends Packet {

    public abstract String getEncoded();

}
