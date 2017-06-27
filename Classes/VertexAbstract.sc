VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *sendNewVertex { |...args|
    VertexMessage.newVertexRequest(*args).sendRequest;
  }

  *newVertexHandler { |msg|
    if (this.vertexExists(msg))
       { this.sendError(msg, Error("VertexName already in use."))}

       { try { this.makeVertex(msg) }
             { |error| this.sendError(msg, error)};
       };
  }

  *makeVertex{ |msg|
    var vertex = super.new.initVertex(msg);
    //Error("This is a basic error.").throw;
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
    (msg.args[0]).postln
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
    msg.sendProxyRequest;
  }

  *proxyRequestHandler{ |msg|
   if (this.vertexExists(msg).not)
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
    msg.methodName = \proxyConfirmation;
    msg.sendResponse;
  }

  *proxyConfirmationHandler { |msg|
      "proxy response received".postln;
      // from vertexResponseHandler
      // should track that all mesh hosts confirm proxy request
      // and resend if necessary?
  }

  sendMethodRequest { |selector, args|
    var vertexHost = this.getVertexHost;
    var msg = VertexMessage.newMethodRequest(this, vertexHost, selector, args).sendRequest;
  }

  sendProxyUpdate { |args|
    var msg = VertexMessage.newMethodRequest(this, Mesh.broadcastAddr, \proxyUpdate, args).sendRequest;
  }

  proxyUpdateHandler {|args|
    if (isProxy)
      { args.asAssociations.do({ |item|
        this.tryPerform(item.key.asSetter, item.value)})
    };
  }

  *vertexExists { |msg|
    ^ (msg.mesh).includesVertex(msg.name)
  }

  doesNotUnderstand {|selector ...args|
    this.sendMethodRequest(selector, args);
    ^ "sent method request";
  }

  initVertex {
   this.subclassResponsibility(thisMethod);
  }

  initProxy {
   this.subclassResponsibility(thisMethod);
  }

  getVertexHost {
   this.subclassResponsibility(thisMethod);
  }

  free {
    this.subclassResponsibility(thisMethod);
  }

  /*printOn {|stream|
    stream << this.instVarDict.values.postln;
  }*/
}
