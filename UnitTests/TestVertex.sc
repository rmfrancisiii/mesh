TestVertex : UnitTest {
	var mesh, thisHost, hostName;

	setUp {
			"Testing Vertexes".postln;
			//this.makeOSCdefs;
			mesh = TestMesh.new.makeMesh;
			mesh.push;
			thisHost = Mesh.thisHost;
			hostName = thisHost.name.asSymbol;
			// for local only (eg. on the bus)
			/*" switching to local LOOPBACK ONLY".warn;
			Mesh.broadcastAddr = MeshHostAddr("127.0.0.1", 57120 + (0..7));*/
	}

	tearDown {
	}

	test_vertex {
		this.vertexInitialized;
		this.makeVertex(\server1, \server, hostName);
		{this.vertexExists(mesh, \server1)}.defer(2);
	}

	vertexInitialized {
		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

	makeVertex { |name, type, hostName|
		Vertex.new(name, type, hostName);
	}

	vertexExists{ |mesh, key|
		this.assert( mesh.vertexes.includesKey(key),
			"Key Exists in mesh.vertexes");

	}

	makeOSCdefs {
		"setting up test OSCdef responder".postln;
		OSCdef(\TestVertexServerRequestor, {|msg, time, addr, recvPort|
		("VertexServerRequestor received message: " ++ msg).postln;
		}, '/VertexServer/request/new');
	}
}
