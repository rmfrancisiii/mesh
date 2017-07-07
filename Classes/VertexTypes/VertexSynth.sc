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
    this.setInstanceVars(msg);
    isProxy = false;
    this.makeInstanceInterfaces;
  }

  initProxy {|msg|
    this.setInstanceVars(msg);
		isProxy = true;
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
