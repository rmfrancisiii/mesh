VertexServer : VertexAbstract {
	*makePath {
		^ "/" ++ this.asSymbol ++ "/request/"
	}



	*makeOSCDefs {


		OSCdef(\VertexServerRequestHandler, {|msg, time, addr, recvPort|
			// TRY TO MOVE INTO VertexAbstract
			// parse message for \name, \vertexType
			// might need to reverse the adress:
			// eg.  /VertexRequest/new/vertexType

			"New Vertex Request Handler".postln;
			// does \name exist?
				// no?
					// \vertexType.newServer(msg);
				// yes?
					// ignore? Send Exception?
					
		}, '/VertexServer/request/new');

		OSCdef(\VertexServerProxyRequestHandler, {|msg, time, addr, recvPort|
			"New Server proxy Request Handler".postln;
			// does it exist?
				// no?
					// this.createProxy
					// this.confirmProxy;
				// yes?
					// this.confirmProxy;

		}, '/VertexServer/request/proxy');

		OSCdef(\VertexServerConfirmationHandler, {|msg, time, addr, recvPort|
			"New Server confirmation Handler".postln;
			// if server confirmed, post confirmation.

			// if server NOT confirmed, post exception.

		}, '/VertexServer/request/proxy');

		OSCdef(\VertexServerProxyConfirmationHandler, {|msg, time, addr, recvPort|
			"New Server proxy confirmation Handler".postln;
			// if not all confirmed, resend request.
		}, '/VertexServer/request/proxy');
	}

	*newServer {
		// new server on THIS machine
		this.proxyRequestor;
		this.confirmServer;

	}

	*proxyRequestor{
		// broadcast server Proxy request

	}

	*makeProxy {
		// new proxy on this machine.
		// Mesh.Vertexes.all.put(name -> this)
		// put proxy into Vertex.all

	}

	*confirmProxy {
		// send a rexponse acknowledging that proxy was created.

	}

	*confirmServer {

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



}
