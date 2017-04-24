TestMeshHosts : UnitTest {
	var dummyMesh, mgr, me, hosts, timeouts, beacon;

	setUp {
		me = Mesh.me;
		dummyMesh = MockMesh.new(\Mock1);
		mgr = MeshHostManager.new(dummyMesh, me);
	}

	tearDown {
	}


	test_hostManagerInitialization {
		this.assert( mgr.isKindOf(MeshHostManager),
		"MeshHostManager is a MeshHostManager");
		//
		// this.assert( hosts.isKindOf(IdentityDictionary),
		// "HostDict is a IdentityDictionary");

	}

}