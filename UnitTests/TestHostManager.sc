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
		hosts.beacon = MockBeacon.new(hosts, 1.0);
		beacon = hosts.beacon;
		this.addManyFakeHosts(5, 3);
	}

	nextFakeHostName{
		^ ("fakeHost" ++ (hosts.names.size)).asSymbol;
	}

	addFakeHost{
		var newHost = this.nextFakeHostName;
		this.hostNotExists(newHost);
		hosts.beacon.fakeHostAdd(this.nextFakeHostName);
		this.hostExists(newHost);
		this.hostOnline(newHost);
	}

	addManyFakeHosts{|num, delay|
		num.do({|i|{ this.addFakeHost}.defer(i*delay)})
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

	hostExists {|host|
		this.assert( hosts.exists(host),
		"Host Exists");
	}

	hostNotExists {|host|
		this.assert( hosts.exists(host).not,
		"Host Does Not Exist");
	}

	hostOnline {|host|
		this.assert( hosts.isOnline(host),
		"Host is online");
	}

	hostNotOnline {|host|
		this.assert( hosts.isOnline(host).not,
		"Host is Not online");
	}

/*

	thisHostIsMeshHost

	timeoutsHasThisHost

	timeoutsHasTime
	*/



}
