VertexServer : VertexAbstract {
	var <>server, <>host, <>isRunning;

	*makeOSCDefs {
		this.makeAbstractOSCDefs;

		OSCdef(\bootServerResponder, {|msg, time, addr, recvPort|
			this.bootHandler;
		}, this.makeOSCdefPath("Request", "Boot"));
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

	boot {
    var path = (this.class.makeOSCdefPath("Request", "Boot"));
    host.sendMsg(path);
	}

	bootHandler{
		"booting server".postln;
		server.boot;
		this.bootNotify;
	}

	bootNotify {
		this.notifyIsRunning;
	}

	notifyIsRunning{
		isRunning = true;
	}

	setServerOptions{ |args|
		"Setting server Options".postln;
		// use args for optional ServerOptions
		server.options.protocol_(\tcp);
		server.options.maxLogins_(8);
		server.options.protocol_(\tcp);
		//server.addr.connect;
		//server.startAliveThread( 0 );
		/*server.doWhenBooted({
			"remote tcp server started".postln;
			server.notify;
			server.initTree });*/
	}

}
