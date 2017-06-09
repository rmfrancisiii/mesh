VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *newVertexRequest { |...args|
    VertexTypeClassMessage.newRequest(*args).sendRequest;
  }

  *newVertexRequestHandler { |msg|
    MeshDebugMon(thisFunctionDef, msg);

    if (this.vertexExists(msg))
       { this.sendError(msg, Error("VertexName already in use."))
       }

       { "received vertex request".postln;
         try { this.makeVertex(msg) }
             { |error| this.sendError(msg, error)};
       };
  }

  *errorHandler {|msg|
    MeshDebugMon(thisFunctionDef, msg);
  }

  *confirmationHandler {|msg|
    MeshDebugMon(thisFunctionDef, msg);
  }

  *makeVertex{ |msg|
    var vertex = super.new.initVertex(msg);
    var name = msg.name;
    var mesh = msg.mesh;
    var vertexes = mesh.vertexes;
    MeshDebugMon(thisFunctionDef, msg);
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
    MeshDebugMon(thisFunctionDef, error);

    msg.sendError(errorString)
  }

  *sendConfirmation{ |msg|
    MeshDebugMon(thisFunctionDef, msg);
    msg.sendConfirmation;
  }

  *sendProxyRequest{ |msg|
    MeshDebugMon(thisFunctionDef, msg);
      /*var path = this.makeClassOSCdefPath("Request", "Proxy");
      msg.sendProxyRequest(path);*/
  }

  *sendProxyConfirmation{ |msg|
      var path = this.makeClassOSCdefPath("Response", "Proxy");
      MeshDebugMon(thisFunctionDef, msg);
      msg.sendProxyResponse(path);
  }

  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }

  *makeClassOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

}
