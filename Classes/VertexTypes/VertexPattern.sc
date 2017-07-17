VertexPattern : VertexAbstract {
  var <>patternDict, <>pbind;

  *makeClassInterface {
    VertexTypeClassInterface.makeGenericClassInterfaces(this)
  }

  makeInstanceInterfaces{
    VertexTypeInstanceInterface.makeInstanceInterface(this);
  }

  getVertexHost {
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

  playHandler{
    "PLAYING".postln;
  }

  patchOutput {|vertexIn|
    "PATCHING % TO %. \n".postf(this.name, vertexIn);
    "Adding pattern pdefns to pbind:".postln;

    vertexIn.postln;
    vertexIn.class.postln;
    vertexIn.postln;
    Vertex(vertexIn).postln;
    Vertex(vertexIn).name.postln;
    Vertex(vertexIn).pdefnList.postln;

    /*pbind = Pbind.new(
      \instrument,     \sin,
      * Vertex(vertexIn).pdefnList
      );

      /** );*/

    pbind.postln;*/

  }

  patchInput {|vertexOut|
		"PATCHING % FROM %. \n".postf(this.name, vertexOut);
	}

  setInstanceVars {|msg|
    name = msg.name;
    mesh = msg.mesh;
  }

}
