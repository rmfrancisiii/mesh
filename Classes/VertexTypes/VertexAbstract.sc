VertexAbstract {

	// All the vertex types inherit from here, providing any common variables or methods that all vertex types might have.

	*requestor { |vertexName, vertexHost, vertexMesh ...passArgs|
		var requestor = ("/" ++ this.asSymbol ++ "/requestNew");
		vertexHost.sendMsg(requestor, vertexName, vertexMesh, *passArgs);
	}

	*requestNewHandler {|msg, time, addr, recvPort|
		if (super.new.init(*msg)) {
			^ "Broadcast Spawn Request message now!".postln
			// TODO: This!!!
		}
		{^ "Not added, Failed, return error to requesting host!".postln
			// TODO: This!!!
		};
	}

}