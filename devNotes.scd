UnitTest.gui;

Vertex.test;
VertexPatchBay.test;
MeshHostsManager.test;
Mesh.test;


// for on the bus
Mesh(\mesh1).push;
Mesh.broadcastAddr.addr = MeshHostAddr("127.0.0.1", 57120 + (0..7))
Mesh.current.hosts.beacon.start;
Vertex(\server1, \server, \rose, \mesh1);
Vertex(\server2, \server, \rose, \mesh1);
Mesh(\mesh1).patchbay.addPatch(\vertex1, \vertex2);
Mesh(\mesh1).patchbay.addPatch(\server1, \server2);

Mesh(\mesh1).patchbay.vertexList.postln;

Mesh(\mesh1).patchbay.vertexList.postln;
Mesh(\mesh1).patchbay.vertexList.at(\server1).postln;
Mesh(\mesh1).patchbay.vertexList.at(\server1).at(\server2).postln;
Mesh(\mesh1).patchbay.vertexList.at(\server2).postln;

Mesh(\mesh1).patchbay.getPatch(\server1, \server2).class.postln;
Mesh(\mesh1).patchbay.getPatch(\server1, \server2).rand.postln;




Mesh(\mesh1).patchbay.vertexList.postln;
Mesh(\mesh1).patchbay.vertexList.keys.postln;

//Vertex(\server1).setOptions(\maxLogins, 12);
Vertex(\server1).boot;
Vertex(\server1).ping;
Vertex(\server1).kill;
Vertex(\server1).free;
Mesh(\mesh1).vertexes.postln;


Vertex(\synth1, \synth, \mesh1,
	{ | out, freq = 440, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
		OffsetOut.ar(out, Pan2.ar(audio, pan, env) )});

Vertex(\pattern1, \pattern, \mesh1,
	    \instrument, \test,
	[ \server, Server.default,
      \nharms, Pseq([4, 10, 40], inf),
      \dur, Pseq([1, 1, 2, 1]/10, inf),
      #[freq, sustain], Ptuple([
		 // assignment to multiple keys
         Pseq( (1..16) * 50, 4),
			Pseq([1/10, 0.5, 1, 2], inf)])]);

Patch(\synth1, \server1);
Patch(\pattern1, \synth1);







// create a mesh,
//  other hosts should use the same mesh name.
Mesh(\mesh1).push;
Mesh(\mesh1).hosts.beacon.start;

UnitTest.gui;
Mesh.at(\some)
Mesh.at(\mesh1)

// create another mesh and push it onto the stack:
Mesh(\mesh3).push;

Mesh(\mesh3).free;

// create a third mesh without pushing it onto the stack:
Mesh(\mesh3);

Mesh.list;
Mesh.peek;

// find out what mesh is active:
Mesh.activeMesh;

// and examine the Mesh stack:
// the last one is the latest (we could reverse this)
Mesh.stack;

//pop it off the stack:
Mesh.pop;

// free it
Mesh(\mesh1).free;

// find out what hosts are available in the current mesh:
Mesh(\mesh1).hosts;

// or do the same thing using a shortcut:
~mesh1.hosts;

// you can also find out info for a different mesh than the current one
Mesh(\mesh3).hostNames;

// but shortcuts only work for the current mesh
~mesh3.hostNames;

// pop the top mesh off of the stack:
Mesh.pop;

UnitTest.gui;

Mesh(\mesh1).hosts;
Mesh(\mesh1).ping;

Mesh.activeMesh;
Mesh.peek;
Vertex Syntax:
-----------
New:

MeshDebugMon.on;
MeshCommandParser.on;
~win.deactivate;
//**


Mesh(\mesh1).push;

Vertex(\serverOne, \server, \rose, \mesh1, \arg1);
Vertex(\serverTwo, \server, \rose, \mesh2, \arg2);

Vertex(\serverOne);
Vertex(\serverOne).test;
Vertex(\serverOne).ping;
Vertex(\serverTwo, name: \mesh2).test;

Mesh(\mesh2).push;
Vertex(\serverTwo).test;


Mesh(\mesh1).vertexList;
Mesh(\mesh2).vertexList;

Mesh.peek;

Mesh.pop;

Mesh.peek;


s = Server.local;

Vertex(\serverOne).reboot;
Vertex(\serverOne).connect;
Vertex(\serverOne).ping;
Mesh(\mesh1).vertexList;
Mesh.me.sendMsg("/VertexServer/spawn");

r = Server.remote(\river, NetAddr(Mesh(\mesh1)[\river].addr.ip, 57110));
Vertex(\serverTwo, \server, Mesh(\mesh1)[\river], r);
Vertex(\serverTwo).connect;
Vertex(\serverTwo).ping;

OSCdef.all;

Mesh(\mesh1)[\river].sendMsg("/VertexServer/spawn");
////// STOP HERE //////////

Vertex(\stereoOut, \audioOut);
Vertex(\stereoOut).setServer(\hostAddr);
Vertex(\sinSynth, \instrument, \sin);
Vertex(\fmSynth, \instrument, \fm);
Vertex(\fmSynth).setOut(\stereoOut);



// or:
Vertex(\fmSynth, \Instrument, \fm, \stereoOut,
	[
		freq=440,
		carPartial = 1,
		modPartial = 1,
		index = 3,
		dur=1.0,
		amp=0.25,
		pan=0,
		att=0.15,
		mod1=0.001,
		mod2=0.001
	]
)

// or:
Vertex(\sinSynth, \Instrument,
	{var mod = SinOsc.ar(freq * modPartial, 0,
		 freq * index * LFNoise1.kr(5.reciprocal).abs);
	 var car = SinOsc.ar((freq * carPartial) + mod, 0, amp);
	 var env = Env([0.001, 0.9, 0.001], [att, 1.0-att], [\sin, \sin]);
	 var envGen = EnvGen.kr(env, doneAction: 2, timeScale: dur);
	Out.ar(outBus, Pan2.ar(car * envGen, pan))})

// AND:

Vertex(\sinPattern, \PVert, trunk: \sinSynth)

/* Development notes

next:
Add ...args passthrough for extra arguments in Vertex.new(name, type, mesh, host, *ARGS)


5/6/17: created skeleton for messages for VertexAbstract


General practices for Vertexes:

- invocation process:

On Requesting Host;
 1. Vertex.new(\name, \type, \args)
 2. VertexType.requestor, inherits from VertexAbstract
 3. sends OSC msg to Vertex Host

---
On Vertex Host
 4. OSCdef VertexTypeRequestVertex
 5. VertexType.tryMakeVertex
 6. checks Mesh for vertex,
    7. makes proxy and initializes it.
    8. puts name -> this vertex into Mesh(\thisMesh).vertexes
    9. sendVertexConfirmation back to Requesting Host
    10. sendProxyRequest to Broadcast address (all hosts)

   b. makes new server, stores reference somewhere?
   c. returns notification or exceptions
6. Broadcast proxy requestor with reference to 5.b.
7. Proxy Request Handler adds proxy to Mesh.Vertexes
- done -



all activity @Vertex is -> proxy.


TODO:

new:
Vertex(\name, \type, \host, \mesh)

method/modify:
Vertex(\name).method(parameters)




Make Offline option for new Mesh?


Ideas:

Use Preprocessor for command confirmation request?
Create a 'Confirm' class. pass a fn into it, and only execute it
if/when //**confirm or similar is executed.

Use ServerTree to set up ordered groups for VertexTypes

CmdPeriod to remove host from mesh?

add confirmation to cmd period? "you have N vertexes hosted on your
machine. are you sure you want to kill the server?"



///////////
Currently:
///////////

VertexServer: A Vertex that represents a Supercollider server node.
  at least for version 0.0, the Server will be at the end of a signal path,
  so essentially it determines the location of running synth nodes.
  when any other node is created, in order for it to be played,
  a server node with an audio out device should be connected

	Instance Variables:
		Host : A MeshHostAddr
		Server: a Server (may be local OR remote)

+++++++++++

Generic Object creation pseudocode:

1. Artist issues Vertex(\vertexName, \vertexType, \hostName, \name);

2. Vertex checks to be sure the mesh exists, then checks \name.vertexList
   2a. if it exists (currently returns the Vertex)
       Might want to extend functionality for updating in this syntax.

   2b. if it does NOT exist, checks for vertexType in vertexTypeList.

3. Messages vertexType.vertexRequestor, and passes:
    a. ref to Mesh object,
    b. ref to Host object from all,
    c. any other Args as an array.

4. VertexRequestor class method generates an OSC message and sends it to the named Host.

       To:   Host Address
       Path: /VertexType/request/new
       Msg:  (vertexName, mesh.name, ...arguments);

   A generic vertexRequestor method is inherited from VertexAbstract,
   but could be overloaded by VertexType depending on specific needs.

----------------------------
- Machine/Process Boundary -
----------------------------

5a. OSCdef receives request and marshalls its arguments
5b. Handles any preprocessing, eg. supplying default arguments,
5c. Passes request off to VertexType.vertexRequestHandler

6. vertexRequestHandler class method calls super.new.init and passes an unpacked array of arguments.
   This step is necessary because the instance isn't created yet.

   A generic vertexRequestHandler class method is inherited from VertexAbstract,
   but could be overloaded by VertexType depending on specific needs.

7. The VertexType.init method:
   7a. sets the initial state of the VertexType object.
   7b. adds the Vertex to the Mesh.vertexList
 ----- stopped here -----
   7c. tests the Success of adding the object,
   7d. returns success status to vertexRequestHandler

8a. If Creation succeeded, vertexRequestHandler calls VertexType.vertexProxyRequestor
8b. If Creation failed, vertexRequestHandler returns an OSC Remoting Error Message to the invoking client

9. vertexProxyRequestor

////////////
/* Completed:
///////////

4/9/17:
  Set up initial requestor, OSCdef, requestHandler

4/2/17:
  Merged Vertex branch back into master,
  started VertexServer Branch

4/1/17:
  fixed dynamic Vertex Type List to include all subclasses of VertexAbstract, and to initialize OSCdefs if possible
  added doesNotUnderstand to MeshHost to pass method requests to the MeshHostAddr

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


