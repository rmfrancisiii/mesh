TestMeshHostsManager : UnitTest {
	var dummyMesh, thisHost, hosts, beacon;

	setUp {
		thisHost = Mesh.thisHost;
		dummyMesh = MockMesh.new(\Mock1);
		hosts = MeshHostsManager.new(dummyMesh, thisHost);
	}

	tearDown {
	}

	test_hostsManager{
		this.hostsManagerInitialization(hosts);
		hosts.beacon.stop;
		hosts.beacon = MockBeacon.new(\testBeacon);
		beacon = hosts.beacon;
		this.addFakeHost;
		hosts.names.postln;
	}

	addFakeHost{
//		var fakeHostAddr = MeshHostAddr.new(\testHost1, NetAddr.langPort);
	  var fakeHost = "fakehost";
		//MeshHost.new(\testHost1, fakeHostAddr);
		// verify beacon is FAKE beacon;
	//	hostsMgr.beacon.fakeHostAdd(hostsMgr, fakeHost);
		/*hosts.postln;*/
		hosts.beacon.fakeHostAdd(hosts, fakeHost );
	}

	hostsManagerInitialization {|obj|
		this.meshHostsManagerIsMeshHostsManager(obj);
		this.allIsIdentityDictionary(obj);
		this.timeoutsIsIdentityDictionary(obj);
	}

	meshHostsManagerIsMeshHostsManager{|obj|
		this.assert( obj.isKindOf(MeshHostsManager),
		"MeshHostManager is a MeshHostManager");
	}

	allIsIdentityDictionary{|obj|
		this.assert( obj.all.isKindOf(IdentityDictionary),
		"HostDict is an IdentityDictionary");
	}

	timeoutsIsIdentityDictionary{|obj|
		this.assert( obj.timeouts.isKindOf(IdentityDictionary),
		"timeouts is an IdentityDictionary");
	}

	beaconIsBeacon{|obj|
		this.assert( obj.beacon.isKindOf(Beacon),
		"Beacon is a beacon");
	}

	beaconIsMockBeacon{|obj|
		this.assert( obj.beacon.isKindOf(MockBeacon),
		"Beacon is a Mock Beacon");
	}

	/*thisHostExists

	thisHostIsOnline

	thisHostIsMeshHost

	timeoutsHasThisHost

	timeoutsHasTime*/



}
