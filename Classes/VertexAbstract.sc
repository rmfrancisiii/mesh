VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexName, vertexType, vertexHost, meshName...args|
    var path = this.makeOSCdefPath("Request", "Vertex");
    var msg = VertexMessage.newRequest(path, vertexName, vertexType, vertexHost, meshName, args);
    msg.sendRequest;
  }

  *makeOSCdef {|transaction, object, method|
    var name = this.makeOSCdefName(transaction, object);
    var path = this.makeOSCdefPath(transaction, object);

    OSCdef(name, {
      |msg, time, host, recvPort|
        msg = VertexMessage.decode(host, msg);
        this.tryPerform(method, msg);
    }, path);
  }

  *makeAbstractOSCDefs {
    this.makeOSCdef("Request", "Vertex", \tryMakeVertex);
    this.makeOSCdef("Response", "Vertex", \vertexResponseHandler);
    this.makeOSCdef("Request", "Proxy", \tryMakeProxy);
    this.makeOSCdef("Response", "Proxy", \proxyResponseHandler);
  }

  *makeOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

  *makeOSCdefName {|transaction, object|
    ^ (this.asSymbol ++ transaction ++ object).asSymbol
  }

  *tryMakeVertex { |msg|
    if (this.vertexExists(msg))
       { this.sendError(msg, Error("VertexName already in use."))}

       { "received vertex request".postln;
         try { this.makeVertex(msg) }
             { |error| this.sendError(msg, error)};
       };
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

  *vertexResponseHandler { |msg|
    var response = msg.args[0];
    var result = case
        { response == \error }   {
          (msg.name ++ " Vertex Error:  " ++ msg.args[1]).postln }
        { response == \confirmed } {
          (msg.name ++ " Vertex Confirmed!").postln };
    result.value;
	}

  *tryMakeProxy{ |msg|
   if (this.vertexExists(msg))
       { this.sendError(msg, Error("VertexName already in use."))}

       { "received proxy request".postln;
         try { this.makeProxy(msg) }
             { |error| this.sendError(msg, error)};
       };
  }

  *makeProxy{ |msg|
    var proxy = super.new.initProxy(msg);
    var vertexes = msg.mesh.vertexes;
    //Error("This is a basic error.").throw;
    vertexes.put(name, proxy);
    this.sendProxyConfirmation(msg);
  }

  *proxyResponseHandler { |msg|
    /* from vertexResponseHandler

    should track that all mesh hosts confirm proxy request
    and resend if necessary?*/

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

  *sendProxyConfirmation{ |msg|
      var path = this.makeOSCdefPath("Response", "Proxy");
      msg.sendProxyResponse(path);
  }

  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }





}
