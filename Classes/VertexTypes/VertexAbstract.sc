VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexHost...passArgs|
    var path = this.makeOSCdefPath("Request", "Vertex");
    vertexHost.sendMsg(path, *passArgs)
  }

  *makeOSCdef {|transaction, object, action|
    OSCdef(this.makeOSCdefName(transaction, object), {|msg, time, host, recvPort|
        action.value(host, msg);
    }, this.makeOSCdefPath(transaction, object));
  }

  *makeAbstractOSCDefs {
    var typeName = this.name;

    this.makeOSCdef("Request", "Vertex",
        { |requestingHost, msg| this.tryMakeVertex(requestingHost, msg) });

    this.makeOSCdef("Request", "Proxy",
        { |vertexHost, msg| this.tryMakeProxy(vertexHost, msg) });

    this.makeOSCdef("Confirm", "Vertex",
        { |requestingHost, msg| this.confirmVertex(requestingHost, msg) });

    this.makeOSCdef("Confirm", "Proxy",
        { |vertexHost, msg| this.confirmProxy(vertexHost, msg) });

    this.makeOSCdef("Error", "Vertex",
        { |requestingHost, msg| this.errorVertex(requestingHost, msg) });

    this.makeOSCdef("Error", "Proxy",
        { |vertexHost, msg| this.errorProxy(vertexHost, msg) });
  }

  *makeOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

  *makeOSCdefName {|transaction, object|
    ^ (this.asSymbol ++ transaction ++ object).asSymbol
  }

  *tryMakeVertex { |requestingHost, msg|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var vertexHost = Mesh.thisHost;
    var args = msg[3..];

    if (mesh.includesVertex(vertexName))
      { this.sendVertexError(vertexName, mesh.name, requestingHost, "Vertex name already in use")}
    // otherwise
    { "received vertex request".postln;
      try { this.makeVertex(vertexName, mesh, args) }
          { |error| this.sendVertexError(vertexName, mesh.name, requestingHost, error)
          }
    }
  }

  *makeVertex{ |vertexName, mesh...args|
    var vertex = super.new.initVertex(vertexName, mesh, args);
    mesh.vertexes.put(vertexName, vertex);
    Error("This is a basic error.").throw;
    ^ true
  }

  *sendVertexConfirmation { |vertexName, meshName, requestingHost|
			var path = (this.makeOSCdefPath("Confirm", "Vertex"));
			requestingHost.sendMsg(path, vertexName, meshName);
		}

  *sendVertexError { |vertexName, meshName, requestingHost, error|
  			var path = (this.makeOSCdefPath("Error", "Vertex"));
        var errorString = error.errorString;
  			requestingHost.sendMsg(path, vertexName, meshName, errorString);
  		}

  *sendProxyRequest{ |vertexName, meshName|
  			var path = (this.makeOSCdefPath("Request", "Proxy"));
  			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
  	}

  *tryMakeProxy { |vertexHost, msg|
    var proxyHost = Mesh.thisHost;
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var args = msg[3..];

    if (vertexHost.ip != Mesh.thisHost.ip){

      "received proxy request".postln;

      if (mesh.includesVertex(vertexName).not)
        {
          if (this.makeProxy(vertexName, mesh, vertexHost, args))
            { "Proxy added, sending Proxy Confirmation".postln;
              this.sendProxyConfirmation(vertexName, mesh.name, proxyHost, vertexHost);
            }{ var error;
              // TODO:  construct Proxy Creation Failed Error Message
              this.sendProxyError(vertexName, mesh.name, vertexHost, error);
              // add caught error?
            }
        }

        {var error;
          // TODO:  construct Proxy Creation Error Message
          this.sendProxyError(vertexName, mesh.name, vertexHost, error);
          // add caught error?
        }
    }
  }

  *makeProxy{ |vertexName, mesh, vertexHost, args|
    var proxy = super.new.initProxy(vertexName, mesh, vertexHost, args);
    mesh.vertexes.put(vertexName, proxy);
    ^ true
  }

  *sendProxyConfirmation { |vertexName, meshName, proxyHost, vertexHost|
    var path = (this.makeOSCdefPath("Confirm", "Proxy"));
    vertexHost.sendMsg(path, vertexName, meshName);
  }

  *sendProxyError { |vertexName, meshName, vertexHost, error|
  	var path = (this.makeOSCdefPath("Error", "Proxy"));
  	vertexHost.sendMsg(path, vertexName, meshName, error);
  }

	*confirmProxy {|proxyHost, msg|
  	"Proxy Confirmed".postln;
	}

	*confirmVertex {|vertexHost, msg|
		"Vertex Confirmed".postln;
	}

	*errorProxy {|proxyHost, msg|
  	"Proxy Error".postln;
	}

	*errorVertex {|vertexHost, msg|
    var vertexName = msg[1];
    var meshName = msg[2];
    var errorString = msg[3];
    // OR?:  Error(msg[3]).throw;
		("Vertex " ++ vertexName ++ "reports error in " ++ meshName).postln; errorString.postln;

	}


}
