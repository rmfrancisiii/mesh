VertexTypeClassInterface {

    *makeGenericClassInterfaces { |vertexType|
      var oscDefName = (vertexType.asSymbol ++ "Interface").asSymbol;
      var oscDefPath = "/" ++ vertexType.asSymbol ++ "/interface" ;

      OSCdef(oscDefName, {|msg, time, host, recvPort|
        var method;
         msg = VertexTypeClassMessage.decode(host, msg);
         method = (msg.methodName++"Handler").asSymbol;
         vertexType.tryPerform(method, msg);
      }, oscDefPath)
    }
}
