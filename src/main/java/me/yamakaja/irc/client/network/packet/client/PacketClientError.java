package me.yamakaja.irc.client.network.packet.client;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketClientError extends ClientboundPacket {

    private String error;

    @Override
    public void read(String data) {
        error = data.substring(data.indexOf(' ') + 1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.ERROR;
    }

    public String getError() {
        return error;
    }
}
