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
    this.setInstanceVars(msg);
    isProxy = false;

      patternDict = IdentityDictionary.with(*[\name -> msg.name]);
  }

  initProxy {|msg|
    this.setInstanceVars(msg);
		isProxy = true;
    this.makeInstanceInterfaces;

  }

  proxyUpdateHandler {|args|
  }

  errorHandler {
  }

  freeHandler{
  }

  patchOutput {|vertexIn|
    "PATCHING % TO %. \n".postf(this.name, vertexIn);
  }

  patchInput {|vertexOut|
		"PATCHING % FROM %. \n".postf(this.name, vertexOut);
	}

  setInstanceVars {|msg|
    name = msg.name;
    mesh = msg.mesh;
  }

}
