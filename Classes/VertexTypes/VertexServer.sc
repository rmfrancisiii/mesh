VertexServer : VertexAbstract {
	var <>server, <>host;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;

		// add or overload any UNIQUE OSCdefs here, eg.
		 OSCdef(\bootServerHandler, {|msg, time, addr, recvPort|
			 this.boot;
		 }, '/VertexServer/boot/');

	}

	initVertex{|args|
		server = Server.local;
		host = Mesh.thisHost;
	}

	initProxy {|thatHost, args|
		server = Server.remote(\test);
		host = thatHost;
	}

	*makeVertex{ |vertexName, mesh, args|
		var vertex = super.new.initVertex(args);
		mesh.vertexes.put(vertexName, vertex);
		^ true
	}

	*makeProxy{ |vertexName, mesh, host, args|
		var proxy = super.new.initProxy(host, args);
		mesh.vertexes.put(vertexName, proxy);
		^ true
	}

	boot {
		"booting server".postln;
	}

}
