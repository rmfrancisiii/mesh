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

	fakeHostAdd {|mgr, host|
//	addr = addr.as(MeshHostAddr);
//	mesh.hosts.checkHost(key, addr);

	}

}
