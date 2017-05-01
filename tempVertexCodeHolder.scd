VertexAbstract {

	// All the vertex types inherit from here, providing any common variables or methods that all vertex types might have.



	*vertexRequestHandler {|msg|
		if (super.new.init(*msg)) {
			^ "Broadcast Vertex Proxy Request message now!".postln
			// TODO: This!!!
			// this.vertexProxyRequestor(arguments)

		}
		{^ "Not added, Failed, return error to requesting host!".postln
			// TODO: This!!!
		};
	}

	*vertexProxyRequestor { |vertexName, vertexHost, mesh ...passArgs|
		var requestor = ("/" ++ this.asSymbol ++ "/request/proxy");
		//broadcastAddr.sendMsg(requestor, vertexName, mesh.name, *passArgs);
	}

	*vertexProxyRequestHandler {|msg, time, addr, recvPort|
		if (super.new.init(*msg)) {
			^ "Broadcast Vertex Proxy Request message now!".postln
			// TODO: This!!!
		}
		{^ "Not added, Failed, return error to requesting host!".postln
			// TODO: This!!!
		};
	}

}




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
	init {|requestor, vertexName, name, thisHost, thisServer|
		host = Mesh.me;
		server = Server.local;

		Mesh(name).vertexDict.add(vertexName -> this);

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
