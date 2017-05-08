VertexAbstract {

  *makeAbstractOSCDefs {
    var typeName = this.name;

    OSCdef((typeName++'RequestHandler').asSymbol, {|msg, time, addr, recvPort|
      this.tryMakeVertex(msg);
    }, '/' ++ typeName ++ '/request/vertex');

    OSCdef((typeName++'ProxyRequestHandler').asSymbol, {|msg, time, addr, recvPort|
      this.tryMakeProxy(msg, addr);
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
  }

  *tryMakeVertex { |msg|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var host = Mesh.thisHost;
    var args = msg[3..];

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

  *tryMakeProxy { |msg, remoteHost|
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var host = remoteHost;
    var args = msg[3..];

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
	}

	*sendVertexConfirmation { |vertexName, meshName, vertexHost|
			var path = (this.makeConfirmPath ++ "vertex");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
		}

	*sendProxyConfirmation { |vertexName, meshName, vertexHost|
			var path = (this.makeConfirmPath ++ "proxy");
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
		}

	*confirmProxy {
		"proxy Created".postln;
	}

	*confirmVertex {
		"Vertex Created".postln;
	}

}
