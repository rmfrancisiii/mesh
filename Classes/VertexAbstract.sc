VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *sendNewVertex { |...args|
    VertexMessage.newRequest(*args).sendRequest;
  }

  *newVertexHandler { |msg|
    MeshDebugMon(thisFunctionDef);

    if (this.vertexExists(msg))
       { this.sendError(msg, Error("VertexName already in use."))}

       { try { this.makeVertex(msg) }
             { |error| this.sendError(msg, error)};
       };
  }

  *makeVertex{ |msg|
    var vertex = super.new.initVertex(msg);
    //Error("This is a basic error.").throw;
    MeshDebugMon(thisFunctionDef);

    msg.mesh.vertexes.put(msg.name, vertex);
    this.sendConfirmation(msg);
    this.sendProxyRequest(msg);
  }


  *sendError { |msg, error|
    var errorString = error.errorString;
    msg.methodName = \error;
    msg.args = [errorString];
    msg.sendResponse;
  }

  *errorHandler { |msg|
    ("Error: " ++ msg.args).postln
  }

  *sendConfirmation{ |msg|
    msg.methodName = \confirmation;
    msg.sendResponse;
  }

  *confirmationHandler {|msg|
    ("Successfully Created " ++ msg.name).postln;
  }

  *sendProxyRequest{ |msg|
    msg.methodName = \proxyRequest;
    msg.args = [\proxyVertex];
    MeshDebugMon(thisFunctionDef);
    msg.sendProxyRequest;
  }

  *proxyRequestHandler{ |msg|
    // REMOVE!!! ONLY FOR TESTING ON ONE MACHINE!!!
    msg.name = (msg.name ++ "Proxy").asSymbol;
    //

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
    vertexes.put(msg.name, proxy);
    this.sendProxyConfirmation(msg);
  }

  *sendProxyConfirmation{ |msg|
    MeshDebugMon(thisFunctionDef);
    msg.methodName = \proxyConfirmation;
    msg.sendResponse;
  }

  *proxyConfirmationHandler { |msg|
      MeshDebugMon(thisFunctionDef);
      "proxy response received".postln;

      // from vertexResponseHandler

      //should track that all mesh hosts confirm proxy request
      // and resend if necessary?

  }

  *vertexExists { |msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }

  doesNotUnderstand {|selector ... args|
    this.methodname = selector;
    this.args = args;
    MeshDebugMon(this);
//    (result = addr.tryPerform(selector, *args));
  }



  initVertex {
   this.subclassResponsibility(thisMethod);
  }

  initProxy {
   this.subclassResponsibility(thisMethod);
  }
}
