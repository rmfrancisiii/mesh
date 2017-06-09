VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *newVertexRequest { |...args|
    MeshDebugMon(thisFunctionDef);
    VertexTypeClassMessage.newRequest(*args).sendRequest;
  }

  *newVertexRequestHandler { |msg|
    MeshDebugMon(thisFunctionDef);

    if (this.vertexExists(msg))
       { this.sendError(msg, Error("VertexName already in use."))
       }

       { try { this.makeVertex(msg) }
             { |error| this.sendError(msg, error)};
       };
  }

  *sendError { |msg, error|
    var errorString = error.errorString;
    msg.methodName = \error;
    msg.args = [errorString];
    MeshDebugMon(thisFunctionDef, error);
    msg.sendResponse;
  }

  *errorHandler {|msg|
    MeshDebugMon(thisFunctionDef);
    msg.args.postln
  }

  *sendConfirmation{ |msg|
    msg.methodName = \confirmation;
    msg.args = [\CONFIRMED];
    MeshDebugMon(thisFunctionDef);
    msg.sendResponse;
  }

  *confirmationHandler {|msg|
    MeshDebugMon(thisFunctionDef);
    msg.args.postln; 
  }


  *makeVertex{ |msg|
    var vertex = super.new.initVertex(msg);
    var name = msg.name;
    var mesh = msg.mesh;
    var vertexes = mesh.vertexes;
    MeshDebugMon(thisFunctionDef);
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
    MeshDebugMon(thisFunctionDef);

	}

  *tryMakeProxy{ |msg|
    MeshDebugMon(thisFunctionDef);

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



  *sendProxyRequest{ |msg|
    MeshDebugMon(thisFunctionDef);
      /*var path = this.makeClassOSCdefPath("Request", "Proxy");
      msg.sendProxyRequest(path);*/
  }

  *sendProxyConfirmation{ |msg|
      var path = this.makeClassOSCdefPath("Response", "Proxy");
      MeshDebugMon(thisFunctionDef);
      msg.sendProxyResponse(path);
  }

  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }

  *makeClassOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

}
