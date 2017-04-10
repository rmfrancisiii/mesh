VertexAbstract {

	// All the vertex types inherit from here, providing any common variables or methods that all vertex types might have.

	*vertexRequestor { |vertexName, vertexHost, mesh ...passArgs|
		var requestor = ("/" ++ this.asSymbol ++ "/request/new");
		vertexHost.sendMsg(requestor, vertexName, mesh.name, *passArgs);
		^ "OSC message sent".postln;
	}

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