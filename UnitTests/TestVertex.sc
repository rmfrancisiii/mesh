TestVertex : UnitTest {
	var mesh;

	setUp {
			"Testing Vertexes".postln;
			mesh = TestMesh.new.makeMesh;
			mesh.push;

	}

	tearDown {
	}

	test_vertex {
		this.vertexInitialized;
		this.makeVertex(\serVer1, \VertexServer, \rose);
		mesh.vertexes.postln;
	}

	vertexInitialized {
		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

	makeVertex { |name, type, host|
		Vertex.new(name, type, host);
	}

}
