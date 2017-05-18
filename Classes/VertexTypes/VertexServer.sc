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
		this.makeInstanceInterfaces;
	}

	makeInstanceInterfaces{
		this.makeInstanceInterface("Boot", "Server", \bootHandler);
	}

	makeInstanceInterface{|transaction, object, method|
				this.makeInstanceOSCdef(transaction, object, method);
				this.makeInstanceMethod;
	}

	makeInstanceMethod{
		this.addUniqueMethod(\boot, {
				var path = ("/" ++ name ++ "/Boot/Server");
				host.sendMsg(path, \bootHandler) });
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

	bootHandler{ |msg|
		// main thread
		"Booting".postln;
		msg.postln;
	}



}
