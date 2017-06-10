/*
Host rose joined the mesh
New Mesh Created: mesh1
WARNING: No current mesh
Entering Mesh: mesh1
-> [ Mesh(mesh1) ]
-> Mesh
-> a Beacon
Meta_Vertex:getMesh
Meta_Vertex:currentMesh
Meta_Vertex:getVertex
Meta_Vertex:new
Meta_Vertex:getHost
Meta_VertexAbstract:newVertexRequest
Meta_VertexTypeClassMessage:newRequest
VertexTypeClassMessage:sendRequest
-> New Vertex Request sent
Meta_VertexTypeClassMessage:decode
Meta_VertexAbstract:newVertexRequestHandler
VertexServer:initVertex
VertexServer:makeInstanceInterfaces
Meta_VertexAbstract:makeVertex
Meta_VertexAbstract:sendConfirmation
VertexTypeClassMessage:sendResponse
Meta_VertexAbstract:sendProxyRequest
VertexTypeClassMessage:sendProxyRequest
a MeshHostAddr(127.0.0.1, 57127)
Meta_VertexTypeClassMessage:decode
Meta_VertexAbstract:confirmationHandler
Meta_VertexTypeClassMessage:decode
Meta_VertexAbstract:proxyRequestHandler



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




*vertexResponseHandler { |msg|
  var response = msg.args[0];
  var result = case
      { response == \error }   {
        (msg.name ++ " Vertex Error:  " ++ msg.args[1]).postln }
      { response == \confirmed } {
        (msg.name ++ " Vertex Confirmed!").postln };
  result.value;
  MeshDebugMon(thisFunctionDef, msg);

}


*proxyResponseHandler { |msg|

  "proxy response received".postln;

  // from vertexResponseHandler

  //should track that all mesh hosts confirm proxy request
  and resend if necessary?

}



*sendProxyRequest{ |msg|
  MeshDebugMon(thisFunctionDef);
    // var path = this.makeClassOSCdefPath("Request", "Proxy");
    // msg.sendProxyRequest(path);
}

*sendProxyConfirmation{ |msg|
    var path = this.makeClassOSCdefPath("Response", "Proxy");
    MeshDebugMon(thisFunctionDef);
    msg.sendProxyResponse(path);
}



*makeClassOSCdefPath {|transaction, object|
  ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
}
*/



		/*var interfaces = Array.with(
    ["boot", "server", \bootHandler],
    ["kill", "server", \killHandler],
    ).do({|args|
			VertexTypeInstanceInterface.new(this, *args)});
			"instance interface initialized".postln;




      */

/*
      c = Condition(false); fork { 0.5.wait; "started ...".postln; c.wait;  "... and finished.".postln };
c.instVarDict.postln;


c.test = true;
c.signal;*/
