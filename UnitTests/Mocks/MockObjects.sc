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
	var <>manager, beaconKeeper, <>pollPeriod;

	*new {|mgr|
		^ super.new.init(mgr)
	}

	init {|mgr|
		manager = mgr;
		beaconKeeper = this.fakeBeaconAdd(1.0);
		"Mock Beacon Created".postln;
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

	fakeBeaconAdd {|poll = 1.0|
		pollPeriod = poll;
		^ SkipJack({
			manager.checkTimeouts;
			}, poll, false);
		}


}
