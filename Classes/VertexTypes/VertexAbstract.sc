VertexAbstract {
  *makeRequestPath {
    ^ "/" ++ this.asSymbol ++ "/request/"
  }

  *makeConfirmPath {
    ^ "/" ++ this.asSymbol ++ "/confirm/"
  }

  *requestor { |vertexName, vertexHost, mesh ...passArgs|
    var path = (this.makeRequestPath ++ "vertex");
    vertexHost.sendMsg(path, vertexName, mesh.name, *passArgs);
    ^ ("OSC message sent to " ++ path).postln;
  }

}
