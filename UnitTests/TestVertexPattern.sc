TestVertexPattern : UnitTest {
	var mesh, patchbay;

	setUp {
	Mesh(\mesh1, local:true).push;
	0.5.wait;
	Vertex(\localServer, \server, \rose, \mesh1);
	0.5.wait;
	Vertex(\localServer).boot;
	2.0.wait;
	Vertex(\sinSynth, \synth, \rose, \mesh1, \sin);
	0.5.wait;
	Vertex(\sawSynth, \synth, \rose, \mesh1, \saw);
	0.5.wait;
	Mesh(\mesh1).patchbay.addPatch(\sawSynth, \localServer);
	0.5.wait;
	Mesh(\mesh1).patchbay.addPatch(\sinSynth, \localServer);
	0.5.wait;
	Vertex(\sawPattern, \pattern, \rose, \mesh1);
	0.5.wait;
	Vertex(\sinPattern, \pattern, \rose, \mesh1);
	0.5.wait;
	Mesh(\mesh1).patchbay.addPatch(\sawPattern, \sawSynth);
	0.5.wait;
	Mesh(\mesh1).patchbay.addPatch(\sinPattern, \sinSynth);

	}

	tearDown {
		"Tearing down".postln;
		patchbay.free;
	}

	test_meshPatchbay {
		"Tests:".postln;

	Vertex(\sinPattern).play;
	Vertex(\sawPattern).play;

		"Tests Completed!".postln
	}



}
