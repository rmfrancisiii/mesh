VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexName, vertexType, vertexHost, meshName...args|
    var path = this.makeClassOSCdefPath("Request", "Vertex");
    var msg = VertexMessage.newRequest(path, vertexName, vertexType, vertexHost, meshName, \new, args);
    msg.sendRequest;
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








  // into VertexTypeInstanceInterface

  makeInstanceInterface { |transaction, object, method|
    var interfaceDef = InterfaceDef(transaction, object, method);
		this.makeInstanceOSCdef(interfaceDef);
		this.makeInstanceMethod(interfaceDef);
	}

	makeInstanceMethod{|interfaceDef|
		var methodName = interfaceDef.transaction.asSymbol;
		this.addUniqueMethod(methodName, {|...args|
				var path = ("/" ++ name ++ "/" ++ interfaceDef.transaction ++ "/" ++ interfaceDef.object);
        var vertexHost = this.getVertexHost;
        var msg = VertexMessage.newRequest(path, name, this.class.asSymbol, vertexHost, mesh.name, methodName);
        msg.args_(args);
        msg.sendRequest;
      })
	}

  makeInstanceOSCdef {|interfaceDef|
    var defName = this.makeInstanceOSCdefName(interfaceDef);
    var defPath = this.makeInstanceOSCdefPath(interfaceDef);

    OSCdef(defName, {
      |msg, time, host, recvPort|
      msg = VertexMessage.decode(host, msg);
      this.tryPerform(interfaceDef.method, host, msg.args);
    }, defPath);
  }

  makeInstanceOSCdefPath {|interfaceDef|
    ^ "/" ++ name ++ "/" ++ interfaceDef.transaction ++ "/" ++ interfaceDef.object
  }

  makeInstanceOSCdefName {|interfaceDef|
    ^ (name ++ interfaceDef.transaction ++ interfaceDef.object).asSymbol
  }









  // into VertexTypeClassInterface
    *makeClassOSCDefs {
      this.makeClassOSCdef("Request", "Vertex", \tryMakeVertex);
      this.makeClassOSCdef("Response", "Vertex", \vertexResponseHandler);
      this.makeClassOSCdef("Request", "Proxy", \tryMakeProxy);
      this.makeClassOSCdef("Response", "Proxy", \proxyResponseHandler);
    }

    *makeClassOSCdef { |transaction, object, method|
      var defName = this.makeClassOSCdefName(transaction, object);
      var defPath = this.makeClassOSCdefPath(transaction, object);

      OSCdef(defName, {
        |msg, time, host, recvPort|
          msg = VertexMessage.decode(host, msg);
          this.tryPerform(method, msg);
      }, defPath);
    }

    *makeClassOSCdefPath { |transaction, object|
      ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
    }

    *makeClassOSCdefName { |transaction, object|
      ^ (this.asSymbol ++ transaction ++ object).asSymbol
    }
}
