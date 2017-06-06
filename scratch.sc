/*  var <>vertexType, <>transaction, <>object, <>method, <>oscDefName, <>oscDefpath;


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

  printOn { |stream| stream << this.class.name << "(" << this.asArray << ")" }


}


VertexTypeInstanceMessage {
  var <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>methodName, <>args;
}
*/
