VertexAbstract {

  *requestor { |vertexName, vertexHost, mesh ...passArgs|

    var requestor = (this.makePath ++ "new");
    vertexHost.sendMsg(requestor, vertexName, mesh.name, *passArgs);
    ^ ("OSC message sent to " ++ requestor).postln;
  }

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
