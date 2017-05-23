VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeClassInterface {
		VertexTypeClassInterface.makeGenericClassInterfaces(this)
	}

	initVertex{|msg|
		this.setInstanceVars(msg);
		isProxy = false;
		server = Server.local;
		this.setServerOptions(msg.args);
		this.makeInstanceInterfaces;
	}

	makeInstanceInterfaces{
		var interfaces = Array.with(
    ["boot", "server", \bootHandler],
    ["kill", "server", \killHandler],
    ).do({|args|
			VertexTypeInstanceInterface.new(this, *args)});
	}

	initProxy {|msg|
		this.setInstanceVars(msg);
		isProxy = true;
		server = Server.remote(name, host);
	}

	getVertexHost {
		^ host;
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

	bootHandler{ |requestingHost, msg|
		"Booting".postln;
		server.boot;
		//when booted:
		// set isRunning to true
		// send proxy update request (isRunning = true)
	}

	killHandler{ |requestingHost, msg|
		"Killing".postln;
		server.quit;
		//when killed:
		// set isRunning to false
		// send proxy update request (isRunning = false)
	}



}
