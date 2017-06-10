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
    MeshDebugMon(thisFunctionDef, msg);
    msg.args.postln;
  }

  *sendProxyRequest{ |msg|
    msg.methodName = \proxyRequest;
    MeshDebugMon(thisFunctionDef);
    msg.sendProxyRequest;
  }

  *proxyRequestHandler { |msg|
    MeshDebugMon(thisFunctionDef, msg);
    msg.args.postln;
  }

  *sendProxyConfirmation{ |msg|
    msg.methodName = \proxyConfirmation;
    MeshDebugMon(thisFunctionDef);
  }

  *proxyConfirmationHandler { |msg|
      MeshDebugMon(thisFunctionDef);
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



  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }



}
