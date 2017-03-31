VertexServer : VertexAbstract {
	var <>host, <>server;

	*new { |myHost, myServer| ^ super.new.init(myHost, myServer) }

	init { |myHost, myServer|
		host = myHost;
		server = myServer;
	}

	status { ^ server.status }

	boot {
		// FIXME: add filter to prevent or pass boot messages over network?
		^ server.boot
	}

	devices { ^ ServerOptions.devices }

	ping { ^ server.ping }

	addr {^ server.addr }

	connect {^ server.addr.connect}

	makeWindow {^ server.makeWindow}
}