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
    var vertex = Vertex(vertexIn);
    var pdefnArray = vertex.pdefnDict.getPairs;
    "PATCHING % TO %. \n".postf(this.name, vertex.name);
    "Adding pattern pdefns to pbind:".postln;
    pdefnArray.postln;
    pbind = Pbind.new(
      \instrument, Vertex(vertexIn).synthDef.name,
      * pdefnArray
      // THIS HAS TO BE A DICT, NEEDS parameterName -> Pdefn
    //  vertex.pdefnDict;
            );

  }

  patchInput {|vertexOut|
		"PATCHING % FROM %. \n".postf(this.name, vertexOut);
	}

  setInstanceVars {|msg|
    name = msg.name;
    mesh = msg.mesh;
  }

}
