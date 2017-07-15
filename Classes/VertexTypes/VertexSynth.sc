VertexSynth : VertexAbstract {
  classvar <>synthDict;
  var <>synthDef, <>pdefnList;

  *initClass{
    synthDict = this.loadSynthDict;
  }

  *loadSynthDict{
    var path = PathName("".resolveRelative).parentLevelPath(2);
    var folder = path +/+ PathName("SynthDefs/*");
    var files = folder.pathMatch;
    var synthDict = files.collectAs({|file|
        this.extractSynth(file).name -> this.extractSynth(file) }, IdentityDictionary);
    ^ synthDict;
  }
  *extractSynth{|file|
    ^ Object.readArchive(file);
  }

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
    synthDef = synthDict[msg.args[0]];
    pdefnList = synthDef.metadata.keysValuesDo({ |parameter, pattern|
      parameter = parameter.asString;
      parameter[0] = parameter.first.toUpper;
      parameter = (name ++ parameter).asSymbol;
      Pdefn(parameter, pattern)
      })
  }

}
