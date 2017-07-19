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
    this.makeInstanceInterfaces;
  }

  initProxy {|msg|
    this.setInstanceVars(msg);
		isProxy = true;
    this.makeInstanceInterfaces;
  }

  proxyUpdateHandler {|args|
  }

  setHandler{|args|

    args.class.postln;
    args.size.postln;
    //flatten(2).postln;
    //|parameter, pattern|
    // working on this
    //parameter[0].postln;
    //pattern.postln;
    //patternDict[parameter[0].asSymbol].source.postcs;
    //patternDict[parameter[0].asSymbol].source = pattern;

    "Updating".postln;
  }

  errorHandler {
  }

  freeHandler{
  }

  playHandler{
    "PLAYING".postln;
    this.pbind.play;
  }

  patchOutput {|vertexIn|
    var vertex = Vertex(vertexIn);
    var pdefnArray = vertex.pdefnDict.getPairs;
    patternDict.putPairs(pdefnArray);

    "PATCHING % TO %. \n".postf(this.name, vertex.name);
    "Adding pattern pdefns to pbind:".postln;
    pbind = Pbind.new(
      \instrument, Vertex(vertexIn).synthDef.name,
      * pdefnArray);
  }

  patchInput {|vertexOut|
		"PATCHING % FROM %. \n".postf(this.name, vertexOut);
	}

  setInstanceVars {|msg|
    name = msg.name;
    mesh = msg.mesh;
  }

}
