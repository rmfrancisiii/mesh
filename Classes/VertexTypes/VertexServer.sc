VertexServer : VertexAbstract {
	var <>host, <>server;

	*initClass {
	}

	*new { |myHost, myServer| ^ super.new.init(myHost, myServer) }


	init { |myHost, myServer|

		// TODO: Check to be sure the following are for a local server.


		if (myHost == Mesh.me)
		{ // if its being added to THIS machine:

			"Local Server".postln;
			host = myHost;
			server = myServer;
			this.setParams;
			this.boot;
			this.notifyMesh;

		}

		{ // What if it's on a remote server?

			"Creating A Remote Server!!!".postln;
			host = myHost;
			server = myServer;
			this.setParams;
			// this.reboot;
		}

	}

	*makeOSCDefs { // Called by Vertex.initVertexTypeList
		OSCdef(\VertexServer, {|msg, time, addr, recvPort| "Spawn a vertex Server".postln}, '/VertexServer/spawn', Mesh.me.addr);
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