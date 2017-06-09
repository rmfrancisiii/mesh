VertexTypeClassMessage {
  var <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>methodName, <>args;

  *newRequest {|name, type, host, mesh, args|
    var upCaseType = this.upcaseFirst(type);
    var path = "/Vertex" ++ upCaseType ++ "/interface";
    MeshDebugMon(thisFunctionDef);
    ^ super.newCopyArgs(path, name, type, Mesh(mesh)[host], Mesh.thisHost, Mesh(mesh), \newVertexRequest, args)
  }

  *upcaseFirst{|string|
    string = string.asString;
    string[0] = string.first.toUpper;
    ^string
  }

  *decode {|host, msg|
    MeshDebugMon(thisFunctionDef);
    ^  super.newCopyArgs(msg[0], msg[1], msg[2], Mesh(msg[5])[msg[3]], Mesh(msg[5])[msg[4]], Mesh(msg[5]), msg[6], msg[7..])
  }

  sendRequest {
    var request = this.asOSCMsg;
    MeshDebugMon(thisFunctionDef);
    vertexHost.sendMsg(*request);
  }

  sendResponse {
    MeshDebugMon(thisFunctionDef);
    requestingHost.sendMsg(*this.asOSCMsg);
  }

  asOSCMsg {
    ^ Array.with(path, name, type, vertexHost.name, requestingHost.name,  mesh.name, methodName, *args)
  }

  printOn {|stream|
    [path, name, type, vertexHost.name, requestingHost.name,
     mesh.name, methodName, args].postln;
  }

}
