package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.util.StringUtils;

/**
 * Created by Yamakaja on 06.02.17.
 */
public class PacketClientPart extends PacketAction {

    private String channel;
    private String reason;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.sender = split[0].substring(1);
        this.channel = split[2];
        if (split.length > 3)
            reason = StringUtils.join(split, " ", 3, split.length - 1).substring(1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.PART;
    }

    public String getChannel() {
        return channel;
    }

    public String getReason() {
        return reason;
    }
}
