VertexPattern : VertexAbstract {
  var <>server, <>pattern;

  *makeClassInterface {
		VertexTypeClassInterface.makeGenericClassInterfaces(this)
	}

  *getVertexHost {
    //todo: something better than this
    ^ Mesh.thisHost;
  }

  initVertex{|msg|
    isProxy = false;
    name = msg.name;
    server = msg.args[0];
    pattern = msg.args[1];
  }

  makeInstanceInterfaces{
    var interfaces = Array.with(
    ["play", "pattern", \playHandler],
    ["stop", "pattern", \stopHandler],
    ).do({|args|
      VertexTypeInstanceInterface.new(this, *args)});
  }

  playHandler{ |requestingHost, msg|
    pattern.play
  }

}
