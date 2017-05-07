VertexServer : VertexAbstract {


	*makeOSCDefs {
		this.makeAbstractOSCDefs;

		// add or overload any UNIQUE OSCdefs here, eg.
		//
		// OSCdef(\VertexServerRequestHandler, {|msg, time, addr, recvPort|
		//	VertexServer.makeVertex(msg);
		// }, '/VertexServer/request/vertex');

	}

	init{
		"initializing server".postln;
		^ this
	}

	*makeVertex{ |vertexName, mesh ...passArgs|
		var server = super.new.init;
		"adding vertex".postln;
		mesh.vertexes.put(vertexName, server);
		^ true
	}

	*makeProxy{ |vertexName, mesh ...passArgs|
		"adding Proxy".postln;
		//mesh.vertexes.put(vertexName, this.asSymbol);
		^ true
	}

	boot {
		"booting server".postln;
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



}
