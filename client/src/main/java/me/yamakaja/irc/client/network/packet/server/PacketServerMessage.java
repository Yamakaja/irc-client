package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketServerMessage extends ServerboundPacket {

    private String target;
    private String message;

    public PacketServerMessage(String target, String message) {
        this.target = target;
        this.message = message;
    }

    @Override
    public String getEncoded() {
        return "PRIVMSG " + target + " " + message;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

}
