VertexAbstract {

  *makeAbstractOSCDefs {
    var typeName = this.name;

    OSCdef((typeName++'RequestHandler').asSymbol, {|msg, time, addr, recvPort|
      this.tryMakeVertex(msg);
    }, '/' ++ typeName ++ '/request/vertex');

    OSCdef((typeName++'ProxyRequestHandler').asSymbol, {|msg, time, addr, recvPort|

      addr.postln;
      //var remoteHost = NetAddr.new(addr, recvPort);
      this.tryMakeProxy(msg, \remoteHost);
      }, '/' ++ typeName ++ '/request/proxy');

    OSCdef((typeName++'ConfirmationHandler').asSymbol, {|msg, time, addr, recvPort|
      this.confirmVertex(msg);
      }, '/' ++ typeName ++ '/confirm/vertex');

    OSCdef((typeName++'ProxyConfirmationHandler').asSymbol, {|msg, time, addr, recvPort|
      this.confirmProxy(msg);
      }, '/' ++ typeName ++ '/confirm/proxy');

  }

  *makeRequestPath {
    ^ "/" ++ this.asSymbol ++ "/request/"
  }

  *makeConfirmPath {
    ^ "/" ++ this.asSymbol ++ "/confirm/"
  }

  *requestor { |vertexName, vertexHost, mesh ...passArgs|
    var path = (this.makeRequestPath ++ "vertex");
    vertexHost.sendMsg(path, vertexName, mesh.name, *passArgs);
    ^ ("OSC message sent to " ++ path).postln;
  }

  *tryMakeVertex { |msg|
    // refactor msg into a collection?

		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var host = Mesh.thisHost;
    var args = msg[3..];

		"Make Vertex Request Received".postln;

		if (mesh.includesVertex(vertexName).not)
			{
        mesh.postln;
				if (this.makeVertex(vertexName, mesh, args))
					{ "Vertex added, sending Vertex Confirmation".postln;
						this.sendVertexConfirmation(vertexName, mesh.name, host);
						"sending Proxy request".postln;
						this.sendProxyRequest(vertexName, mesh.name, host)
					}{
						"failed to add Vertex".postln;
					}
			}

			{	"error, vertex exists".postln}

	}

  *tryMakeProxy { |msg|
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var host = \anotherHost; //host msg rc'd from;
    var args = msg[3..];
    "Make Proxy request received".postln;

    if (mesh.includesVertex(vertexName).not)
      {
        if (this.makeProxy(vertexName, mesh, host, args))
          { "Proxy added, sending Proxy Confirmation".postln;
            this.sendProxyConfirmation(vertexName, mesh.name, host);
          }{
            "failed to add Proxy".postln;
          }
      }

      {	"error, proxy exists".postln}

  }

  *sendProxyRequest{ |vertexName, meshName, vertexHost|
			var path = (this.makeRequestPath ++ "proxy");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
			^ ("OSC message sent to " ++ path).postln;
	}

	*sendVertexConfirmation { |vertexName, meshName, vertexHost|
			var path = (this.makeConfirmPath ++ "vertex");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
			^ ("OSC message sent to " ++ path).postln;
		}

	*sendProxyConfirmation { |vertexName, meshName, vertexHost|
			var path = (this.makeConfirmPath ++ "proxy");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
			^ ("OSC message sent to " ++ path).postln;
		}

	*confirmProxy {
		"proxy Created".postln;
		// send a rexponse acknowledging that proxy was created.
	}

	*confirmVertex {
		"Vertex Created".postln;
    // send a rexponse acknowledging that vertex was created.
	}

}
