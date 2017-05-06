VertexAbstract {

  *makeAbstractOSCDefs {| typeName |

    typeName = this.name;

    OSCdef(\VertexServerRequestHandler, {|msg, time, addr, recvPort|
      VertexServer.tryMakeVertex(msg);
    }, '/' ++ typeName ++ '/request/vertex');

    OSCdef(\VertexServerProxyRequestHandler, {|msg, time, addr, recvPort|
      VertexServer.tryMakeProxy(msg);
      }, '/' ++ typeName ++ '/request/proxy');

    OSCdef(\VertexServerConfirmationHandler, {|msg, time, addr, recvPort|
      this.confirmVertex(msg);
      }, '/' ++ typeName ++ '/confirm/vertex');

    OSCdef(\VertexServerProxyConfirmationHandler, {|msg, time, addr, recvPort|
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
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var host = Mesh.thisHost;

		"Make Vertex Request Received".postln;

		if (mesh.includesVertex(vertexName).not)
			{
				if (this.makeVertex(vertexName, mesh))
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
    var host = Mesh.thisHost;
    "Make Proxy request received".postln;

    if (mesh.includesVertex(vertexName).not)
      {
        if (this.makeVertex(vertexName, mesh))
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
