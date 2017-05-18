VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeClassOSCDefs;
	}

	initVertex{|msg|
		this.setInstanceVars(msg);
		isProxy = false;
		server = Server.local;
		this.setServerOptions(msg.args);
		this.makeInstanceInterfaces;
	}

	makeInstanceInterfaces{
		this.makeInstanceInterface("boot", "server", \bootHandler);
		this.makeInstanceInterface("kill", "server", \killHandler);
	}

	makeInstanceInterface{|transaction, object, method|
				this.makeInstanceOSCdef(transaction, object, method);
				this.makeInstanceMethod(transaction, object, method);
	}

	makeInstanceMethod{|transaction, object, method|
		var methodName = transaction.asSymbol;
		this.addUniqueMethod(methodName, {|...args|
				var path = ("/" ++ name ++ "/" ++ transaction ++ "/" ++ object);
				host.sendMsg(path, *args) });
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

	killHandler{ |msg|
		// main thread
		"Killing".postln;
		msg.postln;
	}



}
