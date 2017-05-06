VertexServer : VertexAbstract {


	*makeOSCDefs {

		OSCdef(\VertexServerRequestHandler, {|msg, time, addr, recvPort|
			VertexServer.makeVertex(msg);
		}, '/VertexServer/request/vertex');

		OSCdef(\VertexServerProxyRequestHandler, {|msg, time, addr, recvPort|
			VertexServer.makeProxy(msg);
		}, '/VertexServer/request/proxy');

		OSCdef(\VertexServerConfirmationHandler, {|msg, time, addr, recvPort|
			this.confirmVertex(msg);
		}, '/VertexServer/confirm/vertex');

		OSCdef(\VertexServerProxyConfirmationHandler, {|msg, time, addr, recvPort|
			this.confirmProxy(msg);
		}, '/VertexServer/confirm/proxy');
	}

	*makeVertex { |msg|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var host = Mesh.thisHost;

		"Make Vertex method".postln;
		if (mesh.includesVertex(vertexName).not)
			{
				this.addVertex(vertexName, mesh);
				this.sendVertexConfirmation(vertexName, mesh.name, host);
				this.sendProxyRequest(vertexName, mesh.name, host);
			}

			{	"error, vertex exists".postln}

	}

	*addVertex{ |vertex, mesh|
		mesh.vertexes.put(vertex, \test);
	}

	*sendProxyRequest{ |vertexName, meshName, vertexHost|
			var path = (this.makeRequestPath ++ "proxy");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
			^ ("OSC message sent to " ++ path).postln;
	}

	*makeProxy { |msg|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var host = Mesh.thisHost;
		this.sendProxyConfirmation;

					// does it exist?
						// no?
							// this.createProxy
							// this.confirmProxy;
						// yes?
							// this.confirmProxy;

		// new proxy on this machine.
		// Mesh.Vertexes.all.put(name -> this)
		// put proxy into Vertex.all

	}

	*sendVertexConfirmation { |vertexName, meshName, vertexHost|
			var path = (this.makeConfirmPath ++ "vertex");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
			^ ("OSC message sent to " ++ path).postln;
		}

	*sendProxyConfirmation { |vertexName, meshName, vertexHost|
			var path = (this.makeConfirmPath ++ "proxy");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
			^ ("OSC message sent to " ++ path).postln;
		}

	*confirmProxy {
		"proxy Created".postln;
		// send a rexponse acknowledging that proxy was created.
	}

	*confirmVertex {
		"server Created".postln;
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
