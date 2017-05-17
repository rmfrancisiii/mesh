VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;
		this.makeTypeMethodOSCDefs;
	}

	*makeTypeMethodOSCDefs{
		this.makeOSCdef("Boot", "Vertex", \tryBootServer);
	}

	initVertex{|msg|
		this.setInstanceVars(msg);
		isProxy = false;
		server = Server.local;
		this.setServerOptions(msg.args);
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
		server.options.protocol_(\tcp);
	}

}
