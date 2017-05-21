VertexTypeClassInterface {

  var <>vertexType, <>transaction, <>object, <>method, <>oscDefName, <>oscDefpath;

    *new { |vertexType, transaction, object, method|
    var oscDefName = (vertexType.asSymbol ++ transaction ++ object).asSymbol;
    var oscDefPath = "/" ++ vertexType.name ++ "/" ++ transaction ++ "/" ++ object;

    "Transaction:  ".post; transaction.postln;
    "object:  ".post; object.postln;
    "method:  ".post; method.postln;
    "Type:  ".post; vertexType.postln;


    oscDefPath.postln;
    oscDefName.postln;

      ^ super.newCopyArgs(transaction, object, method, vertexType, oscDefName, oscDefPath).init;
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

	makeInstanceMethod{|def|
		var methodName = transaction.asSymbol;
		this.addUniqueMethod(methodName,
      {|...args|
        var vertexHost = vertex.getVertexHost;
        var msg = VertexMessage.newRequest(path, name, vertex.class.asSymbol, vertexHost, vertex.mesh.name, methodName);
        msg.args_(args);
        msg.sendRequest;
      })
	}

}