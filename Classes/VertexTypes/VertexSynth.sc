VertexSynth : VertexAbstract {
  var <>synthDef;

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

  patchOut {|vertexIn|
    "PATCHING TO %. \n".postf(vertexIn);
  }

  patchIn {|vertexOut|
    "PATCHING FROM %. \n".postf(vertexOut);
  }

}
