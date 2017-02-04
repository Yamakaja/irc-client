package me.yamakaja.irc.client.network.packet.client.command.topic;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.CommandResponse;

import java.util.Date;

/**
 * Created by Yamakaja on 04.02.17.
 */
public class PacketClientTopicSetInformation extends CommandResponse {

    private String channel;
    private String setter;
    private Date time;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");

        channel = split[3];
        setter = split[4];
        if (setter.contains("!"))
            setter = setter.substring(0, setter.indexOf("!") - 1);
        time = new Date(Long.parseLong(split[5]) * 1000);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_TOPICINFO;
    }

    public String getChannel() {
        return channel;
    }

    public String getSetter() {
        return setter;
    }

    public Date getTime() {
        return time;
    }

}
