VertexMessage {
  var <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>args;

// maybe convert this to a NamedList, send key->value pairs in message? more network overhead but easier to manage named arguments

  *newRequest {|path, name, type, host, mesh, args|
    ^ super.newCopyArgs(path, name, type, host, Mesh.thisHost, Mesh(mesh), args)
  }

  *decode {|host, msg|
    ^  super.newCopyArgs(msg[0], msg[1], msg[2], Mesh(msg[5])[msg[3]], Mesh(msg[5])[msg[4]], Mesh(msg[5]), msg[6..])
  }

  sendRequest {
    var request = this.asVertexRequest;
    vertexHost.sendMsg(*request)
  }

  sendError { |responsePath, error|
    var response = this.asErrorResponse(responsePath, error);
    requestingHost.sendMsg(*response);
  }

  sendConfirmation { |responsePath|
    var response = this.asConfirmation(responsePath);
    requestingHost.sendMsg(*response);
  }

  sendProxyRequest {|requestPath|
    var request = this.asProxyRequest(requestPath);
    var broadcastAddr = Mesh.broadcastAddr;
    broadcastAddr.sendMsg(*request)
  }

  sendProxyConfirmation { |responsePath|
    var response = this.asConfirmation(responsePath);
    requestingHost.sendMsg(*response);
  }

  asVertexRequest {
    ^ Array.with(path, name, type, vertexHost.name, requestingHost.name,  mesh.name, *args)
  }

  asErrorResponse{|responsePath, error|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name, mesh.name, \error, error)
  }

  asConfirmation{|responsePath|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name,  mesh.name, \confirmed)
  }

  asProxyRequest{|responsePath|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name,  mesh.name, *args)
  }

  asArray {
    ^ Array.with(path, name, type, vertexHost, requestingHost,  mesh, *args)
  }

  printOn { |stream| stream << this.class.name << "(" << this.asObjectArray << ")" }


}
