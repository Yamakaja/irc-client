package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class PacketServerMode extends ServerboundPacket {

    private String target;

    private boolean add;
    private String modes;

    /**
     * @param target The target of the mode command
     * @param add    Whether or not to add modes (true = '+', false = '-')
     * @param modes  The modes to manipulate
     */
    public PacketServerMode(String target, boolean add, String modes) {
        this.target = target;
        this.add = add;
        this.modes = modes;
    }

    @Override
    public String getEncoded() {
        return "MODE " + target + " " + (add ? '+' : '-') + modes;
    }

}
