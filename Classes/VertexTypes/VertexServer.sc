VertexServer : VertexAbstract {
	var <>server, <>host, <>isOnline;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;

		// add or overload any UNIQUE OSCdefs here, eg.
		 OSCdef(\bootServerResponder, {|msg, time, addr, recvPort|
			 this.bootHandler;
			 }, this.makeRequestPath ++ name ++ "/boot");

	}

	initVertex{|vertexName, vertexMesh, args|

		server = Server.local;
		host = Mesh.thisHost;
		name = vertexName;
		mesh = vertexMesh;

	}

	initProxy {|thatHost, args|
		server = Server.remote(\test);
		host = thatHost;
	}

	*makeVertex{ |vertexName, mesh, args|
		var vertex = super.new.initVertex(vertexName, mesh, args);
		mesh.vertexes.put(vertexName, vertex);
		^ true
	}

	*makeProxy{ |vertexName, mesh, host, args|
		var proxy = super.new.initProxy(host, args);
		mesh.vertexes.put(vertexName, proxy);
		^ true
	}

	boot {
    var path = (this.makeRequestPath ++ name ++"/boot");
    host.sendMsg(path);
	}

	bootHandler{
		"booting server".postln;
		server.boot;
		isOnline = true;
		this.notifyIsOnline;
	}

}
