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

    OSCdef(oscDefName, { |argMsg, time, host, recvPort|
      var msg = VertexMessage.decode(host, argMsg);
      var method = (msg.methodName++"Handler").asSymbol;
      var vertex = Vertex(msg.name);

      MeshDebugMon(thisFunctionDef, msg);
      vertex.tryPerform(method, msg.args);
    }, oscDefPath)
  }

}
