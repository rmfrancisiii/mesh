VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

// RESPONSIBILITIES
	*makeClassInterface {
		VertexTypeClassInterface.makeGenericClassInterfaces(this)
	}

	makeInstanceInterfaces{
		VertexTypeInstanceInterface.makeInstanceInterface(this);
	}

	initVertex{|msg|
		this.setInstanceVars(msg);
		isProxy = false;
		isRunning = false;
		server = Server.local;
		this.setOptionsHandler(msg.args);
		this.makeInstanceInterfaces;
	}

	initProxy {|msg|
		this.setInstanceVars(msg);
		isProxy = true;
		server = Server.remote(name, host);
	}

	getVertexHost {
		^ host;
	}

	proxyUpdateHandler {|args|
    if (isProxy)
      { args.asAssociations.do({ |item|
        this.tryPerform(item.key.asSetter, item.value)})
    };
  }

	errorHandler { |msg|
    ("Error: " ++ msg).postln
  }

	freeHandler{ |msg|
		//this.sendProxyRequest(\proxyFree);
		/*MeshDebugMon(thisFunctionDef. msg);
		"freeing".postln;
		msg.postln;*/
//		msg.methodName = \proxyFree;
//		msg.sendProxyRequest;
	}

	proxyFreeHandler{
		MeshDebugMon(thisFunctionDef);
		mesh.vertexes.removeAt(name);
		// send proxyFree
		// remove the vertex from the vertexDict

	}

	patchIn {

	}

	patchOut {

	}


// UNIQUE METHODS
	setInstanceVars {|msg|
		name = msg.name;
		host = msg.vertexHost;
		mesh = msg.mesh;
		isRunning = false;
	}

	setOptionsHandler{ |args|
		// NOTE: these are hard coded!
		// TODO: logic HERE
		server.options.protocol_(\tcp);
		server.options.maxLogins_(8);
		args.postln;
	}

	pingHandler{
		// TODO: Fix this ( either rewriting overloading mechanism, or just adding logic here )
		"This unfortunately pings from the server machine itself.".postln;
		server.ping;
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

}
