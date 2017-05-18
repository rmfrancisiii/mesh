VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexName, vertexType, vertexHost, meshName...args|
    var path = this.makeClassOSCdefPath("Request", "Vertex");
    var msg = VertexMessage.newRequest(path, vertexName, vertexType, vertexHost, meshName, args);
    msg.sendRequest;
  }

  *makeClassOSCDefs {
    this.makeClassOSCdef("Request", "Vertex", \tryMakeVertex);
    this.makeClassOSCdef("Response", "Vertex", \vertexResponseHandler);
    this.makeClassOSCdef("Request", "Proxy", \tryMakeProxy);
    this.makeClassOSCdef("Response", "Proxy", \proxyResponseHandler);
  }

  // TODO: abstract out (transaction, object, method) into interfaceDef object?

  *makeClassOSCdef {|transaction, object, method|
    var defName = this.makeClassOSCdefName(transaction, object);
    var defPath = this.makeClassOSCdefPath(transaction, object);

    OSCdef(defName, {
      |msg, time, host, recvPort|
        msg = VertexMessage.decode(host, msg);
        this.tryPerform(method, msg);
    }, defPath);
  }

// interfaceDef
  *makeClassOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

  // interfaceDef
  *makeClassOSCdefName {|transaction, object|
    ^ (this.asSymbol ++ transaction ++ object).asSymbol
  }

  // interfaceDef
  makeInstanceInterface{|transaction, object, method|
				this.makeInstanceOSCdef(transaction, object, method);
				this.makeInstanceMethod(transaction, object, method);
	}

  // interfaceDef
	makeInstanceMethod{|transaction, object, method|
    // Encode MSG into VertexMessage
		var methodName = transaction.asSymbol;
		this.addUniqueMethod(methodName, {|...args|
				var path = ("/" ++ name ++ "/" ++ transaction ++ "/" ++ object);
        var vertexHost = this.getVertexHost;
				vertexHost.sendMsg(path, *args) });
	}

  // interfaceDef
  makeInstanceOSCdef {|transaction, object, method|

    // Decode MSG from VertexMessage
    var defName = this.makeInstanceOSCdefName(transaction, object);
    var defPath = this.makeInstanceOSCdefPath(transaction, object);

    OSCdef(defName, {
      |msg, time, host, recvPort|
        this.tryPerform(method, host, msg);
    }, defPath);
  }

  // interfaceDef
  makeInstanceOSCdefPath {|transaction, object|
    ^ "/" ++ name ++ "/" ++ transaction ++ "/" ++ object
  }

  // interfaceDef
  makeInstanceOSCdefName {|transaction, object|
    ^ (name ++ transaction ++ object).asSymbol
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

    "proxy response received".postln;

    /* from vertexResponseHandler

    should track that all mesh hosts confirm proxy request
    and resend if necessary?*/

  }

  *sendError { |msg, error|
    var errorString = error.errorString;
    var path = this.makeClassOSCdefPath("Response", "Vertex");
    msg.sendError(path, errorString)
  }

  *sendConfirmation{ |msg|
    var path = this.makeClassOSCdefPath("Response", "Vertex");
    msg.sendConfirmation(path);
  }

  *sendProxyRequest{ |msg|
      var path = this.makeClassOSCdefPath("Request", "Proxy");
      msg.sendProxyRequest(path);
  }

  *sendProxyConfirmation{ |msg|
      var path = this.makeClassOSCdefPath("Response", "Proxy");
      msg.sendProxyResponse(path);
  }

  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }

}
