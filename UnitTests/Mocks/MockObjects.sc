MockMesh {
	var <>name;

	*new {|name|
		^ super.new.init(name)
	}

	init {|name|
		name = name;
		postf("Mock Mesh Created: % \n", (name));
	}

}

MockBeacon {
	var <>name;

	*new {|name|
		^ super.new.init(name)
	}

	init {|name|
		name = name;
		postf("Mock Beacon Created: % \n", (name));
	}

	fakeHostAdd {|mgr, key|
		var addr = MeshHostAddr("localhost", 57110);
		addr.postln;
		addr.class.postln;
  /*  var addr = MeshHostAddr.new(key, NetAddr.langPort);*/
  mgr.checkHost(key, addr);
	"Adding a fake Host".postln;
	}

}
