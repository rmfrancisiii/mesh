VertexPattern : VertexAbstract {
  var <>pattern;

  *makeClassInterface {
    VertexTypeClassInterface.makeGenericClassInterfaces(this)
  }

  makeInstanceInterfaces{
    VertexTypeInstanceInterface.makeInstanceInterface(this);
  }

  *getVertexHost {
    ^ Mesh.thisHost;
  }

  initVertex{|msg|
  }

  initProxy {|msg|
  }

  proxyUpdateHandler {|args|
  }

  errorHandler {
  }

  freeHandler{
  }

  patchIn {
  }

  patchOut {
  }

}
