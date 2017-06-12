/*

Vertex.boot {
  msg.methodName=\boot
  msg.oscSendMethod} ->

OSCdef: /vertexName/method
  Vertex(vertexName).tryPerform(msg.method, msg.args)







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
*/

/*
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
*/

/*

c = Condition(false); fork { 0.5.wait; "started ...".postln; c.wait;  "... and finished.".postln };
c.instVarDict.postln;


c.test = true;
c.signal;



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

*/
