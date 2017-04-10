VertexServer : VertexAbstract {
	var <>host, <>server;

	*makeOSCDefs { // Called by Vertex.initVertexTypeList

		OSCdef(\VertexServerRequestNew, {|msg, time, addr, recvPort|
			"OSC Requestor".postln;
			this.requestNewHandler(msg, time, addr, recvPort);
		}, '/VertexServer/requestNew');

	}

	init {|requestor, vertexName, meshName, thisHost, thisServer|

		("osc path: " ++ requestor).postln;

		host = thisHost;
		server = thisServer;


		Mesh(meshName).vertexList.add(vertexName -> this);		//test success

		if (true) {^true} {^false}
	}

	test { (host ++ " : Success!!").postln; }
}
