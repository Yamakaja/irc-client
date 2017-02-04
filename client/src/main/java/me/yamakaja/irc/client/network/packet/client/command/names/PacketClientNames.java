package me.yamakaja.irc.client.network.packet.client.command.names;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.CommandResponse;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class PacketClientNames extends CommandResponse {

    private String sender;
    private String receiver;

    private char mode;
    private String channel;
    private Collection<String> nicks;

    @Override
    public void read(String data) {
        String[] words = data.split(" ");

        sender = words[0].substring(1);
        receiver = words[2];

        mode = words[3].toCharArray()[0];

        channel = words[4];

        nicks = new ArrayList<>(words.length - 5);

        if (words.length > 5)
            words[5] = words[5].substring(1);
        for (int i = 5; i < words.length; i++) {
            nicks.add(words[i]);
        }
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_NAMREPLY;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public char getMode() {
        return mode;
    }

    public String getChannel() {
        return channel;
    }

    public Collection<String> getNicks() {
        return nicks;
    }
}
