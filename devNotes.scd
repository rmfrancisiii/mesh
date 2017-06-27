

// for on the bus


Mesh(\mesh1).push;
Mesh.broadcastAddr.addr = MeshHostAddr("127.0.0.1", 57120 + (0..7))
Mesh.current.hosts.beacon.start;
Vertex(\server1, \server, \rose, \mesh1);
Vertex(\server1).boot;
Vertex(\server1).isRunning.postln;
Vertex(\server1).name.postln;
Vertex(\server1).mesh.postln;
Vertex(\server1).isProxy.postln;
Vertex(\server1).server.postln;
Vertex(\server1).host.postln;
Vertex(\server1).inExistence(\arg1, \arg2);
Vertex(\server1).kill;


o = Server.local.options;
o.numOutputBusChannels.postln;
o.maxLogins.postln;
o.protocol.postln;


Vertex(\server1).free;

Vertex(\pattern1, \pattern, \rose, \mesh1);
Mesh(\mesh1).vertexes.postln;

Mesh(\mesh1)[\broadcast].addr.postln;

Mesh.broadcastAddr.name.postln;

Mesh(\mesh1).vertexes.postln;

Vertex(\server1).postln;
Mesh(\mesh1).vertexes.at(\server1).isProxy.postln;
Mesh(\mesh1).vertexes.at(\server1Proxy).isProxy.postln;


Vertex.test;
Vertex(\server1).boot;

VertexServer.methods.do({|args| args.name.postln});
Vertex(\server1).class.methods.do({|args| args.name.postln});
Vertex(\server1).methods.do({|args| args.name.postln});

OSCdef.all.keys.postln;
OSCdef.all
Mesh.all.postln;

Vertex(\pattern1, \pattern);
Mesh.at(\mesh1).vertexes.postln;

Vertex(\server1).boot(\test1, \test2, \test3);


Vertex.vertexTypeDict.postln;

(
SynthDef("grain", {
	    Out.ar(0, Line.kr(0.1, 0, 0.01, doneAction:2) * FSinOsc.ar([400,1000]))
}).send(Vertex(\server1).server);
)

(
Routine({
    20.do({
		        (Vertex(\server1).server).sendMsg("/s_new", "grain", -1);
        0.01.wait;
    })
}).play;
)


(
p = Pbind(
    \midinote, Pseq([60, 72, 71, 67, 69, 71, 72, 60, 69, 67], 1),
    \dur, Pseq([2, 2, 1, 0.5, 0.5, 1, 1, 2, 2, 3], 1)
).play;
)



Vertex(\server1).kill(\test1, \test2, \test3);
Vertex(\server1).host.postln;
Vertex(\server1).class;

Mesh.thisHost.sendMsg(("/" ++ name ++ "/Boot/Server"), \bootServer)

OSCdef.all;
VertexServer.methods.do({|args| args.name.postln});


UnitTest.gui;

Pbind(\freq, Prand([300, 500, 231.2, 399.2], inf), \dur, 0.1).play;
Pbind(\freq, Prand([300, 500, 231.2, 399.2], inf), \dur, Prand([0.1, 0.3], inf)).play;


n = Synth(\default, [\freq, 850]);
n.set(\freq, 500)
n.free;

Vertex(\server1).boot(\test1, \test2, \test3);





Mesh(\mesh1).vertexes.postln;
Vertex(\server1).server.options.maxLogins.postln;


Vertex(\server2).server.status.postln;
Vertex(\server3).server.status.postln;




Vertex(\server1p).name.postln;
Vertex(\server1p).mesh.postln;
Vertex(\server1p).isProxy.postln;
Vertex(\server1p).server.postln;
Vertex(\server1p).host.postln;
Vertex(\server1p).isRunning.postln;

OSCdef.all

Server.all.postln;

Mesh(\mesh1).push;

Vertex(\server2, \server, \rose, \mesh1, \arg1, \arg2, \arg3);
Vertex(\server3, \server, \river, \mesh1, \arg1, \arg2, \arg3);

Vertex(\server2).host.postln;
Vertex(\server3).host.postln;



Mesh.at(\mesh1).vertexes.at(\vertex1).postln;
Vertex(\vertex1).setOptions(maxLogins: 12);
Vertex(\vertex1).boot;
Vertex(\vertex1).postln;



MeshHostsManager.test;
Mesh.test;

Mesh.at(\mesh1)[\rose].postln;


Vertex(\server1).setOptions(maxLogins: 12);
Vertex(\server1).boot;

Vertex.vertexTypeDict.postln;
UnitTest.gui;

Mesh.current.postln;
Mesh.current.hosts;
Mesh.stack.postln;
Mesh.all.postln;
Mesh.thisHost.name.postln;
Vertex.vertexTypeDict.postln;
Mesh.all.postln;
Mesh.current.hosts;
Mesh.all.removeAt(\mesh1);
NetAddr.localAddr.postln;
NetAddr.localAddr.sendMsg("/x");
r = OSCFunc({ "message arrived".postln }, '/x');
n = NetAddr("127.0.0.1", 57120);
n.sendMsg("/x")

("hostname".unixCmdGetStdOut.split($.)[0]).stripWhiteSpace.asSymbol

OSCdef.all;



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
  // all additional instance/interface methods
  // are calls to the proxy, and can direct requests to the server

  /*doesNotUnderstand {|selector ... args|
    // TODO: need to check order?
    var result = nil;
    (result = server.tryPerform(selector, *args)) !? { ^ result };
    (result = server.options.tryPerform(selector, *args)) !? { ^ result };
    (result = server.addr.tryPerform(selector, *args)) !? { ^ result };
  }*/

  /**vertexRequestHandler {|msg|
    if (super.new.init(*msg)) {
      ^ "Broadcast Vertex Proxy Request message now!".postln
      // TODO: This!!!
      // this.vertexProxyRequestor(arguments)

    }
    {^ "Not added, Failed, return error to requesting host!".postln
      // TODO: This!!!
    };
  }*/




/*

*sendVertexConfirmation { |msg|
  var path = (this.makeOSCdefPath("Confirm", "Vertex"));
  this.relayMsg(path, msg);
}

*sendProxyRequest{ |msg|
  var path = (this.makeOSCdefPath("Request", "Proxy"));
  this.relayMsg(path, msg);
}

*relayMsg {|path, msg, error|
  msg.requestingHost.sendMsg(path, msg, error);
}

*tryMakeProxy { |msg|
  /*var proxyHost = Mesh.thisHost;
  var oscAddr = msg[0];
  var vertexName = msg[1];
  var mesh = Mesh(msg[2]);
  var args = msg[3..];

  if (vertexHost.ip != Mesh.thisHost.ip){

    "received proxy request".postln;

    if (mesh.includesVertex(vertexName).not)
      {
        if (this.makeProxy(vertexName, mesh, vertexHost, args))
          { "Proxy added, sending Proxy Confirmation".postln;
            this.sendProxyConfirmation(vertexName, mesh.name, proxyHost, vertexHost);
          }{ var error;
            // TODO:  construct Proxy Creation Failed Error Message
            this.sendProxyError(vertexName, mesh.name, vertexHost, error);
            // add caught error?
          }
      }

      {var error;
        // TODO:  construct Proxy Creation Error Message
        this.sendProxyError(vertexName, mesh.name, vertexHost, error);
        // add caught error?
      }
  }*/
}

/**makeProxy{ |msg|
  /*var proxy = super.new.initProxy(vertexName, mesh, vertexHost, args);
  mesh.vertexes.put(vertexName, proxy);
  ^ true
}

*sendProxyConfirmation { |msg|
  var path = (this.makeOSCdefPath("Confirm", "Proxy"));
  this.relayMsg(path, msg);
  }

*sendProxyError { |msg, error|
  var path = (this.makeOSCdefPath("Error", "Proxy"));
  this.relayMsg(path, msg, error);
  }

  *proxyResponse {|msg|
  	"Proxy Error".postln;
    /*var vertexName = msg[1];
    var meshName = msg[2];
    var errorString = msg[3];
		("Vertex " ++ msg.vertexName ++ "reports error in " ++ msg.mesh.name).postln; errorString.postln;
	}


  boot {
    var path = (this.class.makeOSCdefPath("Request", "Boot"));
    host.sendMsg(path);
	}

	bootHandler{
		"booting server".postln;
		server.boot;
		this.bootNotify;
	}

	bootNotify {
		this.notifyIsRunning;
	}

	notifyIsRunning{
		isRunning = true;
	}
  setServerOptions{ |args|
		"Setting server Options".postln;
		// use args for optional ServerOptions
		server.options.protocol(\tcp);
		server.options.maxLogins_(8);
		server.options.protocol_(\tcp);
		//server.addr.connect;
		//server.startAliveThread( 0 );
		/*server.doWhenBooted({
			"remote tcp server started".postln;
			server.notify;
			server.initTree });
	}


  *makeTypeMethodOSCDefs{
    // for each method has two helper methods:
    // OSC Invoker (interface) creates and sends OSC message to vertexHost.
    // OSCdef responder on vertexHost unpacks message and calls method, then broadcasts a proxyUpdate message.



    //this.makeOSCInvoker("Boot", "Vertex", \invokeBootServer);
    this.makeOSCdef("Boot", "Vertex", \tryBootServer);

    /*OSCdef(\bootServerResponder, {|msg, time, addr, recvPort|
      this.bootHandler;
    }, this.makeOSCdefPath("Request", "Boot"));


  }
  */
