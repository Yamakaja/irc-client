package me.yamakaja.irc.client.network.packet.client;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketClientMessage extends ClientboundPacket {

    private String message;

    @Override
    public void read(String data) {
        message = data;
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.MESSAGE;
    }

    public String getMessage() {
        return message;
    }
}
