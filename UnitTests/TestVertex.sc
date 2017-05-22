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

	test_vertex {|local = true|
		Routine.new({
		this.vertexInitialized;
		if (local == true)
			{"Resetting broadcast address. local loopback testing only!".warn;
				Mesh.broadcastAddr = MeshHostAddr("127.0.0.1", 57120 + (0..7))};
		this.makeVertex(\server1, \server, hostName);
		2.yield;
		this.vertexExists(mesh, \server1);
		}).play
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
