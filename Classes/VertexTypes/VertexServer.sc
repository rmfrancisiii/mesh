VertexServer : VertexAbstract {
	var <>server, <>host;

	*makeOSCDefs {
		//generate generic OSCdefs for this vertexType
		this.makeAbstractOSCDefs;

		// add or overload any UNIQUE OSCdefs here, eg.

		 OSCdef(\bootServerHandler, {|msg, time, addr, recvPort|
			 this.boot;
		 }, '/VertexServer/boot/');

	}

	initVertex{|args|
		"initializing server".postln;
		args.postln;
		server = Server.local;
		host = Mesh.thisHost;
	}

	initProxy {|args|
		"initializing server proxy".postln;
		args.postln;
		server = Server.remote(\test);
		host = Mesh
	}

	*makeVertex{ |vertexName, mesh, args|
		var vertex = super.new.initVertex(args);
		mesh.postln;
		"adding vertex".postln;
		mesh.vertexes.put(vertexName, vertex);
		^ true
	}

	*makeProxy{ |vertexName, mesh, host, args|
		var proxy = super.new.initProxy(args);
		"adding Proxy".postln;
		mesh.vertexes.put(vertexName, proxy);
		^ true
	}

	boot {
		"booting server".postln;
	}

	setOptions {
		"setting options".postln;
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
