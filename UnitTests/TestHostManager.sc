TestMeshHostsManager : UnitTest {
	var <>dummyMesh, <>thisHost, <>hosts;

	setUp {
		thisHost = Mesh.thisHost;
		dummyMesh = MockMesh.new(\Mock1);
		hosts = MeshHostsManager.new(dummyMesh, thisHost);
	}

	tearDown {
	}

	test_hostsManager{
		this.hostsManagerInitialization;
		hosts.beacon.stop;
		hosts.beacon = MockBeacon.new(hosts, 1.0);
		this.addManyFakeHosts(5, 3);
		this.setAllFakeHostOffline(5);
	}

	setAllFakeHostOffline {|delay|

		{	"check all hosts offline".postln;
			this.checkAllHostOffline;
			}.defer(delay + ((hosts.all.size) * delay));

		hosts.all.keysDo({|key, i|
			{
				"setting host offline".postln;
				this.setFakeHostOffline(key);
				}.defer(delay + (i*delay));
			})
	}

	checkAllHostOffline {
		hosts.all.keysDo({|key| this.hostNotOnline(key)});
	}


	setFakeHostOffline {|key|
		hosts.beacon.fakeHostSetOffline(key);
	}

	hostsManagerInitialization {
		this.meshHostsManagerIsMeshHostsManager;
		this.allIsIdentityDictionary;
		this.timeoutsIsIdentityDictionary;
		this.beaconIsBeacon;
	}

	nextFakeHostName{
		^ ("fakeHost" ++ (hosts.all.size)).asSymbol;
	}

	addFakeHost{
		var newHost = this.nextFakeHostName;
		this.hostNotExists(newHost);
		hosts.beacon.fakeHostAdd(this.nextFakeHostName);
		this.hostExists(newHost);
		this.hostOnline(newHost);
	}

	addManyFakeHosts{|num, delay|
		num.do({|i|
			{this.addFakeHost}.defer(i*delay)
		})
	}

	meshHostsManagerIsMeshHostsManager{
		this.assert( hosts.isKindOf(MeshHostsManager),
		"MeshHostManager is a MeshHostManager");
	}

	allIsIdentityDictionary{
		this.assert( hosts.all.isKindOf(IdentityDictionary),
		"HostDict is an IdentityDictionary");
	}

	timeoutsIsIdentityDictionary{
		this.assert( hosts.timeouts.isKindOf(IdentityDictionary),
		"timeouts is an IdentityDictionary");
	}

	beaconIsBeacon{
		this.assert( hosts.beacon.isKindOf(Beacon),
		"Beacon is a beacon");
	}

	beaconIsMockBeacon{|obj|
		this.assert( hosts.beacon.isKindOf(MockBeacon),
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
