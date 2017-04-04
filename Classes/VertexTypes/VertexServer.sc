VertexServer : VertexAbstract {
	var <>host, <>server;

	*initClass {
	}

	*new { |myHost, myServer| ^ super.new.init(myHost, myServer) }


	init {
		arg myHost = Mesh.me, myServer = Server.local;

		// TODO: Check to be sure the following are for a local server.


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

	}

	spawn {|msg, time, addr, recvPort|
		"Create a Server from this message".postln;

	}

	spawnReply {|msg, time, addr, recvPort|
		"server is created and booted".postln;

	}


	*makeOSCDefs { // Called by Vertex.initVertexTypeList
		OSCdef(\VertexServer, {|msg, time, addr, recvPort| this.spawn(msg, time, addr, recvPort)}, '/VertexServer/spawn');
		OSCdef(\VertexServer, {|msg, time, addr, recvPort| this.spawnReply(msg, time, addr, recvPort)}, '/VertexServer/spawnReply');

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

}