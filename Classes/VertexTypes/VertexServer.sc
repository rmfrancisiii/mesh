VertexServer : VertexAbstract {
	var <>host, <>server;

	*makeOSCDefs { // Called by Vertex.initVertexTypeList

		OSCdef(\VertexServerRequestNew, {|msg, time, addr, recvPort|
			"OSC Request Responder Answering".postln;
			this.requestNewHandler(msg.drop(1), time, addr, recvPort);
		}, '/VertexServer/requestNew');

	}

	init {|vertexName, meshName, thisHost, thisServer|

		host = thisHost;
		server = thisServer;
		//test
		Mesh(meshName).vertexList.add(vertexName -> this);

		if (true) {^true} {^false}
	}

	test { (host ++ " : Success!!").postln; }
}
