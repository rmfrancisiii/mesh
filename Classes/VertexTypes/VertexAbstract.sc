VertexAbstract {

	*vertexRequestor { |vertexName, vertexHost, mesh ...passArgs|
		var requestor = ("/" ++ this.asSymbol ++ "/request/new");
		vertexHost.sendMsg(requestor, vertexName, mesh.name, *passArgs);
		requestor.postln;
		^ "OSC message sent".postln;
	}

}
