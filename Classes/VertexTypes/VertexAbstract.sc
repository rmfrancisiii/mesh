VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexHost...passArgs|
    var path = (this.requestPath("Vertex"));
    vertexHost.sendMsg(path, *passArgs);
  }

  *makeAbstractOSCDefs {
    var typeName = this.name;

    OSCdef(this.requestName("Vertex"), {|msg, time, requestingHost, recvPort|
      "received vertex request".postln;
      this.tryMakeVertex(requestingHost, msg);
    }, this.requestPath("Vertex"));

    OSCdef(this.requestName("Proxy"), {|msg, time, vertexHost, recvPort|
      if (vertexHost.ip != Mesh.thisHost.ip)
        { "received proxy request".postln;
          this.tryMakeProxy(vertexHost, msg)};
      }, this.requestPath("Proxy"));

    OSCdef(this.confirmName("Vertex"), {|msg, time, vertexHost, recvPort|
      this.confirmVertex(vertexHost, msg);
      }, this.confirmPath("Vertex"));

    OSCdef(this.confirmName("Proxy"), {|msg, time, requestingHost, recvPort|
      this.confirmProxy(requestingHost, msg);
      }, this.confirmPath("Proxy"));

    // TODO: Add OSCdef for ErrorVertex

    // TODO: Add OSCdef for ErrorProxy

  }

  *requestPath {|method|
    ^ "/" ++ this.name ++ "/request/" ++ method
  }

  *confirmPath {|method|
    ^ "/" ++ this.asSymbol ++ "/confirm/" ++ method
  }

  *errorPath {|method|
    ^ "/" ++ this.asSymbol ++ "/error/" ++ method
  }

  *requestName {|method|
    ^ (this.asSymbol ++ "Request" ++ method).asSymbol
  }

  *confirmName {|method|
    ^ (this.asSymbol ++ "Confirm" ++ method).asSymbol
  }

  *errorName {|method|
    ^ (this.asSymbol ++ "Error" ++ method).asSymbol
  }

  *tryMakeVertex { |requestingHost, msg|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var vertexHost = Mesh.thisHost;
    var args = msg[3..];

		if (mesh.includesVertex(vertexName).not)
			{ // TODO: add Try... Catch
				if (this.makeVertex(vertexName, mesh, args))
					{ "Vertex added, sending Vertex Confirmation".postln;
						this.sendVertexConfirmation(vertexName, mesh.name, requestingHost);
						this.sendProxyRequest(vertexName, mesh.name)
					}{
            // TODO: Could not construct Vertex Error Message
            this.sendVertexError(vertexName, mesh.name, requestingHost); // add caught error?
					}
			}

			{
        // TODO: return duplicate Vertex Error Message
        this.sendVertexError(vertexName, mesh.name, requestingHost);
        // add caught error?
      }

	}

  *makeVertex{ |vertexName, mesh...args|
    var vertex = super.new.initVertex(vertexName, mesh, args);
    mesh.vertexes.put(vertexName, vertex);
    ^ true
  }

  *sendVertexConfirmation { |vertexName, meshName, requestingHost|
			var path = (this.confirmPath("Vertex"));
			requestingHost.sendMsg(path, vertexName, meshName);
		}

  *sendVertexError { |vertexName, meshName, requestingHost|
  			var path = (this.errorPath("Vertex"));
  			requestingHost.sendMsg(path, vertexName, meshName);
  		}

  *sendProxyRequest{ |vertexName, meshName|
  			var path = (this.requestPath("Proxy"));
  			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
  	}

  *tryMakeProxy { |vertexHost, msg|
    var proxyHost = Mesh.thisHost;
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var args = msg[3..];

    if (mesh.includesVertex(vertexName).not)
      {
        if (this.makeProxy(vertexName, mesh, vertexHost, args))
          { "Proxy added, sending Proxy Confirmation".postln;
            this.sendProxyConfirmation(vertexName, mesh.name, proxyHost, vertexHost);
          }{
            // TODO:  return Proxy Creation Failed Error Message
            this.sendProxyError(vertexName, mesh.name, vertexHost);
            // add caught error?
          }
      }

      {
        // TODO: return duplicate Proxy Creation Error Message
        this.sendProxyError(vertexName, mesh.name, vertexHost);
        // add caught error?
      }

  }

  *makeProxy{ |vertexName, mesh, vertexHost, args|
    var proxy = super.new.initProxy(vertexName, mesh, vertexHost, args);
    mesh.vertexes.put(vertexName, proxy);
    ^ true
  }

  *sendProxyConfirmation { |vertexName, meshName, proxyHost, vertexHost|
    var path = (this.confirmPath("Proxy"));
    vertexHost.sendMsg(path, vertexName, meshName);
  }

  *sendProxyError { |vertexName, meshName, vertexHost|
  	var path = (this.errorPath("Proxy"));
  	vertexHost.sendMsg(path, vertexName, meshName);
  }

	*confirmProxy {|proxyHost, msg|
  	"Proxy Confirmed".postln;
	}

	*confirmVertex {|vertexHost, msg|
		"Vertex Confirmed".postln;
	}

}
