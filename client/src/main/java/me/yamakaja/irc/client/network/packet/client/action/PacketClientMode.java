package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketClientMode extends PacketAction {

    private String target;
    private boolean addition;
    private String modes;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        sender = split[0].substring(1);
        target = split[2];
        modes = split[3].substring(1);
        addition = split[3].charAt(0) == '+';
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.MODE;
    }

    /**
     * @return Whether or not the modes will be added rather than subtracted from the target (false = '-', true = '+')
     */
    public boolean isAddition() {
        return addition;
    }

    public String getTarget() {
        return target;
    }

    public String getModes() {
        return modes;
    }

}
