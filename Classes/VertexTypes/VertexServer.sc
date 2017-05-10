VertexServer : VertexAbstract {
	var <>server, <>host, <>isOnline;

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

	}

	initProxy {|vertexHost...args|
		server = Server.remote(\test);
		host = vertexHost;
	}



	boot {
    var path = (this.makeRequestPath ++ name ++"/boot");
    host.sendMsg(path);
	}

	bootHandler{
		"booting server".postln;
		server.boot;
		isOnline = true;
		this.bootNotify;
	}

	bootNotify {
		this.notifyIsOnline;
	}

	notifyIsOnline{
		isOnline = true;
	}

}
