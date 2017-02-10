package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketServerUser extends ServerboundPacket {

    public static byte MODE_i = 8;
    public static byte MODE_w = 4;

    private String ident;
    private String realname;
    private byte mode;
    private String host;

    public PacketServerUser(String ident, byte mode, String host, String realname) {
        this.ident = ident;
        this.realname = realname;
        this.mode = mode;
        this.host = host;
    }

    @Override
    public String getEncoded() {
        return "USER " + ident + " " + mode + " " + host + " :" + realname;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
