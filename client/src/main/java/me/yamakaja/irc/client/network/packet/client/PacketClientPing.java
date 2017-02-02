package me.yamakaja.irc.client.network.packet.client;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketClientPing extends ClientboundPacket {

    private String payload;

    public PacketClientPing() {
    }

    public String getMessage() {
        return payload;
    }

    @Override
    public void read(String data) {
        payload = data.split(" ")[1].substring(1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.PING;
    }
}
