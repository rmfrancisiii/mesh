VertexTypeClassInterface {
// this enables calling Class methods on a remote machine
// needs error handling
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
// this enables calling Instance methods on a remote machine
  *makeInstanceInterface { |vertex|
    var oscDefName = (vertex.name.asSymbol ++ "Interface").asSymbol ;
    var oscDefPath = "/" ++ vertex.name.asSymbol ++ "/interface" ;

    OSCdef(oscDefName, { |argMsg, time, host, recvPort|
      var msg = VertexMessage.decode(host, argMsg);
      var method = (msg.methodName++"Handler").asSymbol;
      // needs to handle error if vertex does not exist (not try to create)
      var vertex = Vertex.at(msg.name);
      Vertex.at(msg.name).postln;
      // this only prints error on host machine.
      // needs: try, on failure, return Error
      if (vertex.class.findMethod(method).isNil){"ERROR".postln};
      vertex.tryPerform(method, msg.args);
    }, oscDefPath)
  }

}
