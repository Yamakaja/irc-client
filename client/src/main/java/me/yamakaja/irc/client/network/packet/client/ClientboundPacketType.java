package me.yamakaja.irc.client.network.packet.client;

import me.yamakaja.irc.client.network.event.packet.*;
import me.yamakaja.irc.client.network.packet.client.action.*;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;
import me.yamakaja.irc.client.network.packet.client.command.names.PacketClientNames;
import me.yamakaja.irc.client.network.packet.client.command.motd.PacketClientMotdEnd;
import me.yamakaja.irc.client.network.packet.client.command.motd.PacketClientMotdLine;
import me.yamakaja.irc.client.network.packet.client.command.motd.PacketClientMotdStart;
import me.yamakaja.irc.client.network.packet.client.command.names.PacketClientNamesEnd;
import me.yamakaja.irc.client.network.packet.client.command.server.*;
import me.yamakaja.irc.client.network.packet.client.command.topic.PacketClientTopic;
import me.yamakaja.irc.client.network.packet.client.command.topic.PacketClientTopicSetInformation;
import me.yamakaja.irc.client.network.packet.client.command.whois.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yamakaja on 01.02.17.
 */
public enum ClientboundPacketType {

    PING(PacketClientPing.class, PingReceiveEvent.class),
    ERROR(PacketClientError.class, ErrorReceiveEvent.class),
    SERVERMESSAGE(PacketClientServerMessage.class, ServerMessageEvent.class),
    JOIN(PacketClientJoin.class),
    PART(PacketClientPart.class),
    NICK(PacketClientNick.class),
    PRIVMSG(PacketClientMessage.class),
    QUIT(PacketClientQuit.class),
    NOTICE(PacketClientNotice.class),

    RPL_WELCOME(1, PacketClientWelcome.class),
    RPL_YOURHOST(2, PacketClientHost.class),
    RPL_CREATED(3, PacketClientCreated.class),
    RPL_MYINFO(4, PacketClientServerInfo.class),
    RPL_SERVEROPTIONS(5, PacketClientServerOptions.class),
    RPL_UNIQUEID(42, PacketClientUniqueId.class),

    RPL_TRACECONNECTING(201),
    RPL_TRACEHANDSHAKE(202),
    RPL_TRACEUNKNOWN(203),
    RPL_TRACEOPERATOR(204),
    RPL_TRACEUSER(205),
    RPL_TRACESERVER(206),
    RPL_TRACESERVICE(207),
    RPL_TRACENEWTYPE(208),
    RPL_TRACECLASS(209),
    RPL_TRACERECONNECT(210),
    RPL_STATSLINKINFO(211),
    RPL_STATSCOMMANDS(212),
    RPL_ENDOFSTATS(219),
    RPL_UMODEIS(221),
    RPL_SERVLIST(234),
    RPL_SERVLISTEND(235),
    RPL_STATSUPTIME(242),
    RPL_STATSOLINE(243),
    RPL_LUSERCLIENT(251),
    RPL_LUSEROP(252),
    RPL_LUSERUNKNOWN(253),
    RPL_LUSERCHANNELS(254),
    RPL_LUSERME(255),
    RPL_ADMINME(256),
    RPL_ADMINLOC1(257),
    RPL_ADMINLOC2(258),
    RPL_ADMINEMAIL(259),
    RPL_TRACELOG(261),
    RPL_TRACEEND(262),
    RPL_TRYAGAIN(263),
    RPL_AWAY(301, PacketClientWhoisAway.class),
    RPL_USERHOST(302),
    RPL_ISON(303),
    RPL_UNAWAY(305),
    RPL_NOWAWAY(306),
    RPL_WHOISUSER(311, PacketClientWhoisUser.class),
    RPL_WHOISSERVER(312, PacketClientWhoisServer.class),
    RPL_WHOISOPERATOR(313, PacketClientWhoisOperator.class),
    RPL_WHOWASUSER(314),
    RPL_ENDOFWHO(315),
    RPL_WHOISIDLE(317, PacketClientWhoisIdle.class),
    RPL_ENDOFWHOIS(318, PacketClientWhoisEnd.class),
    RPL_WHOISCHANNELS(319, PacketClientWhoisChannels.class),
    RPL_LISTSTART(321),
    RPL_LIST(322),
    RPL_LISTEND(323),
    RPL_CHANNELMODEIS(324),
    RPL_UNIQOPIS(325),
    RPL_NOTOPIC(331),
    RPL_TOPIC(332, PacketClientTopic.class),
    RPL_TOPICINFO(333, PacketClientTopicSetInformation.class),  //Sent by at least the inpIRCd, but not in spec (??) Syntax: <channel> <user who set the topic, nick on inspIRCd. Hostname on freenode> <when they set it>
    RPL_INVITING(341),
    RPL_SUMMONING(342),
    RPL_INVITELIST(346),
    RPL_ENDOFINVITELIST(347),
    RPL_EXCEPTLIST(348),
    RPL_ENDOFEXCEPTLIST(349),
    RPL_VERSION(351),
    RPL_WHOREPLY(352),
    RPL_NAMREPLY(353, PacketClientNames.class),
    RPL_LINKS(364),
    RPL_ENDOFLINKS(365),
    RPL_ENDOFNAMES(366, PacketClientNamesEnd.class),
    RPL_BANLIST(367),
    RPL_ENDOFBANLIST(368),
    RPL_ENDOFWHOWAS(369),
    RPL_INFO(371),
    RPL_MOTD(372, PacketClientMotdLine.class),
    RPL_ENDOFINFO(374),
    RPL_MOTDSTART(375, PacketClientMotdStart.class),
    RPL_ENDOFMOTD(376, PacketClientMotdEnd.class),
    RPL_YOUREOPER(381),
    RPL_REHASHING(382),
    RPL_YOURESERVICE(383),
    RPL_TIME(391),
    RPL_USERSSTART(392),
    RPL_USERS(393),
    RPL_ENDOFUSERS(394),
    RPL_NOUSERS(395),
    RPL_DISPHOST(396, PacketClientDisplayedHost.class),

    ERR_NOSUCHNICK(401),
    ERR_NOSUCHSERVER(402),
    ERR_NOSUCHCHANNEL(403),
    ERR_CANNOTSENDTOCHAN(404),
    ERR_TOOMANYCHANNELS(405),
    ERR_WASNOSUCHNICK(406),
    ERR_TOOMANYTARGETS(407),
    ERR_NOSUCHSERVICE(408),
    ERR_NOORIGIN(409),
    ERR_NORECIPIENT(411),
    ERR_NOTEXTTOSEND(412),
    ERR_NOTOPLEVEL(413),
    ERR_WILDTOPLEVEL(414),
    ERR_BADMASK(415),
    ERR_UNKNOWNCOMMAND(421),
    ERR_NOMOTD(422),
    ERR_NOADMININFO(423),
    ERR_FILEERROR(424),
    ERR_NONICKNAMEGIVEN(431),
    ERR_ERRONEUSNICKNAME(432),
    ERR_NICKNAMEINUSE(433),
    ERR_NICKCOLLISION(436),
    ERR_UNAVAILRESOURCE(437),
    ERR_USERNOTINCHANNEL(441),
    ERR_NOTONCHANNEL(442),
    ERR_USERONCHANNEL(443),
    ERR_NOLOGIN(444),
    ERR_SUMMONDISABLED(445),
    ERR_USERSDISABLED(446),
    ERR_NOTREGISTERED(451),
    ERR_NEEDMOREPARAMS(461),
    ERR_ALREADYREGISTRED(462),
    ERR_NOPERMFORHOST(463),
    ERR_PASSWDMISMATCH(464),
    ERR_YOUREBANNEDCREEP(465),
    ERR_YOUWILLBEBANNED(466),
    ERR_KEYSET(467),
    ERR_CHANNELISFULL(471),
    ERR_UNKNOWNMODE(472),
    ERR_INVITEONLYCHAN(473),
    ERR_BANNEDFROMCHAN(474),
    ERR_BADCHANNELKEY(475),
    ERR_BADCHANMASK(476),
    ERR_NOCHANMODES(477),
    ERR_BANLISTFULL(478),
    ERR_NOPRIVILEGES(481),
    ERR_CHANOPRIVSNEEDED(482),
    ERR_CANTKILLSERVER(483),
    ERR_RESTRICTED(484),
    ERR_UNIQOPPRIVSNEEDED(485),
    ERR_NOOPERHOST(491),
    ERR_UMODEUNKNOWNFLAG(501),
    ERR_USERSDONTMATCH(502);


    private static Map<Integer, ClientboundPacketType> idMap = new HashMap<>();
    private Class<? extends ClientboundPacket> packetClass;
    private Class<? extends PacketEvent> eventClass;
    private int id;
    private boolean isError;
    private boolean isCommandResponse;

    ClientboundPacketType(Class<? extends PacketAction> actionPacket) {
        this.packetClass = actionPacket;
    }

    ClientboundPacketType(Class<? extends ClientboundPacket> packetClass, Class<? extends PacketEvent> eventClass) {
        this.packetClass = packetClass;
        this.eventClass = eventClass;
    }

    ClientboundPacketType(int id, Class<? extends PacketCommandResponse> packetClass, Class<? extends PacketEvent> eventClass) {
        this.id = id;
        this.packetClass = packetClass;
        this.eventClass = eventClass;
        this.isError = name().startsWith("ERR_");
        this.isCommandResponse = true;
    }

    ClientboundPacketType(int id, Class<? extends PacketCommandResponse> packetClass) {
        this(id, packetClass, null);
    }

    ClientboundPacketType(int id) {
        this.id = id;
    }

    /**
     * @param id The id of the packet type to get
     * @return The packet type
     * @throws IllegalArgumentException when supplied with an invalid id
     */
    public static ClientboundPacketType getForId(int id) {
        if (idMap.isEmpty()) {
            for (ClientboundPacketType type : values())
                idMap.put(type.getId(), type);
        }

        if (!idMap.containsKey(id))
            throw new IllegalArgumentException("Invalid packet id!");
        return idMap.get(id);
    }

    public Class<? extends ClientboundPacket> getPacketClass() {
        return packetClass == null ? SERVERMESSAGE.getPacketClass() : packetClass;
    }

    public Class<? extends PacketEvent> getEventClass() {
        return eventClass;
    }

    @Nullable
    public PacketEvent getEvent(ClientboundPacket packet) {
        if(eventClass == null)
            return null;
        try {
            PacketEvent event = eventClass.newInstance();
            event.setPacket(packet);
            return event;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isCommandResponse() {
        return isCommandResponse;
    }
}
