TestVertex : UnitTest {
	var mesh, thisHost, hostName;

	setUp {
			"Testing Vertexes".postln;
			//this.makeOSCdefs;
			mesh = TestMesh.new.makeMesh;
			mesh.push;
			thisHost = Mesh.thisHost;
			hostName = thisHost.name.asSymbol;
	}

	tearDown {
	}

	test_vertex {
		this.vertexInitialized;
		this.makeVertex(\vertex1, \server, hostName);
		this.vertexExists(mesh, \vertex1);
	}

	vertexInitialized {
		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

	makeVertex { |name, type, hostName|
		Vertex.new(name, type, hostName);
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
