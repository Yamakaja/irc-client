package me.yamakaja.irc.client.network.packet.client.command.whois;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;
import me.yamakaja.irc.client.util.Tuple;

import java.util.Arrays;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class PacketClientWhoisChannels extends PacketCommandResponse {

    private String nick;
    private ChannelMap channels;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");

        nick = split[3];

        if(split.length > 3)
            split[4] = split[4].substring(1);

        String[] channels = new String[split.length - 4];
        char[] modes = new char[split.length - 4];

        for(int i = 4; i < split.length; i++) {

            if(!split[i].startsWith("#")) {
                modes[i - 4] = split[i].charAt(0);
                channels[i - 4] = split[i].substring(1);
            } else
                channels[i - 4] = split[i];

        }
        this.channels = new ChannelMap(channels, modes);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_WHOISCHANNELS;
    }

    public ChannelMap getChannels() {
        return channels;
    }

    public String getNick() {
        return nick;
    }

    public static class ChannelMap {
        private String[] channels;
        private char[] modes;

        public ChannelMap(String[] channels, char[] modes) {
            this.channels = channels;
            this.modes = modes;
        }

        public Tuple<String, Character> get(int index) {
            return new Tuple<>(channels[index], modes[index]);
        }

        @Override
        public String toString() {
            return "ChannelMap{" +
                    "channels=" + Arrays.toString(channels) +
                    ", modes=" + Arrays.toString(modes) +
                    '}';
        }
    }

}
