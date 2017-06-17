VertexMessage {
  var <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>methodName, <>args;

  *newVertexRequest {|name, type, host, mesh, args|
    var upCaseType = this.upcaseFirst(type);
    var path = "/Vertex" ++ upCaseType ++ "/interface";
    ^ super.newCopyArgs(path, name, type, Mesh(mesh)[host], Mesh.thisHost, Mesh(mesh), \newVertex, args)
  }

  *newMethodRequest {|vertex, vertexHost, selector, args|
    var path = "/" ++ vertex.name ++ "/interface";
    var mesh = vertex.mesh;
    var type = vertex.class.name.asSymbol;
    var requestingHost = Mesh.thisHost;
    var vertexName = vertex.name;
    ^ super.newCopyArgs(path, vertexName, type, vertexHost, requestingHost, mesh, selector, args);
  }

  *upcaseFirst{|string|
    string = string.asString;
    string[0] = string.first.toUpper;
    ^string
  }

  *decode {|host, msg|
    ^  super.newCopyArgs(msg[0], msg[1], msg[2], Mesh(msg[5])[msg[3]], Mesh(msg[5])[msg[4]], Mesh(msg[5]), msg[6], msg[7..])
  }

  sendRequest {
    //PROBLEM WITH *this.asOSCMsg WHEN USING BROADCAST ADDR

    vertexHost.sendMsg(*this.asOSCMsg);
  }

  sendResponse {
    requestingHost.sendMsg(*this.asOSCMsg);
  }

  sendProxyRequest {
    Mesh.broadcastAddr.sendMsg(*this.asOSCMsg);
  }

  asOSCMsg {
    //PROBLEM WITH vertexHost.name WHEN USING BROADCAST ADDR

    ^ Array.with(path, name, type, vertexHost.name, requestingHost.name,  mesh.name, methodName, *args)
  }

  printOn {|stream|
    //this.asOSCMsg.postln;
    this.instVarDict.postln;
  }


}
