VertexServer : VertexAbstract {

	*vertexRequestor { |vertexName, vertexHost, mesh ...passArgs|

		var requestor = ("/" ++ this.asSymbol ++ "/request/new");

		requestor.postln;

		vertexHost.sendMsg(requestor, vertexName, mesh.name, *passArgs);



		^ "OSC message sent".postln;
	}


}
