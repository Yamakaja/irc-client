package me.yamakaja.irc.client.network.packet.client.command.topic;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.CommandResponse;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class PacketClientTopic extends CommandResponse {

    private String channel;
    private String topic;

    @Override
    public void read(String data) {
        channel = data.split(" ")[3];
        topic = data.substring(data.indexOf(":", 1) + 2);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_TOPIC;
    }

    public String getTopic() {
        return topic;
    }

    public String getChannel() {
        return channel;
    }
}
