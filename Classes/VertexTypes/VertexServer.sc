VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;

		OSCdef(\bootServerResponder, {|msg, time, addr, recvPort|
			this.bootHandler;
		}, this.requestPath("boot"));
	}

	initVertex{|vertexName, vertexMesh...args|
		name = vertexName;
		host = Mesh.thisHost;
		mesh = vertexMesh;
		isProxy = false;
		server = Server.local;
	}

	initProxy {|vertexName, vertexMesh, vertexHost...args|
		name = vertexName;
		host = vertexHost;
		mesh = vertexMesh;
		isProxy = true;
		server = Server.remote(name, host);
	}

	boot {
    var path = (this.makeRequestPath ++ name ++"/boot");
    host.sendMsg(path);
	}

	bootHandler{
		"booting server".postln;
		server.boot;
		this.bootNotify;
	}

	bootNotify {
		this.notifyIsRunning;
	}

	notifyIsRunning{
		isRunning = true;
	}

}
