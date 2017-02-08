package me.yamakaja.irc.client.network.packet.client;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class PacketClientServerMessage extends ClientboundPacket {

    private String message;

    @Override
    public void read(String data) {
        message = data;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.SERVERMESSAGE;
    }

}
