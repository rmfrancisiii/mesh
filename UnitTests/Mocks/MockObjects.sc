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
	var <>name, <>manager;

	*new {|name, mgr|
		^ super.new.init(name, mgr)
	}

	init {|nname, mgr|
		name = name;
		manager = mgr;
		postf("Mock Beacon Created: % \n", (name));
	}

	nextFakeIp {
		^ "192.168.0." ++ (manager.all.size + 101)
	}

	fakeHostAdd {|key|
		var ip = this.nextFakeIp;
		var addr = MeshHostAddr(ip, 57110);
		ip.postln;
  	manager.checkHost(key, addr);
		"Adding a fake Host".postln;
	}

}
