/* Development notes


///////////
Currently:
///////////

Working on VertexServer:

  VertexServer should allow:
    Creating a server object with either:

       s = Server.local(\Local, NetAddr.localAddr);
       Vertex(\serverOne, \server, Mesh.me, s);

          - or something like (maybe could simplify this):

       r = Server.remote(\river, NetAddr(Mesh(\mesh1)[\river].addr.ip, 57110));
       Vertex(\serverTwo, \server, Mesh(\mesh1)[\river], r);

    once created on one machine on the Mesh it should be passed
    around to the other participants via the network, who should "handle"
    identifying local/remote, setting server options, etc,
    and making it available in the mesh vertex list.

  at least for version 0.0, the Server will be at the end of a signal path,
  so essentially it determines the location of running synth nodes.
  when any other node is created, in order for it to be played,
  a server node with an audio out device should be connected

  Process:
  1. Server created on \HostOne.
  2. HostOne sends Announcement message on BroadcastAddr
       Announcement: (HostA, \newServer, OSC Addr, IP Addr, Port Number, number of audioOut channels, running?)
  3. HostTwo and HostThree parse Announcement


  Necessary OSC Commands:
  * serverAdded
  * serverRemoved

  Useful OSC Commands:
  * serverBoot
  * serverReboot
  * serverSendMsg
  * serverOutputBus
  * serverQueryAllNodes
  * serverMute

  Ideas:
  * look into persistent Node Trees for bringing back State if the Server has to be rebooted.
  * meters? Scope? plotting?
  * Recording? Number of Channels?
  * Buffers?

///////////
Completed:
///////////
4/1/17:



3/31/17:
  Trying to use doesNotUnderstand to pass unknown method calls
  up the chain of command, not by Object Inheritance,
  but by the definitions of our own occult system. e.g.

     Vertex(\serverOne).protocol;

  should pass through to

     VertexServer.server.protocol;

  NOT AbstractServer.proocol. I think IdentityDictionary does something similar but my
  Aramaic is rusty. maybe:

    tryPerform... YES!!!!

3/30/17:
  added MeshDebugMon
    allows adding Debug(something) calls anywhere in the code and
    when MeshDebugMon.on it outputs something to the post window
    these should be removed from performance code where latency might matter

    needs:
       * Documentation
       * Help File
       * Unit Test

  added MeshCommandParser
    when MeshCommandParser.on, this runs in the interpreter
    and preprocesses executed code, checking for a matching expression
    this will allow shortcuts to pretty much anything, of the type:
      //** -> Mesh.peek.ping
    this could add a little latency (one extra conditional per shortcut checked)

    needs:
       * maybe a better/more legible init class,
       * a way to map shortcuts on the fly?
       * Documentation
       * Help File
       * Unit Test

  Created initial VertexServer vertex type
    intended to provide access to SC Servers on local and remote machines on the mesh


3/29/17:
  Basic Vertex functionality,
  Class Vertex serves as a factory and a registry
  VertexAbstract is abstract superclass for the classes located in Classes/VertexTypes

3/28/17:

  Fixed gui bug (look at defer)

  Better ping for Jeff:
    Mesh(\mesh1).ping;
    I want everyone to see a line in the post window that says "codedoval pinged the mesh"

3/27/17:
  Quark Install Working (w/dependency on UnitTesting)
  Making and unmaking Meshes
  Pushing, Popping them
  Monitoring other Hosts (online/offline) in a gui
  Basic (stub) help files for all classes,
  Basic (stub) Inline Documentation
  Unit Tests (stub) for all classes/methods
  Basic Readme and Guide files




//////////////////
Conceptual Note:
//////////////////

Mesh should emulate as closely as possible a broader method of communication protocol.
While this is currently based on SC, I'd like to try to keep it abstract enough to use
for IOT devices, etc. down the road, where no SC is even in the graph.

so the goal is, Vertexes, once added to a Mesh should say hello to one another, and negotiate
connections WITHOUT an omniscient manager. admittedly v.0 may not be quite like that, but
i'd like to base the grammar/syntax on this concept.

Vertexes

Initial handshake might be TCP. Object passing might be TCP (in an ideal world)
Should HELLO on connection to the network, check for conflicts,
generate a useful OSC namespace for the device, and distribute that information
to other vertexes on the nesh.

a Vertex with outgoing ports should be able to receive an OSC message that names a second vertex,
( Target IP address, port number, and an OSC path) and an outgoing port.
it should then start passing data:
from the outgoing port of the first Vertex
to the incoming port of the second vertex.

could be exceptions to this, eg. a vertex device that chooses a compatible target randomly, or accdg to some function.

vertex with ONLY incoming ports (eg. hardware synth or dmx controller, etc?)
some "dumb" devices might need a way to serve the vertex info to other nodes/hosts, via proxy (eg. supercollider/max, etc)

Namespace based on:

1.  the device Name
2.  Device Number (due to any collision from a similar device on the network)
3.  Device Port (input or output)

/Arduino1/knob1

steps:
a. DHCP IP assigned to Host (if applicable)
b. Broadcast IP Announce "Mesh::Type::IP Address"
c. Peers reply:
	* IP Address
	* Device OSCPath
	* incomng port number
	* PortList (PortPath, PortType)
		PortTypes: eg. Int, Float, Audio, Video, Image
d. device selects its own OSCPath
e. device broadcasts PortList
f. device listens for OSC Messages on incoming Port Number,
g. listens for Broadcast of new Vertexes

Outgoing Ports: active (assigned)
Incoming: passive

Vertex:
Name
OSCPath
PortList
PatchList
Compatible ports

And if it has input ports, choose an ephemeral incoming UDP port number.

Outgoing UDP Ports of transmitters will just dynamically set to match the incoming port number of the receiver.

Should share their Port information, eg.
Namespace :: Type :: Units :: Min :: Max



Mapping functionality
eg Digital Music Orchestra
? Should Identify other ports with compatible Types for easy connection

? Patches could hold a transformation function (scaling/mapping) of some sort





*/