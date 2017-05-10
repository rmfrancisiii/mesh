VertexAbstract {
  var <>name, <>mesh;

  *requestor { |vertexName, vertexHost, mesh ...passArgs|
    var path = (this.requestPath("vertex"));
    vertexHost.sendMsg(path, vertexName, mesh.name, *passArgs);
  }

  *makeAbstractOSCDefs {
    var typeName = this.name;

    OSCdef(this.requestName("Vertex"), {|msg, time, requestingHost, recvPort|
      this.tryMakeVertex(msg, requestingHost);
    }, this.requestPath("Vertex"));

    OSCdef(this.requestName("Proxy"), {|msg, time, vertexHost, recvPort|
      this.tryMakeProxy(msg, vertexHost);
      }, this.requestPath("Proxy"));

    OSCdef(this.confirmName("Vertex"), {|msg, time, addr, recvPort|
      this.confirmVertex(msg);
      }, this.confirmPath("Vertex"));

    OSCdef(this.confirmName("Proxy"), {|msg, time, addr, recvPort|
      this.confirmProxy(msg);
      }, this.confirmPath("Proxy"));
  }

  *requestPath {|method|
    ^ "/" ++ this.name ++ "/request/" ++ method
  }

  *confirmPath {|method|
    ^ "/" ++ this.asSymbol ++ "/confirm/" ++ method
  }

  *requestName {|method|
    ^ (this.asSymbol ++ "Request" ++ method).asSymbol
  }

  *confirmName {|method|
    ^ (this.asSymbol ++ "Confirm" ++ method).asSymbol
  }


  *tryMakeVertex { |msg, requestingHost|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var vertexHost = Mesh.thisHost;
    var args = msg[3..];

		if (mesh.includesVertex(vertexName).not)
			{
        mesh.postln;
				if (this.makeVertex(vertexName, mesh, args))
					{ "Vertex added, sending Vertex Confirmation".postln;
						this.sendVertexConfirmation(vertexName, mesh.name, vertexHost, requestingHost);
						"sending Proxy request".postln;
						this.sendProxyRequest(requestingHost, vertexName, mesh.name, vertexHost)
					}{
						"failed to add Vertex".postln;
					}
			}

			{	"error, vertex exists".postln}

	}

  *tryMakeProxy { |msg, vertexHost|
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var host = vertexHost;
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
			var path = (this.requestPath("proxy"));
			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
	}

	*sendVertexConfirmation { |vertexName, meshName, vertexHost, requestingHost|
			var path = (this.confirmPath("vertex"));
			Mesh.requestingHost.sendMsg(path, vertexName, meshName);
		}

	*sendProxyConfirmation { |vertexName, meshName, proxyHost, vertexHost|
			var path = (this.confirmPath("proxy"));
			Mesh.vertexHost.sendMsg(path, vertexName, meshName, proxyHost);
		}

	*confirmProxy {
		"proxy Created".postln;
	}

	*confirmVertex {
		"Vertex Created".postln;
	}

}
