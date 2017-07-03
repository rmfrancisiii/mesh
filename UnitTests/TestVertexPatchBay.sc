TestVertexPatchBay : UnitTest {
	var patchBay;

	setUp {
			"Testing Patch Bay".postln;
			patchBay = VertexPatchbay.new;
	}

	tearDown {
		"Tearing down".postln;
		patchBay.free;
	}

	test_vertexPatchBay {
		"Tests:".postln;
		this.isPatchBay;
		patchBay.addVertex(\vertex1);
		patchBay.addPatch(\vertex1, \vertex2);
		this.isPatch(patchBay.getPatch(\vertex1, \vertex2));
		"Tests Completed!".postln
	}

	isPatch {|patch|
		this.assert( patch.isKindOf(VertexPatch),
		"patch is Patch".postln);
	}

	isPatchBay {
		this.assert( patchBay.isKindOf(VertexPatchbay),
		"patchBay is Patchbay".postln);
	}



}
