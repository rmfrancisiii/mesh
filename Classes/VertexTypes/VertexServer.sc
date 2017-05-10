VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;

		OSCdef(\bootServerResponder, {|msg, time, addr, recvPort|
			this.bootHandler;
		}, this.requestPath("boot"));

	}

	initVertex{|vertexName, vertexMesh...args|

		server = Server.local;
		host = Mesh.thisHost;
		name = vertexName;
		mesh = vertexMesh;
		isProxy = false;
	}

	initProxy {|vertexName, vertexMesh, vertexHost...args|
		server = Server.remote(\test);
		name = vertexName;
		host = vertexHost;
		mesh = vertexMesh;
		isProxy = true;
	}



	boot {
    var path = (this.makeRequestPath ++ name ++"/boot");
    host.sendMsg(path);
	}

	bootHandler{
		"booting server".postln;
		server.boot;
		isRunning = true;
		this.bootNotify;
	}

	bootNotify {
		this.notifyIsRunning;
	}

	notifyIsRunning{
		isRunning = true;
	}

}
