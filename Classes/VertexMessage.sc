VertexTypeClassMessage {
  var <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>methodName, <>args;

  *newRequest {|name, type, host, mesh, args|
    var path = "/Vertex" ++ type ++ "/interface";
    ^ super.newCopyArgs(path, name, type, Mesh(mesh)[host], Mesh.thisHost, Mesh(mesh), \new, args)
  }

  *decode {|host, msg|
    ^  super.newCopyArgs(msg[0], msg[1], msg[2], Mesh(msg[5])[msg[3]], Mesh(msg[5])[msg[4]], Mesh(msg[5]), msg[6], msg[7..])
  }

  sendRequest {
    var request = this.asOSCMsg;
    MeshDebugMon(request);
    // FIX NAME OF VertexType
    vertexHost.sendMsg(*request);
  }

  asOSCMsg {
    ^ Array.with(path, name, type, vertexHost.name, requestingHost.name,  mesh.name, methodName, *args)
  }

  printOn {|stream|
    path.postln;
    name.postln;
    type.postln;
    vertexHost.name.postln;
    requestingHost.name.postln;
    mesh.name.postln;
    methodName.postln;
    (args).postln;
  }

}
