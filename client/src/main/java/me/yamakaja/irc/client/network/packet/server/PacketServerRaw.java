package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketServerRaw extends ServerboundPacket {

    private String command;

    public PacketServerRaw(String command) {
        this.command = command;
    }

    @Override
    public String getEncoded() {
        return command;
    }

}
