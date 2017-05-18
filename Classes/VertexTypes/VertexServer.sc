VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;
	}

	initVertex{|msg|
		this.setInstanceVars(msg);
		isProxy = false;
		server = Server.local;
		this.setServerOptions(msg.args);
		this.makeInstanceOSCdefs;
	}

	makeInstanceOSCdefs{
		this.makeInstanceOSCdef("Boot", "Server", \bootHandler);
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
		var path = ("/" ++ name ++ "/Boot/Server");
		host.sendMsg(path, \bootHandler)
	}

	bootHandler{ |msg|
		// main thread
		"Booting".postln;
		msg.postln;
	}

}
