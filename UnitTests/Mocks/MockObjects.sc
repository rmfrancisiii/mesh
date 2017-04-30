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
	var <>manager, <>beaconKeeper, <>fakeHosts, <>pollPeriod;

	*new {|mgr, poll|
		^ super.new.init(mgr, poll)
	}

	init {|mgr, poll|
		manager = mgr;
		pollPeriod = poll;
		beaconKeeper = this.fakeBeaconAdd;
		fakeHosts = IdentityDictionary.new;
		"Mock Beacon Created".postln;
	}

	nextFakeIp {
		^ "192.168.0." ++ (manager.all.size + 101)
	}

	fakeHostAdd {|key|
		var ip = this.nextFakeIp;
		var port = 57110;
		var addr = MeshHostAddr(ip, port);
		var arry = Array.with(addr, true);
		manager.checkHost(key, addr);
		fakeHosts.put(key, arry);
	}

	fakeHostSetOffline{|key|
		key.postln;
		fakeHosts.at(key).postln; //put(1,false);


		//fakeHosts.at(key).put(1,false);
	}

	fakeBeaconAdd {
		^ SkipJack({

			fakeHosts.keysValuesDo({|key, arry|
				 if (arry[1])
					{ manager.checkHost(key, arry[0]) };
					});

			manager.checkTimeouts;
			}, pollPeriod, false);
		}


}
