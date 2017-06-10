/*

Meta_Vertex:getMesh
Meta_Vertex:currentMesh
Meta_Vertex:getVertex
Meta_Vertex:new
Meta_Vertex:getHost
Meta_VertexAbstract:newVertexRequest
Meta_VertexTypeClassMessage:newRequest
VertexTypeClassMessage:sendRequest
-> New Vertex Request sent
Meta_VertexTypeClassMessage:decode
Meta_VertexAbstract:newVertexRequestHandler
VertexServer:initVertex
VertexServer:makeInstanceInterfaces
Meta_VertexAbstract:makeVertex
Meta_VertexAbstract:sendConfirmation
VertexTypeClassMessage:sendResponse
Meta_VertexAbstract:sendProxyRequest
Meta_VertexTypeClassMessage:decode
Meta_VertexAbstract:confirmationHandler
[ CONFIRMED ]




  var <>vertexType, <>transaction, <>object, <>method, <>oscDefName, <>oscDefpath;


    //each VertexType class interface
.do({|args|  this.new(vertexType, *args)});
  }

  *new { |vertexType, transaction, object, method|



  }

VertexTypeInstanceInterface {

  var <>vertex, <>transaction, <>object, <>method, <>name, <>path;

  *new { |vertex, transaction, object, method|
    var name = (vertex.name ++ transaction ++ object).asSymbol;
    var path = "/" ++ vertex.name ++ "/" ++ transaction ++ "/" ++ object;
    var methodName = transaction.asSymbol;

    OSCdef(name, {
      |msg, time, host, recvPort|
      "oscdef!!".postln;
      msg = VertexTypeInstanceMessage.decode(host, msg);
      vertex.tryPerform(method, host, msg.args);
    }, path);

    methodName.postln;

    vertex.addUniqueMethod(methodName,
      {|...args|
        var vertexHost = vertex.getVertexHost;
        // PROBLEM HERE!
        var msg = VertexTypeInstanceMessage.newRequest(path, name, vertex.class.asSymbol, vertexHost, vertex.mesh.name, methodName);
        "BOOT".postln;
        vertexHost.postln;
        vertex.mesh.name.postln;
        msg.postln;
        args.postln;
        msg.args_(args);
        msg.sendRequest;
      })
  }
}


// VertexTypeClassMessage {




  sendError { |responsePath, error|
    var response = this.asErrorResponse(responsePath, error);
    requestingHost.sendMsg(*response);
  }

  sendConfirmation { |responsePath|
    var response = this.asConfirmation(responsePath);
    requestingHost.sendMsg(*response);
  }

  sendProxyRequest {|requestPath|
    var request = this.asProxyRequest(requestPath);
    var broadcastAddr = Mesh.broadcastAddr;
    broadcastAddr.sendMsg(*request)
  }

  sendProxyConfirmation { |responsePath|
    var response = this.asConfirmation(responsePath);
    vertexHost.sendMsg(*response);
  }



  asErrorResponse{|responsePath, error|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name, mesh.name, \error, error)
  }

  asConfirmation{|responsePath|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name,  mesh.name, \confirmed)
  }

  asProxyRequest{|responsePath|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name,  mesh.name, *args)
  }

  asArray {
    ^ Array.with(path, name, type, vertexHost, requestingHost, mesh, *args)
  }




}




*vertexResponseHandler { |msg|
  var response = msg.args[0];
  var result = case
      { response == \error }   {
        (msg.name ++ " Vertex Error:  " ++ msg.args[1]).postln }
      { response == \confirmed } {
        (msg.name ++ " Vertex Confirmed!").postln };
  result.value;
  MeshDebugMon(thisFunctionDef, msg);

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

  // from vertexResponseHandler

  //should track that all mesh hosts confirm proxy request
  and resend if necessary?

}



*sendProxyRequest{ |msg|
  MeshDebugMon(thisFunctionDef);
    // var path = this.makeClassOSCdefPath("Request", "Proxy");
    // msg.sendProxyRequest(path);
}

*sendProxyConfirmation{ |msg|
    var path = this.makeClassOSCdefPath("Response", "Proxy");
    MeshDebugMon(thisFunctionDef);
    msg.sendProxyResponse(path);
}



*makeClassOSCdefPath {|transaction, object|
  ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
}
*/
