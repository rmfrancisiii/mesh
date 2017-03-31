VertexServer : VertexAbstract {
	var <>host, <>server;

	*initClass {

	}

	*new { |myHost, myServer| ^ super.new.init(myHost, myServer) }

	init { |myHost, myServer|
		host = myHost;
		server = myServer;
		this.setParams;
	}

	doesNotUnderstand {|selector ... args|
		// var options = server.options;
		var result = nil;
		(result = server.tryPerform(selector, *args)) !? { ^ result };
		(result = server.options.tryPerform(selector, *args)) !? { ^ result };
		(result = server.addr.tryPerform(selector, *args)) !? { ^ result };
	}

	setParams {
		server.options.protocol_(\tcp);
		server.options.maxLogins_(8);
	}

	// status { ^ server.status }

	boot { server.reboot }

	//protocol{^ server.options.protocol}

	maxLogins{^ server.options.maxLogins}




	devices { ^ ServerOptions.devices }

	// ping { ^ server.ping }

	// addr {^ server.addr }

	// connect {^ server.addr.connect}

	// makeWindow {^ server.makeWindow}
}