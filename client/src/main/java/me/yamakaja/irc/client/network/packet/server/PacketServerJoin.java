package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketServerJoin extends ServerboundPacket {

    private String channel;

    public PacketServerJoin(String channel) {
        this.channel = channel;
    }

    @Override
    public String getEncoded() {
        return "JOIN " + channel;
    }

    public String getChannel() {
        return channel;
    }

}
