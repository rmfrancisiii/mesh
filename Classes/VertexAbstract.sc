VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexName, vertexType, vertexHost, meshName...args|
    var path = this.makeOSCdefPath("Request", "Vertex");
    var msg = VertexRequestMessage.newRequest(path, vertexName, vertexType, vertexHost, meshName, args);
    msg.sendRequest;
  }

  *makeOSCdef {|transaction, object, method|
    var name = this.makeOSCdefName(transaction, object);
    var path = this.makeOSCdefPath(transaction, object);

    OSCdef(name, {
      |msg, time, host, recvPort|
        msg = VertexRequestMessage.decode(host, msg);
        this.tryPerform(method, msg);
    }, path);
  }

  *makeAbstractOSCDefs {
    this.makeOSCdef("Request", "Vertex", \tryMakeVertex);
    this.makeOSCdef("Response", "Vertex", \vertexResponse);

    this.makeOSCdef("Request", "Proxy", \tryMakeProxy);
    this.makeOSCdef("Response", "Proxy", \proxyResponse);

  }

  *makeOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

  *makeOSCdefName {|transaction, object|
    ^ (this.asSymbol ++ transaction ++ object).asSymbol
  }

  *tryMakeVertex { |msg|
    if (this.vertexExists(msg))
       { this.sendVertexError(msg, "Vertex name already in use") }

       { "received vertex request".postln;
         try { this.makeVertex(msg) }
             { |error| this.sendError(msg, error)};
       };
  }

  *sendError { |msg, error|
    var errorString = error.errorString;
    var path = this.makeOSCdefPath("Response", "Vertex");
    msg.sendError(path, errorString)

  }

  *sendConfirmation{ |msg|
    var path = this.makeOSCdefPath("Response", "Vertex");
    msg.sendConfirmation(path);
  }

  *sendProxyRequest{ |msg|
      var path = this.makeOSCdefPath("Request", "Proxy");
      msg.sendProxyRequest(path);
  }

  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }

  *makeVertex{ |msg|
    var vertex = super.new.initVertex(msg);
    var name = msg.name;
    var vertexes = msg.mesh.vertexes;
    //Error("This is a basic error.").throw;
    vertexes.put(name, vertex);
    this.sendConfirmation(msg);
    this.sendProxyRequest(msg);
  }


	*vertexResponse { |msg|
    "Vertex Response!".postln;
    msg.args.postln
	}




}
