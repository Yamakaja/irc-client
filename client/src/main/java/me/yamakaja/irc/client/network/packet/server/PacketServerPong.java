package me.yamakaja.irc.client.network.packet.server;

import me.yamakaja.irc.client.network.packet.client.PacketClientPing;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketServerPong extends ServerboundPacket {

    private String message;

    public PacketServerPong(String message) {
        this.message = message;
    }

    public PacketServerPong(PacketClientPing packet) {
        this.message = packet.getMessage();
    }

    @Override
    public String getEncoded() {
        return "PONG " + message;
    }
}
