package me.yamakaja.irc.client.network.packet.client.command.list;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketClientChannelsEntry extends PacketCommandResponse {

    private String channel;
    private int users;
    private String topic;
    private String modes;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.channel = split[3];
        this.users = Integer.parseInt(split[4]);

        String topicAndModes = data.substring(data.indexOf(':', 1) + 1);
        int endIndex = topicAndModes.indexOf(']') + 1;

        this.modes = topicAndModes.substring(0, endIndex);
        this.topic = topicAndModes.substring(endIndex + 1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_LIST;
    }

    public String getChannel() {
        return channel;
    }

    public int getUsers() {
        return users;
    }

    public String getTopic() {
        return topic;
    }

    public String getModes() {
        return modes;
    }

}
