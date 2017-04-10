VertexServer : VertexAbstract {
	var <>host, <>server;

	*makeOSCDefs { // Called by Vertex.initVertexTypeList

		OSCdef(\VertexServerRequestNew, {|msg, time, addr, recvPort|
			"OSC Requestor".postln;
			// Handle any additional msg processing here, but put arguments in an array
			this.vertexRequestHandler(msg);
		}, '/VertexServer/request/new');

		OSCdef(\VertexServerProxyRequestNew, {|msg, time, addr, recvPort|
			"OSC Requestor".postln;
			// Handle any additional msg processing here, but put arguments in an array
			this.vertexRequestHandler(msg);
		}, '/VertexServer/request/proxy');


	}


	/// working on this:
	init {|requestor, vertexName, meshName, thisHost, thisServer|
		host = Mesh.me;
		server = Server.local;

		Mesh(meshName).vertexList.add(vertexName -> this);

		//TODO: test success
		server.boot;

		if (this.test) {^true} {^false}
	}

	test { ^true }

	doesNotUnderstand {|selector ... args|
		// TODO: need to check order?
		var result = nil;
		(result = server.tryPerform(selector, *args)) !? { ^ result };
		(result = server.options.tryPerform(selector, *args)) !? { ^ result };
		(result = server.addr.tryPerform(selector, *args)) !? { ^ result };
	}

}
