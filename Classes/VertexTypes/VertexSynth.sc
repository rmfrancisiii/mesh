VertexSynth : VertexAbstract {
  classvar <>synthList;
  var <>synthDef;

  *initClass{
    synthList = this.loadSynthList;
  }

  *loadSynthList{
    var path = PathName("".resolveRelative).parentLevelPath(2);
    var folder = path +/+ PathName("SynthDefs/*");
    var files = folder.pathMatch;
    var synths = files.collectAs({|file|

        this.extractSynth(file).name -> this.extractSynth(file) }, IdentityDictionary);

    ^ synths;
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
    synthDef = synthList[msg.args[0]];
  }

}
