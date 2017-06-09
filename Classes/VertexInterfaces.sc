VertexTypeClassInterface {

    *makeGenericClassInterfaces { |vertexType|
      // this is called on compilation by each Vertex Type Class:
      //    Mesh.initClass ->
      //      Vertex.initVertexTypes ->
      //        VertexTypeDict.new ->
      //          VertexTypeDict.initTypeOSCdefs ->
      //            VertexType.makeClassInterface(thisVertexType)
      //
      //  it provides each VertexType with a Local and OSC interface for:
      //    creating new vertexes
      //    confirmation/error messages
      //    potentially for global messages to
      //    every vertex of a Vertex Type
      //
      // responds to /VertexType/interface
      //
      // Message contains:
      // <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>methodName, <>args

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
