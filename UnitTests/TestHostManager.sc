TestMeshHostsManager : UnitTest {
	var dummyMesh, thisHost, hosts, timeouts, beacon;

	setUp {
		thisHost = Mesh.thisHost;
		dummyMesh = MockMesh.new(\Mock1);
		hosts = MeshHostsManager.new(dummyMesh, thisHost);
	}

	tearDown {
	}


	test_hostsManager{
		this.hostsManagerInitialization(hosts);
		hosts.beacon = MockBeacon.new(\testBeacon);
	}

	addFakeHost{
		var fakeHostAddr = MeshHostAddr.new(\testHost1);
		var fakeHost = MeshHost.new(\testHost1, fakeHostAddr);
		// verify beacon is FAKE beacon;
		hosts.beacon.fakeHostAdd(hosts, fakeHost);

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


	/*thisHostExists

	thisHostIsOnline

	thisHostIsMeshHost

	timeoutsHasThisHost

	timeoutsHasTime*/



}
