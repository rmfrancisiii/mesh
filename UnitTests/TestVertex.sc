TestVertex : UnitTest {
	var mesh;

	setUp {
			"Testing Vertexes".postln;
			this.makeOSCdefs;
			mesh = TestMesh.new.makeMesh;
			mesh.push;
	}

	tearDown {
	}

	test_vertex {
		this.vertexInitialized;
		this.makeVertex(\vertex1, \server, \rose);
		this.vertexExists(mesh, \vertex1);
	}

	vertexInitialized {
		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

	makeVertex { |name, type, host|
		Vertex.new(name, type, host);
	}

	vertexExists{ |mesh, key|
		this.assert( mesh.vertexes.includes(key),
			"Key Exists in mesh.vertexes");

	}

	makeOSCdefs {
		"setting up test OSCdef responder".postln;
		OSCdef(\TestVertexServerRequestor, {|msg, time, addr, recvPort|
		("VertexServerRequestor received message: " ++ msg).postln;
		}, '/VertexServer/request/new');
	}
}
