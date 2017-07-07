VertexPattern : VertexAbstract {
  var <>patternDict;

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
      patternDict = IdentityDictionary.with(*[\name -> msg.name]);
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
    "NO".postln;

		"PATCHING FROM %. \n".postf(vertexOut);
	}

}
