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
    ^ super.newCopyArgs(vertexType, transaction, object, method, oscDefName, oscDefPath).init;
  }

  init{
    OSCdef(oscDefName, {|msg, time, host, recvPort|
       msg = VertexMessage.decode(host, msg);
       vertexType.tryPerform(method, msg);
    }, oscDefpath);
  }
}

VertexTypeInstanceInterface {

  var <>vertex, <>transaction, <>object, <>method, <>name, <>path;

  *new { |vertex, transaction, object, method|
    ^ super.newCopyArgs(vertex, transaction, object, method).init;
  }

  init {
    name = (vertex.name ++ transaction ++ object).asSymbol;
    path = "/" ++ vertex.name ++ "/" ++ transaction ++ "/" ++ object;

    this.makeInstanceMethod;

    OSCdef(name, {
      |msg, time, host, recvPort|
      msg = VertexMessage.decode(host, msg);
      vertex.tryPerform(method, host, msg.args);
    }, path);

  }

	makeInstanceMethod{
		var methodName = transaction.asSymbol;
		vertex.addUniqueMethod(methodName,
      {|...args|
        var vertexHost = vertex.getVertexHost;
        var msg = VertexMessage.newRequest(path, name, vertex.class.asSymbol, vertexHost, vertex.mesh.name, methodName);
        msg.args_(args);
        msg.sendRequest;
      })
	}

}
