VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;
	}

	initVertex{|msg|
		this.makeOSCdefs;
		this.setInstanceVars(msg);
		isProxy = false;
		server = Server.local;
		this.setServerOptions(msg.args);
	}

	makeOSCdefs{
		OSCdef(\VertexServerBootServer, {
			|msg, time, msghost, recvPort|
			this.bootHandler(msg);
		}, "/VertexServer/Boot/Server");

	}

	initProxy {|msg|
		this.setInstanceVars(msg);
		isProxy = true;
		server = Server.remote(name, host);
	}

	setInstanceVars {|msg|
		name = msg.name;
		host = msg.vertexHost;
		mesh = msg.mesh;
	}

	setServerOptions{ |args|
		server.options.protocol_(\tcp);
		server.options.maxLogins_(8);
	}

	boot {
		// interface
		this.host.sendMsg("/VertexServer/Boot/Server", \bootServer)
	}

	bootHandler{ |msg|
		// main thread
		"Booting".postln;
		msg.postln;
	}

}
