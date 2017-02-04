# IRC Client

WARNING: In it's current state all raw irc traffic is written to stderr!

## Installation:
Clone the repo and execute `maven install` in this directory to install the maven artifact into your local maven repository. (Or use our private maven repo if you have access to it ;) )

Then just add

```xml
<dependency>
    <groupId>me.yamakaja.irc</groupId>
    <artifactId>client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

to your dependencies and make sure the classes get packaged into your jar file (Maven Assembly Plugin, Maven Shade Plugin, etc. )

## Usage
First, create a guice injector using `Guice.createInjector()`. Then create an instance of `IRCNetworkClient` using that:

```java
IRCNetworkClient ircClient = injector.getInstance(IRCNetworkClient.class);
```

Before we proceed we might want to add some event listeners:

```java
public class WhoisListener implements Listener {

    @Inject
    private IRCNetworkClient client;

    @EventHandler
    public void onWhois(EndOfWhoisEvent e) {
        System.out.println(client.getUser(e.getNick()).toString());
    }

}
```

```java
ircClient.getEventBus().registerListener(injector.getInstance(WhoisListener.class));
```

Then we need to set the remote destination and connect:

```java
ircClient.setRemote("irc.spi.gt", 6667);
ircClient.connect();
```

Once connected we send our nick and user:
```java
ircClient.setNick("SomeIRCBot");
ircClient.setUser("ircBot_", 0, "*", "I'm an irc bot");
```

Now lets send a whois for "Yamakaja"
```java
ircClient.sendWhois("Yamakaja");
```
The listener defined above will be notified once the request has finished

## Contributing
### Handling a packet

First off, determine what kind of packet you're dealing with.
That is, does a user need to be notified of its arrival? Is it only part of a command response?
Lets take a closer look at packet `372 RPL_MOTD`, `375 RPL_MOTDSTART`, `376	RPL_ENDOFMOTD`:

Lets take a look at the syntax of each packet first:
- `RPL_MOTDSTART`: `:- <server> Message of the day - `
- `RPL_MOTD`: `:- <text>`
- `RPL_ENDOFMOTD`: `:End of MOTD command`

How does that look in practice?

```
:simba.spi.gt 375 Yamakaja_ :simba.spi.gt message of the day
:simba.spi.gt 372 Yamakaja_ :-   mmmm    "           #            
:simba.spi.gt 372 Yamakaja_ :-  #"   " mmm    mmmmm  #mmm    mmm  
:simba.spi.gt 372 Yamakaja_ :-  "#mmm    #    # # #  #" "#  "   # 
:simba.spi.gt 372 Yamakaja_ :-      "#   #    # # #  #   #  m"""# 
:simba.spi.gt 372 Yamakaja_ :-  "mmm#" mm#mm  # # #  ##m#"  "mm"# 
:simba.spi.gt 372 Yamakaja_ :- 
:simba.spi.gt 372 Yamakaja_ :-                         I'm gonna be the mane event.
:simba.spi.gt 372 Yamakaja_ :- 	                      ,   __, ,
:simba.spi.gt 372 Yamakaja_ :- 	      _.._         )\/(,-' (-' `.__
:simba.spi.gt 372 Yamakaja_ :- 	     /_   `-.      )'_      ` _  (_    _.---._
:simba.spi.gt 372 Yamakaja_ :- 	    // \     `-. ,'   `-.    _\`.  `.,'   ,--.\
:simba.spi.gt 372 Yamakaja_ :- 	   // -.\       `        `.  \`.   `/   ,'   ||
:simba.spi.gt 372 Yamakaja_ :- 	   || _ `\_         ___    )  )     \  /,-'  ||
:simba.spi.gt 372 Yamakaja_ :- 	   ||  `---\      ,'__ \   `,' ,--.  \/---. //
:simba.spi.gt 372 Yamakaja_ :- 	    \\  .---`.   / /  | |      |,-.\ |`-._ //
:simba.spi.gt 372 Yamakaja_ :- 	     `..___.'|   \ |,-| |      |_  )||\___//
:simba.spi.gt 372 Yamakaja_ :- 	       `.____/    \\\O| |      \o)// |____/
:simba.spi.gt 372 Yamakaja_ :- 	            /      `---/        \-'  \
:simba.spi.gt 372 Yamakaja_ :- 	            |        ,'|,--._.--')    \
:simba.spi.gt 372 Yamakaja_ :- 	            \       /   `n     n'\    /
:simba.spi.gt 372 Yamakaja_ :- 	             `.   `<   .::`-,-'::.) ,'
:simba.spi.gt 372 Yamakaja_ :- 	               `.   \-.____,^.   /,'
:simba.spi.gt 372 Yamakaja_ :- 	                 `. ;`.,-V-.-.`v'
:simba.spi.gt 372 Yamakaja_ :- 	                   \| \     ` \|\
:simba.spi.gt 372 Yamakaja_ :- 	                    ;  `-^---^-'/
:simba.spi.gt 372 Yamakaja_ :- 	                     `-.______,'
:simba.spi.gt 372 Yamakaja_ :- 
:simba.spi.gt 372 Yamakaja_ :-         ------------------------------------------
:simba.spi.gt 372 Yamakaja_ :-        /                                          \
:simba.spi.gt 372 Yamakaja_ :-       /     This server is proudly sponsored by    \
:simba.spi.gt 372 Yamakaja_ :-       |                  SpigotMC                  |
:simba.spi.gt 372 Yamakaja_ :-       |          (http://www.spigotmc.org/)        |
:simba.spi.gt 372 Yamakaja_ :-       \                                            /
:simba.spi.gt 372 Yamakaja_ :-        -------------------------------------------
:simba.spi.gt 376 Yamakaja_ :End of message of the day.
```

Now that we know what we're dealing with we can start to plan:
Since the server only has one MOTD we should be safe to store it in our `IRCNetworkClient` class:

```java
private List<String> motd = new LinkedList<>();

public List<String> getMotd() {
    return motd;
}
```

Now lets define the packets, but before that a couple things to note:
- Packets are named according to their direction. I.e. packets being sent to the server are _server_bound packets, packets coming from the server are _client_bound packets
- Gernal packets are defined in `me.yamakaja.irc.client.network.packet.<server/client>`
- Command response packets are defined in `<...>.client.command`
- Packets which are caused by one command can be grouped together, see `me.yamakaja.irc.client.network.packet.client.command.whois`.

Ok, so lets define our three packet classes in `me.yamakaja.irc.client.network.packet.client.command.motd`:
- PacketClientMotdStart
- PacketClientMotdLine
- PacketClientMotdEnd

Since the MOTD start packet doesn't really contain information we need it's fairly simple:

`PacketClientMotdStart.java`
```java
public class PacketClientMotdStart extends CommandResponse {
    
    @Override
    public void read(String data) {
        
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_MOTDSTART;
    }
    
}
```

The MOTD line packet contains the actual motd lines, thus we need to exactact and store those

`PacketClientMotdLine.java`
```java
public class PacketClientMotdLine extends CommandResponse {

    private String line;

    @Override
    public void read(String data) {
        line = data.substring(data.indexOf(":", 1) + 2);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_MOTD;
    }

    public String getLine() {
        return line;
    }
    
}
```

And the MOTD end packet also doesn't contain any information:

`PacketClientMotdEnd.java`
```java
public class PacketClientMotdEnd extends CommandResponse {
    
    @Override
    public void read(String data) {
        
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_ENDOFMOTD;
    }
    
}
```

Now we need to define an event to notify users that the MOTD has arrived, but we only want that to be called when the MOTD ends so the user can directly access the MOTD:
The event itself doesn't need to contain much information aswell, but because it's triggered as a packet event we need to define it accordingly:

`me.yamakaja.irc.client.network.event.packet.EndOfMotdEvent.java`
```java
public class EndOfMotdEvent extends PacketEvent<PacketClientMotdEnd> {
}
```

After that, we need to register our new packets and events in the `ClientboundPacketType` enum:

`ClientboundPacketType.java`
```java
[...]
RPL_MOTD(372, PacketClientMotdLine.class),
RPL_ENDOFINFO(374),
RPL_MOTDSTART(375, PacketClientMotdStart.class),
RPL_ENDOFMOTD(376, PacketClientMotdEnd.class, EndOfMotdEvent.class),
[...]
```

All that's left to do now is to define a packet handler for the MOTD packets: Packet handlers belong into `me.yamakaja.irc.client.network.handler` 
```java
public class MotdPacketHandler extends ChannelInboundHandlerAdapter {
    
    @Inject
    private IRCNetworkClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClientboundPacket packet = (ClientboundPacket)msg;
        
        switch(packet.getPacketType()) {
            case RPL_MOTDSTART:
                client.getMotd().clear();
                return;
            case RPL_MOTD:
                client.getMotd().add(((PacketClientMotdLine)packet).getLine());
                return;
        }
        
        super.channelRead(ctx, msg);
    }
    
}
```

Not intercepting `RPL_MOTDEND` packets will cause them to get passed through to the `EventPacketHandler` which calls the event we defined earlier.

Also we must not forget the register the packet handler in our pipeline initializer:
`IRCClientChannelInitializer.java`
```java
[...]
.addAfter("pingResponder", "whoisHandler", injector.getInstance(WhoisPacketHandler.class))
.addAfter("whoisHandler", "motdHandler", injector.getInstance(MotdPacketHandler.class))
.addAfter("motdHandler", "packetEventHandler", injector.getInstance(EventPacketHandler.class))
[...]
```

See the terminal interface for an example EndOfMotdEvent listener.

### Useful resources:
- [Protocol Overview](http://www.networksorcery.com/enp/protocol/irc.htm)
- [RFC2812](http://www.networksorcery.com/enp/rfc/rfc2812.txt)
- [RFC2813](http://www.networksorcery.com/enp/rfc/rfc2813.txt)