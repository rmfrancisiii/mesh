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
		// TODO: need to check order?
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

	// TODO: Check to be sure the following are for a local server.
	// MAYBE: add remote method calls?

	boot {
		server.reboot
	}

	devices {
		^ ServerOptions.devices
	}

	// protocol{^ server.options.protocol}
	// maxLogins{^ server.options.maxLogins}
	// ping { ^ server.ping }
	// addr {^ server.addr }
	// connect {^ server.addr.connect}
	// makeWindow {^ server.makeWindow}
}