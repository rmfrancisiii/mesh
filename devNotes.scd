/* Development notes

TODO:

Make Offline otion for new Mesh?



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
    b. ref to Host object from hostList,
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



Code scratchpad (ignore):
/////////////////////////

TODO: Check to be sure the following are for a local server.


		if (myHost == Mesh.me)

		{
		// if its being added to THIS machine:
		// If Local:
		// set Host = Mesh.me
		// set Params = *[options]
		// set Server = Server.local(\Local, Mesh.me);
		// send SPAWN_SIGNAL
		// Reboot Server;
		// Wait for boot;
		// send SPAWN_REPLY;
		// server.connect;

			"Local Server".postln;
			host = myHost;
			server = myServer;
			this.setParams;
			this.boot;
			this.notifyMesh;

		}

		{ // What if it's on a remote server?

		// If Remote:
		// set Host = Mesh(\mesh1)[\river]
		// set Params = *[options]
		// set Server = Server.remote(\river, NetAddr(Mesh(\mesh1)[\river].addr.ip, 57110))
		// send SPAWN_SIGNAL
		// wait for SPAWN_REPLY;
		// server.connect

			"Creating A Remote Server!!!".postln;
			host = myHost;
			server = myServer;
			this.setParams;
			// this.reboot;
		}


	spawn {|msg, time, addr, recvPort|
		"Create a Server from this message".postln;

	}

	spawnReply {|msg, time, addr, recvPort|
		"server is created and booted".postln;

	}





	doesNotUnderstand {|selector ... args|
		// TODO: need to check order?
		var result = nil;
		(result = server.tryPerform(selector, *args)) !? { ^ result };
		(result = server.options.tryPerform(selector, *args)) !? { ^ result };
		(result = server.addr.tryPerform(selector, *args)) !? { ^ result };
	}

	setParams {
		server.options.protocol_(\tcp);
		server.options.maxLogins_(8);
	}

	notifyMesh {
		"Spread the word!!".postln;
	}

*/
