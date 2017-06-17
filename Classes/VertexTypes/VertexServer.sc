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

		this.sendProxyUpdate([\isRunning, true]);

		// WORKING HERE; /////////

		// ADD MESSAGING FOR PROXY UPDATE
		// this.sendProxyUpdate (ARGS)
		// set isRunning to true
		// send proxy update request (isRunning = true)

		////////////////////////////
	}

	killHandler{ |requestingHost, msg|
		"Killing".postln;
		server.quit;
		// ADD MESSAGING FOR PROXY UPDATE
		// set isRunning to false
		// send proxy update request (isRunning = false)
	}


	// example to Overload inherited method from Object
	free {
    this.sendMethodRequest(\free)
  }

	freeHandler{ |requestingHost, msg|
		"freeing".postln;
	}


}
