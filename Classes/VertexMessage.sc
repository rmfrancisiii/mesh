VertexRequestMessage {
  var <>path, <>name, <>type, <>vertexHost, <>requestingHost, <>mesh, <>args;


  *newRequest {|path, name, type, host, mesh, args|
    ^ super.newCopyArgs(path, name, type, host, Mesh.thisHost, Mesh(mesh), args)
  }

  sendRequest {
    vertexHost.sendMsg(*this.asOSCMsgArray)
  }

  asOSCMsgArray {
    ^ Array.with(path, name, type, vertexHost.name, requestingHost.name,  mesh.name, *args)
  }

  asErrorResponse{|responsePath, error|
    ^ Array.with(responsePath, name, type, vertexHost.name, requestingHost.name,  mesh.name, error)
  }

  asObjectArray {
    ^ Array.with(path, name, type, vertexHost, requestingHost,  mesh, *args)
  }

  *decode {|host, msg|
    ^  super.newCopyArgs(msg[0], msg[1], msg[2], Mesh(msg[5])[msg[3]], Mesh(msg[5])[msg[4]], Mesh(msg[5]), msg[6..])
  }

  printOn { |stream| stream << this.class.name << "(" << this.asObjectArray << ")" }

  sendError { |responsePath, error|
    var response = this.asErrorResponse(responsePath, error);
    requestingHost.sendMsg(*response);
  }

  sendConfirmation {}

  sendProxyRequest {}

}
