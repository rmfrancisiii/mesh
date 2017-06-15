VertexTypeClassInterface {

  *makeGenericClassInterfaces { |vertexType|
    var oscDefName = (vertexType.asSymbol ++ "Interface").asSymbol ;
    var oscDefPath = "/" ++ vertexType.asSymbol ++ "/interface" ;

    OSCdef(oscDefName, { |msg, time, host, recvPort|
      var method ;
      msg = VertexMessage.decode(host, msg) ;
      method = (msg.methodName++"Handler").asSymbol ;
      vertexType.tryPerform(method, msg) ;
    }, oscDefPath)
  }

}

VertexTypeInstanceInterface {

  *makeInstanceInterface { |vertex|
    var oscDefName = (vertex.name.asSymbol ++ "Interface").asSymbol ;
    var oscDefPath = "/" ++ vertex.name.asSymbol ++ "/interface" ;
    MeshDebugMon(thisFunctionDef, oscDefName, oscDefPath);

    OSCdef(oscDefName, { |msg, time, host, recvPort|
      var method ;
      var vertex ;
      MeshDebugMon(thisFunctionDef, msg)
//      msg = VertexMessage.decode(host, msg) ;
//      method = (msg.methodName++"Handler").asSymbol ;
//      vertex = Vertex(vertex.name);
//      vertex.tryPerform(method, *msg.args) ;
    }, oscDefPath)
  }

}
