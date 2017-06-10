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
  // creating OSC defs for instances of Vertexes and Proxies
  // accessed by vertexName, operates method on remote server

    *makeInstanceInterfaces { |inst|
      var oscDefName = (inst.name ++ "Interface").asSymbol;
      var oscDefPath = "/" ++ inst.name ++ "/interface";
      MeshDebugMon(thisFunctionDef);

      oscDefName.postln;
      oscDefPath.postln;


      OSCdef(oscDefName, { |msg, time, host, recvPort|
        var instance;
        var method;
        var args;
        MeshDebugMon(thisFunctionDef);

          msg = VertexMessage.decode(host, msg) ;
          instance = Vertex(msg.name) ;
          method = (msg.methodName++"Handler").asSymbol ;
          args = msg.args;
          instance.tryPerform(method, *args) ;
      }, oscDefPath)

    }

}
