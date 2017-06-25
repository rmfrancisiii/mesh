VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeClassInterface {
		VertexTypeClassInterface.makeGenericClassInterfaces(this)
	}

	initVertex{|msg|
		this.setInstanceVars(msg);
		isProxy = false;
		isRunning = false;
		server = Server.local;
		this.setServerOptions(msg.args);
		this.makeInstanceInterfaces;
	}

	makeInstanceInterfaces{
		VertexTypeInstanceInterface.makeInstanceInterface(this);
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
		isRunning = false;
	}

	setServerOptions{ |args|
		// NOTE: these are hard coded!
		// TODO: logic HERE
		server.options.protocol_(\tcp);
		server.options.maxLogins_(8);
	}

	bootHandler{ |requestingHost, msg|
		"Booting".postln;
		this.setServerOptions;
		server.boot;
		isRunning = true;
		this.sendProxyUpdate([\isRunning, \true]);

	}

	killHandler{ |requestingHost, msg|
		"Killing".postln;
		server.quit;
		isRunning = false;
		this.sendProxyUpdate([\isRunning, false]);
	}

	// example to Overload inherited method from Object
	free {
    this.sendMethodRequest(\free)
  }

	//and then handle it
	freeHandler{ |requestingHost, msg|
		"freeing".postln;
		// send proxyFree
		// remove the vertex from the vertexDict
	}


}
