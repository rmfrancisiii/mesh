VertexTypeClassInterface {
  var <>vertexType, <>transaction, <>object, <>method, <>oscDefName, <>oscDefpath;

  *makeGenericClassInterfaces {|vertexType|
    //each VertexType class interface
    var interfaces = Array.with(
    ["Request", "Vertex", \tryMakeVertex],
    ["Response", "Vertex", \vertexResponseHandler],
    ["Request", "Proxy", \tryMakeProxy],
    ["Response", "Proxy", \proxyResponseHandler]
    ).do({|args|  this.new(vertexType, *args)});
  }

  *new { |vertexType, transaction, object, method|
    var oscDefName = (vertexType.asSymbol ++ transaction ++ object).asSymbol;
    var oscDefPath = "/" ++ vertexType.name ++ "/" ++ transaction ++ "/" ++ object;

    OSCdef(oscDefName, {|msg, time, host, recvPort|
       msg = VertexMessage.decode(host, msg);
       vertexType.tryPerform(method, msg);
    }, oscDefPath);
  }

}

VertexTypeInstanceInterface {

  var <>vertex, <>transaction, <>object, <>method, <>name, <>path;

  *new { |vertex, transaction, object, method|
    var name = (vertex.name ++ transaction ++ object).asSymbol;
    var path = "/" ++ vertex.name ++ "/" ++ transaction ++ "/" ++ object;
    var methodName = transaction.asSymbol;

    OSCdef(name, {
      |msg, time, host, recvPort|
      msg = VertexMessage.decode(host, msg);
      vertex.tryPerform(method, host, msg.args);
    }, path);

    vertex.addUniqueMethod(methodName,
      {|...args|
        var vertexHost = vertex.getVertexHost;
        var msg = VertexMessage.newRequest(path, name, vertex.class.asSymbol, vertexHost, vertex.mesh.name, methodName);
        msg.args_(args);
        msg.sendRequest;
      })
  }
}
