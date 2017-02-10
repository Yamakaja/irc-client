package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketServerPart extends ServerboundPacket {

    private String channel;
    private String reason;

    @Override
    public String getEncoded() {
        return "PART " + channel + (reason != null ? " " + reason : "");
    }

    public PacketServerPart(String channel, String reason) {
        this.channel = channel;
        this.reason = reason;
    }

    public PacketServerPart(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public String getReason() {
        return reason;
    }

}
