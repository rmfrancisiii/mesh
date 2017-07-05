TestMeshPatchbay : UnitTest {
	var mesh, patchbay;

	setUp {
			"Testing Patchbay".postln;
			patchbay = MeshPatchbay.new;
	}

	tearDown {
		"Tearing down".postln;
		patchbay.free;
	}

	test_meshPatchbay {
		"Tests:".postln;
		Mesh(\mesh1).push;
		Mesh.broadcastAddr.addr = MeshHostAddr("127.0.0.1", 57120 + (0..7));
		Mesh.current.hosts.beacon.start;
		mesh = Mesh(\mesh1);
		this.isPatchbay;
		1.0.wait;
		this.addVertexes;
		2.0.wait;
		this.addPatches;
		1.0.wait;
		this.testPatches;
		"Tests Completed!".postln
	}

	addVertexes{
		Vertex(\server1, \server, \rose, \mesh1);
		Vertex(\server2, \server, \rose, \mesh1);
		Vertex(\server3, \server, \rose, \mesh1);
		Vertex(\server4, \server, \rose, \mesh1);
	}

	addPatches {
		Mesh(\mesh1).patchbay.addPatch(\server1, \server2);
		Mesh(\mesh1).patchbay.addPatch(\server1, \server3);
		Mesh(\mesh1).patchbay.addPatch(\server1, \server4);
		Mesh(\mesh1).patchbay.addPatch(\server2, \server3);
		Mesh(\mesh1).patchbay.addPatch(\server2, \server4);
		Mesh(\mesh1).patchbay.addPatch(\server3, \server4);
	}

	isPatch {|patch|
		this.assert( patch.isKindOf(MeshPatch),
		"patch is Patch".postln);
	}

	isPatchbay {
		this.assert( mesh.patchbay.isKindOf(MeshPatchbay),
		"patchbay is Patchbay".postln);
	}

	testPatches{
		this.assert( Mesh(\mesh1).patchbay.getPatch(\server1, \server2).isKindOf(MeshPatch),
		"patchbay is Patchbay".postln);

	}


}
